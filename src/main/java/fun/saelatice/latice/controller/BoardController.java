package fun.saelatice.latice.controller;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Player;
import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.tile.Tile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.InputStream;

public class BoardController {

    @FXML
    public Button idPlayBtn;
    @FXML
    public GridPane idRack;
    @FXML
    public Label idCurrentPlayer;
    @FXML
    private GridPane idBoard;

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
            this.idBoard.add(this.getBoardSizedImageView(input), location.x(), location.y());
        });
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
        ((Button) mouseEvent.getSource()).setDisable(true);
    }
}
