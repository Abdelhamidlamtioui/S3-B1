package org.esport.service.impl;

import org.esport.dao.interfaces.GameDao;
import org.esport.model.Game;
import org.esport.service.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameServiceImplTest {

    @Mock
    private GameDao gameDao;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame_success() {
        Game game = new Game("Test Game", 5, 30);
        when(gameDao.create(any(Game.class))).thenReturn(game);

        Game result = gameService.createGame(game);

        assertNotNull(result);
        assertEquals("Test Game", result.getName());
        verify(gameDao, times(1)).create(any(Game.class));
    }

    @Test
    void editGame_success() {
        Game game = new Game("Updated Game", 7, 45);
        when(gameDao.edit(any(Game.class))).thenReturn(game);

        Game result = gameService.editGame(game);

        assertNotNull(result);
        assertEquals("Updated Game", result.getName());
        verify(gameDao, times(1)).edit(any(Game.class));
    }

    @Test
    void deleteGame_success() {
        Long id = 1L;
        doNothing().when(gameDao).delete(id);

        gameService.deleteGame(id);

        verify(gameDao, times(1)).delete(id);
    }

    @Test
    void getGame_success() {
        Long id = 1L;
        Game game = new Game("Test Game", 5, 30);
        when(gameDao.findById(id)).thenReturn(Optional.of(game));

        Optional<Game> result = gameService.getGame(id);

        assertTrue(result.isPresent());
        assertEquals("Test Game", result.get().getName());
        verify(gameDao, times(1)).findById(id);
    }

    @Test
    void getAllGames_success() {
        List<Game> games = Arrays.asList(
                new Game("Game 1", 3, 20),
                new Game("Game 2", 7, 40));
        when(gameDao.findAll()).thenReturn(games);

        List<Game> result = gameService.getAllGames();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gameDao, times(1)).findAll();
    }
}
