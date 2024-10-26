package org.esport.dao;

import org.esport.dao.interfaces.GameDao;
import org.esport.model.Game;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class GameDaoImpl implements GameDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Game create(Game game) {
        entityManager.persist(game);
        System.out.println("Game created with ID: " + game.getId());
        return game;
    }

    @Override
    public Game edit(Game game) {
        Game editedGame = entityManager.merge(game);
        System.out.println("Game edited with ID: " + editedGame.getId());
        return editedGame;
    }

    @Override
    public void delete(Long id) {
        Game game = entityManager.find(Game.class, id);
        if (game != null) {
            entityManager.remove(game);
            System.out.println("Game deleted with ID: " + id);
        } else {
            System.out.println("Attempted to delete a non-existent game with ID: " + id);
        }
    }

    @Override
    public Optional<Game> findById(Long id) {
        Game game = entityManager.find(Game.class, id);
        return Optional.ofNullable(game);
    }

    @Override
    public List<Game> findAll() {
        TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g", Game.class);
        return query.getResultList();
    }
}
