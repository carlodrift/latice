module fun.saelatice.latice {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens fun.saelatice.latice.controller to javafx.fxml;
    exports fun.saelatice.latice;
    opens fun.saelatice.latice.view to javafx.fxml;
}