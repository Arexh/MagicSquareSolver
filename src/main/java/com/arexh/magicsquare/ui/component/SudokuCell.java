package com.arexh.magicsquare.ui.component;

public class SudokuCell extends BasicCell {

    private boolean isChangeAble;

    public SudokuCell(int row, int column, int value, boolean isChangeAble) {
        this.row = row;
        this.column = column;
        this.value = value;
        if (value != 0) {
            setText(String.valueOf(value));
        }
        this.isChangeAble = isChangeAble;
        getStyleClass().add("square");
        setOpacity(0.9);
    }

    public SudokuCell(int row, int column, int value) {
        this(row, column, value, value == 0);
    }

    @Override
    public void setValue(int value) {
        if (!isChangeAble) return;
        this.value = value;
        if (this.value != 0) {
            setText(String.valueOf(value));
        } else {
            setText("");
        }
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
