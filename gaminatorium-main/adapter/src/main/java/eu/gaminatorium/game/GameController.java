package eu.gaminatorium.game;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Game Controller", description = "CRUD operations on Games")
@RequestMapping("/v1/game")
@AllArgsConstructor
public class GameController {

    private final GameRepository gameRepository;
    private final GameFacade gameFacade;

    int gamesTotalCount(){
        return 0;
    }






}
