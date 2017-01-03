package com.mini.category.task;

import java.util.ArrayList;
import java.util.HashMap;
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

@Service ("TaskFeatureService")
public class TaskFeatureService extends ServiceParent {
		
	@Override
	@Transactional(rollbackFor={Exception.class, InvalidException.class}, noRollbackFor={ValidException.class})		// @Transactional Annotation은 doService method에 있어야지 정확하게 작동함.
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String tr = (String) ((Map)extendParameter).get("tr");
		
		if (tr == null) {
			mav.setViewName("/category/task/task_feature");
		} else {
			if (tr.equals(C.TR_EDT)) {
				mav.setViewName("/category/task/task_feature_edit");
			} else if (tr.equals(C.TR_SAV)) {
				mav.setViewName("/category/task/task_feature");
				
				// feature update
				dao.update("task.update_feature", parameter);
				
				// reference 삭제
				dao.delete("task.delete_dp_task_reference", parameter);
				
				// reference 입력
				List referenceList = new ArrayList();
//				List referenceTypeList = (List) ((Map) parameter).get("referenceType");
				List descriptionList = new ArrayList();
				Object descriptionObj = (Object) ((Map) parameter).get("description");
				if (descriptionObj != null) {
					if (descriptionObj instanceof List) {
						descriptionList = (List) descriptionObj;
					} else {
						descriptionList.add((String) descriptionObj);
					}
				}

				List contentsList = new ArrayList();
				Object contentsObj = (Object) ((Map) parameter).get("contents");
					if (contentsObj != null) {
					if (contentsObj instanceof List) {
						contentsList = (List) contentsObj;
					} else {
						contentsList.add((String) contentsObj);
					}
				}
				
//				List descriptionList = (List) ((Map) parameter).get("description");
//				List contentsList = (List) ((Map) parameter).get("contents");
				
				for (int i=0; i<descriptionList.size(); i++) {
					Map refMap = new HashMap();
					refMap.put("taskId", ((Map)parameter).get("taskId"));
//					refMap.put("referenceType", referenceTypeList.get(i));
					refMap.put("referenceType", "1");
					refMap.put("description", descriptionList.get(i));
					refMap.put("contents", contentsList.get(i));
					referenceList.add(refMap);
				}
				
				if (descriptionObj != null) {
					Map referenceMap = new HashMap();
					referenceMap.put("reference", referenceList);
					dao.insert("task.insert_dp_task_reference", referenceMap);
				}
			}
		}
		
		Map featureInfo = (Map) dao.getSingleData("task.query_dp_task", parameter);
		List referenceList = (List) dao.getListData("task.referenceList", parameter);
		mav.addObject("scenario", featureInfo.get("scenario"));
		mav.addObject("algorithm", featureInfo.get("algorithm"));
		mav.addObject("taskId", featureInfo.get("taskId"));
		mav.addObject("referenceList", referenceList);
		
		DropLog.log(JsonE.beautifier(featureInfo));
		DropLog.log(JsonE.beautifier(referenceList));
		
		return mav;
	}

}
