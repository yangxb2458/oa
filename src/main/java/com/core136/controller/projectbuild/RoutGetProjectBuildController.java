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
import com.core136.bean.projectbuild.ProjectBuildDetails;
import com.core136.bean.projectbuild.ProjectBuildSort;
import com.core136.bean.projectbuild.ProjectBuildStage;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildDetailsService;
import com.core136.service.projectbuild.ProjectBuildSortService;
import com.core136.service.projectbuild.ProjectBuildStageService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @ClassName:  RoutGetProjectBuildController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云技术 
 * @date:   2019年9月8日 下午7:46:37   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/ret/projectbuildget")
public class RoutGetProjectBuildController {
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
	 * @Title: getProjectBuildSortTree   
	 * @Description: TODO 获取工程分类树结构
	 * @param: request
	 * @param: sortId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	@RequestMapping(value="/getProjectBuildSortTree",method=RequestMethod.POST)
	public List<Map<String,String>> getProjectBuildSortTree(HttpServletRequest request,String sortId)
	{
		try
		{
			String sortLeave = "0";
			if(StringUtils.isNotBlank(sortId))
			{
				sortLeave = sortId;
			}
			Account account=accountService.getRedisAccount(request);
			return projectBuildSortService.getProjectBuildSortTree(account.getOrgId(), sortLeave);
		}catch (Exception e) {
			return null;
		}
	}
	/**
	 * 
	 * @Title: getProjectBuildListForTree   
	 * @Description: TODO 获取名称作为工程分类树结构   
	 * @param: request
	 * @param: sortId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	@RequestMapping(value="/getProjectBuildListForTree",method=RequestMethod.POST)
	public List<Map<String,String>> getProjectBuildListForTree(HttpServletRequest request,String sortId)
	{
		try
		{
			if(StringUtils.isBlank(sortId))
			{
				return null;
			}
			Account account=accountService.getRedisAccount(request);
			return projectBuildDetailsService.getProjectBuildListForTree(account.getOrgId(), sortId);
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getProjectBuildSortAllParentTree   
	 * @Description: TODO 获取所有工程分类作为父分类
	 * @param: request
	 * @param: sortId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	@RequestMapping(value="/getProjectBuildSortAllParentTree",method=RequestMethod.POST)
	public List<Map<String,String>> getProjectBuildSortAllParentTree(HttpServletRequest request,String sortId)
	{
		try
		{
			String sortLeave = "0";
			if(StringUtils.isNotBlank(sortId))
			{
				sortLeave = sortId;
			}
			Account account=accountService.getRedisAccount(request);
			return projectBuildSortService.getProjectBuildSortAllParentTree(account.getOrgId(), sortLeave);
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getProjectBuildSortById   
	 * @Description: TODO 获取工程分类详情
	 * @param: request
	 * @param: projectBuildSort
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getProjectBuildSortById",method=RequestMethod.POST)
	public RetDataBean getProjectBuildSortById(HttpServletRequest request,ProjectBuildSort projectBuildSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildSortService.selectOneProjectBuildSort(projectBuildSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getprojectbuilddetailslist   
	 * @Description: TODO 获取工程项目列表  
	 * @param: request
	 * @param: sortId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getprojectbuilddetailslist",method=RequestMethod.POST)
	public RetDataBean getprojectbuilddetailslist(
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
		PageInfo<Map<String, String>> pageInfo=projectBuildDetailsService.getprojectbuilddetailslist(pageParam,sortId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getProjectBuildDetailsById   
	 * @Description: TODO 获取工程项目详情
	 * @param: request
	 * @param: projectBuildDetails
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getProjectBuildDetailsById",method=RequestMethod.POST)
	public RetDataBean getProjectBuildDetailsById(HttpServletRequest request,ProjectBuildDetails projectBuildDetails)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildDetails.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildDetailsService.selectOneProjectBuildDetails(projectBuildDetails));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 获取工程项目节点详情
	 * @param request
	 * @param projectBuildStage
	 * @return
	 */
	@RequestMapping(value="/getProjectBuildStageById",method=RequestMethod.POST)
	public RetDataBean getProjectBuildStageById(HttpServletRequest request,ProjectBuildStage projectBuildStage)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildStage.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildStageService.selectOneProjectBuildStage(projectBuildStage));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getprojectbuildStageOpenlist   
	 * @Description: TODO 获取当前工程的进行中的节点  
	 * @param: request
	 * @param: projectId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getprojectbuildStageOpenlist",method=RequestMethod.POST)
	public RetDataBean getprojectbuildStageOpenlist(HttpServletRequest request,String projectId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!",projectBuildStageService.getprojectbuildStageOpenlist(account.getOrgId(),projectId));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getprojectbuildStagelist   
	 * @Description: TODO 获取工程节点列表 
	 * @param: request
	 * @param: sortId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getprojectbuildStagelist",method=RequestMethod.POST)
	public RetDataBean getprojectbuildStagelist(
			HttpServletRequest request,
			String projectId,
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
		PageInfo<Map<String, String>> pageInfo=projectBuildStageService.getprojectbuildStagelist(pageParam,projectId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: selectProjectBuild2ByTitle   
	 * @Description: TODO 模糊获取Select2的列表
	 * @param: request
	 * @param: projectTitle
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/selectProjectBuild2ByTitle",method=RequestMethod.POST)
	public RetDataBean selectProjectBuild2ByTitle(HttpServletRequest request,String projectTitle)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求数据成功!",projectBuildDetailsService.selectProjectBuild2ByTitle(account.getOrgId(),"%"+projectTitle+"%"));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
