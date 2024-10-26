package org.esport.presentation.menu;

import org.esport.controller.PlayerController;
import org.esport.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PlayerMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMenu.class);
    private final PlayerController playerController;
    private final Scanner scanner;

    public PlayerMenu(PlayerController playerController, Scanner scanner) {
        this.playerController = playerController;
        this.scanner = scanner;
    }

    public void displayMenu() {
        boolean continueRunning = true;
        while (continueRunning) {
            LOGGER.info("Player Menu:");
            LOGGER.info("1. Register a player");
            LOGGER.info("2. Edit a player");
            LOGGER.info("3. Delete a player");
            LOGGER.info("4. Display a player");
            LOGGER.info("5. Display all players");
            LOGGER.info("6. Display players by team");
            LOGGER.info("0. Return to main menu");
            LOGGER.info("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    registerPlayer();
                    break;
                case 2:
                    editPlayer();
                    break;
                case 3:
                    deletePlayer();
                    break;
                case 4:
                    displayPlayer();
                    break;
                case 5:
                    displayAllPlayers();
                    break;
                case 6:
                    displayPlayersByTeam();
                    break;
                case 0:
                    continueRunning = false;
                    break;
                default:
                    LOGGER.warn("Invalid option. Please try again.");
            }
        }
    }

    private void registerPlayer() {
        LOGGER.info("Enter player's username: ");
        String username = scanner.nextLine();
        LOGGER.info("Enter player's age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Player newPlayer = playerController.registerPlayer(username, age);
        if (newPlayer != null) {
            LOGGER.info("Player registered successfully. ID: {}", newPlayer.getId());
        } else {
            LOGGER.error("Failed to register player.");
        }
    }

    private void editPlayer() {
        LOGGER.info("Enter the ID of the player to edit: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        LOGGER.info("Enter the new username for the player: ");
        String newUsername = scanner.nextLine();
        LOGGER.info("Enter the new age for the player: ");
        int newAge = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Player editedPlayer = playerController.editPlayer(id, newUsername, newAge);
        if (editedPlayer != null) {
            LOGGER.info("Player edited successfully.");
        } else {
            LOGGER.error("Failed to edit player.");
        }
    }

    private void deletePlayer() {
        LOGGER.info("Enter the ID of the player to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        playerController.deletePlayer(id);
        LOGGER.info("Player deleted successfully.");
    }

    private void displayPlayer() {
        LOGGER.info("Enter the ID of the player to display: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Player> playerOptional = playerController.getPlayer(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            LOGGER.info("Player details:");
            LOGGER.info("ID: {}", player.getId());
            LOGGER.info("Username: {}", player.getUsername());
            LOGGER.info("Age: {}", player.getAge());
        } else {
            LOGGER.warn("Player not found.");
        }
    }

    private void displayAllPlayers() {
        List<Player> players = playerController.getAllPlayers();
        if (!players.isEmpty()) {
            LOGGER.info("List of all players:");
            for (Player player : players) {
                LOGGER.info("ID: {}, Username: {}, Age: {}", player.getId(), player.getUsername(), player.getAge());
            }
        } else {
            LOGGER.warn("No players found.");
        }
    }

    private void displayPlayersByTeam() {
        LOGGER.info("Enter the team ID: ");
        Long teamId = scanner.nextLong();
        scanner.nextLine();

        List<Player> players = playerController.getPlayersByTeam(teamId);
        if (!players.isEmpty()) {
            LOGGER.info("Players in team (ID: {}):", teamId);
            for (Player player : players) {
                LOGGER.info("ID: {}, Username: {}, Age: {}", player.getId(), player.getUsername(), player.getAge());
            }
        } else {
            LOGGER.warn("No players found for this team.");
        }
    }
}
