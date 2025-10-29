package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;

public interface OutputHandler {


    void showGameWinComment();


    void showGameLoseComment();

    void showStartComment();

    void showGameBoard(GameBoard gameBoard);

    void showMessage(String message);

    void showCellActionComment();

    void showCellInputComment();

}
