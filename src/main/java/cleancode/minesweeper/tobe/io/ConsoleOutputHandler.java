package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.Board;
import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;
import cleancode.minesweeper.tobe.position.CellPosition;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;


/**
 * 콘솔 출력 담당
 */
public class ConsoleOutputHandler implements OutputHandler {


    public static final String EMPTY_SIGN = "■";
    public static final String LAND_MINE_SIGN = "☼";
    public static final String FLAG_SIGH = "⚑";
    public static final String UNCHECKED = "□";
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
                CellSnapshot snapshot = board.getSnapshot(CellPosition.of(row, col));

                String sign = decideCellSignFrom(snapshot);

                System.out.print(sign + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private String decideCellSignFrom(CellSnapshot snapshot) {
        CellSnapshotStatus status = snapshot.getStatus();

        if (status == CellSnapshotStatus.EMPTY) {
            return EMPTY_SIGN;
        }

        if (status == CellSnapshotStatus.FLAG) {
            return FLAG_SIGH;
        }

        if (status == CellSnapshotStatus.LAND_MAINE) {
            return LAND_MINE_SIGN;
        }

        if (status == CellSnapshotStatus.NUMBER) {
            return String.valueOf(snapshot.getNearbyLandMineCount());
        }

        if (status == CellSnapshotStatus.UNCHECKED) {
            return UNCHECKED;
        }

        throw new IllegalArgumentException("확인할 수 없는 셀입니다.");
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
