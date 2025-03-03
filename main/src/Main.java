import java.util.Scanner;
import manager.CardManager;
import cli.Menu;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CardManager cardManager = new CardManager();

        Menu menu = new Menu(scanner, cardManager);
        menu.start();

        scanner.close();
    }
}
