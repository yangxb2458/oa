package com.core136.controller.file;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.file.NetDisk;
import com.core136.bean.file.PersonalFile;
import com.core136.bean.file.PersonalFileFolder;
import com.core136.bean.file.Photo;
import com.core136.bean.file.PublicFile;
import com.core136.bean.file.PublicFileFolder;
import com.core136.bean.file.Attach;
import com.core136.service.account.AccountService;
import com.core136.service.account.UserInfoService;
import com.core136.service.file.NetDiskService;
import com.core136.service.file.PersonalFileFolderService;
import com.core136.service.file.PersonalFileService;
import com.core136.service.file.PhotoService;
import com.core136.service.file.PublicFileFolderService;
import com.core136.service.file.PublicFileService;
import com.core136.service.file.AttachService;
import com.core136.unit.fileutils.UploadException;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  RoutSetFileController   
 * @Description:TODO 系统文件系统操作CONTROLLER
 * @author: 稠云信息 
 * @date:   2019年5月27日 下午4:23:56   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/set/fileset")
public class RoutSetFileController {
@Autowired
private NetDiskService netDiskService;
@Autowired
private PersonalFileFolderService personalFileFolderService;
@Autowired
private PersonalFileService personalFileService;
@Autowired
private UserInfoService userInfoService;
@Autowired
private PublicFileFolderService publicFileFolderService;
@Autowired
private PublicFileService publicFileService;
@Autowired
private AttachService attachService;
@Autowired
private PhotoService phoroService;
@Autowired
private AccountService accountService;

@Value("${app.notallow}")  
private  String notallow;

/**
 * 
 * @Title: insertPhoto   
 * @Description: TODO 创建相册
 * @param: request
 * @param: photo
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertPhoto",method=RequestMethod.POST)
public RetDataBean insertPhoto(HttpServletRequest request,Photo photo)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		photo.setPhotoId(SysTools.getGUID());
		photo.setCreateUser(account.getAccountId());
		photo.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		photo.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建相册成功!", phoroService.insertPhoto(photo));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updatePhoto   
 * @Description: TODO 更新相册 
 * @param: request
 * @param: photo
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updatePhoto",method=RequestMethod.POST)
public RetDataBean updatePhoto(HttpServletRequest request,Photo photo)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("您不是系统管理,请与管理员联系!");
		}
		if(StringUtils.isBlank(photo.getPhotoId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Example example = new Example(Photo.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("photoId",photo.getPhotoId());
		return RetDataTools.Ok("相册更新成功!", phoroService.updatePhoto(example, photo));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


@RequestMapping(value="/delPhoto",method=RequestMethod.POST)
public RetDataBean delPhoto(HttpServletRequest request,Photo photo)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("您不是系统管理,请与管理员联系!");
		}
		if(StringUtils.isBlank(photo.getPhotoId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		photo.setOrgId(account.getOrgId());
		return RetDataTools.Ok("相册删除成功!", phoroService.deletePhoto(photo));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title insertPersonalFileFolder   
 * @Description TODO 创建个人文件柜 
 * @param request
 * @param personalFileFolder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertPersonalFileFolder",method=RequestMethod.POST)
public RetDataBean insertPersonalFileFolder(HttpServletRequest request,PersonalFileFolder personalFileFolder)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(personalFileFolder.getFolderLeave()))
		{
			personalFileFolder.setFolderLeave("0");
		}
		personalFileFolder.setFolderId(SysTools.getGUID());
		personalFileFolder.setCreateUser(account.getAccountId());
		personalFileFolder.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		personalFileFolder.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建目录成功!", personalFileFolderService.insert(personalFileFolder));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
* @Title: updatePersonalFileFolder 
* @Description: TODO 更新个人文件柜文件的名称
* @param @param request
* @param @param type
* @param @param fileId
* @param @param fileName
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/updatePersonalFileFolder",method=RequestMethod.POST)
public RetDataBean updatePersonalFileFolder(HttpServletRequest request,String type,String fileId,String fileName)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isNotBlank(type)&&StringUtils.isNotBlank(fileId)&&StringUtils.isNotBlank(fileName))
		{
			if(type.equals("file"))
			{
				Example example = new Example(PersonalFile.class);
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("fileId",fileId);
				PersonalFile personalFile = new PersonalFile();
				personalFile.setOrgId(account.getOrgId());
				personalFile.setFileId(fileId);
				personalFile=personalFileService.selectOnePersonalFile(personalFile);
				String extName = personalFile.getFileName().split("\\.")[1];
				personalFile.setFileName(fileName+"."+extName);
				return RetDataTools.Ok("重命名成功!",personalFileService.updatePersonFile(personalFile, example));
			}else
			{
				Example example = new Example(PersonalFileFolder.class);
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("folderId",fileId);
				PersonalFileFolder personalFileFolder = new PersonalFileFolder();
				personalFileFolder.setFolderName(fileName);
				return RetDataTools.Ok("重命名成功!",personalFileFolderService.updatePersonFileFolder(personalFileFolder, example));
			}
		}else
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title insertPersonalFile   
 * @Description TODO 添加文件  
 * @param request
 * @param personalFile
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertPersonalFile",method=RequestMethod.POST)
public RetDataBean insertPersonalFile(HttpServletRequest request,PersonalFile personalFile)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(personalFile.getAttach()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Attach attach = new Attach();
		attach.setOrgId(account.getOrgId());
		attach.setAttachId(personalFile.getAttach());
		attach = attachService.selectOne(attach);
		personalFile.setFileId(SysTools.getGUID());
		personalFile.setCreateUser(account.getAccountId());
		personalFile.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		personalFile.setOrgId(account.getOrgId());
		personalFile.setFileName(attach.getOldName());
		return RetDataTools.Ok("创建文件成功!", personalFileService.insertPersonalFile(personalFile));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
* @Title: shearPersonalFile 
* @Description: TODO 剪切个人文件柜
* @param @param request
* @param @param fileId
* @param @param currentLocation
* @param @param fType
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/shearPersonalFile",method=RequestMethod.POST)
public RetDataBean shearPersonalFile(HttpServletRequest request,String fileId,String currentLocation,String fType)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isNotBlank(fileId)&&StringUtils.isNotBlank(currentLocation)&&StringUtils.isNotBlank(fType))
		{
			if(fType.equals("file"))
			{
				PersonalFile personalFile = new PersonalFile();
				personalFile.setFileId(fileId);
				personalFile.setOrgId(account.getOrgId());
				personalFile=personalFileService.selectOnePersonalFile(personalFile);
				return RetDataTools.Ok("剪切文件成功!",personalFileService.shearPersonalFile(personalFile, currentLocation));
			}else
			{
				PersonalFileFolder personalFileFolder = new PersonalFileFolder();
				personalFileFolder.setFolderId(fileId);
				personalFileFolder.setOrgId(account.getOrgId());
				personalFileFolder=personalFileFolderService.selectOnePersonalFileFolder(personalFileFolder);
				return RetDataTools.Ok("剪切文件成功!",personalFileFolderService.shearPersonalFileFolder(personalFileFolder, currentLocation));
			}
		}else
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
* @Title: delPersonalFolder 
* @Description: TODO删除个人文件柜中的文件夹
* @param @param request
* @param @param personalFileFolder
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/delPersonalFolder",method=RequestMethod.POST)
public RetDataBean delPersonalFolder(HttpServletRequest request,PersonalFileFolder personalFileFolder)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		personalFileFolder.setOrgId(account.getOrgId());
		if(personalFileService.isExistChild(personalFileFolder.getFolderId(), account.getAccountId(), account.getOrgId())>0)
		{
			return RetDataTools.NotOk("目录中存在文件,请先删除目录中的文件!");
		}
		if(StringUtils.isBlank(personalFileFolder.getFolderId()))
		{
		return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		return RetDataTools.Ok("删除文件夹成功!", personalFileFolderService.delete(personalFileFolder));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
* @Title: delPersonalFile 
* @Description: TODO 删除个人文件柜文件
* @param @param request
* @param @param personalFile
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/delPersonalFile",method=RequestMethod.POST)
public RetDataBean delPersonalFile(HttpServletRequest request,PersonalFile personalFile)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		personalFile.setOrgId(account.getOrgId());
		if(StringUtils.isBlank(personalFile.getFileId()))
		{
		return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		return RetDataTools.Ok("删除文件夹成功!", personalFileService.deletePersonalFile(personalFile));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
* @Title: pastePersonalFile 
* @Description: TODO 粘贴个人文件
* @param @param request
* @param @param currentLocation
* @param @param fileId
* @param @param type
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/pastePersonalFile",method=RequestMethod.POST)
public RetDataBean pastePersonalFile(HttpServletRequest request,String currentLocation,String fileId,String type)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isNotBlank(currentLocation)&&StringUtils.isNotBlank(fileId)&&StringUtils.isNotBlank(type))
		{
			if(type.equals("file"))
			{
				PersonalFile personalFile = new PersonalFile();
				personalFile.setOrgId(account.getOrgId());
				personalFile.setFileId(fileId);
				return RetDataTools.Ok("粘贴文件成功!", personalFileService.pastePersonalFile(personalFile,currentLocation));
			}else
			{
				PersonalFileFolder personFileFolder = new PersonalFileFolder();
				personFileFolder.setOrgId(account.getOrgId());
				personFileFolder.setFolderId(fileId);
				return RetDataTools.Ok("粘贴文件夹成功!", personalFileFolderService.pastePersonalFileFolder(personFileFolder,currentLocation));
			}
		}else
		{
			return RetDataTools.NotOk("请求的参数有问题,请检查!");
		}
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title createNetDisk   
 * @Description TODO 创建网盘  
 * @param request
 * @param netDisk
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/createNetDisk",method=RequestMethod.POST)
public RetDataBean createNetDisk(HttpServletRequest request,NetDisk netDisk)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		netDisk.setNetDiskId(SysTools.getGUID());
		netDisk.setDiskCreateUser(account.getAccountId());
		netDisk.setDiskCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		netDisk.setOrgId(account.getOrgId());
		return RetDataTools.Ok("网盘创建成功!", netDiskService.insertNetDisk(netDisk));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
	
	/**
	 * 
	* @Title: createNetDiskFile 
	* @Description: TODO 网络硬盘创建文件夹
	* @param @param request
	* @param @param folderName
	* @param @param netDisk
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/createNetDiskFolder",method=RequestMethod.POST)
	public RetDataBean createNetDiskFolder(HttpServletRequest request,String sourcePath,String folderName,NetDisk netDisk)
	{
		try
		{
			if(StringUtils.isBlank(folderName)||StringUtils.isBlank(sourcePath))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			netDisk.setOrgId(account.getOrgId());
			UserInfo userInfo = userInfoService.getUserInfoByAccountId(account.getAccountId(), account.getOrgId());
			if(account.getOpFlag().equals("1"))
			{
				netDisk = netDiskService.selectOneNetDisk(netDisk);
				return RetDataTools.Ok("创建文件夹成功!", netDiskService.createNetDiskFolder(sourcePath,folderName,netDisk));
			}else
			{
				netDisk = netDiskService.selectOneNetDisk(netDisk);
				int flag = netDiskService.getCreateFilePriv(account.getOrgId(), netDisk.getNetDiskId(),account.getAccountId(), userInfo.getDeptId(), userInfo.getLeadLeave());
				if(flag>0)
				{
					return RetDataTools.Ok("创建文件夹成功!", netDiskService.createNetDiskFolder(sourcePath,folderName,netDisk));
				}else
				{
					return RetDataTools.NotOk("您无权限创建,请与系统管理员联系!");
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
}
	/**
	 * 
	* @Title: createNetDiskFile 
	* @Description: TODO 在网络硬盘中创建文件
	* @param @param request
	* @param @param sourcePath
	* @param @param netDisk
	* @param @return
 UploadException 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping("/createNetDiskFile")
    public RetDataBean createNetDiskFile(HttpServletRequest request,String sourcePath, NetDisk netDisk) throws UploadException {
		Account account=accountService.getRedisAccount(request);
		netDisk.setOrgId(account.getOrgId());
		netDisk = netDiskService.selectOneNetDisk(netDisk);
        Collection<Part> parts = null;
        try {
            parts = request.getParts();
        } catch (IOException e) {
            RetDataTools.Error(e.getMessage());
        } catch (ServletException e) {
        	RetDataTools.Error(e.getMessage());
        }
        try {
        	int code = netDiskService.createNetDiskFile(parts,sourcePath,notallow,netDisk);
        	String msg="";
        	if(code==1)
        	{
        		msg="创建文件成功!";
        	}else if(code==2)
        	{
        		msg="文件已存在!";
        	}else if(code==3)
        	{
        		msg="文件不能为空!";
        	}else if(code==4)
        	{
        		msg="文件格式不正确!";
        	}
        	return RetDataTools.Ok(msg);
        } catch (IOException e) {
            return RetDataTools.Error(e.getMessage());
        }
    }
	/**
	 * 
	* @Title: delNetDiskfile 
	* @Description: TODO 删除文件
	* @param @param request
	* @param @param sourcePath
	* @param @param netDisk
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/delNetDiskfile",method=RequestMethod.POST)
	public RetDataBean delNetDiskfile(HttpServletRequest request,String sourcePath,NetDisk netDisk)
	{
		try
		{
			if(StringUtils.isBlank(sourcePath))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			netDisk.setOrgId(account.getOrgId());
			UserInfo userInfo = userInfoService.getUserInfoByAccountId(account.getAccountId(), account.getOrgId());
			if(account.getOpFlag().equals("1"))
			{
				netDisk = netDiskService.selectOneNetDisk(netDisk);
				return RetDataTools.Ok("删除文件成功!", netDiskService.delNetDiskFile(sourcePath, netDisk));
			}else
			{
				netDisk = netDiskService.selectOneNetDisk(netDisk);
				int flag = netDiskService.getCreateFilePriv(account.getOrgId(), netDisk.getNetDiskId(),account.getAccountId(), userInfo.getDeptId(), userInfo.getLeadLeave());
				if(flag>0)
				{
					return RetDataTools.Ok("删除文件成功!", netDiskService.delNetDiskFile(sourcePath, netDisk));
				}else
				{
					return RetDataTools.NotOk("您无权限创建,请与系统管理员联系!");
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
}
/**
 * 
 * @Title deleteNetDisk   
 * @Description TODO 删除网盘  
 * @param request
 * @param netDisk
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/deleteNetDisk",method=RequestMethod.POST)
public RetDataBean deleteNetDisk(HttpServletRequest request,NetDisk netDisk)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(netDisk.getNetDiskId()))
		{
			return RetDataTools.NotOk("请求的参数有问题,请检查!");
		}
		netDisk.setOrgId(account.getOrgId());
		return RetDataTools.Ok("网盘删除成功!", netDiskService.deleteNetDisk(netDisk));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
* @Title: renameNetDiskFileName 
* @Description: TODO 文件重合名
* @param @param request
* @param @param netDisk
* @param @param sourcePath
* @param @param newName
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/renameNetDiskFileName",method=RequestMethod.POST)
public RetDataBean renameNetDiskFileName(HttpServletRequest request,NetDisk netDisk,String sourcePath,String newFileName)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(netDisk.getNetDiskId()))
		{
			return RetDataTools.NotOk("请求的参数有问题,请检查!");
		}
		netDisk.setOrgId(account.getOrgId());
		netDisk = netDiskService.selectOneNetDisk(netDisk);
		if(netDiskService.renameNetDiskFileName(sourcePath,netDisk,newFileName))
		{
			return RetDataTools.Ok("文件重命名成功!");
		}else
		{
			return RetDataTools.NotOk("文件重命名失败");
		}
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title updateNetDisk   
 * @Description TODO 更新网盘
 * @param request
 * @param netDisk
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/updateNetDisk",method=RequestMethod.POST)
public RetDataBean updateNetDisk(HttpServletRequest request,NetDisk netDisk)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(netDisk.getNetDiskId()))
		{
			return RetDataTools.NotOk("请求的参数有问题,请检查!");
		}
		netDisk.setOrgId(account.getOrgId());
		Example example = new Example(NetDisk.class);
		example.createCriteria().andEqualTo("orgId",netDisk.getOrgId()).andEqualTo("netDiskId",netDisk.getNetDiskId());
		return RetDataTools.Ok("网盘更新成功!", netDiskService.updateNetDisk(netDisk,example));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
* @Title: copyFile 
* @Description: TODO 文件复制
* @param @param request
* @param @param sourcePath
* @param @param targetPath
* @param @param sourceNetDiskId
* @param @param targetNetDiskId
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/copyFile",method=RequestMethod.POST)
public RetDataBean copyFile(HttpServletRequest request,String sourcePath,String targetPath,String sourceNetDiskId,String targetNetDiskId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(sourceNetDiskId)||StringUtils.isBlank(targetNetDiskId))
		{
			return RetDataTools.NotOk("请求的参数有问题,请检查!");
		}else
		{
			NetDisk sourceNetDisk = new NetDisk();
			sourceNetDisk.setNetDiskId(sourceNetDiskId);
			sourceNetDisk.setOrgId(account.getOrgId());
			sourceNetDisk = netDiskService.selectOneNetDisk(sourceNetDisk);
			NetDisk targetNetDiks = new NetDisk();
			targetNetDiks.setNetDiskId(sourceNetDiskId);
			targetNetDiks.setOrgId(account.getOrgId());
			targetNetDiks = netDiskService.selectOneNetDisk(targetNetDiks);
			if(netDiskService.copyFile(sourcePath, sourceNetDisk, targetPath, targetNetDiks))
			{
				return RetDataTools.Ok("文件复制粘贴成功!");
			}else
			{
				return RetDataTools.NotOk("文件复制粘贴失败!");
			}
		}
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
* @Title: addNetDiskPriv 
* @Description: TODO 批量更新权限
* @param @param request
* @param @param user
* @param @param dept
* @param @param leave
* @param @param range
* @param @param netDiskId
* @param @return 设定文件 
* @return RetDataBean 返回类型 

 */

@RequestMapping(value="/addNetDiskPriv",method=RequestMethod.POST)
public RetDataBean addNetDiskPriv(HttpServletRequest request,
		String user,
		String dept,
		String leave,
		String range,
		String netDiskId
		)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(netDiskId))
		{
			return RetDataTools.NotOk("请求的参数有问题,请检查!");
		}
		return RetDataTools.Ok("批量更新权限成功!", netDiskService.addNetDiskPriv(account, netDiskId, user, dept, leave, range));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
* @Title: removeNetDiskPriv 
* @Description: TODO 批量移除权限
* @param @param request
* @param @param user
* @param @param dept
* @param @param leave
* @param @param range
* @param @param netDiskId
* @param @return 设定文件 
* @return RetDataBean 返回类型 

 */
@RequestMapping(value="/removeNetDiskPriv",method=RequestMethod.POST)
public RetDataBean removeNetDiskPriv(HttpServletRequest request,
		String user,
		String dept,
		String leave,
		String range,
		String netDiskId
		)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(netDiskId))
		{
			return RetDataTools.NotOk("请求的参数有问题,请检查!");
		}
		return RetDataTools.Ok("批量更新权限成功!", netDiskService.removeNetDiskPriv(account, netDiskId, user, dept, leave, range));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
* @Title: createPublicFile 
* @Description: TODO 创建公共文件柜文件
* @param @param request
* @param @param publicFileFolder
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/createPublicFile",method=RequestMethod.POST)
public RetDataBean createPublicFile(HttpServletRequest request,PublicFile publicFile,String msgType)
{
	try
	{
		Account account = accountService.getRedisAccount(request);
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		if(StringUtils.isBlank(publicFile.getAttach()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Attach attach = new Attach();
		attach.setOrgId(account.getOrgId());
		attach.setAttachId(publicFile.getAttach());
		attach = attachService.selectOne(attach);
		publicFile.setFileId(SysTools.getGUID());
		publicFile.setCreateUser(account.getAccountId());
		publicFile.setFileName(attach.getOldName());
		publicFile.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		publicFile.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建成功!", publicFileService.createPublicFile(account,userInfo,publicFile,msgType));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: deletePublicFile   
 * @Description: TODO 删除公共文件柜文件
 * @param: request
 * @param: publicFile
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deletePublicFile",method=RequestMethod.POST)
public RetDataBean deletePublicFile(HttpServletRequest request,PublicFile publicFile)
{
	try
	{
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		if(StringUtils.isBlank(publicFile.getFileId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		publicFile.setOrgId(userInfo.getOrgId());
		publicFile = publicFileService.selectOnePublicFile(publicFile);
		boolean flag = publicFileFolderService.haveManagePriv(userInfo.getOrgId(), publicFile.getFolderId(), userInfo.getAccountId(), userInfo.getDeptId(), userInfo.getLeadLeave());
		if(flag)
		{
			return RetDataTools.Ok("删除成功!", publicFileService.deletePublicFile(publicFile));
		}else {
			return RetDataTools.NotOk("您没有权限删除此文件!");
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
* @Title: createPublicFileFolder 
* @Description: TODO 创建公共文件夹
* @param @param request
* @param @param publicFileFolder
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/createPublicFileFolder",method=RequestMethod.POST)
public RetDataBean createPublicFileFolder(HttpServletRequest request,PublicFileFolder publicFileFolder)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		publicFileFolder.setFolderId(SysTools.getGUID());
		publicFileFolder.setCreateUser(account.getAccountId());
		publicFileFolder.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		publicFileFolder.setOrgId(account.getOrgId());
		publicFileFolder.setStatus("0");
		if(StringUtils.isBlank(publicFileFolder.getOwner()))
		{
			publicFileFolder.setOwner(account.getAccountId());
		}
		if(StringUtils.isBlank(publicFileFolder.getFolderLeave()))
		{
			publicFileFolder.setFolderLeave("0");
		}
		return RetDataTools.Ok("创建成功!", publicFileFolderService.insertPublicFileFolder(publicFileFolder));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: setPublicFilePriv   
 * @Description: TODO 设置公共文件夹权限
 * @param: request
 * @param: publicFileFolder
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/setPublicFilePriv",method=RequestMethod.POST)
public RetDataBean setPublicFilePriv(HttpServletRequest request,PublicFileFolder publicFileFolder)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(publicFileFolder.getFolderId()))
		{
			return RetDataTools.NotOk("参数有问题，请检查！");
		}
		if(!account.getOpFlag().equals("1"))
		{
			PublicFileFolder newPublicFileFolder = new PublicFileFolder();
			newPublicFileFolder.setFolderId(publicFileFolder.getFolderId());
			newPublicFileFolder.setOrgId(account.getOrgId());
			newPublicFileFolder=publicFileFolderService.selectOnePublicFileFolder(newPublicFileFolder);
			if(!newPublicFileFolder.getCreateUser().equals(account.getAccountId()))
					{
				return RetDataTools.NotOk("您不是文件夹创建者！");
					}
		}
		Example example = new Example(PublicFileFolder.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("folderId",publicFileFolder.getFolderId());
		return RetDataTools.Ok("权限设置成功!", publicFileFolderService.updatePublicFileFolder(publicFileFolder, example));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
* @Title: addPublicFolderPriv 
* @Description: TODO 批量添加公共文件柜权限
* @param @param request
* @param @param user
* @param @param dept
* @param @param leave
* @param @param range
* @param @param folderId
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/addPublicFolderPriv",method=RequestMethod.POST)
public RetDataBean addPublicFolderPriv(HttpServletRequest request,
		String user,
		String dept,
		String leave,
		String range,
		String folderId
		)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(folderId))
		{
			return RetDataTools.NotOk("请求的参数有问题,请检查!");
		}
		return RetDataTools.Ok("批量更新权限成功!", publicFileFolderService.addPublicFileFolderPriv(account, folderId, user, dept, leave, range));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
* @Title: removePublicFolderPriv 
* @Description: TODO 批量删除权限
* @param @param request
* @param @param user
* @param @param dept
* @param @param leave
* @param @param range
* @param @param folderId
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
@RequestMapping(value="/removePublicFolderPriv",method=RequestMethod.POST)
public RetDataBean removePublicFolderPriv(HttpServletRequest request,
		String user,
		String dept,
		String leave,
		String range,
		String folderId
		)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(folderId))
		{
			return RetDataTools.NotOk("请求的参数有问题,请检查!");
		}
		return RetDataTools.Ok("批量更新权限成功!", publicFileFolderService.removePublicFileFolderPriv(account, folderId, user, dept, leave, range));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}






}
