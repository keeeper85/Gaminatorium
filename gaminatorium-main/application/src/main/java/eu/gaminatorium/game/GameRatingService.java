package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameRatingDto;
import eu.gaminatorium.game.dto.NewGameRatingDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class GameRatingService {

    GameRepository gameRepository;

    public String getCurrentGameScore(long gameid) {
        if (gameRepository.existsById(gameid)) {
            return gameRepository.findById(gameid).getAverageRating();
        }
        return "No game with the given id.";
    }

    public Optional<GameRatingDto> getRandomRating(long gameid) {
        if (gameRepository.existsById(gameid)) {
            Game.Rating gameRating = gameRepository.findById(gameid).getRandomRating();
            return Optional.of(toDto(gameRating));
        }
        return Optional.empty();
    }

    public List<GameRatingDto> getAllRatingsPaged(long gameid, Pageable pageable) {
        if (gameRepository.existsById(gameid)) {
            return gameRepository.findAllRatingsByGameId(gameid, pageable).map(GameRatingService::toDto).toList();
        }
        return List.of();
    }

    public Optional<NewGameRatingDto> addRating(NewGameRatingDto rating) {
        if (gameRepository.existsById(rating.getGameid())) {
            var game = gameRepository.findById(rating.getGameid());
            game.addRating(rating.getScore(), rating.getComment());
            gameRepository.save(game);
            return Optional.of(rating);
            //todo???
        }
        return Optional.empty();
    }

    public boolean deleteRating(long ratingId) {
        if (gameRepository.existsGameRatingById(ratingId)){
            Game.Rating gameRating = gameRepository.findGameRatingById(ratingId);
            Game game = gameRating.getGame();
            if (game.deleteGameRating(gameRating)) {
                gameRepository.save(game);
                return true;
            }
        }
        return false;
    }

    private static GameRatingDto toDto(Game.Rating gameRating) {
        GameRatingDto gameRatingDto = GameRatingDto.builder()
                .ratingId(gameRating.getId())
                .score(gameRating.getScore())
                .comment(gameRating.getComment())
                .postDate(gameRating.getPostingDate())
                .gameid(gameRating.getGame().getId())
                .build();

        return gameRatingDto;
    }
}
