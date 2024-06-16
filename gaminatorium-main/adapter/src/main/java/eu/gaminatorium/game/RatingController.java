package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameRatingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Game Rating Controller", description = "Check game comments and score, add new ratings. " +
        "'Rating' object is a combination of it's string content, user-author, score (int), postingDate and the Game it belongs to.")
@RequestMapping("/v1/ratings")
@AllArgsConstructor
class RatingController {

    private final Facade facade;

    @GetMapping("/score/{gameid}")
    @Operation(description = "You will get a String with the average score of the chosen game, e.g. '4.5' or '6.8'.")
    ResponseEntity<String> getGameAverageScore(@PathVariable long gameid){
        return ResponseEntity.ok().build();
        //todo
    }

    @GetMapping("/random/{gameid}")
    @Operation(description = "Use this endpoint to get one random (for each request) rating.")
    ResponseEntity<GameRatingDto> getRandomRatingForThisGame(@PathVariable long gameid){
        return ResponseEntity.ok().build();
        //todo
    }

    @GetMapping("/{gameid}")
    @Operation(description = "Use this endpoint to a pageable object containing all ratings given to the chosen game.")
    ResponseEntity<List<GameRatingDto>> getAllRatingsForThisGame(@PathVariable long gameid, Pageable pageable){
        return ResponseEntity.ok().build();
        //todo
    }

    @PostMapping("/{gameid}")
    @Operation(description = "Post method for creating new ratings. Content and score are mandatory, date, game and user are added" +
            "automatically. Ratings can not be edited or deleted so add a popup 'Are you sure?' while posting them.")
    ResponseEntity<GameRatingDto> addRatingForThisGame(@PathVariable long gameid, @RequestBody GameRatingDto rating){
        return ResponseEntity.ok().build();
        //todo
    }
}
