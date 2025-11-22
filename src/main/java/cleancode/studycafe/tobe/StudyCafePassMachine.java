package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.model.order.StudyCafePassOrder;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;

import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPasses;
import cleancode.studycafe.tobe.provider.LockerPassProvider;
import cleancode.studycafe.tobe.provider.SeatPassProvider;
import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final SeatPassProvider seatPassProvider;
    private final LockerPassProvider lockerPassProvider;

    public StudyCafePassMachine(SeatPassProvider seatPassProvider,
        LockerPassProvider lockerPassProvider) {
        this.seatPassProvider = seatPassProvider;
        this.lockerPassProvider = lockerPassProvider;
    }

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();


            StudyCafeSeatPass selectedPass = getStudyCafePass();

            Optional<StudyCafeLockerPass> optionalLockerPass = getLockerPass(selectedPass);

            StudyCafePassOrder passOrder = StudyCafePassOrder.of(selectedPass,
                optionalLockerPass.orElse(null));

            ioHandler.showPassOrderSummary(passOrder);

        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeSeatPass getStudyCafePass() {

        ioHandler.askPassTypeSelection();

        StudyCafePassType selectCafePassType = ioHandler.getPassTypeSelectingUserAction();

        List<StudyCafeSeatPass> cafePassCandidates = getStudyCafePassCandidatesBy(
            selectCafePassType);

        ioHandler.showPassListForSelection(cafePassCandidates);

        return ioHandler.getSelectedStudyCafePass(cafePassCandidates);
    }

    private List<StudyCafeSeatPass> getStudyCafePassCandidatesBy(StudyCafePassType selectCafePassType) {

        StudyCafeSeatPasses allCafePass = seatPassProvider.getSeatPasses();
        return allCafePass.findPassBy(selectCafePassType);
    }

    private Optional<StudyCafeLockerPass> getLockerPass(StudyCafeSeatPass selectedPass) {

        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> lockerPassCandidate = getLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate.isPresent()) {
            StudyCafeLockerPass lockerPass = lockerPassCandidate.get();
            ioHandler.askLockerPass(lockerPass);

            if (ioHandler.getLockerSelection()) {
                return Optional.of(lockerPass);
            }
        }

        return Optional.empty();
    }

    private Optional<StudyCafeLockerPass> getLockerPassCandidateBy(StudyCafeSeatPass selectedPass) {
        StudyCafeLockerPasses allLockerPasses = lockerPassProvider.getLockerPasses();

        return allLockerPasses.findLockerPassBy(selectedPass);
    }

}
