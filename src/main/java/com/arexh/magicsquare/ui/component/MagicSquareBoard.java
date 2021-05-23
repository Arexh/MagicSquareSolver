package com.arexh.magicsquare.ui.component;

import com.jfoenix.controls.JFXSlider;
import javafx.scene.CacheHint;
import javafx.scene.layout.Pane;

public class MagicSquareBoard extends Pane {
    public static final double MAX_DIMENSION = 20;
    public static final double SIZE = 650;
    public static final double MARGIN_PROP = 0;
    public static final double CELL_MAX_SIZE = 40;
    public static final double CELL_MAX_WIDTH = 50;

    private BasicCell[][] cells;
    private int[][] square;
    private int dimension;

    public MagicSquareBoard(int dimension) {
        this.dimension = dimension;
        this.square = new int[dimension][dimension];
        this.cells = new BasicCell[dimension][dimension];
        initDefaultSquare();
        initCell();
        initSlider();
    }

    private void initSlider() {
        JFXSlider slider = new JFXSlider(3, 20, 0);
        slider.setLayoutX(SIZE + 15);
        slider.setLayoutY(100);
        slider.setPrefWidth(150);
        slider.setPrefHeight(20);
        slider.valueProperty().addListener((obs, oldval, newVal) -> {
            slider.setValue(newVal.intValue());
            if (oldval.intValue() != newVal.intValue()) {
                resize(newVal.intValue());
            }
        });
        getChildren().add(slider);
    }

    private void resize(int dimension) {
        this.dimension = dimension;
        double averageSize = SIZE / dimension;
        double maxSize = Math.min(CELL_MAX_WIDTH, averageSize);
        double diffSize = maxSize - averageSize;
        averageSize = Math.min(CELL_MAX_SIZE, averageSize);
        double offset = (SIZE - maxSize * dimension) / 2;
        initDefaultSquare();
        for (int i = 0; i < MAX_DIMENSION; i++) {
            for (int j = 0; j < MAX_DIMENSION; j++) {
                BasicCell cell = cells[i][j];
                setCache(true);
                setCacheShape(true);
                setCacheHint(CacheHint.SPEED);
                if (i < dimension && j < dimension) {
                    cell.setVisible(true);
                    cell.setValue(this.square[i][j]);
                    cell.setLayoutY(offset + i * maxSize - diffSize / 10);
                    cell.setLayoutX(offset + j * maxSize - diffSize / 10);
                    cell.setPrefWidth(averageSize);
                    cell.setPrefHeight(averageSize);
                } else {
                    cell.setVisible(false);
                }
            }
        }
    }

    private void initDefaultSquare() {
        int count = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.square[i][j] = count++;
            }
        }
    }

    private void initCell() {
        double averageSize = SIZE / dimension;
        double margin = averageSize * MARGIN_PROP;
        for (int i = 0; i < MAX_DIMENSION; i++) {
            for (int j = 0; j < MAX_DIMENSION; j++) {
                BasicCell cell = new BasicCell(i, j, square[i][j]);
                this.cells[i][j] = cell;
                cell.setLayoutY(i * averageSize + margin);
                cell.setLayoutX(j * averageSize + margin);
                cell.setPrefWidth(averageSize);
                cell.setPrefHeight(averageSize);
                getChildren().add(cell);
            }
        }
    }
}
