package User;

import Enums.StaffLevel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Staff extends User{

    private final LocalDate joinDate;
    private StaffLevel level;

    public Staff(String name, Long id, LocalDate joinDate) {
        super(name, id);
        this.joinDate = joinDate;
    }

    @Override
    public String getLevel() {
        return level.name();
    }

    @Override
    public void updateLevel() {
        long monthsWorked = ChronoUnit.MONTHS.between(joinDate, LocalDate.now());
        if (monthsWorked >= 36) {
            level = StaffLevel.SENIOR;
        } else if (monthsWorked >= 12) {
            level = StaffLevel.MID_LEVEL;
        } else {
            level = StaffLevel.JUNIOR;
        }
    }
}
