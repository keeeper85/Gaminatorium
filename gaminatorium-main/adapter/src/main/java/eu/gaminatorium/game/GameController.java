package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.RatingDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Game Controller", description = "CRUD operations on Games")
@RequestMapping("/v1/game")
@AllArgsConstructor
public class GameController {

    private final GameRepository gameRepository;
    private final GameFacade gameFacade;

    @GetMapping("/count")
    ResponseEntity<Integer> gamesTotalCount(){
        return ResponseEntity.ok(gameRepository.countAllBy());
    }

    @GetMapping("/all")
    ResponseEntity<List<Game>> getAllGames(){
        return ResponseEntity.ok(gameRepository.findAllBy());
    }

    @GetMapping("/{id}")
    ResponseEntity<Game> getGame(@PathVariable long id){
        return ResponseEntity.ok(gameRepository.findById(id));
    }

    @PostMapping("/new")
    ResponseEntity<Game> createGame(Game game){
        return ResponseEntity.ok(gameRepository.save(game));
    }

    @PatchMapping("/update/{id}")
    Game updateGame(@PathVariable int id){
        return null;
        //todo
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    ResponseEntity<Void> deleteGame(@PathVariable int id){
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/score")
    Double getGameScore(@PathVariable int id){
        return 0.0;
        //todo
    }

    @GetMapping("/{id}/ratings")
    ResponseEntity<List<RatingDto>> getAllRatings(@PathVariable int id){
        return ResponseEntity.notFound().build();
        //todo
    }

    @PostMapping("/{id}/ratings")
    ResponseEntity<RatingDto> addRating(@PathVariable int id, @RequestBody RatingDto ratingDto){
        return ResponseEntity.notFound().build();
        //todo
    }






}
