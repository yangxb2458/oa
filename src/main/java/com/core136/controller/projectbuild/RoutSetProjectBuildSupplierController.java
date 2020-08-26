package com.core136.controller.projectbuild;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.projectbuild.ProjectBuildSupplier;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildSupplierService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/set/projectbuilsupplierset")
public class RoutSetProjectBuildSupplierController {
@Autowired
private ProjectBuildSupplierService projectBuildSupplierService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: delSupplier   
 * @Description: TODO 删除供应商
 * @param: request
 * @param: projectBuildSupplier
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/delSupplier",method=RequestMethod.POST)
public RetDataBean delSupplier(HttpServletRequest request,ProjectBuildSupplier projectBuildSupplier)
{
	try
	{
		if(StringUtils.isBlank(projectBuildSupplier.getSupplierId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildSupplier.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",projectBuildSupplierService.deleteProjectBuildSupplier(projectBuildSupplier));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertSupplier   
 * @Description: TODO 添加供应商
 * @param: request
 * @param: projectBuildSupplier
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertSupplier",method=RequestMethod.POST)
public RetDataBean insertSupplier(HttpServletRequest request,ProjectBuildSupplier projectBuildSupplier)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildSupplier.setSupplierId(SysTools.getGUID());
		projectBuildSupplier.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildSupplier.setCreateUser(account.getAccountId());
		projectBuildSupplier.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",projectBuildSupplierService.insertProjectBuildSupplier(projectBuildSupplier));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateSupplier   
 * @Description: TODO 更新供应商信息
 * @param: request
 * @param: projectBuildSupplier
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateSupplier",method=RequestMethod.POST)
public RetDataBean updateSupplier(HttpServletRequest request,ProjectBuildSupplier projectBuildSupplier)
{
	try
	{
		if(StringUtils.isBlank(projectBuildSupplier.getSupplierId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildSupplier.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("supplierId",projectBuildSupplier.getSupplierId());
		return RetDataTools.Ok("请求成功!",projectBuildSupplierService.updateProjectBuildSupplier(example, projectBuildSupplier));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

}
