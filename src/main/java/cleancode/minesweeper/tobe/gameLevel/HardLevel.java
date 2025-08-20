package cleancode.minesweeper.tobe.gameLevel;

public class HardLevel implements GameLevel{

    @Override
    public int getColSize() {
        return 20;
    }

    @Override
    public int getRowSize() {
        return 24;
    }

    @Override
    public int getLandMineCount() {
        return 50;

    }
}
