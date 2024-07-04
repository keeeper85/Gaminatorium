package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.game.dto.NewGameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Game Controller", description = "CRUD operations on Games")
@RequestMapping("/v1/games")
@AllArgsConstructor
class GameController {

    private final Facade facade;

    @GetMapping("/count")
    @Operation(description = "Get the current number of all AVAILABLE games")
    ResponseEntity<Integer> gamesTotalCount(){
        return ResponseEntity.ok(facade.countAllAvailableGames());
    }

    @GetMapping()
    @Operation(description = "Get pageable list of all AVAILABLE games")
    ResponseEntity<List<GameDto>> getAll(Pageable pageable){
        return ResponseEntity.ok(facade.getAllAvailableGamesPaged(Game.ModerationStatus.ACCEPTED, pageable));
    }

    @GetMapping("/recent")
    @Operation(description = "Get pageable list of games which were played most recently, sorted date-descending.")
    ResponseEntity<List<GameDto>> getMostRecent(Pageable pageable){
        return ResponseEntity.ok(facade.getRecentlyPlayedGames(pageable));
    }

    @GetMapping("/pending")
    @Operation(description = "Get pageable list of all UNAVAILABLE (waiting for moderation) games")
    ResponseEntity<List<GameDto>> getPendingGames(Pageable pageable){
        return ResponseEntity.ok(facade.getAllAvailableGamesPaged(Game.ModerationStatus.PENDING, pageable));
    }

    @GetMapping("/{gameid}")
    @Operation(description = "Get a JSON game object using game id")
    ResponseEntity<GameDto> get(@PathVariable long gameid){
        return ResponseEntity.of(facade.getGameById(gameid));
    }

    @GetMapping("/tags/{gameid}")
    @Operation(description = "Get a list of tags for the game of the given id")
    ResponseEntity<String[]> getGameTags(@PathVariable long gameid){
        return ResponseEntity.ok(facade.getGameTags(gameid));
    }

    @GetMapping("/find/{title}")
    @Operation(description = "Find all AVAILABLE games (paged) which titles match the given String variable")
    ResponseEntity<List<GameDto>> findByTitle(Pageable pageable, @PathVariable String title){
        return ResponseEntity.ok(facade.findAvailableMatchingGamesPaged(title, pageable));
    }

    @PatchMapping("/toggle-status/{gameid}")
    @Operation(description = "Admin only. Switch the game status between 'pending' (invisible, in moderation) and 'accepted' (visible, ready to play).")
    ResponseEntity<Boolean> toggleStatus(@PathVariable long gameid){
        return ResponseEntity.ok(facade.toggleGameStatus(gameid));
    }

    @PostMapping()
    @Operation(description = "Add a new game with JSON object. Provide: title, description, " +
            "tags (space separated), url and source code url, max number of players which can play together",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Game created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "409", description = "Conflict - Game title already in use")
            })
    ResponseEntity<?> createGame(@RequestBody @Valid NewGameDto newGameDto){
        if (facade.isGameTitleUsed(newGameDto.getTitle())) {
            ErrorResponse errorResponse = new ErrorResponse(
                    "Game title already in use",
                    "The title '" + newGameDto.getTitle() + "' is already taken. Please choose a different title."
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        return ResponseEntity.of(Optional.of(facade.addNewGame(newGameDto)));
    }

    @PatchMapping("/{gameid}")
    @Operation(description = "Update an existing game with JSON object.")
    ResponseEntity<?> updateGame(@PathVariable long gameid, @RequestBody @Valid NewGameDto newGameDto){
        if (facade.isGameTitleUsed(newGameDto.getTitle())) {
            ErrorResponse errorResponse = new ErrorResponse(
                    "Game title already in use",
                    "The title '" + newGameDto.getTitle() + "' is already taken. Please choose a different title."
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        return ResponseEntity.of(Optional.of(facade.updateGame(gameid, newGameDto)));
    }

    @DeleteMapping("/{gameid}")
    @Operation(description = "Delete an existing game by its id.")
    @Transactional
    ResponseEntity<Void> deleteGame(@PathVariable long gameid){
        facade.deleteGame(gameid);
        return ResponseEntity.ok().build();
    }

}
