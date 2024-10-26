package org.esport.controller;

import org.esport.model.Player;
import org.esport.service.interfaces.PlayerService;

import java.util.List;
import java.util.Optional;

public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public Player registerPlayer(String username, int age) {
        System.out.println("Attempting to register a new player: " + username);
        Player player = new Player();
        player.setUsername(username);
        player.setAge(age);
        return playerService.registerPlayer(player);
    }

    public Player editPlayer(Long id, String newUsername, int newAge) {
        System.out.println("Attempting to edit player with ID: " + id);
        Optional<Player> playerOptional = playerService.getPlayer(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            player.setUsername(newUsername);
            player.setAge(newAge);
            return playerService.editPlayer(player);
        } else {
            System.out.println("Player with ID " + id + " not found");
            return null;
        }
    }

    public void deletePlayer(Long id) {
        System.out.println("Attempting to delete player with ID: " + id);
        playerService.deletePlayer(id);
    }

    public Optional<Player> getPlayer(Long id) {
        System.out.println("Attempting to get player with ID: " + id);
        return playerService.getPlayer(id);
    }

    public List<Player> getAllPlayers() {
        System.out.println("Attempting to get all players");
        return playerService.getAllPlayers();
    }

    public List<Player> getPlayersByTeam(Long teamId) {
        System.out.println("Attempting to get players for team with ID: " + teamId);
        return playerService.getPlayersByTeam(teamId);
    }

    public boolean playerExistsByUsername(String username) {
        System.out.println("Checking if player exists with username: " + username);
        return playerService.existsByUsername(username);
    }

    public Optional<Player> getPlayerByUsername(String username) {
        System.out.println("Attempting to get player with username: " + username);
        return playerService.findByUsername(username);
    }
}
