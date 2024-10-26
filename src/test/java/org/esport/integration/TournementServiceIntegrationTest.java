package org.esport.integration;

import org.esport.dao.interfaces.TournementDao;
import org.esport.dao.interfaces.TeamDao;
import org.esport.model.Game;
import org.esport.model.Tournement;
import org.esport.model.Team;
import org.esport.service.TournementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.esport.model.enums.TournementStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TournementServiceIntegrationTest {

    @Mock
    private TournementDao tournementDao;

    @Mock
    private TeamDao teamDao;

    @InjectMocks
    private TournementServiceImpl tournementService;

    private Tournement testTournement;
    private Game testGame;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testGame = new Game("Test Game", 2, 30);
        testTournement = new Tournement("Test Tournement", testGame, LocalDate.now(), LocalDate.now().plusDays(7), 1000);
        testTournement.setPauseTimeBetweenMatches(15);
        testTournement.setCeremonyTime(60);

        Team team1 = new Team("Team 1");
        Team team2 = new Team("Team 2");
        testTournement.setTeams(Arrays.asList(team1, team2));
    }

    @Test
    void testCreateTournement() {
        when(tournementDao.create(any(Tournement.class))).thenReturn(testTournement);
        Tournement createdTournement = tournementService.createTournement(testTournement);
        assertNotNull(createdTournement);
        assertEquals(testTournement.getTitle(), createdTournement.getTitle());
        verify(tournementDao, times(1)).create(any(Tournement.class));
    }

    @Test
    void testEditTournement() {
        when(tournementDao.edit(any(Tournement.class))).thenReturn(testTournement);
        testTournement.setTitle("Updated Tournement");
        Tournement updatedTournement = tournementService.editTournement(testTournement);
        assertNotNull(updatedTournement);
        assertEquals("Updated Tournement", updatedTournement.getTitle());
        verify(tournementDao, times(1)).edit(any(Tournement.class));
    }

    @Test
    void testDeleteTournement() {
        doNothing().when(tournementDao).delete(anyLong());
        tournementService.deleteTournement(1L);
        verify(tournementDao, times(1)).delete(1L);
    }

    @Test
    void testGetTournement() {
        Long tournementId = 1L;
        when(tournementDao.findByIdWithTeams(tournementId)).thenReturn(Optional.of(testTournement));

        Optional<Tournement> retrievedTournement = tournementService.getTournement(tournementId);

        assertTrue(retrievedTournement.isPresent(), "Tournement should be present");
        assertEquals(testTournement.getTitle(), retrievedTournement.get().getTitle(),
                "Tournement titles should match");
        verify(tournementDao, times(1)).findByIdWithTeams(tournementId);
    }

    @Test
    void testGetAllTournements() {
        when(tournementDao.findAll()).thenReturn(Arrays.asList(testTournement));
        List<Tournement> tournements = tournementService.getAllTournements();
        assertEquals(1, tournements.size());
        assertEquals(testTournement.getTitle(), tournements.get(0).getTitle());
    }

    @Test
    void testAddTeam() {
        Team team = new Team("Test Team");
        when(teamDao.findById(anyLong())).thenReturn(Optional.of(team));
        doNothing().when(tournementDao).addTeam(anyLong(), any(Team.class));
        tournementService.addTeam(1L, 2L);
        verify(tournementDao, times(1)).addTeam(eq(1L), any(Team.class));
    }

    @Test
    void testRemoveTeam() {
        Team team = new Team("Test Team");
        when(teamDao.findById(anyLong())).thenReturn(Optional.of(team));
        doNothing().when(tournementDao).removeTeam(anyLong(), any(Team.class));
        tournementService.removeTeam(1L, 2L);
        verify(tournementDao, times(1)).removeTeam(eq(1L), any(Team.class));
    }

    @Test
    void testCalculateEstimatedTournementDuration() {
        when(tournementDao.calculateEstimatedDuration(anyLong())).thenReturn(135);
        int estimatedDuration = tournementService.calculateEstimatedTournementDuration(1L);
        assertEquals(135, estimatedDuration);
    }

    @Test
    void testUpdateTournementStatus() {
        doNothing().when(tournementDao).updateStatus(anyLong(), any(TournementStatus.class));
        tournementService.updateTournementStatus(1L, TournementStatus.IN_PROGRESS);
        verify(tournementDao, times(1)).updateStatus(1L, TournementStatus.IN_PROGRESS);
    }
}
