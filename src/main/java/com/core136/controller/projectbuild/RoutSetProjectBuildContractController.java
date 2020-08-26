package com.core136.controller.projectbuild;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.projectbuild.ProjectBuildContract;
import com.core136.bean.projectbuild.ProjectBuildContractSort;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildContractService;
import com.core136.service.projectbuild.ProjectBuildContractSortService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/set/projectbuildset/contract")
public class RoutSetProjectBuildContractController {
@Autowired
private ProjectBuildContractSortService projectBuildContractSortService;
@Autowired
private ProjectBuildContractService projectBuildContractService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: delProjectBuildContractSort   
 * @Description: TODO 删除工程分类
 * @param: request
 * @param: projectBuildContractSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/delProjectBuildContractSort",method=RequestMethod.POST)
public RetDataBean delProjectBuildContractSort(HttpServletRequest request,ProjectBuildContractSort projectBuildContractSort)
{
	try
	{
		if(StringUtils.isBlank(projectBuildContractSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildContractSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除合同分类成功!",projectBuildContractSortService.deleteProjectBuildContractSort(projectBuildContractSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: insertProjectBuildContractSort   
 * @Description: TODO 添加分类 
 * @param: request
 * @param: projectBuildContractSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertProjectBuildContractSort",method=RequestMethod.POST)
public RetDataBean insertProjectBuildContractSort(HttpServletRequest request,ProjectBuildContractSort projectBuildContractSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildContractSort.setSortId(SysTools.getGUID());
		projectBuildContractSort.getSortLeave();
		projectBuildContractSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildContractSort.setCreateUser(account.getAccountId());
		if(StringUtils.isBlank(projectBuildContractSort.getSortLeave()))
		{
			projectBuildContractSort.setSortLeave("0");
		}
		projectBuildContractSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加合同分类成功!",projectBuildContractSortService.insertProjectBuildContractSort(projectBuildContractSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateProjectBuildContractSort   
 * @Description: TODO 更新合同分类
 * @param: request
 * @param: projectBuildContractSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateProjectBuildContractSort",method=RequestMethod.POST)
public RetDataBean updateProjectBuildContractSort(HttpServletRequest request,ProjectBuildContractSort projectBuildContractSort)
{
	try
	{
		if(StringUtils.isBlank(projectBuildContractSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildContractSort.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("sortId",projectBuildContractSort.getSortId());
		return RetDataTools.Ok("更新合同成功!",projectBuildContractSortService.updateProjectBuildContractSort(example,projectBuildContractSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: delProjectBuildContract   
 * @Description: TODO 删除合同 
 * @param: request
 * @param: projectBuildContract
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/delProjectBuildContract",method=RequestMethod.POST)
public RetDataBean delProjectBuildContract(HttpServletRequest request,ProjectBuildContract projectBuildContract)
{
	try
	{
		if(StringUtils.isBlank(projectBuildContract.getContractId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildContract.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除合同成功!",projectBuildContractService.deleteProjectBuildContract(projectBuildContract));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertProjectBuildContract   
 * @Description: TODO 创建合同
 * @param: request
 * @param: projectBuildContract
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertProjectBuildContract",method=RequestMethod.POST)
public RetDataBean insertProjectBuildContract(HttpServletRequest request,ProjectBuildContract projectBuildContract)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildContract.setContractId(SysTools.getGUID());
		projectBuildContract.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildContract.setCreateUser(account.getAccountId());
		projectBuildContract.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加合同成功!",projectBuildContractService.insertProjectBuildContract(projectBuildContract));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateProjectBuildContract   
 * @Description: TODO 更新合同  
 * @param: request
 * @param: projectBuildContract
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateProjectBuildContract",method=RequestMethod.POST)
public RetDataBean updateProjectBuildContract(HttpServletRequest request,ProjectBuildContract projectBuildContract)
{
	try
	{
		if(StringUtils.isBlank(projectBuildContract.getContractId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildContract.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("contractId",projectBuildContract.getContractId());
		return RetDataTools.Ok("更新合同成功!",projectBuildContractService.updateProjectBuildContract(example,projectBuildContract));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

}
