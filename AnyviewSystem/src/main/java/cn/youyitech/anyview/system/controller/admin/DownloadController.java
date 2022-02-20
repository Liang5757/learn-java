package cn.youyitech.anyview.system.controller.admin;

import javax.servlet.http.HttpServletRequest;

import com.framework.loippi.utils.doc.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.framework.loippi.support.Pageable;

import java.io.File;
import java.io.IOException;

/**
 * Controller - 应用版本
 * 
 * @author zzq
 * @version 1.0
 */

@Controller("adminDownloadController")
@RequestMapping("/admin/download")
public class DownloadController extends GenericController {

	/**
	 * 下载客户端按钮显示
	 */
	@RequiresPermissions("admin:system:download")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Pageable pageable, ModelMap model) {
		return "/admin/download/list";
	}


	@RequestMapping("/download")
	public ResponseEntity<byte[]> downloadFile() throws IOException{
		String path = "D:/client";
		//String path = System.getProperty("user.dir") +"/cilent";
		File dir = new File(path);
		File file = dir.listFiles()[0];
		HttpHeaders headers = new HttpHeaders();
		String fileName=new String(file.getName().getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}

}
