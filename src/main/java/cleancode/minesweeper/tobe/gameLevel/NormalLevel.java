package cleancode.minesweeper.tobe.gameLevel;

public class NormalLevel implements GameLevel {

    @Override
    public int getColSize() {
        return 8;
    }

    @Override
    public int getRowSize() {
        return 10;
    }

    @Override
    public int getLandMineCount() {
        return 10;
    }
}
