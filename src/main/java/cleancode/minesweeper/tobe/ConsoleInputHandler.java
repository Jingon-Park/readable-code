package cleancode.minesweeper.tobe;

import java.util.Scanner;

/**
 * 유저 인풋 담당
 */
public class ConsoleInputHandler {


    private final Scanner SCANNER = new Scanner(System.in);

    public String getUserInput() {
        return SCANNER.nextLine();
    }





}
