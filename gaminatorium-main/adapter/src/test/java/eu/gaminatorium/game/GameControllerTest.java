package eu.gaminatorium.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.game.dto.NewGameDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@WebMvcTest(GameController.class)
class GameControllerTest {

    private final String BASE_URL = "/v1/game";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Facade facade;

    @Nested
    class getMethodTestes {

        @Test
        void getGamesTotalCountWhenGameListIsNotEmpty() throws Exception {
            //given

            //when
            Mockito.when(facade.countAllAvailableGames()).thenReturn(5);

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game/count"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().string("5"));
        }

        @Test
        void getGamesTotalCountWhenGameListIsEmpty() throws Exception {
            //given
            int gameListSize = 0;

            //when
            Mockito.when(facade.countAllAvailableGames()).thenReturn(gameListSize);

            //given
            mvc.perform(MockMvcRequestBuilders.get("/v1/game/count"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().string("" + gameListSize));
        }

        @Test
        void getAllAvailableGamesWhenAcceptedGamesExist () throws Exception {
            //given
            var gameStatus = Game.ModerationStatus.ACCEPTED;
            var gameDto = GameDto.builder()
                    .title("foo")
                    .build();

            List<GameDto> gameDtoList = List.of(gameDto);

            //when
            Mockito.when(facade.getAllAvailableGamesPaged(gameStatus, PageRequest.of(0, 5))).thenReturn(gameDtoList);

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game")
                    .param("page", "0")
                    .param("size", "5"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", is("foo")));
        }

        @Test
        void getAllAvailableGamesWhenGameListIsEmpty () throws Exception {
            //given
            List<GameDto> gameDtoList = new ArrayList<>();
            var gameStatus = Game.ModerationStatus.ACCEPTED;

            //when
            Mockito.when(facade.getAllAvailableGamesPaged(gameStatus, PageRequest.of(0, 5))).thenReturn(gameDtoList);

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game")
                    .param("page", "0")
                    .param("size", "5"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
        }

        @Test
        void getPendingGamesWhenPendingGamesExist () throws Exception {
            //given
            var gameStatus = Game.ModerationStatus.PENDING;
            var gameDto = GameDto.builder()
                    .title("foo")
                    .build();

            List<GameDto> gameDtoList = List.of(gameDto);

            //when
            Mockito.when(facade.getAllAvailableGamesPaged(gameStatus, PageRequest.of(0, 5))).thenReturn(gameDtoList);

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game/pending")
                    .param("page", "0")
                    .param("size", "5"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", is("foo")));
        }

        @Test
        void getPendingGamesWhenGameListIsEmpty () throws Exception {
            //given
            var gameStatus = Game.ModerationStatus.PENDING;
            List<GameDto> gameDtoList = new ArrayList<>();

            //when
            Mockito.when(facade.getAllAvailableGamesPaged(gameStatus, PageRequest.of(0, 5))).thenReturn(gameDtoList);

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game/pending")
                    .param("page", "0")
                    .param("size", "5"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
        }

        @Test
        void getGameByIdWhenGameExist() throws Exception {
            //given
            var gameId = 1;
            var gameDto = GameDto.builder()
                    .title("foo")
                    .build();

            Optional<GameDto> optionalGameDto = Optional.of(gameDto);

            //when
            Mockito.when(facade.getGameById(1)).thenReturn(optionalGameDto);

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game/" + gameId))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("foo")));
        }

        @Test
        void getGameByIdWhenGameNotExist() throws Exception {
            //given
            var gameId = 1;

            //when
            Mockito.when(facade.getGameById(gameId)).thenReturn(Optional.empty());

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game/" + gameId))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void getGameTagsWhenGameExistAndGameContainsTags() throws Exception {   //TODO napisać WhenGameNotExist
            //given
            var gameId = 1;
            var tags = new String[]{"foo", "bar"};

            //when
            Mockito.when(facade.getGameTags(gameId)).thenReturn(tags);

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game/tags/" + gameId))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content()
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0]", is("foo")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1]", is("bar")));
        }

        @Test
        void getGameTagsWhenGameExistAndGameNotContainsTags() throws Exception {
            //given
            var gameId = 1;
            var tags = new String[]{};

            //when
            Mockito.when(facade.getGameTags(gameId)).thenReturn(tags);

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game/tags/" + gameId))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
        }

        @Test
        void getGameTagsWhenGameNotExist() throws Exception {   // TODO metoda getGameTags powinna przewidzieć że gra o podanym id nie istnieje ponieważ
            //given
            var gameId = 1;

            //when
            Mockito.when(facade.getGameTags(gameId)).thenThrow(new NoSuchElementException("Game not found"));

            //then
            /*Exception resolvedException = */mvc.perform(MockMvcRequestBuilders.get("/v1/game/tags/" + gameId))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
//                    .andReturn()
//                    .getResolvedException();

//            assertThrows(NoSuchElementException.class, () -> {throw resolvedException; });
//
//            ArgumentCaptor<NoSuchElementException> captor = ArgumentCaptor.forClass(NoSuchElementException.class);
//            assertEquals("Game not found", captor.capture().getMessage());


        }

        @Test
        void getGameByTitle() throws Exception {
            //TODO czy dopuszczamy możliwośc istnienia wielu gier o tym samym tytule
            // jeśli nie to czy metoda nie powinna zwracać gameDto zamiast List<GameDto>

            //given
            var gameTitle = "foo";
            var gameDto = GameDto.builder()
                    .title(gameTitle)
                    .build();
            Sort sort = Sort.by(Sort.Order.asc("title"));   //

            //when
            Mockito.when(facade.findAvailableMatchingGamesPaged(gameTitle, PageRequest.of(0, 5, sort))).thenReturn(List.of(gameDto));

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game/find/" + gameTitle)
                            .param("page", "0")
                            .param("size", "5")
                            .param("sort", "title,asc")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", is("foo")));
        }
    }

    @Nested
    class postMethodTestes {  //TODO dodać testy kiedy podane pola nie spełniają wymagań

        // TODO dodano adnotacje @Builder w klasie NewGameDto, zapytać o działanie swaggera (dlaczego w kontrolerze endpoint postMapping jest /v1/game a w swagger ud widnieje jako /v1/game/add
        @Test
        void createGameIfGameNotExist() throws Exception {
            //given
            var newGame = NewGameDto.builder()
                    .title("foo")
                    .description("Lorem ipsum dolor sit amet")
                    .tags("bar")
                    .gamelink("https://newgame.gamelink.com")
                    .sourceCodelink("https://newgame.gamelink.com")
                    .maxPlayers(5)
                    .build();

            String newGameJson = objectMapper.writeValueAsString(newGame);

            //when
            Mockito.when(facade.addNewGame(newGame)).thenReturn(Optional.of(newGame));

            //then
            mvc.perform(MockMvcRequestBuilders.post("/v1/game")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newGameJson))
                    .andExpect(MockMvcResultMatchers.status().isOk())   // TODO metoda może zwracać status 201 created
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("foo"))) //TODO propozycja zmiany aby responseEntity zwracał obiekt GameDto zamiast Optionala
//                    .andExpect(MockMvcResultMatchers.header().exists("Location")) //TODO metod post powinna zwracać adres nowo utworzonego zasobu
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        void createGameIfGameExist() throws Exception {
            //given
            var newGame = NewGameDto.builder()
                    .title("foo")
                    .description("Lorem ipsum dolor sit amet")
                    .tags("bar")
                    .gamelink("https://newgame.gamelink.com")
                    .sourceCodelink("https://newgame.gamelink.com")
                    .maxPlayers(5)
                    .build();

            String newGameJson = objectMapper.writeValueAsString(newGame);

            //when
            Mockito.when(facade.isGameTitleUsed(newGame.getTitle())).thenReturn(true);

            //then
            mvc.perform(MockMvcRequestBuilders.post("/v1/game")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newGameJson))
                    .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CONFLICT.value()))
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    //TODO sprawdzić jak lepiej sparsować message przekazany w response body
                    .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Game title already in use\",\"details\":\"The title 'foo' is already taken. Please choose a different title.\"}"));
        }
    }

    @Nested
    class patchMethodTestes {

        @Test
        void updateToggleStatus() throws Exception {
            //given
            var gameId = 1;

            //when
            Mockito.when(facade.toggleGameStatus(gameId)).thenReturn(true);

            //then
            mvc.perform(MockMvcRequestBuilders.patch("/v1/game/toggle-status/" + gameId).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().string("true"));

            Mockito.verify(facade, Mockito.times(1)).toggleGameStatus(gameId);
        }

        @Test
        void updateGameWhenTitleIsExist() throws Exception {
            //given
            var gameId = 1;
            var newGameDto = NewGameDto.builder()
                    .title("foo")
                    .description("Lorem ipsum dolor sit amet")
                    .tags("bar")
                    .gamelink("https://newgame.gamelink.com")
                    .sourceCodelink("https://newgame.gamelink.com")
                    .maxPlayers(5)
                    .build();

            String newGameDtoJson = objectMapper.writeValueAsString(newGameDto);

            //when
            Mockito.when(facade.isGameTitleUsed(newGameDto.getTitle())).thenReturn(true);

            //then
            mvc.perform(MockMvcRequestBuilders.patch("/v1/game/" + gameId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newGameDtoJson))
                    .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CONFLICT.value()))
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    //TODO sprawdzić jak lepiej sparsować message przekazany w response body
                    .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Game title already in use\",\"details\":\"The title 'foo' is already taken. Please choose a different title.\"}"));
        }

        @Test
        void updateGameWhenTitleIsNotExist() throws Exception {
            //given
            var gameId = 1;
            var newGameDto = NewGameDto.builder()
                    .title("foo")
                    .description("Lorem ipsum dolor sit amet")
                    .tags("bar")
                    .gamelink("https://newgame.gamelink.com")
                    .sourceCodelink("https://newgame.gamelink.com")
                    .maxPlayers(5)
                    .build();

            var gameDto = GameDto.builder()
                    .title(newGameDto.getTitle())
                    .description(newGameDto.getDescription())
                    .tags(newGameDto.getTags())
                    .build();

            Optional<GameDto> optionalGameDto = Optional.of(gameDto);
            //TODO dopytać o NewGameDto

            String newGameDtoJson = objectMapper.writeValueAsString(newGameDto);

            //when
            Mockito.when(facade.isGameTitleUsed(newGameDto.getTitle())).thenReturn(false);
            Mockito.when(facade.updateGame(gameId, newGameDto)).thenReturn(optionalGameDto);

            //then
            mvc.perform(MockMvcRequestBuilders.patch("/v1/game/" + gameId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newGameDtoJson))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }
    }

    @Nested
    class deleteMethodTestes {

        @Test
        void deleteGameById() throws Exception {
            //given
            var gameId = 1;

            //when

            //then
            mvc.perform(MockMvcRequestBuilders.delete("/v1/game/" + gameId))
//                    .andExpect(MockMvcResultMatchers.status().isNoContent()) TODO metoda może zwracać status NoContent
                    .andExpect(MockMvcResultMatchers.status().isOk());

            Mockito.verify(facade, Mockito.times(1)).deleteGame(gameId);
        }
    }
}