package input;

import java.util.Scanner;

public class InputValue {
    private static Scanner in = new Scanner(System.in);

    public static String getCommand(String command) {
        System.out.print(command + " : ");
        return in.nextLine();
    }

}
