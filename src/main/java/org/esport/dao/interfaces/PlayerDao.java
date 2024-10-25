package org.esport.dao.interfaces;

import org.esport.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerDao {
    Player register(Player player);

    Player edit(Player player);

    void delete(Long id);

    Optional<Player> findById(Long id);

    List<Player> findAll();

    List<Player> findByTeam(Long teamId);

    boolean existsByUsername(String username);

    Optional<Player> findByUsername(String username);
}
