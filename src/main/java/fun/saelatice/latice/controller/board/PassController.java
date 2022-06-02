package fun.saelatice.latice.controller.board;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Player;
import fun.saelatice.latice.model.tile.TileColor;
import fun.saelatice.latice.model.tile.TileShape;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public record PassController(BoardController boardController, Game game) implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent mouseEvent) {
        this.boardController.disableChangeRack();
        this.game.checkOver();
        if (this.game.isOver()) {
            this.boardController.switchPassBtnVisibility();
            Player winner = this.game.winner();
            int tiles = TileColor.values().length * TileShape.values().length;
            if (winner != null) {
                String winnerName;
                winnerName = winner.playerName(this.game);
                this.boardController.winAlert(winnerName, this.game.getPlayer1().playerName(this.game),
                        this.game.getPlayer2().playerName(this.game),
                        tiles - this.game.getPlayer1().getPool().size() - this.game.getPlayer1().getRack().size(),
                        tiles - this.game.getPlayer2().getPool().size() - this.game.getPlayer2().getRack().size()
                );
            } else {
                this.boardController.drawAlert(tiles - this.game.getCurrentPlayer().getPool().size() - this.game.getCurrentPlayer().getRack().size());
            }

        }
        this.boardController.hideSettingsMenu();
        this.boardController.setNextPlayerText(this.game.nextPlayer().playerName(this.game) + ", à vous de jouer !");
        this.boardController.nextPlayerAlert();
        this.game.goNextPlayer();
        this.boardController.fillRack(this.game.getCurrentPlayer());
        this.boardController.updateCurrentPlayer(this.game);
        this.boardController.disableChangeRack();
        if (this.game.getCurrentPlayer().getPoints() >= Board.MOVE_PRICE) {
            this.boardController.enableChangeRack();
        }
    }


}
