package com.core136.controller.projectbuild;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.projectbuild.ProjectBuildContract;
import com.core136.bean.projectbuild.ProjectBuildContractSort;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildContractService;
import com.core136.service.projectbuild.ProjectBuildContractSortService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/ret/projectbuildget/contract")
public class RoutGetProjectBuildContractController {
@Autowired
private ProjectBuildContractSortService projectBuildContractSortService;
@Autowired
private ProjectBuildContractService projectBuildContractService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: getProjectBuildContractSortTree   
 * @Description: TODO 获取工程合同树结构   
 * @param: request
 * @param: sortId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
@RequestMapping(value="/getProjectBuildContractSortTree",method=RequestMethod.POST)
public List<Map<String,String>> getProjectBuildContractSortTree(HttpServletRequest request,String sortId)
{
	try
	{
		String sortLeave = "0";
		if(StringUtils.isNotBlank(sortId))
		{
			sortLeave = sortId;
		}
		Account account=accountService.getRedisAccount(request);
		return projectBuildContractSortService.getProjectBuildContractSortTree(account.getOrgId(), sortLeave);
	}catch (Exception e) {
		return null;
	}
}

/**
 * 
 * @Title: projectBuildContractSortById   
 * @Description: TODO 工程分类详情  
 * @param: request
 * @param: projectBuildPontractSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getProjectBuildContractSortById",method=RequestMethod.POST)
public RetDataBean getProjectBuildContractSortById(HttpServletRequest request,ProjectBuildContractSort projectBuildPontractSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildPontractSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",projectBuildContractSortService.selectOneProjectBuildContractSort(projectBuildPontractSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getProjectBuildContractById   
 * @Description: TODO 获取合同详情 
 * @param: request
 * @param: projectBuildPontract
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getProjectBuildContractById",method=RequestMethod.POST)
public RetDataBean getProjectBuildContractById(HttpServletRequest request,ProjectBuildContract projectBuildPontract)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildPontract.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",projectBuildContractService.selectOneProjectBuildContract(projectBuildPontract));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getcontractlist   
 * @Description: TODO 获取合同列表
 * @param: request
 * @param: sortId
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getcontractlist",method=RequestMethod.POST)
public RetDataBean getcontractlist(
		HttpServletRequest request,
		String sortId,
		PageParam pageParam
		)
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
	PageInfo<Map<String, String>> pageInfo=projectBuildContractService.getcontractlist(pageParam,sortId);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: querycontractlist   
 * @Description: TODO 合同查询 
 * @param: request
 * @param: beginTime
 * @param: endTime
 * @param: signUser
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/querycontractlist",method=RequestMethod.POST)
public RetDataBean querycontractlist(
		HttpServletRequest request,
		String beginTime,
		String endTime,
		String signUser,
		String type,
		PageParam pageParam
		)
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
	PageInfo<Map<String, String>> pageInfo=projectBuildContractService.querycontractlist(pageParam,beginTime,endTime,signUser,type);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

}
