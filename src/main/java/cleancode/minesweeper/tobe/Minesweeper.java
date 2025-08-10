package cleancode.minesweeper.tobe;

public class Minesweeper {


    public static final int GAME_LOSE_STATUS = -1;
    public static final int GAME_WIN_STATUS = 1;
    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COLUM_SIZE = 10;
    private final Board board = new Board(BOARD_ROW_SIZE, BOARD_COLUM_SIZE);
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();

    public void run() {

        consoleOutputHandler.showStartComment();
        board.initGameBoard();

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
        int selectedColIndex = getSelectedColIndex(userInputPosition);
        int selectedRowIndex = getSelectedRowIndex(userInputPosition);

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

    private int getSelectedRowIndex(String userInputPosition) {
        char userInputRow = getUserInputRowFrom(userInputPosition);
        return convertRowFrom(userInputRow);
    }

    private int getSelectedColIndex(String userInputPosition) {
        char userInputColum = getUserInputColumFrom(userInputPosition);
        return convertColumFrom(userInputColum);
    }

    private boolean chooseCellOpenAction(String userInputAction) {
        return userInputAction.equals("1");
    }

    private boolean choosePlantFlagAction(String userInputAction) {
        return userInputAction.equals("2");
    }


    private char getUserInputRowFrom(String userInputPosition) {
        return userInputPosition.charAt(1);
    }

    private char getUserInputColumFrom(String userInputPosition) {
        return userInputPosition.charAt(0);
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

    private int convertRowFrom(char userInputRow) {
        int rowIndex = Character.getNumericValue(userInputRow) - 1;
        if (rowIndex < 0 || rowIndex >= BOARD_ROW_SIZE) {
            throw new AppException("Invalid row input.");
        }
        return rowIndex;
    }

    private int convertColumFrom(char userInputColum) {
        switch (userInputColum) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new AppException("Invalid input colum.");
        }
    }

}
