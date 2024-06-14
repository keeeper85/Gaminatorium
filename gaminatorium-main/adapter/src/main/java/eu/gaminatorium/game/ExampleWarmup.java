package eu.gaminatorium.game;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Warmup to klasa inicjalizująca która wykonuje zadane polecenia podczas startu aplikacji.
 * Jest to dobre miejsce na umieszczenie testowych/mockowych rekordów w bazie.
 */

@Component
class ExampleWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final ExampleRepository exampleRepository;

    public ExampleWarmup(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        for (int i = 0; i < 10; i++) {
            var exampleGame = new ExampleGame();
            exampleGame.setTitle("Game #" + i);
            exampleRepository.save(exampleGame);
        }
    }
}
