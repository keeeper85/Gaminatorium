package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
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

import static org.hamcrest.Matchers.is;


@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Facade facade;

    @Nested
    class getMethodTestes {

//        @BeforeAll
//        static void setUpBeforeClass() throws Exception {
//
//        }

        @Test
        void returnGameById() throws Exception {
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