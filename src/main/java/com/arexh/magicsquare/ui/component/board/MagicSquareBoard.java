package com.arexh.magicsquare.ui.component.board;

import com.arexh.magicsquare.algorithm.AlgorithmSolver;
import com.arexh.magicsquare.algorithm.HelperFunction;
import com.arexh.magicsquare.algorithm.MagicSquareSolver;
import com.arexh.magicsquare.ui.component.cell.BasicCell;
import com.arexh.magicsquare.ui.component.cell.MagicSquareSumCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.scene.CacheHint;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MagicSquareBoard extends Pane {
    private static final DataFormat JAVA_FORMAT = new DataFormat("application/x-java-serialized-object");
    public static final int MAX_DIMENSION = 22;
    public static final double SIZE = 650;
    public static final double MARGIN_PROP = 0;
    public static final double CELL_MAX_SIZE = 40;
    public static final double CELL_MAX_WIDTH = 50;
    public static final long MIN_UPDATE_INTERVAL = 100;

    private final int[][] algorithmSquare;
    private final BasicCell[][] cells;
    private final int[][] square;
    private int dimension;
    private int magicConstant;
    private BasicCell draggedCell;
    private volatile boolean isUpdatingAlgorithmResult;
    private final Runnable updateAlgorithmResult = () -> {
        while (isUpdatingAlgorithmResult) {
            setSquare(MagicSquareBoard.this.algorithmSquare);
            try {
                Thread.sleep(MIN_UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public MagicSquareBoard() {
        this.dimension = MAX_DIMENSION;
        this.square = new int[MAX_DIMENSION][MAX_DIMENSION];
        this.cells = new BasicCell[MAX_DIMENSION][MAX_DIMENSION];
        this.algorithmSquare = new int[MAX_DIMENSION][MAX_DIMENSION];

        setCache(true);
        setCacheShape(true);
        setCacheHint(CacheHint.SPEED);

        initDefaultSquare();
        initCell();
        initMagicConstant();
        updateSumCell();
        initSlider();
        JFXButton startAlgorithm = new JFXButton("Algorithm");
        startAlgorithm.getStyleClass().add("sudoku-back-button");
        startAlgorithm.setLayoutX(SIZE + 20);
        startAlgorithm.setLayoutY(200);
        startAlgorithm.setOnMouseClicked(event -> {
            updateAlgorithmResult();
            startAlgorithm.setDisable(true);
            MagicSquareSolver magicSquareSolver = new MagicSquareSolver(dimension - 2);
            magicSquareSolver.run(new AlgorithmSolver.SolverCallBack() {
                @Override
                public void onReheat() {

                }

                @Override
                public void onSquareChanged(int[][] square) {
                    setAlgorithmSquare(square);
                }

                @Override
                public void onFinish() {
                    isUpdatingAlgorithmResult = false;
                    Platform.runLater(() -> setSquare(MagicSquareBoard.this.algorithmSquare));
                    startAlgorithm.setDisable(false);
                }
            });
        });
        getChildren().add(startAlgorithm);
    }

    private void updateAlgorithmResult() {
        isUpdatingAlgorithmResult = true;
        new Thread(updateAlgorithmResult).start();
    }

    private void initSlider() {
        JFXSlider slider = new JFXSlider(3, 20, 20);
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
                this.algorithmSquare[i][j] = square[i][j];
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
                    configDragEvent(cell);
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

    private void configDragEvent(BasicCell cell) {
        cell.setOnDragDetected(event -> {
            draggedCell = cell;
            Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(JAVA_FORMAT, draggedCell.getValue());
            db.setContent(content);
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            db.setDragView(cell.snapshot(parameters, null));
            event.consume();
        });
        cell.setOnDragOver(event -> {
            if (!event.getDragboard().hasContent(JAVA_FORMAT)) return;
            if (draggedCell.getValue() == cell.getValue()) return;
            event.acceptTransferModes(TransferMode.MOVE);
        });
        cell.setOnDragDropped(event -> {
            int temp = draggedCell.getValue();
            draggedCell.setValue(cell.getValue());
            cell.setValue(temp);
            this.square[draggedCell.getRow()][draggedCell.getColumn()] = draggedCell.getValue();
            this.square[cell.getRow()][cell.getColumn()] = cell.getValue();
            event.setDropCompleted(true);
        });
        cell.setOnDragDone(event -> {
            updateSumCell();
        });
    }

    private void setSquare(int[][] square) {
        for (int i = 1; i < dimension - 1; i++) {
            for (int j = 1; j < dimension - 1; j++) {
                this.square[i - 1][j - 1] = square[i - 1][j - 1];
                this.cells[i][j].setValue(square[i - 1][j - 1]);
            }
        }
        updateSumCell();
    }

    private void setAlgorithmSquare(int[][] square) {
        for (int i = 0; i < dimension - 2; i++) {
            for (int j = 0; j < dimension - 2; j++) {
                this.algorithmSquare[i][j] = square[i][j];
            }
        }
    }

    private void initMagicConstant() {
        this.magicConstant = HelperFunction.magicConstant(this.dimension - 2);
    }
}
