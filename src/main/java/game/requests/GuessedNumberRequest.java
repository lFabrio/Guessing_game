package game.requests;

public class GuessedNumberRequest {

    private String number;

    public GuessedNumberRequest() {
    }

    public GuessedNumberRequest(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
