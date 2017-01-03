package com.Xnote.template;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.file.FileE;
import com.mini.common.service.ServiceParent;

@Service("XNoteService")
public class XNoteService extends ServiceParent {

	@Override
	public Object doService(Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String tr = (String) ((Map)extendParameter).get("tr");
		
		if (tr != null) {
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) ((Map)extendParameter).get("file");
			MultipartFile multipartFile = mpRequest.getFile("uploadFile");
			
			String filePath = "D:\\FIle\\temp\\file\\" + multipartFile.getOriginalFilename();
			
			FileE.writeFile(filePath, multipartFile);
			
//			File file = new File(filePath);
//			if (!file.exists()) {
//				file.mkdirs();
//			}
//			
//			mulitpartFile.transferTo(file);
			mav.setViewName("/common/result");
			mav.addObject("result", multipartFile.getOriginalFilename());
			
		} else {
			mav.setViewName("/template/xNote");
		}
		
		return mav;
	}

}

