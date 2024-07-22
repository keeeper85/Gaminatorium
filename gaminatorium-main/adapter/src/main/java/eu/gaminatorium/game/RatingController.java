package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameRatingDto;
import eu.gaminatorium.game.dto.NewGameRatingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Game Rating Controller", description = "Check game comments and score, add new ratings. " +
        "'Rating' object is a combination of it's string content, user-author, score (int), postingDate and the Game it belongs to.")
@RequestMapping("/v1/ratings")
@RequiredArgsConstructor
class RatingController {

    private final Facade facade;

    @GetMapping("/score/{gameid}")
    @Operation(description = "You will get a String with the average score of the chosen game, e.g. '4.5' or '6.8'.")
    ResponseEntity<String> getGameAverageScore(@PathVariable long gameid){
        return facade.getCurrentGameScore(gameid).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/random/{gameid}")
    @Operation(description = "Use this endpoint to get one random (generated for each request) rating.")
    ResponseEntity<GameRatingDto> getRandomRatingForThisGame(@PathVariable long gameid){
        return facade.getRandomRating(gameid).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{gameid}")
    @Operation(description = "Use this endpoint to get a pageable list containing all ratings given to the chosen game.")
    ResponseEntity<List<GameRatingDto>> getAllRatingsForThisGame(@PathVariable long gameid, Pageable pageable){
        return ResponseEntity.ok(facade.getAllRatingsPaged(gameid, pageable));
    }

    @PostMapping()
    @Operation(description = "Post method for creating new ratings. Content and score are mandatory, date, game and user are added" +
            "automatically. Ratings can not be edited or deleted (by regular users) so add a popup 'Are you sure?' while posting them.")
    ResponseEntity<NewGameRatingDto> addRatingForThisGame(@RequestBody @Valid NewGameRatingDto rating){
        Optional<NewGameRatingDto> ratingOptional = facade.addRating(rating);
        if(ratingOptional.isPresent()){
            return new ResponseEntity(ratingOptional.get(), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{ratingid}")
    @Operation(description = "Delete an existing rating by its gameid.")
    @Transactional
    ResponseEntity<Void> deleteRating(@PathVariable long ratingid){
        facade.deleteRating(ratingid);
        return ResponseEntity.ok().build();
    }


}
