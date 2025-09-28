package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.config.GameConfig;
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
    private final Board board;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public Minesweeper(GameConfig gameConfig) {

        board = new Board(gameConfig.getGameLevel());
        this.inputHandler = gameConfig.getInputHandler();
        this.outputHandler = gameConfig.getOutputHandler();
    }
    @Override
    public void init() {
        board.initGameBoard();
    }

    @Override
    public void run() {

        outputHandler.showStartComment();

        while (board.isInProgress()) {
            try {
                outputHandler.showGameBoard(board);

                CellPosition cellPosition = inputCellPositionFromUser();
                UserAction userAction = inputActionFromUser();

                actionOnCell(cellPosition, userAction);
            } catch (AppException e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                outputHandler.showMessage("시스템 오류 발생.");
            }

        }

        outputHandler.showGameBoard(board);

        if (board.isWinStatus()) {
            outputHandler.showGameWinComment();
        }
        if (board.isLoseStatus()) {
            outputHandler.showGameLoseComment();
        }
    }

    private void actionOnCell(CellPosition cellPosition, UserAction userAction) {


        if (choosePlantFlagAction(userAction)) {
            board.flagAt(cellPosition);
            return;
        }

        if (chooseCellOpenAction(userAction)) {
            board.openAt(cellPosition);

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
