package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.RelativePosition;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 게임 보드 역할 수행
 */
public class Board {

    private final int landMineCount;

    private final Cell[][] board;

    private final static List<RelativePosition> BOARD_RELATIVE_POSITIONS = List.of(
        RelativePosition.of(1, 1),
        RelativePosition.of(1, -1),
        RelativePosition.of(-1, 1),
        RelativePosition.of(-1, -1),
        RelativePosition.of(0, 1),
        RelativePosition.of(0, -1),
        RelativePosition.of(1, 0),
        RelativePosition.of(-1, 0)
    );

    public Board(GameLevel gameLevel) {
        board = new Cell[gameLevel.getRowSize()][gameLevel.getColSize()];
        landMineCount = gameLevel.getLandMineCount();
    }

    private Cell findCell(CellPosition cellPosition) {
        return this.board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public void flag(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();
    }

    public int getRowSize() {
        return board.length;

    }

    public int getColSize() {
        return board[0].length;
    }

    public String getSign(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSign();
    }

    public boolean isLandMineCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();

    }

    public void open(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board).flatMap(Arrays::stream)
            .allMatch(Cell::isChecked);
    }

    public void initGameBoard() {
        for (int row = 0; row < getRowSize(); row++) {
            for (int col = 0; col < getColSize(); col++) {
                board[row][col] = new EmptyCell();
            }
        }
        for (int i = 0; i < landMineCount; i++) {
            int col = new Random().nextInt(getColSize());
            int row = new Random().nextInt(getRowSize());
            LandMineCell landMineCell = new LandMineCell();

            board[row][col] = landMineCell;
        }
        for (int row = 0; row < getRowSize(); row++) {
            for (int col = 0; col < getColSize(); col++) {

                CellPosition cellPosition = CellPosition.of(row, col);

                if (isLandMineCell(cellPosition)) {
                    continue;
                }
                int count = countNearByLandMines(cellPosition);

                if (count == 0) {
                    continue;
                }

                NumberCell numberCell = new NumberCell(count);
                board[row][col] = numberCell;
            }
        }
    }

    private int countNearByLandMines(CellPosition cellPosition) {

        int count = (int) getCalculatablePositionList(cellPosition).stream()
            .filter(this::isLandMineCell)
            .count();

        return count;
    }

    private List<CellPosition> getCalculatablePositionList(CellPosition cellPosition) {
        return BOARD_RELATIVE_POSITIONS.stream().filter(cellPosition::isCalculatablePosition)
            .map(cellPosition::calculatePosition)
            .filter(position -> position.isRowIndexLessThan(getRowSize()))
            .filter(position -> position.isColIndexLessThan(getColSize()))
            .collect(Collectors.toList());
    }


    public boolean isOpenedCell(CellPosition cellPosition) {
        return findCell(cellPosition).isOpened();
    }

    public boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }

    public void openSurroundCell(CellPosition cellPosition) {

        int rowIndex = cellPosition.getRowIndex();
        int colIndex = cellPosition.getColIndex();


        if (rowIndex < 0 || rowIndex >= getRowSize() || colIndex < 0 || colIndex >= getColSize()) {
            return;
        }

        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCell(cellPosition)) {
            return;
        }

        open(cellPosition);
        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
        }

        getCalculatablePositionList(cellPosition).forEach(this::openSurroundCell);
    }
}
