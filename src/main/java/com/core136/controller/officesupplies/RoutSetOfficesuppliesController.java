/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetOfficesuppliesController.java   
 * @Package com.core136.controller.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月31日 下午2:22:12   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.officesupplies;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.officesupplies.OfficeSupplies;
import com.core136.bean.officesupplies.OfficeSuppliesApply;
import com.core136.bean.officesupplies.OfficeSuppliesApproval;
import com.core136.bean.officesupplies.OfficeSuppliesGrant;
import com.core136.bean.officesupplies.OfficeSuppliesSort;
import com.core136.bean.officesupplies.OfficeSuppliesUnit;
import com.core136.service.account.AccountService;
import com.core136.service.officesupplies.OfficeSuppliesApplyService;
import com.core136.service.officesupplies.OfficeSuppliesApprovalService;
import com.core136.service.officesupplies.OfficeSuppliesGrantService;
import com.core136.service.officesupplies.OfficeSuppliesService;
import com.core136.service.officesupplies.OfficeSuppliesSortService;
import com.core136.service.officesupplies.OfficeSuppliesUnitService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/set/officesuppliesset")
public class RoutSetOfficesuppliesController {
	@Autowired
	private OfficeSuppliesSortService officeSuppliesSortService;
	@Autowired
	private OfficeSuppliesService officeSuppliesService;
	@Autowired
	private OfficeSuppliesUnitService officeSuppliesUnitService;
	@Autowired
	private OfficeSuppliesApprovalService officeSuppliesApprovalService;
	@Autowired
	private OfficeSuppliesApplyService officeSuppliesApplyService;
	@Autowired
	private OfficeSuppliesGrantService officeSuppliesGrantService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: insertOfficeSuppliesGrant   
	 * @Description: TODO 添加发放记录  
	 * @param: request
	 * @param: officeSuppliesGrant
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertOfficeSuppliesGrant",method=RequestMethod.POST)
	public RetDataBean insertOfficeSuppliesGrant(HttpServletRequest request,OfficeSuppliesGrant officeSuppliesGrant)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			OfficeSuppliesApply officeSuppliesApply = new OfficeSuppliesApply();
			officeSuppliesApply.setOrgId(account.getOrgId());
			officeSuppliesApply.setApplyId(officeSuppliesGrant.getApplyId());
			officeSuppliesApply = officeSuppliesApplyService.selectOneOfficeSuppliesApply(officeSuppliesApply);
			int quantity = officeSuppliesApply.getQuantity();
			if(officeSuppliesGrantService.getGrantCount(account.getOrgId(), officeSuppliesGrant.getApplyId())<quantity)
			{
				officeSuppliesGrant.setGrantId(SysTools.getGUID());
				officeSuppliesGrant.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
				officeSuppliesGrant.setCreateUser(account.getAccountId());
				officeSuppliesGrant.setOrgId(account.getOrgId());
				return RetDataTools.Ok("发放办公用品成功!",officeSuppliesGrantService.insertOfficeSuppliesGrant(officeSuppliesGrant));
			}else
			{
				return RetDataTools.NotOk("办公用品已足量发放！");
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertOfficeSuppliesApproval   
	 * @Description: TODO 添加审批记录   
	 * @param: request
	 * @param: officeSuppliesApproval
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertOfficeSuppliesApproval",method=RequestMethod.POST)
	public RetDataBean insertOfficeSuppliesApproval(HttpServletRequest request,OfficeSuppliesApproval officeSuppliesApproval)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			officeSuppliesApproval.setApprovalId(SysTools.getGUID());
			officeSuppliesApproval.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			officeSuppliesApproval.setCreateUser(account.getAccountId());
			officeSuppliesApproval.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",officeSuppliesApprovalService.approvalOfficeSuppliesApply(officeSuppliesApproval));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteOfficeSuppliesApproval   
	 * @Description: TODO 删除审批记录
	 * @param: request
	 * @param: officeSuppliesApproval
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteOfficeSuppliesApproval",method=RequestMethod.POST)
	public RetDataBean deleteOfficeSuppliesApproval(HttpServletRequest request,OfficeSuppliesApproval officeSuppliesApproval)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(officeSuppliesApproval.getApprovalId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			officeSuppliesApproval.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",officeSuppliesApprovalService.deleteOfficeSuppliesApproval(officeSuppliesApproval));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateofficeSuppliesApproval   
	 * @Description: TODO 更新审批记录
	 * @param: request
	 * @param: officeSuppliesApproval
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateofficeSuppliesApproval",method=RequestMethod.POST)
	public RetDataBean updateofficeSuppliesApproval(HttpServletRequest request,OfficeSuppliesApproval officeSuppliesApproval)
	{
		try
		{
			if(StringUtils.isBlank(officeSuppliesApproval.getApprovalId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(OfficeSuppliesApproval.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("approvalId",officeSuppliesApproval.getApprovalId());
			return RetDataTools.Ok("更新成功!",officeSuppliesApprovalService.updateOfficeSuppliesApproval(example,officeSuppliesApproval));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertOfficeSuppliesApply   
	 * @Description: TODO 发起申请  
	 * @param: request
	 * @param: officeSuppliesApply
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertOfficeSuppliesApply",method=RequestMethod.POST)
	public RetDataBean insertOfficeSuppliesApply(HttpServletRequest request,OfficeSuppliesApply officeSuppliesApply)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			officeSuppliesApply.setApplyId(SysTools.getGUID());
			officeSuppliesApply.setStatus("0");
			officeSuppliesApply.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			officeSuppliesApply.setCreateUser(account.getAccountId());
			officeSuppliesApply.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",officeSuppliesApplyService.insertOfficeSuppliesApply(officeSuppliesApply));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteOfficeSuppliesApply   
	 * @Description: TODO 删除申请
	 * @param: request
	 * @param: officeSuppliesApply
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteOfficeSuppliesApply",method=RequestMethod.POST)
	public RetDataBean deleteOfficeSuppliesApply(HttpServletRequest request,OfficeSuppliesApply officeSuppliesApply)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(officeSuppliesApply.getApplyId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			officeSuppliesApply.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",officeSuppliesApplyService.deleteOfficeSuppliesApply(officeSuppliesApply));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateOfficeSuppliesApply   
	 * @Description: TODO 更新申请   
	 * @param: request
	 * @param: officeSuppliesApply
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateOfficeSuppliesApply",method=RequestMethod.POST)
	public RetDataBean updateOfficeSuppliesApply(HttpServletRequest request,OfficeSuppliesApply officeSuppliesApply)
	{
		try
		{
			if(StringUtils.isBlank(officeSuppliesApply.getApplyId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(OfficeSuppliesApply.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("applyId",officeSuppliesApply.getApplyId());
			return RetDataTools.Ok("更新成功!",officeSuppliesApplyService.updateOfficeSuppliesApply(example,officeSuppliesApply));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertOfficeSuppliesUnit   
	 * @Description: TODO 添加办公用品计量单位 
	 * @param: request
	 * @param: officeSuppliesUnit
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertOfficeSuppliesUnit",method=RequestMethod.POST)
	public RetDataBean insertOfficeSuppliesUnit(HttpServletRequest request,OfficeSuppliesUnit officeSuppliesUnit)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			officeSuppliesUnit.setUnitId(SysTools.getGUID());
			officeSuppliesUnit.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			officeSuppliesUnit.setCreateUser(account.getAccountId());
			officeSuppliesUnit.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",officeSuppliesUnitService.insertOfficeSuppliesUnit(officeSuppliesUnit));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/deleteOfficeSuppliesUnit",method=RequestMethod.POST)
	public RetDataBean deleteOfficeSuppliesUnit(HttpServletRequest request,OfficeSuppliesUnit officeSuppliesUnit)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(officeSuppliesUnit.getUnitId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			officeSuppliesUnit.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",officeSuppliesUnitService.deleteOfficeSuppliesUnit(officeSuppliesUnit));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/updateOfficeSuppliesUnit",method=RequestMethod.POST)
	public RetDataBean updateOfficeSuppliesUnit(HttpServletRequest request,OfficeSuppliesUnit officeSuppliesUnit)
	{
		try
		{
			if(StringUtils.isBlank(officeSuppliesUnit.getUnitId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(OfficeSuppliesUnit.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("unitId",officeSuppliesUnit.getUnitId());
			return RetDataTools.Ok("更新成功!",officeSuppliesUnitService.updateOfficeSuppliesUnit(example,officeSuppliesUnit));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/insertOfficeSuppliesSort",method=RequestMethod.POST)
	public RetDataBean insertOfficeSuppliesSort(HttpServletRequest request,OfficeSuppliesSort officeSuppliesSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			officeSuppliesSort.setSortId(SysTools.getGUID());
			officeSuppliesSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			officeSuppliesSort.setCreateUser(account.getAccountId());
			officeSuppliesSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",officeSuppliesSortService.insertOfficeSuppliesSort(officeSuppliesSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/deleteOfficeSuppliesSort",method=RequestMethod.POST)
	public RetDataBean deleteOfficeSuppliesSort(HttpServletRequest request,OfficeSuppliesSort officeSuppliesSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(officeSuppliesSort.getSortId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			officeSuppliesSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",officeSuppliesSortService.deleteOfficeSuppliesSort(officeSuppliesSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/updateOfficeSuppliesSort",method=RequestMethod.POST)
	public RetDataBean updateOfficeSuppliesSort(HttpServletRequest request,OfficeSuppliesSort officeSuppliesSort)
	{
		try
		{
			if(StringUtils.isBlank(officeSuppliesSort.getSortId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(OfficeSuppliesSort.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("sortId",officeSuppliesSort.getSortId());
			return RetDataTools.Ok("更新成功!",officeSuppliesSortService.updateOfficeSuppliesSort(example,officeSuppliesSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertOfficeSupplies   
	 * @Description: TODO 添加办公用品   
	 * @param: request
	 * @param: officeSupplies
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertOfficeSupplies",method=RequestMethod.POST)
	public RetDataBean insertOfficeSupplies(HttpServletRequest request,OfficeSupplies officeSupplies)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			officeSupplies.setSuppliesId(SysTools.getGUID());
			officeSupplies.setStatus("0");
			officeSupplies.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			officeSupplies.setCreateUser(account.getAccountId());
			officeSupplies.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",officeSuppliesService.insertOfficeSupplies(officeSupplies));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteOfficeSupplies   
	 * @Description: TODO 删除办公用品
	 * @param: request
	 * @param: officeSupplies
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteOfficeSupplies",method=RequestMethod.POST)
	public RetDataBean deleteOfficeSupplies(HttpServletRequest request,OfficeSupplies officeSupplies)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(officeSupplies.getSuppliesId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			officeSupplies.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",officeSuppliesService.deleteOfficeSupplies(officeSupplies));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateOfficeSupplies   
	 * @Description: TODO 更新办公用品信息   
	 * @param: request
	 * @param: officeSupplies
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateOfficeSupplies",method=RequestMethod.POST)
	public RetDataBean updateOfficeSupplies(HttpServletRequest request,OfficeSupplies officeSupplies)
	{
		try
		{
			if(StringUtils.isBlank(officeSupplies.getSuppliesId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(OfficeSupplies.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("suppliesId",officeSupplies.getSuppliesId());
			return RetDataTools.Ok("更新成功!",officeSuppliesService.updateOfficeSupplies(example,officeSupplies));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}

}
