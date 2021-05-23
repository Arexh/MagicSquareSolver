package com.arexh.magicsquare.ui.component;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class BasicCell extends StackPane {
    protected Text text;
    protected int value;
    protected int row;
    protected int column;

    public BasicCell() {}

    public BasicCell(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.text = new Text(String.valueOf(value));
        configEvent();
        getStyleClass().add("square");
        setOpacity(0.9);
        getChildren().add(this.text);
    }

    private void configEvent() {
        hoverProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) {
                hoverHighlight();
            } else {
                hoverUnhighlight();
            }
        });
    }

    public void setValue(int value) {
        this.value = value;
        this.text.setText(String.valueOf(value));
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
}
