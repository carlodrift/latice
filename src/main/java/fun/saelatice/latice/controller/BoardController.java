package fun.saelatice.latice.controller;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Player;
import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.tile.Tile;
import fun.saelatice.latice.model.tile.TileShape;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.InputStream;
import java.net.URL;

public class BoardController {

    private static final DataFormat DATA_FORMAT = new DataFormat("Tile");
    @FXML
    public Button idPlayBtn;
    @FXML
    public Button idPassBtn;
    @FXML
    public GridPane idRack;
    @FXML
    public Label idCurrentPlayer;
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

    private String getTileImagePath(Tile tile) {
        return tile.color().toString().toLowerCase() + "-" + tile.shape().toString().toLowerCase() + ".png";
    }

    private String getSquareImagePath(Square square) {
        return square.getType().toString().toLowerCase() + ".png";

    }

    private ImageView getBoardSizedImageView(InputStream input) {
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(this.idBoard.getMaxHeight() / 9);
        imageView.setFitWidth(this.idBoard.getMaxWidth() / 9);
        imageView.setBlendMode(BlendMode.MULTIPLY);
        return imageView;
    }

    public void fillRack(Player player) {
        this.idRack.getChildren().clear();
        Tile tile;
        for (int j = 0; j < player.getRack().size(); j++) {
            tile = player.getRack().get(j);
            InputStream input = this.getClass().getResourceAsStream(this.getTileImagePath(tile));
            InputStream input1 = this.getClass().getResourceAsStream(this.getTileImagePath(tile));
            if (input == null || input1 == null) {
                return;
            }
            Image image = new Image(input1, 45, 0, true, true);
            ImageView imageView = this.getBoardSizedImageView(input);
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
                input = this.getClass().getResourceAsStream(this.getTileImagePath(square.getTile()));
            } else {
                input = this.getClass().getResourceAsStream(this.getSquareImagePath(square));
            }
            if (input == null) {
                return;
            }
            ImageView imageView = this.getBoardSizedImageView(input);
            imageView.setOnDragOver(new DragTileOverBoardController(BoardController.DATA_FORMAT, square, board, position, imageView, game));
            imageView.setOnDragExited(event -> imageView.setEffect(null));
            imageView.setOnDragDropped(new PlayTileController(BoardController.DATA_FORMAT, board, position, game, this));
            this.idBoard.add(imageView, position.x(), position.y());
        });
    }

    public void loadSound(String sound, boolean play) {
        if (this.idSoundCb.isSelected()) {
            URL soundURL = this.getClass().getResource(sound);
            if (soundURL != null) {
                Media media = new Media(soundURL.toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                if (play) {
                    mediaPlayer.setAutoPlay(true);
                }
            }
        }
    }

    public void updateCurrentPlayer(Game game) {
        this.idCurrentPlayer.setText(game.getCurrentPlayer().getPlayerName(game));
        this.idPoints.setText("Cagnotte : " + game.getCurrentPlayer().getPoints());
        this.idPoolCount.setText("Pioche : " + game.getCurrentPlayer().getPool().size());
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
    public void initialize() {
        Board board = new Board();
        board.init();
        this.fillBoard(board, null);
        this.loadSound(TileShape.values()[0] + "-failed.wav", false);
    }

    @FXML
    public void play(MouseEvent mouseEvent) {
        Game game = new Game();
        game.start();
        ((Button) mouseEvent.getSource()).setVisible(false);
        this.fillRack(game.getCurrentPlayer());
        this.fillBoard(game.getBoard(), game);
        PassController passController = new PassController(this, game);
        this.idPassBtn.setOnMouseClicked(passController);
        this.switchPassBtnVisibility();
        this.updateCycles(game);
        passController.handle(null);
        this.updateCurrentPlayer(game);
    }

    public void switchVisibility(Node node) {
        node.setVisible(!node.isVisible());
    }

    public void switchRackVisibility() {
        this.switchVisibility(this.idRack);
    }

    public void switchPassBtnVisibility() {
        this.switchVisibility(this.idPassBtn);
    }

}
