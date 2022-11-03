package hexlet.code.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigForTest;
import static hexlet.code.config.SpringConfigForTest.TEST_PROFILE;
import hexlet.code.dto.StatusDto;
import hexlet.code.dto.TaskDto;
import hexlet.code.model.Status;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.service.StatusService;
import hexlet.code.service.TaskService;
import hexlet.code.utils.TestUtils;
import static hexlet.code.utils.TestUtils.asJson;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForTest.class)
public class TaskControllerTest {

    @Autowired
    private StatusService statusService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestUtils testUtils;

    @BeforeEach
    public void clearBefore() {
        testUtils.removeAll();
    }

    @Test
    public void createTask() throws Exception {
        User user = testUtils.registerDefaultUser();

        assertEquals(0, taskRepository.count());

        TaskDto taskDto = new TaskDto(
                "new-task-name",
                "new-task-description",
                createDefaultStatus().getId(),
                user.getId()
        );

        ResultActions response = testUtils.makeRequestAuth(
                post("/api/tasks")
                        .content(asJson(taskDto))
                        .contentType(APPLICATION_JSON),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isCreated());
        assertEquals(1, taskRepository.count());
    }

    @Test
    public void getTasks() throws Exception {
        User user = testUtils.registerDefaultUser();
        Status status = createDefaultStatus();

        Task expectedTask1 = createTask(user, status, "name-1", "description-1");
        Task expectedTask2 = createTask(user, status, "name-2", "description-2");

        ResultActions response = testUtils.makeRequestAuth(
                get("/api/tasks"),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());

        String json = response.andReturn().getResponse().getContentAsString();

        List<Task> tasks = TestUtils.fromJson(json, new TypeReference<>() {
        });

        assertEquals(tasks.size(), 2);

        Task task1 = taskRepository.findById(expectedTask1.getId()).get();
        assertEquals(expectedTask1.getName(), task1.getName());
        assertEquals(expectedTask1.getDescription(), task1.getDescription());

        Task task2 = taskRepository.findById(expectedTask2.getId()).get();
        assertEquals(expectedTask2.getName(), task2.getName());
        assertEquals(expectedTask2.getDescription(), task2.getDescription());
    }

    @Test
    public void getTask() throws Exception {
        User user = testUtils.registerDefaultUser();
        Status status = createDefaultStatus();
        Task expectedTask = createTask(user, status, "expected-name", "expected-description");

        ResultActions response = testUtils.makeRequestAuth(
                get("/api/tasks/" + expectedTask.getId()),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());

        String json = response.andReturn().getResponse().getContentAsString();

        Task task = TestUtils.fromJson(json, new TypeReference<>() {
        });

        assertEquals(expectedTask.getName(), task.getName());
        assertEquals(expectedTask.getDescription(), task.getDescription());
    }

    @Test
    public void updateTask() throws Exception {
        User user = testUtils.registerDefaultUser();
        Status status = createDefaultStatus();
        Task defaultTask = createTask(user, status, "default-name", "default-description");
        long countTaskBefore = taskRepository.count();

        TaskDto newTaskDto = new TaskDto(
                "new-task-name",
                "new-task-description",
                status.getId(),
                user.getId()
        );

        ResultActions response = testUtils.makeRequestAuth(
                put("/api/tasks/" + defaultTask.getId())
                        .content(asJson(newTaskDto))
                        .contentType(APPLICATION_JSON),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());
        assertEquals(countTaskBefore, taskRepository.count());

        Task newTask = taskRepository.findById(defaultTask.getId()).get();

        assertEquals(newTask.getName(), newTaskDto.getName());
        assertEquals(newTask.getDescription(), newTaskDto.getDescription());
    }

     public void deleteTask() throws Exception {
        User user = testUtils.registerDefaultUser();
        Status status = createDefaultStatus();
        Task expectedTask = createTask(user, status, "expected-name", "expected-description");

        assertEquals(1, taskRepository.count());

        ResultActions response = testUtils.makeRequestAuth(
                delete("/api/tasks/" + expectedTask.getId()),
                testUtils.getDefaultUserDto().getEmail()
        );

        response.andExpect(status().isOk());

        assertEquals(0, taskRepository.count());
    }

    private Status createDefaultStatus() {
        StatusDto statusDto = new StatusDto("gefault-status");
        return statusService.createNewStatus(statusDto);
    }

    private Task createTask(User user, Status status, String name, String description) {
        TaskDto taskDto = new TaskDto(
                name,
                description,
                status.getId(),
                user.getId()
        );
        return taskService.createdNewTask(user, taskDto);
    }
}
