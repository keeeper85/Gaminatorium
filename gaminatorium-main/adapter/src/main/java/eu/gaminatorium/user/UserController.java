package eu.gaminatorium.user;

import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.user.dto.NewUserDto;
import eu.gaminatorium.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "User Controller", description = "CRUD operations on users")
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

    @GetMapping("/{userid}")
    @Operation(description = "Get user by user gameid")
    ResponseEntity<Optional<UserDto>> getUser(@PathVariable long userid) {
        Optional<UserDto> user = userFacade.getUserById(userid);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add")
    @Operation(description = "Add new user providing user details")
    public ResponseEntity<NewUserDto> addUser(@Valid @RequestBody NewUserDto newUserDto) {
        return new ResponseEntity<>(userFacade.addUser(newUserDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userid}")
    @Operation(description = "Delete user by user gameid")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable long userid) {
        userFacade.deleteUserById(userid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/{userid}")
    @Operation(description = "Update user providing user details")
    ResponseEntity<Optional<NewUserDto>> updateUser(@PathVariable long userid, @Valid @RequestBody NewUserDto newUserDto) {
        Optional<NewUserDto> user = userFacade.updateUser(userid, newUserDto);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userid}/favorites") //todo: mapping usera: {userid} tymczasowy, tylko do czasu kiedy będzie możliwa jego autentykacja
    @Operation(description = "Get the list of the user's favorite games")
    ResponseEntity<List<GameDto>> getFavorites(@PathVariable long userid) {
        return ResponseEntity.ok(userFacade.getFavoriteGames(userid));
    }

    @GetMapping("/{userid}/last") //todo: mapping usera: {userid} tymczasowy, tylko do czasu kiedy będzie możliwa jego autentykacja
    @Operation(description = "Get the last game selected user has played")
    ResponseEntity<Optional<GameDto>> getLastGamePlayed(@PathVariable long userid) {
        return ResponseEntity.ok(userFacade.getLastGamePlayed(userid));
    }

    @PatchMapping("/{userid}/favorites/{gameid}") //todo: mapping usera: {userid} tymczasowy, tylko do czasu kiedy będzie możliwa jego autentykacja
    @Operation(description = "Toggle favorite game for the selected user")
    ResponseEntity<Void> toggleGameFavoriteStatus(@PathVariable long userid, @PathVariable long gameid) {
        boolean result = userFacade.toggleFavoriteStatus(userid, gameid);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
