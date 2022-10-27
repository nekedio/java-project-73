package hexlet.code.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Nullable
    @Lob
    private String description;

    @ManyToOne
    private Status taskStatus;

    @ManyToOne
    private User author;

    @Nullable
    @ManyToOne
    private User executor;

    @ManyToMany
    private Set<Label> labels;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getTaskStatus() {
        return taskStatus;
    }

    public User getAuthor() {
        return author;
    }

    public User getExecutor() {
        return executor;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
