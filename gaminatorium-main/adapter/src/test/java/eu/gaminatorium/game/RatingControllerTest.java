package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.game.dto.GameRatingDto;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RatingController.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Facade facade;

    @Nested
    class getMethodsTests {

        @Test
        void getGameAverageScoreIfGameExist() throws Exception {
            //TODO score gry będzie opisowe czy liczbowe? Teraz wartość score jest typu String
            // metoda GameAverageScore nie zwraca Jsona
            //given
            var gameId = 1;
            var score = "4.0";

            //when
            when(facade.getCurrentGameScore(gameId)).thenReturn(score);

            //then
            mvc.perform(get("/v1/ratings/score/" + gameId).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().string(score));
        }

        @Test
        void getGameRandomRating() throws Exception {
            //given
            var gameId = 1;

            var gameRatingDto = GameRatingDto.builder()
                    .comment("foo")
                    .build();

            Optional<GameRatingDto> optionalGameRatingDto = Optional.of(gameRatingDto);

            //when
            when(facade.getRandomRating(gameId)).thenReturn(optionalGameRatingDto); //TODO metoda powinna zwracac obiekt lub rzucic wyjątek zamiast optional

            //then
            mvc.perform(get("/v1/ratings/random/" + gameId).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.comment").value("foo"));

        }
    }

}