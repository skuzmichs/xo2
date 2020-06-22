package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;


public class DialogController {

    @FXML
    private ImageView imgView;
    private Stage dialogStage;
    private int typeDialog;
    private FXMLLoader loader;

    public DialogController() {
    }

    @FXML
    private void initialize() {
        //obsługiwamy kliknięcia myszą
        imgView.setOnMouseClicked(event -> {
            try {
                loader = new FXMLLoader();
                //W zaleznjsci od sytuacji wybieramy okno
                if (typeDialog == 1) {
                    Stage stage = getNextStage("/fxml/Main.fxml");
                    MainController controller = loader.getController();
                    controller.setDialogStage(stage);
                    stage.show();
                }
                if (typeDialog == 0) {
                    Stage stage = getNextStage("/fxml/Dialog.fxml");
                    DialogController controller = loader.getController();
                    controller.setDialogStage(stage, 1, new Image("START! (460x260).png"));
                    stage.show();
                }
                this.dialogStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setDialogStage(Stage dialogStage, int typeDialog, Image image) {
        this.dialogStage = dialogStage;
        this.typeDialog = typeDialog;
        imgView.setImage(image);

    }

    private Stage getNextStage(String fxmlName) throws IOException {
        loader.setLocation(getClass().getResource(fxmlName));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        return stage;
    }
}
