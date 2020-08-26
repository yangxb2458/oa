package com.core136.controller.dataupload;

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
import com.core136.bean.dataupload.DataUploadHandle;
import com.core136.bean.dataupload.DataUploadInfo;
import com.core136.bean.hr.HrRecruitPlan;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.dataupload.DataUploadHandleService;
import com.core136.service.dataupload.DataUploadInfoService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/ret/datauploadget")
public class RoutGetDataInfoController {
@Autowired
private DataUploadInfoService dataUploadInfoService;
@Autowired
private DataUploadHandleService dataUploadHandleService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: getDataUploadHandleById   
 * @Description: TODO 获取信息处理详情
 * @param request
 * @param dataUploadHandle
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getDataUploadHandleById",method=RequestMethod.POST)
public RetDataBean getDataUploadHandleById(HttpServletRequest request,DataUploadHandle dataUploadHandle)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		dataUploadHandle.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",dataUploadHandleService.selectOneDataUploadHandle(dataUploadHandle));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getDataUploadInfoById   
 * @Description: TODO 获取上报信息详情
 * @param request
 * @param dataUploadInfo
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getDataUploadInfoById",method=RequestMethod.POST)
public RetDataBean getDataUploadInfoById(HttpServletRequest request,DataUploadInfo dataUploadInfo)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		dataUploadInfo.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",dataUploadInfoService.selectOneDataUploadInfo(dataUploadInfo));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getDataUploadInfoList   
 * @Description: TODO 获取上报信息列表
 * @param request
 * @param pageParam
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dataType
 * @param approvedType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getDataUploadInfoList",method=RequestMethod.POST)
public RetDataBean getDataUploadInfoList(HttpServletRequest request,PageParam pageParam,String deptId,String fromAccountId,
		String beginTime,String endTime,String dataType,String approvedType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=dataUploadInfoService.getDataUploadInfoList(pageParam, deptId, fromAccountId, beginTime, endTime, dataType, approvedType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getToProcessInfoList   
 * @Description: TODO 获取持处理的信息列表
 * @param request
 * @param pageParam
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dataType
 * @param approvedType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getToProcessInfoList",method=RequestMethod.POST)
public RetDataBean getToProcessInfoList(HttpServletRequest request,PageParam pageParam,String deptId,String fromAccountId,
		String beginTime,String endTime,String dataType,String approvedType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=dataUploadInfoService.getToProcessInfoList(pageParam, deptId, fromAccountId, beginTime, endTime, dataType, approvedType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getOldProcessInfoList   
 * @Description: TODO 信息处理历史列表
 * @param request
 * @param pageParam
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dataType
 * @param approvedType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getOldProcessInfoList",method=RequestMethod.POST)
public RetDataBean getOldProcessInfoList(HttpServletRequest request,PageParam pageParam,String deptId,String fromAccountId,
		String beginTime,String endTime,String dataType,String approvedType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=dataUploadInfoService.getOldProcessInfoList(pageParam, deptId, fromAccountId, beginTime, endTime, dataType, approvedType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

}
