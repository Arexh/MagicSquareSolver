package com.arexh.magicsquare.ui.component;

import javafx.scene.layout.*;
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
        if (value != 0) {
            this.text = new Text(String.valueOf(value));
        } else {
            this.text = new Text("");
        }
        this.text.getStyleClass().add("square-text");
        getStyleClass().add("square");
        setOpacity(0.9);
        getChildren().add(text);
    }

    public void setValue(int value) {
        this.value = value;
        if (this.value != 0) {
            this.text.setText(String.valueOf(value));
        } else {
            this.text.setText("");
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getValue() {
        return value;
    }

    public void hoverHighlight() {
        getStyleClass().remove("square-hover-highlight");
        getStyleClass().add("square-hover-highlight");
    }

    public void hoverUnhighlight() {
        getStyleClass().remove("square-hover-highlight");
    }

    public void clickedHighlight() {
        getStyleClass().remove("square-click");
        getStyleClass().add("square-click");
    }

    public void clickedUnHighlight() {
        getStyleClass().remove("square-click");
    }
}
