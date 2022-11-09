package hexlet.code.service;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.model.User;

public interface TaskService {

    public Task createdNewTask(User author, TaskDto taskDto);

    public Task updateTask(Long id, TaskDto taskDto);

    public void deleteTask(Long id);
}
