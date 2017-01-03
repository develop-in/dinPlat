package com.dinPlat.box.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.springframework.web.multipart.MultipartFile;

public class FileE {

	public final static String NEW_FILE = "new";
	public final static String EXIST_FILE = "exist";
	
	/**
	 * File이 해당 경로에 존재하는지 확인하는 Method.
	 * @param filePath
	 * @return true (존재함), false (없음)
	 * @throws Exception
	 */
	public static boolean findFile (String filePath) throws Exception {
		
		boolean exist = false;
		
		try {
			File file = new File(filePath);
			if (file.exists()) exist = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return exist;
	}
	
	/**
	 * 해당경로로 File을 만들고 stream형태로 File을 write할 수 있는 FileOutputStream 객체를 반환하는 Method.
	 *   ex) fos.write((fileData+System.getProperty("line.separator")).getBytes());
	 * @param filePath
	 * @param mode			: "new"일 경우 기존 File 삭제하고 신규 File return.
	 * 						: "exist"일 경우 기존 File을 return.
	 * @return
	 * @throws Exception
	 */
	public static FileOutputStream getFile (String filePath, String mode) throws Exception {
		File file = null;
		FileOutputStream fos = null;
		
		try {
			file = new File(filePath);
			
			boolean exist = file.exists();
			if (mode.equals(NEW_FILE)) {
				if (exist) {
					file.delete();
				}
				file.createNewFile();
			} else {
				if (exist) {
				} else {
					file.createNewFile();
				}
			}
			
			fos = new FileOutputStream(file, true);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return fos;
	}
	
	
	
	/**
	 * 해당경로로 File을 만들고 String으로 쓸수 있는 PrinterWriter 객체를 반환하는 Method. (기존파일이 있으면 삭제 후 신규 생성).
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static PrintWriter getFile (String filePath) throws Exception {
		PrintWriter printWriter = null;

		try {
			File rFile = new File(filePath);
			FileWriter rWriter = new FileWriter(rFile);
			BufferedWriter rB = new BufferedWriter(rWriter);
			printWriter = new PrintWriter(rB);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return printWriter;
	}
	
	
	/**
	 * WEB에서 multipart/form-data를 이용하여 File을 Upload할 경우 upload된 파일을 filePath에 write하는 Method.
	 * @param filePath
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	public static boolean writeFile (String filePath, MultipartFile multipartFile) throws Exception {
		
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		multipartFile.transferTo(file);
		
		return true;
	}
}
