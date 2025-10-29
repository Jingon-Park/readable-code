package cleancode.minesweeper.tobe.minesweeper.board.cell;

public interface Cell {



    boolean hasLandMineCount();
    boolean isLandMine();
//    String getSign();

    CellSnapshot getSnapshot();
    void flag();
    void open();
    boolean isChecked();
    boolean isOpened();
}
