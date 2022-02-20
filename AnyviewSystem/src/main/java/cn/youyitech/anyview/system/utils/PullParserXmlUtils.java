package cn.youyitech.anyview.system.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.kxml2.io.KXmlSerializer;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import cn.youyitech.anyview.system.dto.QuestionTemp;
import cn.youyitech.anyview.system.entity.QuestionContent;
import cn.youyitech.anyview.system.entity.QuestionHeaderFile;

@Component
public class PullParserXmlUtils {

	private static final String PROBLEM = "problem";
	private static final String CONFIG = "config";
	private static final String DOC = "doc";
	private static final String MAIN_FILE = "main-file";
	private static final String MAIN_FILE_NAME = "mainName";
	private static final String ANSWER_FILE = "answer-file";
	private static final String FILENAME = "filename";
	private static final String USER_FILE = "user-file";
	private static final String HEAD_FILES = "head-files";
	private static final String HEAD_FILE_NAME = "headName";
	private static final String HEAD_FILE = "head-file";
	private static final String CONTENT = "content";
	private static final String STANDARD_ANSWER = "Standard_answer";
	private static final String STUDENT_ANSWER = "Student_answer";

	// 把题目的xml文件解析成题目内容与题目内容头文件
	public QuestionTemp getPullParserQuestionList(String string) {

		QuestionTemp temp = new QuestionTemp();
		QuestionContent questionContent = null;
		List<QuestionHeaderFile> headerFileList = null;
		QuestionHeaderFile questionHeaderFile = null;

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(getStringToStream(string), "utf-8");
			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				String currentTagName = parser.getName();
				switch (eventType) {
				// 开始文档
				case XmlPullParser.START_DOCUMENT:
					questionContent = new QuestionContent();
					break;
				// 开始节点的标签
				case XmlPullParser.START_TAG:

					// 题目描述
					if (DOC.equals(currentTagName)) {
						questionContent.setQuestion_description(parser.nextText());
					}
					// 主文件
					if (MAIN_FILE.equals(currentTagName)) {
						String filename = parser.getAttributeValue(0);
						questionContent.setHeaderfile_name(filename);
						questionContent.setHeaderfile_content(parser.nextText());
					}
					// 标准答案
					if (ANSWER_FILE.equals(currentTagName)) {
						questionContent.setStandard_answer(parser.nextText());
					}
					// 学生答案
					if (USER_FILE.equals(currentTagName)) {
						questionContent.setStudent_answer(parser.nextText());
					}
					if (HEAD_FILES.equals(currentTagName)) {
						headerFileList = new ArrayList<>();
					}
					// 头文件
					if (HEAD_FILE.equals(currentTagName)) {
						questionHeaderFile = new QuestionHeaderFile();
						String filename = parser.getAttributeValue(0);
						questionHeaderFile.setHeader_file(filename);
						questionHeaderFile.setHeader_file_content(parser.nextText());
						headerFileList.add(questionHeaderFile);
					}

					break;
				// 结束节点的标签
				case XmlPullParser.END_TAG:

					if (HEAD_FILES.equals(currentTagName)) {
						temp.setHeaderFileList(headerFileList);
					}

					if (CONTENT.equals(currentTagName)) {
						temp.setQuestionContent(questionContent);
					}

					break;
				// 结束文档
				case XmlPullParser.END_DOCUMENT:

					break;
				default:
					break;

				}
				// 进行下一个事件类型的判断
				eventType = parser.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	// 把字符串变成流
	public InputStream getStringToStream(String string) {
		if (string != null && !string.trim().equals("")) {

			ByteArrayInputStream stream;
			try {
				stream = new ByteArrayInputStream(string.getBytes("utf-8"));
				return stream;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// 把题目内容与题目内容头文件创建成xml文件
	public String createQuestionXml(QuestionContent questionContent, List<QuestionHeaderFile> headerFileList) {

		XmlSerializer serializer = new KXmlSerializer();
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			serializer.setOutput(os, "utf-8");
			serializer.startDocument("UTF-8", true);

			serializer.startTag(null, PROBLEM);

			serializer.startTag(null, CONFIG);
			serializer.text(CONFIG);
			serializer.endTag(null, CONFIG);

			// 题目描述
			serializer.startTag(null, DOC);

			if (questionContent.getQuestion_description() != null) {
				serializer.text(questionContent.getQuestion_description());
			} else {
				serializer.text("");
			}

			serializer.endTag(null, DOC);

			serializer.startTag(null, CONTENT);

			// 主文件
			serializer.startTag(null, MAIN_FILE);
			if (questionContent.getHeaderfile_content() != null) {
				serializer.attribute(null, FILENAME, MAIN_FILE_NAME);// ltt
				serializer.text(questionContent.getHeaderfile_content());
			} else {
				serializer.attribute(null, FILENAME, MAIN_FILE_NAME);// ltt
				serializer.text("");
			}
			serializer.endTag(null, MAIN_FILE);

			// 标准答案
			serializer.startTag(null, ANSWER_FILE);
			serializer.attribute(null, FILENAME, STANDARD_ANSWER);
			if (questionContent.getStandard_answer() != null) {
				serializer.text(questionContent.getStandard_answer());
			} else {
				serializer.text("");
			}
			serializer.endTag(null, ANSWER_FILE);

			// 学生答案
			serializer.startTag(null, USER_FILE);
			serializer.attribute(null, FILENAME, STUDENT_ANSWER);
			if (questionContent.getStudent_answer() != null) {
				serializer.text(questionContent.getStudent_answer());
			} else {
				serializer.text("");
			}
			serializer.endTag(null, USER_FILE);

			// 头文件
			serializer.startTag(null, HEAD_FILES);
			serializer.text(HEAD_FILES);
			// 循环头文件
			if (headerFileList.size() > 0) {
				for (QuestionHeaderFile headerFile : headerFileList) {
					serializer.startTag(null, HEAD_FILE);
					if (!"".equals(headerFile.getHeader_file()) && headerFile.getHeader_file() != null) {
						serializer.attribute(null, FILENAME, headerFile.getHeader_file());
					} else {
						serializer.attribute(null, FILENAME, HEAD_FILE_NAME);
					}
					serializer.text(headerFile.getHeader_file_content());
					serializer.endTag(null, HEAD_FILE);
				}
			} else {

				serializer.startTag(null, HEAD_FILE);
				serializer.attribute(null, FILENAME, FILENAME); // ltt
				serializer.text("");
				serializer.endTag(null, HEAD_FILE);
			}

			serializer.endTag(null, HEAD_FILES);

			serializer.endTag(null, CONTENT);

			serializer.endTag(null, PROBLEM);

			serializer.endDocument();

			serializer.flush();

			return os.toString("utf-8");

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String getStudentAnswer(String string){
		String econtent = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(getStringToStream(string), "utf-8");
			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				String currentTagName = parser.getName();
				switch (eventType) {
					// 开始文档
					case XmlPullParser.START_DOCUMENT:
						econtent = null;
						break;
					case XmlPullParser.START_TAG:
						// 题目描述
						if ("econtent".equals(currentTagName)) {
							econtent = parser.nextText();
						}
						break;
					// 结束节点的标签
					case XmlPullParser.END_TAG:
						break;
					// 结束文档
					case XmlPullParser.END_DOCUMENT:
						break;
					default:
						break;
				}
				// 进行下一个事件类型的判断
				eventType = parser.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return econtent;
	}
}
