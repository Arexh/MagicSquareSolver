package com.arexh.magicsquare.ui;

import com.arexh.magicsquare.ui.component.SudokuBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    double width = 800;
    double height = 600;

    ImageView imageView = new ImageView(new Image("https://upload.wikimedia.org/wikipedia/commons/c/c7/Pink_Cat_2.jpg"));

    @Override
    public void start(Stage primaryStage) {
        try {
            SudokuBoard sudokuBoard = new SudokuBoard(new int[][]{
                {1, 2, 4, 1, 5, 7, 6, 9, 2},
                {6, 5, 9, 3, 4, 2, 3, 8, 7},
                {8, 7, 3, 6, 8, 9, 4, 1, 5},
                {4, 3, 1, 6, 8, 5, 4, 7, 9},
                {5, 2, 6, 7, 1, 9, 1, 3, 2},
                {7, 9, 8, 4, 3, 2, 8, 6, 5},
                {2, 7, 9, 7, 1, 8, 8, 2, 1},
                {8, 4, 6, 2, 9, 3, 9, 3, 4},
                {3, 1, 5, 5, 6, 4, 7, 5, 6},
            });
            StackPane stackPane = new StackPane(imageView, sudokuBoard);
            Scene scene = new Scene(stackPane, width, height);
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