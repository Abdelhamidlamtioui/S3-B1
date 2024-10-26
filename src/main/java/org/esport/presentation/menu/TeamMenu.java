package org.esport.presentation.menu;

import org.esport.controller.TeamController;
import org.esport.controller.PlayerController;
import org.esport.model.Player;
import org.esport.model.Team;
import org.esport.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TeamMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamMenu.class);
    private final TeamController teamController;
    private final PlayerController playerController;
    private final Scanner scanner;

    public TeamMenu(TeamController teamController, PlayerController playerController, Scanner scanner) {
        this.teamController = teamController;
        this.playerController = playerController;
        this.scanner = scanner;
    }

    public void displayMenu() {
        int choice;
        do {
            LOGGER.info("Team Menu:");
            LOGGER.info("1. Create a team");
            LOGGER.info("2. Edit a team");
            LOGGER.info("3. Delete a team");
            LOGGER.info("4. Display a team");
            LOGGER.info("5. Display all teams");
            LOGGER.info("6. Add a player to a team");
            LOGGER.info("7. Remove a player from a team");
            LOGGER.info("0. Return to main menu");
            LOGGER.info("Choose an option: ");

            try {
                choice = ValidationUtil.readNonNegativeInt(scanner, "Choose an option:");
                switch (choice) {
                    case 1:
                        createTeam();
                        break;
                    case 2:
                        editTeam();
                        break;
                    case 3:
                        deleteTeam();
                        break;
                    case 4:
                        displayTeam();
                        break;
                    case 5:
                        displayAllTeams();
                        break;
                    case 6:
                        addPlayerToTeam();
                        break;
                    case 7:
                        removePlayerFromTeam();
                        break;
                    case 0:
                        // Do nothing, exit the loop
                        break;
                    default:
                        LOGGER.warn("Invalid option. Please try again.");
                }

            } catch (IllegalArgumentException e) {
                LOGGER.error("Error: {}", e.getMessage());
                choice = -1; // Ensures the loop continues in case of exception
            }
        } while (choice != 0);
    }

    private void createTeam() {
        LOGGER.info("Creating a new team");
        String name = ValidationUtil.readNonEmptyString(scanner, "Enter the team name:");

        try {
            Team createdTeam = teamController.createTeam(name);
            if (createdTeam != null) {
                LOGGER.info("Team created successfully. ID: {}", createdTeam.getId());
            } else {
                LOGGER.error("Error creating team.");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error: {}", e.getMessage());
        }
    }

    private void editTeam() {
        LOGGER.info("Editing a team");
        Long id = ValidationUtil.readPositiveLong(scanner, "Enter the ID of the team to edit:");
        String newName = ValidationUtil.readNonEmptyString(scanner, "Enter the new team name:");

        try {
            Team editedTeam = teamController.editTeam(id, newName);
            if (editedTeam != null) {
                LOGGER.info("Team edited successfully.");
            } else {
                LOGGER.error("Error editing team.");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error: {}", e.getMessage());
        }
    }

    private void deleteTeam() {
        LOGGER.info("Deleting a team");
        Long id = ValidationUtil.readPositiveLong(scanner, "Enter the ID of the team to delete:");

        try {
            teamController.deleteTeam(id);
            LOGGER.info("Team deleted successfully (if it existed).");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error: {}", e.getMessage());
        }
    }

    private void displayTeam() {
        LOGGER.info("Displaying a team");
        Long id = ValidationUtil.readPositiveLong(scanner, "Enter the ID of the team to display:");

        try {
            Optional<Team> optionalTeam = teamController.getTeam(id);
            if (optionalTeam.isPresent()) {
                Team team = optionalTeam.get();
                LOGGER.info("ID: {}", team.getId());
                LOGGER.info("Name: {}", team.getName());
                LOGGER.info("Ranking: {}", team.getRanking());
                LOGGER.info("Players:");
                for (Player player : team.getPlayers()) {
                    LOGGER.info("  - {}", player.getUsername());
                }
            } else {
                LOGGER.warn("Team not found.");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error: {}", e.getMessage());
        }
    }

    private void displayAllTeams() {
        LOGGER.info("List of all teams:");
        List<Team> teams = teamController.getAllTeams();
        if (!teams.isEmpty()) {
            for (Team team : teams) {
                LOGGER.info("ID: {}, Name: {}, Ranking: {}", team.getId(), team.getName(), team.getRanking());
            }
        } else {
            LOGGER.warn("No teams found.");
        }
    }

    private void addPlayerToTeam() {
        LOGGER.info("Adding a player to a team");
        Long teamId = ValidationUtil.readPositiveLong(scanner, "Enter the team ID:");
        Long playerId = ValidationUtil.readPositiveLong(scanner, "Enter the player ID to add:");

        try {
            teamController.addPlayerToTeam(teamId, playerId);
            LOGGER.info("Player added to team successfully (if both existed).");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error: {}", e.getMessage());
        }
    }

    private void removePlayerFromTeam() {
        LOGGER.info("Removing a player from a team");
        Long teamId = ValidationUtil.readPositiveLong(scanner, "Enter the team ID:");
        Long playerId = ValidationUtil.readPositiveLong(scanner, "Enter the player ID to remove:");

        try {
            teamController.removePlayerFromTeam(teamId, playerId);
            LOGGER.info("Player removed from team successfully (if both existed).");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error: {}", e.getMessage());
        }
    }
}
