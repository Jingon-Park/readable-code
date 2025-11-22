package cleancode.studycafe.tobemy.machine;


import cleancode.studycafe.tobemy.io.FileHandler;
import cleancode.studycafe.tobemy.io.UserInputHandler;
import cleancode.studycafe.tobemy.io.ConsoleOutputHandler;

public class MachineConfig {

    private ConsoleOutputHandler consoleOutputHandler;
    private UserInputHandler userInputHandler;
    private FileHandler fileHandler;

    public MachineConfig(ConsoleOutputHandler consoleOutputHandler, UserInputHandler userInputHandler, FileHandler fileHandler) {
        this.consoleOutputHandler = consoleOutputHandler;
        this.userInputHandler = userInputHandler;
        this.fileHandler = fileHandler;
    }

    public ConsoleOutputHandler getOutputHandler() {
        return consoleOutputHandler;
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public UserInputHandler getInputHandler() {
        return userInputHandler;
    }
}
