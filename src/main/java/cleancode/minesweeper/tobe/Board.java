package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import java.util.Arrays;
import java.util.Random;

/**
 * 게임 보드 역할 수행
 */
public class Board {

    private final int landMineCount;

    private final Cell[][] board;

    public Board(GameLevel gameLevel) {
        board = new Cell[gameLevel.getRowSize()][gameLevel.getColSize()];
        landMineCount = gameLevel.getLandMineCount();
    }

    public void flag(int selectedRowIndex, int selectedColIndex) {
        Cell cell = this.board[selectedRowIndex][selectedColIndex];
        cell.flag();

    }

    public int getRowSize() {
        return board.length;

    }

    public int getColSize() {
        return board[0].length;
    }

    public String getSign(int row, int col) {
        Cell cell = findCell(row, col);
        return cell.getSign();
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColIndex);
        return cell.isLandMine();

    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public void open(int selectedRowIndex, int selectedColIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColIndex);
        cell.open();
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board).flatMap(Arrays::stream)
            .allMatch(Cell::isChecked);
    }

    public void initGameBoard() {
        for (int row = 0; row < getRowSize(); row++) {
            for (int col = 0; col < getColSize(); col++) {
                board[row][col] = Cell.create();
            }
        }
        for (int i = 0; i < landMineCount; i++) {
            int col = new Random().nextInt(getColSize());
            int row = new Random().nextInt(getRowSize());
            findCell(row, col).turnOnLandMine();
        }
        for (int row = 0; row < getRowSize(); row++) {
            for (int col = 0; col < getColSize(); col++) {
                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearByLandMines(row, col);
                findCell(row, col).updateNearbyLandMineCount(count);
            }
        }
    }

    private int countNearByLandMines(int row, int colum) {
        int count = 0;
        if (row - 1 >= 0 && colum - 1 >= 0 && isLandMineCell(row - 1, colum - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, colum)) {
            count++;
        }
        if (row - 1 >= 0 && colum + 1 < getColSize() && isLandMineCell(row - 1, colum + 1)) {
            count++;
        }
        if (colum - 1 >= 0 && isLandMineCell(row, colum - 1)) {
            count++;
        }
        if (colum + 1 < getColSize() && isLandMineCell(row, colum + 1)) {
            count++;
        }
        if (row + 1 < getRowSize() && colum - 1 >= 0 && isLandMineCell(row + 1, colum - 1)) {
            count++;
        }
        if (row + 1 < getRowSize() && isLandMineCell(row + 1, colum)) {
            count++;
        }
        if (row + 1 < getRowSize() && colum + 1 < getColSize() && isLandMineCell(row + 1,
            colum + 1)) {
            count++;
        }
        return count;
    }

    public boolean isOpenedCell(int rowIndex, int colIndex) {
        return findCell(rowIndex, colIndex).isOpened();
    }

    public boolean doesCellHaveLandMineCount(int rowIndex, int colIndex) {
        return findCell(rowIndex, colIndex).hasLandMineCount();
    }

    public void openSurroundCell(int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= getRowSize() || colIndex < 0 || colIndex >= getColSize()) {
            return;
        }
        if (isOpenedCell(rowIndex, colIndex)) {
            return;
        }
        if (isLandMineCell(rowIndex, colIndex)) {
            return;
        }

        open(rowIndex, colIndex);
        if (doesCellHaveLandMineCount(rowIndex, colIndex)) {
            return;
        }

        openSurroundCell(rowIndex - 1, colIndex - 1);
        openSurroundCell(rowIndex - 1, colIndex);
        openSurroundCell(rowIndex - 1, colIndex + 1);
        openSurroundCell(rowIndex, colIndex - 1);
        openSurroundCell(rowIndex, colIndex + 1);
        openSurroundCell(rowIndex + 1, colIndex - 1);
        openSurroundCell(rowIndex + 1, colIndex);
        openSurroundCell(rowIndex + 1, colIndex + 1);
    }
}
