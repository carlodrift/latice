package fun.saelatice.latice.controller;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Player;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

public record PassController(BoardController boardController, Game game) implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent mouseEvent) {
        this.boardController.switchRackVisibility();
        this.game.checkOver();
        if (this.game.isOver()) {
            this.boardController.switchPassBtnVisibility();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Player winner = this.game.winner();
            String headerText = "Égalité !";
            if (winner != null) {
                String winnerName;
                winnerName = winner.playerName(this.game);
                headerText = winnerName + " est le vainqueur !";
            }
            alert.setHeaderText(headerText);
            alert.setContentText("La partie est terminée.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(this.game.nextPlayer().playerName(this.game) + ", à vous !");
        alert.setContentText("Préparez-vous à jouer...");
        ButtonType ready = new ButtonType("Je suis prêt !");
        alert.getButtonTypes().setAll(ready);
        alert.showAndWait();
        this.game.goNextPlayer();
        this.boardController.fillRack(this.game.getCurrentPlayer());
        this.boardController.updateCurrentPlayer(this.game);
        this.boardController.switchRackVisibility();
        if (this.game.getCurrentPlayer().getPoints() >= Board.MOVE_PRICE) {
            this.boardController.switchChangeRackDisable();
        }
    }
}
