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
import com.core136.bean.projectbuild.ProjectBuildArtificialSort;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildArtificialSortService;
import com.core136.service.projectbuild.ProjectBuildTeamService;
import com.core136.service.projectbuild.ProjectBuildWorkerService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
@RestController
@RequestMapping("/ret/projectbuildartificialget")
public class RoutGetProjectBuildArtificialController {
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
	 * @Title: getProjectBuildArtificialSortTree   
	 * @Description: TODO 获取工种分类树结构  
	 * @param: request
	 * @param: sortId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	@RequestMapping(value="/getProjectBuildArtificialSortTree",method=RequestMethod.POST)
	public List<Map<String,String>> getProjectBuildArtificialSortTree(HttpServletRequest request,String sortId)
	{
		try
		{
			String sortLeave = "0";
			if(StringUtils.isNotBlank(sortId))
			{
				sortLeave = sortId;
			}
			Account account=accountService.getRedisAccount(request);
			return projectBuildArtificialSortService.getProjectBuildArtificialSortTree(account.getOrgId(), sortLeave);
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getProjectArtificialSortById   
	 * @Description: 获取工种分类详情 
	 * @param: request
	 * @param: projectBuildArtificialSort
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getProjectArtificialSortById",method=RequestMethod.POST)
	public RetDataBean getProjectArtificialSortById(HttpServletRequest request,ProjectBuildArtificialSort projectBuildArtificialSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildArtificialSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildArtificialSortService.selectOneProjectBuildArtificialSort(projectBuildArtificialSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
