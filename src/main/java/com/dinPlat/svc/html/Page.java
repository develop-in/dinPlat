package com.dinPlat.svc.html;

public class Page {
	
	/**
	 * HTML List 화면 하단의 페이지 처리를 하는 Method
	 * @param lineCount			페이지당 출력 Line 수
	 * @param pageCount			List 하단에 보여줄 페이지 수
	 * @param totalCount		조회된 총건수
	 * @param currentPage		조회하려는 현재 페이지
	 * @return
	 */
	public static String getPageList (int lineCount, int pageCount, int totalCount, int currentPage, String aTag) {

		String onePage = "[ 1 ]";
		
		if (totalCount == 0) return null;								// 조회건이 없을 경우
		if (totalCount <= lineCount) return onePage;					// 1페이지만 조회될 경우
		
		// 총 페이지수 구하기
		int totalPageCount = totalCount / lineCount;
		int remain = totalCount % lineCount;
		if (remain > 0) totalPageCount++;

		if (totalPageCount < currentPage) return null;					// 페이지 입력이 잘못된 경우 null 리턴
		if (currentPage < 1) return null;								// 페이지 입력이 잘못된 경우 null 리턴
		
		int divide = currentPage / pageCount;
		remain = currentPage % pageCount;
		
		// 시작페이지수 구하기
		int startPage = 0;
		if (currentPage <= pageCount) {
			startPage = 1;
		} else {
			if (remain == 0) {
				startPage = ((divide - 1) * pageCount) + 1;
			} else {
				startPage = (divide * pageCount) + 1;
			}
		}
		
		// 종료 페이지수 구하기
		int loopCount = totalPageCount - startPage;
		if (loopCount >= pageCount) {
			loopCount = pageCount;
		} else {
			loopCount++;
		}
		
//		System.out.println("lineCount : " + lineCount);
//		System.out.println("pageCount : " + pageCount);
//		System.out.println("totalPageCount : " + totalPageCount);
//		System.out.println("startPage : " + startPage);
//		System.out.println("loopCount : " + loopCount);

		// 페이지 처리
		String page[] = new String[loopCount];
		for (int i=0; i<loopCount; i++) {
			if (i == pageCount) break;
			page[i] = String.valueOf(startPage + i);
		}
		
		// Prev 처리
		String prev="";
		if (startPage > pageCount) prev = "Prev";

		// Next 처리
		String next="";
		if (totalPageCount >= (startPage + pageCount)) next = "Next";
		

		// Array에 담기 (Prev + Page + Next)
		int arraySize = page.length;
		if (!prev.equals("")) arraySize++;
		if (!next.equals("")) arraySize++;
		
		String pageArray[][] = new String[arraySize][arraySize];
		int idx=0;
		if (!prev.equals("")) {
			pageArray[0][0] = String.valueOf(startPage - 1);
			pageArray[0][1] = prev;
			idx++;
		}
		
		for (int i=0; i<page.length; i++, idx++) {
			pageArray[idx][0] = page[i];
			pageArray[idx][1] = page[i];
		}
				
		if (!next.equals("")) {
			pageArray[idx][0] = String.valueOf(startPage + loopCount);
			pageArray[idx][1] = next;	
		}
		
		
		// <a> tag 포함된 string 만들기
		String pageStr = "";
		for (int i=0; i<pageArray.length; i++, idx++) {
			pageStr = pageStr + " [ ";
			if (!pageArray[i][0].equals(String.valueOf(currentPage))) {
				pageStr = pageStr + aTag;
				pageStr = pageStr + pageArray[i][0] + ")>";
			}
			pageStr = pageStr + pageArray[i][1];
			
			if (!pageArray[i][0].equals(String.valueOf(currentPage))) {
				pageStr = pageStr + "</a>";
			}
			pageStr = pageStr + " ] ";
		}

		return pageStr;
	}

}
