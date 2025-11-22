package cleancode.studycafe.tobemy.io;

import cleancode.studycafe.tobemy.model.StudyCafePass;
import cleancode.studycafe.tobemy.model.StudyCafePassType;
import java.util.List;

public interface InputHandler {

    public StudyCafePassType getPassTypeSelectingUserAction();

    public StudyCafePass getSelectPass(List<StudyCafePass> passes);

    public boolean getLockerSelection();
}
