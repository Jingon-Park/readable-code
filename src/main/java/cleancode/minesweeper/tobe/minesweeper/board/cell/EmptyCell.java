package cleancode.minesweeper.tobe.minesweeper.board.cell;


public class EmptyCell implements Cell {


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
        return false;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean isChecked() {
        return cellStatus.isChecked();
    }

    @Override
    public boolean isOpened() {
        return cellStatus.isOpened();
    }

    @Override
    public CellSnapshot getSnapshot() {
        if (cellStatus.isOpened()) {
            return CellSnapshot.ofEmpty();
        }

        if (cellStatus.isFlagged()) {
            return CellSnapshot.ofFlag();
        }
        return CellSnapshot.ofUnChecked();
    }

}
