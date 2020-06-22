

import com.google.gson.Gson;
import model.RequestClient;
import model.ResponseServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

//otzrymuje i przesyla dane gry
public class Server extends WebSocketServer {

    //pole gry w postaci macierzy
    private static int playground[][] = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}};
    //nastepny gracz
    private int nextPlayer = 1;
    //zwyciestwo 1-х 2-о 3-remis
    private int winner = 0;

    private final Gson GSON = new Gson();

    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    //metoda przesyla dane stanu gry podlaczonemu graczu
    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        broadcast(gameState());
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    //metoda otzrzymuje wiadomosc od gracza
    @Override
    public void onMessage(WebSocket webSocket, String s) {
        //serializacja wiadomosci(json) w obiekt klasy RequestClient, ktora przechowywa informacje o tym jaki gracz zagral
        RequestClient requestClient = GSON.fromJson(s, RequestClient.class);
        //wrzucamy dane do macierzy gracza
        playground[requestClient.getPositionX()][requestClient.getPositionY()] = requestClient.getPlayer();
        //wskazujemy kto bedzie nastepny
        nextPlayer = requestClient.getPlayer() == 1 ? 2 : 1;
        //sprawdzamy czy jest zwyciezca
        winner = checkWinner();
        //wysylamy wszystkim graczom stan gry
        broadcast(gameState());

        //jezeli jest zwyciezca zerujemy stan gry
        if (winner != 0) {
            playground = new int[][]{
                    {0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}};
            nextPlayer = 1;
            winner = 0;
        }

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }

    //resetujemy serwer
    public static void main(String[] args) {
        Server server = new Server(4004);
        server.start();
    }

    //otrzymujemy aktualny stan gry
    private String gameState() {
        //(deserial) przeksztalcamy obiekt klasy w json i wysylamy graczom
        ResponseServer responseServer = new ResponseServer(playground, nextPlayer, winner);
        String message = GSON.toJson(responseServer);
        System.out.println("gameState " + winner);
        return message;
    }

    //sprawdzenie zwyciezcy
    private int checkWinner() {
        //wiersz
        for (int i = 0; i < 3; i++) {
            int res = 0;
            if (playground[i][0] != 0) {
                for (int j = 1; j < 3; j++) {
                    if (playground[i][j] == playground[i][0]) {
                        res++;
                    }
                }
            }
            if (res == 2)
                return playground[i][0];
        }
        //kolumna
        for (int i = 0; i < 3; i++) {
            int res = 0;
            if (playground[0][i] != 0) {
                for (int j = 1; j < 3; j++) {
                    if (playground[j][i] == playground[0][i]) {
                        res++;
                    }
                }
            }
            if (res == 2)
                return playground[0][i];
        }

        //diagonale
        int res = 0;
        for (int i = 1; i < 3; i++)
            if (playground[0][0] != 0) {
                if (playground[i][i] == playground[0][0]) {
                    res++;
                }
            }
        if (res == 2)
            return playground[0][0];

        res = 0;
        for (int i = 1; i < 3; i++)
            if (playground[3 - 1][0] != 0) {
                if (playground[3 - i - 1][i] == playground[3 - 1][0]) res++;
            }
        if (res == 2)
            return playground[3 - 1][0];

        //sprawdzenie remisa
        int z = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (playground[i][j] == 0) z++;
            }
        }
        if (z == 0) return 3;

        return 0;
    }

}
