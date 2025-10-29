package cleancode.minesweeper.tobe.minesweeper.gameLevel;

public class EasyLevel implements GameLevel {

    @Override
    public int getColSize() {
        return 5;
    }

    @Override
    public int getRowSize() {
        return 7;
    }

    @Override
    public int getLandMineCount() {
        return 5;
    }
}
