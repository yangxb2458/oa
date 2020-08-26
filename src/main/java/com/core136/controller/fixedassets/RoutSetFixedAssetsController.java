/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetFixedassetController.java   
 * @Package com.core136.controller.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月25日 下午5:19:06   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.fixedassets;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.fixedassets.FixedAssets;
import com.core136.bean.fixedassets.FixedAssetsApply;
import com.core136.bean.fixedassets.FixedAssetsApproval;
import com.core136.bean.fixedassets.FixedAssetsRepair;
import com.core136.bean.fixedassets.FixedAssetsSort;
import com.core136.bean.fixedassets.FixedAssetsStorage;
import com.core136.service.account.AccountService;
import com.core136.service.fixedassets.FixedAssetsApplyService;
import com.core136.service.fixedassets.FixedAssetsApprovalService;
import com.core136.service.fixedassets.FixedAssetsRepairService;
import com.core136.service.fixedassets.FixedAssetsService;
import com.core136.service.fixedassets.FixedAssetsSortService;
import com.core136.service.fixedassets.FixedAssetsStorageService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/set/fixedassetsset")
public class RoutSetFixedAssetsController {
	@Autowired
	private FixedAssetsSortService fixedAssetsSortService;
	@Autowired
	private FixedAssetsService fixedAssetsService;
	@Autowired
	private FixedAssetsStorageService fixedAssetsStorageService;
	@Autowired
	private FixedAssetsApplyService fixedAssetApplayService;
	@Autowired
	private FixedAssetsApprovalService fixedAssetsApprovalService;
	@Autowired
	private FixedAssetsRepairService fixedAssetsRepairService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: insertFixedAssetsRepair   
	 * @Description: TODO 发起维修
	 * @param: request
	 * @param: fixedAssetsRepair
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertFixedAssetsRepair",method=RequestMethod.POST)
	public RetDataBean insertFixedAssetsRepair(HttpServletRequest request,FixedAssetsRepair fixedAssetsRepair)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			fixedAssetsRepair.setRepairId(SysTools.getGUID());
			fixedAssetsRepair.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			fixedAssetsRepair.setCreateUser(account.getAccountId());
			fixedAssetsRepair.setOrgId(account.getOrgId());
			return RetDataTools.Ok("审批成功!",fixedAssetsRepairService.insertFixedAssetsRepair(fixedAssetsRepair));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteFixedAssetsRepair   
	 * @Description: TODO 删除维修 
	 * @param: request
	 * @param: fixedAssetsRepair
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteFixedAssetsRepair",method=RequestMethod.POST)
	public RetDataBean deleteFixedAssetsRepair(HttpServletRequest request,FixedAssetsRepair fixedAssetsRepair)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(fixedAssetsRepair.getRepairId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			fixedAssetsRepair.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",fixedAssetsRepairService.deleteFixedAssetsRepair(fixedAssetsRepair));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateFixedAssetsRepair   
	 * @Description: TODO 更新维修记录  
	 * @param: request
	 * @param: fixedAssetsRepair
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateFixedAssetsRepair",method=RequestMethod.POST)
	public RetDataBean updateFixedAssetsRepair(HttpServletRequest request,FixedAssetsRepair fixedAssetsRepair)
	{
		try
		{
			if(StringUtils.isBlank(fixedAssetsRepair.getRepairId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(FixedAssetsRepair.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("repairId",fixedAssetsRepair.getRepairId());
			return RetDataTools.Ok("更新成功!",fixedAssetsRepairService.updateFixedAssetsRepair(example,fixedAssetsRepair));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}	
	
	/**
	 * 
	 * @Title: updateFixedAssetsApply   
	 * @Description: TODO 更新审批意见
	 * @param: request
	 * @param: fixedAssetsApply
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateFixedAssetsApproval",method=RequestMethod.POST)
	public RetDataBean updateFixedAssetsApproval(HttpServletRequest request,FixedAssetsApproval fixedAssetsApproval)
	{
		try
		{
			if(StringUtils.isBlank(fixedAssetsApproval.getApprovalId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(FixedAssetsApproval.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("approvalId",fixedAssetsApproval.getApprovalId());
			return RetDataTools.Ok("更新成功!",fixedAssetsApprovalService.updateFixedAssetsApproval(example,fixedAssetsApproval));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}	
	
	/**
	 * 
	 * @Title: deleteFixedAssetsApply   
	 * @Description: TODO 册除审批意见
	 * @param: request
	 * @param: fixedAssetsApply
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteFixedAssetsApproval",method=RequestMethod.POST)
	public RetDataBean deleteFixedAssetsApproval(HttpServletRequest request,FixedAssetsApproval fixedAssetsApproval)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(fixedAssetsApproval.getApprovalId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			if(!account.getOpFlag().equals("1"))
			{
				fixedAssetsApproval.setCreateUser(account.getAccountId());
			}
			fixedAssetsApproval.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",fixedAssetsApprovalService.deleteFixedAssetsApproval(fixedAssetsApproval));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertFixedAssetsApply   
	 * @Description: TODO 审批
	 * @param: request
	 * @param: fixedAssetsApply
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertFixedAssetsApproval",method=RequestMethod.POST)
	public RetDataBean insertFixedAssetsApproval(HttpServletRequest request,FixedAssetsApproval fixedAssetsApproval)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			fixedAssetsApproval.setApprovalId(SysTools.getGUID());
			fixedAssetsApproval.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			fixedAssetsApproval.setCreateUser(account.getAccountId());
			fixedAssetsApproval.setOrgId(account.getOrgId());
			return RetDataTools.Ok("审批成功!",fixedAssetsApprovalService.approveFixedAssets(fixedAssetsApproval));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: updateFixedAssetsApply   
	 * @Description: TODO 更新申请
	 * @param: request
	 * @param: fixedAssetsApply
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateFixedAssetsApply",method=RequestMethod.POST)
	public RetDataBean updateFixedAssetsApply(HttpServletRequest request,FixedAssetsApply fixedAssetsApply)
	{
		try
		{
			if(StringUtils.isBlank(fixedAssetsApply.getApplyId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(FixedAssetsApply.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("applyId",fixedAssetsApply.getApplyId());
			return RetDataTools.Ok("更新成功!",fixedAssetApplayService.updateFixedAssetsApply(example,fixedAssetsApply));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}	
	
	/**
	 * 
	 * @Title: deleteFixedAssetsApply   
	 * @Description: TODO 册除申请
	 * @param: request
	 * @param: fixedAssetsApply
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteFixedAssetsApply",method=RequestMethod.POST)
	public RetDataBean deleteFixedAssetsApply(HttpServletRequest request,FixedAssetsApply fixedAssetsApply)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(fixedAssetsApply.getApplyId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			fixedAssetsApply.setCreateUser(account.getAccountId());
			fixedAssetsApply.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",fixedAssetApplayService.deleteFixedAssetsApply(fixedAssetsApply));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertFixedAssetsApply   
	 * @Description: TODO 发起申请
	 * @param: request
	 * @param: fixedAssetsApply
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertFixedAssetsApply",method=RequestMethod.POST)
	public RetDataBean insertFixedAssetsApply(HttpServletRequest request,FixedAssetsApply fixedAssetsApply)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			fixedAssetsApply.setApplyId(SysTools.getGUID());
			fixedAssetsApply.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			fixedAssetsApply.setCreateUser(account.getAccountId());
			fixedAssetsApply.setOrgId(account.getOrgId());
			return RetDataTools.Ok("申请成功!",fixedAssetApplayService.insertFixedAssetsApply(fixedAssetsApply));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertFixedAssetsStorage   
	 * @Description: TODO创建仓库
	 * @param: request
	 * @param: fixedAssetsStorage
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertFixedAssetsStorage",method=RequestMethod.POST)
	public RetDataBean insertFixedAssetsStorage(HttpServletRequest request,FixedAssetsStorage fixedAssetsStorage)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			fixedAssetsStorage.setStorageId(SysTools.getGUID());
			fixedAssetsStorage.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			fixedAssetsStorage.setCreateUser(account.getAccountId());
			fixedAssetsStorage.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",fixedAssetsStorageService.insertFixedAssetsStorage(fixedAssetsStorage));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteFixedAssetsStorage   
	 * @Description: TODO删除仓库  
	 * @param: request
	 * @param: fixedAssetsStorage
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteFixedAssetsStorage",method=RequestMethod.POST)
	public RetDataBean deleteFixedAssetsStorage(HttpServletRequest request,FixedAssetsStorage fixedAssetsStorage)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(fixedAssetsStorage.getStorageId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			fixedAssetsStorage.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",fixedAssetsStorageService.deleteFixedAssetsStorage(fixedAssetsStorage));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: updateFixedAssetsStorage   
	 * @Description: TODO 更新仓库信息
	 * @param: request
	 * @param: fixedAssetsStorage
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateFixedAssetsStorage",method=RequestMethod.POST)
	public RetDataBean updateFixedAssetsStorage(HttpServletRequest request,FixedAssetsStorage fixedAssetsStorage)
	{
		try
		{
			if(StringUtils.isBlank(fixedAssetsStorage.getStorageId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(FixedAssetsStorage.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("storageId",fixedAssetsStorage.getStorageId());
			return RetDataTools.Ok("更新成功!",fixedAssetsStorageService.updateFixedAssetsStorage(example,fixedAssetsStorage));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}	
	
	
	@RequestMapping(value="/insertFixedAssets",method=RequestMethod.POST)
	public RetDataBean insertFixedAssets(HttpServletRequest request,FixedAssets fixedAssets)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			fixedAssets.setAssetsId(SysTools.getGUID());
			fixedAssets.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			fixedAssets.setCreateUser(account.getAccountId());
			fixedAssets.setStatus("0");
			fixedAssets.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",fixedAssetsService.insertFixedAssets(fixedAssets));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/deleteFixedAssets",method=RequestMethod.POST)
	public RetDataBean deleteFixedAssets(HttpServletRequest request,FixedAssets fixedAssets)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(fixedAssets.getAssetsId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			fixedAssets.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",fixedAssetsService.deleteFixedAssets(fixedAssets));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/updateFixedAssets",method=RequestMethod.POST)
	public RetDataBean updateFixedAssets(HttpServletRequest request,FixedAssets fixedAssets)
	{
		try
		{
			if(StringUtils.isBlank(fixedAssets.getAssetsId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(FixedAssets.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("assetsId",fixedAssets.getAssetsId());
			return RetDataTools.Ok("更新成功!",fixedAssetsService.updateFixedAssets(example,fixedAssets));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}	
	
	
	
	
	@RequestMapping(value="/insertFixedAssetsSort",method=RequestMethod.POST)
	public RetDataBean insertFixedAssetsSort(HttpServletRequest request,FixedAssetsSort fixedAssetsSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			fixedAssetsSort.setSortId(SysTools.getGUID());
			fixedAssetsSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			fixedAssetsSort.setCreateUser(account.getAccountId());
			fixedAssetsSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",fixedAssetsSortService.insertFixedAssetsSort(fixedAssetsSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/deleteFixedAssetsSort",method=RequestMethod.POST)
	public RetDataBean deleteFixedAssetsSort(HttpServletRequest request,FixedAssetsSort fixedAssetsSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(fixedAssetsSort.getSortId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			fixedAssetsSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",fixedAssetsSortService.deleteFixedAssetsSort(fixedAssetsSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/updateFixedAssetsSort",method=RequestMethod.POST)
	public RetDataBean updateFixedAssetsSort(HttpServletRequest request,FixedAssetsSort fixedAssetsSort)
	{
		try
		{
			if(StringUtils.isBlank(fixedAssetsSort.getSortId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(FixedAssetsSort.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("sortId",fixedAssetsSort.getSortId());
			return RetDataTools.Ok("更新成功!",fixedAssetsSortService.updateFixedAssetsSort(example,fixedAssetsSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
