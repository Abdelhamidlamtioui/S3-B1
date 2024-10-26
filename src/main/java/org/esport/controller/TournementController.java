package org.esport.controller;

import org.esport.model.Game;
import org.esport.model.Tournement;
import org.esport.service.interfaces.GameService;
import org.esport.service.interfaces.TournementService;

import java.time.LocalDate;
import org.esport.model.enums.TournementStatus;
import java.util.List;
import java.util.Optional;
import org.esport.util.ValidationUtil;

public class TournementController {
    private final TournementService tournementService;
    private final GameService gameService;

    public TournementController(TournementService tournementService, GameService gameService) {
        this.tournementService = tournementService;
        this.gameService = gameService;
    }

    public Tournement createTournement(String title, Long gameId, LocalDate startDate, LocalDate endDate,
                                       int spectatorCount, int averageMatchDuration, int ceremonyTime, int pauseTimeBetweenMatches) {
        System.out.println("Attempting to create a new tournement: " + title);

        // Validate inputs
        ValidationUtil.validateTitle(title);
        ValidationUtil.validateTournamentId(gameId);
        ValidationUtil.validateDates(startDate, endDate);
        ValidationUtil.validateSpectatorCount(spectatorCount);
        ValidationUtil.validateTime(averageMatchDuration);
        ValidationUtil.validateTime(ceremonyTime);
        ValidationUtil.validateTime(pauseTimeBetweenMatches);

        Tournement tournement = new Tournement();
        tournement.setTitle(title);

        Game game = gameService.getGame(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found for ID: " + gameId));
        tournement.setGame(game);

        tournement.setStartDate(startDate);
        tournement.setEndDate(endDate);
        tournement.setSpectatorCount(spectatorCount);
        tournement.setAverageMatchDuration(averageMatchDuration);
        tournement.setCeremonyTime(ceremonyTime);
        tournement.setPauseTimeBetweenMatches(pauseTimeBetweenMatches);

        // Save the tournement first to get an ID
        Tournement savedTournement = tournementService.createTournement(tournement);

        // Calculate estimated duration using the method from TournementDaoExtension
        int estimatedDuration = tournementService.calculateEstimatedTournementDuration(savedTournement.getId());
        savedTournement.setEstimatedDuration(estimatedDuration);

        // Update the tournement with the calculated estimated duration
        return tournementService.editTournement(savedTournement);
    }

    public Tournement editTournement(Long id, String newTitle, LocalDate newStartDate,
                                     LocalDate newEndDate, int newSpectatorCount) {
        System.out.println("Attempting to edit tournement with ID: " + id);

        // Validate inputs
        ValidationUtil.validateTournamentId(id);
        ValidationUtil.validateTitle(newTitle);
        ValidationUtil.validateDates(newStartDate, newEndDate);
        ValidationUtil.validateSpectatorCount(newSpectatorCount);

        Optional<Tournement> tournementOptional = tournementService.getTournement(id);
        if (tournementOptional.isPresent()) {
            Tournement tournement = tournementOptional.get();
            tournement.setTitle(newTitle);
            tournement.setStartDate(newStartDate);
            tournement.setEndDate(newEndDate);
            tournement.setSpectatorCount(newSpectatorCount);
            return tournementService.editTournement(tournement);
        } else {
            System.out.println("Tournement with ID " + id + " not found");
            return null;
        }
    }

    public void deleteTournement(Long id) {
        System.out.println("Attempting to delete tournement with ID: " + id);
        ValidationUtil.validateTournamentId(id);
        tournementService.deleteTournement(id);
    }

    public Optional<Tournement> getTournement(Long id) {
        System.out.println("Attempting to get tournement with ID: " + id);
        ValidationUtil.validateTournamentId(id);
        return tournementService.getTournement(id);
    }

    public List<Tournement> getAllTournements() {
        System.out.println("Attempting to get all tournements");
        return tournementService.getAllTournements();
    }

    public void addTeamToTournement(Long tournementId, Long teamId) {
        System.out.println("Attempting to add team " + teamId + " to tournement " + tournementId);
        ValidationUtil.validateTournamentId(tournementId);
        ValidationUtil.validateTournamentId(teamId);
        tournementService.addTeam(tournementId, teamId);
    }

    public void removeTeamFromTournement(Long tournementId, Long teamId) {
        System.out.println("Attempting to remove team " + teamId + " from tournement " + tournementId);
        ValidationUtil.validateTournamentId(tournementId);
        ValidationUtil.validateTournamentId(teamId);
        tournementService.removeTeam(tournementId, teamId);
    }

    public int getEstimatedTournementDuration(Long tournementId) {
        System.out.println("Attempting to get estimated duration of tournement with ID: " + tournementId);
        ValidationUtil.validateTournamentId(tournementId);
        return tournementService.calculateEstimatedTournementDuration(tournementId);
    }

    public void updateTournementStatus(Long tournementId, TournementStatus newStatus) {
        System.out.println("Attempting to update tournement status " + tournementId + " to " + newStatus);
        ValidationUtil.validateTournamentId(tournementId);
        ValidationUtil.validateStatus(newStatus);
        tournementService.updateTournementStatus(tournementId, newStatus);
    }
}
