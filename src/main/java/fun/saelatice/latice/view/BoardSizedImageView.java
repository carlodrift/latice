package fun.saelatice.latice.view;

import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BoardSizedImageView extends ImageView {

    public BoardSizedImageView(Image image, GridPane board) {
        this.setImage(image);
        this.setFitHeight(board.getMaxHeight() / 9);
        this.setFitWidth(board.getMaxWidth() / 9);
        this.setBlendMode(BlendMode.MULTIPLY);
    }

}
