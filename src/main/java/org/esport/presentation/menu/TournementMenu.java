package org.esport.presentation.menu;

import org.esport.controller.GameController;
import org.esport.controller.TournementController;
import org.esport.controller.TeamController;
import org.esport.model.Tournement;
import org.esport.model.Team;
import org.esport.util.ConsoleLogger;
import org.esport.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.esport.model.enums.TournementStatus;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.InputMismatchException;

public class TournementMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournementMenu.class);
    private final TournementController tournementController;
    private final TeamController teamController;
    private final GameController gameController;
    private final Scanner scanner;

    public TournementMenu(TournementController tournementController, TeamController teamController,
                          GameController gameController, Scanner scanner) {
        this.tournementController = tournementController;
        this.teamController = teamController;
        this.gameController = gameController;
        this.scanner = scanner;
    }

    public void displayMenu() {
        int choice = -1;
        do {
            try {
                System.out.println("\n--- Tournement Menu ---");
                System.out.println("1. Create a tournement");
                System.out.println("2. Edit a tournement");
                System.out.println("3. Delete a tournement");
                System.out.println("4. Display a tournement");
                System.out.println("5. Display all tournements");
                System.out.println("6. Add a team to a tournement");
                System.out.println("7. Remove a team from a tournement");
                System.out.println("8. Calculate estimated duration of a tournement");
                System.out.println("9. Change tournement status");
                System.out.println("0. Return to main menu");
                System.out.print("Your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        createTournement();
                        break;
                    case 2:
                        editTournement();
                        break;
                    case 3:
                        deleteTournement();
                        break;
                    case 4:
                        displayTournement();
                        break;
                    case 5:
                        displayAllTournements();
                        break;
                    case 6:
                        addTeamToTournement();
                        break;
                    case 7:
                        removeTeamFromTournement();
                        break;
                    case 8:
                        calculateEstimatedDuration();
                        break;
                    case 9:
                        changeTournementStatus();
                        break;
                    case 0:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        } while (choice != 0);
    }

    private void createTournement() {
        System.out.println("Creating a new tournement");
        String title = ValidationUtil.readNonEmptyString(scanner, "Enter tournement title:");

        Long gameId = ValidationUtil.readPositiveLong(scanner, "Enter game ID for this tournement:");

        LocalDate startDate = ValidationUtil.readDate(scanner, "Enter start date (format: dd/MM/yyyy):",
                "dd/MM/yyyy");

        LocalDate endDate;
        while (true) {
            endDate = ValidationUtil.readDate(scanner, "Enter end date (format: dd/MM/yyyy):", "dd/MM/yyyy");
            try {
                ValidationUtil.validateDates(startDate, endDate);
                break; // Exit loop if dates are valid
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + " Please enter a new end date.");
            }
        }

        int spectatorCount = ValidationUtil.readNonNegativeInt(scanner, "Enter expected spectator count:");
        int averageMatchDuration = ValidationUtil.readNonNegativeInt(scanner, "Enter average match duration (minutes):");
        int ceremonyTime = ValidationUtil.readNonNegativeInt(scanner, "Enter ceremony time (minutes):");
        int pauseBetweenMatches = ValidationUtil.readNonNegativeInt(scanner, "Enter pause time between matches (minutes):");

        try {
            Tournement newTournement = tournementController.createTournement(title, gameId, startDate, endDate, spectatorCount,
                    averageMatchDuration, ceremonyTime, pauseBetweenMatches);
            if (newTournement != null) {
                System.out.println("Tournement created successfully. ID: " + newTournement.getId());
            } else {
                System.out.println("Error creating tournement.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editTournement() {
        System.out.println("Editing a tournement");
        System.out.print("Enter tournement ID to edit: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Optional<Tournement> tournementOptional = tournementController.getTournement(id);
        if (tournementOptional.isPresent()) {
            Tournement tournement = tournementOptional.get();
            System.out.print("Enter new title (or press Enter to keep current): ");
            String newTitle = scanner.nextLine();
            if (newTitle.isEmpty()) {
                newTitle = tournement.getTitle();
            }

            System.out.print("Enter new start date (dd/MM/yyyy) (or press Enter to keep current): ");
            String startDateStr = scanner.nextLine();
            LocalDate newStartDate = startDateStr.isEmpty() ? tournement.getStartDate()
                    : LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("Enter new end date (dd/MM/yyyy) (or press Enter to keep current): ");
            String endDateStr = scanner.nextLine();
            LocalDate newEndDate = endDateStr.isEmpty() ? tournement.getEndDate()
                    : LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("Enter new spectator count (or -1 to keep current): ");
            int newSpectatorCount = scanner.nextInt();
            if (newSpectatorCount == -1) {
                newSpectatorCount = tournement.getSpectatorCount();
            }
            scanner.nextLine(); // Consume newline

            try {
                ValidationUtil.validateDates(newStartDate, newEndDate);
                Tournement updatedTournement = tournementController.editTournement(id, newTitle, newStartDate, newEndDate, newSpectatorCount);
                if (updatedTournement != null) {
                    System.out.println("Tournement updated successfully.");
                } else {
                    System.out.println("Error updating tournement.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Tournement not found.");
        }
    }

    private void deleteTournement() {
        System.out.println("Deleting a tournement");
        System.out.print("Enter tournement ID to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        try {
            tournementController.deleteTournement(id);
            System.out.println("Tournement deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting tournement: " + e.getMessage());
        }
    }

    private void displayTournement() {
        System.out.println("Displaying a tournement");
        System.out.print("Enter tournement ID to display: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Optional<Tournement> tournementOptional = tournementController.getTournement(id);
        if (tournementOptional.isPresent()) {
            Tournement tournement = tournementOptional.get();
            System.out.println("Tournement details:");
            System.out.println("ID: " + tournement.getId());
            System.out.println("Title: " + tournement.getTitle());
            System.out.println("Game: " + tournement.getGame().getName());
            System.out.println("Start Date: " + tournement.getStartDate());
            System.out.println("End Date: " + tournement.getEndDate());
            System.out.println("Spectator count: " + tournement.getSpectatorCount());
            System.out.println("Status: " + tournement.getStatus());
            System.out.println("Participating teams:");
            for (Team team : tournement.getTeams()) {
                System.out.println("- " + team.getName());
            }
        } else {
            System.out.println("Tournement not found.");
        }
    }

    private void displayAllTournements() {
        System.out.println("List of all tournements:");
        List<Tournement> tournements = tournementController.getAllTournements();
        if (!tournements.isEmpty()) {
            for (Tournement tournement : tournements) {
                String gameName = tournement.getGame() != null ? tournement.getGame().getName() : "N/A";
                System.out.println("ID: " + tournement.getId() + ", Title: " + tournement.getTitle() + ", Game: "
                        + gameName + ", Status: " + tournement.getStatus());
            }
        } else {
            System.out.println("No tournements found.");
        }
    }

    private void addTeamToTournement() {
        System.out.println("Adding a team to a tournement");
        System.out.print("Enter tournement ID: ");
        Long tournementId = scanner.nextLong();
        System.out.print("Enter team ID to add: ");
        Long teamId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        try {
            tournementController.addTeamToTournement(tournementId, teamId);
            System.out.println("Team successfully added to tournement.");
        } catch (Exception e) {
            System.out.println("Error adding team to tournement: " + e.getMessage());
        }
    }

    private void removeTeamFromTournement() {
        System.out.println("Removing a team from a tournement");
        System.out.print("Enter tournement ID: ");
        Long tournementId = scanner.nextLong();
        System.out.print("Enter team ID to remove: ");
        Long teamId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        try {
            tournementController.removeTeamFromTournement(tournementId, teamId);
            System.out.println("Team successfully removed from tournement.");
        } catch (Exception e) {
            System.out.println("Error removing team from tournement: " + e.getMessage());
        }
    }

    private void calculateEstimatedDuration() {
        System.out.println("Calculating estimated duration of a tournement");
        System.out.print("Enter tournement ID: ");
        Long tournementId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        int estimatedDuration = tournementController.getEstimatedTournementDuration(tournementId);
        if (estimatedDuration > 0) {
            Optional<Tournement> tournementOptional = tournementController.getTournement(tournementId);
            if (tournementOptional.isPresent()) {
                Tournement tournement = tournementOptional.get();
                System.out.println("The estimated duration of the tournement is " + tournement.getEstimatedDuration() + " minutes.");
            } else {
                System.out.println("Tournement not found after calculation.");
            }
        } else {
            System.out.println("Unable to calculate estimated duration for tournement.");
        }
    }

    private void changeTournementStatus() {
        System.out.print("Enter tournement ID: ");
        Long tournementId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        System.out.println("Choose the new status:");
        System.out.println("1. PLANNED");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. COMPLETED");
        System.out.println("4. CANCELED");

        int choice = -1;
        while (choice < 1 || choice > 4) {
            System.out.print("Enter a number between 1 and 4: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        TournementStatus newStatus;
        switch (choice) {
            case 1:
                newStatus = TournementStatus.PLANNED;
                break;
            case 2:
                newStatus = TournementStatus.IN_PROGRESS;
                break;
            case 3:
                newStatus = TournementStatus.COMPLETED;
                break;
            case 4:
                newStatus = TournementStatus.CANCELED;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }

        tournementController.updateTournementStatus(tournementId, newStatus);
        System.out.println("Tournement status updated successfully.");
    }

}
