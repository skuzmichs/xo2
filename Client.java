package model;

import com.google.gson.Gson;
import interfaces.MyObservable;
import interfaces.MyObserver;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;


public class Client extends WebSocketClient implements MyObservable {

    private int playground[][]; //Stan pola gry
    private int nextPlayer; //Kto jest nastepny
    private final int PLAYER = 1; //X = 1, O = 2
    private final Gson GSON = new Gson();//obiekt dla pracy z json
    private MyObserver myObserver; //obiekt obserwator


    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    //metoda otrzymuje wiadomosci serwera
    @Override
    public void onMessage(String s) {
        //otrzymujemy wiadomosc od serwera
        System.out.println("Mamy wiadomosc od serwera: " + s);
        //serializujemy json do obiektu klasy
        ResponseServer responseServer = GSON.fromJson(s, ResponseServer.class);
        //otrzymane dane wrzucamy do parametrow
        playground = responseServer.getPlayground();
        nextPlayer = responseServer.getNextPlayer();
        //tutaj jest realizowany pattern sluchaczy
        //wszystcy kto sa podpisane, wysylamy wiadomosc
        notifyObservers(responseServer);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
    }

    @Override
    public void onError(Exception e) {
        System.err.println(e);
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public int getPLAYER() {
        return PLAYER;
    }

    //metoda wyslania wiadomosci serweru
    public void sendMessage(MyImgView myImgView) {
        //przeksztalcamy obiekt w json wiersz
        RequestClient requestClient = new RequestClient(myImgView.getPositionX(), myImgView.getPositionY(), PLAYER);
        String message = GSON.toJson(requestClient);
        //Wysylamy nowe dane na serwer
        super.send(message);
    }

    @Override
    public void registerObserver(MyObserver myObserver) {
        this.myObserver = myObserver;
    }

    @Override
    public void notifyObservers(ResponseServer responseServer) {
        myObserver.notification(responseServer);
    }
}