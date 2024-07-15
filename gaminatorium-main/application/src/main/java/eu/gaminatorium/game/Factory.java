package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.NewGameDto;

import java.time.LocalDate;

class Factory {

    static Game from(NewGameDto source){
        var game = new Game();
        game.setModerationStatus(Game.ModerationStatus.PENDING);
        game.setTitle(source.getTitle());
        game.setDescription(source.getDescription());
        game.setGameTags(source.getTags());
        game.setGameServiceUrl(source.getGameUrl());
        game.setSourceCodeUrl(source.getSourceCodeUrl());
        game.setMaxPlayers(source.getMaxPlayers());
        game.setReleaseDate(LocalDate.now());

        return game;
    }

}
