package org.esport.dao;

import org.esport.dao.interfaces.PlayerDao;
import org.esport.model.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class PlayerDaoImpl implements PlayerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Player register(Player player) {
        if (entityManager == null) {
            System.out.println("EntityManager is null");
            throw new IllegalStateException("EntityManager is not initialized");
        }
        entityManager.persist(player);
        System.out.println("Player registered with ID: " + player.getId());
        return player;
    }

    @Override
    public Player edit(Player player) {
        Player editedPlayer = entityManager.merge(player);
        System.out.println("Player edited with ID: " + editedPlayer.getId());
        return editedPlayer;
    }

    @Override
    public void delete(Long id) {
        Player player = entityManager.find(Player.class, id);
        if (player != null) {
            entityManager.remove(player);
            System.out.println("Player deleted with ID: " + id);
        } else {
            System.out.println("Attempted to delete a non-existent player with ID: " + id);
        }
    }

    @Override
    public Optional<Player> findById(Long id) {
        Player player = entityManager.find(Player.class, id);
        return Optional.ofNullable(player);
    }

    @Override
    public List<Player> findAll() {
        TypedQuery<Player> query = entityManager.createQuery("SELECT p FROM Player p", Player.class);
        return query.getResultList();
    }

    @Override
    public List<Player> findByTeam(Long teamId) {
        TypedQuery<Player> query = entityManager.createQuery(
                "SELECT p FROM Player p WHERE p.team.id = :teamId", Player.class);
        query.setParameter("teamId", teamId);
        return query.getResultList();
    }

    @Override
    public boolean existsByUsername(String username) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(p) FROM Player p WHERE p.username = :username", Long.class);
        query.setParameter("username", username);
        return query.getSingleResult() > 0;
    }

    @Override
    public Optional<Player> findByUsername(String username) {
        TypedQuery<Player> query = entityManager.createQuery(
                "SELECT p FROM Player p WHERE p.username = :username", Player.class);
        query.setParameter("username", username);
        List<Player> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
