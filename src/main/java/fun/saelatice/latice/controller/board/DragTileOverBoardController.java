package fun.saelatice.latice.controller.board;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Position;
import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.tile.Tile;
import javafx.event.EventHandler;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

public record DragTileOverBoardController(DataFormat format, Square square, Board board, Position position,
                                          ImageView imageView, Game game) implements EventHandler<DragEvent> {

    private static final double OVER_HUE = 0.78;
    private static final int OVER_GLOW = 2;

    @Override
    public void handle(DragEvent event) {
        if (event.getDragboard().hasContent(this.format)) {
            event.acceptTransferModes(TransferMode.MOVE);
            Tile tile = (Tile) event.getDragboard().getContent(this.format);
            if (this.square.getTile() == null) {
                if (this.board.canPlayHere(this.position, tile)) {
                    this.imageView.setEffect(new Glow(1));
                } else {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setHue(DragTileOverBoardController.OVER_HUE);
                    Blend blend = new Blend(BlendMode.MULTIPLY);
                    blend.setBottomInput(colorAdjust);
                    blend.setTopInput(new Glow(DragTileOverBoardController.OVER_GLOW));
                    this.imageView.setEffect(blend);
                }
            }
        }
    }
}
