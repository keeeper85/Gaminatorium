package eu.gaminatorium.game;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class ExampleWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final ExampleRepository exampleRepository;

    ExampleWarmup(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        var exampleGame = new ExampleGame();
        exampleGame.setTitle("New title");
        exampleRepository.save(exampleGame);
    }
}
