package hexlet.code.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDto {

    @Email
    @NotBlank
    private String email;

    @Size(min = 3, max = 22)
    private String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginDto() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDto{" + "email=" + email + ", password=" + password + '}';
    }
}
