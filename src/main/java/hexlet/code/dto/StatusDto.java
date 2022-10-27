package hexlet.code.dto;

import javax.validation.constraints.NotBlank;

public class StatusDto {

    @NotBlank
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public StatusDto() {
    }

    public StatusDto(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StatusDto{" + "name=" + name + '}';
    }
}
