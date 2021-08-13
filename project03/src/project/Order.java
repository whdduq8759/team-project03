package project;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Order {
    //=======================================
    //++++++++++++++ 전역 변수 +++++++++++++++
    //=======================================

    //사장님 메뉴판 수정에서 사용, 메뉴선택시 이외의 선택지는 다시 입력
    static String[] badInput = {"짜장면", "짬뽕", "볶음밥", "탕수육", "칠리새우"};
    // 사장님 메뉴판 수정에서 사용할 배열
    static int[] outterCost = {5000, 6000, 7000, 12000, 30000};

    //전역변수로 뺄 때는 static 사용.
    static Scanner sc = new Scanner(System.in);
    // 음식 선택한 배열
    static String[] menuBox = new String[0];
    //음식 선택한 것
    static String foods;
    // 요청사항 배열
    static String[] requestBox = new String[0];
    // 주문음식 총 금액
    static int total;

    // 취소함수에서 입력양식 맞나 안맞나 확인 + 음식 취소할 때 사용할 변수
    static int outterDelete;

    //=========================================
    //++++++++++사장님, 손님 구분 로그인++++++++++
    //손님 계정 아이디 확인 후 존재하면 폼으로 이동
    //=========================================

    static String guestLogin(String id) {
        String[] idArr = {"guest1", "guest2"};
        String[] pwArr = {"1234", "5678"};
        String[] nickArr = {"밥그릇", "숟가락"};
        int idx = -1;

        while (true) {
            //id 인덱스 탐색
            for (int i = 0; i < idArr.length; i++) {
                if (idArr[i].equals(id)) {
                    idx = i;
                    //동일한 인덱스의 비밀번호 배열에서 일치여부 조회
                    System.out.println("비밀번호를 입력해 주세요!");
                    String pw = sc.next();

                    if (pwArr[idx].equals(pw)) { //로그인 성공
                        //로그인 성공
                        System.out.println(nickArr[idx] + "님 환영합니다!\n");

                        //손님 폼으로 이동
                        guestForm();
                        break;

                    } else { //로그인 실패
                        System.out.println("비밀번호가 일치하지 않습니다! 프로그램을 종료합니다.");
                        break;
                    }//pw id == pw if
                } else {
                    System.out.printf("%s은 존재하지 않습니다.", id);
                    break;
                }
            }//ID for end
            return id;
        }//while end
    }//end guestLogin 손님 계정 로그인 끝


    //=======================================
    //++++++++++++++손님 전용 폼+++++++++++++++
    //=======================================

    // 메뉴선택 함수
    static void menuSelect() {
        // 선택한 음식을 보관하는 배열
        String[] selectFood = new String[0];

        System.out.println("***** 메뉴를 선택 하세요 *****");
        for (int i = 0; i < badInput.length; i++) {
            System.out.println(badInput[i] + " - " + outterCost[i] + "원");
//            System.out.println("짜장면 - 5000원");
//            System.out.println("짬뽕 - 6000원");
//            System.out.println("볶음밥 - 7000원");
//            System.out.println("탕수육 - 12000원");
//            System.out.println("칠리새우 - 30000원");
        }
        System.out.println("메뉴 선택이 완료 되었으면 \"선택완료\"라고 입력하세요!!");
        //총 액수
        int totalCost = 0;

        // 주문작업
        while (true) {
            System.out.print(">> ");

            // 음식을 선택하는 작업
            String food = sc.next();
            foods = food;
            sc.nextLine();

            if (food.equals("선택완료")) {
                boolean flag = selectYesOrNo(selectFood, totalCost);
                //***
                if (flag) {
                    break;
                } else {
                    String[] resetFood = new String[0];

                    // 배열초기화와 총 금액 초기화
                    selectFood = resetFood;
                    menuBox = resetFood;
                    resetFood = null;
                    totalCost = 0;
                    continue;
                }
            } else if (food.equals("짜장면")) {
                totalCost += 5000;
            } else if (food.equals("짬뽕")) {
                totalCost += 6000;
            } else if (food.equals("볶음밥")) {
                totalCost += 7000;
            } else if (food.equals("탕수육")) {
                totalCost += 12000;
            } else if (food.equals("칠리새우")) {
                totalCost += 30000;
            } else if (!food.equals(badInput)) {
                System.out.println("잘못 입력하셨습니다. 위에 메뉴판을 보시고 다시 입력해주시기 바랍니다.");
            }

            // 선택한 음식들이 메누판에 있는 음식이면 배열에 담는 작업 (메뉴판에 없는 음식이면 배열에 담지 않음)
            if (food.equals(badInput[0]) || food.equals(badInput[1]) || food.equals(badInput[2]) || food.equals(badInput[3]) || food.equals(badInput[4])) {
                // selectFood의 음식들을 넣는 새로운 배열
                String[] temp = new String[selectFood.length + 1];

                // 선택한 음식을 배열에 넣는 것
                for (int i = 0; i < selectFood.length; i++) {
                    temp[i] = selectFood[i];
                }

                temp[temp.length - 1] = food;
                selectFood = temp;
                menuBox = selectFood;
                temp = null;
            }
        } // end while
    } // end menuSelect

    //주문 최종 확인
    private static boolean selectYesOrNo(String[] selectFood, int totalCost) {
        while (true) {
            System.out.println(" ");
            System.out.println("주문하신 음식은 " + Arrays.toString(selectFood) + " 입니다.");
            System.out.println("주문 하신 음식의 가격은 " + totalCost + "입니다.");
            System.out.println(" ");
            System.out.println("주문 하시려면 \"예\"를 다시 주문하고 싶으면 \"아니요\"를 입력하세요!!");
            System.out.print(">> ");
            String lastFoodSelect = sc.next();
            sc.nextLine();

            //주문 수락 -> 총 금액 전역변수 total에 복사, 요청사항, 메뉴수정 함수로 넘어가기
            if (lastFoodSelect.equals("예")) {
                System.out.println(" ");
                System.out.println("주문하신 음식: " + Arrays.toString(selectFood));
                System.out.println("총 금액: " + totalCost + "원");
                System.out.println(" ");

                //총 금액 전역변수에 복사하기
                total = totalCost;

                //요청사항, 메뉴수정 함수
                menuModifyDelete();
                requestList();

                System.out.println("");
                System.out.println("#  주문이 접수되었습니다!!");
                return true;
            } else if (lastFoodSelect.equals("아니요")) {
                System.out.println("주문을 처음부터 다시 접수하세요!!");
                System.out.println("");
                // selectFood 배열을 빈배열로 만드는 작업 (초기화)
                return false;
            }
        } // end while
    }

    // 주문후 수정, 삭제, 그대로 주문 기능이 있는 함수
    static void menuModifyDelete() {
        while (true) {
            System.out.println("1. 주문메뉴 수정하기 2. 주문메뉴 취소하기 3. 이대로 주문하기");
            System.out.print(">> ");
            int deleteOrOrder = sc.nextInt();
            outterDelete = deleteOrOrder;
            sc.nextLine();


            // 1, 2, 3이 아니면 다시 입력하도록 하는 작업
            if (deleteOrOrder == 1 || deleteOrOrder == 2 || deleteOrOrder == 3) {
                break;
            } else {
                System.out.println(" ");
                System.out.println("입력 양식에 맞게 입력하세요!!");
                System.out.println(" ");
            }
        }

        //1. 음식을 바꿀수 있는 기능 2.음식을 삭제할 수 있는 기능 3. 이대로주문시에는 특정 이벤트 없음.
        switch (outterDelete) {
            // 기존 음식을 다른 음식으로 바꾸는 기능
            case 1:
                while (true) {
                    System.out.println(" ");
                    System.out.println(Arrays.toString(menuBox));
                    System.out.println("바꾸고 싶은 음식을 입력해 주세요!");
                    System.out.print(">> ");
                    String modifyFood = sc.next();
                    sc.nextLine();

                    int idx = -1;

                    for (int i = 0; i < menuBox.length; i++) {
                        if (menuBox[i].equals(modifyFood)) {
                            idx = i;
                            break;
                        }
                    }


                    if (idx != -1) {
                        System.out.println(" ");
                        System.out.println("어떤 음식으로 바꾸시겠습니까?");
                        System.out.println("***** 짜장면, 짬뽕, 볶음밥, 탕수육, 칠리새우 *****");
                        System.out.print(">> ");
                        String changeFood = sc.next();
                        sc.nextLine();
                        menuBox[idx] = changeFood;

                        System.out.println(" ");
                        System.out.println("주문하신 음식: " + Arrays.toString(menuBox));
                        System.out.println("주문을 진행합니다!");
                        System.out.println(" ");
                        break;
                    }
                }
                break;

            // 기존음식을 삭제하는 기능
            case 2:
                System.out.println(Arrays.toString(menuBox));
                System.out.println("취소하고 싶은 음식을 골라 주세요!");
                System.out.print(">>");
                String deleteFood = sc.next();
                sc.nextLine();


                int idx = -1;

                for (int i = 0; i < menuBox.length; i++) {
                    if (menuBox[i].equals(deleteFood)) {
                        idx = i;
                        break;
                    }
                }
                // 탐색 후에 배열안에 있으면 앞으로 옮기는 작업
                if (idx != -1) {

                    for (int i = idx; i < menuBox.length - 1; i++) {
                        menuBox[i] = menuBox[i + 1];
                        break;
                    }
                }
                // 새로운 배열 생성
                String[] deleteTemp = new String[menuBox.length - 1];

                // 새로운 배열에 삭제된 배열을 넣는 작업
                for (int i = 0; i < deleteTemp.length; i++) {
                    deleteTemp[i] = menuBox[i];
                }

                menuBox = deleteTemp;
                deleteTemp = null;

                System.out.println(" ");
                System.out.println("주문하신 음식: " + Arrays.toString(menuBox));
                System.out.println("주문을 진행합니다!");
                System.out.println(" ");
                break;

            // 기존음식을 그대로 주문하는 기능
            case 3:
                System.out.println(" ");
                System.out.println("주문하신 음식: " + Arrays.toString(menuBox));
                System.out.println("주문을 진행합니다!");
                System.out.println(" ");
                break;

        }
    } // end menuModifyDelete


    // 요청사항 함수
    static void requestList() {
        System.out.println("음식 주문 요청사항을 입력하세요!!");
        while (true) {
            System.out.print(">> ");

            // 요청사항을 입력하는 작업
            String request = sc.nextLine();

            // 요청사항을 보관하는 배열
            String[] requestStore = new String[0];

            // requestStore의 요청사항을 넣는 새로운 배열
            String[] requestTemp = new String[requestStore.length + 1];

            for (int i = 0; i < requestStore.length; i++) {
                requestTemp[i] = requestStore[i];
            }

            requestTemp[requestTemp.length - 1] = request;
            requestStore = requestTemp;
            requestBox = requestTemp;
            requestTemp = null;

            System.out.println("요청사항이 입력되었습니다!!");
            System.out.println(" ");

            // 최종 주문을 입력하는 작업
//         System.out.println("주문하신 음식은 " + Arrays.toString(selectFood) + "입니다.");
            System.out.println("고객님의 요청사항은 " + Arrays.toString(requestStore) + "입니다.");

            System.out.println();

            // 최종 주문이 이루어지는 작업
            System.out.println("위의 내용이 맞으면 \"예\"를 틀리면 \"아니요\"를 입력하세요!!");
            System.out.print(">> ");
            String lastOrder = sc.next();
            sc.nextLine();

            // true 와 false를 담는 변수 선언
            boolean rightAnswer;
            boolean wrongAnswer;

            // 최종 판단
            if (lastOrder.equals("예")) {
                rightAnswer = true;
                System.out.println(" ");
                System.out.println("주문한 음식: " + Arrays.toString(menuBox));
                System.out.println("요청사항: " + Arrays.toString(requestStore));
                System.out.println(" ");
                System.out.println("최종적으로 주문 접수가 완료 되었습니다. 감사합니다!!");
                System.out.println(" ");
                System.out.println("메인 화면으로 돌아갑니다.\n\n");
                guestForm();
                return;
            } else if (lastOrder.equals("아니요")) {
                wrongAnswer = false;
                System.out.println("요청사항을 다시 입력해주세요^^");
                // requestStore 배열을 빈배열로 만드는 작업 (초기화)
                String[] resetRequest = new String[0];
                requestStore = resetRequest;
                resetRequest = null;
                continue;
            }

        }
    }// end menuSelect

    // 주문내역 함수
    static void orderList() {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일");
        Date time = new Date();
        String time2 = format2.format(time);

        System.out.println("");
        System.out.println("*****최근 주문목록 *****");
        if (menuBox.length != 0) {
            for (int i = 0; i < menuBox.length; i++) {
                System.out.println(time2 + " 주문한 음식: " + menuBox[i]);
            }
            for (int i = 0; i < requestBox.length; i++) {
                System.out.println("[요청사항: " + requestBox[i] + "]");
            }
            System.out.println(" ");
        } else {
            System.out.println("최근에 주문한 것이 없습니다. 많이 이용해주세요!!");
            System.out.println(" ");
        }
    }

    //=====================================
    //              게스트 폼
    //=====================================
    static void guestForm() {
        System.out.println("=================================");
        System.out.println("1. 메뉴 선택");
        System.out.println("2. 내 주문 기록");
        System.out.println("3. 로그아웃");
        System.out.println("4. 프로그램 종료");
        System.out.println("=================================");
        System.out.println("원하는 메뉴 번호를 입력해 주세요!");

        int menu = sc.nextInt();
        System.out.println("");

        switch (menu) {
            case 1:
                System.out.println("1. 메뉴 선택");
                menuSelect();
                guestForm();
                break;
            case 2:
                System.out.println("2. 내 주문 기록");
                orderList();
                guestForm();
                break;
            case 3:
                System.out.println("3. 로그아웃");
                login();
                break;
            case 4:
                System.out.println("4. 프로그램 종료");
                break;
            default:
                System.out.println("# 잘못된 입력입니다. 프로그램을 종료합니다.");
                break;
        }
    }


    //=======================================
    //+++++++++++++사장님 전용 폼++++++++++++++
    //=======================================

    //사장님 폼 - 신규 주문서 확인함수
    //주문 취소 시 메인 폼으로 돌아간다.
    static void OrderView() {
        if (menuBox.length > 0) {

            System.out.println("주문이 들어왔습니다!\n" + "주문음식: " + Arrays.toString(menuBox) + "\n 요청사항: " + Arrays.toString(requestBox));
            System.out.println("총 금액 : " + total + "원");
            System.out.println("");

            System.out.println("주문을 수락하시겠습니까?");
            System.out.println("1. 예 2. 아니요 취소하겠습니다.");
            int choose = sc.nextInt();
            sc.nextLine();
            System.out.println("");
            switch (choose) {
                case 1:
                    System.out.println("주문이 수락되었습니다!");
                    System.out.println("메뉴로 돌아갑니다!");
                    System.out.println("");
                    break;
                case 2:
                    //주문 취소 => 주문 배열 비우고 메인 폼으로 돌아가기
                    System.out.println("주문이 취소되었습니다!");
                    System.out.println("메뉴로 돌아갑니다!");
                    System.out.println("");

                    menuBox = new String[0];
                    total = 0;
                    break;
            }
        } else {
            System.out.println("주문 내역이 없습니다!");
            System.out.println("메뉴로 돌아갑니다!");
            System.out.println("");
        }
    }

    //사장님 폼 - 3. 최근 주문기록 조회하기
    static void historyMaster() {
        //기본값 음식 기록들
        String[] history = {"[08.02]숟가락님의 주문입니다", "[08.03]밥그릇님의 주문입니다.", "[08.03]뚝배기님의 주문입니다."};
        int[] price = {13500, 28000, 10000};
        int total = 0;

//        System.out.println("3. 최근 주문기록 조회하기");
        System.out.println("이번달 주문내역은 총" + history.length + "개 입니다.");

        for (int i = 0; i < price.length; i++) {
            total += price[i];
        }

        System.out.printf("이번달 총 수익은 %d원 입니다.\n", total);
        System.out.println("");

    }

    //사장님 폼 메인 화면
    static void masterForm() {
        System.out.println("=================================");
        System.out.println("1. 메뉴판 수정하기");
        System.out.println("2. 신규 주문 확인하기");
        System.out.println("3. 최근 주문기록 조회하기");
        System.out.println("4. 로그아웃");
        System.out.println("5. 프로그램 종료");
        System.out.println("=================================");
        System.out.println("원하는 메뉴 번호를 입력해 주세요!");
        int menu = sc.nextInt();
        System.out.println("");

        switch (menu) {
            case 1:
                System.out.println("1. 메뉴판 수정이 메뉴판 배열이 없어서 불가능");
                System.out.println("");
                masterForm();
                break;
            case 2:
                System.out.println("2. 주문서 조회하기"); //+ 주문취소 여부
                OrderView();
                masterForm();
                break;
            case 3:
                System.out.println("3. 최근 주문기록 조회하기");
                historyMaster();
                masterForm();
                break;
            case 4:
                System.out.println("3. 로그아웃");
                login();
                break;
            case 5:
                System.out.println("4. 프로그램 종료");
                break;
        }
    }

    //==================================
    //++++++++++++로그인 함수+++++++++++++
    //손님, 사장님 로그인에 따라 폼이 구분된다.
    static void login() {
        System.out.println("=================================");
        System.out.println("\n++++++++++주문서 프로그램++++++++++\n");
        System.out.println("=================================");
        System.out.println("로그인이 필요합니다!");
        System.out.println("id를 입력 해주세요!");
        System.out.print(">> ");
        String id = sc.next();
        System.out.println("");

        switch (id) {
            case "master"://사장님 계정, 비밀번호 일치시 함수 실행
                System.out.println("사장님 계정 입니다.");
                System.out.println("사장님 비밀번호를 입력해 주세요!");
                String pw = sc.next();
                System.out.println("");
                String realPw = "abc";
                if (pw.equals(realPw)) {
                    System.out.println("사장님 어서오세요!");
                    System.out.println("");
                    //로그인시 사장님 폼으로 이동.
                    masterForm();
                    break;
                } else {
                    System.out.println("비밀번호가 틀렸습니다. 다시 로그인 해 주세요.");
                    System.out.println("");
                    //비밀번호 실패 시 재 로그인 필요.
                    login();
                    break;
                }
            default: //손님 계정. 아이디 일치 후 비밀번호 확인 => 게스트함수 실행
                guestLogin(id);
                break;
        }
    }

    // ===================================================
    // +++++++++++++++++메인 실행 함수+++++++++++++++++++++
    // ===================================================

    public static void main(String[] args) {//메인함수
        //로그인 창으로 돌아갈 수 있도록 로그인 함수 login()으로 시작.
        //로그인 확인 후 각자 손님/사장님 폼으로 이동 후 작동.
        login();
    }//class end
}