package cleancode.minesweeper.tobe.cell;

public class NumberCell extends Cell {


    private final int nearbyLandMineCount;

    public NumberCell(int nearbyLandMineCount) {
        this.nearbyLandMineCount = nearbyLandMineCount;
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
    public String getSign() {

        if (isOpened) {
            return Integer.toString(this.nearbyLandMineCount);
        }

        if (isFlagged) {
            return FLAG_SIGH;
        }

        return UNCHECKED;
    }
}
