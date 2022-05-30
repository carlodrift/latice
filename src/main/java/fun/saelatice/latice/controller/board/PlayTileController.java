package fun.saelatice.latice.controller.board;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Position;
import fun.saelatice.latice.model.tile.Tile;
import javafx.event.EventHandler;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

public record PlayTileController(DataFormat format, Board board, Position position, Game game,
                                 BoardController boardController) implements EventHandler<DragEvent> {

    @Override
    public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.getContent(this.format) == null) {
            return;
        }
        Tile tile = (Tile) db.getContent(this.format);
        if (this.board.canPlayHere(this.position, tile)) {
            this.board.playTile(this.position, tile, this.game.getCurrentPlayer());
            event.setDropCompleted(true);
            this.boardController.loadSound(tile.shape().toString().toLowerCase() + "-played.wav", true);
            this.boardController.updateCurrentPlayer(this.game);
            this.boardController.fillBoard(this.board, this.game);
            if (this.game.getCurrentPlayer().getPoints() >= Board.MOVE_PRICE) {
                this.boardController.switchChangeRackDisable();
            } else {
                PassController passController = new PassController(this.boardController, this.game);
                passController.handle(null);
            }
        } else {
            this.boardController.loadSound(tile.shape().toString().toLowerCase() + "-failed.wav", true);
        }
    }
}
