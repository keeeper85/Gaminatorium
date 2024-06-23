package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ActiveGameDto;
import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.game.dto.GameRatingDto;
import eu.gaminatorium.game.dto.NewGameDto;

import java.time.LocalDate;

class Factory {

    static Game from(NewGameDto source){
        var game = new Game();
        game.setModerationStatus(Game.ModerationStatus.PENDING);
        game.setTitle(source.getTitle());
        game.setDescription(source.getDescription());
        game.setGameTags(source.getTags());
        game.setGameServiceLink(source.getGamelink());
        game.setSourceCodeLink(source.getSourceCodelink());
        game.setMaxPlayers(source.getMaxPlayers());
        game.setReleaseDate(LocalDate.now());

        return game;
    }

    static Game.Rating from(GameRatingDto source){
        var rating = new Game.Rating();
        rating.setScore(source.score());
        rating.setComment(source.comment());
        rating.setPostingDate(source.postDate());
        rating.setGame(source.game());

        return rating;
    }

    static Game.Active from(ActiveGameDto source){
        var activeGame = new Game.Active();
        activeGame.setCurrentPlayers(source.getCurrentPlayers());
        activeGame.setStartedAt(activeGame.getStartedAt());
        activeGame.setGame(source.getGame());

        return activeGame;
    }

}
