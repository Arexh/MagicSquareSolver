package com.arexh.magicsquare.ui.component;

import javafx.scene.text.Text;

public class SudokuCell extends BasicCell {

    private boolean isChangeAble;

    public SudokuCell(int row, int column, int value, boolean isChangeAble) {
        this.row = row;
        this.column = column;
        this.value = value;
        if (value != 0) {
            this.text = new Text(String.valueOf(value));
        } else {
            this.text = new Text("");
        }
        this.isChangeAble = isChangeAble;
        getStyleClass().add("square");
        setOpacity(0.9);
        getChildren().add(text);
    }

    public SudokuCell(int row, int column, int value) {
        this(row, column, value, value == 0);
    }

    @Override
    public void setValue(int value) {
        if (!isChangeAble) return;
        this.value = value;
        if (this.value != 0) {
            this.text.setText(String.valueOf(value));
        } else {
            this.text.setText("");
        }
    }

    public void clickedHighlight() {
        getStyleClass().remove("square-click");
        getStyleClass().add("square-click");
    }

    public void clickedUnHighlight() {
        getStyleClass().remove("square-click");
    }
}
