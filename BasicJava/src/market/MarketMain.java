package market;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MarketMain {
	// 내부저장소(관리자 계정 ID와 PW 선언)
	String id = "admin";
	String pw = "1234";
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ProductDAO pDao = new ProductDAO();
		SaleDAO sDao = new SaleDAO();
		MarketMain mm = new MarketMain();
		int code = 0;
		boolean flag = false;
		
		// 프로그램 시작
		String userid = "";
		String userpw = "";
		System.out.println("▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨");
		System.out.println("▧▨ Market System Ver1.0 ▧▨");
		
		// 로그인 체크
//		do {
//			System.out.println("▧▨ [Msg] Please login to use.");
//			System.out.print("▧▨ ID>> ");
//			userid = sc.nextLine();
//			System.out.print("▧▨ PW>> ");
//			userpw = sc.nextLine();
//		} while(mm.login(userid, userpw));
		
		// 로그인 성공 업무 시작
		while(true) {
			while(true) {
				System.out.println("▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨");
				System.out.println("▧▨ 1. 제품 판매");
				System.out.println("▧▨ 2. 제품 등록&추가");
				System.out.println("▧▨ 3. 제품 수정") ;
				System.out.println("▧▨ 4. 제품 삭제");
				System.out.println("▧▨ 5. 제품 조회");
				System.out.println("▧▨ 6. 제품 검색");
				System.out.println("▧▨ 7. 일일 매출현황");
				System.out.println("▧▨ 8. 프로그램 종료");
				System.out.println("▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨");
				System.out.print("▧▨ Code>> ");
				code = sc.nextInt();
				
				if(code >= 1 && code <= 8) {
					break;
				} else {
					System.out.println("▧▨ [Msg] Please enter a valid value.");
				}
			}
			
			// 1~8중 코드 값 입력
			if(code == 1) {
				System.out.println("▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨");
				System.out.println("▧▨ 구매하고싶은 제품의 번호와 수량을 입력하세요.");
				// 현재 등록된 제품중 재고가 1보다 큰거(즉 수량이 0인 제품을 제외)
				List<ProductDTO> list = pDao.selectUsePdt();
				int cnt = 0;
				int tprice = 0;
				String sname = "";
				while(true) {
					System.out.print("▧▨ 구매번호>>");
					int buyCode = sc.nextInt();
					System.out.print("▧▨ 구매수량>>");
					cnt = sc.nextInt();
					// 판매하는 제품명
					sname = list.get(buyCode-1).getPname();
					// 사용자가 구매하려는 제품의 1개 가격
					int price = list.get(buyCode-1).getPrice(); 
					// 총가격=1개가격x구매수량
					tprice = price * cnt; 
					// 현재 제품에 재고량
					int nowCnt = list.get(buyCode-1).getCnt();
					
					if(nowCnt >= cnt) {
						break;
					} else {
						System.out.println("▧▨  [Msg] There is not enough quantity.");
					}
				}
				
				
				// tbl_sale에 판매한 기록을 저장(판매하는 제품명, 수량, 총가격)
				HashMap<String, Object> map = new HashMap<>();
				map.put("sname", sname);
				map.put("cnt", cnt);
				map.put("tprice", tprice);
				int result = sDao.insertSale(map);
				if(result > 0) {
					// tbl_product에서 재고를 마이너스
					pDao.cntMinusPdt(sname, cnt);
				} else {
					System.out.println("▧▨  [Msg] Error, Contact your admin.");
				}
				
				
			} else if(code == 2) {
				System.out.println("▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨");
				System.out.print("▧▨ 제품명:");
				sc.nextLine();
				String pname = sc.nextLine();
				
				if(pDao.pdtAlready(pname)) {
					// 기존에 등록된 제품임으로 추가(UPDATE) 기능
					// 입고수량 입력받아서 UPDATE
					System.out.print("▧▨ 입고수량:");
					int cnt = sc.nextInt();
					pDao.cntPlusPdt(pname, cnt);
				} else {
					// 최초 등록된 제품임으로 등록(INSERT) 기능
					// 제조회사, 가격, 입고수량 입력받아서 INSERT
					System.out.print("▧▨ 제조회사:");
					String company = sc.nextLine();
					System.out.print("▧▨ 가격:");
					int price = sc.nextInt();
					System.out.print("▧▨ 입고수량:");
					int cnt = sc.nextInt();
					ProductDTO pDto = new ProductDTO(pname, company, price, cnt);
					pDao.insertPdt(pDto);
				}
			} else if(code == 3) {
				
			} else if(code == 4) {
				System.out.println("▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨");
				System.out.print("▧▨ 삭제할 제품명:");
				sc.nextLine();
				String pname = sc.nextLine();
				pDao.deletePdt(pname);
			} else if(code == 5) {
				System.out.println("▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨");
				System.out.println("▧▨ 등록된 제품 전체를 조회합니다.");
				pDao.selectPdt();
			} else if(code == 6) {
				
			} else if(code == 7) {
				System.out.println("▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨");
				System.out.println("▧▨ 일일매출현황입니다.");
				sDao.dashBoard();
				
			} else if(code == 8) {
				System.out.println("▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨▧▨");
				System.out.println("▧▨ [Msg] Exit the program.");
				System.exit(0);
			} 
		}
	}
	
	public boolean login(String userid, String userpw) {
		Boolean flag = true; // 로그인 유무 판별(true:실패, false:성공)
		if(userid.equals(id) && userpw.equals(pw)) { // 로그인 성공
			flag = false;
			System.out.println("▧▨ [Msg] Welcome admin, Have a nice day");
		} else {
			System.out.println("▧▨ [Msg] Login denied.");
		}
		return flag;
	}
	
	
}








