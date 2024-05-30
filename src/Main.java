import command.CommandProcessor;
import command.OrderCommand;
import command.QuitCommand;
import dataset.ItemDataSet;
import exception.SoldOutException;
import item.Item;

import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException, SoldOutException {
        /*
            메인 클래스로 구분 역할 명확해야 함
            메서드를 하나의 기능으로 잘개 쪼개서 하나의 기능만 담당하도록 해야한다
            BufferReader 대신 scanner 이용
            입력에, 맵에섯 데이터 로그, 명령어 실행 -> 최소 클래스 3개
            명령이 여러 개 추가되는 경우 if 개수 늘어남(루프 안에 명령어가 너무 많음)
            상속, 인터페이스 확용, solid 원칙 찾아보기
         */
        Map<String, Item> itemMap = ItemDataSet.getItemData();

        CommandProcessor commandProcessor = new CommandProcessor(
                new OrderCommand(),
                new QuitCommand()
        );

        commandProcessor.run(itemMap);

    }
}
