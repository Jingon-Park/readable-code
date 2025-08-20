package cleancode.minesweeper.tobe;

/**
 * 유저가 입력한 위치 정보를 row, col 인덱스로 변환
 */
public class BoardIndexConverter {

    public int getSelectedRowIndex(String userInputPosition, int rowSize) {
        String userInputRow = getUserInputRowFrom(userInputPosition);
        return convertRowFrom(userInputRow, rowSize);
    }

    public int getSelectedColIndex(String userInputPosition, int colSize) {
        char userInputColum = getUserInputColumFrom(userInputPosition);
        return convertColumFrom(userInputColum, colSize);
    }

    private int convertRowFrom(String userInputRow, int rowSize) {
        int rowIndex = Integer.parseInt(userInputRow) - 1;
        if (rowIndex < 0 || rowIndex >= rowSize) {
            throw new AppException("Invalid row input.");
        }
        return rowIndex;
    }

    private int convertColumFrom(char userInputColum, int colSize) {
        int colIndex = userInputColum - 'a';
        if (colIndex < 0 || colIndex > colSize) {
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
