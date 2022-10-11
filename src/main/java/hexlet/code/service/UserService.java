package hexlet.code.service;

import hexlet.code.dto.UserDto;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createNewUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User userToUpdate, UserDto newUserDto) {
        userToUpdate.setEmail(newUserDto.getEmail());
        userToUpdate.setFirstName(newUserDto.getFirstName());
        userToUpdate.setLastName(newUserDto.getLastName());
        userToUpdate.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        return userRepository.save(userToUpdate);

    }

}
