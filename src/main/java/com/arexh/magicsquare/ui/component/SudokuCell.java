package com.arexh.magicsquare.ui.component;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SudokuCell extends StackPane {

    private Text text;
    private int value;
    private int row;
    private int column;

    public SudokuCell(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.text = new Text(String.valueOf(value));
        this.text.getStyleClass().add("square-text");
        getStyleClass().add("square");
        setOpacity(0.9);
        getChildren().add(text);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void highlight() {
        getStyleClass().remove("square-highlight");
        getStyleClass().add("square-highlight");
    }

    public void unHighlight() {
        getStyleClass().remove("square-highlight");
    }

    public void hoverHighlight() {
        getStyleClass().remove("square-hover-highlight");
        getStyleClass().add("square-hover-highlight");
    }

    public void hoverUnhighlight() {
        getStyleClass().remove("square-hover-highlight");
    }
}
