package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;

public interface OutputHandler {


    void showGameWinComment();


    void showGameLoseComment();

    void showStartComment();

    void showGameBoard(GameBoard gameBoard);

    void showMessage(String message);

    void showCellActionComment();

    void showCellInputComment();

}
