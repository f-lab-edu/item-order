package fsm;

public enum Event {
    ORDER,
    ITEM_INPUT,
    QUANTITY_INPUT,
    FINISH,
    EXECUTE,
    QUIT;
    }
/*

INIT,
order 명령이 들어옴면 ordering 상태 변경

ORDERING,
실제로 주문이 가능한 상태
상품번호를 입력받는 상태로 전환한다.

ITEM,
상품 evnet 가 들어오면 그 값에 따라 수량을 입력받을지 주문을 실행할지

QUANTITY,
수량 event 가 들어오면 그 값에 따라 주문을 더 받을지 주문을 실행할지 설

EXECUTE
" " execute 명령이 들어오면 주문을 실행하고 종료한다

QUIT;
quit 명령이 들어오면 프로그램을 종료한다.







 */