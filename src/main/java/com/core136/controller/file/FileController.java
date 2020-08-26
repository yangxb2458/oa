package com.core136.controller.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.core136.bean.account.Account;
import com.core136.bean.bpm.BpmSealSign;
import com.core136.bean.file.Photo;
import com.core136.bean.file.Attach;
import com.core136.bean.file.NetDisk;
import com.core136.service.account.AccountService;
import com.core136.service.bpm.BpmSealSignService;
import com.core136.service.file.PhotoService;
import com.core136.service.file.AttachService;
import com.core136.service.file.NetDiskService;
import com.core136.unit.fileutils.BarCodeUtils;
import com.core136.unit.fileutils.DownUtils;
import com.core136.unit.fileutils.UploadException;
import com.core136.unit.fileutils.UploadUtils;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;

import org.core136.common.enums.FileExt;
import org.core136.common.utils.Md5CaculateUtil;
import org.core136.common.utils.SysTools;
/**
 * 
 * @ClassName:  FileController   
 * @DescriptionTODO 文件上传下载Controller
 * @author: 稠云信息
 * @date:   2018年12月10日 下午1:28:36   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/sys/file")
@MultipartConfig
public class FileController {
@Autowired
private UploadUtils uploadUtils;
@Autowired
private DownUtils downUtils;
@Autowired
private AttachService attachService;
@Autowired
private AccountService accountService;
@Autowired
private PhotoService photoService;
@Autowired
private BpmSealSignService bpmSealSignService;
@Autowired
private NetDiskService netDiskService;

@Value("${app.attachpath}")  
private  String attachpath;
@Value("${app.notallow}")  
private  String notallow;
@Value("${app.poi.tmppath}")	
private String tmppath;
@Value("${app.bpm.xhtmlpath}")	
private String xhtmlpath;


/**
 * 
 * @Title: sysRegist   
 * @Description: TODO 系统注册
 * @param request
 * @return
 * @throws UploadException
 * RetDataBean    
 * @throws
 */
@RequestMapping("/sysRegist")
public ModelAndView sysRegist(HttpServletRequest request) throws UploadException {
    try {
        uploadUtils.sysRegist(request,attachpath);
        return new ModelAndView("/");
    } catch (IOException e) {
    	 return null;
    }
}



/**
 * 
 * @Title: uploadBackgroundImg   
 * @Description: TODO 系统背影图上传
 * @param: request
 * @param: @return
 * @param: @throws UploadException      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping("/uploadBackgroundImg")
public RetDataBean uploadBackgroundImg(HttpServletRequest request) throws UploadException {
    String targetpath = attachpath+"/background/";
    try {
        return RetDataTools.Ok("系统登陆背景图上传成功!", uploadUtils.uploadImg(request,targetpath));
    } catch (IOException e) {
        return RetDataTools.Error(e.getMessage());
    }
}

/**
 * 
 * @Title: uploadimglogo   
 * @Description: TODO 上传系统LOGO
 * @param request
 * @return
 * @throws UploadException
 * RetDataBean    
 * @throws
 */
@RequestMapping("/uploadimglogo")
public RetDataBean uploadimglogo(HttpServletRequest request) throws UploadException {
	 String targetpath = attachpath+"/background/";
    try {
        return RetDataTools.Ok("图片上传成功!", uploadUtils.uploadImg(request,targetpath));
    } catch (IOException e) {
        return RetDataTools.Error(e.getMessage());
    }
}

/**
 * 
 * @Title: uploadimg   
 * @Description: TODO 图片上传
 * @param request
 * @param module
 * @return
 * @throws UploadException
 * RetDataBean    
 * @throws
 */
@RequestMapping("/uploadimg")
public RetDataBean uploadimg(HttpServletRequest request,String module) throws UploadException {
    String targetpath =  SysTools.greateAttachDir(attachpath, module);
    try {
        return RetDataTools.Ok("图片上传成功!", uploadUtils.uploadImg(request,targetpath));
    } catch (IOException e) {
        return RetDataTools.Error(e.getMessage());
    }
}
/**
 * 
 * @Title: getStaticImg   
 * @Description: TODO 获取静态图片
 * @param response
 * @param request
 * @param module
 * @param fileName
 * void    
 * @throws
 */
@RequestMapping("/getStaticImg")
public void getStaticImg(HttpServletResponse response,HttpServletRequest request,String module,String fileName)
{
	try
	{
		String targetpath =  SysTools.greateAttachDir(attachpath, module)+File.separator+fileName;
		downUtils.download(targetpath, response);
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}

/**
 * 
 * @Title: getBackgroundImg   
 * @Description: TODO 获取登陆背景图片
 * @param: response
 * @param: request
 * @param: fileName      
 * @return: void      
 * @throws
 */
@RequestMapping("/getBackgroundImg")
public void getBackgroundImg(HttpServletResponse response,HttpServletRequest request,String fileName)
{
	try
	{
		String path = attachpath+"/background/"+fileName;
		downUtils.download(path, response);
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}

/**
 * 
 * @Title: getBarcodeOne   
 * @Description: TODO 获取BPM条形码
 * @param: request
 * @param: response
 * @param: codeStr      
 * @return: void      
 * @throws
 */
@RequestMapping("/bpm/getBarcodeOne")
public void  getBarcodeOne(HttpServletRequest request,HttpServletResponse response,String codeStr)
{
	try
	{
		BarCodeUtils.getBarCodeOne(response, codeStr);
	}catch (Exception e) {
		e.printStackTrace();
	}
}
/**
 * 
 * @Title: getQrcodeTwo   
 * @Description: TODO 获取BPM二维码
 * @param: request
 * @param: response
 * @param: codeStr      
 * @return: void      
 * @throws
 */
@RequestMapping("/bpm/getQrcodeTwo")
public void  getQrcodeTwo(HttpServletRequest request,HttpServletResponse response,String codeStr)
{
	try
	{
		BarCodeUtils.getQrcodeTwo(response, codeStr);
	}catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * 
 * @Title: getBpmSealSign   
 * @Description:  获取BPM签名图片
 * @param: response
 * @param: request
 * @param: bpmSealSign      
 * @return: void      
 * @throws
 */
@RequestMapping("/getBpmSealSign")
public void getBpmSealSign(HttpServletResponse response,HttpServletRequest request,BpmSealSign bpmSealSign)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		bpmSealSign.setOrgId(account.getOrgId());
		bpmSealSign = bpmSealSignService.selectOneBpmSealSign(bpmSealSign);
		downUtils.getBpmSealSign(bpmSealSign,response);
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}


/**
 UploadException 
 * @Title upload   
 * @Description TODO 文件上传
 * @param file
 * @param type
 * @return RetDataBean

 */
	@RequestMapping("/upload")
    public RetDataBean upload(HttpServletRequest request,String module) throws UploadException {
        String targetpath = SysTools.greateAttachDir(attachpath, module);
        try {
            return RetDataTools.Ok("文件上传成功!", uploadUtils.upload(request,notallow, targetpath));
        } catch (Exception e) {
            return RetDataTools.Error(e.getMessage());
        }
    }
	
	
	/**
	 * 
	 * @Title: bpmTemplateFileUpLoad   
	 * @Description: TODO BPM XHTML的文件上传
	 * @param: request
	 * @param: @return
	 * @param: @throws UploadException      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping("/bpmTemplateFileUpLoad")
    public RetDataBean bpmTemplateFileUpLoad(HttpServletRequest request) throws UploadException {
        try {
            return RetDataTools.Ok("文件上传成功!", uploadUtils.uploadXhtml(request,xhtmlpath));
        } catch (Exception e) {
            return RetDataTools.Error(e.getMessage());
        }
    }	
	
	
	/**
	 * 
	 * @Title: uploadHeadImg   
	 * @Description: TODO 头像上传
	 * @param request
	 * @return
 UploadException      
	 * @return: RetDataBean      

	 */
	@RequestMapping("/uploadHeadImg")
    public RetDataBean uploadHeadImg(HttpServletRequest request) throws UploadException {
        String targetpath = attachpath+"/headimg/";
        try {
            return RetDataTools.Ok("头像上传成功!", uploadUtils.uploadHeadImg(request,targetpath));
        } catch (IOException e) {
            return RetDataTools.Error(e.getMessage());
        }
    }
	
	/**
	 * 
	 * @Title getFileDown   
	 * @Description TODO 文件下载
	 * @param response
	 * @param request      
	 * @return void      

	 */
	@RequestMapping("/getFileDown")
	public void getFileDown(HttpServletResponse response,HttpServletRequest request)
	{
		try
		{
			String attachId = request.getParameter("attachId");
			Account account=accountService.getRedisAccount(request);
			Attach attach = new Attach();
			attach.setAttachId(attachId);
			attach.setOrgId(account.getOrgId());
			attach=attachService.selectOne(attach);
			downUtils.download(attach.getPath(), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @Title getHeadImg   
	 * @Description TODO 获取账户头像
	 * @param request      
	 * @return void      

	 */
	@RequestMapping("/getHeadImg")
	public void getHeadImg(HttpServletResponse response,HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			String headImg = account.getHeadImg();
			String path = attachpath+"/headimg/"+headImg;
			downUtils.download(path, response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title getHeadImg   
	 * @Description TODO 获取他人头像
	 * @param response
	 * @param request
	 * @param headImg      
	 * void
	 */
	@RequestMapping("/getOtherHeadImg")
	public void getOtherHeadImg(HttpServletResponse response,HttpServletRequest request,String headImg)
	{
		try
		{
			String path = attachpath+"/headimg/"+headImg;
			downUtils.download(path, response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: getImFile 
	* @Description: TODO 获取Im中的文件
	* @param @param response
	* @param @param request
	* @param @param filename
	* @param @param module 设定文件 
	* @return void 返回类型
	 */
	@RequestMapping("/getImFile")
	public void getImFile(HttpServletResponse response,HttpServletRequest request,String attachId,String module)
	{
		try
		{
			Attach attach = new Attach();
			attach.setAttachId(attachId);
			attach= attachService.getAttachById(attach);
			downUtils.Imdownload(attach.getPath(), response,2);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @Title getImage   
	 * @Description TODO 获取图片
	 * @param response
	 * @param request
	 * @param attach      
	 * void
	 */
	@RequestMapping("/getImage")
	public void getImage(HttpServletResponse response,HttpServletRequest request,Attach attach,NetDisk netDisk,String path)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			String imgepath=SysTools.getAppDir()+"/static/gobal/img/error.jpg";
			if(StringUtils.isBlank(attach.getAttachId()))
			{
				netDisk.setOrgId(account.getOrgId());
				netDisk = netDiskService.selectOneNetDisk(netDisk);
				imgepath = netDisk.getRootPath()+path;
			}else
			{
				attach.setOrgId(attach.getOrgId());
				attach = attachService.selectOne(attach);
				imgepath = attach.getPath();
				if(FileExt.getFileextmap().get("img").indexOf(","+attach.getExtName()+",")>-1)
	            {
					imgepath = attach.getPath();
	            }
			}
			downUtils.getImg(imgepath, response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Title getAttachList   
	 * @Description TODO 按attachIds获取附件列表 
	 * @param response
	 * @param request      
	 * @return void      

	 */
	@RequestMapping("/getAttachList")
	public RetDataBean getAttachList(HttpServletResponse response,HttpServletRequest request)
	{
		try
		{
			String attachIds = request.getParameter("attachIds");
			Account account=accountService.getRedisAccount(request);
			List<String> attachList = new ArrayList<String>();
			if(StringUtils.isNotEmpty(attachIds))
			{
				String [] attachArray = attachIds.split(",");
				attachList = Arrays.asList(attachArray);
			}else
			{
				RetDataTools.Fail("参数格式有问题!");
			}
			if(attachList.size()>0)
			{
				return RetDataTools.Ok("请求数据成功!",attachService.getAttachList(attachList, account.getOrgId()));
			}else
			{
				return RetDataTools.NotOk("参数格式有问题!");
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}

	
	
	
	/**
	 * 
	 * @Title delAttach   
	 * @Description 附件逻辑删除 
	 * @param request
	 * @param attach
	 * @return RetDataBean      

	 */
	@RequestMapping("/delAttch")
	public RetDataBean delAttach(HttpServletRequest request,Attach attach)
	{
		try
		{
		Account account=accountService.getRedisAccount(request);
		attach.setOrgId(account.getOrgId());
		if(!account.getOpFlag().equals("1"))
		{
			attach.setCreateAccount(account.getAccountId());
			if(attachService.deleteAttach(attach)>0)
			{
				return RetDataTools.Ok("删除附件成功!",1);
			}else
			{
				return RetDataTools.NotOk("删除附件失败!"); 	
			}
		}else
		{
			if(attachService.deleteAttach(attach)>0)
			{
				return RetDataTools.Ok("删除附件成功!");	
			}else
			{
				return RetDataTools.NotOk("删除附件失败!"); 
			}
		}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @Title readFile   
	 * @Description TODO 在线查看文件
	 * @param response
	 * @param request      
	 * @return void      

	 */
	@RequestMapping(value="/readFile",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	public void readFile(HttpServletResponse response,HttpServletRequest request,Attach attach,NetDisk netDisk,String path)
	{
		try
		{
			String attachPath="";
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isNotBlank(attach.getAttachId()))
			{
				attach.setOrgId(account.getOrgId());
				attach=attachService.selectOne(attach);
				attachPath = attach.getPath();
			}else
			{
				netDisk.setOrgId(account.getOrgId());
				netDisk = netDiskService.selectOneNetDisk(netDisk);
				attachPath = netDisk.getRootPath()+path;
			}
			downUtils.readFile(attachPath, response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * IM文件上传
	* @Title: imUpload 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param request
	* @param @param module
	* @param @return
	* @param @throws UploadException 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping("/imUpload")
    public RetDataBean imUpload(HttpServletRequest request,String module,String accountId,String passWord) throws UploadException {
        String targetpath = SysTools.greateAttachDir(attachpath, module);
        try {
        	Account account = accountService.getLoginAccount(accountId, passWord);
            return RetDataTools.Ok("文件上传成功!", uploadUtils.ImUpload(request,account,notallow, targetpath));
        } catch (IOException e) {
            return RetDataTools.Error(e.getMessage());
        }catch (UploadException e) {
            return RetDataTools.Error(e.getMessage());
        }
    }
	
	/**
	 * 
	* @Title: imAudioUpload 
	* @Description: TODO 接收语言文件
	* @param @param request
	* @param @param module
	* @param @param accountId
	* @param @param passWord
	* @param @return
	* @param @throws UploadException 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping("/imAudioUpload")
    public RetDataBean imAudioUpload(HttpServletRequest request,String module,String accountId,String passWord) throws UploadException {
        String targetpath = SysTools.greateAttachDir(attachpath, module);
        try {
        	Account account = accountService.getLoginAccount(accountId, Md5CaculateUtil.MD5(accountId+passWord));
            return RetDataTools.Ok("文件上传成功!", uploadUtils.ImAudioUpload(request,account,notallow, targetpath));
        } catch (IOException e) {
            return RetDataTools.Error(e.getMessage());
        }catch (UploadException e) {
            return RetDataTools.Error(e.getMessage());
        }
    }
	/**
	 * 
	* @Title: getImAudioFile 
	* @Description: TODO 从服务器上获取语音文件
	* @param @param response
	* @param @param request
	* @param @param fileName
	* @param @param module 设定文件 
	* @return void 返回类型
	 */
	@RequestMapping("/getImAudioFile")
	public void getImAudioFile(HttpServletResponse response,HttpServletRequest request,String fileName,String module)
	{
		try
		{
			String attachId = fileName.substring(0, fileName.lastIndexOf("."));
			Attach attach = new Attach();
			attach.setAttachId(attachId);
			attach= attachService.getAttachById(attach);
			downUtils.download(attach.getPath(), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @Title: getImgByPath   
	 * @Description: TODO 按服务器目录获取网络图片
	 * @param: response
	 * @param: request
	 * @param: imgName      
	 * @return: void      
	 * @throws
	 */
	@RequestMapping("/getImgByPath")
	public void getImgByPath(HttpServletResponse response,HttpServletRequest request,String imgName,String filetage)
	{
		try
		{
			String path = tmppath+File.separator+filetage+File.separator+ "image"+File.separator+imgName;
			downUtils.download(path, response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @Title: getPhotoImg   
	 * @Description: TODO 获取相册下的图片
	 * @param: response
	 * @param: request
	 * @param: fileName
	 * @param: photo      
	 * @return: void      
	 * @throws
	 */
	@RequestMapping("/getPhotoImg")
	public void getPhotoImg(HttpServletResponse response,HttpServletRequest request,String fileName,Photo photo)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			photo.setOrgId(account.getOrgId());
			photo = photoService.selectOnePhoto(photo);
			String path = photo.getRootPath()+File.separator+fileName;
			downUtils.getImg(path, response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
