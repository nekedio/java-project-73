package hexlet.code.service.impl;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Label;
import hexlet.code.model.Status;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.StatusRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.TaskService;
import hexlet.code.service.UserService;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final StatusRepository statusRepository;

    private final UserService userService;

    private final LabelRepository labelRepository;

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

        final User executor = Optional.ofNullable(taskDto.getExecutorId())
                .map(User::new)
                .orElse(null);

        final Status status = Optional.ofNullable(taskDto.getTaskStatusId())
                .map(Status::new)
                .orElse(null);

        final Set<Label> labels = Optional.ofNullable(taskDto.getLabelIds())
                .orElse(Set.of())
                .stream()
                .filter(Objects::nonNull)
                .map(Label::new)
                .collect(Collectors.toSet());

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
