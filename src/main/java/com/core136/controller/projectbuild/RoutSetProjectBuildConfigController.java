/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetProjectBuildConfigController.java   
 * @Package com.core136.controller.projectbuild   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月7日 上午9:49:56   
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
import com.core136.bean.projectbuild.ProjectBuildBpmConfig;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildBpmConfigService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/set/projectbuildset/config")
public class RoutSetProjectBuildConfigController {
	@Autowired
	private ProjectBuildBpmConfigService projectBuildBpmConfigService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: insertProjectBuildBpmConfig   
	 * @Description: TODO 添加审批流程
	 * @param: request
	 * @param: projectBuildBpmConfig
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertProjectBuildBpmConfig",method=RequestMethod.POST)
	public RetDataBean insertProjectBuildBpmConfig(HttpServletRequest request,ProjectBuildBpmConfig projectBuildBpmConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			
			ProjectBuildBpmConfig pc = new ProjectBuildBpmConfig();
			pc.setOrgId(account.getOrgId());
			pc.setEvent(projectBuildBpmConfig.getEvent());
			if(projectBuildBpmConfigService.selectOneProjectBuildBpmConfig(pc)==null)
			{
				projectBuildBpmConfig.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
				projectBuildBpmConfig.setCreateUser(account.getAccountId());
				projectBuildBpmConfig.setConfigId(SysTools.getGUID());
				projectBuildBpmConfig.setOrgId(account.getOrgId());
				return RetDataTools.Ok("设置BPM流程成功!",projectBuildBpmConfigService.insertProjectBuildBpmConfig(projectBuildBpmConfig));
			}else {
				Example example = new Example(ProjectBuildBpmConfig.class);
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("event",projectBuildBpmConfig.getEvent());
				projectBuildBpmConfig.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
				return RetDataTools.Ok("设置BPM流程成功!",projectBuildBpmConfigService.updateProjectBuildBpmConfig(example, projectBuildBpmConfig));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}

	
	/**
	 * 
	 * @Title: deleteProjectBuildBpmConfig   
	 * @Description: TODO 删除BPM配置
	 * @param: request
	 * @param: projectBuildBpmConfig
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteProjectBuildBpmConfig",method=RequestMethod.POST)
	public RetDataBean deleteProjectBuildBpmConfig(HttpServletRequest request,ProjectBuildBpmConfig projectBuildBpmConfig)
	{
		try
		{
			if(StringUtils.isBlank(projectBuildBpmConfig.getConfigId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			projectBuildBpmConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除BPM配置成功!",projectBuildBpmConfigService.deleteProjectBuildBpmConfig(projectBuildBpmConfig));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
