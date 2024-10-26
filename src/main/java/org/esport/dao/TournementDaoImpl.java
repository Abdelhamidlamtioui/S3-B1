package org.esport.dao;

import org.esport.dao.interfaces.TournementDao;
import org.esport.model.Tournement;
import org.esport.model.Team;
import org.esport.model.Game;
import org.esport.model.enums.TournementStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TournementDaoImpl implements TournementDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tournement create(Tournement tournement) {
        entityManager.persist(tournement);
        System.out.println("Tournement created with ID: " + tournement.getId());
        return tournement;
    }

    @Override
    public Tournement edit(Tournement tournement) {
        Tournement editedTournement = entityManager.merge(tournement);
        System.out.println("Tournement edited with ID: " + editedTournement.getId());
        return editedTournement;
    }

    @Override
    public void delete(Long id) {
        Tournement tournement = entityManager.find(Tournement.class, id);
        if (tournement != null) {
            entityManager.remove(tournement);
            System.out.println("Tournement deleted with ID: " + id);
        } else {
            System.out.println("Attempted to delete a non-existent tournement with ID: " + id);
        }
    }

    @Override
    public Optional<Tournement> findById(Long id) {
        Tournement tournement = entityManager.find(Tournement.class, id);
        return Optional.ofNullable(tournement);
    }

    @Override
    public List<Tournement> findAll() {
        TypedQuery<Tournement> query = entityManager.createQuery("SELECT t FROM Tournement t", Tournement.class);
        return query.getResultList();
    }

    @Override
    public void addTeam(Long tournementId, Team team) {
        Tournement tournement = entityManager.find(Tournement.class, tournementId);
        if (tournement != null) {
            tournement.getTeams().add(team);
            entityManager.merge(tournement);
            System.out.println("Team added to tournement with ID: " + tournementId);
        } else {
            System.out.println("Attempted to add a team to a non-existent tournement with ID: " + tournementId);
        }
    }

    @Override
    public void removeTeam(Long tournementId, Team team) {
        Tournement tournement = entityManager.find(Tournement.class, tournementId);
        if (tournement != null) {
            tournement.getTeams().remove(team);
            entityManager.merge(tournement);
            System.out.println("Team removed from tournement with ID: " + tournementId);
        } else {
            System.out.println("Attempted to remove a team from a non-existent tournement with ID: " + tournementId);
        }
    }

    @Override
    public int calculateEstimatedDuration(Long tournementId) {
        Tournement tournement = entityManager.find(Tournement.class, tournementId);
        if (tournement != null) {
            int teamCount = tournement.getTeams().size();
            Game game = tournement.getGame();
            int averageMatchDuration = game.getAverageMatchDuration();
            int pauseTimeBetweenMatches = tournement.getPauseTimeBetweenMatches();

            int estimatedDuration = (teamCount * averageMatchDuration) + pauseTimeBetweenMatches;
            tournement.setEstimatedDuration(estimatedDuration);
            entityManager.merge(tournement);
            return estimatedDuration;
        }
        return 0;
    }

    @Override
    public Optional<Tournement> findByIdWithTeams(Long id) {
        TypedQuery<Tournement> query = entityManager.createQuery(
                "SELECT DISTINCT t FROM Tournement t LEFT JOIN FETCH t.teams WHERE t.id = :id", Tournement.class);
        query.setParameter("id", id);
        List<Tournement> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public void updateStatus(Long tournementId, TournementStatus newStatus) {
        Tournement tournement = entityManager.find(Tournement.class, tournementId);
        if (tournement != null) {
            tournement.setStatus(newStatus);
            entityManager.merge(tournement);
            System.out.println("Status of tournement with ID " + tournementId + " updated to " + newStatus);
        } else {
            System.out.println("Attempted to update status of a non-existent tournement with ID: " + tournementId);
        }
    }
}
