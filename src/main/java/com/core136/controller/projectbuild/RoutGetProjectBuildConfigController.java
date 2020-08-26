/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetProjectBuildConfigController.java   
 * @Package com.core136.controller.projectbuild   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月7日 上午9:48:14   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.projectbuild;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildBpmConfigService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/ret/projectbuilget/config")
public class RoutGetProjectBuildConfigController {
	@Autowired
	private ProjectBuildBpmConfigService projectBuildBpmConfigService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: getAllProjectBuildBpmConfig   
	 * @Description: TODO 获取本工程下的所有BPM配置列表
	 * @param: request
	 * @param: sortId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getAllProjectBuildBpmConfig",method=RequestMethod.POST)
	public RetDataBean getAllProjectBuildBpmConfig(HttpServletRequest request,String sortId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!",projectBuildBpmConfigService.getAllProjectBuildBpmConfig(account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
