package cn.youyitech.anyview.system.service;

import org.springframework.web.multipart.MultipartFile;

import cn.youyitech.anyview.system.support.FileInfo.FileType;

/**
 * Service - 文件
 *
 * @author Mounate Yan。
 * @version 1.0
 */
public interface FileService {

	/**
	 * 文件验证
	 *
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 文件验证是否通过
	 */
	boolean isValid(FileType fileType, MultipartFile multipartFile);

	/**
	 * 文件上传至本地
	 *
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 路径
	 */
	String uploadLocal(FileType fileType, MultipartFile multipartFile);

	public String getUrl(String path);

}