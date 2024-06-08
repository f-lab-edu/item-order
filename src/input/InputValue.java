package input;

import java.util.Scanner;

public class InputValue {
    private static Scanner in = new Scanner(System.in);

    // 입력 받는것만 필요 , orderservice 와 연관됨
    // FSM
    // 루프 여기
    public static String getCommand(String command) {
        System.out.print(command + " : ");
        return in.nextLine();
    }

}
