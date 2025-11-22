package cleancode.studycafe.tobemy;

import cleancode.studycafe.tobemy.exception.AppException;
import cleancode.studycafe.tobemy.io.FileHandler;
import cleancode.studycafe.tobemy.io.UserInputHandler;
import cleancode.studycafe.tobemy.io.ConsoleOutputHandler;
import cleancode.studycafe.tobemy.machine.KioskMachineInitializer;
import cleancode.studycafe.tobemy.machine.KioskMachineRunner;
import cleancode.studycafe.tobemy.machine.MachineConfig;
import cleancode.studycafe.tobemy.model.StudyCafeLockerPass;
import cleancode.studycafe.tobemy.model.StudyCafePass;
import cleancode.studycafe.tobemy.model.StudyCafePassType;
import java.util.List;
import java.util.stream.Collectors;

public class StudyCafePassMachine implements KioskMachineRunner, KioskMachineInitializer {

    private UserInputHandler userInputHandler;
    private ConsoleOutputHandler consoleOutputHandler;

    private FileHandler fileHandler;

    public MachineConfig config;

    public StudyCafePassMachine(MachineConfig config) {
        this.config = config;
        this.init();
    }

    @Override
    public void init() {
        this.userInputHandler = config.getInputHandler();
        this.consoleOutputHandler = config.getOutputHandler();
        this.fileHandler = config.getFileHandler();
    }

    @Override
    public void run() {
        try {
            consoleOutputHandler.showWelcomeMessage();
            consoleOutputHandler.showAnnouncement();

            consoleOutputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = userInputHandler.getPassTypeSelectingUserAction();

            List<StudyCafePass> selectTypePassList = this.getStudyCafePassFor(studyCafePassType);
            consoleOutputHandler.showPassListForSelection(selectTypePassList);

            StudyCafePass selectedPass = userInputHandler.getSelectPass(selectTypePassList);

            if (studyCafePassType == StudyCafePassType.HOURLY) {
                consoleOutputHandler.showPassOrderSummary(selectedPass, null);
            } else if (studyCafePassType == StudyCafePassType.WEEKLY) {
                consoleOutputHandler.showPassOrderSummary(selectedPass, null);
            } else if (studyCafePassType == StudyCafePassType.FIXED) {

                List<StudyCafeLockerPass> lockerPasses = fileHandler.readLockerPasses();
                StudyCafeLockerPass lockerPass = lockerPasses.stream()
                    .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                            && option.getDuration() == selectedPass.getDuration()
                    )
                    .findFirst()
                    .orElse(null);

                boolean lockerSelection = false;
                if (lockerPass != null) {
                    consoleOutputHandler.askLockerPass(lockerPass);
                    lockerSelection = userInputHandler.getLockerSelection();
                }

                if (lockerSelection) {
                    consoleOutputHandler.showPassOrderSummary(selectedPass, lockerPass);
                } else {
                    consoleOutputHandler.showPassOrderSummary(selectedPass, null);
                }
            }
        } catch (AppException e) {
            consoleOutputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            consoleOutputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private List<StudyCafePass> getStudyCafePassFor(StudyCafePassType passTypeType) {

        List<StudyCafePass> studyCafePasses = fileHandler.readStudyCafePasses();

        return studyCafePasses.stream().filter(studyCafePass -> studyCafePass.getPassType().equals(passTypeType)).collect(
            Collectors.toList());


    }





}
