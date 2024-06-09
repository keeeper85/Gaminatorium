package eu.gaminatorium.game;

import org.springframework.stereotype.Service;

@Service
public class ExampleFacade {

    ExampleService exampleService;

    public ExampleFacade(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    ExampleGame combinedMethod(ExampleGame game){
        exampleService.changeTitle(game);
        exampleService.changePublishDate(game);
        return game;
    }

}
