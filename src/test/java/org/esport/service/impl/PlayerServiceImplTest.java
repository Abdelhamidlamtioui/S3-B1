package org.esport.service.impl;

import org.esport.dao.interfaces.PlayerDao;
import org.esport.model.Player;
import org.esport.service.PlayerServiceImpl;
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

class PlayerServiceImplTest {

    @Mock
    private PlayerDao playerDao;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerPlayer_success() {
        Player player = new Player("TestPlayer", 20);
        when(playerDao.register(any(Player.class))).thenReturn(player);

        Player result = playerService.registerPlayer(player);

        assertNotNull(result);
        assertEquals("TestPlayer", result.getUsername());
        assertEquals(20, result.getAge());
        verify(playerDao, times(1)).register(any(Player.class));
    }

    @Test
    void editPlayer_success() {
        Player player = new Player("UpdatedPlayer", 22);
        when(playerDao.edit(any(Player.class))).thenReturn(player);

        Player result = playerService.editPlayer(player);

        assertNotNull(result);
        assertEquals("UpdatedPlayer", result.getUsername());
        assertEquals(22, result.getAge());
        verify(playerDao, times(1)).edit(any(Player.class));
    }

    @Test
    void deletePlayer_success() {
        Long id = 1L;
        doNothing().when(playerDao).delete(id);

        playerService.deletePlayer(id);

        verify(playerDao, times(1)).delete(id);
    }

    @Test
    void getPlayer_success() {
        Long id = 1L;
        Player player = new Player("ExistingPlayer", 25);
        when(playerDao.findById(id)).thenReturn(Optional.of(player));

        Optional<Player> result = playerService.getPlayer(id);

        assertTrue(result.isPresent());
        assertEquals("ExistingPlayer", result.get().getUsername());
        assertEquals(25, result.get().getAge());
        verify(playerDao, times(1)).findById(id);
    }

    @Test
    void getAllPlayers_success() {
        List<Player> players = Arrays.asList(
                new Player("Player1", 20),
                new Player("Player2", 22)
        );
        when(playerDao.findAll()).thenReturn(players);

        List<Player> result = playerService.getAllPlayers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(playerDao, times(1)).findAll();
    }

    @Test
    void getPlayersByTeam_success() {
        Long teamId = 1L;
        List<Player> players = Arrays.asList(
                new Player("Player1", 20),
                new Player("Player2", 22)
        );
        when(playerDao.findByTeam(teamId)).thenReturn(players);

        List<Player> result = playerService.getPlayersByTeam(teamId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(playerDao, times(1)).findByTeam(teamId);
    }

    @Test
    void existsByPseudo_success() {
        String pseudo = "TestPlayer";
        when(playerDao.existsByUsername(pseudo)).thenReturn(true);

        boolean result = playerService.existsByUsername(pseudo);

        assertTrue(result);
        verify(playerDao, times(1)).existsByUsername(pseudo);
    }

    @Test
    void findByPseudo_success() {
        String pseudo = "TestPlayer";
        Player player = new Player(pseudo, 25);
        when(playerDao.findByUsername(pseudo)).thenReturn(Optional.of(player));

        Optional<Player> result = playerService.findByUsername(pseudo);

        assertTrue(result.isPresent());
        assertEquals(pseudo, result.get().getUsername());
        assertEquals(25, result.get().getAge());
        verify(playerDao, times(1)).findByUsername(pseudo);
    }
}
