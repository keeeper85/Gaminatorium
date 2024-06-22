package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameRatingDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class GameRatingService {

    GameRepository gameRepository;

    private static GameRatingDto toDto(Game.Rating gameRating) {
        GameRatingDto gameRatingDto = GameRatingDto.builder()
                .score(gameRating.getScore())
                .comment(gameRating.getComment())
                .postDate(gameRating.getPostingDate())
                .game(gameRating.getGame())
                .build();

        return gameRatingDto;
    }
}
