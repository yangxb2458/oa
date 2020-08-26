package com.core136.controller.projectbuild;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.projectbuild.ProjectBuildDetails;
import com.core136.bean.projectbuild.ProjectBuildSort;
import com.core136.bean.projectbuild.ProjectBuildStage;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildDetailsService;
import com.core136.service.projectbuild.ProjectBuildSortService;
import com.core136.service.projectbuild.ProjectBuildStageService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @ClassName:  RoutSetProjectBuildController   
 * @Description:TODO 工程项目
 * @author: 稠云技术 
 * @date:   2019年9月8日 下午7:46:51   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@RestController
@RequestMapping("/set/projectbuildset")
public class RoutSetProjectBuildController {
@Autowired
private ProjectBuildSortService projectBuildSortService;
@Autowired
private ProjectBuildDetailsService projectBuildDetailsService;
@Autowired
private ProjectBuildStageService projectBuildStageService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: insertProjectBuildDetails   
 * @Description: TODO 创建项目
 * @param: request
 * @param: projectBuildDetails
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertProjectBuildDetails",method=RequestMethod.POST)
public RetDataBean insertProjectBuildDetails(HttpServletRequest request,ProjectBuildDetails projectBuildDetails)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildDetails.setProjectId(SysTools.getGUID());
		projectBuildDetails.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildDetails.setCreateUser(account.getAccountId());
		projectBuildDetails.setStatus("0");
		projectBuildDetails.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建项目成功!",projectBuildDetailsService.insertProjectBuildDetails(projectBuildDetails));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: delProjectBuildDetails   
 * @Description: TODO 删除工程项目 
 * @param: request
 * @param: projectBuildDetails
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/delProjectBuildDetails",method=RequestMethod.POST)
public RetDataBean delProjectBuildDetails(HttpServletRequest request,ProjectBuildDetails projectBuildDetails)
{
	try
	{
		if(StringUtils.isBlank(projectBuildDetails.getProjectId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildDetails.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除项目成功!",projectBuildDetailsService.deleteProjectBuildDetails(projectBuildDetails));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateProjectBuildDetails   
 * @Description: TODO 更新工程项目详情 
 * @param: request
 * @param: projectBuildDetails
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateProjectBuildDetails",method=RequestMethod.POST)
public RetDataBean updateProjectBuildDetails(HttpServletRequest request,ProjectBuildDetails projectBuildDetails)
{
	try
	{
		if(StringUtils.isBlank(projectBuildDetails.getProjectId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildDetails.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("projectId",projectBuildDetails.getProjectId());
		return RetDataTools.Ok("更新成功!",projectBuildDetailsService.updateProjectBuildDetails(example,projectBuildDetails));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: delProjectlSort   
 * @Description: TODO 删除工程分类
 * @param: request
 * @param: projectBuildMaterialSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/delProjectBuildSort",method=RequestMethod.POST)
public RetDataBean delProjectBuildSort(HttpServletRequest request,ProjectBuildSort projectBuildSort)
{
	try
	{
		if(StringUtils.isBlank(projectBuildSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除操作成功!",projectBuildSortService.deleteProjectBuildSort(projectBuildSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertProjectBuildSort   
 * @Description: TODO 创建工程分类
 * @param: request
 * @param: projectBuildSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertProjectBuildSort",method=RequestMethod.POST)
public RetDataBean insertProjectBuildSort(HttpServletRequest request,ProjectBuildSort projectBuildSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildSort.setSortId(SysTools.getGUID());
		projectBuildSort.getSortLeave();
		projectBuildSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildSort.setCreateUser(account.getAccountId());
		if(StringUtils.isBlank(projectBuildSort.getSortLeave()))
		{
			projectBuildSort.setSortLeave("0");
		}
		projectBuildSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加项目分类成功!",projectBuildSortService.insertProjectBuildSort(projectBuildSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateProjectBuildSort   
 * @Description: TODO 更新工程分类
 * @param: request
 * @param: projectBuildSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateProjectBuildSort",method=RequestMethod.POST)
public RetDataBean updateProjectBuildSort(HttpServletRequest request,ProjectBuildSort projectBuildSort)
{
	try
	{
		if(StringUtils.isBlank(projectBuildSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildSort.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("sortId",projectBuildSort.getSortId());
		return RetDataTools.Ok("更新成功!",projectBuildSortService.updateProjectBuildSort(example,projectBuildSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 创建项目节点
 * @param request
 * @param projectBuildStage
 * @return
 */
@RequestMapping(value="/insertProjectBuildStage",method=RequestMethod.POST)
public RetDataBean insertProjectBuildStage(HttpServletRequest request,ProjectBuildStage projectBuildStage)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildStage.setStatus("0");
		projectBuildStage.setStageId(SysTools.getGUID());
		projectBuildStage.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildStage.setCreateUser(account.getAccountId());
		projectBuildStage.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建项目节点成功!",projectBuildStageService.insertProjectBuildStage(projectBuildStage));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 删除项目节点
 * @param request
 * @param projectBuildStage
 * @return
 */
@RequestMapping(value="/delProjectBuildStage",method=RequestMethod.POST)
public RetDataBean delProjectBuildStage(HttpServletRequest request,ProjectBuildStage projectBuildStage)
{
	try
	{
		if(StringUtils.isBlank(projectBuildStage.getStageId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildStage.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除项目节点成功!",projectBuildStageService.deleteProjectBuildStage(projectBuildStage));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 更新工程项目节点
 * @param request
 * @param projectBuildStage
 * @return
 */
@RequestMapping(value="/updateProjectBuildStage",method=RequestMethod.POST)
public RetDataBean updateProjectBuildStage(HttpServletRequest request,ProjectBuildStage projectBuildStage)
{
	try
	{
		if(StringUtils.isBlank(projectBuildStage.getStageId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildStage.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("stageId",projectBuildStage.getStageId());
		return RetDataTools.Ok("更新成功!",projectBuildStageService.updateProjectBuildStage(example,projectBuildStage));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}



}
