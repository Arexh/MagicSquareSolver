package com.arexh.magicsquare.ui.component.board;

import com.arexh.magicsquare.algorithm.AlgorithmSolver;
import com.arexh.magicsquare.algorithm.HelperFunction;
import com.arexh.magicsquare.algorithm.MagicSquareSolver;
import com.arexh.magicsquare.ui.component.cell.BasicCell;
import com.arexh.magicsquare.ui.component.cell.MagicSquareSumCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
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
    public static int MIN_UPDATE_INTERVAL = 100;
    public static int MAX_UPDATE_INTERVAL = 2000;

    private final int[][] algorithmSquare;
    private final BasicCell[][] cells;
    private final int[][] square;
    private int dimension;
    private int magicConstant;
    private BasicCell draggedCell;
    private volatile boolean isUpdatingAlgorithmResult;
    private MagicSquareSolver magicSquareSolver;
    private boolean isAlgorithmPaused;
    private int updateInterval = MIN_UPDATE_INTERVAL;

    private JFXButton runAlgorithmBtn;
    private JFXButton pauseAlgorithmBtn;
    private JFXButton stopAlgorithmBtn;
    private JFXButton restoreSquareBoardBtn;
    private JFXToggleButton infiniteLoopBtn;
    private JFXSlider dimensionSlider;
    private JFXSlider updateIntervalSlider;

    private final Runnable updateAlgorithmResult = () -> {
        while (isUpdatingAlgorithmResult) {
            setSquare(MagicSquareBoard.this.algorithmSquare);
            try {
                Thread.sleep(updateInterval);
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
        initButton();
    }

    private void initButton() {
        this.runAlgorithmBtn = new JFXButton("Start Algorithm");
        runAlgorithmBtn.getStyleClass().add("sudoku-back-button");
        runAlgorithmBtn.setLayoutX(SIZE + 20);
        runAlgorithmBtn.setLayoutY(200);

        this.restoreSquareBoardBtn = new JFXButton("Restore");
        restoreSquareBoardBtn.getStyleClass().add("sudoku-back-button");
        restoreSquareBoardBtn.setLayoutX(SIZE + 20);
        restoreSquareBoardBtn.setLayoutY(250);

        this.pauseAlgorithmBtn = new JFXButton("Pause");
        pauseAlgorithmBtn.getStyleClass().add("sudoku-back-button");
        pauseAlgorithmBtn.setLayoutX(SIZE + 20);
        pauseAlgorithmBtn.setLayoutY(300);
        pauseAlgorithmBtn.setDisable(true);

        this.stopAlgorithmBtn = new JFXButton("Stop");
        stopAlgorithmBtn.getStyleClass().add("sudoku-back-button");
        stopAlgorithmBtn.setLayoutX(SIZE + 110);
        stopAlgorithmBtn.setLayoutY(300);
        stopAlgorithmBtn.setDisable(true);

        this.infiniteLoopBtn = new JFXToggleButton();
        infiniteLoopBtn.setText("Infinite");
        infiniteLoopBtn.setLayoutX(SIZE + 20);
        infiniteLoopBtn.setLayoutY(350);

        runAlgorithmBtn.setOnMouseClicked(event -> {
            updateAlgorithmResult();
            algorithmRunning();
            this.magicSquareSolver = new MagicSquareSolver(dimension - 2);
            this.magicSquareSolver.run(new AlgorithmSolver.SolverCallBack() {
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
                    Platform.runLater(() -> {
                        setSquare(MagicSquareBoard.this.algorithmSquare);
                        algorithmStopped();
                    });
                }
            }, infiniteLoopBtn.isSelected());
        });

        pauseAlgorithmBtn.setOnMousePressed(event -> {
            if (!isAlgorithmPaused) {
                isAlgorithmPaused = true;
                magicSquareSolver.pause();
                pauseAlgorithmBtn.setText("Resume");
            } else {
                isAlgorithmPaused = false;
                magicSquareSolver.resume();
                pauseAlgorithmBtn.setText("Pause");
            }
        });

        restoreSquareBoardBtn.setOnMousePressed(event -> {
            initDefaultSquare();
            setSquare(this.square);
        });

        stopAlgorithmBtn.setOnMousePressed(event -> {
            magicSquareSolver.stop();
            algorithmStopped();
        });


        getChildren().addAll(runAlgorithmBtn, restoreSquareBoardBtn, pauseAlgorithmBtn, stopAlgorithmBtn, infiniteLoopBtn);
    }

    private void algorithmRunning() {
        this.isAlgorithmPaused = false;
        this.runAlgorithmBtn.setDisable(true);
        this.restoreSquareBoardBtn.setDisable(true);
        this.pauseAlgorithmBtn.setDisable(false);
        this.stopAlgorithmBtn.setDisable(false);
        this.infiniteLoopBtn.setDisable(true);
        this.dimensionSlider.setDisable(true);
    }

    private void algorithmStopped() {
        this.isAlgorithmPaused = false;
        this.isUpdatingAlgorithmResult = false;
        this.runAlgorithmBtn.setDisable(false);
        this.restoreSquareBoardBtn.setDisable(false);
        this.pauseAlgorithmBtn.setDisable(true);
        this.stopAlgorithmBtn.setDisable(true);
        this.pauseAlgorithmBtn.setText("Pause");
        this.infiniteLoopBtn.setDisable(false);
        this.dimensionSlider.setDisable(false);

    }

    private void updateAlgorithmResult() {
        if (isUpdatingAlgorithmResult) return;
        isUpdatingAlgorithmResult = true;
        new Thread(updateAlgorithmResult).start();
    }

    private void initSlider() {
        this.dimensionSlider = new JFXSlider(3, 20, 20);
        dimensionSlider.setLayoutX(SIZE + 15);
        dimensionSlider.setLayoutY(100);
        dimensionSlider.setPrefWidth(150);
        dimensionSlider.setPrefHeight(20);
        dimensionSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            dimensionSlider.setValue(newVal.intValue());
            if (oldval.intValue() != newVal.intValue()) {
                resize(newVal.intValue());
            }
        });

        this.updateIntervalSlider = new JFXSlider(MIN_UPDATE_INTERVAL, MAX_UPDATE_INTERVAL, MIN_UPDATE_INTERVAL);
        updateIntervalSlider.setLayoutX(SIZE + 15);
        updateIntervalSlider.setLayoutY(140);
        updateIntervalSlider.setPrefWidth(150);
        updateIntervalSlider.setPrefHeight(20);
        updateIntervalSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            updateIntervalSlider.setValue(newVal.intValue());
            if (oldval.intValue() != newVal.intValue()) {
                updateInterval = newVal.intValue();
            }
        });

        getChildren().addAll(dimensionSlider, updateIntervalSlider);
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
