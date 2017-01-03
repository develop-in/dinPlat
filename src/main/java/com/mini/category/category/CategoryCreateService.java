package com.mini.category.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.StringE;
import com.dinPlat.svc.code.C;
import com.dinPlat.svc.code.M;
import com.dinPlat.svc.exception.InvalidException;
import com.dinPlat.svc.exception.ValidException;
import com.mini.common.service.ServiceParent;

@Service ("CategoryCreateService")
public class CategoryCreateService extends ServiceParent {
		
	@Override
	@Transactional(rollbackFor={Exception.class, InvalidException.class}, noRollbackFor={ValidException.class})		// @Transactional Annotation은 doService method에 있어야지 정확하게 작동함.
	public ModelAndView doService (Object parameter, Object extendParameter) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		String tr = (String)(((Map)extendParameter).get("tr"));
		
		if (tr != null) {
			if (tr.equals(C.TR_INS)) {
				mav = insert_dp_category((Map)parameter, mav);
			} else if (tr.equals(C.TR_EDT)) {
				mav = query_dp_category((Map)parameter, mav);
			} else if (tr.equals(C.TR_UPD)) {
				mav = update_dp_category((Map)parameter, mav);
			}
		}
		else {
			mav.setViewName("/category/category/category_create");
		}
		
		return mav;
	}
	
	
	/** 카테고리 등록 */
	private ModelAndView insert_dp_category (Map parameter, ModelAndView mav) throws Exception {
		
		String codeHeader = "CTG";
		
		mav.setViewName("/category/category/category_save_result");
		
		// Sequence 구하기
		Map seqMap = new HashMap();
		dao.insert("category.getSeq", seqMap);
		String categoryId = codeHeader + StringE.lPadString(((Integer)seqMap.get("seq")).toString(), "0", 5);
		parameter.put("categoryId", categoryId);
		
		// 카테고리 등록
		try {
			if (parameter != null) {
				dao.insert("category.insert_dp_category", parameter);
			} else {
				super.setMessage(M.DB_NOT_NULL);
			}
		} catch (Exception e) {
			super.setMessage(M.DB_SAVE_FAIL);
			throw e;
		}
		
		mav.addObject("categoryId", categoryId);
		mav.addObject("categoryName", parameter.get("categoryName"));
		mav.addObject("description", parameter.get("description"));
		mav.addObject("menuName", parameter.get("menuName"));
		mav.addObject("directory", parameter.get("directory"));
		
		return mav;
		
	}
	
	
	/** 카테고리 edit */
	private ModelAndView query_dp_category (Map parameter, ModelAndView mav) throws Exception {
		
	mav.setViewName("/category/category/category_edit");
		
		// 조회
		Map categoryInfo = new HashMap();
		categoryInfo = (Map) dao.getSingleData("category.query_dp_category", parameter);
		
		mav.addObject("categoryId", categoryInfo.get("categoryId"));
		mav.addObject("categoryName", categoryInfo.get("categoryName"));
		mav.addObject("description", categoryInfo.get("description"));
		mav.addObject("menuName", categoryInfo.get("menuName"));
		mav.addObject("directory", categoryInfo.get("directory"));
		
		return mav;
		
	}

	
	/** 카테고리 수정 */
	private ModelAndView update_dp_category (Map parameter, ModelAndView mav) throws Exception {
		
		mav.setViewName("/category/category/category_save_result");
		
		// 카테고리 수정
		try {
			dao.update("category.update_dp_category", parameter);
		} catch (Exception e) {
			super.setMessage(M.DB_SAVE_FAIL);
			throw e;
		}
		
		mav.addObject("categoryId", parameter.get("categoryId"));
		mav.addObject("categoryName", parameter.get("categoryName"));
		mav.addObject("description", parameter.get("description"));
		mav.addObject("menuName", parameter.get("menuName"));
		mav.addObject("directory", parameter.get("directory"));
		
		return mav;
		
	}
	
}
