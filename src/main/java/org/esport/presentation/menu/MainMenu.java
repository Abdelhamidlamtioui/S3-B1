package org.esport.presentation.menu;

import org.esport.controller.PlayerController;
import org.esport.controller.TeamController;
import org.esport.controller.TournementController;
import org.esport.controller.GameController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class MainMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainMenu.class);
    private final PlayerController playerController;
    private final TeamController teamController;
    private final TournementController tournementController;
    private final GameController gameController;
    private final Scanner scanner;

    public MainMenu(PlayerController playerController, TeamController teamController,
                    TournementController tournementController, GameController gameController) {
        this.playerController = playerController;
        this.teamController = teamController;
        this.tournementController = tournementController;
        this.gameController = gameController;
        this.scanner = new Scanner(System.in);
    }

    public void displayMainMenu() {
        boolean continueRunning = true;
        while (continueRunning) {
            LOGGER.info("Main Menu:");
            LOGGER.info("1. Player Management");
            LOGGER.info("2. Team Management");
            LOGGER.info("3. Tournement Management");
            LOGGER.info("4. Game Management");
            LOGGER.info("0. Exit");
            LOGGER.info("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    new PlayerMenu(playerController, scanner).displayMenu();
                    break;
                case 2:
                    new TeamMenu(teamController, playerController, scanner).displayMenu();
                    break;
                case 3:
                    new TournementMenu(tournementController, teamController, gameController, scanner).displayMenu();
                    break;
                case 4:
                    new GameMenu(gameController, scanner).displayMenu();
                    break;
                case 0:
                    continueRunning = false;
                    break;
                default:
                    LOGGER.warn("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}
