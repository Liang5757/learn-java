package cn.youyitech.anyview.system.utils;

import cn.youyitech.anyview.system.entity.LanguageEnum;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;

/**
 * 创建文件工具类
 * 
 * @author zzq
 */

@Component
public class FileUtils {

	/**
	 * 创建文件夹
	 * 
	 * @param newpath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @return 是否创建成功，成功则返回true
	 */
	public String createFileDir(String newpath, String fileName) {
		String filenameTemp = newpath + File.separator + fileName;// 文件路径+名称+文件类型
		File file = new File(filenameTemp);
		try {
			// 如果文件不存在，则创建新的文件
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filenameTemp;
	}

	/**
	 * 创建文件
	 * 
	 * @param newpath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @param filecontent
	 *            文件内容
	 * @param suffix
	 *            文件类型
	 * @return 是否创建成功，成功则返回true
	 */
	public boolean createFile(String newpath, String fileName, String filecontent, String suffix) {
		Boolean bool = false;
		// 文件路径+名称+文件类型
		String filenameTemp = newpath + File.separator + fileName + suffix;
		File file = new File(filenameTemp);
		try {
			// 如果文件不存在，则创建新的文件
			if (!file.exists()) {
				file.createNewFile();
				bool = true;
				// 创建文件成功后，写入内容到文件里
				writeFileContent(filenameTemp, filecontent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bool;
	}

	/**
	 * 向文件中写入内容
	 * 
	 * @param filepath
	 *            文件路径与名称
	 * @param newstr
	 *            写入的内容
	 * @return 是否写入成功，成功则返回true
	 * @throws IOException
	 */
	public boolean writeFileContent(String filepath, String newstr) throws IOException {
		Boolean bool = false;
		String filein = newstr + "\r\n";// 新写入的行，换行
		String temp = "";

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filepath);// 文件路径(包括文件名称)
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buffer = new StringBuffer();

			// 文件原有内容
			for (int i = 0; (temp = br.readLine()) != null; i++) {
				buffer.append(temp);
				// 行与行之间的分隔符 相当于“\n”
				buffer = buffer.append(System.getProperty("line.separator"));
			}
			buffer.append(filein);

			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buffer.toString().toCharArray());
			pw.flush();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 不要忘记关闭
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return bool;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		/**
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
		 **/
		return true;
	}
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
	public static void getFolder(File[] files, ArrayList children, LanguageEnum languageEnum) {

			if (files.length > 0) {
				for (File file1 : files) {
					if (!file1.isDirectory()) {
						System.out.println("不是directory");
						String fileName = file1.getName();
						String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
						switch (languageEnum.name()) {
							case "ALL":
								if ("txt".equals(prefix) || "c".equals(prefix) || "cpp"
										.equals(prefix)
										|| "java".equals(prefix)
										|| "TXT".equals(prefix) || "C".equals(prefix) || "CPP"
										.equals(prefix)
										|| "JAVA".equals(prefix)) {
									children.add(file1.getAbsolutePath());
								}
								break;
							case "C":
								if ("c".equals(prefix) || "C".equals(prefix)) {
									children.add(file1.getAbsolutePath());
								}
								break;
							case "CPP":
								if ("cpp".equals(prefix) || "CPP".equals(prefix)) {
									children.add(file1.getAbsolutePath());
								}
								break;
							case "JAVA":
								if ("java".equals(prefix) || "JAVA".equals(prefix)) {
									children.add(file1.getAbsolutePath());
								}
								break;
							default:
								break;
						}
					}
				}
			}
		}
	}

