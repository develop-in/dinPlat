package com.dinPlat.svc.exception;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.dinPlat.svc.code.M;

public class ExceptionE {

	/**
	 * 사용자 정의 Excpetion (InvalidException)은 각 서비스에서 처리를 하고 
	 * DB Exception, Exception에 대해서 공통 처리를 한다.
	 * 만약, DB관련 Exception에 대해서 특별한 처리가 필요한 부분은 해당 서비스에서 처리 하도록 한다.
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static Exception doException (Exception e) throws Exception {
		
		if (e instanceof InvalidException || e instanceof ValidException) { 

		} else if (e instanceof DataAccessException) {									// DB (MySQL 기준)
			SQLException se = (SQLException)((DataAccessException)e).getRootCause();
				
			switch (se.getErrorCode()) {
				case 1062 : e = new InvalidException(M.DB_DUPLICATE); break;
				case 1048 : e = new InvalidException(M.DB_NOT_NULL); break;
				default   : e = new InvalidException(((Integer)se.getErrorCode()).toString(), se.getMessage());
			}
			
		} else {
			e = new InvalidException(M.UNDEFINED_ERROR);
		}
		
		return e;
	}
}
