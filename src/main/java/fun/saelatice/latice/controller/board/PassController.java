package fun.saelatice.latice.controller.board;

import fun.saelatice.latice.Latice;
import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Player;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public record PassController(BoardController boardController, Game game) implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent mouseEvent) {
        this.boardController.disableChangeRack();
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
            ButtonType ready = new ButtonType("Retour à l'accueil");
            alert.getButtonTypes().setAll(ready);
            alert.showAndWait();
            Stage stage = Latice.stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Latice.class.getResource("welcome-view.fxml"));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                return;
            }
            stage.setScene(scene);
            stage.show();
            return;
        }
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
