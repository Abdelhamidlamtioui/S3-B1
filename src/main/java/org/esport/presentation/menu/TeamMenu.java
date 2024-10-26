package org.esport.presentation.menu;

import org.esport.controller.TeamController;
import org.esport.controller.PlayerController;
import org.esport.model.Player;
import org.esport.model.Team;
import org.esport.util.ValidationUtil;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TeamMenu {
    private final TeamController teamController;
    private final PlayerController playerController;
    private final Scanner scanner;

    public TeamMenu(TeamController teamController, PlayerController playerController, Scanner scanner) {
        this.teamController = teamController;
        this.playerController = playerController;
        this.scanner = scanner;
    }

    public void displayMenu() {
        int choice = -1;
        do {
            System.out.println("Team Menu:");
            System.out.println("1. Create a team");
            System.out.println("2. Edit a team");
            System.out.println("3. Delete a team");
            System.out.println("4. Display a team");
            System.out.println("5. Display all teams");
            System.out.println("6. Add a player to a team");
            System.out.println("7. Remove a player from a team");
            System.out.println("0. Return to main menu");
            System.out.print("Choose an option: ");

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
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private void createTeam() {
        System.out.println("Creating a new team");
        String name = ValidationUtil.readNonEmptyString(scanner, "Enter the team name:");

        try {
            Team createdTeam = teamController.createTeam(name);
            if (createdTeam != null) {
                System.out.println("Team created successfully. ID: " + createdTeam.getId());
            } else {
                System.out.println("Error creating team.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editTeam() {
        System.out.println("Editing a team");
        Long id = ValidationUtil.readPositiveLong(scanner, "Enter the ID of the team to edit:");
        String newName = ValidationUtil.readNonEmptyString(scanner, "Enter the new team name:");

        try {
            Team editedTeam = teamController.editTeam(id, newName);
            if (editedTeam != null) {
                System.out.println("Team edited successfully.");
            } else {
                System.out.println("Error editing team.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteTeam() {
        System.out.println("Deleting a team");
        Long id = ValidationUtil.readPositiveLong(scanner, "Enter the ID of the team to delete:");

        try {
            teamController.deleteTeam(id);
            System.out.println("Team deleted successfully (if it existed).");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayTeam() {
        System.out.println("Displaying a team");
        Long id = ValidationUtil.readPositiveLong(scanner, "Enter the ID of the team to display:");

        try {
            Optional<Team> optionalTeam = teamController.getTeam(id);
            if (optionalTeam.isPresent()) {
                Team team = optionalTeam.get();
                System.out.println("ID: " + team.getId());
                System.out.println("Name: " + team.getName());
                System.out.println("Ranking: " + team.getRanking());
                System.out.println("Players:");
                for (Player player : team.getPlayers()) {
                    System.out.println("  - " + player.getUsername());
                }
            } else {
                System.out.println("Team not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayAllTeams() {
        System.out.println("List of all teams:");
        List<Team> teams = teamController.getAllTeams();
        if (!teams.isEmpty()) {
            for (Team team : teams) {
                System.out.println("ID: " + team.getId() + ", Name: " + team.getName() + ", Ranking: " + team.getRanking());
            }
        } else {
            System.out.println("No teams found.");
        }
    }

    private void addPlayerToTeam() {
        System.out.println("Adding a player to a team");
        Long teamId = ValidationUtil.readPositiveLong(scanner, "Enter the team ID:");
        Long playerId = ValidationUtil.readPositiveLong(scanner, "Enter the player ID to add:");

        try {
            teamController.addPlayerToTeam(teamId, playerId);
            System.out.println("Player added to team successfully (if both existed).");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void removePlayerFromTeam() {
        System.out.println("Removing a player from a team");
        Long teamId = ValidationUtil.readPositiveLong(scanner, "Enter the team ID:");
        Long playerId = ValidationUtil.readPositiveLong(scanner, "Enter the player ID to remove:");

        try {
            teamController.removePlayerFromTeam(teamId, playerId);
            System.out.println("Player removed from team successfully (if both existed).");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
