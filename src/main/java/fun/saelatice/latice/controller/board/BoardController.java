package fun.saelatice.latice.controller.board;

import fun.saelatice.latice.Latice;
import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Player;
import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.tile.Tile;
import fun.saelatice.latice.view.BoardSizedImageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class BoardController {

    private static final DataFormat DATA_FORMAT = new DataFormat("Tile");
    private static final int MUSIC_NUMBER = 3;
    @FXML
    public MediaView idSoundMediaView;
    @FXML
    private MediaView idMusicMediaView;
    @FXML
    private Button idPassBtn;
    @FXML
    private GridPane idRack;
    @FXML
    private Label idCurrentPlayer;
    @FXML
    private Pane idNextPlayerPane;
    @FXML
    private Button idReadyBtn;
    @FXML
    private Label idNextPlayerLabel;
    @FXML
    private ImageView idBackground;
    @FXML
    private GridPane idBoard;
    @FXML
    private Label idPoints;
    @FXML
    private CheckBox idSoundCb;
    @FXML
    private Label idPoolCount;
    @FXML
    private Label idCycles;
    @FXML
    private Button idChangeRack;
    @FXML
    private ImageView idSettingsBtn;
    @FXML
    private ImageView idNextBtn;
    @FXML
    private ImageView idPreviousBtn;
    @FXML
    private ImageView idPauseBtn;
    @FXML
    private ImageView idPlayBtn;
    @FXML
    private Pane idSettingsPane;
    @FXML
    private Slider idMusicSld;
    @FXML
    private Pane idWinPane;
    @FXML
    private Label idWinLabel;
    @FXML
    private Label idWinInfo1Label;
    @FXML
    private Label idWinInfo2Label;
    @FXML
    private ImageView idWinImg;
    @FXML
    private Pane idTiePane;
    @FXML
    private Label idTieInfoLabel;
    @FXML
    private ImageView idTieImg;
    @FXML
    private Pane idMainMenuPne;
    private Media music;
    private MediaPlayer musicPlayer;


    private String tileImagePath(Tile tile) {
        return tile.color().toString().toLowerCase() + "-" + tile.shape().toString().toLowerCase() + ".png";
    }

    private String squareImagePath(Square square) {
        return square.getType().toString().toLowerCase() + ".png";
    }

    public void fillRack(Player player) {
        this.idRack.getChildren().clear();
        Tile tile;
        for (int j = 0; j < player.getRack().size(); j++) {
            tile = player.getRack().get(j);
            URL input = this.getClass().getResource(this.tileImagePath(tile));
            URL input1 = this.getClass().getResource(this.tileImagePath(tile));
            if (input == null || input1 == null) {
                return;
            }
            Image image = new Image(input1.toString(), 45, 0, true, true);
            ImageView imageView = new BoardSizedImageView(new Image(input.toString()), this.idBoard);
            imageView.addEventFilter(MouseEvent.DRAG_DETECTED, new DragTileController(imageView, image, this.idRack, tile, BoardController.DATA_FORMAT));
            imageView.setOnDragDone(new DragTileDoneController(this.idRack, imageView));
            this.idRack.add(imageView, j, 0);
        }
    }

    public void fillBoard(Board board, Game game) {
        this.idBoard.getChildren().retainAll(this.idBoard.getChildren().get(0));
        board.getSquares().forEach((position, square) -> {
            URL input;
            if (square.getTile() != null) {
                input = this.getClass().getResource(this.tileImagePath(square.getTile()));
            } else {
                input = this.getClass().getResource(this.squareImagePath(square));
            }
            if (input == null) {
                return;
            }
            ImageView imageView = new BoardSizedImageView(new Image(input.toString()), this.idBoard);
            imageView.setOnDragEntered(new DragTileOverBoardController(BoardController.DATA_FORMAT, square, board, position, imageView, game));
            imageView.setOnDragExited(event -> imageView.setEffect(null));
            imageView.setOnDragDropped(new PlayTileController(BoardController.DATA_FORMAT, board, position, game, this));
            this.idBoard.add(imageView, position.x(), position.y());
        });
    }

    public void loadSound(String sound) {
        if (this.idSoundCb.isSelected()) {
            URL soundURL = this.getClass().getResource(sound);
            if (soundURL != null) {
                Media media = new Media(soundURL.toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(0.5);
                this.idSoundMediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.setAutoPlay(true);
            }
        }
    }

    public void updateCurrentPlayer(Game game) {
        this.idCurrentPlayer.setText(game.getCurrentPlayer().playerName(game));
        this.idPoints.setText(game.getCurrentPlayer().getPoints() + "");
        this.idPoolCount.setText(game.getCurrentPlayer().getPool().size() + "");
        this.idPoints.setVisible(true);
        this.idPoolCount.setVisible(true);
        this.idCurrentPlayer.setVisible(true);
        this.updateCycles(game);
    }

    public void updateCycles(Game game) {
        this.idCycles.setText("Cycle " + (game.getCycles() + 1));
        this.idCycles.setVisible(true);
    }

    @FXML
    public void ready() {
        this.idNextPlayerPane.setVisible(false);
        this.idPassBtn.setDisable(false);
    }

    @FXML
    public void nextPlayerAlert() {
        this.idPassBtn.setDisable(true);
        this.idNextPlayerPane.setVisible(true);
        this.idReadyBtn.setOnMouseClicked(event -> this.ready());
    }

    @FXML
    public void settings() {
        this.switchVisibility(this.idSettingsPane);
        this.idMainMenuPne.setVisible(false);
    }

    public void hideSettingsMenu() {
        this.idSettingsPane.setVisible(false);
    }

    @FXML
    public void initialize() {
        URL musicURL = this.getClass().getResource("music" + Latice.random().nextInt(BoardController.MUSIC_NUMBER) + ".mp3");
        if (musicURL != null) {
            this.music = new Media(musicURL.toString());
        }
        this.musicPlayer = new MediaPlayer(this.music);
        BoardController.this.idMusicMediaView.setMediaPlayer(this.musicPlayer);
        this.playMedia();
        this.nextMedia();
        this.idMusicSld.setValue(this.musicPlayer.getVolume() * 100);
        this.idMusicSld.valueProperty().addListener(observable -> BoardController.this.musicPlayer.setVolume(BoardController.this.idMusicSld.getValue() / 100));

        URL settingsImage = this.getClass().getResource("gears.png");
        URL backgroundImage = this.getClass().getResource("game.png");
        URL nextImage = this.getClass().getResource("next.png");
        URL previousImage = this.getClass().getResource("previous.png");
        URL pauseImage = this.getClass().getResource("pause.png");
        URL playImage = this.getClass().getResource("play.png");
        if (settingsImage != null && backgroundImage != null && nextImage != null
                && previousImage != null && pauseImage != null && playImage != null) {
            this.idSettingsBtn.setImage(new Image(settingsImage.toString()));
            this.idNextBtn.setImage(new Image(nextImage.toString()));
            this.idPreviousBtn.setImage(new Image(previousImage.toString()));
            this.idPauseBtn.setImage(new Image(pauseImage.toString()));
            this.idPlayBtn.setImage(new Image(playImage.toString()));
            this.idBackground.setImage(new Image(backgroundImage.toString()));
        }
        this.idPlayBtn.setVisible(false);


        Board board = new Board();
        board.init();
        this.fillBoard(board, null);
        Game game = new Game();
        game.start();
        this.fillRack(game.getCurrentPlayer());
        this.fillBoard(game.getBoard(), game);
        PassController passController = new PassController(this, game);
        this.idPassBtn.setOnMouseClicked(passController);
        this.idChangeRack.setOnAction(new ChangeRackController(this, game));
        this.updateCycles(game);
        passController.handle(null);
        this.updateCurrentPlayer(game);
    }

    @FXML
    private void switchButtonColor(MouseEvent event) {
        Button button = (Button) event.getSource();
        if (button.getTextFill() == Color.WHITE) {
            button.setTextFill(Color.valueOf("#626262"));
        } else {
            button.setTextFill(Color.WHITE);
        }
    }

    @FXML
    private void switchPassButtonColor(MouseEvent event) {
        Button button = (Button) event.getSource();
        if (button.getTextFill() == Color.GREY) {
            button.setStyle("-fx-background-color: ff8686; -fx-background-radius: 10; -fx-border-color: black; -fx-border-radius: 10;");
            button.setTextFill(Color.BLACK);
        } else {
            button.setStyle("-fx-background-color: ffb3b3; -fx-background-radius: 10; -fx-border-color: black; -fx-border-radius: 10;");
            button.setTextFill(Color.GREY);
        }
    }

    @FXML
    private void mainMenu() {
        this.musicPlayer.stop();
        Stage stage = Latice.stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Latice.class.getResource("welcome-view.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void playMedia() {
        this.idPauseBtn.setVisible(true);
        this.idPlayBtn.setVisible(false);
        this.musicPlayer.setVolume(0.18);
        this.musicPlayer.play();
        this.musicPlayer.setOnEndOfMedia(this::nextMedia);
    }

    @FXML
    public void pauseMedia() {
        this.idPauseBtn.setVisible(false);
        this.idPlayBtn.setVisible(true);
        this.musicPlayer.pause();
    }

    @FXML
    private void previousMedia() {
        this.musicPlayer.stop();
        URL musicUrl = this.getClass().getResource("music" + this.getPreviousMusic(this.music) + ".mp3");
        if (musicUrl != null) {
            this.music = new Media(musicUrl.toString());
        }
        this.musicPlayer = new MediaPlayer(this.music);
        BoardController.this.idMusicMediaView.setMediaPlayer(this.musicPlayer);
        this.playMedia();
    }

    @FXML
    private void nextMedia() {
        this.musicPlayer.stop();
        URL musicURL = this.getClass().getResource("music" + this.getNextMusic(this.music) + ".mp3");
        if (musicURL != null) {
            this.music = new Media(musicURL.toString());
        }
        this.musicPlayer = new MediaPlayer(this.music);
        BoardController.this.idMusicMediaView.setMediaPlayer(this.musicPlayer);
        this.playMedia();
    }

    public void switchVisibility(Node node) {
        node.setVisible(!node.isVisible());
    }

    public void disableChangeRack() {
        this.idChangeRack.setDisable(true);
    }

    public void enableChangeRack() {
        this.idChangeRack.setDisable(false);
    }

    public void switchPassBtnVisibility() {
        this.switchVisibility(this.idPassBtn);
    }

    public void setNextPlayerText(String text) {
        this.idNextPlayerLabel.setText(text);
    }

    public int getNextMusic(Media media) {
        int musicNumber = Character.getNumericValue(media.getSource().charAt(media.getSource().length() - 5));
        if (musicNumber == BoardController.MUSIC_NUMBER - 1) {
            musicNumber = 0;
        } else {
            musicNumber += 1;
        }
        return musicNumber;
    }

    public int getPreviousMusic(Media media) {
        int musicNumber = Character.getNumericValue(media.getSource().charAt(media.getSource().length() - 5));
        if (musicNumber == 0) {
            musicNumber = BoardController.MUSIC_NUMBER - 1;
        } else {
            musicNumber -= 1;
        }
        return musicNumber;
    }

    public void drawAlert(int info) {
        URL drawImage = this.getClass().getResource("tie.png");
        if (drawImage != null) {
            this.idTieImg.setImage(new Image(drawImage.toString()));
        }
        this.idTieInfoLabel.setText("Les joueurs ont posé " + info + " tuile" + this.plural(info) + " chacun.");
        this.idTiePane.setVisible(true);
    }

    public void winAlert(String winner, String player1, String player2, int info1, int info2) {
        URL winImage = this.getClass().getResource("win.png");
        if (winImage != null) {
            this.idWinImg.setImage(new Image(winImage.toString()));
        }
        this.idWinLabel.setText(winner + " a gagné !");
        this.idWinInfo1Label.setText(player1 + " a posé " + info1 + " tuile" + this.plural(info1) + ".");
        this.idWinInfo2Label.setText(player2 + " a posé " + info2 + " tuile" + this.plural(info2) + ".");
        this.idWinPane.setVisible(true);
    }

    public String plural(int quantity) {
        if (quantity > 1) {
            return "s";
        }
        return "";
    }

    @FXML
    public void mainMenuConfirmation() {
        this.switchVisibility(this.idMainMenuPne);
    }

}
