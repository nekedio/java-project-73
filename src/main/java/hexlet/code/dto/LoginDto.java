package hexlet.code.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String firstName;

    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Size(min = 3, max = 22)
    private String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
