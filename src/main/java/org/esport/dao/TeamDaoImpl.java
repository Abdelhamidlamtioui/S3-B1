package org.esport.dao;

import org.esport.dao.interfaces.TeamDao;
import org.esport.model.Player;
import org.esport.model.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TeamDaoImpl implements TeamDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Team create(Team team) {
        entityManager.persist(team);
        System.out.println("Team created with ID: " + team.getId());
        return team;
    }

    @Override
    public Team edit(Team team) {
        Team editedTeam = entityManager.merge(team);
        System.out.println("Team edited with ID: " + editedTeam.getId());
        return editedTeam;
    }

    @Override
    public void delete(Long id) {
        Team team = entityManager.find(Team.class, id);
        if (team != null) {
            entityManager.remove(team);
            System.out.println("Team deleted with ID: " + id);
        } else {
            System.out.println("Attempted to delete a non-existent team with ID: " + id);
        }
    }

    @Override
    public Optional<Team> findById(Long id) {
        Team team = entityManager.find(Team.class, id);
        return Optional.ofNullable(team);
    }

    @Override
    public List<Team> findAll() {
        TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t", Team.class);
        return query.getResultList();
    }

    @Override
    public void addPlayer(Long teamId, Player player) {
        Team team = entityManager.find(Team.class, teamId);
        if (team != null) {
            team.getPlayers().add(player);
            player.setTeam(team);
            entityManager.merge(team);
            System.out.println("Player added to team with ID: " + teamId);
        } else {
            System.out.println("Attempted to add a player to a non-existent team with ID: " + teamId);
        }
    }

    @Override
    public void removePlayer(Long teamId, Player player) {
        Team team = entityManager.find(Team.class, teamId);
        if (team != null) {
            team.getPlayers().remove(player);
            player.setTeam(null);
            entityManager.merge(team);
            System.out.println("Player removed from team with ID: " + teamId);
        } else {
            System.out.println("Attempted to remove a player from a non-existent team with ID: " + teamId);
        }
    }

    @Override
    public List<Team> findByTournement(Long tournementId) {
        TypedQuery<Team> query = entityManager.createQuery(
                "SELECT t FROM Team t JOIN t.tournements tr WHERE tr.id = :tournementId", Team.class);
        query.setParameter("tournementId", tournementId);
        return query.getResultList();
    }
}
