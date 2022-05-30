package fun.saelatice.latice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.IOException;

public class Latice extends Application {

    private static Stage stage;

    public static Stage stage() {
        return Latice.stage;
    }

    public static void setStage(Stage stage) {
        Latice.stage = stage;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.setOnDragOver(event -> event.acceptTransferModes(TransferMode.MOVE));
        primaryStage.show();
        Latice.setStage(primaryStage);
    }
}
