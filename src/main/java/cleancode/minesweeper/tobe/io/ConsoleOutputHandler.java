package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.Board;
import cleancode.minesweeper.tobe.position.CellPosition;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;


/**
 * 콘솔 출력 담당
 */
public class ConsoleOutputHandler implements OutputHandler {


    @Override
    public void showGameWinComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }


    @Override
    public void showGameLoseComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    @Override
    public void showStartComment() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void showGameBoard(Board board) {
        String alphabets = generateColAlpahbets(board);
        System.out.println("    " + alphabets);
        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%2d  ", row + 1);
            for (int col = 0; col < board.getColSize(); col++) {
                System.out.print(board.getSign(CellPosition.of(row, col)) + " ");
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

    @Override
    public void showMessage(String message) {
        System.out.println(message);

    }

    @Override
    public void showCellActionComment() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void showCellInputComment() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }
}
