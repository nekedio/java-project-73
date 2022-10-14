package hexlet.code.controller;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;
import hexlet.code.service.TaskService;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("{id}")
    public Task getTask(@PathVariable Long id) {
        return taskRepository.findById(id).get();
    }

    @PostMapping("")
    public Task createdTask(@RequestBody TaskDto taskDto) {
        return taskService.createdNewTask(taskDto);
    }

    @PutMapping("{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto){
        Task task = taskRepository.findById(id).get();

        return taskService.updateTask(task, taskDto);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id).get();

        taskService.deleteTask(task);
    }
}
