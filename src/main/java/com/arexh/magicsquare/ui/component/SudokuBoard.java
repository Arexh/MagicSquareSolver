package com.arexh.magicsquare.ui.component;

import javafx.scene.layout.Pane;

// from: https://stackoverflow.com/questions/36369224/creating-an-editable-paintable-grid-in-javafx
public class SudokuBoard extends Pane {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int DIMENSION = 3;
    private static final int LENGTH = DIMENSION * DIMENSION;
    private SudokuCell[][] cells;

    public SudokuBoard(int[][] square) {
        this.cells = new SudokuCell[LENGTH][LENGTH];
        initCell(square);
        configCellEventHandler();
    }

    private void initCell(int[][] square) {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                cells[i][j] = new SudokuCell(i, j, square[i][j]);
                addCellToUi(cells[i][j]);
            }
        }
    }

    private void addCellToUi(SudokuCell cell) {
        int row = cell.getRow();
        int column = cell.getColumn();
        cells[row][column] = cell;
        double w = WIDTH / LENGTH;
        double h = HEIGHT / LENGTH;
        double x = w * column;
        double y = h * row;
        cell.setLayoutX(x);
        cell.setLayoutY(y);
        cell.setPrefWidth(w);
        cell.setPrefHeight(h);
        getChildren().add(cell);
    }

    private void configCellEventHandler() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                configCellEventHandler(cells[i][j]);
            }
        }
    }

    private void configCellEventHandler(SudokuCell cell) {
        cell.hoverProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) {
                cell.highlight();
            } else {
                cell.unHighlight();
            }
        });
    }

    public void clearHighlight() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                cells[i][j].unHighlight();
            }
        }
    }
}
