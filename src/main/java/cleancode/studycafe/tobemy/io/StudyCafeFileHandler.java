package cleancode.studycafe.tobemy.io;

import cleancode.studycafe.tobemy.model.StudyCafeLockerPass;
import cleancode.studycafe.tobemy.model.StudyCafePass;
import cleancode.studycafe.tobemy.model.StudyCafePassType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeFileHandler implements FileHandler{

    private List<String> readFrom(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    @Override
    public List<StudyCafePass> readStudyCafePasses() {
        try {
            final String PASS_LIST_PATH = "src/main/resources/cleancode/studycafe/pass-list.csv";

            List<String> lines = readFrom(Paths.get(PASS_LIST_PATH));
            List<StudyCafePass> studyCafePasses = new ArrayList<>();
            for (String line : lines) {
                String[] values = line.split(",");
                StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);
                double discountRate = Double.parseDouble(values[3]);

                StudyCafePass studyCafePass = StudyCafePass.of(studyCafePassType, duration, price, discountRate);
                studyCafePasses.add(studyCafePass);
            }

            return studyCafePasses;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    @Override
    public List<StudyCafeLockerPass> readLockerPasses() {
        try {
            final String LOCKER_FILE_PATH = "src/main/resources/cleancode/studycafe/locker.csv";

            List<String> lines = Files.readAllLines(Paths.get(LOCKER_FILE_PATH));
            List<StudyCafeLockerPass> lockerPasses = new ArrayList<>();
            for (String line : lines) {
                String[] values = line.split(",");
                StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);

                StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(studyCafePassType, duration, price);
                lockerPasses.add(lockerPass);
            }

            return lockerPasses;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

}
