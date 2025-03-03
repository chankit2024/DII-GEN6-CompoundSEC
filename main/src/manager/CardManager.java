package manager;

import model.Card;
import role.Role;
import role.BaseRole;
import role.LowFloorDecorator;
import role.MediumFloorDecorator;
import role.HighFloorDecorator;
import util.TOTPGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardManager {
    private List<Card> cards;

    public CardManager() {
        cards = new ArrayList<>();
    }

    /**
     * สร้าง Card ใหม่
     * @param name         ชื่อบัตร (Card Name)
     * @param privateRoom  ชื่อห้องส่วนตัว (เช่น R101, M101)
     * @param roleChoice   1=Standard, 2=Vip, 3=VipPlus
     */
    public Card createCard(String name, String privateRoom, int roleChoice) {
        // สร้าง UID 7 ตัว (ป้องกันซ้ำ)
        String uid = generateUID();
        while (getCardByUID(uid) != null) {
            uid = generateUID();
        }

        // Secret Key 16 ตัว
        String secretKey = TOTPGenerator.generateSecretKey();

        // Role จาก Decorator
        Role role = buildRoleByChoice(roleChoice);

        Card newCard = new Card(uid, name, secretKey, role, privateRoom);
        cards.add(newCard);

        LogManager.getInstance().addLog(
                "Create Card => Name : " + name +
                        " | UID : " + uid +
                        " | Room : " + privateRoom +
                        " | Role : " + newCard.getRoleName() + ")"
        );

        return newCard;
    }

    /**
     * แสดงบัตรทั้งหมด
     */
    public void viewAllCards() {
        if (cards.isEmpty()) {
            System.out.println("No cards in the system.");
            return;
        }
        for (Card c : cards) {
            System.out.println(
                    "UID : " + c.getUid() +
                            " | Card Name : " + c.getName() +
                            " | Room : " + c.getPrivateRoomName() +
                            " | Card role : " + c.getRoleName() +
                            " | Secret Key : " + c.getSecretKey()
            );
        }
    }

    /**
     * แก้ไข Card (input Name หรือ UID)
     */
    public boolean editCard(String searchInput, String newName, int newRoleChoice, String newPrivateRoom) {
        Card card = getCardByNameOrUID(searchInput);
        if (card == null) {
            return false;
        }
        card.setName(newName);
        card.setPrivateRoomName(newPrivateRoom);
        card.setRole(buildRoleByChoice(newRoleChoice));

        LogManager.getInstance().addLog(
                "Edit Card => UID :" + card.getUid() +
                        " | NewName :" + newName +
                        " | NewRoom :" + newPrivateRoom +
                        " | NewRole :" + card.getRoleName()
        );
        return true;
    }

    /**
     * ลบ Card (input Name หรือ UID)
     */
    public boolean revokeCard(String searchInput) {
        Card card = getCardByNameOrUID(searchInput);
        if (card == null) {
            return false;
        }
        cards.remove(card);

        LogManager.getInstance().addLog(
                "Revoke Card => UID : " + card.getUid() +
                        " | Name : " + card.getName()
        );
        return true;
    }

    /**
     * Access Card
     */
    public boolean accessCard(String searchInput, String area) {
        Card card = getCardByNameOrUID(searchInput);
        if (card == null) {
            return false;
        }
        boolean result = card.canAccess(area);

        LogManager.getInstance().addLog(
                "Access => Card Name : " + card.getName() +
                        " | UID : " + card.getUid() +
                        " | Area : " + area +
                        " | Result : " + (result ? "Granted" : "Denied")
        );
        return result;
    }

    //========================================
    // Private methods
    //========================================

    /**
     * ค้นหา Card จาก Name หรือ UID
     */
    private Card getCardByNameOrUID(String input) {
        for (Card c : cards) {
            if (c.getUid().equalsIgnoreCase(input) || c.getName().equalsIgnoreCase(input)) {
                return c;
            }
        }
        return null;
    }

    /**
     * สร้าง UID 7 ตัว
     */
    private String generateUID() {
        String raw = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return raw.substring(0, 7);
    }

    /**
     * หา Card จาก UID => ใช้ใน while() กันซ้ำ
     */
    private Card getCardByUID(String uid) {
        for (Card c : cards) {
            if (c.getUid().equalsIgnoreCase(uid)) {
                return c;
            }
        }
        return null;
    }

    /**
     * สร้าง Role ตาม roleChoice
     */
    private Role buildRoleByChoice(int choice) {
        Role base = new BaseRole();
        switch (choice) {
            case 1: // Standard => Private + Low
                return new LowFloorDecorator(base);
            case 2: // Vip => Private + Low + Medium
                return new MediumFloorDecorator(new LowFloorDecorator(base));
            case 3: // VipPlus => Private + Low + Medium + High
                return new HighFloorDecorator(
                        new MediumFloorDecorator(
                                new LowFloorDecorator(base)
                        )
                );
            default:
                return new LowFloorDecorator(base);
        }
    }
}
