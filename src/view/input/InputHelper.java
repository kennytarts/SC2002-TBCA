package view.input;

import java.util.Scanner;

public final class InputHelper {
    private InputHelper() {
    }

    public static int readIntInRange(Scanner scanner, int min, int max, String invalidMessage) {
        while (true) {
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
            }

            System.out.println(invalidMessage);
        }
    }
}
