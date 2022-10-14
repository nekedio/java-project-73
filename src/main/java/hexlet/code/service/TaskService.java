package hexlet.code.service;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Status;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.StatusRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserService userService;

    public Task createdNewTask(TaskDto taskDto) {
        Task task = new Task();

        User executor = userRepository.findById(taskDto.getExecutorId()).get();

        String login = userService.getCurrentUserName();
        User author = userRepository.findByEmail(login).get();

        Status status = statusRepository.findById(taskDto.getTaskStatusId()).get();

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setExecutor(executor);
        task.setTaskStatus(status);
        task.setAuthor(author);

        return taskRepository.save(task);
    }

    public Task updateTask(Task task, TaskDto taskDto) {

        User executor = userRepository.findById(taskDto.getExecutorId()).get();
        Status status = statusRepository.findById(taskDto.getTaskStatusId()).get();

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setExecutor(executor);
        task.setTaskStatus(status);

        return task;
    }

    public void deleteTask(Task task) {
        String login = userService.getCurrentUserName();
        User author = userRepository.findByEmail(login).get();

        if (author.equals(task.getAuthor())) {
            taskRepository.delete(task);
        }
    }
}
