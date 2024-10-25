package org.esport.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.esport.model.enums.TournementStatus;

@Entity
@Table(name = "tournements")
public class Tournement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Min(value = 0)
    private int spectatorCount;

    @ManyToMany
    @JoinTable(name = "tournement_team", joinColumns = @JoinColumn(name = "tournement_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams = new ArrayList<>();

    @Column(nullable = false)
    private int estimatedDuration = 0;

    @Column(nullable = false)
    private int averageMatchDuration = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TournementStatus status = TournementStatus.PLANNED;

    @Column(nullable = false)
    private int ceremonyTime = 0;

    @Column(nullable = false)
    private int pauseTimeBetweenMatches = 0;

    // Constructors
    public Tournement() {
    }

    public Tournement(String title, Game game, LocalDate startDate, LocalDate endDate, int averageMatchDuration) {
        this.title = title;
        this.game = game;
        this.startDate = startDate;
        this.endDate = endDate;
        this.averageMatchDuration = averageMatchDuration;
        this.status = TournementStatus.PLANNED;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getSpectatorCount() {
        return spectatorCount;
    }

    public void setSpectatorCount(int spectatorCount) {
        this.spectatorCount = spectatorCount;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public int getPauseTimeBetweenMatches() {
        return pauseTimeBetweenMatches;
    }

    public void setPauseTimeBetweenMatches(int pauseTimeBetweenMatches) {
        this.pauseTimeBetweenMatches = pauseTimeBetweenMatches;
    }

    public int getCeremonyTime() {
        return ceremonyTime;
    }

    public void setCeremonyTime(int ceremonyTime) {
        this.ceremonyTime = ceremonyTime;
    }

    public TournementStatus getStatus() {
        return status;
    }

    public void setStatus(TournementStatus status) {
        this.status = status;
    }

    public int calculateEstimatedDuration() {
        int teamCount = this.teams.size();
        int gameDifficulty = this.game.getDifficulty();
        int baseEstimate = (teamCount * this.averageMatchDuration * gameDifficulty) + this.pauseTimeBetweenMatches;
        return baseEstimate + this.ceremonyTime;
    }

    public int getAverageMatchDuration() {
        return averageMatchDuration;
    }

    public void setAverageMatchDuration(int averageMatchDuration) {
        this.averageMatchDuration = averageMatchDuration;
    }
}
