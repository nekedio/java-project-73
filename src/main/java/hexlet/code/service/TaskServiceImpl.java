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
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    final private TaskRepository taskRepository;

    @Autowired
    final private UserRepository userRepository;

    @Autowired
    final private StatusRepository statusRepository;

    @Autowired
    final private UserService userService;

    @Autowired
    final private LabelRepository labelRepository;

    @Override
    public Task createdNewTask(User author, TaskDto taskDto) {

        Task task = fromDto(taskDto);

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id).get();

        merge(task, taskDto);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).get();

        String login = userService.getCurrentUserName();
        User author = userRepository.findByEmail(login).get();

        if (author.equals(task.getAuthor())) {
            taskRepository.delete(task);
        }
    }

    private void merge(Task task, TaskDto taskDto) {
        Task newTask = fromDto(taskDto);

        task.setName(newTask.getName());
        task.setDescription(newTask.getDescription());
        task.setExecutor(newTask.getExecutor());
        task.setTaskStatus(newTask.getTaskStatus());
        task.setLabels(newTask.getLabels());
    }

    private Task fromDto(TaskDto taskDto) {
        User author = userService.getCurrentUser();
        User executor = userRepository.findById(taskDto.getExecutorId()).get();
        Status status = statusRepository.findById(taskDto.getTaskStatusId()).get();

        Set<Label> labels = new HashSet<>();
        if (taskDto.getLabelIds() != null) {
            List<Label> newLabels = labelRepository.findAllById(taskDto.getLabelIds());
            labels.addAll(newLabels);
        }

        return Task.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .executor(executor)
                .taskStatus(status)
                .labels(labels)
                .author(author)
                .build();
    }
}
