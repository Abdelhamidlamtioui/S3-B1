package org.esport.presentation.menu;

import org.esport.controller.PlayerController;
import org.esport.model.Player;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PlayerMenu {
    private final PlayerController playerController;
    private final Scanner scanner;

    public PlayerMenu(PlayerController playerController, Scanner scanner) {
        this.playerController = playerController;
        this.scanner = scanner;
    }

    public void displayMenu() {
        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println("Player Menu:");
            System.out.println("1. Register a player");
            System.out.println("2. Edit a player");
            System.out.println("3. Delete a player");
            System.out.println("4. Display a player");
            System.out.println("5. Display all players");
            System.out.println("6. Display players by team");
            System.out.println("0. Return to main menu");
            System.out.print("Choose an option: ");

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
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void registerPlayer() {
        System.out.print("Enter player's username: ");
        String username = scanner.nextLine();
        System.out.print("Enter player's age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Player newPlayer = playerController.registerPlayer(username, age);
        if (newPlayer != null) {
            System.out.println("Player registered successfully. ID: " + newPlayer.getId());
        } else {
            System.out.println("Failed to register player.");
        }
    }

    private void editPlayer() {
        System.out.print("Enter the ID of the player to edit: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter the new username for the player: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter the new age for the player: ");
        int newAge = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Player editedPlayer = playerController.editPlayer(id, newUsername, newAge);
        if (editedPlayer != null) {
            System.out.println("Player edited successfully.");
        } else {
            System.out.println("Failed to edit player.");
        }
    }

    private void deletePlayer() {
        System.out.print("Enter the ID of the player to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        playerController.deletePlayer(id);
        System.out.println("Player deleted successfully.");
    }

    private void displayPlayer() {
        System.out.print("Enter the ID of the player to display: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Player> playerOptional = playerController.getPlayer(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            System.out.println("Player details:");
            System.out.println("ID: " + player.getId());
            System.out.println("Username: " + player.getUsername());
            System.out.println("Age: " + player.getAge());
        } else {
            System.out.println("Player not found.");
        }
    }

    private void displayAllPlayers() {
        List<Player> players = playerController.getAllPlayers();
        if (!players.isEmpty()) {
            System.out.println("List of all players:");
            for (Player player : players) {
                System.out.println("ID: " + player.getId() + ", Username: " + player.getUsername() + ", Age: " + player.getAge());
            }
        } else {
            System.out.println("No players found.");
        }
    }

    private void displayPlayersByTeam() {
        System.out.print("Enter the team ID: ");
        Long teamId = scanner.nextLong();
        scanner.nextLine();

        List<Player> players = playerController.getPlayersByTeam(teamId);
        if (!players.isEmpty()) {
            System.out.println("Players in team (ID: " + teamId + "):");
            for (Player player : players) {
                System.out.println("ID: " + player.getId() + ", Username: " + player.getUsername() + ", Age: " + player.getAge());
            }
        } else {
            System.out.println("No players found for this team.");
        }
    }
}
