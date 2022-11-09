package hexlet.code.service;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.model.User;

public interface TaskService {

    Task createdNewTask(User author, TaskDto taskDto);

    Task updateTask(Long id, TaskDto taskDto);

    void deleteTask(Long id);
}
