package cleancode.minesweeper.tobe;

/**
 * 유저가 입력한 위치 정보를 row, col 인덱스로 변환
 */
public class BoardIndexConverter {

    public int getSelectedRowIndex(String userInputPosition) {
        String userInputRow = getUserInputRowFrom(userInputPosition);
        return convertRowFrom(userInputRow);
    }

    public int getSelectedColIndex(String userInputPosition) {
        char userInputColum = getUserInputColumFrom(userInputPosition);
        return convertColumFrom(userInputColum);
    }

    private int convertRowFrom(String userInputRow) {
        int rowIndex = Integer.parseInt(userInputRow) - 1;
        if (rowIndex < 0) {
            throw new AppException("Invalid row input.");
        }
        return rowIndex;
    }

    private int convertColumFrom(char userInputColum) {
        int colIndex = userInputColum - 'a';
        if (colIndex < 0) {
            throw new AppException("Invalid col input");
        }
        return colIndex;
    }

    private String getUserInputRowFrom(String userInputPosition) {
        return userInputPosition.substring(1);
    }

    private char getUserInputColumFrom(String userInputPosition) {
        return userInputPosition.charAt(0);
    }
}
