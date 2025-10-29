package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class NumberCell implements Cell {

    private final int nearbyLandMineCount;

    public NumberCell(int nearbyLandMineCount) {
        this.nearbyLandMineCount = nearbyLandMineCount;
    }

    private final CellStatus cellStatus = CellStatus.initialize();

    @Override
    public void flag() {
        cellStatus.flag();
    }

    @Override
    public void open() {
        cellStatus.open();
    }

    @Override
    public boolean hasLandMineCount() {
        return true;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean isChecked() {
        return cellStatus.isOpened();
    }

    @Override
    public boolean isOpened() {
        return cellStatus.isOpened();
    }

    @Override
    public CellSnapshot getSnapshot() {
        if (cellStatus.isOpened()) {
            return CellSnapshot.ofNumber(nearbyLandMineCount);
        }

        if (cellStatus.isFlagged()) {
            return CellSnapshot.ofFlag();
        }
        return CellSnapshot.ofUnChecked();
    }
}
