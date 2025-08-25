package cleancode.minesweeper.tobe.cell;

public class LandMineCell implements Cell {

    public static final String LAND_MINE_SIGN = "☼";

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
        return true;
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
    public String getSign() {
        if (cellStatus.isOpened()) {
            return LAND_MINE_SIGN;
        }

        if (cellStatus.isFlagged()) {
            return FLAG_SIGH;
        }
        return UNCHECKED;
    }
}
