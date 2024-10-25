package org.esport.service.interfaces;

import org.esport.model.Game;
import java.util.List;
import java.util.Optional;

public interface GameService {
    Game createGame(Game game);

    Game editGame(Game game);

    void deleteGame(Long id);

    Optional<Game> getGame(Long id);

    List<Game> getAllGames();
}
