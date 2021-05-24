package com.arexh.magicsquare.ui.component.board;

import com.arexh.magicsquare.model.MessageEvent;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.scene.layout.Pane;
import org.greenrobot.eventbus.EventBus;

import static com.arexh.magicsquare.model.MessageEvent.MessageType.UPDATE_SUDOKU_KEYBOARD;

public class SudokuPlayerKeyBoard extends Pane {
    public final static int BUTTON_NUM = 9;
    public final static int BUTTON_WIDTH = 50;
    public final static int BUTTON_HEIGHT = 50;
    public final static int BUTTON_HORIZONTAL_MARGIN = 22;
    public final static int BUTTON_VERTICAL_MARGIN = 15;
    public final static int LINE_BUTTON_NUM = 3;
    private JFXButton[] buttons;

    public SudokuPlayerKeyBoard() {
        this.buttons = new JFXButton[BUTTON_NUM];
        initKeyBoard();
        initButton();
    }

    private void initKeyBoard() {
        int offsetY = 0;
        for (int i = 0; i < BUTTON_NUM; i++) {
            int lineIndex = i % LINE_BUTTON_NUM;
            JFXButton keyButton = new JFXButton(String.valueOf(i + 1));
            buttons[i] = keyButton;
            keyButton.getStyleClass().add("sudoku-player-keyboard");
            keyButton.setLayoutX(lineIndex * (BUTTON_WIDTH + BUTTON_HORIZONTAL_MARGIN));
            keyButton.setLayoutY(offsetY);
            keyButton.setPrefWidth(BUTTON_WIDTH);
            keyButton.setPrefHeight(BUTTON_HEIGHT);
            keyButton.setOnMousePressed(event -> EventBus.getDefault().post(new MessageEvent(UPDATE_SUDOKU_KEYBOARD, Integer.valueOf(keyButton.getText()))));
            getChildren().add(keyButton);
            if (lineIndex == 2) {
                offsetY = offsetY + BUTTON_WIDTH + BUTTON_VERTICAL_MARGIN;
            }
        }
    }

    private void initButton() {
        JFXButton backButton = new JFXButton("Back", GlyphsDude.createIcon(FontAwesomeIcons.STEP_BACKWARD));
        backButton.setLayoutY((BUTTON_WIDTH + BUTTON_VERTICAL_MARGIN) * (double) BUTTON_NUM / LINE_BUTTON_NUM + 10);
        backButton.setPrefHeight(44);
        backButton.setPrefWidth(86);
        backButton.getStyleClass().add("sudoku-back-button");
        JFXButton pauseButton = new JFXButton("Back", GlyphsDude.createIcon(FontAwesomeIcons.PAUSE));
        pauseButton.setLayoutY((BUTTON_WIDTH + BUTTON_VERTICAL_MARGIN) * (double) BUTTON_NUM / LINE_BUTTON_NUM + 10);
        pauseButton.setLayoutX(110);
        pauseButton.setPrefHeight(44);
        pauseButton.setPrefWidth(86);
        pauseButton.getStyleClass().add("sudoku-back-button");
        JFXButton saveButton = new JFXButton("Save", GlyphsDude.createIcon(FontAwesomeIcons.SAVE));
        saveButton.setLayoutY((BUTTON_WIDTH + BUTTON_VERTICAL_MARGIN) * (double) BUTTON_NUM / LINE_BUTTON_NUM + 70);
        saveButton.setPrefHeight(44);
        saveButton.setPrefWidth(86);
        saveButton.getStyleClass().add("sudoku-back-button");
        JFXButton loadButton = new JFXButton("Load", GlyphsDude.createIcon(FontAwesomeIcons.UPLOAD));
        loadButton.setLayoutY((BUTTON_WIDTH + BUTTON_VERTICAL_MARGIN) * (double) BUTTON_NUM / LINE_BUTTON_NUM + 70);
        loadButton.setLayoutX(110);
        loadButton.setPrefHeight(44);
        loadButton.setPrefWidth(86);
        loadButton.getStyleClass().add("sudoku-back-button");
        getChildren().addAll(backButton, pauseButton, loadButton, saveButton);
    }
}
