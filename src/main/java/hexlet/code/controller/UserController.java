package hexlet.code.controller;

import hexlet.code.dto.UserDto;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public User createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createNewUser(userDto);
    }

    @GetMapping("")
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable Long id) {
        return this.userRepository.findById(id).get();
    }

    @PatchMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDto newUserDto) {
        User oldUser = this.userRepository.findById(id).get();
        return this.userService.updateUser(oldUser, newUserDto);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        User user = this.userRepository.findById(id).get();
        this.userRepository.delete(user);
    }

}
