package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.gameLevel.HardLevel;
import cleancode.minesweeper.tobe.gameLevel.NormalLevel;

public class GameApplication {


    /**
     * 게임 애플리케이션 실행 역할
     * @param args
     */
    public static void main(String[] args) {
        GameLevel normal = new NormalLevel();
        GameLevel hard = new HardLevel();

        Minesweeper minesweeper = new Minesweeper(hard);
        minesweeper.run();
    }


}
