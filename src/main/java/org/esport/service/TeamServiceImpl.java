package org.esport.service;

import org.esport.dao.interfaces.TeamDao;
import org.esport.dao.interfaces.PlayerDao;
import org.esport.model.Player;
import org.esport.model.Team;
import org.esport.service.interfaces.TeamService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {

    private final TeamDao teamDao;
    private final PlayerDao playerDao;

    public TeamServiceImpl(TeamDao teamDao, PlayerDao playerDao) {
        this.teamDao = teamDao;
        this.playerDao = playerDao;
    }

    @Override
    @Transactional
    public Team createTeam(Team team) {
        System.out.println("Creating a new team");
        return teamDao.create(team);
    }

    @Override
    @Transactional
    public Team editTeam(Team team) {
        System.out.println("Editing team with ID: " + team.getId());
        return teamDao.edit(team);
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        System.out.println("Deleting team with ID: " + id);
        teamDao.delete(id);
    }

    @Override
    public Optional<Team> getTeam(Long id) {
        System.out.println("Searching for team with ID: " + id);
        return teamDao.findById(id);
    }

    @Override
    public List<Team> getAllTeams() {
        System.out.println("Retrieving all teams");
        return teamDao.findAll();
    }

    @Override
    @Transactional
    public void addPlayer(Long teamId, Long playerId) {
        System.out.println("Adding player " + playerId + " to team " + teamId);
        Optional<Team> teamOptional = teamDao.findById(teamId);
        Optional<Player> playerOptional = playerDao.findById(playerId);
        if (teamOptional.isPresent() && playerOptional.isPresent()) {
            Team team = teamOptional.get();
            Player player = playerOptional.get();
            team.getPlayers().add(player);
            player.setTeam(team);
            teamDao.edit(team);
        } else {
            System.out.println("Team with ID " + teamId + " or Player with ID " + playerId + " not found");
        }
    }

    @Override
    @Transactional
    public void removePlayer(Long teamId, Long playerId) {
        System.out.println("Removing player " + playerId + " from team " + teamId);
        Optional<Player> player = playerDao.findById(playerId);
        if (player.isPresent()) {
            teamDao.removePlayer(teamId, player.get());
        } else {
            System.out.println("Player with ID " + playerId + " not found");
        }
    }

    @Override
    public List<Team> getTeamsByTournement(Long tournementId) {
        System.out.println("Retrieving teams for tournement with ID: " + tournementId);
        return teamDao.findByTournement(tournementId);
    }
}
