package eu.gaminatorium.user;

import eu.gaminatorium.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userFacade.findAll());
    }

    @GetMapping("/amount")
    public ResponseEntity<Integer> getUsersAmount() {
        return ResponseEntity.ok(userFacade.findAll().size());
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<UserDto>> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userFacade.getUserById(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(UserDto userDto) {
        return ResponseEntity.ok(userFacade.addUser(userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        userFacade.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{id}")
    ResponseEntity<Optional<UserDto>> updateUser(long userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userFacade.updateUser(userId, userDto));
    }
}
