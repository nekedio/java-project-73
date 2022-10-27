package hexlet.code.dto;

import java.util.List;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

public class TaskDto {

    @NotBlank
    private String name;

    @Lob
    private String description;

    private Long taskStatusId;

    @Nullable
    private Long executorId;

    private List<Long> labelIds;

    public TaskDto() {
    }

    public TaskDto(
            String name,
            String description,
            Long taskStatusId,
            Long executorId) {
//            , List<Long> labelIds) {
        this.name = name;
        this.description = description;
        this.taskStatusId = taskStatusId;
        this.executorId = executorId;
//        this.labelIds = labelIds;
    }

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

    public List<Long> getLabelIds() {
        return labelIds;
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

    public void setLabelIds(List<Long> labelIds) {
        this.labelIds = labelIds;
    }

    @Override
    public String toString() {
        return "TaskDto{" + "name=" + name + ", description=" + description
                + ", taskStatusId=" + taskStatusId + ", executorId=" + executorId + ", labelIds=" + labelIds + '}';
    }

}
