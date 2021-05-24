package com.arexh.magicsquare.ui.component;

import com.arexh.magicsquare.algorithm.HelperFunction;
import com.jfoenix.controls.JFXSlider;
import javafx.scene.CacheHint;
import javafx.scene.layout.Pane;

public class MagicSquareBoard extends Pane {
    public static final int MAX_DIMENSION = 22;
    public static final double SIZE = 650;
    public static final double MARGIN_PROP = 0;
    public static final double CELL_MAX_SIZE = 40;
    public static final double CELL_MAX_WIDTH = 50;

    private BasicCell[][] cells;
    private int[][] square;
    private int dimension;
    private int magicConstant;

    public MagicSquareBoard(int dimension) {
        dimension += 2;
        this.dimension = dimension;
        this.square = new int[dimension][dimension];
        this.cells = new BasicCell[dimension][dimension];

        setCache(true);
        setCacheShape(true);
        setCacheHint(CacheHint.SPEED);

        initDefaultSquare();
        initCell();
        initMagicConstant();
        updateSumCell();
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
        dimension += 2;
        this.dimension = dimension;
        initMagicConstant();
        double averageSize = SIZE / dimension;
        double maxSize = Math.min(CELL_MAX_WIDTH, averageSize);
        double diffSize = maxSize - averageSize;
        averageSize = Math.min(CELL_MAX_SIZE, averageSize);
        double offset = (SIZE - maxSize * dimension) / 2;
        initDefaultSquare();
        for (int i = MAX_DIMENSION - 1; i >= 0; i--) {
            for (int j = MAX_DIMENSION - 1; j >= 0; j--) {
                BasicCell cell;
                if (i < dimension && j < dimension) {
                    if (i == 0 || i == dimension - 1 || j == 0 || j == dimension - 1) {
                        int row = i == dimension - 1 ? MAX_DIMENSION - 1 : i;
                        int column = j == dimension - 1 ? MAX_DIMENSION - 1 : j;
                        cells[i][j].setVisible(false);
                        cell = cells[row][column];
                        cell.setVisible(true);
                        cell.setValue(0);
                    } else {
                        cell = cells[i][j];
                        cell.setVisible(true);
                        cell.setValue(this.square[i - 1][j - 1]);
                    }
                    cell.setLayoutY(offset + i * maxSize - diffSize / 10);
                    cell.setLayoutX(offset + j * maxSize - diffSize / 10);
                    cell.setPrefWidth(averageSize);
                    cell.setPrefHeight(averageSize);
                } else {
                    cell = cells[i][j];
                    cell.setVisible(false);
                }
            }
        }
        updateSumCell();
    }

    private void updateSumCell() {
        int diagonalSum = 0;
        int antiDiagonalSum = 0;
        for (int i = 0; i < dimension - 2; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < dimension - 2; j++) {
                rowSum += this.square[i][j];
                colSum += this.square[j][i];
            }
            ((MagicSquareSumCell) this.cells[i + 1][0]).setValue(rowSum, magicConstant);
            ((MagicSquareSumCell) this.cells[i + 1][MAX_DIMENSION - 1]).setValue(rowSum, magicConstant);
            ((MagicSquareSumCell) this.cells[0][i + 1]).setValue(colSum, magicConstant);
            ((MagicSquareSumCell) this.cells[MAX_DIMENSION - 1][i + 1]).setValue(colSum, magicConstant);
            diagonalSum += this.square[i][i];
            antiDiagonalSum += this.square[i][dimension - 3 - i];
        }
        ((MagicSquareSumCell) this.cells[0][0]).setValue(diagonalSum, magicConstant);
        ((MagicSquareSumCell) this.cells[MAX_DIMENSION - 1][MAX_DIMENSION - 1]).setValue(diagonalSum, magicConstant);
        ((MagicSquareSumCell) this.cells[0][MAX_DIMENSION - 1]).setValue(antiDiagonalSum, magicConstant);
        ((MagicSquareSumCell) this.cells[MAX_DIMENSION - 1][0]).setValue(antiDiagonalSum, magicConstant);
    }

    private void initDefaultSquare() {
        int count = 1;
        for (int i = 0; i < dimension - 2; i++) {
            for (int j = 0; j < dimension - 2; j++) {
                this.square[i][j] = count++;
            }
        }
    }

    private void initCell() {
        double averageSize = SIZE / dimension;
        double margin = averageSize * MARGIN_PROP;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                BasicCell cell;
                if (i == 0 || i == dimension - 1 || j == 0 || j == dimension - 1) {
                    cell = new MagicSquareSumCell();
                } else {
                    cell = new BasicCell(i - 1, j - 1, square[i - 1][j - 1]);
                }
                this.cells[i][j] = cell;
                cell.setLayoutY(i * averageSize + margin);
                cell.setLayoutX(j * averageSize + margin);
                cell.setPrefWidth(averageSize);
                cell.setPrefHeight(averageSize);
                getChildren().add(cell);
            }
        }
    }

    private void initMagicConstant() {
        this.magicConstant = HelperFunction.magicConstant(this.dimension - 2);
    }
}
