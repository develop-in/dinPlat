package com.mini.category.task;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.code.C;
import com.dinPlat.svc.exception.InvalidException;
import com.dinPlat.svc.exception.ValidException;
import com.dinPlat.svc.log.DropLog;
import com.mini.common.service.ServiceParent;

@Service ("TaskModifyService")
public class TaskModifyService extends ServiceParent {
		
	@Override
	@Transactional(rollbackFor={Exception.class, InvalidException.class}, noRollbackFor={ValidException.class})		// @Transactional Annotation은 doService method에 있어야지 정확하게 작동함.
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String tr = (String) ((Map)extendParameter).get("tr");
		if (tr == null) {
			// 카테고리 List 조회,
			List categoryList = (List) dao.getListData("category.categoryList", null);
			
			mav.setViewName("/category/task/task_modify");
			mav.addObject("categoryList", categoryList);
		} else {
			if (tr.equals("taskList")) {
				// Task List 조회
				String categoryId = (String) ((Map)parameter).get("categoryId");
				List taskList = (List) dao.getListData("task.taskList", categoryId);
				
				mav.setViewName("/common/result");
				mav.addObject("result", JsonE.changeJsonString(taskList));
				
				DropLog.log(JsonE.beautifier(taskList));
			} else if (tr.equals(C.TR_UPD)) {
				dao.update("task.update_dp_task", parameter);
				
				List categoryList = (List) dao.getListData("category.categoryList", null);
				mav.setViewName("/category/task/task_modify");
				mav.addObject("categoryList", categoryList);
			}
		}
		
		return mav;
	}

}
