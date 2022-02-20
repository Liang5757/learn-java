package cn.youyitech.anyview.system.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import cn.youyitech.anyview.system.support.Setting;

/**
 * POI解析Word文件内容
 * 
 * @author zzq
 * @param path
 */

@Component
public class PoiReadWord {

	// 解析Word文件内容
	public String readWord(String path) {
		String text = "";
		File file = new File(path);
		FileInputStream stream = null;
		// word低版本
		if (file.getName().endsWith(".doc")) {
			try {
				stream = new FileInputStream(file);
				WordExtractor word = new WordExtractor(stream);
				text = word.getText();
				text = text.replaceAll("(\\r\\n){2,}", "\r\n");
				text = text.replaceAll("(\\n){2,}", "\n");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (stream != null) {
						stream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		// word高版本
		else if (file.getName().endsWith(".docx")) {

			OPCPackage opcPackage;
			try {
				opcPackage = POIXMLDocument.openPackage(path);
				XWPFDocument xwpf = new XWPFDocument(opcPackage);
				POIXMLTextExtractor ex = new XWPFWordExtractor(xwpf);
				text = ex.getText();
				text = text.replaceAll("(\\r\\n){2,}", "\r\n");
				text = text.replaceAll("(\\n){2,}", "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return text;
	}

	// 读取单个文件
	public String readText(String path) {

		 Setting setting = SettingUtils.get();
		 String newpath = setting.getUploadPath() + path;
//		String newpath = System.getProperty("user.dir") + "/src/main/webapp/"
//		+ path;
		File file = new File(newpath);
		FileInputStream is = null;
		StringBuilder content = new StringBuilder();
		byte[] b = new byte[1024];
		int count = 0;
		String tempString = null;
		String code = "utf-8";
		boolean fristTime = true;
		try {
			is = new FileInputStream(file);

			while ((count = is.read(b)) != -1) {
				if (fristTime && !(b[0] == -17 && b[1] == -69 && b[2] == -65)) {
					code = "gbk";
					fristTime = false;
				}
				tempString = new String(b, 0, count, code);
				content.append(tempString);
			}
			return content.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "";

	}

}
