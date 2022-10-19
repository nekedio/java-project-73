package hexlet.code.controller;

import com.querydsl.core.types.Predicate;
import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.service.TaskService;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public Iterable<Task> getTasksByFilter(
            @QuerydslPredicate(root = Task.class) Predicate predicate
    ) {
        return taskRepository.findAll(predicate);
    }

    @GetMapping("{id}")
    public Task getTask(@PathVariable Long id) {
        return taskRepository.findById(id).get();
    }

    @PostMapping("")
    public Task createdTask(@RequestBody TaskDto taskDto) {

//        return "qwe";
        return taskService.createdNewTask(taskDto);
    }

    @PutMapping("{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        Task task = taskRepository.findById(id).get();

        return taskService.updateTask(task, taskDto);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id).get();

        taskService.deleteTask(task);
    }
}
