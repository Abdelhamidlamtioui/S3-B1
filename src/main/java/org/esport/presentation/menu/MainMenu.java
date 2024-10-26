package org.esport.presentation.menu;

import org.esport.controller.PlayerController;
import org.esport.controller.TeamController;
import org.esport.controller.TournementController;
import org.esport.controller.GameController;

import java.util.Scanner;

public class MainMenu {
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
            System.out.println("Main Menu:");
            System.out.println("1. Player Management");
            System.out.println("2. Team Management");
            System.out.println("3. Tournement Management");
            System.out.println("4. Game Management");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

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
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}
