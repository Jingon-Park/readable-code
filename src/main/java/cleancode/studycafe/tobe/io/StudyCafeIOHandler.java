package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.order.StudyCafePassOrder;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import java.util.List;

public class StudyCafeIOHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();


    public void showWelcomeMessage() {
        outputHandler.showWelcomeMessage();
    }

    public void showAnnouncement() {
        outputHandler.showAnnouncement();
    }

    public void showPassOrderSummary(StudyCafePassOrder passOrder) {
        outputHandler.showPassOrderSummary(passOrder);
    }

    public void showSimpleMessage(String message) {
        outputHandler.showSimpleMessage(message);
    }

    public StudyCafePassType getPassTypeSelectingUserAction() {
        return inputHandler.getPassTypeSelectingUserAction();
    }

    public void showPassListForSelection(List<StudyCafeSeatPass> cafePassCandidates) {
        outputHandler.showPassListForSelection(cafePassCandidates);
    }

    public StudyCafeSeatPass getSelectedStudyCafePass(List<StudyCafeSeatPass> cafePassCandidates) {
        return this.inputHandler.getSelectedStudyCafePass(cafePassCandidates);
    }

    public void askLockerPass(StudyCafeLockerPass lockerPassCandidate) {
        outputHandler.askLockerPass(lockerPassCandidate);
    }

    public boolean getLockerSelection() {
        return inputHandler.getLockerSelection();
    }

    public void askPassTypeSelection() {
        outputHandler.askPassTypeSelection();

    }
}
