package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameRatingDto;
import eu.gaminatorium.game.dto.NewGameRatingDto;
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
@Tag(name = "Game Rating Controller", description = "Check game comments and score, add new ratings. " +
        "'Rating' object is a combination of it's string content, user-author, score (int), postingDate and the Game it belongs to.")
@RequestMapping("/v1/ratings")
@AllArgsConstructor
class RatingController {

    private final Facade facade;

    @GetMapping("/score/{gameid}")
    @Operation(description = "You will get a String with the average score of the chosen game, e.g. '4.5' or '6.8'.")
    ResponseEntity<String> getGameAverageScore(@PathVariable long gameid){
        return ResponseEntity.ok(facade.getCurrentGameScore(gameid));
    }

    @GetMapping("/random/{gameid}")
    @Operation(description = "Use this endpoint to get one random (for each request) rating.")
    ResponseEntity<Optional<GameRatingDto>> getRandomRatingForThisGame(@PathVariable long gameid){
        return ResponseEntity.ok(facade.getRandomRating(gameid));
    }

    @GetMapping("/{gameid}")
    @Operation(description = "Use this endpoint to a pageable object containing all ratings given to the chosen game.")
    ResponseEntity<List<GameRatingDto>> getAllRatingsForThisGame(@PathVariable long gameid, Pageable pageable){
        return ResponseEntity.ok(facade.getAllRatingsPaged(gameid, pageable));
    }

    @PostMapping()
    @Operation(description = "Post method for creating new ratings. Content and score are mandatory, date, game and user are added" +
            "automatically. Ratings can not be edited or deleted so add a popup 'Are you sure?' while posting them.")
    ResponseEntity<Optional<NewGameRatingDto>> addRatingForThisGame(@RequestBody NewGameRatingDto rating){
        return ResponseEntity.ok(facade.addRating(rating));
    }

    @DeleteMapping("/{ratingId}")
    @Operation(description = "Delete an existing rating by its id.")
    @Transactional
    ResponseEntity<Void> deleteRating(@PathVariable long ratingId){
        facade.deleteRating(ratingId);
        return ResponseEntity.ok().build();
    }


}
