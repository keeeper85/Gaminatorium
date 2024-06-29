package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ActiveGameDto;
import eu.gaminatorium.game.dto.GameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Active Game Controller", description = "Start a new game or join the existing one")
@RequestMapping("/v1/activegames")
@AllArgsConstructor
class ActiveGameController {

    private final Facade facade;

    @GetMapping()
    @Operation(description = "You will get pageable object with ALL pending games for ALL offered games.")
    ResponseEntity<List<ActiveGameDto>> getAllActiveGames(Pageable pageable){
        return ResponseEntity.notFound().build();
        //todo
    }

    @GetMapping("/{gameid}")
    @Operation(description = "You will get pageable object with ALL pending games only for THIS game.")
    ResponseEntity<List<ActiveGameDto>> getAllActiveGamesForThisGame(@PathVariable long gameid, Pageable pageable){
        return ResponseEntity.ok(facade.getAllActiveGamesForThisGame(gameid, pageable));
    }

    @GetMapping("/find/{title}")
    @Operation(description = "You will get pageable object with ALL pending games which title matches the given string.")
    ResponseEntity<List<ActiveGameDto>> findByTitle(Pageable pageable, String title){
        return ResponseEntity.ok(facade.getMatchingActiveGamesPaged(title, pageable));
    }

    @GetMapping("/start/{gameid}")
    @Operation(description = "Use this endpoint to launch a new instance of the chosen game.")
    ResponseEntity<Optional<ActiveGameDto>> startNewGame(@PathVariable long gameid){
        return ResponseEntity.ok(facade.startNewGame(gameid));
    }

    @GetMapping("/join/{activegameid}")
    @Operation(description = "Use this endpoint to join the instance of the chosen game.")
    ResponseEntity<ActiveGameDto> joinGame(@PathVariable long activegameid){
        return ResponseEntity.notFound().build();
        //todo
    }

}
