package hexlet.code.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.UserDto;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.UserRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import hexlet.code.helper.JWTHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.StatusRepository;
import hexlet.code.service.UserService;

@Component
public class TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    private final UserDto defaultUserDto = new UserDto(
            "test-first-name",
            "test-last-name",
            "test@gmail.com",
            "test-password"
    );

    private final UserDto user1Dto = new UserDto(
            "test-first-name-1",
            "test-last-name-1",
            "test1@gmail.com",
            "test-password-1"
    );

    private final UserDto user2Dto = new UserDto(
            "test-first-name-2",
            "test-last-name-2",
            "test2@gmail.com",
            "test-password-2"
    );

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private JWTHelper jwtHelper;

    public void removeAll() {
        labelRepository.deleteAll();
        taskRepository.deleteAll();
        statusRepository.deleteAll();
        userRepository.deleteAll();
    }

    public UserDto getDefaultUserDto() {
        return defaultUserDto;
    }

    public UserDto getUser1Dto() {
        return user1Dto;
    }

    public UserDto getUser2Dto() {
        return user2Dto;
    }

    public User registerDefaultUser() throws Exception {

//        return registerUser(defaultUserDto);
        return userService.createNewUser(defaultUserDto);
    }

    public User registerUser(UserDto userDto) throws Exception {

//        final var request = post("/api/users")
//                .content(asJson(userDto))
//                .contentType(APPLICATION_JSON);
//
//        return mockMvc.perform(request);
        return userService.createNewUser(userDto);

    }

    public ResultActions makeRequestAuth(
            final MockHttpServletRequestBuilder request,
            final String byUser
    ) throws Exception {
        String token = jwtHelper.expiring(Map.of("username", byUser));
        request.header(AUTHORIZATION, token);

        return mockMvc.perform(request);
    }

    public ResultActions makeRequestNotAuth(MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request);
    }

    public static String asJson(final Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(String stringAsJcon, TypeReference<T> to) throws JsonProcessingException {
        return MAPPER.readValue(stringAsJcon, to);
    }

}
