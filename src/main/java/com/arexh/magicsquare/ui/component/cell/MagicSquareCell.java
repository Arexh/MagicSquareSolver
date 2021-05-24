package com.arexh.magicsquare.ui.component.cell;

import javafx.css.PseudoClass;

public class MagicSquareCell extends BasicCell {

    public MagicSquareCell(int row, int column, int value) {
        super(row, column, value);
    }

    public void select() {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    }

    public void unSelect() {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
    }
}
