package hexlet.code.controller;

import hexlet.code.dto.UserDto;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    private static final String ONLY_OWNER_BY_ID = """
            @userRepository.findById(#id).get().getEmail() == authentication.getName()
        """;

    @Operation(summary = "Create new user")
    @ApiResponse(
            responseCode = "201",
            description = "User created"
    )
    @PostMapping("")
    @ResponseStatus(CREATED)
    public User createUser(
            @Parameter(description = "User data to save")
            @RequestBody @Valid UserDto userDto
    ) {
        return userService.createNewUser(userDto);
    }

    @Operation(summary = "Get list of all users")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "List of all users"
        )
    })
    @GetMapping("")
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Operation(summary = "Get specific user by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "User found"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "User not found"
        )
    })
    @SecurityRequirement(name = "javainuseapi")
    @GetMapping("{id}")
    public User getUser(
            @Parameter(description = "Id of user to be found")
            @PathVariable Long id
    ) {
        return this.userRepository.findById(id).get();
    }

    @Operation(summary = "Update specific user by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Updated user"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "User not found"
        )
    })
    @SecurityRequirement(name = "javainuseapi")
    @PutMapping("{id}")
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public User updateUser(
            @Parameter(description = "Id of user to be updated")
            @PathVariable Long id,
            @Parameter(description = "User data to update")
            @RequestBody @Valid UserDto userDto) {
        return this.userService.updateUser(id, userDto);
    }

    @Operation(summary = "Delete specific user by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Deleted user"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "User not found"
        )
    })
    @SecurityRequirement(name = "javainuseapi")
    @DeleteMapping("{id}")
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public void deleteUser(
            @Parameter(description = "Id of user to be deleted")
            @PathVariable Long id) {
        this.userRepository.deleteById(id);
    }

}
