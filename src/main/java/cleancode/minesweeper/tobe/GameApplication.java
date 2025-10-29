package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import cleancode.minesweeper.tobe.minesweeper.Minesweeper;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.HardLevel;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.NormalLevel;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.InputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.OutputHandler;

public class GameApplication {


    /**
     * 게임 애플리케이션 실행 역할
     * @param args
     */
    public static void main(String[] args) {
        GameLevel normal = new NormalLevel();
        GameLevel hard = new HardLevel();

        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        GameConfig gameConfig = new GameConfig(normal, inputHandler, outputHandler);

        Minesweeper minesweeper = new Minesweeper(gameConfig);
        minesweeper.init();
        minesweeper.run();
    }


}
