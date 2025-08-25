package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.Board;

public interface OutputHandler {


    void showGameWinComment();


    void showGameLoseComment();

    void showStartComment();

    void showGameBoard(Board board);

    void showMessage(String message);

    void showCellActionComment();

    void showCellInputComment();

}
