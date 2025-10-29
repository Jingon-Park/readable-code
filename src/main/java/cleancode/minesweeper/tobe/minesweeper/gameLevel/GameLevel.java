package cleancode.minesweeper.tobe.minesweeper.gameLevel;

/**
 * 게임 난이도 인터페이스
 */
public interface GameLevel {

    int getColSize();
    int getRowSize();
    int getLandMineCount();
}
