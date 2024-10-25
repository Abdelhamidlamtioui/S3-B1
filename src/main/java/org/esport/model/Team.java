package org.esport.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
    private List<Player> players = new ArrayList<>();

    @ManyToMany(mappedBy = "teams")
    private List<Tournement> tournements = new ArrayList<>();

    private int ranking;

    // Constructors
    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Tournement> getTournements() {
        return tournements;
    }

    public void setTournements(List<Tournement> tournements) {
        this.tournements = tournements;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    // Helper methods
    public void addPlayer(Player player) {
        this.players.add(player);
        player.setTeam(this);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        player.setTeam(null);
    }

    // toString, equals, and hashCode methods
}
