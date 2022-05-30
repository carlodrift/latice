package fun.saelatice.latice.controller.board;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.GridPane;

public record DragTileDoneController(GridPane rack, ImageView imageView) implements EventHandler<DragEvent> {

    @Override
    public void handle(DragEvent event) {
        if (event.getTransferMode() == null) {
            this.rack.getChildren().add(this.imageView);
        }
    }
}
