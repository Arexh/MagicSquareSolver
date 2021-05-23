package com.arexh.magicsquare.ui;

import com.arexh.magicsquare.ui.component.MagicSquareBoard;
import com.arexh.magicsquare.ui.component.SudokuBoard;
import com.arexh.magicsquare.ui.component.SudokuPlayerKeyBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    public static final double WIDTH = 850;
    public static final double HEIGHT = 700;

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setScene(magicSquareScene());
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Scene magicSquareScene() {
        Pane pane = new Pane();
        pane.getChildren().add(new MagicSquareBoard(20));
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getClassLoader().getResource("css/application.css")).toExternalForm());
        return scene;
    }

    private Scene sudokuScene() {
        SudokuBoard sudokuBoard = new SudokuBoard(new int[][]{
                {0, 2, 4, 0, 0, 7, 0, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 8, 0, 4, 1, 5},
                {4, 3, 1, 0, 0, 5, 0, 0, 0},
                {5, 0, 0, 0, 0, 0, 0, 3, 2},
                {7, 9, 0, 0, 0, 0, 0, 6, 0},
                {2, 0, 9, 7, 1, 0, 8, 0, 0},
                {0, 4, 0, 0, 9, 3, 0, 0, 0},
                {3, 1, 0, 0, 0, 4, 7, 5, 0}});
        SudokuPlayerKeyBoard sudokuPlayerKeyBoard = new SudokuPlayerKeyBoard();
        sudokuPlayerKeyBoard.setLayoutX(SudokuBoard.SIZE + 80);
        sudokuPlayerKeyBoard.setLayoutY(260);
        Pane pane = new Pane();
        pane.getChildren().addAll(sudokuBoard, sudokuPlayerKeyBoard);
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getClassLoader().getResource("css/application.css")).toExternalForm());
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}