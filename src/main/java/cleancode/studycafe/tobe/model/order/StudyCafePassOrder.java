package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import java.util.Optional;

public class StudyCafePassOrder {

    private StudyCafeSeatPass seatPass;
    private StudyCafeLockerPass lockerPass;

    private StudyCafePassOrder(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass) {
        this.seatPass = seatPass;
        this.lockerPass = lockerPass;
    }

    public static StudyCafePassOrder of(StudyCafeSeatPass seatPass,
        StudyCafeLockerPass lockerPass) {
        return new StudyCafePassOrder(seatPass, lockerPass);
    }

    public StudyCafeSeatPass getSeatPass() {
        return seatPass;
    }

    public Optional<StudyCafeLockerPass> getLockerPass() {
        return Optional.ofNullable(lockerPass);
    }

    public int getDiscountPrice() {
        return seatPass.getDiscountPrice();
    }

    public int getTotalPrice() {
        int seatPassPrice = seatPass.getPrice();
        int lockerPassPrice = (lockerPass != null ? lockerPass.getPrice() : 0);
        int disCountPrice = seatPass.getDiscountPrice();
        return seatPassPrice + lockerPassPrice - disCountPrice;
    }
}
