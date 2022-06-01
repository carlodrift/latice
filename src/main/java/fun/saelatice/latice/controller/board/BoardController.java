package fun.saelatice.latice.controller.board;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Player;
import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.tile.Tile;
import fun.saelatice.latice.view.BoardSizedImageView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.net.URL;

public class BoardController {

    private static final DataFormat DATA_FORMAT = new DataFormat("Tile");
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
    private Pane idSettingsPane;

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
            InputStream input = this.getClass().getResourceAsStream(this.tileImagePath(tile));
            InputStream input1 = this.getClass().getResourceAsStream(this.tileImagePath(tile));
            if (input == null || input1 == null) {
                return;
            }
            Image image = new Image(input1, 45, 0, true, true);
            ImageView imageView = new BoardSizedImageView(new Image(input), this.idBoard);
            imageView.addEventFilter(MouseEvent.DRAG_DETECTED, new DragTileController(imageView, image, this.idRack, tile, BoardController.DATA_FORMAT));
            imageView.setOnDragDone(new DragTileDoneController(this.idRack, imageView));
            this.idRack.add(imageView, j, 0);
        }
    }

    public void fillBoard(Board board, Game game) {
        this.idBoard.getChildren().retainAll(this.idBoard.getChildren().get(0));
        board.getSquares().forEach((position, square) -> {
            InputStream input;
            if (square.getTile() != null) {
                input = this.getClass().getResourceAsStream(this.tileImagePath(square.getTile()));
            } else {
                input = this.getClass().getResourceAsStream(this.squareImagePath(square));
            }
            if (input == null) {
                return;
            }
            ImageView imageView = new BoardSizedImageView(new Image(input), this.idBoard);
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
    public void parameters() {
        this.switchVisibility(this.idSettingsPane);
    }

    @FXML
    public void initialize() {
        InputStream settingsImage = this.getClass().getResourceAsStream("gears.png");
        InputStream backgroundImage = this.getClass().getResourceAsStream("game.png");
        if (settingsImage != null && backgroundImage != null) {
            this.idSettingsBtn.setImage(new Image(settingsImage));
            this.idBackground.setImage(new Image(backgroundImage));
        }
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
        if (button.getTextFill() == Color.BLACK) {
            button.setTextFill(Color.valueOf("#aaaaaa"));
        } else {
            button.setTextFill(Color.BLACK);
        }
    }

    @FXML
    private void switchPassButtonColor(MouseEvent event) {
        Button button = (Button) event.getSource();
        if (button.getStyle().contains("-fx-background-color: #ff8686")) {
            button.setTextFill(Color.valueOf("#aaaaaa"));
        } else {
            button.setTextFill(Color.BLACK);
        }
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
}
