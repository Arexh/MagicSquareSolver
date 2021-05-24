package com.arexh.magicsquare.ui.component.cell;

import javafx.css.PseudoClass;
import javafx.scene.text.Text;

public class MagicSquareSumCell extends BasicCell {
    public MagicSquareSumCell() {
        this.text = new Text(String.valueOf(value));
        getStyleClass().add("magic-square-sum-cell");
        getChildren().add(this.text);
    }

    public void setValue(int value, int magicConstant) {
        setValue(value);
        if (value < magicConstant) {
            tooLow();
        } else if (value > magicConstant) {
            tooHigh();
        } else {
            just();
        }
    }

    private void tooHigh() {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("high"), true);
        pseudoClassStateChanged(PseudoClass.getPseudoClass("low"), false);
        pseudoClassStateChanged(PseudoClass.getPseudoClass("just"), false);
    }

    private void tooLow() {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("high"), false);
        pseudoClassStateChanged(PseudoClass.getPseudoClass("low"), true);
        pseudoClassStateChanged(PseudoClass.getPseudoClass("just"), false);
    }

    private void just() {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("high"), false);
        pseudoClassStateChanged(PseudoClass.getPseudoClass("low"), false);
        pseudoClassStateChanged(PseudoClass.getPseudoClass("just"), true);
    }
}
