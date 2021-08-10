module it.units.italiandraughts {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.units.italiandraughts to javafx.fxml;
    exports it.units.italiandraughts;
    exports it.units.italiandraughts.ui;
    opens it.units.italiandraughts.ui to javafx.fxml;
    exports it.units.italiandraughts.logic;
    opens it.units.italiandraughts.logic to javafx.fxml;
}