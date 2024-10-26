package org.esport.service;

import org.esport.dao.interfaces.TeamDao;
import org.esport.dao.interfaces.TournementDao;
import org.esport.model.Tournement;
import org.esport.model.Team;
import org.esport.service.interfaces.TournementService;
import org.springframework.transaction.annotation.Transactional;
import org.esport.model.enums.TournementStatus;

import java.util.List;
import java.util.Optional;

@Transactional
public class TournementServiceImpl implements TournementService {

    private final TournementDao tournementDao;
    private final TeamDao teamDao;

    public TournementServiceImpl(TournementDao tournementDao, TeamDao teamDao) {
        this.tournementDao = tournementDao;
        this.teamDao = teamDao;
    }

    @Override
    public Tournement createTournement(Tournement tournement) {
        System.out.println("Creating a new tournement");
        return tournementDao.create(tournement);
    }

    @Override
    public Tournement editTournement(Tournement tournement) {
        System.out.println("Editing tournement with ID: " + tournement.getId());
        return tournementDao.edit(tournement);
    }

    @Override
    public void deleteTournement(Long id) {
        System.out.println("Deleting tournement with ID: " + id);
        tournementDao.delete(id);
    }

    @Override
    public Optional<Tournement> getTournement(Long id) {
        System.out.println("Searching for tournement with ID: " + id);
        return tournementDao.findByIdWithTeams(id);
    }

    @Override
    public List<Tournement> getAllTournements() {
        System.out.println("Retrieving all tournements");
        return tournementDao.findAll();
    }

    @Override
    public void addTeam(Long tournementId, Long teamId) {
        System.out.println("Adding team " + teamId + " to tournement " + tournementId);
        Optional<Team> team = teamDao.findById(teamId);
        if (team.isPresent()) {
            tournementDao.addTeam(tournementId, team.get());
        } else {
            System.out.println("Team with ID " + teamId + " not found");
        }
    }

    @Override
    public void removeTeam(Long tournementId, Long teamId) {
        System.out.println("Removing team " + teamId + " from tournement " + tournementId);
        Optional<Team> team = teamDao.findById(teamId);
        if (team.isPresent()) {
            tournementDao.removeTeam(tournementId, team.get());
        } else {
            System.out.println("Team with ID " + teamId + " not found");
        }
    }

    @Override
    public int calculateEstimatedTournementDuration(Long tournementId) {
        System.out.println("Calculating estimated duration for tournement with ID: " + tournementId);
        return tournementDao.calculateEstimatedDuration(tournementId);
    }

    @Override
    @Transactional
    public void updateTournementStatus(Long tournementId, TournementStatus newStatus) {
        System.out.println("Updating tournement status " + tournementId + " to " + newStatus);
        tournementDao.updateStatus(tournementId, newStatus);
    }
}
