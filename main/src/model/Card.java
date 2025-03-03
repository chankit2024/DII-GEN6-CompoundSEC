package model;

import role.Role;

/**
 * Card => เก็บ UID, name, secretKey, role (Decorator), และ privateRoomName
 */
public class Card {
    private String uid;
    private String name;
    private String secretKey;
    private Role role;
    private String privateRoomName;

    public Card(String uid, String name, String secretKey, Role role, String privateRoomName) {
        this.uid = uid;
        this.name = name;
        this.secretKey = secretKey;
        this.role = role;
        this.privateRoomName = privateRoomName;
    }

    // Getter
    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Role getRole() {
        return role;
    }

    public String getPrivateRoomName() {
        return privateRoomName;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPrivateRoomName(String privateRoomName) {
        this.privateRoomName = privateRoomName;
    }

    // ช่วยบอกชื่อ Role
    public String getRoleName() {
        return role.getRoleName();
    }

    /**
     * canAccess:
     *  ถ้า area เท่ากับ privateRoomName (ignoreCase)
     *    => เช็ค role.canAccess("PRIVATE_ROOM_BASE")
     *  ถ้าไม่ใช่ => ถือเป็น Floor => role.canAccess(area)
     */
    public boolean canAccess(String area) {
        if (area.equalsIgnoreCase(privateRoomName)) {
            // ห้องตรงกัน => ต้องมีสิทธิ์ PRIVATE_ROOM_BASE
            return role.canAccess("PRIVATE_ROOM_BASE");
        }
        // ไม่ใช่ชื่อห้อง => ถือว่าเป็น Floor
        return role.canAccess(area);
    }
}
