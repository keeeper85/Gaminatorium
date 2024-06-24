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

import static org.hamcrest.Matchers.is;


@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameRepository repository;

    @Nested
    class getMethodTestes {

        @BeforeAll
        static void setUpBeforeClass() throws Exception {

        }

        @Test
        void getTestShouldReturnGameById() throws Exception {
            //given
            var gameId = 1;
            var game = new Game();
            game.setTitle("foo");
            var gameDto = GameDto.builder()
                    .title("foo")
                    .build();

            //when
            Mockito.when(repository.findById(1)).thenReturn(new Game());

            //then
            mvc.perform(MockMvcRequestBuilders.get("/v1/game/" + gameId))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("foo")));
        }

    }
}