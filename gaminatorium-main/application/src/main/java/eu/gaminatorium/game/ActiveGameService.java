package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ActiveGameDto;
import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.game.dto.GameRatingDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class ActiveGameService {

    GameRepository gameRepository;

    public List<ActiveGameDto> getMatchingActiveGamesPaged(String title, Pageable pageable) {
        return null;
    }

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
