package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    public static final int GAME_LOSE_STATUS = -1;
    public static final int GAME_WIN_STATUS = 1;
    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COLUM_SIZE = 10;
    public static final String FLAG_SIGH = "⚑";
    public static final String LAND_MINE_SIGN = "☼";
    public static final String CLOSED_CELL_SIGN = "□";
    public static final String OPENED_CELL_SIGN = "■";
    public static final Scanner SCANNER = new Scanner(System.in);
    private static String[][] board = new String[BOARD_ROW_SIZE][BOARD_COLUM_SIZE];
    private static Integer[][] NEARBY_LAND_MINE_COUNTS = new Integer[BOARD_ROW_SIZE][BOARD_COLUM_SIZE];
    private static boolean[][] landMines = new boolean[BOARD_ROW_SIZE][BOARD_COLUM_SIZE];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {

        showStartComment();
        initGameBoard();

        while (true) {
            try {
                showGameBoard();

                if (isUserWinTheGame()) {
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    break;
                }

                if (isUserLoseTheGame()) {
                    System.out.println("지뢰를 밟았습니다. GAME OVER!");
                    break;
                }

                String userInputPosition = inputPositionFromUser();
                String userInputAction = inputActionFromUser();
                actOnUserInput(userInputPosition, userInputAction);
            } catch (AppException e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                System.out.println("시스템 오류 발생.");
            }

        }
    }

    private static void actOnUserInput(String userInputPosition, String userInputAction) {
        int selectedColumIndex = getSelectedColumIndex(userInputPosition);
        int selectedRowIndex = getSelectedRowIndex(userInputPosition);

        if (choosePlantFlagAction(userInputAction)) {
            board[selectedRowIndex][selectedColumIndex] = FLAG_SIGH;
            checkIfGameOver();
            return;
        }
        if (chooseCellOpenAction(userInputAction)) {
            if (landMines[selectedRowIndex][selectedColumIndex]) {
                board[selectedRowIndex][selectedColumIndex] = LAND_MINE_SIGN;
                gameStatus = GAME_LOSE_STATUS;
                return;
            }
            open(selectedRowIndex, selectedColumIndex);

            checkIfGameOver();
            return;
        }
        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private static int getSelectedRowIndex(String userInputPosition) {
        char userInputRow = getUserInputRowFrom(userInputPosition);
        return convertRowFrom(userInputRow);
    }

    private static int getSelectedColumIndex(String userInputPosition) {
        char userInputColum = getUserInputColumFrom(userInputPosition);
        return convertColumFrom(userInputColum);
    }

    private static boolean chooseCellOpenAction(String userInputAction) {
        return userInputAction.equals("1");
    }

    private static boolean choosePlantFlagAction(String userInputAction) {
        return userInputAction.equals("2");
    }


    private static char getUserInputRowFrom(String userInputPosition) {
        return userInputPosition.charAt(1);
    }

    private static char getUserInputColumFrom(String userInputPosition) {
        return userInputPosition.charAt(0);
    }

    private static String inputActionFromUser() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return SCANNER.nextLine();
    }
    private static String inputPositionFromUser() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return SCANNER.nextLine();
    }

    private static boolean isUserLoseTheGame() {
        return gameStatus == GAME_LOSE_STATUS;
    }

    private static boolean isUserWinTheGame() {
        return gameStatus == GAME_WIN_STATUS;
    }

    private static void checkIfGameOver() {
        boolean isAllOpened = isAllCellOpened();

        if (isAllOpened) {
            gameStatus = GAME_WIN_STATUS;
        }
    }


    private static boolean isAllCellOpened() {
        return Arrays.stream(board).flatMap(Arrays::stream)
            .noneMatch(CLOSED_CELL_SIGN::equals);
    }

    private static int convertRowFrom(char userInputRow) {
        int rowIndex = Character.getNumericValue(userInputRow) - 1;
        if (rowIndex < 0 || rowIndex >= BOARD_ROW_SIZE) {
            throw new AppException("Invalid row input.");
        }
        return rowIndex;
    }

    private static int convertColumFrom(char userInputColum) {
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

    private static void showGameBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            System.out.printf("%d  ", row + 1);
            for (int colum = 0; colum < BOARD_COLUM_SIZE; colum++) {
                System.out.print(board[row][colum] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initGameBoard() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int colum = 0; colum < BOARD_COLUM_SIZE; colum++) {
                board[row][colum] = CLOSED_CELL_SIGN;
            }
        }
        for (int i = 0; i < 10; i++) {
            int col = new Random().nextInt(BOARD_COLUM_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            landMines[row][col] = true;
        }
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int colum = 0; colum < BOARD_COLUM_SIZE; colum++) {
                if (isLandMineCell(row, colum)) {
                    NEARBY_LAND_MINE_COUNTS[row][colum] = 0;
                    continue;
                }
                int count = countNearByLandMines(row, colum);
                NEARBY_LAND_MINE_COUNTS[row][colum] = count;
            }
        }
    }

    private static int countNearByLandMines(int row, int colum) {
        int count = 0;
        if (row - 1 >= 0 && colum - 1 >= 0 && landMines[row - 1][colum - 1]) {
            count++;
        }
        if (row - 1 >= 0 && landMines[row - 1][colum]) {
            count++;
        }
        if (row - 1 >= 0 && colum + 1 < BOARD_COLUM_SIZE && landMines[row - 1][colum + 1]) {
            count++;
        }
        if (colum - 1 >= 0 && landMines[row][colum - 1]) {
            count++;
        }
        if (colum + 1 < BOARD_COLUM_SIZE && landMines[row][colum + 1]) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && colum - 1 >= 0 && landMines[row + 1][colum - 1]) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && landMines[row + 1][colum]) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && colum + 1 < BOARD_COLUM_SIZE && landMines[row + 1][colum + 1]) {
            count++;
        }
        return count;
    }

    private static boolean isLandMineCell(int row, int colum) {
        return landMines[row][colum];
    }

    private static void showStartComment() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COLUM_SIZE) {
            return;
        }
        if (!board[row][col].equals(CLOSED_CELL_SIGN)) {
            return;
        }
        if (landMines[row][col]) {
            return;
        }
        if (NEARBY_LAND_MINE_COUNTS[row][col] != 0) {
            board[row][col] = String.valueOf(NEARBY_LAND_MINE_COUNTS[row][col]);
            return;
        } else {
            board[row][col] = OPENED_CELL_SIGN;
        }
        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
