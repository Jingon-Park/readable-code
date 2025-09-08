package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.cell.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {

    private final List<CellPosition> cellPositions;

    private CellPositions(List<CellPosition> cellPositions) {
        this.cellPositions = cellPositions;
    }

    public static CellPositions of(List<CellPosition> cellPositions) {
        return new CellPositions(cellPositions);
    }

    public static CellPositions from(Cell[][] board) {
        List<CellPosition> cellPositionList = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                cellPositionList.add(CellPosition.of(row, col));
            }
        }
        return of(cellPositionList);
    }

    public List<CellPosition> getPositions() {
        return new ArrayList<>(cellPositions);
    }

    public List<CellPosition> extractRandomCellPositions(int count) {
        List<CellPosition> positions = getPositions();
        Collections.shuffle(positions);

        return positions.subList(0, count);
    }
}
