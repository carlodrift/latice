package fun.saelatice.latice.controller;

import fun.saelatice.latice.model.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public record ChangeRackController(BoardController boardController, Game game) implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        this.game.getCurrentPlayer().changeRack(true);
        PassController passController = new PassController(this.boardController, this.game);
        passController.handle(null);
    }
}
