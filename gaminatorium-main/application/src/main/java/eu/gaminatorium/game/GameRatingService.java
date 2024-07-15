package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameRatingDto;
import eu.gaminatorium.game.dto.NewGameRatingDto;
import eu.gaminatorium.user.TestUser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class GameRatingService {

    GameRepository gameRepository;

    public Optional<String> getCurrentGameScore(long gameid) {
        Optional<Game> gameOptional = gameRepository.findById(gameid);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            return Optional.of(game.getAverageRating());
        }
        return Optional.empty();
    }

    public Optional<GameRatingDto> getRandomRating(long gameid) {
        Optional<Game> gameOptional = gameRepository.findById(gameid);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            Game.Rating gameRating = game.getRandomRating();
            return Optional.of(toDto(gameRating));
        }
        return Optional.empty();
    }

    public List<GameRatingDto> getAllRatingsPaged(long gameid, Pageable pageable) {
        Optional<Game> gameOptional = gameRepository.findById(gameid);
        if (gameOptional.isPresent()) {
            return gameRepository.findAllRatingsByGameId(gameid, pageable).map(GameRatingService::toDto).toList();
        }
        return List.of();
    }

    public Optional<NewGameRatingDto> addRating(NewGameRatingDto rating) {
        Optional<Game> gameOptional = gameRepository.findById(rating.getGameid());
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            game.addRating(TestUser.TEST_USER, rating.getComment(), rating.getScore());
            gameRepository.save(game);
            return Optional.of(rating);
        }
        return Optional.empty();
    }

    public boolean deleteRating(long ratingid) {
        Optional<Game.Rating> ratingOptional = gameRepository.findGameRatingById(ratingid);
        if (ratingOptional.isPresent()){
            Game.Rating gameRating = ratingOptional.get();
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
                .ratingid(gameRating.getId())
                .score(gameRating.getScore())
                .comment(gameRating.getComment())
                .postDate(gameRating.getPostingDate())
                .gameid(gameRating.getGame().getId())
                .build();

        return gameRatingDto;
    }
}
