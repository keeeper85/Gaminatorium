package eu.gaminatorium.user;

import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping
    @Operation(description = "Get list of all users")
    public ResponseEntity<List<UserDto>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userFacade.findAll(pageable));
    }

    @GetMapping("/count")
    @Operation(description = "Get number of all users")
    public ResponseEntity<Integer> countAllUsers() {
        return ResponseEntity.ok(userFacade.countAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(description = "Get user by user id")
    ResponseEntity<Optional<UserDto>> getUser(@PathVariable long id) {
        Optional<UserDto> user = userFacade.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add")
    @Operation(description = "Add new user providing user details")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userFacade.addUser(userDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete user by user id")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userFacade.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/{id}")
    @Operation(description = "Update user providing user details")
    ResponseEntity<Optional<UserDto>> updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        Optional<UserDto> user = userFacade.updateUser(id, userDto);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/favorites") //todo: mapping usera: {id} tymczasowy, tylko do czasu kiedy będzie możliwa jego autentykacja
    @Operation(description = "Get the list of the user's favorite games")
    ResponseEntity<List<GameDto>> getFavorites(@PathVariable long id) {
        //todo
        return ResponseEntity.ok(userFacade.getFavoriteGames(id));
    }

    @GetMapping("/{id}/last") //todo: mapping usera: {id} tymczasowy, tylko do czasu kiedy będzie możliwa jego autentykacja
    @Operation(description = "Get the last game selected user has played")
    ResponseEntity<Optional<GameDto>> getLastGamePlayed(@PathVariable long id) {
        //todo
        return ResponseEntity.ok(userFacade.getLastGamePlayed(id));
    }

    @PatchMapping("/{id}/favorites/{gameid}") //todo: mapping usera: {id} tymczasowy, tylko do czasu kiedy będzie możliwa jego autentykacja
    @Operation(description = "Toggle favorite game for the selected user")
    ResponseEntity<Void> toggleGameFavoriteStatus(@PathVariable long id, @PathVariable long gameid) {
        boolean result = userFacade.toggleFavoriteStatus(id, gameid);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
