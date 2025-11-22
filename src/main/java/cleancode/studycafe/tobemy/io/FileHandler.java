package cleancode.studycafe.tobemy.io;

import cleancode.studycafe.tobemy.model.StudyCafeLockerPass;
import cleancode.studycafe.tobemy.model.StudyCafePass;
import java.util.List;

public interface FileHandler {

    public List<StudyCafePass> readStudyCafePasses();

    public List<StudyCafeLockerPass> readLockerPasses();
}
