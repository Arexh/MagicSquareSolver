package com.arexh.magicsquare.ui.component;

import com.jfoenix.controls.JFXButton;

public class BasicCell extends JFXButton {
    protected int value;
    protected int row;
    protected int column;

    public BasicCell() {}

    public BasicCell(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
        getStyleClass().add("square");
        setOpacity(0.9);
        setText(String.valueOf(this.value));
    }

    public void setValue(int value) {
        this.value = value;
        setText(String.valueOf(value));
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
}
