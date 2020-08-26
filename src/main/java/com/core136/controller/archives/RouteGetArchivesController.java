package com.core136.controller.archives;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.archives.ArchivesBorrowFile;
import com.core136.bean.archives.ArchivesBorrowVolume;
import com.core136.bean.archives.ArchivesFile;
import com.core136.bean.archives.ArchivesRepository;
import com.core136.bean.archives.ArchivesVolume;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.archives.ArchivesBorrowFileService;
import com.core136.service.archives.ArchivesBorrowVolumeService;
import com.core136.service.archives.ArchivesDestroyRecordService;
import com.core136.service.archives.ArchivesFileService;
import com.core136.service.archives.ArchivesRepositoryService;
import com.core136.service.archives.ArchivesVolumeService;
import com.github.pagehelper.PageInfo;


@RestController
@RequestMapping("/ret/archivesget")
public class RouteGetArchivesController {
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
 * @Title: getArchivesDestoryVolumeList   
 * @Description: TODO 档案销毁记录
 * @param request
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param createUer
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesDestoryVolumeList",method=RequestMethod.POST)
public RetDataBean getArchivesDestoryVolumeList(HttpServletRequest request,PageParam pageParam,String beginTime,String endTime,String createUer)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("D.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(createUer);
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=archivesDestroyRecordService.getArchivesDestoryVolumeList(pageParam,beginTime,endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesDestoryFileList   
 * @Description: TODO 档案销毁记录
 * @param request
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param createUer
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesDestoryFileList",method=RequestMethod.POST)
public RetDataBean getArchivesDestoryFileList(HttpServletRequest request,PageParam pageParam,String beginTime,String endTime,String createUer)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("D.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(createUer);
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=archivesDestroyRecordService.getArchivesDestoryFileList(pageParam,beginTime,endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getApprovalVolumeFile   
 * @Description: TODO 获取案卷借阅文件详情
 * @param request
 * @param archivesBorrowVolume
 * @param archivesFile
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getApprovalVolumeFile",method=RequestMethod.POST)
public RetDataBean getApprovalVolumeFile(HttpServletRequest request,ArchivesBorrowVolume archivesBorrowVolume,ArchivesFile archivesFile)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		archivesBorrowVolume.setOrgId(account.getOrgId());
		archivesFile.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", archivesBorrowVolumeService.getApprovalVolumeFile(account,archivesBorrowVolume,archivesFile));
	}catch (Exception e) {
		
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getBorrowArchivesFileList   
 * @Description: TODO 获取借阅的文件列表
 * @param request
 * @param pageParam
 * @param volumeId
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getBorrowArchivesFileList",method=RequestMethod.POST)
public RetDataBean getBorrowArchivesFileList(HttpServletRequest request,PageParam pageParam,String volumeId)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("F.SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=archivesFileService.getBorrowArchivesFileList(pageParam,volumeId);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getApprovalFile   
 * @Description: TODO 获取借阅文件详情
 * @param request
 * @param archivesBorrowFile
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getApprovalFile",method=RequestMethod.POST)
public RetDataBean getApprovalFile(HttpServletRequest request,ArchivesBorrowFile archivesBorrowFile)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		archivesBorrowFile.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", archivesBorrowFileService.getApprovalFile(account,archivesBorrowFile));
	}catch (Exception e) {
		
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesBorrowVolumeApprovalList   
 * @Description: TODO 获取待审批记录
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesBorrowVolumeApprovalList",method=RequestMethod.POST)
public RetDataBean getArchivesBorrowVolumeApprovalList(HttpServletRequest request,PageParam pageParam)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("B.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=archivesBorrowVolumeService.getArchivesBorrowVolumeApprovalList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesBorrowFileApprovalList   
 * @Description: TODO 获取待审批记录
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesBorrowFileApprovalList",method=RequestMethod.POST)
public RetDataBean getArchivesBorrowFileApprovalList(HttpServletRequest request,PageParam pageParam)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("B.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=archivesBorrowFileService.getArchivesBorrowFileApprovalList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesFileQueryList   
 * @Description: TODO 档案查询列表
 * @param request
 * @param pageParam
 * @param repositoryId
 * @param volumeId
 * @param fileType
 * @param secretLevel
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesFileQueryList",method=RequestMethod.POST)
public RetDataBean getArchivesFileQueryList(HttpServletRequest request,PageParam pageParam,String repositoryId,String volumeId,String fileType,String secretLevel)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("F.SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=archivesFileService.getArchivesFileQueryList(pageParam,repositoryId,volumeId,fileType,secretLevel);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesBorrowVolumeList   
 * @Description: TODO 案卷借阅记录
 * @param request
 * @param pageParam
 * @param approvalStatus
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesBorrowVolumeList",method=RequestMethod.POST)
public RetDataBean getArchivesBorrowVolumeList(HttpServletRequest request,PageParam pageParam,String approvalStatus)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("B.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=archivesBorrowVolumeService.getArchivesBorrowVolumeList(pageParam,approvalStatus);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesBorrowVolumeById   
 * @Description: TODO 获取案卷详情
 * @param request
 * @param archivesBorrowVolume
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesBorrowVolumeById",method=RequestMethod.POST)
public RetDataBean getArchivesBorrowVolumeById(HttpServletRequest request,ArchivesBorrowVolume archivesBorrowVolume)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		archivesBorrowVolume.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", archivesBorrowVolumeService.selectOneArchivesBorrowVolume(archivesBorrowVolume));
	}catch (Exception e) {
		
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getArchivesBorrowFileList   
 * @Description: TODO 我的借阅记录
 * @param request
 * @param pageParam
 * @param approvalStatus
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesBorrowFileList",method=RequestMethod.POST)
public RetDataBean getArchivesBorrowFileList(HttpServletRequest request,PageParam pageParam,String approvalStatus)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("B.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=archivesBorrowFileService.getArchivesBorrowFileList(pageParam,approvalStatus);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesBorrowById   
 * @Description: TODO 获取借阅详情
 * @param request
 * @param archivesBorrow
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesBorrowFileById",method=RequestMethod.POST)
public RetDataBean getArchivesBorrowById(HttpServletRequest request,ArchivesBorrowFile archivesBorrowFile)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		archivesBorrowFile.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", archivesBorrowFileService.selectOneArchivesBorrowFile(archivesBorrowFile));
	}catch (Exception e) {
		
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesFileList   
 * @Description: TODO 获取案卷列表
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesFileList",method=RequestMethod.POST)
public RetDataBean getArchivesFileList(HttpServletRequest request,PageParam pageParam,String volumeId,String fileType,String secretLevel)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("F.SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=archivesFileService.getArchivesFileList(pageParam,volumeId,fileType,secretLevel);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesVolumeListForSelect   
 * @Description: TODO 获取案卷列表
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesVolumeListForSelect",method=RequestMethod.POST)
public RetDataBean getArchivesVolumeListForSelect(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", archivesVolumeService.getArchivesVolumeListForSelect(account.getOrgId(),account.getOpFlag(),account.getAccountId()));
	}catch (Exception e) {
		
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getArchivesVolumeByRepositoryId   
 * @Description: TODO 获取卷库下的案卷列表
 * @param request
 * @param repositoryId
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesVolumeByRepositoryId",method=RequestMethod.POST)
public RetDataBean getArchivesVolumeByRepositoryId(HttpServletRequest request,String repositoryId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", archivesVolumeService.getArchivesVolumeByRepositoryId(account.getOrgId(),repositoryId));
	}catch (Exception e) {
		
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getArchivesFileById   
 * @Description: TODO 获取文件详情
 * @param request
 * @param archivesFile
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesFileById",method=RequestMethod.POST)
public RetDataBean getArchivesFileById(HttpServletRequest request,ArchivesFile archivesFile)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		archivesFile.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", archivesFileService.selectOneArchivesFile(archivesFile));
	}catch (Exception e) {
		
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getArchivesVolumeById   
 * @Description: TODO 获取案卷详情
 * @param request
 * @param archivesVolume
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesVolumeById",method=RequestMethod.POST)
public RetDataBean getArchivesVolumeById(HttpServletRequest request,ArchivesVolume archivesVolume)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		archivesVolume.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", archivesVolumeService.selectOneArchivesVolume(archivesVolume));
	}catch (Exception e) {
		
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getArchivesVolumeList   
 * @Description: TODO 获取案卷列表
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesVolumeList",method=RequestMethod.POST)
public RetDataBean getArchivesVolumeList(HttpServletRequest request,PageParam pageParam)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("V.SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=archivesVolumeService.getArchivesVolumeList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesRepositoryById   
 * @Description: TODO 获取卷库详情
 * @param request
 * @param archivesRepository
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesRepositoryById",method=RequestMethod.POST)
public RetDataBean getArchivesRepositoryById(HttpServletRequest request,ArchivesRepository archivesRepository)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		archivesRepository.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", archivesRepositoryService.selectOneArchivesRepository(archivesRepository));
	}catch (Exception e) {
		
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getArchivesRepositoryList   
 * @Description: TODO(这里用一句话描述这个方法的作用)
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getArchivesRepositoryList",method=RequestMethod.POST)
public RetDataBean getArchivesRepositoryList(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", archivesRepositoryService.getArchivesRepositoryList(account.getOrgId(),account.getOpFlag(),account.getAccountId()));
	}catch (Exception e) {
		
		return RetDataTools.Error(e.getMessage());
	}
}

}
