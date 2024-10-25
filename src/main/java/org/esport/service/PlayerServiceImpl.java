package org.esport.service;

import org.esport.dao.interfaces.PlayerDao;
import org.esport.model.Player;
import org.esport.service.interfaces.PlayerService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerDao playerDao;

    public PlayerServiceImpl(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Player registerPlayer(Player player) {
        System.out.println("Registering a new player");
        return playerDao.register(player);
    }

    @Override
    public Player editPlayer(Player player) {
        System.out.println("Editing player with ID: " + player.getId());
        return playerDao.edit(player);
    }

    @Override
    public void deletePlayer(Long id) {
        System.out.println("Deleting player with ID: " + id);
        playerDao.delete(id);
    }

    @Override
    public Optional<Player> getPlayer(Long id) {
        System.out.println("Searching for player with ID: " + id);
        return playerDao.findById(id);
    }

    @Override
    public List<Player> getAllPlayers() {
        System.out.println("Retrieving all players");
        return playerDao.findAll();
    }

    @Override
    public List<Player> getPlayersByTeam(Long teamId) {
        System.out.println("Retrieving players for team with ID: " + teamId);
        return playerDao.findByTeam(teamId);
    }

    @Override
    public boolean existsByUsername(String username) {
        System.out.println("Checking if player exists with username: " + username);
        return playerDao.existsByUsername(username);
    }

    @Override
    public Optional<Player> findByUsername(String username) {
        System.out.println("Searching for player with username: " + username);
        return playerDao.findByUsername(username);
    }
}
