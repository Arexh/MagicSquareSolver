package com.arexh.magicsquare.ui;

import com.arexh.magicsquare.ui.component.SudokuBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    public static final double WIDTH = 1100;
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
            Scene scene = new Scene(sudokuBoard, WIDTH, HEIGHT);
            scene.getStylesheets().add(App.class.getClassLoader().getResource("css/application.css").toExternalForm());
//            primaryStage.setResizable(false);
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