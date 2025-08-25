package cleancode.minesweeper.tobe.io;

import java.util.Scanner;

/**
 * 유저 인풋 담당
 */
public class ConsoleInputHandler implements InputHandler {


    private final Scanner SCANNER = new Scanner(System.in);

    @Override
    public String getUserInput() {
        return SCANNER.nextLine();
    }


}
