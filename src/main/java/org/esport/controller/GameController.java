package org.esport.controller;

import org.esport.model.Game;
import org.esport.service.interfaces.GameService;

import java.util.List;
import java.util.Optional;

public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    public Game createGame(String name, int difficulty, int averageMatchDuration) {
        System.out.println("Attempting to create a new game: " + name);
        Game game = new Game();
        game.setName(name);
        game.setDifficulty(difficulty);
        game.setAverageMatchDuration(averageMatchDuration);
        return gameService.createGame(game);
    }

    public Game editGame(Long id, String newName, int newDifficulty, int newAverageMatchDuration) {
        System.out.println("Attempting to edit game with ID: " + id);
        Optional<Game> gameOptional = gameService.getGame(id);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            game.setName(newName);
            game.setDifficulty(newDifficulty);
            game.setAverageMatchDuration(newAverageMatchDuration);
            return gameService.editGame(game);
        } else {
            System.out.println("Game with ID " + id + " not found");
            return null;
        }
    }

    public void deleteGame(Long id) {
        System.out.println("Attempting to delete game with ID: " + id);
        gameService.deleteGame(id);
    }

    public Optional<Game> getGame(Long id) {
        System.out.println("Attempting to get game with ID: " + id);
        return gameService.getGame(id);
    }

    public List<Game> getAllGames() {
        System.out.println("Attempting to get all games");
        return gameService.getAllGames();
    }
}
