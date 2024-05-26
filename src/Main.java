import exception.SoldOutException;
import item.Item;
import item.ItemService;
import order.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException, SoldOutException {
        // 메서드를 ㅎ나의 기능으로 잘개 쪼개서 하나의 기능만 담당하도록 해야한다
        // 메인 클래스로 구분 역할 명
        Map<String, Item> itemMap = new HashMap<>();
        itemMap.put("768848", new Item("768848", "[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000, 45));
        itemMap.put("748943", new Item("748943", "디오디너리 데일리 세트 (Daily set)", 19000, 89));
        itemMap.put("779989", new Item("779989", "버드와이저 HOME DJing 굿즈 세트", 35000, 43));
        itemMap.put("779943", new Item("779943", "Fabrik Pottery Flat Cup & Saucer - Mint", 24900, 89));
        itemMap.put("768110", new Item("768110", "네페라 손 세정제 대용량 500ml 이더블유지", 7000, 79));
        itemMap.put("517643", new Item("517643", "에어팟프로 AirPods PRO 블루투스 이어폰(MWP22KH/A)", 260800, 26));

        ItemService itemService = new ItemService(itemMap);
        OrderService orderService = new OrderService(itemService);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // scanner 이
        // 입력에, 맵에섯 데이터 로그, 명령어 실행 -> 클래스 3개로 나눠야함 최소
        // 명령이 여러개가 생기는 경우 if 개수 늘어남 -> 고민
        // 명령이 추가 될 때 어떻게 할까
        // 루프안 안에 명령어가 너무 많음 곳곳용
        // 상ㅅ속, 인터페이스 확용, solid 원칙 찾아보기
        while (true) {
            System.out.println();
            System.out.print("입력(o[order]: 주문, q[quite]: 종료) : ");
            String answer = br.readLine();

            if (answer.equalsIgnoreCase("q") || answer.equalsIgnoreCase("quit")) {
                System.out.println("고객님의 주문 감사합니다.");
                break;
            } else if (answer.equalsIgnoreCase("o")) {
                orderService.processOrder();

            } else {
                System.out.println("다시 입력해주세요.");
            }

        }

    }
}
