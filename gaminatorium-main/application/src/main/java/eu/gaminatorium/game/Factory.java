package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ActiveGameDto;
import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.game.dto.GameRatingDto;

class Factory {

    static Game from(GameDto source){
        var game = new Game();
        game.setTitle(source.title());
        game.setDescription(source.description());
        game.setGameServiceLink(source.link());
        game.setMaxPlayers(source.maxPlayers());
        game.setReleaseDate(source.releaseDate());
        game.setRatings(source.ratings());
        game.setActiveGames(source.activeGames());

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
