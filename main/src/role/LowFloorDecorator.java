package role;

import java.util.HashSet;
import java.util.Set;

/**
 * LowFloorDecorator => เพิ่มการเข้าถึง "Low Floor"
 */
public class LowFloorDecorator extends RoleDecorator {
    private Set<String> accessible;
    private String roleName;

    public LowFloorDecorator(Role wrappee) {
        super(wrappee);
        accessible = new HashSet<>();
        accessible.add("Low Floor");
        // ต่อชื่อ role
        roleName = wrappee.getRoleName() + " + LowFloor";
    }

    @Override
    public boolean canAccess(String area) {
        if (accessible.contains(area)) {
            return true;
        }
        return super.canAccess(area);
    }

    @Override
    public String getRoleName() {
        return roleName;
    }
}
