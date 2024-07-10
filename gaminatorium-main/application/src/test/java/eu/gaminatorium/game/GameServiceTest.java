package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.game.dto.NewGameDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    void counAllAvailableGames() {
        //given
        var gameStatus = Game.ModerationStatus.ACCEPTED;

        //when
        when(gameRepository.countAllByModerationStatus(gameStatus)).thenReturn(1);

        //then
        int result = gameService.countAllAvailableGames();

        assertEquals(1, result);
    }

    @Test
    void getGameByIdWhenGameExists() {
        //given
        var gameId = 1;
        var game = new Game();
        game.setTitle("foo");

        var gameDto = GameDto.builder()
                        .title(game.getTitle())
                        .build();

        //when
        when(gameRepository.existsById(gameId)).thenReturn(true);
        when(gameRepository.findById(gameId)).thenReturn(game);

        //then
        var result = gameService.getGameById(gameId);
        assertEquals(result.orElse(null).title(), gameDto.title());
    }

    @Test
    void getGameByIdWhenGameDoesNotExist() {
        //given
        var gameId = 1;

        //when
        when(gameRepository.existsById(gameId)).thenReturn(false);

        //then
        var result = gameService.getGameById(gameId);

        assertEquals(result, Optional.empty() );
    }

    @Test
    void addNewGameWhenTitleExists() {
        //given
        var newGameDto = NewGameDto.builder()
                .title("foo")
                .build();

        //when
        when(gameRepository.existsByTitle("foo")).thenReturn(true);

        //then
        Optional<NewGameDto> result = gameService.addNewGame(newGameDto);

        assertEquals(result.orElse(null).getTitle(), "Error! Chosen title already exists!");
    }

    @Test
    void addNewGameWhenTitleDoesNotExist() {
        //given
        var newGameDto = NewGameDto.builder()
                .title("foo")
                .build();

        //when
        when(gameRepository.existsByTitle("foo")).thenReturn(false);

        //then
        Optional<NewGameDto> result = gameService.addNewGame(newGameDto);

        assertEquals(result.orElse(null).getTitle(), "foo");
    }

    @Test
    void updateGameWhenGameExist() {
        //given
        var gameId = 1;
        var newGameDto = NewGameDto.builder()
                .title("foo")
                .build();

        var game = new Game();
        game.setTitle("foo");

        //when
        when(gameRepository.existsById(gameId)).thenReturn(true);
        when(gameRepository.findById(gameId)).thenReturn(game);

        //then
        Optional<GameDto> result = gameService.updateGame(gameId, newGameDto);

        assertEquals(result.orElse(null).title(), "foo");
    }

    @Test
    void updateGameWhenGameDoesNotExist() {
        //given
        var gameId = 1;
        var newGameDto = NewGameDto.builder()
                .title("foo")
                .build();

        var game = new Game();
        game.setTitle("foo");

        //when
        when(gameRepository.existsById(gameId)).thenReturn(false);

        //then
        Optional<GameDto> result = gameService.updateGame(gameId, newGameDto);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteGameWhenGameExists() {   //TODO metoda deleteGame może być typu void zamiast zwracąć Optional<Void>
        //given
        var gameId = 1;

        //when
        when(gameRepository.existsById(gameId)).thenReturn(true);

        //then
        Optional<Void> result = gameService.deleteGame(gameId);

        assertTrue(result.isEmpty());
        verify(gameRepository, times(1)).deleteById(gameId);
    }
}