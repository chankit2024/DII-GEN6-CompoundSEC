package role;

import java.util.HashSet;
import java.util.Set;

/**
 * HighFloorDecorator => เพิ่ม "High Floor"
 */
public class HighFloorDecorator extends RoleDecorator {
    private Set<String> accessible;
    private String roleName;

    public HighFloorDecorator(Role wrappee) {
        super(wrappee);
        accessible = new HashSet<>();
        accessible.add("High Floor");
        roleName = wrappee.getRoleName() + " + HighFloor";
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
