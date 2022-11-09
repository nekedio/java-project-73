package hexlet.code.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigForTest;
import static hexlet.code.config.SpringConfigForTest.TEST_PROFILE;
import hexlet.code.dto.LabelDto;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelService;
import hexlet.code.service.LabelServiceImpl;
import hexlet.code.utils.TestUtils;
import static hexlet.code.utils.TestUtils.asJson;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForTest.class)
public class LabelControllerTest {

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private LabelService labelService;

    @Autowired
    private LabelRepository labelRepository;

    @BeforeEach
    public void clearBefore() {
        testUtils.removeAll();
    }

    @Test
    public void createLabel() throws Exception {
        testUtils.registerDefaultUser();
        assertEquals(0, labelRepository.count());

        LabelDto labelDto = new LabelDto("test-label");

        ResultActions response = testUtils.makeRequestAuth(
                post("/api/labels/")
                        .content(asJson(labelDto))
                        .contentType(APPLICATION_JSON),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isCreated());
        assertEquals(1, labelRepository.count());

        List<Label> labels = labelRepository.findAll();
        assertThat(
                labels.stream().anyMatch(s -> s.getName().equals(labelDto.getName()))
        ).isTrue();
    }

    @Test
    public void getLabels() throws Exception {
        testUtils.registerDefaultUser();

        assertEquals(0, labelRepository.count());

        Label label1 = createLabel("test-label-1");
        Label label2 = createLabel("test-label-2");

        ResultActions response = testUtils.makeRequestAuth(
                get("/api/labels/"),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());
        assertEquals(2, labelRepository.count());

        String json = response.andReturn().getResponse().getContentAsString();
        List<Label> labels = TestUtils.fromJson(json, new TypeReference<>() {
        });

        assertThat(
                labels.stream().anyMatch(s -> s.getName().equals(label1.getName()))
        ).isTrue();

        assertThat(
                labels.stream().anyMatch(s -> s.getName().equals(label2.getName()))
        ).isTrue();
    }

    @Test
    public void getLabel() throws Exception {
        testUtils.registerDefaultUser();

        Label defaultLabel = createLabel("test-label");

        ResultActions response = testUtils.makeRequestAuth(
                get("/api/labels/" + defaultLabel.getId()),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());

        String json = response.andReturn().getResponse().getContentAsString();
        Label label = TestUtils.fromJson(json, new TypeReference<>() {
        });

        assertEquals(defaultLabel.getName(), label.getName());
    }

    @Test
    public void updateLabel() throws Exception {
        testUtils.registerDefaultUser();

        Label defaultLabel = createLabel("test-label");

        LabelDto newLabelDto = new LabelDto("new-label");

        long countLabelBefore = labelRepository.count();

        ResultActions response = testUtils.makeRequestAuth(
                put("/api/labels/" + defaultLabel.getId())
                        .content(asJson(newLabelDto))
                        .contentType(APPLICATION_JSON),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());
        assertEquals(countLabelBefore, labelRepository.count());

        Label label = labelRepository.findById(defaultLabel.getId()).get();
        assertEquals(newLabelDto.getName(), label.getName());
    }

    @Test
    public void deleteLabel() throws Exception {
        testUtils.registerDefaultUser();

        Label label = createLabel("test-label");

        ResultActions response = testUtils.makeRequestAuth(
                delete("/api/labels/" + label.getId()),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());

        assertEquals(0, labelRepository.count());
    }

    public void createLabelWithIncorrectName() throws Exception {
        testUtils.registerDefaultUser();

        LabelDto labelDto = new LabelDto("");

        ResultActions response = testUtils.makeRequestAuth(
                post("/api/labels/")
                        .content(asJson(labelDto))
                        .contentType(APPLICATION_JSON),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isUnprocessableEntity());
    }

    private Label createLabel(String name) {
        LabelDto defaultLabelDto = new LabelDto(name);
        return labelService.createNewLabel(defaultLabelDto);
    }
}
