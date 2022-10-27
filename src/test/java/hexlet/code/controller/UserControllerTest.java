package hexlet.code.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigForTest;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static hexlet.code.config.SpringConfigForTest.TEST_PROFILE;
import hexlet.code.dto.LoginDto;
import hexlet.code.dto.UserDto;
import hexlet.code.model.User;
import static hexlet.code.utils.TestUtils.asJson;
import java.util.List;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForTest.class)
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestUtils testUtils;

    @BeforeEach
    public void clearBefore() {
        testUtils.removeAll();
    }

    @Test
    public void registration() throws Exception {
        assertEquals(0, userRepository.count());

        ResultActions response = testUtils.makeRequestNotAuth(
                post("/api/users")
                        .content(asJson(testUtils.getDefaultUserDto()))
                        .contentType(APPLICATION_JSON)
        );

        response.andExpect(status().isCreated());
        assertEquals(1, userRepository.count());
    }

    @Test
    public void login() throws Exception {
        testUtils.registerDefaultUser();

        UserDto userDto = testUtils.getDefaultUserDto();

        final LoginDto loginDto = new LoginDto(
                userDto.getEmail(),
                userDto.getPassword()
        );

        ResultActions response = testUtils.makeRequestNotAuth(
                post("/api/login")
                        .content(asJson(loginDto))
                        .contentType(APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }

    @Test
    public void getUsersTest() throws Exception {
        User expectedUser1 = testUtils.registerUser(testUtils.getUser1Dto());
        User expectedUser2 = testUtils.registerUser(testUtils.getUser2Dto());

        ResultActions response = testUtils.makeRequestNotAuth(
                get("/api/users")
        );

        response.andExpect(status().isOk());
        String usersAsString = response.andReturn().getResponse().getContentAsString();

        List<User> users = TestUtils.fromJson(usersAsString, new TypeReference<>() {
        });

        assertEquals(users.size(), 2);

        User user1 = userRepository.findByEmail(testUtils.getUser1Dto().getEmail()).get();

        assertEquals(user1.getId(), expectedUser1.getId());
        assertEquals(user1.getEmail(), expectedUser1.getEmail());
        assertEquals(user1.getLastName(), expectedUser1.getLastName());
        assertEquals(user1.getFirstName(), expectedUser1.getFirstName());

        User user2 = userRepository.findByEmail(testUtils.getUser2Dto().getEmail()).get();

        assertEquals(user2.getId(), expectedUser2.getId());
        assertEquals(user2.getEmail(), expectedUser2.getEmail());
        assertEquals(user2.getLastName(), expectedUser2.getLastName());
        assertEquals(user2.getFirstName(), expectedUser2.getFirstName());
    }

    @Test
    public void getUserById() throws Exception {
        User expectedUser = testUtils.registerDefaultUser();

        ResultActions response = testUtils.makeRequestAuth(
                get("/api/users/" + expectedUser.getId()),
                expectedUser.getEmail()
        );

        response.andExpect(status().isOk());

        String userAsString = response.andReturn().getResponse().getContentAsString();
        User user = TestUtils.fromJson(userAsString, new TypeReference<>() {
        });

        assertEquals(expectedUser.getId(), user.getId());
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getLastName(), user.getLastName());
        assertEquals(expectedUser.getFirstName(), user.getFirstName());
    }

    @Disabled
    @Test
    public void getUserByIdNotAuth() throws Exception {
        User expectedUser = testUtils.registerDefaultUser();

        var response = testUtils.makeRequestNotAuth(
                get("/api/users/" + expectedUser.getId())
        );

        response.andExpect(status().isUnauthorized());
    }

    @Test
    public void updateUserTest() throws Exception {
        User defaultUser = testUtils.registerDefaultUser();
        long countUserBefore = userRepository.count();

        UserDto updateUserDto = testUtils.getUser1Dto();

        ResultActions response = testUtils.makeRequestAuth(
                patch("/api/users/" + defaultUser.getId())
                        .content(asJson(updateUserDto))
                        .contentType(APPLICATION_JSON),
                defaultUser.getEmail()
        );

        response.andExpect(status().isOk());
        assertEquals(countUserBefore, userRepository.count());

        User updateUser = userRepository.findByEmail(updateUserDto.getEmail()).get();

        assertEquals(defaultUser.getId(), updateUser.getId());
        assertEquals(updateUserDto.getEmail(), updateUser.getEmail());
        assertEquals(updateUserDto.getLastName(), updateUser.getLastName());
        assertEquals(updateUserDto.getFirstName(), updateUser.getFirstName());
    }

    @Test
    public void updateUserTestNegativ() throws Exception {
        User defaultUser = testUtils.registerDefaultUser();
        User anotherUser = testUtils.registerUser(testUtils.getUser2Dto());
        long countUserBefore = userRepository.count();

        UserDto updateUserDto = testUtils.getUser1Dto();

        ResultActions response = testUtils.makeRequestAuth(
                patch("/api/users/" + anotherUser.getId())
                        .content(asJson(updateUserDto))
                        .contentType(APPLICATION_JSON),
                defaultUser.getEmail()
        );

        response.andExpect(status().isForbidden());
        assertEquals(countUserBefore, userRepository.count());
    }

    @Test
    public void deleteUserTest() throws Exception {
        long countUserBefore = userRepository.count();
        User defaultUser = testUtils.registerDefaultUser();

        ResultActions response = testUtils.makeRequestAuth(
                delete("/api/users/" + defaultUser.getId()),
                defaultUser.getEmail()
        );

        response.andExpect(status().isOk());
        assertEquals(countUserBefore, userRepository.count());
    }

    @Test
    public void deleteUserTestNegativ() throws Exception {
        User defaultUser = testUtils.registerDefaultUser();
        User anotherUser = testUtils.registerUser(testUtils.getUser2Dto());
        long countUserBefore = userRepository.count();

        ResultActions response = testUtils.makeRequestAuth(
                delete("/api/users/" + anotherUser.getId()),
                defaultUser.getEmail()
        );

        response.andExpect(status().isForbidden());
        assertEquals(countUserBefore, userRepository.count());
    }

    @Test
    public void createUserWithIncorrectPassword() throws Exception {

        UserDto userDto = new UserDto("Ivan", "Petrov", "ivan@google.com", "qw");

        ResultActions response = testUtils.makeRequestAuth(
                post("/api/users/")
                        .content(asJson(userDto))
                        .contentType(APPLICATION_JSON),
                userDto.getPassword()
        );

        response.andExpect(status().isUnprocessableEntity());
    }

    public void createUserWithIncorrectFirstName() throws Exception {

        UserDto userDto = new UserDto("Iv", "Petrov", "ivan@google.com", "qweqwe");

        ResultActions response = testUtils.makeRequestAuth(
                post("/api/users/")
                        .content(asJson(userDto))
                        .contentType(APPLICATION_JSON),
                userDto.getPassword()
        );

        response.andExpect(status().isUnprocessableEntity());
    }

    public void createUserWithIncorrectLastName() throws Exception {

        UserDto userDto = new UserDto("Ivan", "Pe", "ivan@google.com", "qweqwe");

        ResultActions response = testUtils.makeRequestAuth(
                post("/api/users/")
                        .content(asJson(userDto))
                        .contentType(APPLICATION_JSON),
                userDto.getPassword()
        );

        response.andExpect(status().isUnprocessableEntity());
    }
}
