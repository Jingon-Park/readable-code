package cleancode.minesweeper.tobe.io.sign;

import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.Arrays;

public enum CellSignProvider implements CellSignProvidable{

    EMPTY(CellSnapshotStatus.EMPTY) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return EMPTY_SIGN;
        }
    },
    NUMBER(CellSnapshotStatus.NUMBER){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return String.valueOf(cellSnapshot.getNearbyLandMineCount());
        }
    },
    LAND_MAIN(CellSnapshotStatus.LAND_MAINE){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return LAND_MINE_SIGN;
        }
    },
    FLAG(CellSnapshotStatus.FLAG){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return FLAG_SIGH;
        }
    },
    UNCHECKED(CellSnapshotStatus.UNCHECKED) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return UNCHECKED_SIGN;
        }
    },
    ;

    public static final String EMPTY_SIGN = "■";

    public static final String FLAG_SIGH = "⚑";
    public static final String LAND_MINE_SIGN = "☼";
    public static final String UNCHECKED_SIGN = "□";
    CellSignProvider(CellSnapshotStatus status) {
        this.status = status;
    }

    private final CellSnapshotStatus status;
    @Override
    public boolean support(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(status);
    }

    public static String findCellSignFrom(CellSnapshot snapshot) {
        return Arrays.stream(CellSignProvider.values()).filter(provider -> provider.support(snapshot))
            .findFirst().map(provider -> provider.provide(snapshot))
            .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다."));
    }
}
