package org.esport.presentation.menu;

import org.esport.controller.GameController;
import org.esport.model.Game;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GameMenu {
    private final GameController gameController;
    private final Scanner scanner;

    public GameMenu(GameController gameController, Scanner scanner) {
        this.gameController = gameController;
        this.scanner = scanner;
    }

    public void displayMenu() {
        boolean continueMenu = true;
        while (continueMenu) {
            System.out.println("Game Menu:");
            System.out.println("1. Create a game");
            System.out.println("2. Edit a game");
            System.out.println("3. Delete a game");
            System.out.println("4. View a game");
            System.out.println("5. View all games");
            System.out.println("0. Return to main menu");
            System.out.println("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    createGame();
                    break;
                case 2:
                    editGame();
                    break;
                case 3:
                    deleteGame();
                    break;
                case 4:
                    viewGame();
                    break;
                case 5:
                    viewAllGames();
                    break;
                case 0:
                    continueMenu = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createGame() {
        System.out.println("Creating a new game");
        System.out.println("Enter the game name:");
        String name = scanner.nextLine();
        System.out.println("Enter the game difficulty (1-10):");
        int difficulty = scanner.nextInt();
        System.out.println("Enter the average match duration (in minutes):");
        int averageMatchDuration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Game newGame = gameController.createGame(name, difficulty, averageMatchDuration);
        if (newGame != null) {
            System.out.println("Game created successfully. ID: " + newGame.getId());
        } else {
            System.out.println("Error creating the game.");
        }
    }

    private void editGame() {
        System.out.println("Editing a game");
        System.out.println("Enter the game ID to edit:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Game> gameOptional = gameController.getGame(id);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            System.out.println("Game found: " + game.getName());
            System.out.println("Enter the new game name (or press Enter to keep the current name):");
            String newName = scanner.nextLine();
            if (newName.isEmpty()) {
                newName = game.getName();
            }

            System.out.println("Enter the new game difficulty (1-10) (or -1 to keep the current difficulty):");
            int newDifficulty = scanner.nextInt();
            if (newDifficulty == -1) {
                newDifficulty = game.getDifficulty();
            }

            System.out.println("Enter the new average match duration (in minutes) (or -1 to keep the current duration):");
            int newAverageMatchDuration = scanner.nextInt();
            if (newAverageMatchDuration == -1) {
                newAverageMatchDuration = game.getAverageMatchDuration();
            }
            scanner.nextLine(); // Consume the newline

            Game modifiedGame = gameController.editGame(id, newName, newDifficulty, newAverageMatchDuration);
            if (modifiedGame != null) {
                System.out.println("Game edited successfully.");
            } else {
                System.out.println("Error editing the game.");
            }
        } else {
            System.out.println("Game not found.");
        }
    }

    private void deleteGame() {
        System.out.println("Deleting a game");
        System.out.println("Enter the game ID to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Game> gameOptional = gameController.getGame(id);
        if (gameOptional.isPresent()) {
            System.out.println("Are you sure you want to delete the game " + gameOptional.get().getName() + "? (Y/N)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("Y")) {
                gameController.deleteGame(id);
                System.out.println("Game deleted successfully.");
            } else {
                System.out.println("Deletion canceled.");
            }
        } else {
            System.out.println("Game not found.");
        }
    }

    private void viewGame() {
        System.out.println("Viewing a game");
        System.out.println("Enter the game ID to view:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Game> gameOptional = gameController.getGame(id);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            System.out.println("Game details:");
            System.out.println("ID: " + game.getId());
            System.out.println("Name: " + game.getName());
            System.out.println("Difficulty: " + game.getDifficulty());
            System.out.println("Average match duration: " + game.getAverageMatchDuration() + " minutes");
        } else {
            System.out.println("Game not found.");
        }
    }

    private void viewAllGames() {
        System.out.println("List of all games:");
        List<Game> games = gameController.getAllGames();
        if (!games.isEmpty()) {
            for (Game game : games) {
                System.out.println("ID: " + game.getId() + ", Name: " + game.getName() + ", Difficulty: "
                        + game.getDifficulty() + ", Average duration: " + game.getAverageMatchDuration() + " minutes");
            }
        } else {
            System.out.println("No games found.");
        }
    }
}
