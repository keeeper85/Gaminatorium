package eu.gaminatorium.game;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameFacade {

    private final GameRepository gameRepository;


}
