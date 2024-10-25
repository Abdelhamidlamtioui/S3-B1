package org.esport.service.interfaces;

import org.esport.model.Team;
import java.util.List;
import java.util.Optional;

public interface TeamService {
    Team createTeam(Team team);

    Team editTeam(Team team);

    void deleteTeam(Long id);

    Optional<Team> getTeam(Long id);

    List<Team> getAllTeams();

    void addPlayer(Long teamId, Long playerId);

    void removePlayer(Long teamId, Long playerId);

    List<Team> getTeamsByTournement(Long tournementId);
}
