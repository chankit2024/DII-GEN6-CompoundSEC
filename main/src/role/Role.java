package role;

/**
 * Interface แทนบทบาท (Role) ที่สามารถตรวจสอบการเข้าถึงพื้นที่ หรือห้องต่าง ๆ
 * และมีเมธอด getRoleName() เพื่อบอกรายละเอียด Role
 */
public interface Role {
    boolean canAccess(String area);

    /**
     * บอกชื่อ Role เช่น "Standard Card (Base) +LowFloor"
     */
    String getRoleName();
}
