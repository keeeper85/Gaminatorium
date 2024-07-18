package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ActiveGameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Active Game Controller", description = "Start a new game or join the existing one")
@RequestMapping("/v1/activegames")
@RequiredArgsConstructor
class ActiveGameController {

    private final Facade facade;

    @GetMapping()
    @Operation(description = "You will get pageable list with ALL pending games for ALL offered games.")
    ResponseEntity<List<ActiveGameDto>> getAllActiveGames(Pageable pageable){
        return ResponseEntity.ok(facade.getAllActiveGamesForAllGames(pageable));
    }

    @GetMapping("/{gameid}")
    @Operation(description = "You will get pageable object with ALL pending games only for THIS game.")
    ResponseEntity<List<ActiveGameDto>> getAllActiveGamesForThisGame(@PathVariable long gameid, Pageable pageable){
        return facade.getAllActiveGamesForThisGame(gameid, pageable).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/find/{title}")
    @Operation(description = "You will get pageable object with ALL pending games which title matches the given string.")
    ResponseEntity<List<ActiveGameDto>> findByTitle(Pageable pageable, String title){
        return ResponseEntity.ok(facade.getMatchingActiveGamesPaged(title, pageable));
    }

    @GetMapping("/start/{gameid}")
    @Operation(description = "Use this endpoint to launch a new instance of the chosen game.")
    ResponseEntity<ActiveGameDto> startNewGame(@PathVariable long gameid){
        return facade.startNewGame(gameid).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/join/{activegameid}")
    @Operation(description = "Use this endpoint to join the instance of the chosen game.")
    ResponseEntity<ActiveGameDto> joinGame(@PathVariable long activegameid){
        return facade.joinGame(activegameid).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/leave/{activegameid}")
    @Operation(description = "Use this endpoint to leave the instance of the chosen game.")
    ResponseEntity<ActiveGameDto> leaveGame(@PathVariable long activegameid){
        return facade.leaveGame(activegameid).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
