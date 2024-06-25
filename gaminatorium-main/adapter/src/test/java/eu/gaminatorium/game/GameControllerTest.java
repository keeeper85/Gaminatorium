package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Facade facade;

    @Nested
    class getMethodTestes {

        @Test
        void getGamesTottalCount() throws Exception {
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
        void getGameTagsWhenGameExistAndGameContainsTags() throws Exception {
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
        void getGameTagsWhenGameExistAndGameNotContainsTags() throws Exception{
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
        void getGameByTitle(){
            //TODO czy dopuszczamy możliwośc istnienia wielu gier o tym samym tytule
            // jeśli nie to czy metoda nie powinna zwracać gameDto zamiast List<GameDto>
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
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }
}