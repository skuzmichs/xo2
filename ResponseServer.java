package model;

//klasa do nadawania stanu gry od serwera do klientow
public class ResponseServer {
    private int playground[][];//stan gry
    private int nextPlayer;//nastepny gracz
    private int winner;//zwyciezca

    public ResponseServer(int[][] playground, int nextPlayer, int winner) {
        this.playground = playground;
        this.nextPlayer = nextPlayer;
        this.winner = winner;
    }

    public int[][] getPlayground() {
        return playground;
    }

    public void setPlayground(int[][] playground) {
        this.playground = playground;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
