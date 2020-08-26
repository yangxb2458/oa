package com.core136.controller.archives;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.archives.ArchivesBorrowFile;
import com.core136.bean.archives.ArchivesBorrowVolume;
import com.core136.bean.archives.ArchivesDestroyRecord;
import com.core136.bean.archives.ArchivesFile;
import com.core136.bean.archives.ArchivesRepository;
import com.core136.bean.archives.ArchivesVolume;
import com.core136.service.account.AccountService;
import com.core136.service.archives.ArchivesBorrowFileService;
import com.core136.service.archives.ArchivesBorrowVolumeService;
import com.core136.service.archives.ArchivesDestroyRecordService;
import com.core136.service.archives.ArchivesFileService;
import com.core136.service.archives.ArchivesRepositoryService;
import com.core136.service.archives.ArchivesVolumeService;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/set/archivesset")
public class RouteSetArchivesController {
	@Autowired
	private ArchivesRepositoryService archivesRepositoryService;
	@Autowired
	private ArchivesVolumeService archivesVolumeService;
	@Autowired
	private ArchivesFileService archivesFileService;
	@Autowired
	private ArchivesBorrowFileService archivesBorrowFileService;
	@Autowired
	private ArchivesBorrowVolumeService archivesBorrowVolumeService;
	@Autowired
	private ArchivesDestroyRecordService archivesDestroyRecordService;
	@Autowired
	private AccountService accountService;
	
	/**
	 * 
	 * @Title: destroyArchives
	 * @Description: TODO 档案销毁
	 * @param request
	 * @param archivesBorrow
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/destroyArchives",method=RequestMethod.POST)
	public RetDataBean destroyArchives(HttpServletRequest request,ArchivesDestroyRecord archivesDestroyRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			archivesDestroyRecord.setRecordId(SysTools.getGUID());
			archivesDestroyRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			archivesDestroyRecord.setCreateUser(account.getAccountId());
			archivesDestroyRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("档案销毁成功!", archivesDestroyRecordService.destroyArchives(archivesDestroyRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: doApprovalvolume   
	 * @Description: TODO档案案卷借阅审批
	 * @param request
	 * @param archivesBorrowVolume
	 * @param status
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/doApprovalvolume",method=RequestMethod.POST)
	public RetDataBean doApprovalvolume(HttpServletRequest request,ArchivesBorrowVolume archivesBorrowVolume,String status)
	{
		try
		{
			if(StringUtils.isBlank(archivesBorrowVolume.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(ArchivesBorrowVolume.class);
			archivesBorrowVolume.setApprovalStatus(status);
			archivesBorrowVolume.setApprovalTime("yyyy-MM-dd HH:mm:ss");
			archivesBorrowVolume.setApprovalUser(account.getAccountId());
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",archivesBorrowVolume.getRecordId());
			return RetDataTools.Ok("档案案卷借阅 审批完成!", archivesBorrowVolumeService.updateArchivesBorrowVolume(example,archivesBorrowVolume));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	
	/**
	 * 
	 * @Title: doApprovalfile   
	 * @Description: TODO档案文件借阅 审批
	 * @param request
	 * @param archivesBorrowVolume
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/doApprovalfile",method=RequestMethod.POST)
	public RetDataBean doApprovalfile(HttpServletRequest request,ArchivesBorrowFile archivesBorrowFile,String status)
	{
		try
		{
			if(StringUtils.isBlank(archivesBorrowFile.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(ArchivesBorrowFile.class);
			archivesBorrowFile.setApprovalStatus(status);
			archivesBorrowFile.setApprovalTime("yyyy-MM-dd HH:mm:ss");
			archivesBorrowFile.setApprovalUser(account.getAccountId());
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",archivesBorrowFile.getRecordId());
			return RetDataTools.Ok("档案文件借阅 审批完成!", archivesBorrowFileService.updateArchivesBorrowFile(example,archivesBorrowFile));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertArchivesBorrowVolume
	 * @Description: TODO 添加借阅记录
	 * @param request
	 * @param archivesBorrow
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertArchivesBorrowVolume",method=RequestMethod.POST)
	public RetDataBean insertArchivesBorrowVolume(HttpServletRequest request,ArchivesBorrowVolume archivesBorrowVolume)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			archivesBorrowVolume.setRecordId(SysTools.getGUID());
			archivesBorrowVolume.setApprovalStatus("0");
			archivesBorrowVolume.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			archivesBorrowVolume.setCreateUser(account.getAccountId());
			archivesBorrowVolume.setOrgId(account.getOrgId());
			return RetDataTools.Ok("申请成功!", archivesBorrowVolumeService.insertArchivesBorrowVolume(archivesBorrowVolume));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteArchivesBorrowVolume  
	 * @Description: TODO 删除借阅记录
	 * @param request
	 * @param archivesBorrow
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteArchivesBorrowVolume",method=RequestMethod.POST)
	public RetDataBean deleteArchivesBorrowVolume(HttpServletRequest request,ArchivesBorrowVolume archivesBorrowVolume)
	{
		try
		{
			if(StringUtils.isBlank(archivesBorrowVolume.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			archivesBorrowVolume.setOrgId(account.getOrgId());
			return RetDataTools.Ok("撤销申请成功!",archivesBorrowVolumeService.deleteArchivesBorrowVolume(archivesBorrowVolume));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: updateArchivesBorrowVolume   
	 * @Description: TODO 更新借阅记录
	 * @param request
	 * @param archivesBorrow
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateArchivesBorrowVolume",method=RequestMethod.POST)
	public RetDataBean updateArchivesBorrowVolume(HttpServletRequest request,ArchivesBorrowVolume archivesBorrowVolume)
	{
		try
		{
			if(StringUtils.isBlank(archivesBorrowVolume.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(ArchivesBorrowVolume.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",archivesBorrowVolume.getRecordId());
			return RetDataTools.Ok("更新文件成功!", archivesBorrowVolumeService.updateArchivesBorrowVolume(example,archivesBorrowVolume));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertArchivesBorrowFile 
	 * @Description: TODO 添加借阅记录
	 * @param request
	 * @param archivesBorrow
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertArchivesBorrowFile",method=RequestMethod.POST)
	public RetDataBean insertArchivesBorrowFile(HttpServletRequest request,ArchivesBorrowFile archivesBorrowFile)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			archivesBorrowFile.setRecordId(SysTools.getGUID());
			archivesBorrowFile.setApprovalStatus("0");
			archivesBorrowFile.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			archivesBorrowFile.setCreateUser(account.getAccountId());
			archivesBorrowFile.setOrgId(account.getOrgId());
			return RetDataTools.Ok("申请成功!", archivesBorrowFileService.insertArchivesBorrowFile(archivesBorrowFile));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteArchivesBorrowFile   
	 * @Description: TODO 删除借阅记录
	 * @param request
	 * @param archivesBorrow
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteArchivesBorrowFile",method=RequestMethod.POST)
	public RetDataBean deleteArchivesBorrowFile(HttpServletRequest request,ArchivesBorrowFile archivesBorrowFile)
	{
		try
		{
			if(StringUtils.isBlank(archivesBorrowFile.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			archivesBorrowFile.setOrgId(account.getOrgId());
			return RetDataTools.Ok("撤销申请成功!",archivesBorrowFileService.deleteArchivesBorrowFile(archivesBorrowFile));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: updateArchivesBorrowFile   
	 * @Description: TODO 更新借阅记录
	 * @param request
	 * @param archivesBorrow
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateArchivesBorrowFile",method=RequestMethod.POST)
	public RetDataBean updateArchivesBorrowFile(HttpServletRequest request,ArchivesBorrowFile archivesBorrowFile)
	{
		try
		{
			if(StringUtils.isBlank(archivesBorrowFile.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(ArchivesBorrowFile.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",archivesBorrowFile.getRecordId());
			return RetDataTools.Ok("更新文件成功!", archivesBorrowFileService.updateArchivesBorrowFile(example,archivesBorrowFile));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertArchivesFile   
	 * @Description: TODO 添加文件
	 * @param request
	 * @param archivesVolume
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertArchivesFile",method=RequestMethod.POST)
	public RetDataBean insertArchivesFile(HttpServletRequest request,ArchivesFile archivesFile)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			archivesFile.setFileId(SysTools.getGUID());
			archivesFile.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			archivesFile.setCreateUser(account.getAccountId());
			archivesFile.setOrgId(account.getOrgId());
			archivesFile.setDestoryFlag("0");
			return RetDataTools.Ok("创建成功!", archivesFileService.insertArchivesFile(archivesFile));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteArchivesFile 
	 * @Description: TODO 删除文件
	 * @param request
	 * @param archivesVolume
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteArchivesFile",method=RequestMethod.POST)
	public RetDataBean deleteArchivesFile(HttpServletRequest request,ArchivesFile archivesFile)
	{
		try
		{
			if(StringUtils.isBlank(archivesFile.getFileId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			archivesFile.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除文件成功!",archivesFileService.deleteArchivesFile(archivesFile));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateArchivesFile   
	 * @Description: TODO 更新文件
	 * @param request
	 * @param archivesVolume
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateArchivesFile",method=RequestMethod.POST)
	public RetDataBean updateArchivesFile(HttpServletRequest request,ArchivesFile archivesFile)
	{
		try
		{
			if(StringUtils.isBlank(archivesFile.getFileId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(ArchivesFile.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("fileId",archivesFile.getFileId());
			return RetDataTools.Ok("更新文件成功!", archivesFileService.updateArchivesFile(example,archivesFile));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertArchivesVolume   
	 * @Description: TODO 添加案卷
	 * @param request
	 * @param archivesVolume
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertArchivesVolume",method=RequestMethod.POST)
	public RetDataBean insertArchivesVolume(HttpServletRequest request,ArchivesVolume archivesVolume)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			archivesVolume.setVolumeId(SysTools.getGUID());
			archivesVolume.setCreateTime("yyyy-MM-dd HH:mm:ss");
			archivesVolume.setCreateUser(account.getAccountId());
			archivesVolume.setOrgId(account.getOrgId());
			archivesVolume.setDestoryFlag("0");
			return RetDataTools.Ok("创建成功!", archivesVolumeService.insertArchivesVolume(archivesVolume));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteArchivesVolume   
	 * @Description: TODO 删除案卷
	 * @param request
	 * @param archivesVolume
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteArchivesVolume",method=RequestMethod.POST)
	public RetDataBean deleteArchivesVolume(HttpServletRequest request,ArchivesVolume archivesVolume)
	{
		try
		{
			if(StringUtils.isBlank(archivesVolume.getVolumeId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			archivesVolume.setOrgId(account.getOrgId());
			ArchivesFile  archivesFile = new ArchivesFile();
			archivesFile.setOrgId(account.getOrgId());
			archivesFile.setVolumeId(archivesVolume.getVolumeId());
			if(archivesFileService.isExist(archivesFile)==0)
			{
				return RetDataTools.Ok("删除案卷成功!",archivesVolumeService.deleteArchivesVolume(archivesVolume));
			}else
			{
				return RetDataTools.NotOk("案卷下有文件，请先删除!");
			}
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateArchivesVolume   
	 * @Description: TODO 更新案卷
	 * @param request
	 * @param archivesVolume
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateArchivesVolume",method=RequestMethod.POST)
	public RetDataBean updateArchivesVolume(HttpServletRequest request,ArchivesVolume archivesVolume)
	{
		try
		{
			if(StringUtils.isBlank(archivesVolume.getVolumeId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(ArchivesVolume.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("volumeId",archivesVolume.getVolumeId());
			return RetDataTools.Ok("更新案卷成功!", archivesVolumeService.updateArchivesVolume(example,archivesVolume));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertArchivesRepository   
	 * @Description: TODO 添加卷库
	 * @param request
	 * @param archivesRepository
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertArchivesRepository",method=RequestMethod.POST)
	public RetDataBean insertArchivesRepository(HttpServletRequest request,ArchivesRepository archivesRepository)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			archivesRepository.setRepositoryId(SysTools.getGUID());
			archivesRepository.setCreateTime("yyyy-MM-dd HH:mm:ss");
			archivesRepository.setCreateUser(account.getAccountId());
			archivesRepository.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建成功!", archivesRepositoryService.insertArchivesRepository(archivesRepository));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteArchivesRepository   
	 * @Description: TODO 删除卷库
	 * @param request
	 * @param archivesRepository
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteArchivesRepository",method=RequestMethod.POST)
	public RetDataBean deleteArchivesRepository(HttpServletRequest request,ArchivesRepository archivesRepository)
	{
		try
		{
			if(StringUtils.isBlank(archivesRepository.getRepositoryId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			archivesRepository.setOrgId(account.getOrgId());
			ArchivesVolume archivesVolume = new ArchivesVolume();
			archivesVolume.setOrgId(account.getOrgId());
			archivesVolume.setRepositoryId(archivesRepository.getRepositoryId());
			if(archivesVolumeService.isExist(archivesVolume)==0)
			{
				return RetDataTools.Ok("删除卷库成功!",archivesRepositoryService.deleteArchivesRepository(archivesRepository));
			}else
			{
				return RetDataTools.NotOk("卷库下有案卷，请先删除!");
			}
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateArchivesRepository   
	 * @Description: TODO 更新卷库信息
	 * @param request
	 * @param archivesRepository
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateArchivesRepository",method=RequestMethod.POST)
	public RetDataBean updateArchivesRepository(HttpServletRequest request,ArchivesRepository archivesRepository)
	{
		try
		{
			if(StringUtils.isBlank(archivesRepository.getRepositoryId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(ArchivesRepository.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("repositoryId",archivesRepository.getRepositoryId());
			return RetDataTools.Ok("更新卷库成功!", archivesRepositoryService.updateArchivesRepository(example,archivesRepository));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
