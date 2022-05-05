module fun.saelatice.latice {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens fun.saelatice.latice to javafx.fxml;
    exports fun.saelatice.latice;
}