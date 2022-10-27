package hexlet.code.service;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Label;
import hexlet.code.model.Status;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.StatusRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Autowired
    private LabelRepository labelRepository;

    public Task createdNewTask(User author, TaskDto taskDto) {
        Task task = new Task();

        User executor = userRepository.findById(taskDto.getExecutorId()).get();

        Set<Label> labels = new HashSet<>();

        if (taskDto.getLabelIds() != null) {
            List<Label> newLabels = labelRepository.findAllById(taskDto.getLabelIds());
            labels.addAll(newLabels);
        }

        Status status = statusRepository.findById(taskDto.getTaskStatusId()).get();

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setExecutor(executor);
        task.setTaskStatus(status);
        task.setLabels(labels);
        task.setAuthor(author);

        return taskRepository.save(task);
    }

    public Task updateTask(Task task, TaskDto taskDto) {

        User executor = userRepository.findById(taskDto.getExecutorId()).get();
        Status status = statusRepository.findById(taskDto.getTaskStatusId()).get();

        Set<Label> labels = new HashSet<>();

        if (taskDto.getLabelIds() != null) {
            List<Label> newLabels = labelRepository.findAllById(taskDto.getLabelIds());
            labels.addAll(newLabels);
        }

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setExecutor(executor);
        task.setTaskStatus(status);
        task.setLabels(labels);

        return taskRepository.save(task);
    }

    public void deleteTask(Task task) {
        String login = userService.getCurrentUserName();
        User author = userRepository.findByEmail(login).get();

        if (author.equals(task.getAuthor())) {
            taskRepository.delete(task);
        }
    }
}
