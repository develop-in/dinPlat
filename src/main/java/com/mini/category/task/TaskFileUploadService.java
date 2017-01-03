package com.mini.category.task;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.box.file.FileE;
import com.dinPlat.svc.code.C;
import com.dinPlat.svc.exception.InvalidException;
import com.dinPlat.svc.exception.ValidException;
import com.mini.common.service.ServiceParent;

@Service ("TaskFileUploadService")
public class TaskFileUploadService extends ServiceParent {
		
	@Override
	@Transactional(rollbackFor={Exception.class, InvalidException.class}, noRollbackFor={ValidException.class})		// @Transactional Annotation은 doService method에 있어야지 정확하게 작동함.
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) ((Map)extendParameter).get("file");
		MultipartFile multipartFile = mpRequest.getFile("uploadFile");
		
		String taskId = (String) ((Map)parameter).get("taskId");
		String referenceId = (String) ((Map)parameter).get("referenceId");
		String filePath = C.TSK_FILE_PATH + taskId + "\\" + referenceId + "\\" + multipartFile.getOriginalFilename();
		
		FileE.writeFile(filePath, multipartFile);
		
		mav.setViewName("/common/result");
		mav.addObject("result", multipartFile.getOriginalFilename());
		
		return mav;
	}

}
