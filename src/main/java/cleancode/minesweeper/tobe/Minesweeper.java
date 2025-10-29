package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.config.GameConfig;
import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.user.UserAction;

/**
 * 지뢰 찾기 게임 역할
 */
public class Minesweeper implements GameRunnable, GameInitializable {
    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public Minesweeper(GameConfig gameConfig) {

        gameBoard = new GameBoard(gameConfig.getGameLevel());
        this.inputHandler = gameConfig.getInputHandler();
        this.outputHandler = gameConfig.getOutputHandler();
    }
    @Override
    public void init() {
        gameBoard.initGameBoard();
    }

    @Override
    public void run() {

        outputHandler.showStartComment();

        while (gameBoard.isInProgress()) {
            try {
                outputHandler.showGameBoard(gameBoard);

                CellPosition cellPosition = inputCellPositionFromUser();
                UserAction userAction = inputActionFromUser();

                actionOnCell(cellPosition, userAction);
            } catch (AppException e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                outputHandler.showMessage("시스템 오류 발생.");
            }

        }

        outputHandler.showGameBoard(gameBoard);

        if (gameBoard.isWinStatus()) {
            outputHandler.showGameWinComment();
        }
        if (gameBoard.isLoseStatus()) {
            outputHandler.showGameLoseComment();
        }
    }

    private void actionOnCell(CellPosition cellPosition, UserAction userAction) {


        if (choosePlantFlagAction(userAction)) {
            gameBoard.flagAt(cellPosition);
            return;
        }

        if (chooseCellOpenAction(userAction)) {
            gameBoard.openAt(cellPosition);

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

    private UserAction inputActionFromUser() {
        outputHandler.showCellActionComment();
        return this.inputHandler.getUserActionFromUser();
    }

    private CellPosition inputCellPositionFromUser() {
        outputHandler.showCellInputComment();
        return this.inputHandler.getCellPositionFromUserInput();
    }
}
