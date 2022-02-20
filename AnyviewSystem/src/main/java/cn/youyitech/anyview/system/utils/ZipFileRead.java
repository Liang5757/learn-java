package cn.youyitech.anyview.system.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.stereotype.Component;

import cn.youyitech.anyview.system.dto.ImportQuestionContent;
import cn.youyitech.anyview.system.entity.QuestionContent;
import cn.youyitech.anyview.system.entity.QuestionHeaderFile;
import cn.youyitech.anyview.system.support.Setting;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

/**
 * 压缩包的读取和解压
 * 
 * @author zzq
 * @param path
 */

@Component
public class ZipFileRead {

    int headerNameCount = 0;
    
	// zip批量导入题目和导入题目内容
	// 传一个标志位，0代表批量导入题目，1代表导入题目内容
	public ImportQuestionContent readQuestionZip(String path, int flag) {
		Setting setting = SettingUtils.get();
		String newpath = setting.getUploadPath() + path;
//		 String newpath = System.getProperty("user.dir") + "/src/main/webapp/"
//		 + path;
		ImportQuestionContent importqc = new ImportQuestionContent();
		// 题目名称集合
		List<String> questionNameList = new ArrayList<>();
		// 题目内容集合
		List<QuestionContent> questionContentList = new ArrayList<>();
		// 题目内容
		QuestionContent questionContent = null;
		// 题目内容头文件集合
		List<QuestionHeaderFile> headerFileList = new ArrayList<>();
		// 题目中包含的头文件数目
        List<Integer> headerNameList = new ArrayList<>();
		ZipFile zipFile = null;
		Scanner scanner = null;
		InputStream is = null;
		byte[] b = new byte[3];
		try {
			StringBuilder content = new StringBuilder();
			zipFile = new ZipFile(newpath, Charset.forName("UTF-8"));
			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> enu = (Enumeration<ZipEntry>) zipFile.entries();
			if (flag == 1) {
				questionContent = new QuestionContent();
			}
			while (enu.hasMoreElements()) {
				// 每一次循环清除记录
				content.delete(0, content.length());

				ZipEntry zipElement = (ZipEntry) enu.nextElement();

				// 跳过苹果电脑打包的多一层MACOSX文件
				if (zipElement.getName().contains("MACOSX")) {
					continue;
				}

				// 文件名字
				String name = zipElement.getName().split("/")[(zipElement.getName().split("/").length) - 1];
				// 通过前三个字节判断文件的编码
				is = zipFile.getInputStream(zipElement);
				is.read(b);
				if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
					scanner = new Scanner(zipFile.getInputStream(zipElement), "utf-8");
				} else {
					scanner = new Scanner(zipFile.getInputStream(zipElement), "gbk");
				}

				while (scanner.hasNextLine()) {
					content.append(scanner.nextLine() + "\n");
				}
				if (!zipElement.isDirectory()) {
					importQuestionContent(name, content.toString(), questionContentList, questionContent,
							headerFileList,headerNameList);
				} else {
					if (flag == 0) {
						String[] tempName = zipElement.getName().split("/");
						if (tempName.length == 1) {
							questionContent = new QuestionContent();
							questionNameList.add(tempName[0]);
						}
					}
				}
			}
			// 把获取到的数据设置到dto
			importqc.setQuestionNameList(questionNameList);
			importqc.setQuestionContentList(questionContentList);
			importqc.setHeaderFileList(headerFileList);
			importqc.setHeaderNameList(headerNameList);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
				if (scanner != null) {
					scanner.close();
				}
				if (questionNameList != null) {
					questionNameList = null;
				}
				if (questionContentList != null) {
					questionContentList = null;
				}
				if (questionContent != null) {
					questionContent = null;
				}
				if (headerFileList != null) {
					headerFileList = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return importqc;
	}

	// zip批量导入题库
	public ImportQuestionContent readQuestionBankZip(String path) {
		Setting setting = SettingUtils.get();
		String newpath = setting.getUploadPath() + path;
//		 String newpath = System.getProperty("user.dir") + "/src/main/webapp/"
//		 + path;
		ZipFile zipFile = null;
		Scanner scanner = null;
		InputStream is = null;
		byte[] b = new byte[3];
		ImportQuestionContent importqc = new ImportQuestionContent();
		// 章节名集合
		List<String> chapterNameList = new ArrayList<>();
		// 一个章节拥有多少个题目
		List<Integer> questionNumberList = new ArrayList<>();
		// 记录一个章节拥有多少个题目数量
		int questionNumber = 0;
		// 题目名称集合
		List<String> questionNameList = new ArrayList<>();
		// 题目内容集合
		List<QuestionContent> questionContentList = new ArrayList<>();
		// 题目内容
		QuestionContent questionContent = null;
		// 题目内容头文件集合
		List<QuestionHeaderFile> headerFileList = new ArrayList<>();
		// 题目中包含的头文件数目
        List<Integer> headerNameList = new ArrayList<>();
		try {
			StringBuilder content = new StringBuilder();
			zipFile = new ZipFile(newpath, Charset.forName("UTF-8"));
			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> enu = (Enumeration<ZipEntry>) zipFile.entries();
			while (enu.hasMoreElements()) {
				// 每一次循环清除记录
				content.delete(0, content.length());

				ZipEntry zipElement = (ZipEntry) enu.nextElement();

				// 跳过苹果电脑打包的多一层MACOSX文件
				if (zipElement.getName().contains("MACOSX")) {
					continue;
				}
				String name = zipElement.getName().split("/")[(zipElement.getName().split("/").length) - 1];
				// 通过前三个字节判断文件的编码
				is = zipFile.getInputStream(zipElement);
				is.read(b);
				if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
					scanner = new Scanner(zipFile.getInputStream(zipElement), "utf-8");
				} else {
					scanner = new Scanner(zipFile.getInputStream(zipElement), "gbk");
				}
				while (scanner.hasNextLine()) {
					String string = scanner.nextLine();
					content.append(string + "\n");
				}
				if (!zipElement.isDirectory()) {
					importQuestionContent(name, content.toString(), questionContentList, questionContent,
							headerFileList,headerNameList);
				} else {
					String[] tempName = zipElement.getName().split("/");
					if (tempName.length == 1) {
						chapterNameList.add(tempName[0]);
						questionNumberList.add(questionNumber);
						questionNumber = 0;
					} else if (tempName.length == 2) {
						questionContent = new QuestionContent();
						questionNameList.add(tempName[1]);
						questionNumber++;
					}
				}
			}
			if (questionNumber != 0) {
				questionNumberList.add(questionNumber);
				questionNumber = 0;
			}
			// 把获取到的数据设置到dto
			importqc.setChapterNameList(chapterNameList);
			importqc.setQuestionNumberList(questionNumberList);
			importqc.setQuestionNameList(questionNameList);
			importqc.setQuestionContentList(questionContentList);
			importqc.setHeaderFileList(headerFileList);
			importqc.setHeaderNameList(headerNameList);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
				if (is != null) {
					is.close();
				}
				if (scanner != null) {
					scanner.close();
				}
				if (chapterNameList != null) {
					chapterNameList = null;
				}
				if (questionNumberList != null) {
					questionNumberList = null;
				}
				if (questionNameList != null) {
					questionNameList = null;
				}
				if (questionContentList != null) {
					questionContentList = null;
				}
				if (questionContent != null) {
					questionContent = null;
				}
				if (headerFileList != null) {
					headerFileList = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return importqc;
	}

	// name：文件名称，content：读取到的文件内容
	private void importQuestionContent(String name, String content, List<QuestionContent> questionContentList,
			QuestionContent questionContent, List<QuestionHeaderFile> headerFileList,
			List<Integer> headerNameList) {
	    System.out.println("->name="+name);
		if (name.contains("document") || name.contains("Document")) {

			// 设置题目描述
			questionContent.setQuestion_description(content);

		} else if (name.contains("dx") || name.contains("Dx")) {

			// 设置主文件内容
			questionContent.setHeaderfile_name(name);
			questionContent.setHeaderfile_content(content);

		} else if (name.contains("stds") || name.contains("Stds")) {

			// 设置标准答案
			questionContent.setStandard_answer(content);
			questionContentList.add(questionContent);
            headerNameList.add(headerNameCount);
            headerNameCount = 0;

		} else if (name.contains("head") || name.contains("Head") || name.contains(".h")) {

			// 设置头文件内容
			QuestionHeaderFile headerFile = new QuestionHeaderFile();
			headerFile.setHeader_file(name);
			headerFile.setHeader_file_content(content);
			headerFileList.add(headerFile);
			headerNameCount = headerNameCount + 1;

		} else if (name.contains("DS_Store")) {

		} else {

			// 设置学生答案
			questionContent.setStudent_answer(content);

		}
		

	}

	// 解压rar格式的批量导入题目(0)和导入题目内容(1)
	public ImportQuestionContent readQuestionRar(String path, int flag) {
		Setting setting = SettingUtils.get();
		String newpath = setting.getUploadPath() + path;
		// String newpath = System.getProperty("user.dir") + "/src/main/webapp/" + path;
		ImportQuestionContent importqc = new ImportQuestionContent();
		// 题目名称集合
		List<String> questionNameList = new ArrayList<>();
		// 题目内容集合
		List<QuestionContent> questionContentList = new ArrayList<>();
		// 题目内容
		QuestionContent questionContent = null;
		// 题目内容头文件集合
		List<QuestionHeaderFile> headerFileList = new ArrayList<>();
		// 题目中包含的头文件数目
		List<Integer> headerNameList = new ArrayList<>();
		InputStream is = null;
		byte[] b = new byte[3];
		Archive archive = null;
		Scanner scanner = null;
		File file = null;
		StringBuilder content = new StringBuilder();
		try {
			file = new File(newpath);
			archive = new Archive(file);
			if (archive != null) {
				if (flag == 1) {
					questionContent = new QuestionContent();
				}
				FileHeader fh = archive.nextFileHeader();
				while (fh != null) {
					// 每一次循环清除记录
					content.delete(0, content.length());

					String name = fh.getFileNameW().isEmpty() ? fh.getFileNameString() : fh.getFileNameW();
					String[] fileNames = name.split("\\\\");
					if (fh.isDirectory()) {
						if (flag == 0) {
							questionNameList.add(name);
						}
					} else {
						String fileName = fileNames[fileNames.length - 1];
						// 通过前三个字节判断文件的编码
						is = archive.getInputStream(fh);
						is.read(b);
						if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
							scanner = new Scanner(archive.getInputStream(fh), "utf-8");
						} else {
							scanner = new Scanner(archive.getInputStream(fh), "gbk");
						}
						while (scanner.hasNextLine()) {
							String string = scanner.nextLine();
							content.append(string + "\n");
						}
						if (flag == 0) {
							String dirName = fileNames[fileNames.length - 2];
							if (fileName.contains(dirName)) {
								questionContent = new QuestionContent();
							}
						}
						importQuestionContent(fileName, content.toString(), questionContentList, questionContent,
								headerFileList,headerNameList);
					}
					fh = archive.nextFileHeader();
				}
				// 把获取到的数据设置到dto
				importqc.setQuestionNameList(questionNameList);
				importqc.setQuestionContentList(questionContentList);
				importqc.setHeaderFileList(headerFileList);
				importqc.setHeaderNameList(headerNameList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (archive != null) {
					archive.close();
				}
				if (scanner != null) {
					scanner.close();
				}
				if (questionNameList != null) {
					questionNameList = null;
				}
				if (questionContentList != null) {
					questionContentList = null;
				}
				if (questionContent != null) {
					questionContent = null;
				}
				if (headerFileList != null) {
					headerFileList = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return importqc;
	}

	// 解压rar格式的批量导入题库
	public ImportQuestionContent readQuestionBankRar(String path) {
		Setting setting = SettingUtils.get();
		String newpath = setting.getUploadPath() + path;
//		 String newpath = System.getProperty("user.dir") + "/src/main/webapp/"
//		 + path;
		ImportQuestionContent importqc = new ImportQuestionContent();
		// 章节名称集合
		List<String> chapterNameList = new ArrayList<>();
		// 一个章节有多少个题目
		List<Integer> questionNumberList = new ArrayList<>();
		// 一个章节有多少个题目数量记载
		int questionNumber = 0;
		// 记录第一次到题目层次的目录
		boolean tempflag = true;
		// 记录之前的题目
		String old_questionBank = "";
		// 记录新的题目
		String new_questionBank = "";
		// 题目名称集合
		List<String> questionNameList = new ArrayList<>();
		// 题目内容集合
		List<QuestionContent> questionContentList = new ArrayList<>();
		// 题目内容
		QuestionContent questionContent = null;
		// 题目内容头文件集合
		List<QuestionHeaderFile> headerFileList = new ArrayList<>();
		// 题目中包含的头文件数目
        List<Integer> headerNameList = new ArrayList<>();
		InputStream is = null;
		byte[] b = new byte[3];
		File file = null;
		Archive archive = null;
		Scanner scanner = null;
		StringBuilder content = new StringBuilder();
		try {
			file = new File(newpath);
			archive = new Archive(file);
			if (archive != null) {
				FileHeader fh = archive.nextFileHeader();
				while (fh != null) {
					// 每一次循环清除记录
					content.delete(0, content.length());

					String name = fh.getFileNameW().isEmpty() ? fh.getFileNameString() : fh.getFileNameW();
					String[] fileNames = name.split("\\\\");

					if (fh.isDirectory()) {
						if (fileNames.length == 2) {
							if (tempflag) {
								old_questionBank = fileNames[fileNames.length - 2];
								questionNumberList.add(questionNumber);
								tempflag = false;
							}
							new_questionBank = fileNames[fileNames.length - 2];
							if (!new_questionBank.equals(old_questionBank)) {
								old_questionBank = new_questionBank;
								questionNumberList.add(questionNumber);
								questionNumber = 0;
							}
							questionNumber++;
							questionNameList.add(fileNames[fileNames.length - 1]);
						} else if (fileNames.length == 1) {
							chapterNameList.add(fileNames[fileNames.length - 1]);
						}
					} else {
						String fileName = fileNames[fileNames.length - 1];
						String dirName = fileNames[fileNames.length - 2];
						if (fileName.contains(dirName)) {
							questionContent = new QuestionContent();
						}
						// 通过前三个字节判断文件的编码
						is = archive.getInputStream(fh);
						is.read(b);
						if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
							scanner = new Scanner(archive.getInputStream(fh), "utf-8");
						} else {
							scanner = new Scanner(archive.getInputStream(fh), "gbk");
						}
						while (scanner.hasNextLine()) {
							String string = scanner.nextLine();
							content.append(string + "\n");
						}
						importQuestionContent(fileName, content.toString(), questionContentList, questionContent,
								headerFileList,headerNameList);
					}
					fh = archive.nextFileHeader();
				}
				// 把获取到的数据设置到dto
				importqc.setChapterNameList(chapterNameList);
				importqc.setQuestionNumberList(questionNumberList);
				importqc.setQuestionNameList(questionNameList);
				importqc.setQuestionContentList(questionContentList);
				importqc.setHeaderFileList(headerFileList);
				importqc.setHeaderNameList(headerNameList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (archive != null) {
					archive.close();
				}
				if (scanner != null) {
					scanner.close();
				}
				if (chapterNameList != null) {
					chapterNameList = null;
				}
				if (questionNumberList != null) {
					questionNumberList = null;
				}
				if (questionNameList != null) {
					questionNameList = null;
				}
				if (questionContentList != null) {
					questionContentList = null;
				}
				if (questionContent != null) {
					questionContent = null;
				}
				if (headerFileList != null) {
					headerFileList = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return importqc;
	}

}
