package fun.saelatice.latice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Latice extends Application {

    private static final Random random = new Random();
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

    public static Random random() {
        return Latice.random;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Latice — Mario Edition");
        URL icon = this.getClass().getResource("icon.png");
        if (icon != null) {
            primaryStage.getIcons().add(new Image(icon.toString()));
        }
        primaryStage.setResizable(false);
        scene.setOnDragOver(event -> event.acceptTransferModes(TransferMode.MOVE));
        primaryStage.show();
        Latice.setStage(primaryStage);
    }
}
