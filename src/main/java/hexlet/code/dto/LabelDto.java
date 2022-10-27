package hexlet.code.dto;

import javax.validation.constraints.NotBlank;


public class LabelDto {

    @NotBlank
    private String name;

    public LabelDto() {
    }

    public LabelDto(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "LabelDto{" + "name=" + name + '}';
    }


}
