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
                LOGGER.info("\n--- Tournement Menu ---");
                LOGGER.info("1. Create a tournement");
                LOGGER.info("2. Edit a tournement");
                LOGGER.info("3. Delete a tournement");
                LOGGER.info("4. Display a tournement");
                LOGGER.info("5. Display all tournements");
                LOGGER.info("6. Add a team to a tournement");
                LOGGER.info("7. Remove a team from a tournement");
                LOGGER.info("8. Calculate estimated duration of a tournement");
                LOGGER.info("9. Change tournement status");
                LOGGER.info("0. Return to main menu");
                LOGGER.info("Your choice: ");
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
                        LOGGER.info("Returning to main menu...");
                        break;
                    default:
                        LOGGER.warn("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                LOGGER.error("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        } while (choice != 0);
    }

    private void createTournement() {
        LOGGER.info("Creating a new tournement");
        String title = ValidationUtil.readNonEmptyString(scanner, "Enter tournement title:");
        Long gameId = ValidationUtil.readPositiveLong(scanner, "Enter game ID for this tournement:");
        LocalDate startDate = ValidationUtil.readDate(scanner, "Enter start date (format: dd/MM/yyyy):", "dd/MM/yyyy");

        LocalDate endDate;
        while (true) {
            endDate = ValidationUtil.readDate(scanner, "Enter end date (format: dd/MM/yyyy):", "dd/MM/yyyy");
            try {
                ValidationUtil.validateDates(startDate, endDate);
                break; // Exit loop if dates are valid
            } catch (IllegalArgumentException e) {
                LOGGER.error("Error: {}. Please enter a new end date.", e.getMessage());
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
                LOGGER.info("Tournement created successfully. ID: {}", newTournement.getId());
            } else {
                LOGGER.error("Error creating tournement.");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error: {}", e.getMessage());
        }
    }

    private void editTournement() {
        LOGGER.info("Editing a tournement");
        Long id = ValidationUtil.readPositiveLong(scanner, "Enter tournement ID to edit:");
        Optional<Tournement> tournementOptional = tournementController.getTournement(id);

        if (tournementOptional.isPresent()) {
            Tournement tournement = tournementOptional.get();
            String newTitle = ValidationUtil.readNonEmptyString(scanner, "Enter new title (or press Enter to keep current):");
            if (newTitle.isEmpty()) {
                newTitle = tournement.getTitle();
            }

            LOGGER.info("Enter new start date (dd/MM/yyyy) (or press Enter to keep current): ");
            String startDateStr = scanner.nextLine();
            LocalDate newStartDate = startDateStr.isEmpty() ? tournement.getStartDate() : LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            LOGGER.info("Enter new end date (dd/MM/yyyy) (or press Enter to keep current): ");
            String endDateStr = scanner.nextLine();
            LocalDate newEndDate = endDateStr.isEmpty() ? tournement.getEndDate() : LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            LOGGER.info("Enter new spectator count (or -1 to keep current): ");
            int newSpectatorCount = scanner.nextInt();
            if (newSpectatorCount == -1) {
                newSpectatorCount = tournement.getSpectatorCount();
            }
            scanner.nextLine(); // Consume newline

            try {
                ValidationUtil.validateDates(newStartDate, newEndDate);
                Tournement updatedTournement = tournementController.editTournement(id, newTitle, newStartDate, newEndDate, newSpectatorCount);
                if (updatedTournement != null) {
                    LOGGER.info("Tournement updated successfully.");
                } else {
                    LOGGER.error("Error updating tournement.");
                }
            } catch (IllegalArgumentException e) {
                LOGGER.error("Error: {}", e.getMessage());
            }
        } else {
            LOGGER.warn("Tournement not found.");
        }
    }

    private void deleteTournement() {
        LOGGER.info("Deleting a tournement");
        LOGGER.info("Enter tournement ID to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        try {
            tournementController.deleteTournement(id);
            LOGGER.info("Tournement deleted successfully.");
        } catch (Exception e) {
            LOGGER.error("Error deleting tournement: {}", e.getMessage());
        }
    }

    private void displayTournement() {
        LOGGER.info("Displaying a tournement");
        LOGGER.info("Enter tournement ID to display: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Optional<Tournement> tournementOptional = tournementController.getTournement(id);
        if (tournementOptional.isPresent()) {
            Tournement tournement = tournementOptional.get();
            LOGGER.info("Tournement details:");
            LOGGER.info("ID: {}", tournement.getId());
            LOGGER.info("Title: {}", tournement.getTitle());
            LOGGER.info("Game: {}", tournement.getGame().getName());
            LOGGER.info("Start Date: {}", tournement.getStartDate());
            LOGGER.info("End Date: {}", tournement.getEndDate());
            LOGGER.info("Spectator count: {}", tournement.getSpectatorCount());
            LOGGER.info("Status: {}", tournement.getStatus());
            LOGGER.info("Participating teams:");
            for (Team team : tournement.getTeams()) {
                LOGGER.info("- {}", team.getName());
            }
        } else {
            LOGGER.warn("Tournement not found.");
        }
    }

    private void displayAllTournements() {
        LOGGER.info("List of all tournements:");
        List<Tournement> tournements = tournementController.getAllTournements();
        if (!tournements.isEmpty()) {
            for (Tournement tournement : tournements) {
                String gameName = tournement.getGame() != null ? tournement.getGame().getName() : "N/A";
                LOGGER.info("ID: {}, Title: {}, Game: {}, Status: {}", tournement.getId(), tournement.getTitle(), gameName, tournement.getStatus());
            }
        } else {
            LOGGER.warn("No tournements found.");
        }
    }

    private void addTeamToTournement() {
        LOGGER.info("Adding a team to a tournement");
        LOGGER.info("Enter tournement ID: ");
        Long tournementId = scanner.nextLong();
        LOGGER.info("Enter team ID to add: ");
        Long teamId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        try {
            tournementController.addTeamToTournement(tournementId, teamId);
            LOGGER.info("Team successfully added to tournement.");
        } catch (Exception e) {
            LOGGER.error("Error adding team to tournement: {}", e.getMessage());
        }
    }

    private void removeTeamFromTournement() {
        LOGGER.info("Removing a team from a tournement");
        LOGGER.info("Enter tournement ID: ");
        Long tournementId = scanner.nextLong();
        LOGGER.info("Enter team ID to remove: ");
        Long teamId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        try {
            tournementController.removeTeamFromTournement(tournementId, teamId);
            LOGGER.info("Team successfully removed from tournement.");
        } catch (Exception e) {
            LOGGER.error("Error removing team from tournement: {}", e.getMessage());
        }
    }

    private void calculateEstimatedDuration() {
        LOGGER.info("Calculating estimated duration of a tournement");
        LOGGER.info("Enter tournement ID: ");
        Long tournementId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        int estimatedDuration = tournementController.getEstimatedTournementDuration(tournementId);
        if (estimatedDuration > 0) {
            Optional<Tournement> tournementOptional = tournementController.getTournement(tournementId);
            if (tournementOptional.isPresent()) {
                Tournement tournement = tournementOptional.get();
                LOGGER.info("The estimated duration of the tournement is {} minutes.", tournement.getEstimatedDuration());
            } else {
                LOGGER.warn("Tournement not found after calculation.");
            }
        } else {
            LOGGER.warn("Unable to calculate estimated duration for tournement.");
        }
    }

    private void changeTournementStatus() {
        LOGGER.info("Enter tournement ID: ");
        Long tournementId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        LOGGER.info("Choose the new status:");
        LOGGER.info("1. PLANNED");
        LOGGER.info("2. IN_PROGRESS");
        LOGGER.info("3. COMPLETED");
        LOGGER.info("4. CANCELED");

        int choice = -1;
        while (choice < 1 || choice > 4) {
            LOGGER.info("Enter a number between 1 and 4: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                LOGGER.error("Invalid input. Please enter a number.");
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
        LOGGER.info("Tournement status updated successfully.");
    }

}
