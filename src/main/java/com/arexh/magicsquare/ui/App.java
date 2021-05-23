package com.arexh.magicsquare.ui;

import com.arexh.magicsquare.ui.component.SudokuBoard;
import com.arexh.magicsquare.ui.component.SudokuPlayerKeyBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    public static final double WIDTH = 1250;
    public static final double HEIGHT = 900;

    @Override
    public void start(Stage primaryStage) {
        try {
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
            sudokuPlayerKeyBoard.setLayoutX(SudokuBoard.SIZE + 120);
            sudokuPlayerKeyBoard.setLayoutY(200);
            Pane stackPane = new Pane();
            stackPane.getChildren().addAll(sudokuBoard, sudokuPlayerKeyBoard);
            Scene scene = new Scene(stackPane, WIDTH, HEIGHT);
            scene.getStylesheets().add(App.class.getClassLoader().getResource("css/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}