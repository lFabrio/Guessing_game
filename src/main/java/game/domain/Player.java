package game.domain;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @Column(name = "player_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "correct_guesses", nullable = false)
    private int correctGuesses;
    @Column(name = "games_played", nullable = false)
    private int gamesPlayed;
    @Column(name = "score", nullable = false)
    private double score;
    @Column(name = "game_id")
    private Long gameId;

    public Player() {}

    public Player(String username) {
        this.username = username;
        this.correctGuesses = 0;
        this.gamesPlayed = 0;
        this.score = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public int getCorrectGuesses() {
        return correctGuesses;
    }

    public void increaseCorrectGuesses() {
        correctGuesses++;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void increaseGamesPlayed() {
        this.gamesPlayed++;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGame(Long gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return username + ", score: " + score + ", games count: " + gamesPlayed;
    }

}
