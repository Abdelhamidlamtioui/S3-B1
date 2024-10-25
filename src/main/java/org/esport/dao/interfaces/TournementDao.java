package org.esport.dao.interfaces;

import org.esport.model.Tournement;
import org.esport.model.Team;
import java.util.List;
import java.util.Optional;
import org.esport.model.enums.TournementStatus;

public interface TournementDao {
    Tournement create(Tournement tournement);

    Tournement edit(Tournement tournement);

    void delete(Long id);

    Optional<Tournement> findById(Long id);

    List<Tournement> findAll();

    void addTeam(Long tournementId, Team team);

    void removeTeam(Long tournementId, Team team);

    int calculateEstimatedDuration(Long tournementId);

    Optional<Tournement> findByIdWithTeams(Long id);

    void updateStatus(Long tournementId, TournementStatus newStatus);
}
