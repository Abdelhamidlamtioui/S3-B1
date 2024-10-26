package org.esport.controller;

import org.esport.model.Player;
import org.esport.service.interfaces.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @Test
    void createPlayer_success() {
        Player player = new Player("TestPlayer", 20);
        when(playerService.registerPlayer(any(Player.class))).thenReturn(player);

        Player result = playerController.registerPlayer("TestPlayer", 20);

        assertNotNull(result);
        assertEquals("TestPlayer", result.getUsername());
        assertEquals(20, result.getAge());
        verify(playerService, times(1)).registerPlayer(any(Player.class));
    }

    @Test
    void updatePlayer_success() {
        Long id = 1L;
        Player player = new Player("UpdatedPlayer", 22);
        when(playerService.getPlayer(id)).thenReturn(Optional.of(new Player()));
        when(playerService.editPlayer(any(Player.class))).thenReturn(player);

        Player result = playerController.editPlayer(id, "UpdatedPlayer", 22);

        assertNotNull(result);
        assertEquals("TestPlayer", result.getUsername());
        assertEquals(22, result.getAge());
        verify(playerService, times(1)).getPlayer(id);
        verify(playerService, times(1)).editPlayer(any(Player.class));
    }

    @Test
    void deletePlayer_success() {
        Long id = 1L;
        doNothing().when(playerService).deletePlayer(id);

        playerController.deletePlayer(id);

        verify(playerService, times(1)).deletePlayer(id);
    }

    @Test
    void getPlayer_success() {
        Long id = 1L;
        Player player = new Player("ExistingPlayer", 25);
        when(playerService.getPlayer(id)).thenReturn(Optional.of(player));

        Optional<Player> result = playerController.getPlayer(id);

        assertTrue(result.isPresent());
        assertEquals("ExistingPlayer", result.get().getUsername());
        assertEquals(25, result.get().getAge());
        verify(playerService, times(1)).getPlayer(id);
    }

    @Test
    void getAllPlayers_success() {
        List<Player> players = Arrays.asList(
                new Player("Player1", 20),
                new Player("Player2", 22));
        when(playerService.getAllPlayers()).thenReturn(players);

        List<Player> result = playerController.getAllPlayers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(playerService, times(1)).getAllPlayers();
    }
}
