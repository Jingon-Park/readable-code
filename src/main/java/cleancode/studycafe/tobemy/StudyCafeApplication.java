package cleancode.studycafe.tobemy;

import cleancode.studycafe.tobemy.io.FileHandler;
import cleancode.studycafe.tobemy.io.UserInputHandler;
import cleancode.studycafe.tobemy.io.ConsoleOutputHandler;
import cleancode.studycafe.tobemy.io.StudyCafeFileHandler;
import cleancode.studycafe.tobemy.machine.KioskMachineRunner;
import cleancode.studycafe.tobemy.machine.MachineConfig;

public class StudyCafeApplication {

    public static void main(String[] args) {

        ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
        UserInputHandler userInputHandler = new UserInputHandler();
        FileHandler fileHandler = new StudyCafeFileHandler();

        MachineConfig config = new MachineConfig(consoleOutputHandler, userInputHandler, fileHandler);
        KioskMachineRunner studyCafePassMachine = new StudyCafePassMachine(config);
        studyCafePassMachine.run();
    }

}
