import java.util.Scanner;

public class GameHelper {
    private Scanner scanner = new Scanner(System.in);

    public int getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print(prompt + ": ");
        }
        return scanner.nextInt();
    }
}