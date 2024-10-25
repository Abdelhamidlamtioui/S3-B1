package org.esport.service.interfaces;

import org.esport.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    Player registerPlayer(Player player);

    Player editPlayer(Player player);

    void deletePlayer(Long id);

    Optional<Player> getPlayer(Long id);

    List<Player> getAllPlayers();

    List<Player> getPlayersByTeam(Long teamId);

    boolean existsByUsername(String username);

    Optional<Player> findByUsername(String username);
}
