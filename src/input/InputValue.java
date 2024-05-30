package input;

import java.util.Scanner;

public class InputValue {
    private static Scanner in = new Scanner(System.in);

    public static String getCommand() {
        System.out.println();
        System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
        return in.nextLine();
    }

    public static String getItemId() {
        System.out.print("상품번호: ");
        return in.nextLine();
    }

    public static String getOrderCount() {
        System.out.print("수량: ");
        return in.nextLine();
    }
}
