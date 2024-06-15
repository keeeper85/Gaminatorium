package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;

public class GameFactory {

    static Game from(GameDto source){
        var game = new Game();

        return game;
    }

}
