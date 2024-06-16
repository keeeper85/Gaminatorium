package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ActiveGameDto;
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

@RestController
@Tag(name = "Active Game Controller", description = "Start a new game or join the existing one")
@RequestMapping("/v1/activegames")
@AllArgsConstructor
class ActiveGameController {

    private final Facade facade;

    @GetMapping("/all")
    @Operation(description = "You will get pageable object with ALL pending games for ALL offered games.")
    ResponseEntity<List<ActiveGameDto>> getAllActiveGames(Pageable pageable){
        return ResponseEntity.notFound().build();
        //todo
    }

    @GetMapping("/allforthis/{gameid}")
    @Operation(description = "You will get pageable object with ALL pending games only for THIS game.")
    ResponseEntity<List<ActiveGameDto>> getAllActiveGamesForThisGame(@PathVariable long gameid, Pageable pageable){
        return ResponseEntity.notFound().build();
        //todo
    }

    @GetMapping("/start/{gameid}")
    @Operation(description = "Use this endpoint to launch a new instance of the chosen game.")
    ResponseEntity<ActiveGameDto> startNewGame(@PathVariable long gameid){
        return ResponseEntity.notFound().build();
        //todo
    }

    @GetMapping("/join/{activegameid}")
    @Operation(description = "Use this endpoint to join the instance of the chosen game.")
    ResponseEntity<ActiveGameDto> joinGame(@PathVariable long activegameid){
        return ResponseEntity.notFound().build();
        //todo
    }

}
