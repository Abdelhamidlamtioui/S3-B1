package org.esport.service.interfaces;

import org.esport.model.Tournement;

import java.util.List;
import java.util.Optional;
import org.esport.model.enums.TournementStatus;

public interface TournementService {
    Tournement createTournement(Tournement tournement);

    Tournement editTournement(Tournement tournement);

    void deleteTournement(Long id);

    Optional<Tournement> getTournement(Long id);

    List<Tournement> getAllTournements();

    void addTeam(Long tournementId, Long teamId);

    void removeTeam(Long tournementId, Long teamId);

    int calculateEstimatedTournementDuration(Long tournementId);

    void updateTournementStatus(Long tournementId, TournementStatus newStatus);
}
