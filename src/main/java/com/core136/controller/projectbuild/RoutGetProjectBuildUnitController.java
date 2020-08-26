package com.core136.controller.projectbuild;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.projectbuild.ProjectBuildUnit;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildUnitService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/ret/projectbuildunitget")
public class RoutGetProjectBuildUnitController {
@Autowired
private ProjectBuildUnitService projectBuildUnitService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: getAllUnit   
 * @Description: TODO 获取工程材料计量单位
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getAllUnit",method=RequestMethod.POST)
public RetDataBean getAllUnit(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!",projectBuildUnitService.getAllUnit(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getunit   
 * @Description: TODO 获取材料计量单位
 * @param: request
 * @param: projectBuildUnit
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getunitbyid",method=RequestMethod.POST)
public RetDataBean getunit(HttpServletRequest request,ProjectBuildUnit projectBuildUnit)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildUnit.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",projectBuildUnitService.selectOne(projectBuildUnit));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getProjectBuildUnitList   
 * @Description: TODO 获取工程项目计量单位列表
 * @param: request
 * @param: type
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getProjectBuildUnitList",method=RequestMethod.POST)
public RetDataBean getProjectBuildUnitList(
		HttpServletRequest request,
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
	PageInfo<Map<String, String>> pageInfo=projectBuildUnitService.getProjectBuildUnitList(pageParam,type);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}



}
