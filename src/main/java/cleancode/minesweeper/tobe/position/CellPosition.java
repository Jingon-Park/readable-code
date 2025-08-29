package cleancode.minesweeper.tobe.position;

import java.util.Objects;

public class CellPosition {

    private int rowIndex;
    private int colIndex;



    private CellPosition(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public static CellPosition of(int rowIndex, int colIndex) {
        if (rowIndex < 0 || colIndex < 0) {
            throw new IllegalArgumentException("잘못된 좌표입니다.");
        }
        return new CellPosition(rowIndex, colIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, colIndex);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CellPosition that = (CellPosition) obj;
        return rowIndex == that.rowIndex && colIndex == that.colIndex;

    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public boolean isCalculatablePosition(RelativePosition relativePosition) {
        return rowIndex + relativePosition.getDeltaRow() >= 0
            && colIndex + relativePosition.getDeltaCol() >= 0;
    }

    public CellPosition calculatePosition(RelativePosition relativePosition) {
        return CellPosition.of(rowIndex + relativePosition.getDeltaRow(),
            colIndex + relativePosition.getDeltaCol());
    }

    public boolean isRowIndexLessThan(int rowIndex) {
        return this.rowIndex < rowIndex;
    }

    public boolean isColIndexLessThan(int colIndex) {
        return this.colIndex < colIndex;
    }
}
