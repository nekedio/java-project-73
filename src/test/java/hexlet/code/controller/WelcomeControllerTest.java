package hexlet.code.controller;

import hexlet.code.utils.TestUtils;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class WelcomeControllerTest {

    @Autowired
    private TestUtils testUtils;

    @Test
    public void getWelcome() throws Exception {
        ResultActions response = testUtils.makeRequestNotAuth(
                get("/welcome")
        );

        response.andExpect(status().isOk());
        response.andExpect(content().string(equalTo("Welcome to Spring")));

    }

    @Test
    public void getHome() throws Exception {

        ResultActions response = testUtils.makeRequestNotAuth(
                get("/")
        );

        response.andExpect(status().isOk());
    }
}
