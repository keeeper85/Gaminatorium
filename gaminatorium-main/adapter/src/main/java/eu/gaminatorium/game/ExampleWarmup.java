package eu.gaminatorium.game;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class ExampleWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final ExampleRepository exampleRepository;
    private final ExampleConfiguration exampleConfiguration;

    public ExampleWarmup(ExampleRepository exampleRepository, ExampleConfiguration exampleConfiguration) {
        this.exampleRepository = exampleRepository;
        this.exampleConfiguration = exampleConfiguration;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        for (int i = 0; i < 10; i++) {
            var exampleGame = new ExampleGame();
            exampleGame.setTitle("Game #" + i);
            exampleRepository.save(exampleGame);
        }
        exampleConfiguration.print();
    }
}
