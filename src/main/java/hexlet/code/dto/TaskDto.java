package hexlet.code.dto;

import hexlet.code.model.Status;
import hexlet.code.model.User;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

public class TaskDto {
    @NotBlank
    private String name;

//    @Nullable
    @Lob
    private String description;

//    @ManyToOne
    private Long taskStatusId;

    @Nullable
//    @ManyToOne
    private Long executorId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getTaskStatusId() {
        return taskStatusId;
    }

    public Long getExecutorId() {
        return executorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskStatusId(Long taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    @Override
    public String toString() {
        return "TaskDto{" + "name=" + name + ", description=" + description + ", taskStatusId=" + taskStatusId + ", executorId=" + executorId + '}';
    }






}
