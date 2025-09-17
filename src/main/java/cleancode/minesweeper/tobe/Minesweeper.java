package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.user.UserAction;

/**
 * 지뢰 찾기 게임 역할
 */
public class Minesweeper implements GameRunnable, GameInitializable {

    public static final int GAME_LOSE_STATUS = -1;
    public static final int GAME_WIN_STATUS = 1;
    private final Board board;
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public Minesweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        board = new Board(gameLevel);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    @Override
    public void init() {
        board.initGameBoard();
    }

    @Override
    public void run() {

        outputHandler.showStartComment();

        while (true) {
            try {

                outputHandler.showGameBoard(board);

                if (isUserWinTheGame()) {
                    outputHandler.showGameWinComment();
                    break;
                }

                if (isUserLoseTheGame()) {
                    outputHandler.showGameLoseComment();
                    break;
                }

                CellPosition cellPosition = inputCellPositionFromUser();
                UserAction userAction = inputActionFromUser();

                actionOnCell(cellPosition, userAction);
            } catch (AppException e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                outputHandler.showMessage("시스템 오류 발생.");
            }

        }
    }

    private void actionOnCell(CellPosition cellPosition, UserAction userAction) {


        if (choosePlantFlagAction(userAction)) {
            board.flag(cellPosition);
            checkIfGameOver();
            return;
        }

        if (chooseCellOpenAction(userAction)) {
            if (board.isLandMineCell(cellPosition)) {
                board.open(cellPosition);

                setGameStatusToLose();
                return;
            }

            board.openSurroundCell(cellPosition);

            checkIfGameOver();
            return;
        }

        outputHandler.showMessage("잘못된 번호를 선택하셨습니다.");
    }

    private boolean chooseCellOpenAction(UserAction userAction) {
        return UserAction.OPEN.equals(userAction);
    }

    private boolean choosePlantFlagAction(UserAction userAction) {
        return UserAction.FLAG.equals(userAction);
    }

    private void setGameStatusToLose() {
        gameStatus = GAME_LOSE_STATUS;
    }


    private UserAction inputActionFromUser() {
        outputHandler.showCellActionComment();
        return this.inputHandler.getUserActionFromUser();
    }

    private CellPosition inputCellPositionFromUser() {
        outputHandler.showCellInputComment();
        return this.inputHandler.getCellPositionFromUserInput();
    }

    private boolean isUserLoseTheGame() {
        return gameStatus == GAME_LOSE_STATUS;
    }

    private boolean isUserWinTheGame() {
        return gameStatus == GAME_WIN_STATUS;
    }

    private void checkIfGameOver() {
        if (board.isAllCellChecked()) {
            setGameStatusToWin();
        }
    }

    private static void setGameStatusToWin() {
        gameStatus = GAME_WIN_STATUS;
    }


}
