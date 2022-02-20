package cn.youyitech.anyview.system.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.loippi.support.Pageable;

import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.utils.ClientDownloadUtil;
import cn.youyitech.anyview.system.utils.FileUtils;

@Controller("adminClientController")
@RequestMapping("/admin/client")
public class ClientController extends GenericController{
	
	@Resource
	private RedisService redisService;

    @RequiresPermissions("admin:system:client")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Pageable pageable, ModelMap model){
        //String versionId=redisService.get("versionId");
        //model.addAttribute("versionId", versionId);
    	return "/admin/client/list";
    }

    @RequestMapping(value="/uploadClient",method=RequestMethod.POST)
    public String uploadFile(MultipartFile uploadFile, HttpServletRequest request,RedirectAttributes redirectAttributes) throws IOException{
    	String choose=request.getParameter("compatibility");
    	if(choose==null) {
    		choose="不兼容";
    	}
    	if(choose.equals("兼容")) {
        	if(!uploadFile.isEmpty()) {
        		//String versionId=ClientDownloadUtil.getVersionId();
        		//List<String> list=ClientDownloadUtil.getList();
        		List<String> list=new ArrayList<>();
        		String versionId;
        		if(redisService.get("versionList")!=null) {
        			String[] nums=redisService.get("versionList").split(",");
        			int id=Integer.valueOf(nums[nums.length-1]);
        			versionId=String.valueOf(id+1);        			
        			list=new ArrayList<>(Arrays.asList(nums));
        		}else {
        			versionId=String.valueOf(1);
        		}
        		System.out.println(list+"--------"+versionId);
        		list.add(versionId);
        		System.out.println("版本Id是:"+list);
        		String versionList=String.join(",", list);
        		redisService.save("versionList",versionList );
    	    	String path=request.getServletContext().getRealPath("/")+"client" ;	    	
    	    	System.out.println("路径是："+path);
    	    	String filename = uploadFile.getOriginalFilename();
    	        FileUtils fileUtils = new FileUtils();
    	        File dir = new File(path);
    	        dir.mkdir();
    	        if (filename.endsWith("zip")||filename.endsWith("rar")){
//    	    		System.out.println("准备上传");
    	            File file = new File(path + File.separator + filename);
    	            if(file.exists()) {
    	            	fileUtils.deleteDir(file);
    	            }
    	            file.mkdir();
    	            uploadFile.transferTo(file);
    	        }else{
    	            addFlashMessage(redirectAttributes,ERROR_MESSAGE);
    	        }
    	        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
    	    }else {

    	    	addFlashMessage(redirectAttributes,ERROR_MESSAGE);
    	    }
    	}else {
        	if(!uploadFile.isEmpty()) {
        		//String versionId=ClientDownloadUtil.getVersionId();
        		//List<String> list=ClientDownloadUtil.getList();
        		List<String> list=new ArrayList<>();
        		String versionId;
        		if(redisService.get("versionList")!=null) {
        			String[] nums=redisService.get("versionList").split(",");
        			int id=Integer.valueOf(nums[nums.length-1]);
        			versionId=String.valueOf(id+1);
        		}else {
        			versionId=String.valueOf(1);
        		}
        		list.clear();
        		list.add(versionId);
        		System.out.println("版本Id是:"+list);
        		String versionList=String.join(",", list);
        		redisService.save("versionList",versionList );
    	    	String path=request.getServletContext().getRealPath("/")+"client" ;	    	
    	    	System.out.println("路径是："+path);
    	    	String filename = uploadFile.getOriginalFilename();
    	        FileUtils fileUtils = new FileUtils();
    	        File dir = new File(path);
    	        if(dir.exists()) {
    	        	fileUtils.deleteDir(dir);
    	        }
    	        dir.mkdir();
    	        if (filename.endsWith("zip")||filename.endsWith("rar")){
//    	    		System.out.println("准备上传");
    	            File file = new File(path + File.separator + filename);
    	            if(file.exists()) {
    	            	fileUtils.deleteDir(file);
    	            }
    	            file.mkdir();
    	            uploadFile.transferTo(file);
    	        }else{
    	            addFlashMessage(redirectAttributes,ERROR_MESSAGE);
    	        }
    	        addFlashMessage(redirectAttributes,SUCCESS_MESSAGE);
    	    }else {

    	    	addFlashMessage(redirectAttributes,ERROR_MESSAGE);
    	    }
    	}
    	

    	return "redirect:/admin/client/list.jhtml";
	}
}
