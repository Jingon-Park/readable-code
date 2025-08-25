package cleancode.minesweeper.tobe.cell;

public interface Cell {


    String FLAG_SIGH = "⚑";
    String UNCHECKED = "□";

    boolean hasLandMineCount();
    boolean isLandMine();
    String getSign();
    void flag();
    void open();
    boolean isChecked();
    boolean isOpened();
}
