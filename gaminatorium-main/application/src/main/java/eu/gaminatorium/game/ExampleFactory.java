package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ExampleDto;

/**
 * Wzorzec Fabryka może w prosty sposób konwertować obiekty Dto na encje
 */

class ExampleFactory {

    static ExampleGame from(ExampleDto source){
        var result = new ExampleGame();
        result.setId(source.id());
        result.setTitle(source.title());
        result.setPublishDate(source.publishDate());
        return result;
    }

}
