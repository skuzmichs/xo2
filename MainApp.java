import controller.DialogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/Dialog.fxml";
        FXMLLoader loader = new FXMLLoader();
        //ladowanie pliku narzutu GUI typu
        loader.setLocation(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        //tworzenie sceny
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("XO");
        DialogController controller = loader.getController();
        controller.setDialogStage(stage, 1, new Image("START! (460x260).png"));
        stage.show();
    }
}
