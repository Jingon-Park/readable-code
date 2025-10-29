package cleancode.minesweeper.tobe.minesweeper.gameLevel;

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
        return 8;
    }
}
