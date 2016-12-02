package com.dinPlat.svc.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.dinPlat.svc.code.M;
import com.dinPlat.svc.exception.InvalidException;

public class CheckDefinition {

	public final static void checkInputField (Object instance) throws InvalidException, Exception {
		if (instance == null) {
			throw new InvalidException(M.FLEXIBLE_MESSAGE, "Input data is not null.");
		}
		
		// Input Field로 들어온 모든 데이터를 copy
		Field[] fields = instance.getClass().getDeclaredFields();
		
		for (int i=0; i<fields.length; i++) {
			Field f = fields[i];
			Annotation annotation = f.getAnnotation(Definition.class);
			
			// Annotaion이 걸려 있지 않은 Field는 pass
			if (annotation == null) continue;
			
			f.setAccessible(true);
			
			// Input Data 
			String field = (String)f.get(instance);
			
			// Input Data에 해당하는 Field에 설정된 Annotation 데이터
			boolean required = ((Definition)annotation).required();
			int minLength = ((Definition)annotation).minLength();
			int maxLength = ((Definition)annotation).maxLength();
			int equalLength = ((Definition)annotation).equalLength();
			String dateFormat = ((Definition)annotation).dateFormat();
			String[] checkedData = ((Definition)annotation).checkedData();
			
			// 필수값 Check
			if (required) {
				if (field == null || field.equals("")) 
					throw new InvalidException(M.FLEXIBLE_MESSAGE, f.getName() + " cannot be null.");
			}
			
			// 길이 Check
			if (field != null) {
				if (field.length() < minLength) 
					throw new InvalidException
						(M.FLEXIBLE_MESSAGE, f.getName() + " cannot be shorter than minLength (" + minLength + "). [minLength]");
				if (field.length() > maxLength)
					throw new InvalidException
						(M.FLEXIBLE_MESSAGE, f.getName() + " cannot be longer than maxLength (" + maxLength + "). [maxLength]");
//				if (field.length() != equalLength)
//					throw new GangException(InputFieldValidator.class.toString() + " : " + f.getName() + 
//							" is " + equalLength + " length. [equalLength]");
			}
			
			// 값 비교
			if (checkedData.length > 0) {
				boolean is = false;
				
				for (String cd : checkedData) {
					if (cd != null && cd.length() > 0) {
						if (cd.equals(field)) {
							is = true;
							break;
						}
					}
				}
				
				if (!is) 
					throw new 
					InvalidException(M.FLEXIBLE_MESSAGE, 
									 f.getName() + " is available within " + Arrays.toString(checkedData) + ". [checkedData]");
			}
			
			// DateFormat 확인
			if (dateFormat != null && !dateFormat.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				try {
					sdf.parse(field);
				} catch (ParseException e) {
					throw new InvalidException(M.FLEXIBLE_MESSAGE, f.getName() + " correct format is " + dateFormat + ". [dateFormat]");
				}
			}
		}
	}
	
}
