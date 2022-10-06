package hexlet.code.controller;

import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateUser() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
                .perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{"
                                        + "\"email\": \"ivan@google.com\",\n"
                                        + "\"firstName\": \"Ivan\",\n"
                                        + "\"lastName\": \"Petrov\",\n"
                                        + "\"password\": \"some-password\""
                                        + "}")
                )
                .andReturn()
                .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);
    }

    @Test
    public void testCreateUserWithIncorrectPassword() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
                .perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{"
                                        + "\"email\": \"ivan@google.com\",\n"
                                        + "\"firstName\": \"Ivan\",\n"
                                        + "\"lastName\": \"Petrov\",\n"
                                        + "\"password\": \"so\""
                                        + "}")
                )
                .andReturn()
                .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(422);
    }

    @Test
    public void testCreateUserWithEmptyPassword() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
                .perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{"
                                        + "\"email\": \"ivan@google.com\",\n"
                                        + "\"firstName\": \"Ivan\",\n"
                                        + "\"lastName\": \"Petrov\",\n"
                                        + "\"password\": \"\""
                                        + "}")
                )
                .andReturn()
                .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(422);
    }

    @Test
    public void testCreateUserWithEmptyEmail() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
                .perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{"
                                        + "\"email\": \"\",\n"
                                        + "\"firstName\": \"Ivan\",\n"
                                        + "\"lastName\": \"Petrov\",\n"
                                        + "\"password\": \"some-password\""
                                        + "}")
                )
                .andReturn()
                .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(422);
    }
}
