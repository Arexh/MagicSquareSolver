package com.arexh.magicsquare.ui.component;

import javafx.css.PseudoClass;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

// ref: https://stackoverflow.com/questions/36369224/creating-an-editable-paintable-grid-in-javafx
public class SudokuBoard extends StackPane {
    public static final Logger logger = LogManager.getLogger(SudokuBoard.class);
    private static final int DIMENSION = 3;
    private static final double SIZE = 800;
    private static final int LENGTH = DIMENSION * DIMENSION;
    private static final double GROUP_MARGIN = 18;
    private static final double SQUARE_MARGIN = 10;
    private static final double SQUARE_SIZE = (SIZE - (DIMENSION - 1) * GROUP_MARGIN) / LENGTH;
    private SudokuCell[][] cells;
    private Pane[] rowPanes;
    private Pane[] columnPanes;
    private Pane[][] subSquarePanes;
    private Pane squarePane;
    private Pane backgroundPane;

    public SudokuBoard(int[][] square) {
        this.cells = new SudokuCell[LENGTH][LENGTH];
        this.rowPanes = new Pane[LENGTH];
        this.columnPanes = new Pane[LENGTH];
        this.subSquarePanes = new Pane[DIMENSION][DIMENSION];
        this.squarePane = new Pane();
        this.backgroundPane = new Pane();
        initBackground();
        initCell(square);
        initLine();
        configCellEventHandler();
        setHeight(SIZE);
        setWidth(SIZE);
        System.out.println(getHeight());
        System.out.println(getWidth());
        getChildren().add(backgroundPane);
        getChildren().add(squarePane);
    }

    private void initBackground() {
        int offsetY = 0;
        for (int i = 0; i < LENGTH; i++) {
            Pane rowPane = new Pane();
            rowPane.getStyleClass().add("sudoku-board-row");
            rowPanes[i] = rowPane;
            rowPane.setPrefWidth(SIZE);
            rowPane.setPrefHeight(SQUARE_SIZE);
            rowPane.setLayoutX(0);
            rowPane.setLayoutY(offsetY + i * SQUARE_SIZE);
            backgroundPane.getChildren().add(rowPane);
            if ((i + 1) % DIMENSION == 0) offsetY += GROUP_MARGIN;
        }
        int offsetX = 0;
        for (int i = 0; i < LENGTH; i++) {
            Pane columnPane = new Pane();
            columnPane.getStyleClass().add("sudoku-board-column");
            columnPanes[i] = columnPane;
            columnPane.setPrefWidth(SQUARE_SIZE);
            columnPane.setPrefHeight(SIZE);
            columnPane.setLayoutX(offsetX + i * SQUARE_SIZE);
            columnPane.setLayoutY(0);
            backgroundPane.getChildren().add(columnPane);
            if ((i + 1) % DIMENSION == 0) offsetX += GROUP_MARGIN;
        }
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                double startX = i * DIMENSION * SQUARE_SIZE + i * GROUP_MARGIN;
                double startY = j * DIMENSION * SQUARE_SIZE + j * GROUP_MARGIN;
                Pane subSquarePane = new Pane();
                subSquarePane.getStyleClass().add("sudoku-board-sub-square");
                subSquarePanes[i][j] = subSquarePane;
                subSquarePane.setPrefWidth(SQUARE_SIZE * DIMENSION);
                subSquarePane.setPrefHeight(SQUARE_SIZE * DIMENSION);
                subSquarePane.setLayoutY(startX);
                subSquarePane.setLayoutX(startY);
                backgroundPane.getChildren().add(subSquarePane);
            }
        }
    }

    private void initCell(int[][] square) {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                double offsetX = GROUP_MARGIN * i + DIMENSION * i * SQUARE_SIZE;
                double offsetY = GROUP_MARGIN * j + DIMENSION * j * SQUARE_SIZE;
                for (int x = 0; x < DIMENSION; x++) {
                    for (int y = 0; y < DIMENSION; y++) {
                        int row = i * DIMENSION + x;
                        int col = j * DIMENSION + y;
                        cells[row][col] = new SudokuCell(row, col, square[row][col]);
                        addCellToUi(cells[row][col], offsetX + SQUARE_SIZE * x, offsetY + SQUARE_SIZE * y);
                    }
                }
            }
        }
    }

    private void addCellToUi(SudokuCell cell, double y, double x) {
        int row = cell.getRow();
        int column = cell.getColumn();
        cells[row][column] = cell;
        cell.setLayoutX(x + SQUARE_MARGIN);
        cell.setLayoutY(y + SQUARE_MARGIN);
        cell.setPrefWidth(SQUARE_SIZE - 2 * SQUARE_MARGIN);
        cell.setPrefHeight(SQUARE_SIZE - 2 * SQUARE_MARGIN);
        squarePane.getChildren().add(cell);
    }

    private void initLine() {
        Line lineOne = new Line(DIMENSION * SQUARE_SIZE + GROUP_MARGIN / 2, 0, DIMENSION * SQUARE_SIZE + GROUP_MARGIN / 2, SIZE);
        Line lineTwo = new Line(0, DIMENSION * SQUARE_SIZE + GROUP_MARGIN / 2, SIZE, DIMENSION * SQUARE_SIZE + GROUP_MARGIN / 2);
        Line lineThree = new Line(0, DIMENSION * SQUARE_SIZE * 2 + GROUP_MARGIN * 1.5, SIZE, DIMENSION * SQUARE_SIZE * 2 + GROUP_MARGIN * 1.5);
        Line lineFour = new Line(DIMENSION * SQUARE_SIZE * 2 + GROUP_MARGIN * 1.5, 0, DIMENSION * SQUARE_SIZE * 2 + GROUP_MARGIN * 1.5, SIZE);
        lineOne.getStyleClass().add("suduku-board-line");
        lineTwo.getStyleClass().add("suduku-board-line");
        lineThree.getStyleClass().add("suduku-board-line");
        lineFour.getStyleClass().add("suduku-board-line");
        squarePane.getChildren().add(lineOne);
        squarePane.getChildren().add(lineTwo);
        squarePane.getChildren().add(lineThree);
        squarePane.getChildren().add(lineFour);
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
                cell.hoverHighlight();
            } else {
                cell.hoverUnhighlight();
            }
        });
        cell.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                updateValue(cell.getRow(), cell.getColumn(), 1);
            }
            if (event.isSecondaryButtonDown()) {
                updateValue(cell.getRow(), cell.getColumn(), 0);
            }
            cell.clickedHighlight();
        });
        cell.setOnMouseReleased(event -> cell.clickedUnHighlight());
    }

    public void updateValue(int row, int column, int value) {
        this.cells[row][column].setValue(value);
        highlightError();
    }

    private void highlightError() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                Set<Integer> squareSet = new HashSet<>();
                a: for (int x = 0; x < DIMENSION; x++) {
                    for (int y = 0; y < DIMENSION; y++) {
                        int r = i * DIMENSION + x;
                        int c = j * DIMENSION + y;
                        if (squareSet.contains(cells[r][c].getValue())) {
                            highlightSubSquare(i, j);
                            break a;
                        } else if (cells[r][c].getValue() != 0) {
                            squareSet.add(cells[r][c].getValue());
                        }
                        if (x == DIMENSION - 1 && y == DIMENSION - 1) unHighlightSubSquare(i, j);
                    }
                }
            }
        }
        for (int i = 0; i < LENGTH; i++) {
            Set<Integer> rowSet = new HashSet<>();
            Set<Integer> columnSet = new HashSet<>();
            for (int j = 0; j < LENGTH; j++) {
                if (rowSet.contains(cells[i][j].getValue())) {
                    highlightRow(i);
                    break;
                } else if (cells[i][j].getValue() != 0) {
                    rowSet.add(cells[i][j].getValue());
                }
                if (j == DIMENSION - 1) unHighlightRow(i);
            }
            for (int j = 0; j < LENGTH; j++) {
                if (columnSet.contains(cells[j][i].getValue())) {
                    highlightColumn(i);
                    break;
                } else if (cells[j][i].getValue() != 0) {
                    columnSet.add(cells[j][i].getValue());
                }
                if (j == DIMENSION - 1) unHighlightColumn(i);
            }
        }
    }

    public void highlightRow(int row) {
        logger.debug("[ROW ERROR] Row: " + row);
        rowPanes[row].pseudoClassStateChanged(PseudoClass.getPseudoClass("highlight"), true);
    }

    public void unHighlightRow(int row) {
        rowPanes[row].pseudoClassStateChanged(PseudoClass.getPseudoClass("highlight"), false);
    }

    public void highlightColumn(int column) {
        logger.debug("[COLUMN ERROR] Column: " + column);
        columnPanes[column].pseudoClassStateChanged(PseudoClass.getPseudoClass("highlight"), true);
    }

    public void unHighlightColumn(int column) {
        columnPanes[column].pseudoClassStateChanged(PseudoClass.getPseudoClass("highlight"), false);
    }

    public void highlightSubSquare(int i, int j) {
        logger.debug("[SUB SQUARE ERROR] X: " + i + ", Y: " + j);
        subSquarePanes[i][j].pseudoClassStateChanged(PseudoClass.getPseudoClass("highlight"), true);
    }

    public void unHighlightSubSquare(int i, int j) {
        subSquarePanes[i][j].pseudoClassStateChanged(PseudoClass.getPseudoClass("highlight"), false);
    }
}
