package eu.gaminatorium.user;

import eu.gaminatorium.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/list")
    @Operation(description = "Get list of all users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userFacade.findAll());
    }

    @GetMapping("/amount")
    @Operation(description = "Get number of all users")
    public ResponseEntity<Integer> getUsersAmount() {
        return ResponseEntity.ok(userFacade.findAll().size());
    }

    @GetMapping("/{id}")
    @Operation(description = "Get user by user id")
    ResponseEntity<Optional<UserDto>> getUser(@PathVariable long userId) {
        Optional<UserDto> user = userFacade.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add")
    @Operation(description = "Add new user providing user details")
    public ResponseEntity<UserDto> addUser(UserDto userDto) {
        return ResponseEntity.ok(userFacade.addUser(userDto));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete user by user id")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        userFacade.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{id}")
    @Operation(description = "Update user providing user details")
    ResponseEntity<Optional<UserDto>> updateUser(long userId, @RequestBody UserDto userDto) {
        Optional<UserDto> user = userFacade.updateUser(userId, userDto);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
