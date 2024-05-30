package dataset;

import item.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemDataSet {

    public static Map<String, Item> getItemData() {
        Map<String, Item> itemMap = new HashMap<>();
        itemMap.put("768848", new Item("768848", "[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000, 45));
        itemMap.put("748943", new Item("748943", "디오디너리 데일리 세트 (Daily set)", 19000, 89));
        itemMap.put("779989", new Item("779989", "버드와이저 HOME DJing 굿즈 세트", 35000, 43));
        itemMap.put("779943", new Item("779943", "Fabrik Pottery Flat Cup & Saucer - Mint", 24900, 89));
        itemMap.put("768110", new Item("768110", "네페라 손 세정제 대용량 500ml 이더블유지", 7000, 79));
        itemMap.put("517643", new Item("517643", "에어팟프로 AirPods PRO 블루투스 이어폰(MWP22KH/A)", 260800, 26));
        return itemMap;
    }
}
