package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
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
    ResponseEntity<Integer> gamesTotalCount(){
        return ResponseEntity.ok(facade.countAllGames());
    }

    @GetMapping("/all")
    ResponseEntity<List<GameDto>> getAllGames(Pageable pageable){
        return ResponseEntity.ok(facade.getAllGamesPaged(pageable));
    }

    @GetMapping("/find/{title}")
    ResponseEntity<List<GameDto>> findByTitle(Pageable pageable, String title){
        return ResponseEntity.ok(facade.getMatchingGamesPaged(pageable));
    }


    @GetMapping("/{gameid}")
    ResponseEntity<Optional<GameDto>> getGame(@PathVariable long gameid){
        return ResponseEntity.ok(facade.getGameById(gameid));
    }

    @PostMapping("/add")
    ResponseEntity<Optional<GameDto>> createGame(GameDto gameDto){
        return ResponseEntity.ok(facade.addNewGame(gameDto));
    }

    @PatchMapping("/update/{gameid}")
    ResponseEntity<Optional<GameDto>> updateGame(@PathVariable long gameid){
        return ResponseEntity.ok(facade.updateGame(gameid));
    }

    @DeleteMapping("/delete/{gameid}")
    @Transactional
    ResponseEntity<Void> deleteGame(@PathVariable long gameid){
        facade.deleteGame(gameid);
        return ResponseEntity.ok().build();
    }

}
