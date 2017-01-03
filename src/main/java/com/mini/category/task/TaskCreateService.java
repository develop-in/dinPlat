package com.mini.category.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.box.ds.StringE;
import com.dinPlat.svc.code.C;
import com.dinPlat.svc.code.M;
import com.dinPlat.svc.log.DropLog;
import com.mini.common.service.ServiceParent;

@Service ("TaskCreateService")
public class TaskCreateService extends ServiceParent {
		
	@Override
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String tr = (String) ((Map)extendParameter).get("tr");
		
		if (tr != null) {
			if (tr.equals(C.TR_INS)) {
				mav = insert_dp_task((Map) parameter, mav);
			} else if (tr.equals(C.TR_EDT)) {
				mav = query_dp_task((Map) parameter, mav);
			} else if (tr.equals(C.TR_UPD)) {
				mav = update_dp_task((Map) parameter, mav);
			}
		} else {
			mav.setViewName("/category/task/task_create");
		}
		
		List categoryList = (List) dao.getListData("category.categoryList", null);
		mav.addObject("categoryList", categoryList);
		
		return mav;
	}

	/** TASK 등록 */
	private ModelAndView insert_dp_task (Map parameter, ModelAndView mav) throws Exception {
		String codeHeader = "TSK";
		
		// Sequence 구하기
		Map seqMap = new HashMap();
		dao.insert("category.getSeq", seqMap);
		String taskId = codeHeader + StringE.lPadString(((Integer)seqMap.get("seq")).toString(), "0", 5);
		parameter.put("taskId", taskId);
		
		mav.setViewName("/category/task/task_save_result");
		
		// TASK 등록
		try {
			if (parameter != null) {
				dao.insert("task.insert_dp_task", parameter);
			} else {
				super.setMessage(M.DB_NOT_NULL);
			}
		} catch (Exception e) {
			super.setMessage(M.DB_SAVE_FAIL);
			throw e;
		}
		
		mav.addObject("taskId", taskId);
		mav.addObject("taskName", parameter.get("taskName"));
		mav.addObject("categoryName", parameter.get("categoryName"));
		mav.addObject("categoryId", parameter.get("categoryId"));
		mav.addObject("menuName", parameter.get("menuName"));
		mav.addObject("description", parameter.get("description"));
		mav.addObject("directory", parameter.get("directory"));
		
		
		return mav;
		
	}
	
	
	/** TASK 조회 */
	private ModelAndView query_dp_task (Map parameter, ModelAndView mav) throws Exception {
		mav.setViewName("/category/task/task_edit");
		
		// TASK 조회
		Map taskMap = (Map) dao.getSingleData("task.query_dp_task", parameter);
		
		mav.addObject("taskId", taskMap.get("taskId"));
		mav.addObject("taskName", taskMap.get("taskName"));
		mav.addObject("categoryName", taskMap.get("categoryName"));
		mav.addObject("categoryId", taskMap.get("categoryId"));
		mav.addObject("menuName", taskMap.get("menuName"));
		mav.addObject("description", taskMap.get("description"));
		mav.addObject("directory", taskMap.get("directory"));
		
		DropLog.log(JsonE.beautifier(taskMap));
		
		return mav;
		
	}
	
	
	/** TASK 수정 */
	private ModelAndView update_dp_task (Map parameter, ModelAndView mav) throws Exception {
		
		mav.setViewName("/category/task/task_save_result");
		
		// TASK 수정
		try {
			if (parameter != null) {
				dao.update("task.update_dp_task", parameter);
			} else {
				super.setMessage(M.DB_NOT_NULL);
			}
		} catch (Exception e) {
			super.setMessage(M.DB_SAVE_FAIL);
			throw e;
		}
		
		mav.addObject("taskId", parameter.get("taskId"));
		mav.addObject("taskName", parameter.get("taskName"));
		mav.addObject("categoryName", parameter.get("categoryName"));
		mav.addObject("categoryId", parameter.get("categoryId"));
		mav.addObject("menuName", parameter.get("menuName"));
		mav.addObject("description", parameter.get("description"));
		mav.addObject("directory", parameter.get("directory"));
		
		
		return mav;
		
	}
}
