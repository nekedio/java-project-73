package hexlet.code.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigForTest;
import static hexlet.code.config.SpringConfigForTest.TEST_PROFILE;
import hexlet.code.dto.StatusDto;
import hexlet.code.model.Status;
import hexlet.code.repository.StatusRepository;
import hexlet.code.service.StatusService;
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
public class SatusControllerTest {

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private StatusService statusService;

    @Autowired
    private StatusRepository statusRepository;

    @BeforeEach
    public void clearBefore() {
        testUtils.removeAll();
    }

    @Test
    public void createStatus() throws Exception {
        testUtils.registerDefaultUser();
        assertEquals(0, statusRepository.count());

        StatusDto statusDto = new StatusDto("test-status");

        ResultActions response = testUtils.makeRequestAuth(
                post("/api/statuses/")
                        .content(asJson(statusDto))
                        .contentType(APPLICATION_JSON),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isCreated());
        assertEquals(1, statusRepository.count());

        List<Status> statuses = statusRepository.findAll();
        assertThat(
                statuses.stream().anyMatch(s -> s.getName().equals(statusDto.getName()))
        ).isTrue();
    }

    @Test
    public void getStatuses() throws Exception {
        testUtils.registerDefaultUser();

        assertEquals(0, statusRepository.count());

        Status status1 = createStatus("test-status-1");
        Status status2 = createStatus("test-status-2");

        ResultActions response = testUtils.makeRequestAuth(
                get("/api/statuses/"),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());
        assertEquals(2, statusRepository.count());

        String statusesToJson = response.andReturn().getResponse().getContentAsString();
        List<Status> statuses = TestUtils.fromJson(statusesToJson, new TypeReference<>() {
        });

        assertThat(
                statuses.stream().anyMatch(s -> s.getName().equals(status1.getName()))
        ).isTrue();

        assertThat(
                statuses.stream().anyMatch(s -> s.getName().equals(status2.getName()))
        ).isTrue();
    }

    @Test
    public void getStatus() throws Exception {
        testUtils.registerDefaultUser();

        Status defaultStatus = createStatus("test-status");

        ResultActions response = testUtils.makeRequestAuth(
                get("/api/statuses/" + defaultStatus.getId()),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());

        String json = response.andReturn().getResponse().getContentAsString();
        Status status = TestUtils.fromJson(json, new TypeReference<>() {
        });

        assertEquals(defaultStatus.getName(), status.getName());
    }

    @Test
    public void updateStatus() throws Exception {
        testUtils.registerDefaultUser();

        Status defaultStatus = createStatus("test-status");

        StatusDto newStatusDto = new StatusDto("new-status");

        long countStatusBefore = statusRepository.count();

        ResultActions response = testUtils.makeRequestAuth(
                put("/api/statuses/" + defaultStatus.getId())
                        .content(asJson(newStatusDto))
                        .contentType(APPLICATION_JSON),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());
        assertEquals(countStatusBefore, statusRepository.count());

        Status status = statusRepository.findById(defaultStatus.getId()).get();
        assertEquals(newStatusDto.getName(), status.getName());
    }

    @Test
    public void deleteStatus() throws Exception {
        testUtils.registerDefaultUser();

        Status status = createStatus("test-status");

        ResultActions response = testUtils.makeRequestAuth(
                delete("/api/statuses/" + status.getId()),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());

        assertEquals(0, statusRepository.count());
    }

    public void createStatusWithIncorrectName() throws Exception {
        testUtils.registerDefaultUser();

        StatusDto statusDto = new StatusDto("");

        ResultActions response = testUtils.makeRequestAuth(
                post("/api/statuses/")
                        .content(asJson(statusDto))
                        .contentType(APPLICATION_JSON),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isUnprocessableEntity());
    }

    private Status createStatus(String name) {
        StatusDto defaultStatusDto = new StatusDto(name);
        return statusService.createNewStatus(defaultStatusDto);
    }
}
