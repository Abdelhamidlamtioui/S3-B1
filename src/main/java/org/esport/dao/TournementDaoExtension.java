package org.esport.dao;

import org.esport.dao.interfaces.TournementDao;
import org.esport.model.Tournement;
import org.esport.model.Team;
import org.esport.model.Game;
import org.esport.model.enums.TournementStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class TournementDaoExtension implements TournementDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final TournementDaoImpl tournementDaoImpl;

    public TournementDaoExtension(TournementDaoImpl tournementDaoImpl) {
        this.tournementDaoImpl = tournementDaoImpl;
    }

    @Override
    public Tournement create(Tournement tournement) {
        return tournementDaoImpl.create(tournement);
    }

    @Override
    public Tournement edit(Tournement tournement) {
        return tournementDaoImpl.edit(tournement);
    }

    @Override
    public void delete(Long id) {
        tournementDaoImpl.delete(id);
    }

    @Override
    public Optional<Tournement> findById(Long id) {
        return tournementDaoImpl.findById(id);
    }

    @Override
    public List<Tournement> findAll() {
        return tournementDaoImpl.findAll();
    }

    @Override
    public void addTeam(Long tournementId, Team team) {
        tournementDaoImpl.addTeam(tournementId, team);
    }

    @Override
    public void removeTeam(Long tournementId, Team team) {
        tournementDaoImpl.removeTeam(tournementId, team);
    }

    @Override
    public Optional<Tournement> findByIdWithTeams(Long id) {
        return tournementDaoImpl.findByIdWithTeams(id);
    }

    @Override
    public int calculateEstimatedDuration(Long tournementId) {
        Tournement tournement = entityManager.find(Tournement.class, tournementId);
        if (tournement != null) {
            int teamCount = tournement.getTeams().size();
            Game game = tournement.getGame();
            int averageMatchDuration = game.getAverageMatchDuration();
            int gameDifficulty = game.getDifficulty();
            int pauseTimeBetweenMatches = tournement.getPauseTimeBetweenMatches();
            int ceremonyTime = tournement.getCeremonyTime();

            int estimatedDuration = (teamCount * averageMatchDuration * gameDifficulty) + pauseTimeBetweenMatches + ceremonyTime;
            tournement.setEstimatedDuration(estimatedDuration);
            entityManager.merge(tournement);
            return estimatedDuration;
        }
        return 0;
    }

    @Override
    public void updateStatus(Long tournementId, TournementStatus newStatus) {
        tournementDaoImpl.updateStatus(tournementId, newStatus);
    }
}
