package role;

import java.util.HashSet;
import java.util.Set;

/**
 * MediumFloorDecorator => เพิ่ม "Medium Floor"
 */
public class MediumFloorDecorator extends RoleDecorator {
    private Set<String> accessible;
    private String roleName;

    public MediumFloorDecorator(Role wrappee) {
        super(wrappee);
        accessible = new HashSet<>();
        accessible.add("Medium Floor");
        roleName = wrappee.getRoleName() + " + MediumFloor";
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
