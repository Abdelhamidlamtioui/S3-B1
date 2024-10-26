package org.esport.presentation.menu;

import org.esport.controller.GameController;
import org.esport.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GameMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMenu.class);
    private final GameController gameController;
    private final Scanner scanner;

    public GameMenu(GameController gameController, Scanner scanner) {
        this.gameController = gameController;
        this.scanner = scanner;
    }

    public void displayMenu() {
        boolean continueMenu = true;
        while (continueMenu) {
            LOGGER.info("Game Menu:");
            LOGGER.info("1. Create a game");
            LOGGER.info("2. Edit a game");
            LOGGER.info("3. Delete a game");
            LOGGER.info("4. View a game");
            LOGGER.info("5. View all games");
            LOGGER.info("0. Return to main menu");
            LOGGER.info("Choose an option:");

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
                    LOGGER.warn("Invalid option. Please try again.");
            }
        }
    }

    private void createGame() {
        LOGGER.info("Creating a new game");
        LOGGER.info("Enter the game name:");
        String name = scanner.nextLine();
        LOGGER.info("Enter the game difficulty (1-10):");
        int difficulty = scanner.nextInt();
        LOGGER.info("Enter the average match duration (in minutes):");
        int averageMatchDuration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Game newGame = gameController.createGame(name, difficulty, averageMatchDuration);
        if (newGame != null) {
            LOGGER.info("Game created successfully. ID: {}", newGame.getId());
        } else {
            LOGGER.error("Error creating the game.");
        }
    }

    private void editGame() {
        LOGGER.info("Editing a game");
        LOGGER.info("Enter the game ID to edit:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Game> gameOptional = gameController.getGame(id);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            LOGGER.info("Game found: {}", game.getName());
            LOGGER.info("Enter the new game name (or press Enter to keep the current name):");
            String newName = scanner.nextLine();
            if (newName.isEmpty()) {
                newName = game.getName();
            }

            LOGGER.info("Enter the new game difficulty (1-10) (or -1 to keep the current difficulty):");
            int newDifficulty = scanner.nextInt();
            if (newDifficulty == -1) {
                newDifficulty = game.getDifficulty();
            }

            LOGGER.info("Enter the new average match duration (in minutes) (or -1 to keep the current duration):");
            int newAverageMatchDuration = scanner.nextInt();
            if (newAverageMatchDuration == -1) {
                newAverageMatchDuration = game.getAverageMatchDuration();
            }
            scanner.nextLine(); // Consume the newline

            Game modifiedGame = gameController.editGame(id, newName, newDifficulty, newAverageMatchDuration);
            if (modifiedGame != null) {
                LOGGER.info("Game edited successfully.");
            } else {
                LOGGER.error("Error editing the game.");
            }
        } else {
            LOGGER.warn("Game not found.");
        }
    }

    private void deleteGame() {
        LOGGER.info("Deleting a game");
        LOGGER.info("Enter the game ID to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Game> gameOptional = gameController.getGame(id);
        if (gameOptional.isPresent()) {
            LOGGER.info("Are you sure you want to delete the game {}? (Y/N)", gameOptional.get().getName());
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("Y")) {
                gameController.deleteGame(id);
                LOGGER.info("Game deleted successfully.");
            } else {
                LOGGER.info("Deletion canceled.");
            }
        } else {
            LOGGER.warn("Game not found.");
        }
    }

    private void viewGame() {
        LOGGER.info("Viewing a game");
        LOGGER.info("Enter the game ID to view:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Game> gameOptional = gameController.getGame(id);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            LOGGER.info("Game details:");
            LOGGER.info("ID: {}", game.getId());
            LOGGER.info("Name: {}", game.getName());
            LOGGER.info("Difficulty: {}", game.getDifficulty());
            LOGGER.info("Average match duration: {} minutes", game.getAverageMatchDuration());
        } else {
            LOGGER.warn("Game not found.");
        }
    }

    private void viewAllGames() {
        LOGGER.info("List of all games:");
        List<Game> games = gameController.getAllGames();
        if (!games.isEmpty()) {
            for (Game game : games) {
                LOGGER.info("ID: {}, Name: {}, Difficulty: {}, Average duration: {} minutes",
                        game.getId(), game.getName(), game.getDifficulty(), game.getAverageMatchDuration());
            }
        } else {
            LOGGER.warn("No games found.");
        }
    }
}
