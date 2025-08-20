package cleancode.minesweeper.tobe;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;


/**
 * 콘솔 출력 담당
 */
public class ConsoleOutputHandler {


    public void printGameWinComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }


    public void printGameLoseComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    public void showStartComment() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    public void showGameBoard(Board board) {
        String alphabets = generateColAlpahbets(board);
        System.out.println("    " + alphabets);
        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%2d  ", row + 1);
            for (int colum = 0; colum < board.getColSize(); colum++) {
                System.out.print(board.getSign(row, colum) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private String generateColAlpahbets(Board board) {
        List<String> alphabets = IntStream.range(0, board.getColSize())
            .mapToObj(index -> (char) ('a' + index))
            .map(Objects::toString)
            .toList();
        return String.join(" ", alphabets);
    }

    public void printMessage(String message) {
        System.out.println(message);

    }

    public void printCellActionComment() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    public void printCellInputComment() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }
}
