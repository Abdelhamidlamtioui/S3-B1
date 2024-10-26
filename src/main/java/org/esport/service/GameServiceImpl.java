package org.esport.service;

import org.esport.dao.interfaces.GameDao;
import org.esport.model.Game;
import org.esport.service.interfaces.GameService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class GameServiceImpl implements GameService {

    private final GameDao gameDao;

    public GameServiceImpl(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public Game createGame(Game game) {
        System.out.println("Creating a new game");
        return gameDao.create(game);
    }

    @Override
    public Game editGame(Game game) {
        System.out.println("Editing game with ID: " + game.getId());
        return gameDao.edit(game);
    }

    @Override
    public void deleteGame(Long id) {
        System.out.println("Deleting game with ID: " + id);
        gameDao.delete(id);
    }

    @Override
    public Optional<Game> getGame(Long id) {
        System.out.println("Searching for game with ID: " + id);
        return gameDao.findById(id);
    }

    @Override
    public List<Game> getAllGames() {
        System.out.println("Retrieving all games");
        return gameDao.findAll();
    }
}
