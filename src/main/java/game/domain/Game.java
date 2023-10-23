package game.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @Column(name = "game_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "secret_number", nullable = false)
    private String secretNumber;
    @ElementCollection
    @CollectionTable(name = "game_turns", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "turn")
    private List<String> turns;
    @Column(name = "tries_left", nullable = false)
    private int triesLeft;
    @Column(name = "last_result")
    private String lastResult;
    @Column(name = "is_finished", nullable = false)
    private boolean isFinished;

    public Game() {

    }

    public Game(String secretNumber) {
        this.secretNumber = secretNumber;
        this.turns = new ArrayList<>();
        this.triesLeft = 8;
        this.isFinished = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecretNumber() {
        return secretNumber;
    }

    public List<String> getTurns() {
        return turns;
    }

    public void setTurns(List<String> turns) {
        this.turns = turns;
    }

    public void saveGuess(String guess) {
        turns.add(guess);
    }

    public int getTriesLeft() {
        return triesLeft;
    }

    public void decreaseTries() {
        triesLeft--;
    }

    public void setTriesLeft(int triesLeft) {
        this.triesLeft = triesLeft;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

}
