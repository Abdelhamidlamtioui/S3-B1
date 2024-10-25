package org.esport.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(unique = true)
    private String name;

    @Min(value = 1)
    @Max(value = 10)
    private int difficulty;

    @Min(value = 1)
    private int averageMatchDuration;

    // Constructors
    public Game() {
    }

    public Game(String name, int difficulty, int averageMatchDuration) {
        this.name = name;
        this.difficulty = difficulty;
        this.averageMatchDuration = averageMatchDuration;
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getAverageMatchDuration() {
        return averageMatchDuration;
    }

    public void setAverageMatchDuration(int averageMatchDuration) {
        this.averageMatchDuration = averageMatchDuration;
    }

    // toString method
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", averageMatchDuration=" + averageMatchDuration +
                '}';
    }
}
