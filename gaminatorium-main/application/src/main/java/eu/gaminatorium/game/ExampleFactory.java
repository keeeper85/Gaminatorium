package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ExampleDto;

class ExampleFactory {

    ExampleGame from(ExampleDto source){
        var result = new ExampleGame();
        result.setId(source.getId());
        result.setTitle(source.getTitle());
        result.setPublishDate(source.getPublishDate());
        return result;
    }

}
