package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.config.GameConfig;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.gameLevel.HardLevel;
import cleancode.minesweeper.tobe.gameLevel.NormalLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

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
