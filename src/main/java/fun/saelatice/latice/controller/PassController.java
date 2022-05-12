package fun.saelatice.latice.controller;

import fun.saelatice.latice.model.Game;
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
            alert.setHeaderText("Il y a un vainqueur !");
            alert.setContentText("La partie est terminée.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(this.game.getPlayer(true) + ", à vous !");
        alert.setContentText("Préparez-vous à jouer...");
        ButtonType ready = new ButtonType("Je suis prêt !");
        alert.getButtonTypes().setAll(ready);
        alert.showAndWait();
        this.game.nextPlayer();
        this.boardController.fillRack(this.game.getCurrentPlayer());
        this.boardController.updateCurrentPlayer(this.game);
        this.boardController.switchRackVisibility();
    }
}
