package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.io.LockerPassFileProvider;
import cleancode.studycafe.tobe.io.SeatPassFileProvider;
import cleancode.studycafe.tobe.provider.LockerPassProvider;
import cleancode.studycafe.tobe.provider.SeatPassProvider;

public class StudyCafeApplication {

    public static void main(String[] args) {
        SeatPassProvider seatPassProvider = new SeatPassFileProvider();
        LockerPassProvider lockerPassProvider = new LockerPassFileProvider();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(seatPassProvider,
            lockerPassProvider);
        studyCafePassMachine.run();
    }

}
