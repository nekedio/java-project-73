package hexlet.code.service;

import hexlet.code.dto.UserDto;
import hexlet.code.model.User;

public interface UserService {

    User createNewUser(UserDto userDto);

    User updateUser(User user, UserDto userDto);

    String getCurrentUserName();

    User getCurrentUser();

}
