package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.BoardIndexConverter;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.user.UserAction;
import java.util.Scanner;

/**
 * 유저 인풋 담당
 */
public class ConsoleInputHandler implements InputHandler {


    private final Scanner SCANNER = new Scanner(System.in);
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    @Override
    public String getUserInput() {
        return SCANNER.nextLine();
    }

    @Override
    public UserAction getUserActionFromUser() {
        String userInput = getUserInput();

        if ("1".equals(userInput)) {
            return UserAction.OPEN;
        }

        if ("2".equals(userInput)) {
            return UserAction.FLAG;
        }

        return UserAction.UNKNOWN;
    }

    @Override
    public CellPosition getCellPositionFromUserInput() {

        String userInput = getUserInput();

        int rowIndex = boardIndexConverter.getSelectedRowIndex(userInput);
        int colIndex = boardIndexConverter.getSelectedColIndex(userInput);

        return CellPosition.of(rowIndex, colIndex);
    }
}
