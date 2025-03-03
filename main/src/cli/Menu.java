package cli;

import manager.CardManager;
import manager.LogManager;
import model.Card;
import util.TOTPGenerator;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private CardManager cardManager;

    public Menu(Scanner scanner, CardManager cardManager) {
        this.scanner = scanner;
        this.cardManager = cardManager;
    }

    public void start() {
        while (true) {
            showMenu();
            int choice = getIntInput("Choose (1-7): ");
            switch (choice) {
                case 1:
                    createCardMenu();
                    break;
                case 2:
                    viewCardMenu();
                    break;
                case 3:
                    editCardMenu();
                    break;
                case 4:
                    revokeCardMenu();
                    break;
                case 5:
                    accessCardMenu();
                    break;
                case 6:
                    LogManager.getInstance().viewLogs();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("=======================================");
        System.out.println("  Card Management System (Decorator)  ");
        System.out.println("=======================================");
        System.out.println("1. Create Card");
        System.out.println("2. View Card");
        System.out.println("3. Edit Card");
        System.out.println("4. Revoke Card");
        System.out.println("5. Access Card");
        System.out.println("6. View Log");
        System.out.println("7. Exit");
        System.out.println("=======================================");
    }

    //========== Menu 1 : Create Card ==========
    private void createCardMenu() {
        System.out.print("Card Name : ");
        String name = scanner.nextLine();

        System.out.print("Private Room : ");
        String privateRoom = scanner.nextLine();

        System.out.println("Choose Role (1-3): ");
        System.out.println("1. Standard Card  (Private + Low)");
        System.out.println("2. Vip Card       (Private + Low + Medium)");
        System.out.println("3. Vip Plus       (Private + Low + Medium + High)");
        int roleChoice = getIntInput("Enter choice: ");

        // Create
        Card c = cardManager.createCard(name, privateRoom, roleChoice);
        System.out.println("Created Card =>");
        System.out.println(" UID : " + c.getUid() +
                " | Card Name : " + c.getName() +
                " | Room : " + c.getPrivateRoomName() +
                " | Card role : " + c.getRoleName() +
                " | Secret Key : " + c.getSecretKey());
        // TOTP
//        String totp = TOTPGenerator.generateTOTP(c.getSecretKey());
//        System.out.println("TOTP (example): " + totp);
    }

    //========== Menu 2 : View Card ==========
    private void viewCardMenu() {
        cardManager.viewAllCards();
    }

    //========== Menu 3 : Edit Card (Name/UID) ==========
    private void editCardMenu() {
        System.out.print("Input Name or UID to edit : ");
        String search = scanner.nextLine();

        System.out.print("New Card Name : ");
        String newName = scanner.nextLine();

        System.out.print("New Private Room : ");
        String newRoom = scanner.nextLine();

        System.out.println("New Role (1-3): ");
        System.out.println("1. Standard");
        System.out.println("2. Vip");
        System.out.println("3. Vip Plus");
        int newRole = getIntInput("Enter choice: ");

        boolean success = cardManager.editCard(search, newName, newRole, newRoom);
        if (success) {
            System.out.println("Card updated successfully!");
        } else {
            System.out.println("Card not found!");
        }
    }

    //========== Menu 4 : Revoke Card (Name/UID) ==========
    private void revokeCardMenu() {
        System.out.print("Input Name or UID to revoke : ");
        String search = scanner.nextLine();

        boolean success = cardManager.revokeCard(search);
        if (success) {
            System.out.println("Card revoked successfully!");
        } else {
            System.out.println("Card not found!");
        }
    }

    //========== Menu 5 : Access Card ==========
    private void accessCardMenu() {
        System.out.println("(You can input either Card Name or UID)");
        System.out.print("Enter Card Name or UID: ");
        String search = scanner.nextLine();

        System.out.println("1. Private Room");
        System.out.println("2. Floor");
        int sub = getIntInput("Choose (1/2): ");

        String area;
        if (sub == 1) {
            // Private Room
            System.out.print("Enter Private Room number (e.g. R101, M101, etc.): ");
            area = scanner.nextLine();
        } else if (sub == 2) {
            // Floor
            System.out.println("1. Low Floor");
            System.out.println("2. Medium Floor");
            System.out.println("3. High Floor");
            int f = getIntInput("Choose (1/2/3): ");
            switch (f) {
                case 1:
                    area = "Low Floor";
                    break;
                case 2:
                    area = "Medium Floor";
                    break;
                case 3:
                    area = "High Floor";
                    break;
                default:
                    System.out.println("Invalid floor, default to 'Low Floor'");
                    area = "Low Floor";
            }
        } else {
            System.out.println("Invalid choice. Return to main menu.");
            return;
        }

        boolean result = cardManager.accessCard(search, area);
        if (result) {
            System.out.println("Access Granted!");
        } else {
            System.out.println("Access Denied!");
        }
    }

    //========== Utility : รับ int ==========
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }
}
