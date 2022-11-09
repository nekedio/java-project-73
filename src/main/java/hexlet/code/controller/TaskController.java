package hexlet.code.controller;

import com.querydsl.core.types.Predicate;
import com.rollbar.notifier.Rollbar;
import hexlet.code.config.RollbarConfig;
import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.TaskService;
import hexlet.code.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tasks")
@SecurityRequirement(name = "javainuseapi")
@AllArgsConstructor
public class TaskController {

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final TaskService taskService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserService userService;

    @Operation(summary = "Get list of all tasks")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "List of all tasks"
        )
    })
    @GetMapping("")
    public Iterable<Task> getTasksByFilter(
            @Parameter(description = "Get parameters for filtering")
            @QuerydslPredicate(root = Task.class) Predicate predicate) {

        Rollbar rollbar = new RollbarConfig().rollbar();
        rollbar.debug("@@@Here is some debug message @@@");
        return taskRepository.findAll(predicate);
    }

    @Operation(summary = "Get specific task by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Task found"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Task not found"
        )
    })
    @GetMapping("{id}")
    public Task getTask(
            @Parameter(description = "Id of task to be found")
            @PathVariable Long id
    ) {
        return taskRepository.findById(id).get();
    }

    @Operation(summary = "Create new task")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "List of all tasks"
        )
    })
    @PostMapping("")
    @ResponseStatus(CREATED)
    public Task createdTask(
            @Parameter(description = "Task data to save")
            @RequestBody @Valid TaskDto taskDto
    ) {
        String login = userService.getCurrentUserName();
        User author = userRepository.findByEmail(login).get();

        return taskService.createdNewTask(author, taskDto);
    }

    @Operation(summary = "Update specific task by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Updated task"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Task not found"
        )
    })
    @PutMapping("{id}")
    public Task updateTask(
            @Parameter(description = "Id of task to be updated")
            @PathVariable Long id,
            @Parameter(description = "Task data to update")
            @RequestBody @Valid TaskDto taskDto) {
        return taskService.updateTask(id, taskDto);
    }

    @Operation(summary = "Delete specific task by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Deleted task"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Task not found"
        )
    })
    @DeleteMapping("{id}")
    public void deleteTask(
            @Parameter(description = "Id of task to be deleted")
            @PathVariable Long id
    ) {
        taskService.deleteTask(id);
    }
}
