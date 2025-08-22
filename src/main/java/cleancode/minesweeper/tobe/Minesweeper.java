package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;

/**
 * 지뢰 찾기 게임 역할
 */
public class Minesweeper implements GameRunnable, GameInitializable {

    public static final int GAME_LOSE_STATUS = -1;
    public static final int GAME_WIN_STATUS = 1;
    private final Board board;
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    public Minesweeper(GameLevel gameLevel) {
        board = new Board(gameLevel);
    }

    @Override
    public void init() {
        board.initGameBoard();
    }

    @Override
    public void run() {

        consoleOutputHandler.showStartComment();

        while (true) {
            try {

                consoleOutputHandler.showGameBoard(board);

                if (isUserWinTheGame()) {
                    consoleOutputHandler.printGameWinComment();
                    break;
                }

                if (isUserLoseTheGame()) {
                    consoleOutputHandler.printGameLoseComment();
                    break;
                }

                String userInputPosition = inputPositionFromUser();
                String userInputAction = inputActionFromUser();
                actOnUserInput(userInputPosition, userInputAction);
            } catch (AppException e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                consoleOutputHandler.printMessage("시스템 오류 발생.");
            }

        }
    }

    private void actOnUserInput(String userInputPosition, String userInputAction) {

        int selectedRowIndex = this.boardIndexConverter.getSelectedRowIndex(userInputPosition,
            board.getRowSize());
        int selectedColIndex = this.boardIndexConverter.getSelectedColIndex(userInputPosition,
            board.getColSize());

        if (choosePlantFlagAction(userInputAction)) {
            board.flag(selectedRowIndex, selectedColIndex);
            checkIfGameOver();
            return;
        }

        if (chooseCellOpenAction(userInputAction)) {
            if (board.isLandMineCell(selectedRowIndex, selectedColIndex)) {
                board.open(selectedRowIndex, selectedColIndex);

                setGameStatusToLose();
                return;
            }

            board.openSurroundCell(selectedRowIndex, selectedColIndex);

            checkIfGameOver();
            return;
        }

        consoleOutputHandler.printMessage("잘못된 번호를 선택하셨습니다.");
    }

    private void setGameStatusToLose() {
        gameStatus = GAME_LOSE_STATUS;
    }


    private boolean chooseCellOpenAction(String userInputAction) {
        return userInputAction.equals("1");
    }

    private boolean choosePlantFlagAction(String userInputAction) {
        return userInputAction.equals("2");
    }

    private String inputActionFromUser() {
        consoleOutputHandler.printCellActionComment();
        return this.consoleInputHandler.getUserInput();
    }

    private String inputPositionFromUser() {
        consoleOutputHandler.printCellInputComment();
        return this.consoleInputHandler.getUserInput();
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
