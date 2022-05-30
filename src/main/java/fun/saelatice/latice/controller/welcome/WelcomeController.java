package fun.saelatice.latice.controller.welcome;

import fun.saelatice.latice.model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeController {

    @FXML
    private MediaView idVideoMediaView;
    @FXML
    private MediaView idMusicMediaView;
    @FXML
    private Button idPlayBtn;
    @FXML
    private Button idQuitBtn;
    @FXML
    private Button idNewGameBtn;
    @FXML
    private Pane idPlayAlertPane;
    @FXML
    private TextField idName1TextField;
    @FXML
    private TextField idName2TextField;
    @FXML
    private Button idSettingsBtn;

    @FXML
    public void close() {
        Stage stage = (Stage) this.idQuitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void start() throws IOException {
        if (!this.idName1TextField.getText().isEmpty() && this.idName1TextField.getText().equals(this.idName2TextField.getText())) {
            this.idName2TextField.setText(this.idName2TextField.getText() + " bis");
        }
        if (!this.idName1TextField.getText().isEmpty()) {
            Game.setPlayer1Name(this.idName1TextField.getText());
        }
        if (!this.idName2TextField.getText().isEmpty()) {
            Game.setPlayer2Name(this.idName2TextField.getText());
        }
        Stage stage = (Stage) this.idNewGameBtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("board-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setOnDragOver(e -> e.acceptTransferModes(TransferMode.MOVE));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void playAlert() {
        this.switchButtonsVisibility();
        this.idPlayAlertPane.setVisible(true);
        this.idNewGameBtn.requestFocus();

    }

    @FXML
    public void closePlayAlert() {
        this.switchButtonsVisibility();
        this.idPlayAlertPane.setVisible(false);
    }

    @FXML
    public void settings() {
    }

    @FXML
    public void initialize() {

        Media intro = new Media(this.getClass().getResource("intro.mp4").toString());
        Media introLoop = new Media(this.getClass().getResource("intro2.mp4").toString());
        Media music = new Media(this.getClass().getResource("intro.mp3").toString());

        this.idPlayAlertPane.setVisible(false);

        MediaPlayer introPlayer = new MediaPlayer(intro);

        MediaPlayer musicPlayer = new MediaPlayer(music);

        MediaPlayer introLoopPlayer = new MediaPlayer(introLoop);

        this.idVideoMediaView.setMediaPlayer(introPlayer);
        introPlayer.setAutoPlay(true);

        this.switchButtonsVisibility();

        List<Button> buttons = Arrays.asList(this.idPlayBtn, this.idQuitBtn, this.idSettingsBtn);
        buttons.forEach(button -> {
            button.setOnMouseEntered(event -> button.setTextFill(Color.BLACK));
            button.setOnMouseExited(event -> button.setTextFill(Color.valueOf("#aaaaaa")));
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                WelcomeController.this.switchButtonsVisibility();
            }
        }, 10800);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                WelcomeController.this.idMusicMediaView.setMediaPlayer(musicPlayer);
                musicPlayer.setAutoPlay(true);
            }
        }, 4500);

        introPlayer.setOnEndOfMedia(() -> {
            WelcomeController.this.idVideoMediaView.setMediaPlayer(introLoopPlayer);
            introLoopPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            introLoopPlayer.setAutoPlay(true);
        });

    }

    private void switchButtonsVisibility() {
        this.idPlayBtn.setVisible(!this.idPlayBtn.isVisible());
        this.idSettingsBtn.setVisible(!this.idSettingsBtn.isVisible());
        this.idQuitBtn.setVisible(!this.idQuitBtn.isVisible());
    }


}
