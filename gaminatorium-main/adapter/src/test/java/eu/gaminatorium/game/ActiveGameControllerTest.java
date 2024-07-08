package eu.gaminatorium.game;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ActiveGameController.class)
class ActiveGameControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Facade facade;

    @Nested
    class getMethodsTests {

        @Test
        void getAllActiveGames() throws Exception {
            //TODO Uzupełnić ActiveGameController
        }

    }

}