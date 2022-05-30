module fun.saelatice.latice {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    exports fun.saelatice.latice;
    opens fun.saelatice.latice.view to javafx.fxml;
    opens fun.saelatice.latice.controller.board to javafx.fxml;
    opens fun.saelatice.latice.controller.welcome to javafx.fxml;
}