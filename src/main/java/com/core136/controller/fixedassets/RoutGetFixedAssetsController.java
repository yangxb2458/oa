/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetFixedassetController.java   
 * @Package com.core136.controller.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月25日 下午5:18:44   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.fixedassets;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.fixedassets.FixedAssets;
import com.core136.bean.fixedassets.FixedAssetsApply;
import com.core136.bean.fixedassets.FixedAssetsApproval;
import com.core136.bean.fixedassets.FixedAssetsRepair;
import com.core136.bean.fixedassets.FixedAssetsSort;
import com.core136.bean.fixedassets.FixedAssetsStorage;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.fixedassets.FixedAssetsApplyService;
import com.core136.service.fixedassets.FixedAssetsApprovalService;
import com.core136.service.fixedassets.FixedAssetsRepairService;
import com.core136.service.fixedassets.FixedAssetsService;
import com.core136.service.fixedassets.FixedAssetsSortService;
import com.core136.service.fixedassets.FixedAssetsStorageService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/ret/fixedassetsget")
public class RoutGetFixedAssetsController {
@Autowired
private FixedAssetsSortService fixedAssetsSortService;
@Autowired
private FixedAssetsService fixedAssetsService;
@Autowired
private FixedAssetsStorageService fixedAssetsStorageService;
@Autowired
private FixedAssetsApplyService fixedAssetApplayService;
@Autowired
private FixedAssetsApprovalService fixedAssetsApprovalService;
@Autowired
private FixedAssetsRepairService fixedAssetsRepairService;
@Autowired
private AccountService accountService;

/**
 * 
 * @Title: getApplyAndApproveInfo   
 * @Description: TODO 申请与审批详情
 * @param: request
 * @param: applyId
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getApplyAndApproveInfo",method=RequestMethod.POST)
public RetDataBean getApplyAndApproveInfo(HttpServletRequest request,String applyId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!",fixedAssetApplayService.getApplyAndApproveInfo(account.getOrgId(),applyId));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getFixedAssetsApplyList   
 * @Description: TODO 获取申请列表 
 * @param: request
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: status
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getFixedAssetsApplyList",method=RequestMethod.POST)
public RetDataBean getFixedAssetsApplyList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,String endTime,String status,String assetsSortId
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("A.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOpFlag(account.getOpFlag());
	PageInfo<Map<String, String>> pageInfo=fixedAssetApplayService.getFixedAssetsApplyList(pageParam, status, beginTime, endTime,assetsSortId);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getFixedAssetsRepairList   
 * @Description: TODO 获取修改列表
 * @param: request
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: assetsSortId
 * @param: status
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getFixedAssetsRepairList",method=RequestMethod.POST)
public RetDataBean getFixedAssetsRepairList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,String endTime,String assetsSortId,String status
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("R.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOpFlag(account.getOpFlag());
	PageInfo<Map<String, String>> pageInfo=fixedAssetsRepairService.getFixedAssetsRepairList(pageParam, beginTime, endTime, assetsSortId, status);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}



/**
 * 
 * @Title: getFixedAssetsRepairById   
 * @Description: TODO 获取修改详情
 * @param: request
 * @param: fixedAssetsRepair
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getFixedAssetsRepairById",method=RequestMethod.POST)
public RetDataBean getFixedAssetsRepairById(HttpServletRequest request,FixedAssetsRepair fixedAssetsRepair)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		fixedAssetsRepair.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",fixedAssetsRepairService.selectOneFixedAssetsRepair(fixedAssetsRepair));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getFixedAssetsApprovalById   
 * @Description: TODO 获取审批详情
 * @param: request
 * @param: fixedAssetsApproval
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getFixedAssetsApprovalById",method=RequestMethod.POST)
public RetDataBean getFixedAssetsApprovalById(HttpServletRequest request,FixedAssetsApproval fixedAssetsApproval)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		fixedAssetsApproval.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",fixedAssetsApprovalService.selectOneFixedAssetsApproval(fixedAssetsApproval));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getFixedAssetsApplyById   
 * @Description: TODO 获取申请详情
 * @param: request
 * @param: fixedAssetsApply
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getFixedAssetsApplyById",method=RequestMethod.POST)
public RetDataBean getFixedAssetsApplyById(HttpServletRequest request,FixedAssetsApply fixedAssetsApply)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		fixedAssetsApply.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",fixedAssetApplayService.selectOneFixedAssetsApply(fixedAssetsApply));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getApplyFixedAssetsList   
 * @Description: TODO 获取可申请的固定资产的列表
 * @param: request
 * @param: pageParam
 * @param: sortId
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getApplyFixedAssetsList",method=RequestMethod.POST)
public RetDataBean getApplyFixedAssetsList(
		HttpServletRequest request,
		PageParam pageParam,
		String sortId
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=fixedAssetsService.getApplyFixedAssetsList(pageParam,sortId);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: queryFixedAssetsList   
 * @Description: TODO 查询固定资产列表
 * @param: request
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: sortId
 * @param: ownDept
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/queryFixedAssetsList",method=RequestMethod.POST)
public RetDataBean queryFixedAssetsList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String sortId,
		String ownDept,
		String status
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=fixedAssetsService.queryFixedAssetsList(pageParam,beginTime,endTime,sortId,ownDept,status);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getAllocationList   
 * @Description: TODO 获取调拨列表  
 * @param: request
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: sortId
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getAllocationList",method=RequestMethod.POST)
public RetDataBean getAllocationList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String sortId
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("A.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=fixedAssetsService.getAllocationList(pageParam,beginTime,endTime,sortId);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getFixedAssetsList   
 * @Description: TODO 获取固定资产列表
 * @param: request
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getFixedAssetsList",method=RequestMethod.POST)
public RetDataBean getFixedAssetsList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String sortId
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=fixedAssetsService.getFixedAssetsList(pageParam,beginTime,endTime,sortId);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getFixedAssetsSortById   
 * @Description: TODO 获取固定资产分类详情
 * @param: request
 * @param: fixedAssetsSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getFixedAssetsSortById",method=RequestMethod.POST)
public RetDataBean getFixedAssetsSortById(HttpServletRequest request,FixedAssetsSort fixedAssetsSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		fixedAssetsSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",fixedAssetsSortService.selectOneFixedAssetsSort(fixedAssetsSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getFixedAssetsStorageById   
 * @Description: TODO 获取仓库详情
 * @param: request
 * @param: fixedAssetsStorage
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getFixedAssetsStorageById",method=RequestMethod.POST)
public RetDataBean getFixedAssetsStorageById(HttpServletRequest request,FixedAssetsStorage fixedAssetsStorage)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		fixedAssetsStorage.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",fixedAssetsStorageService.selectOneFixedAssetsStorage(fixedAssetsStorage));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getAllFixedAssetsStorageList   
 * @Description: TODO 获取仓库列表
 * @param: request
 * @param: fixedAssetsStorage
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getAllFixedAssetsStorageList",method=RequestMethod.POST)
public RetDataBean getAllFixedAssetsStorageList(HttpServletRequest request,FixedAssetsStorage fixedAssetsStorage)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!",fixedAssetsStorageService.getAllFixedAssetsStorageList(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getFixedAssetsById   
 * @Description: TODO 获取固定资产详情 
 * @param: request
 * @param: fixedAssets
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getFixedAssetsById",method=RequestMethod.POST)
public RetDataBean getFixedAssetsById(HttpServletRequest request,FixedAssets fixedAssets)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		fixedAssets.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",fixedAssetsService.selectOneFixedAssets(fixedAssets));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getFixedAssetSortTree   
 * @Description: TODO 获取固定资产的分类  
 * @param: request
 * @param: sortId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
@RequestMapping(value="/getFixedAssetSortTree",method=RequestMethod.POST)
public List<Map<String,String>> getFixedAssetSortTree(HttpServletRequest request,String sortId)
{
	try
	{
		String parentId = "0";
		if(StringUtils.isNotBlank(sortId))
		{
			parentId = sortId;
		}
		Account account=accountService.getRedisAccount(request);
		return fixedAssetsSortService.getFixedAssetSortTree(account.getOrgId(), parentId);
	}catch (Exception e) {
		return null;
	}
}

/**
 * 
 * @Title: getFixedAssetsStorageList   
 * @Description: TODO 获取仓库列表
 * @param: request
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getFixedAssetsStorageList",method=RequestMethod.POST)
public RetDataBean getFixedAssetsStorageList(HttpServletRequest request,PageParam pageParam)
	{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=fixedAssetsStorageService.getFixedAssetsStorageList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

}
