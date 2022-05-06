package fun.saelatice.latice.controller;

import fun.saelatice.latice.model.tile.Tile;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public record DragTileController(ImageView view, Image image, GridPane rack, Tile tile,
                                 DataFormat data) implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        Dragboard dragboard = this.view.startDragAndDrop(TransferMode.MOVE);
        dragboard.setDragView(this.image);
        ClipboardContent content = new ClipboardContent();
        content.put(this.data, this.tile);
        dragboard.setContent(content);
        this.rack.getChildren().remove(this.view);
    }
}
