module it.units.italiandraughts {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.units.italiandraughts to javafx.fxml;
    exports it.units.italiandraughts;
}