package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.Cells;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

//    public String getSign(CellPosition cellPosition) {
//        Cell cell = findCell(cellPosition);
//        return cell.getSign();
//    }

    public CellSnapshot getSnapshot(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSnapshot();
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
        return Cells.from(this.board).isAllChecked();
    }

    public void initGameBoard() {
        CellPositions cellPositions = CellPositions.from(board);

        initEmptyCells(cellPositions);

        List<CellPosition> landMineCells = cellPositions.extractRandomCellPositions(
            landMineCount);
        initLandMineCells(landMineCells);

        List<CellPosition> numberCellCandidate = cellPositions.getPositions().stream()
            .filter(cellPosition -> !isLandMineCell(cellPosition))
            .toList();

        initNumberCells(numberCellCandidate);
    }

    private void initEmptyCells(CellPositions cellPositions) {
        for (CellPosition position : cellPositions.getPositions()) {
            updateCellAt(position, new EmptyCell());
        }
    }

    private void initLandMineCells(List<CellPosition> landMineCells) {
        for (CellPosition landMineCell : landMineCells) {
            updateCellAt(landMineCell, new LandMineCell());
        }
    }

    private void initNumberCells(List<CellPosition> numberCellCandidate) {
        for (CellPosition cellPosition : numberCellCandidate) {
            int count = countNearByLandMines(cellPosition);
            if (count != 0) {
                updateCellAt(cellPosition, new NumberCell(count));
            }
        }
    }

    private void updateCellAt(CellPosition position, Cell cell) {
        board[position.getRowIndex()][position.getColIndex()] = cell;
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
