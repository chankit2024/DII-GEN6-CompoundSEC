package role;

import java.util.HashSet;
import java.util.Set;

/**
 * BaseRole => มีสิทธิ์ Private Room พื้นฐาน (เชิง symbolic = "PRIVATE_ROOM_BASE")
 */
public class BaseRole implements Role {
    protected Set<String> accessibleAreas;

    public BaseRole() {
        accessibleAreas = new HashSet<>();
        accessibleAreas.add("PRIVATE_ROOM_BASE");  // แสดงว่ามีสิทธิ์ใช้ Private Room
    }

    @Override
    public boolean canAccess(String area) {
        // BaseRole จะเช็คแค่ "PRIVATE_ROOM_BASE"
        return accessibleAreas.contains(area);
    }

    @Override
    public String getRoleName() {
        return "Standard Card (Base)";
    }
}
