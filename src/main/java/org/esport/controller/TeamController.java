package org.esport.controller;

import org.esport.model.Team;
import org.esport.service.interfaces.TeamService;

import java.util.List;
import java.util.Optional;

public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    public Team createTeam(String name) {
        System.out.println("Attempting to create a new team: " + name);
        Team team = new Team();
        team.setName(name);
        return teamService.createTeam(team);
    }

    public Team editTeam(Long id, String newName) {
        System.out.println("Attempting to edit team with ID: " + id);
        Optional<Team> teamOptional = teamService.getTeam(id);
        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            team.setName(newName);
            return teamService.editTeam(team);
        } else {
            System.out.println("Team with ID " + id + " not found");
            return null;
        }
    }

    public void deleteTeam(Long id) {
        System.out.println("Attempting to delete team with ID: " + id);
        teamService.deleteTeam(id);
    }

    public Optional<Team> getTeam(Long id) {
        System.out.println("Attempting to get team with ID: " + id);
        return teamService.getTeam(id);
    }

    public List<Team> getAllTeams() {
        System.out.println("Attempting to get all teams");
        return teamService.getAllTeams();
    }

    public void addPlayerToTeam(Long teamId, Long playerId) {
        System.out.println("Attempting to add player " + playerId + " to team " + teamId);
        teamService.addPlayer(teamId, playerId);
    }

    public void removePlayerFromTeam(Long teamId, Long playerId) {
        System.out.println("Attempting to remove player " + playerId + " from team " + teamId);
        teamService.removePlayer(teamId, playerId);
    }

    public List<Team> getTeamsByTournament(Long tournamentId) {
        System.out.println("Attempting to get teams for tournament with ID: " + tournamentId);
        return teamService.getTeamsByTournement(tournamentId);
    }
}
