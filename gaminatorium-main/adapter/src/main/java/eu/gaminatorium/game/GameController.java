package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Game Controller", description = "CRUD operations on Games")
@RequestMapping("/v1/game")
@AllArgsConstructor
class GameController {

    private final Facade facade;

    @GetMapping("/count")
    @Operation(description = "Get the current number of all AVAILABLE games")
    ResponseEntity<Integer> gamesTotalCount(){
        return null;
    }

    @GetMapping()
    @Operation(description = "Get pageable list of all games or one game - by its id")
    ResponseEntity<List<GameDto>> get(@RequestParam long gameid, Pageable pageable){
        return null;
    }

    @GetMapping("/find/{title}")
    @Operation(description = "Find all games (paged) which titles match the given String variable")
    ResponseEntity<List<GameDto>> findByTitle(Pageable pageable, @PathVariable String title){
        return null;
    }

    @GetMapping("/toggle-status/{gameid}")
    @Operation(description = "Admin only. Switch the game status between 'pending' (invisible, in moderation) and 'accepted' (visible, ready to play).")
    ResponseEntity<List<GameDto>> accept(@PathVariable long gameid){
        return null;
    }

    @PostMapping()
    @Operation(description = "Add a new game with JSON object.")
    ResponseEntity<Optional<GameDto>> createGame(GameDto gameDto){
        return null;
    }

    @PatchMapping("/{gameid}")
    @Operation(description = "Update an existing game with JSON object.")
    ResponseEntity<Optional<GameDto>> updateGame(@PathVariable long gameid){
        return null;
    }

    @DeleteMapping("/delete/{gameid}")
    @Operation(description = "Delete an existing game by its id.")
    @Transactional
    ResponseEntity<Void> deleteGame(@PathVariable long gameid){
        facade.deleteGame(gameid);
        return ResponseEntity.ok().build();
    }

}
