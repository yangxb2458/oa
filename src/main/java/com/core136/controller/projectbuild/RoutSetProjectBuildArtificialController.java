package com.core136.controller.projectbuild;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.projectbuild.ProjectBuildArtificialSort;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildArtificialSortService;
import com.core136.service.projectbuild.ProjectBuildTeamService;
import com.core136.service.projectbuild.ProjectBuildWorkerService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/set/projectbuildartificialset")
public class RoutSetProjectBuildArtificialController {
	@Autowired
	private ProjectBuildArtificialSortService projectBuildArtificialSortService;
	@Autowired
	private ProjectBuildTeamService projectBuildTeamService;
	@Autowired
	private ProjectBuildWorkerService projectBuildWorkerService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: delProjectBuildArtificialSort   
	 * @Description: TODO 删除工种分类  
	 * @param: request
	 * @param: projectBuildArtificialSort
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/delProjectBuildArtificialSort",method=RequestMethod.POST)
	public RetDataBean delProjectBuildArtificialSort(HttpServletRequest request,ProjectBuildArtificialSort projectBuildArtificialSort)
	{
		try
		{
			if(StringUtils.isBlank(projectBuildArtificialSort.getSortId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			projectBuildArtificialSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("分类删除成功!",projectBuildArtificialSortService.deleteProjectBuildArtificialSort(projectBuildArtificialSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertProjectBuildArtificialSort   
	 * @Description: TODO 添加工种分类
	 * @param: request
	 * @param: projectBuildArtificialSort
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertProjectBuildArtificialSort",method=RequestMethod.POST)
	public RetDataBean insertProjectBuildArtificialSort(HttpServletRequest request,ProjectBuildArtificialSort projectBuildArtificialSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildArtificialSort.setSortId(SysTools.getGUID());
			projectBuildArtificialSort.getSortLeave();
			projectBuildArtificialSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			projectBuildArtificialSort.setCreateUser(account.getAccountId());
			if(StringUtils.isBlank(projectBuildArtificialSort.getSortLeave()))
			{
				projectBuildArtificialSort.setSortLeave("0");
			}
			projectBuildArtificialSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("分类添加成功!",projectBuildArtificialSortService.insertProjectBuildArtificialSort(projectBuildArtificialSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: updateProjectBuildArtificialSort   
	 * @Description: TODO 更新工种分类
	 * @param: request
	 * @param: projectBuildArtificialSort
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateProjectBuildArtificialSort",method=RequestMethod.POST)
	public RetDataBean updateProjectBuildArtificialSort(HttpServletRequest request,ProjectBuildArtificialSort projectBuildArtificialSort)
	{
		try
		{
			if(StringUtils.isBlank(projectBuildArtificialSort.getSortId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(ProjectBuildArtificialSort.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("sortId",projectBuildArtificialSort.getSortId());
			return RetDataTools.Ok("分类修改成功!",projectBuildArtificialSortService.updateProjectBuildArtificialSort(example,projectBuildArtificialSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
