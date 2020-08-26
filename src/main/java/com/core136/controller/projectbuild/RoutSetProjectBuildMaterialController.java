package com.core136.controller.projectbuild;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.projectbuild.ProjectBuildMaterial;
import com.core136.bean.projectbuild.ProjectBuildMaterialIn;
import com.core136.bean.projectbuild.ProjectBuildMaterialMx;
import com.core136.bean.projectbuild.ProjectBuildMaterialOut;
import com.core136.bean.projectbuild.ProjectBuildMaterialPurchase;
import com.core136.bean.projectbuild.ProjectBuildMaterialSort;
import com.core136.bean.projectbuild.ProjectBuildMaterialStage;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildMaterialInService;
import com.core136.service.projectbuild.ProjectBuildMaterialMxService;
import com.core136.service.projectbuild.ProjectBuildMaterialOutService;
import com.core136.service.projectbuild.ProjectBuildMaterialPurchaseService;
import com.core136.service.projectbuild.ProjectBuildMaterialService;
import com.core136.service.projectbuild.ProjectBuildMaterialSortService;
import com.core136.service.projectbuild.ProjectBuildMaterialStageService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @ClassName:  RoutSetProjectBuildController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云技术 
 * @date:   2019年9月8日 下午7:46:51   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@RestController
@RequestMapping("/set/projectbuildmaterialset")
public class RoutSetProjectBuildMaterialController {
@Autowired
private ProjectBuildMaterialSortService projectBuildMaterialSortService;
@Autowired
private ProjectBuildMaterialService projectBuildMaterialService;
@Autowired
private ProjectBuildMaterialStageService projectBuildMaterialStageService;
@Autowired
private ProjectBuildMaterialPurchaseService projectBuildMaterialPurchaseService;
@Autowired
private ProjectBuildMaterialMxService projectBuildMaterialMxService;
@Autowired
private ProjectBuildMaterialInService projectBuildMaterialInService;
@Autowired
private ProjectBuildMaterialOutService projectBuildMaterialOutService;
@Autowired
private AccountService accountService;

/**
 * 
 * @Title: delMaterialSort   
 * @Description: TODO 删除工程材料分类
 * @param: request
 * @param: projectBuildMaterialSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/delMaterialSort",method=RequestMethod.POST)
public RetDataBean delMaterialSort(HttpServletRequest request,ProjectBuildMaterialSort projectBuildMaterialSort)
{
	try
	{
		if(StringUtils.isBlank(projectBuildMaterialSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterialSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除成功!",projectBuildMaterialSortService.deleteProjectBuildMaterialSort(projectBuildMaterialSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertMaterialSort   
 * @Description: TODO 添加工程材料分类
 * @param: request
 * @param: projectBuildMaterialSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMaterialSort",method=RequestMethod.POST)
public RetDataBean insertMaterialSort(HttpServletRequest request,ProjectBuildMaterialSort projectBuildMaterialSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterialSort.setSortId(SysTools.getGUID());
		projectBuildMaterialSort.getSortLeave();
		projectBuildMaterialSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildMaterialSort.setCreateUser(account.getAccountId());
		if(StringUtils.isBlank(projectBuildMaterialSort.getSortLeave()))
		{
			projectBuildMaterialSort.setSortLeave("0");
		}
		projectBuildMaterialSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加成功!",projectBuildMaterialSortService.insertProjectBuildMaterialSort(projectBuildMaterialSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateMaterialSort   
 * @Description: TODO 更新工程材料分类
 * @param: request
 * @param: projectBuildMaterialSort
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */

@RequestMapping(value="/updateMaterialSort",method=RequestMethod.POST)
public RetDataBean updateMaterialSort(HttpServletRequest request,ProjectBuildMaterialSort projectBuildMaterialSort)
{
	try
	{
		if(StringUtils.isBlank(projectBuildMaterialSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildMaterialSort.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("sortId",projectBuildMaterialSort.getSortId());
		return RetDataTools.Ok("更新成功!",projectBuildMaterialSortService.updateProjectBuildMaterialSort(example,projectBuildMaterialSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertMaterial   
 * @Description: TODO 添加材料
 * @param: request
 * @param: projectBuildMaterial
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMaterial",method=RequestMethod.POST)
public RetDataBean insertMaterial(HttpServletRequest request,ProjectBuildMaterial projectBuildMaterial)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterial.setMaterialId(SysTools.getGUID());
		projectBuildMaterial.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildMaterial.setCreateUser(account.getAccountId());
		projectBuildMaterial.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加成功!",projectBuildMaterialService.insertProjectBuildMaterial(projectBuildMaterial));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: delMaterial   
 * @Description: TODO 删除材料
 * @param: request
 * @param: projectBuildMaterial
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/delMaterial",method=RequestMethod.POST)
public RetDataBean delMaterial(HttpServletRequest request,ProjectBuildMaterial projectBuildMaterial)
{
	try
	{
		if(StringUtils.isBlank(projectBuildMaterial.getMaterialId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterial.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除成功!",projectBuildMaterialService.deleteProjectBuildMaterial(projectBuildMaterial));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateMaterial   
 * @Description: TODO 更新材料信息
 * @param: request
 * @param: projectBuildMaterial
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateMaterial",method=RequestMethod.POST)
public RetDataBean updateMaterial(HttpServletRequest request,ProjectBuildMaterial projectBuildMaterial)
{
	try
	{
		if(StringUtils.isBlank(projectBuildMaterial.getMaterialId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildMaterial.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("materialId",projectBuildMaterial.getMaterialId());
		return RetDataTools.Ok("更新成功!",projectBuildMaterialService.updateProjectBuildMaterial(example,projectBuildMaterial));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: insertMaterialStage   
 * @Description: TODO 添加材料预警数量
 * @param: request
 * @param: projectBuildMaterialStage
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMaterialStage",method=RequestMethod.POST)
public RetDataBean insertMaterialStage(HttpServletRequest request,ProjectBuildMaterialStage projectBuildMaterialStage)
{
	try
	{
		
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterialStage.setMaterialStageId(SysTools.getGUID());
		projectBuildMaterialStage.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildMaterialStage.setCreateUser(account.getAccountId());
		projectBuildMaterialStage.setOrgId(account.getOrgId());
		int i=projectBuildMaterialStageService.isExistChild(account.getOrgId(), projectBuildMaterialStage.getStageId(), projectBuildMaterialStage.getMaterialId());
		if(i>0)
		{
			return RetDataTools.NotOk("添加的材料已存在，不能添加，只能修改数量!");
		}else
		{
		return RetDataTools.Ok("添加成功!",projectBuildMaterialStageService.insertProjectBuildMaterialStage(projectBuildMaterialStage));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: delMaterialStage   
 * @Description: TODO 删除材料预警记录
 * @param: request
 * @param: projectBuildMaterialStage
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/delMaterialStage",method=RequestMethod.POST)
public RetDataBean delMaterialStage(HttpServletRequest request,ProjectBuildMaterialStage projectBuildMaterialStage)
{
	try
	{
		if(StringUtils.isBlank(projectBuildMaterialStage.getMaterialStageId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterialStage.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除成功!",projectBuildMaterialStageService.deleteProjectBuildMaterialStage(projectBuildMaterialStage));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateMaterialStage   
 * @Description: TODO 更新材料预警记录
 * @param: request
 * @param: projectBuildMaterialStage
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateMaterialStage",method=RequestMethod.POST)
public RetDataBean updateMaterialStage(HttpServletRequest request,ProjectBuildMaterialStage projectBuildMaterialStage)
{
	try
	{
		if(StringUtils.isBlank(projectBuildMaterialStage.getMaterialStageId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildMaterialStage.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("materialStageId",projectBuildMaterialStage.getMaterialStageId());
		return RetDataTools.Ok("更新成功!",projectBuildMaterialStageService.updateProjectBuildMaterialStage(example,projectBuildMaterialStage));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertMaterialPurchase   
 * @Description: TODO 创建采购申请
 * @param: request
 * @param: projectBuildMaterialPurchase
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMaterialPurchase",method=RequestMethod.POST)
public RetDataBean insertMaterialPurchase(HttpServletRequest request,ProjectBuildMaterialPurchase projectBuildMaterialPurchase,String  materialMx)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterialPurchase.setPurchaseId(SysTools.getGUID());
		projectBuildMaterialPurchase.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildMaterialPurchase.setCreateUser(account.getAccountId());
		projectBuildMaterialPurchase.setOrgId(account.getOrgId());
		return projectBuildMaterialPurchaseService.purchaseAndSendBpm(projectBuildMaterialPurchase,materialMx);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: delMaterialPurchase   
 * @Description: TODO 删除材料申请
 * @param: request
 * @param: projectBuildMaterialPurchase
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/delMaterialPurchase",method=RequestMethod.POST)
public RetDataBean delMaterialPurchase(HttpServletRequest request,ProjectBuildMaterialPurchase projectBuildMaterialPurchase)
{
	try
	{
		if(StringUtils.isBlank(projectBuildMaterialPurchase.getSupplierId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterialPurchase.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除成功!",projectBuildMaterialPurchaseService.deleteProjectBuildMaterialPurchase(projectBuildMaterialPurchase));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateMaterialPurchase   
 * @Description: TODO 更新材料采购申请
 * @param: request
 * @param: projectBuildMaterialPurchase
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateMaterialPurchase",method=RequestMethod.POST)
public RetDataBean updateMaterialPurchase(HttpServletRequest request,ProjectBuildMaterialPurchase projectBuildMaterialPurchase)
{
	try
	{
		if(StringUtils.isBlank(projectBuildMaterialPurchase.getPurchaseId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildMaterialPurchase.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("purchaseId",projectBuildMaterialPurchase.getPurchaseId());
		return RetDataTools.Ok("更新成功!",projectBuildMaterialPurchaseService.updateProjectBuildMaterialPurchase(example,projectBuildMaterialPurchase));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: insertMaterialMx   
 * @Description: TODO 添加材料采购申请明细
 * @param: request
 * @param: projectBuildMaterialMx
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMaterialMx",method=RequestMethod.POST)
public RetDataBean insertMaterialMx(HttpServletRequest request,ProjectBuildMaterialMx projectBuildMaterialMx)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterialMx.setMaterialMxId(SysTools.getGUID());
		projectBuildMaterialMx.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildMaterialMx.setCreateUser(account.getAccountId());
		projectBuildMaterialMx.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建成功!",projectBuildMaterialMxService.insertProjectBuildMaterialMx(projectBuildMaterialMx));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: delMaterialMx   
 * @Description: TODO 删除材料采购明细
 * @param: request
 * @param: projectBuildMaterialMx
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/delMaterialMx",method=RequestMethod.POST)
public RetDataBean delMaterialMx(HttpServletRequest request,ProjectBuildMaterialMx projectBuildMaterialMx)
{
	try
	{
		if(StringUtils.isBlank(projectBuildMaterialMx.getMaterialMxId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterialMx.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除成功!",projectBuildMaterialMxService.deleteProjectBuildMaterialMx(projectBuildMaterialMx));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateMaterialMx   
 * @Description: TODO 更新材料采购明细
 * @param: request
 * @param: projectBuildMaterialMx
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateMaterialMx",method=RequestMethod.POST)
public RetDataBean updateMaterialMx(HttpServletRequest request,ProjectBuildMaterialMx projectBuildMaterialMx)
{
	try
	{
		if(StringUtils.isBlank(projectBuildMaterialMx.getMaterialMxId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ProjectBuildMaterialMx.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("materialMxId",projectBuildMaterialMx.getMaterialMxId());
		return RetDataTools.Ok("更新成功!",projectBuildMaterialMxService.updateProjectBuildMaterialMx(example,projectBuildMaterialMx));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: insertMaterialIn   
 * @Description: TODO 材料入库
 * @param: request
 * @param: projectBuildMaterialIn
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMaterialIn",method=RequestMethod.POST)
public RetDataBean insertMaterialIn(HttpServletRequest request,ProjectBuildMaterialIn projectBuildMaterialIn)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterialIn.setInId(SysTools.getGUID());
		projectBuildMaterialIn.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildMaterialIn.setCreateUser(account.getAccountId());
		projectBuildMaterialIn.setOrgId(account.getOrgId());
		return RetDataTools.Ok("入库成功!",projectBuildMaterialInService.insertProjectBuildMaterialIn(projectBuildMaterialIn));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertMaterialOut   
 * @Description: TODO 添加出库记录
 * @param: request
 * @param: projectBuildMaterialIOut
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMaterialOut",method=RequestMethod.POST)
public RetDataBean insertMaterialOut(HttpServletRequest request,ProjectBuildMaterialOut projectBuildMaterialIOut)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		projectBuildMaterialIOut.setOutId(SysTools.getGUID());
		projectBuildMaterialIOut.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildMaterialIOut.setCreateUser(account.getAccountId());
		projectBuildMaterialIOut.setOrgId(account.getOrgId());
		return RetDataTools.Ok("出库成功!",projectBuildMaterialOutService.insertProjectBuildMaterialOut(projectBuildMaterialIOut));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


}
