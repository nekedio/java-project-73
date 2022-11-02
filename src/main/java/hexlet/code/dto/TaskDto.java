package hexlet.code.dto;

import java.util.List;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

    @NotBlank
    private String name;

    @Lob
    private String description;

    private Long taskStatusId;

    @Nullable
    private Long executorId;

    private List<Long> labelIds;

    public TaskDto(
            String name,
            String description,
            Long taskStatusId,
            Long executorId) {
        this.name = name;
        this.description = description;
        this.taskStatusId = taskStatusId;
        this.executorId = executorId;
    }
}
