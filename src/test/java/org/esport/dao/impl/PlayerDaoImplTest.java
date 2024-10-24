package org.esport.dao.impl;

import org.esport.dao.PlayerDaoImpl;
import org.esport.model.Player;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PlayerDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PlayerDaoImpl playerDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_success() {
        Player player = new Player("TestPlayer", 20);
        doNothing().when(entityManager).persist(any(Player.class));

        Player result = playerDao.register(player);

        assertNotNull(result);
        assertEquals("TestPlayer", result.getUsername()); // Changed getPseudo to getUsername
        assertEquals(20, result.getAge());
        verify(entityManager, times(1)).persist(any(Player.class));
    }

    @Test
    void update_success() {
        Player player = new Player("UpdatedPlayer", 22);
        when(entityManager.merge(any(Player.class))).thenReturn(player);

        Player result = playerDao.edit(player);

        assertNotNull(result);
        assertEquals("UpdatedPlayer", result.getUsername()); // Changed getPseudo to getUsername
        assertEquals(22, result.getAge());
        verify(entityManager, times(1)).merge(any(Player.class));
    }

    @Test
    void delete_success() {
        Long id = 1L;
        Player player = new Player("PlayerToDelete", 25);
        when(entityManager.find(Player.class, id)).thenReturn(player);
        doNothing().when(entityManager).remove(any(Player.class));

        playerDao.delete(id);

        verify(entityManager, times(1)).find(Player.class, id);
        verify(entityManager, times(1)).remove(any(Player.class));
    }

    @Test
    void findById_success() {
        Long id = 1L;
        Player player = new Player("ExistingPlayer", 25);
        when(entityManager.find(Player.class, id)).thenReturn(player);

        Optional<Player> result = playerDao.findById(id);

        assertTrue(result.isPresent());
        assertEquals("ExistingPlayer", result.get().getUsername()); // Changed getPseudo to getUsername
        assertEquals(25, result.get().getAge());
        verify(entityManager, times(1)).find(Player.class, id);
    }

    @Test
    void findAll_success() {
        List<Player> players = Arrays.asList(
                new Player("Player1", 20),
                new Player("Player2", 22));
        TypedQuery<Player> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Player.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(players);

        List<Player> result = playerDao.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(entityManager, times(1)).createQuery(anyString(), eq(Player.class));
        verify(query, times(1)).getResultList();
    }
}
