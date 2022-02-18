package it.units.italiandraughts.ui;

import it.units.italiandraughts.ItalianDraughts;
import it.units.italiandraughts.event.EventType;
import it.units.italiandraughts.event.GameEvent;
import it.units.italiandraughts.event.GameEventListener;
import it.units.italiandraughts.logic.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class BoardController implements GameEventListener {

    @FXML
    GridPane gridPane;

    @FXML
    VBox rowNumbers;

    @FXML
    HBox columnLetters;

    @FXML
    Label player1NameLabel, player2NameLabel;

    @FXML
    Line line;

    @FXML
    Button undoButton;

    @FXML
    Button resetButton;

    private Game game;

    private static double getGridPaneHeight() {
        return StaticUtil.getScreenHeight() / 3 * 2;
    }

    private void showActivePlayerInBold(Player activePlayer) {
        if (activePlayer.getName().equals(player1NameLabel.getText())) {
            player1NameLabel.setStyle("-fx-font-weight: bold;");
            player2NameLabel.setStyle("-fx-font-weight: normal;");
        } else {
            player1NameLabel.setStyle("-fx-font-weight: normal;");
            player2NameLabel.setStyle("-fx-font-weight: bold;");
        }
    }

    private void addGridPaneConstraints() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(12.5);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(12.5);
        for (int i = 0; i < Board.SIZE; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
            gridPane.getRowConstraints().add(rowConstraints);
        }
    }

    public void initializeWindow() {
        gridPane.setMinSize(getGridPaneHeight(), getGridPaneHeight());
        gridPane.setMaxSize(getGridPaneHeight(), getGridPaneHeight());

        Player player1 = new Player(player1NameLabel.getText(), PieceColor.WHITE);
        Player player2 = new Player(player2NameLabel.getText(), PieceColor.BLACK);

        game = new Game(player1, player2);
        addGridPaneConstraints();
        BoardDrawer boardDrawer = new BoardDrawer(gridPane, game);
        game.addListeners(EventType.GAME_OVER, this);
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER, this, boardDrawer);

        resizeNumberLabels();

        Platform.runLater(() -> columnLetters.setPadding(
                new Insets(0, 0, 0, rowNumbers.getWidth())
        ));

        resizeLetterLabels();

        line.setEndX(gridPane.getMaxWidth());
        gridPane.setStyle("-fx-border-color: #d47d35");
        resetButton.setOnAction(event -> resetWindow());
        undoButton.setOnAction(event -> game.undo());
        undoButton.setDisable(true);
        showActivePlayerInBold(player1);
    }

    private void resizeLetterLabels() {
        List<Node> columnLabels = columnLetters.getChildren();
        columnLabels.forEach(node -> {
            Label label = (Label) node;
            label.setMaxWidth(getGridPaneHeight() / Board.SIZE);
            label.setMinWidth(getGridPaneHeight() / Board.SIZE);
        });
    }

    private void resizeNumberLabels() {
        List<Node> rowLabels = rowNumbers.getChildren();
        rowLabels.forEach(node -> {
            Label label = (Label) node;
            label.setMaxHeight(getGridPaneHeight() / Board.SIZE);
            label.setMinHeight(getGridPaneHeight() / Board.SIZE);
        });
    }

    @Override
    public void onGameEvent(GameEvent event) {
        switch (event.getEventType()) {
            case GAME_OVER -> {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndgameLayout.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load(), StaticUtil.getScreenWidth() / 5f, StaticUtil.getScreenHeight() / 5f);
                    EndgameController controller = fxmlLoader.getController();
                    controller.setWinner(game.getWinnerPlayer());
                    controller.initializeWindow();
                    Stage stage = new Stage();
                    controller.newGameButton.setOnAction((clickEvent) -> {
                        resetWindow();
                        stage.hide();
                    });
                    stage.setScene(scene);
                    stage.setTitle("Game over");
                    stage.getIcons().add(new Image(Objects.requireNonNull(ItalianDraughts.class.getResourceAsStream("ui/img/icon.png"))));
                    ItalianDraughts.setupStage(stage);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case SWITCH_ACTIVE_PLAYER -> {
                showActivePlayerInBold(game.getActivePlayer());
                undoButton.setDisable(game.getMoves().size() <= 0);
            }
        }
    }

    private void resetWindow() {
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        initializeWindow();
    }

}
