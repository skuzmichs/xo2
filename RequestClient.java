package model;

//klasa dla wysylania wspolrzednych wybranej klatki klientem do serwera
public class RequestClient {
    private int positionX; //wdpolrzedna klatki wzdluz х
    private int positionY; //wdpolrzedna klatki wzdluz у
    private int player; // identyfikator gracza

    public RequestClient(int positionX, int positionY, int player) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.player = player;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
