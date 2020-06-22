package controller;

import interfaces.MyObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.MyImgView;
import model.Client;
import model.ResponseServer;

import java.io.IOException;
import java.net.URI;

public class MainController implements MyObserver {

    @FXML
    private AnchorPane anchorPane;
    private Client client;
    private Stage stage;

    public MainController() {
    }

    @FXML
    private void initialize() {
        //Wszystkim imageViewer przypisujemy obsluge klikniec mysza
        for (Node node : anchorPane.getChildren()) {
            if (node instanceof MyImgView) {
                node.setOnMouseClicked(event -> {
                    send((MyImgView) node);
                });
            }
        }
        //Podlaczamy sie do serwera
        try {
            client = new Client(new URI("ws://localhost:4004"));
            //Rejestrujemy sie jako obserwator
            //ten mechanizm pomaga przy odbiorze wiadomosci od serwera
            client.registerObserver(this);
            client.connect();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void send(MyImgView myImgView) {
        //wysylamy wspolrzedne oraz wartosc (х lub о) na serwer
        if (myImgView.getValue() == 0 && client.getPLAYER() == client.getNextPlayer() && client != null && client.isOpen()) {
            System.out.println("Wysylamy wspolrzedne: x=" + myImgView.getPositionX() + " y=" + myImgView.getPositionY());
            client.sendMessage(myImgView);
        }

    }

    //metoda rysowania
    private void repaint(int playground[][]) {
        for (Node n : anchorPane.getChildren()) {
            if (n instanceof MyImgView) {
                switch (playground[((MyImgView) n).getPositionX()][((MyImgView) n).getPositionY()]) {
                    case 1:
                        ((MyImgView) n).setImage(new Image("X (180x180).png"));
                        ((MyImgView) n).setValue(1);
                        break;
                    case 2:
                        ((MyImgView) n).setImage(new Image("O(180x180).png"));
                        ((MyImgView) n).setValue(2);
                        break;
                    default:
                        ((MyImgView) n).setImage(new Image("back.png"));
                        ((MyImgView) n).setValue(0);
                        break;
                }
            }
        }
    }


    //Po wiadomosci z serwera wyprowadzamy ta metode
    @Override
    public void notification(ResponseServer responseServer) {
        //rysujemy sytuacje na podstawie nowych danych
        repaint(responseServer.getPlayground());
        //Puszczamy w glownym strumieniu
        Platform.runLater(
                () -> {
                    if (responseServer.getWinner() != 0) {
                        Image image = null;
                        if (responseServer.getWinner() == 1) {
                            image = new Image("Xwin (460x260).png");
                        }
                        if (responseServer.getWinner() == 2) {
                            image = new Image("Owin(460x260).png");
                        }
                        if (responseServer.getWinner() == 3) {
                            image = new Image("XO win (460x260).png");
                        }

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/fxml/Dialog.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stage dialogStage = new Stage();
                        dialogStage.setScene(new Scene(root));
                        dialogStage.setResizable(false);
                        DialogController controller = loader.getController();
                        controller.setDialogStage(dialogStage, 0, image);
                        dialogStage.show();
                        client.close();
                        this.stage.close();
                    }
                }
        );

    }

    public void setDialogStage(Stage stage) {
        this.stage = stage;
        this.stage.setTitle(client.getPLAYER() == 1 ? "X" : "O");

    }
}
