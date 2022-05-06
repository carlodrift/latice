package fun.saelatice.latice.controller;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Player;
import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.tile.Tile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
    public GridPane idRack;
    @FXML
    public Label idCurrentPlayer;
    @FXML
    private GridPane idBoard;
    @FXML
    private CheckBox idSoundCb;

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

    private void fillRack(Player player) {
        this.idRack.getChildren().clear();
        Tile tile;
        for (int j = 0; j < player.getRack().size(); j++) {
            tile = player.getRack().get(j);
            InputStream input = this.getClass().getResourceAsStream(this.getTileImagePath(tile));
            InputStream input1 = this.getClass().getResourceAsStream(this.getTileImagePath(tile));
            if (input == null || input1 == null) {
                return;
            }
            Image image = new Image(input1, 45, 45, true, true);
            ImageView imageView = this.getBoardSizedImageView(input);
            imageView.addEventFilter(MouseEvent.DRAG_DETECTED, new DragTileController(imageView, image, this.idRack, tile, BoardController.DATA_FORMAT));
            imageView.setOnDragDone(new DragTileDoneController(this.idRack, imageView));
            this.idRack.add(imageView, j, 0);
        }
    }

    private void fillBoard(Board board) {
        this.idBoard.getChildren().retainAll(this.idBoard.getChildren().get(0));
        board.getSquares().forEach((location, square) -> {
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
            imageView.setOnDragOver(event -> {
                if (event.getDragboard().hasContent(BoardController.DATA_FORMAT)) {
                    event.acceptTransferModes(TransferMode.MOVE);
                    if (square.getTile() == null) {
                        ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setHue(0.78);
                        imageView.setEffect(colorAdjust);
                    }
                }
            });
            imageView.setOnDragExited(event -> {
                imageView.setEffect(null);
            });
            imageView.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                Tile tile = (Tile) db.getContent(BoardController.DATA_FORMAT);
                if (square.getTile() == null) {
                    event.setDropCompleted(true);
                    board.getSquares().get(location).setTile(tile);
                    this.fillBoard(board);
                    this.playSound(tile.shape().toString().toLowerCase() + "-played.wav");
                } else {
                    this.playSound(tile.shape().toString().toLowerCase() + "-failed.wav");
                }
            });
            this.idBoard.add(imageView, location.x(), location.y());
        });
    }

    private void playSound(String sound) {
        if (this.idSoundCb.isSelected()) {
            URL soundURL = this.getClass().getResource(sound);
            if (soundURL != null) {
                Media media = new Media(soundURL.toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            }
        }
    }

    private void updateCurrentPlayer(Game game) {
        if (game.getCurrentPlayer() == game.getPlayer1()) {
            this.idCurrentPlayer.setText("Joueur 1");
        }
        if (game.getCurrentPlayer() == game.getPlayer2()) {
            this.idCurrentPlayer.setText("Joueur 2");
        }
        this.idCurrentPlayer.setVisible(true);
    }

    @FXML
    public void initialize() {
        Board board = new Board();
        board.init();
        this.fillBoard(board);
    }

    @FXML
    public void play(MouseEvent mouseEvent) {
        Game game = new Game();
        game.start();
        this.updateCurrentPlayer(game);
        ((Button) mouseEvent.getSource()).setDisable(true);
        this.fillRack(game.getCurrentPlayer());
    }
}
