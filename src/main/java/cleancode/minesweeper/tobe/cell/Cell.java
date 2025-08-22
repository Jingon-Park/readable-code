package cleancode.minesweeper.tobe.cell;

/**
 * 게임 보드의 각 Cell
 */
public abstract class Cell {

    public static final String FLAG_SIGH = "⚑";
    public static final String UNCHECKED = "□";

    protected boolean isFlagged;
    protected boolean isOpened;

    public void flag() {
        isFlagged = true;
    }

    public void open() {
        isOpened = true;
    }


    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public boolean isOpened() {
        return isOpened;
    }


    public abstract boolean hasLandMineCount();

    public abstract boolean isLandMine();

    public abstract String getSign();
}
