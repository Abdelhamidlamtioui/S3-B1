package org.esport.dao.impl;

import org.esport.dao.GameDaoImpl;
import org.esport.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private GameDaoImpl gameDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame_success() {
        Game game = new Game("Test Game", 5, 30);
        doNothing().when(entityManager).persist(any(Game.class));

        Game result = gameDao.create(game);

        assertNotNull(result);
        assertEquals("Test Game", result.getName()); // Renamed getNom to getName
        verify(entityManager, times(1)).persist(any(Game.class));
    }

    @Test
    void updateGame_success() {
        Game game = new Game("Updated Game", 7, 45);
        when(entityManager.merge(any(Game.class))).thenReturn(game);

        Game result = gameDao.edit(game);

        assertNotNull(result);
        assertEquals("Updated Game", result.getName()); // Renamed getNom to getName
        verify(entityManager, times(1)).merge(any(Game.class));
    }

    @Test
    void deleteGame_success() {
        Long id = 1L;
        Game game = new Game("Test Game", 5, 30);
        when(entityManager.find(Game.class, id)).thenReturn(game);
        doNothing().when(entityManager).remove(any(Game.class));

        gameDao.delete(id);

        verify(entityManager, times(1)).find(Game.class, id);
        verify(entityManager, times(1)).remove(any(Game.class));
    }

    @Test
    void findGameById_success() {
        Long id = 1L;
        Game game = new Game("Test Game", 5, 30);
        when(entityManager.find(Game.class, id)).thenReturn(game);

        Optional<Game> result = gameDao.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Test Game", result.get().getName()); // Renamed getNom to getName
        verify(entityManager, times(1)).find(Game.class, id);
    }

    @Test
    void findAllGames_success() {
        List<Game> games = Arrays.asList(
                new Game("Game 1", 3, 20),
                new Game("Game 2", 7, 40));
        TypedQuery<Game> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Game.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(games);

        List<Game> result = gameDao.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(entityManager, times(1)).createQuery(anyString(), eq(Game.class));
        verify(query, times(1)).getResultList();
    }
}
