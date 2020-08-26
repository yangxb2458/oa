/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetProjectBuildUnitController.java   
 * @Package com.core136.controller.projectbuild   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月12日 上午8:44:38   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.projectbuild;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.projectbuild.ProjectBuildUnit;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildUnitService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/set/projectbuildunitset")

public class RoutSetProjectBuildUnitController {
@Autowired
private ProjectBuildUnitService projectBuildUnitService;
@Autowired
private AccountService accountService;
	
	@RequestMapping(value="/insertProjectBuildUnit",method=RequestMethod.POST)
	public RetDataBean insertSupplier(HttpServletRequest request,ProjectBuildUnit projectBuildUnit)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildUnit.setUnitId(SysTools.getGUID());
			projectBuildUnit.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			projectBuildUnit.setCreateUser(account.getAccountId());
			projectBuildUnit.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",projectBuildUnitService.insertProjectBuildUnit(projectBuildUnit));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: deleteProjectBuildUnit   
	 * @Description: TODO 删除单位
	 * @param: request
	 * @param: projectBuildUnit
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteProjectBuildUnit",method=RequestMethod.POST)
	public RetDataBean deleteProjectBuildUnit(HttpServletRequest request,ProjectBuildUnit projectBuildUnit)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(projectBuildUnit.getUnitId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			projectBuildUnit.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",projectBuildUnitService.deleteProjectBuildUnit(projectBuildUnit));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateProjectBuildUnit   
	 * @Description: TODO 更新工程单位  
	 * @param: request
	 * @param: projectBuildUnit
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateProjectBuildUnit",method=RequestMethod.POST)
	public RetDataBean updateProjectBuildUnit(HttpServletRequest request,ProjectBuildUnit projectBuildUnit)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(projectBuildUnit.getUnitId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Example example = new Example(ProjectBuildUnit.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("unitId",projectBuildUnit.getUnitId());
			return RetDataTools.Ok("更新成功!",projectBuildUnitService.updateProjectBuildUnit(example, projectBuildUnit));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
