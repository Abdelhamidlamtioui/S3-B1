package org.esport.controller;

import org.esport.model.Game;
import org.esport.service.interfaces.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        // No need to call MockitoAnnotations.openMocks(this) when using @ExtendWith(MockitoExtension.class)
    }

    @Test
    void createGame_success() {
        Game game = new Game("Test Game", 5, 30);
        when(gameService.createGame(any(Game.class))).thenReturn(game);

        Game result = gameController.createGame("Test Game", 5, 30);

        assertNotNull(result);
        assertEquals("Test Game", result.getName());
        assertEquals(5, result.getDifficulty());
        assertEquals(30, result.getAverageMatchDuration());
        verify(gameService, times(1)).createGame(any(Game.class));
    }

    @Test
    void updateGame_success() {
        Long id = 1L;
        Game game = new Game("Updated Game", 7, 45);
        when(gameService.getGame(id)).thenReturn(Optional.of(new Game()));
        when(gameService.editGame(any(Game.class))).thenReturn(game);

        Game result = gameController.editGame(id, "Updated Game", 7, 45);

        assertNotNull(result);
        assertEquals("Updated Game", result.getName());
        assertEquals(7, result.getDifficulty());
        assertEquals(45, result.getAverageMatchDuration());
        verify(gameService, times(1)).getGame(id);
        verify(gameService, times(1)).editGame(any(Game.class));
    }

    @Test
    void deleteGame_success() {
        Long id = 1L;
        doNothing().when(gameService).deleteGame(id);

        gameController.deleteGame(id);

        verify(gameService, times(1)).deleteGame(id);
    }

    @Test
    void getGame_success() {
        Long id = 1L;
        Game game = new Game("Test Game", 5, 30);
        when(gameService.getGame(id)).thenReturn(Optional.of(game));

        Optional<Game> result = gameController.getGame(id);

        assertTrue(result.isPresent());
        assertEquals("Test Game", result.get().getName());
        verify(gameService, times(1)).getGame(id);
    }

    @Test
    void getAllGames_success() {
        List<Game> games = Arrays.asList(
                new Game("Game 1", 3, 20),
                new Game("Game 2", 7, 40));
        when(gameService.getAllGames()).thenReturn(games);

        List<Game> result = gameController.getAllGames();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gameService, times(1)).getAllGames();
    }
}
