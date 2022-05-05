package fun.saelatice.latice.controller;

import fun.saelatice.latice.model.Board;
import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BoardController {

    @FXML
    private GridPane idBoard;

    @FXML
    public void initialize() {
        Board board = new Board();
        board.init();
        board.getSquares().forEach((location, square) -> {
            Image image;
            if (square.getTile() != null) {
                image = new Image(this.getClass().getResourceAsStream(square.getTile().color().toString().toLowerCase() + "-" + square.getTile().shape().toString().toLowerCase() + ".png"));
            } else {
                image = new Image(this.getClass().getResourceAsStream(square.getType().toString().toLowerCase() + ".png"));
            }
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(this.idBoard.getMaxHeight() / 9);
            imageView.setFitWidth(this.idBoard.getMaxWidth() / 9);
            imageView.setBlendMode(BlendMode.MULTIPLY);
            this.idBoard.add(imageView, location.x(), location.y());
        });
    }

}
