/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetOfficesuppliesController.java   
 * @Package com.core136.controller.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月31日 下午2:22:30   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.officesupplies;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.officesupplies.OfficeSupplies;
import com.core136.bean.officesupplies.OfficeSuppliesApply;
import com.core136.bean.officesupplies.OfficeSuppliesApproval;
import com.core136.bean.officesupplies.OfficeSuppliesSort;
import com.core136.bean.officesupplies.OfficeSuppliesUnit;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.officesupplies.OfficeSuppliesApplyService;
import com.core136.service.officesupplies.OfficeSuppliesApprovalService;
import com.core136.service.officesupplies.OfficeSuppliesService;
import com.core136.service.officesupplies.OfficeSuppliesSortService;
import com.core136.service.officesupplies.OfficeSuppliesUnitService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/ret/officesuppliesget")
public class RoutGetOfficesuppliesController {
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
	private AccountService accountService;
	/**
	 * 
	 * @Title: getApplyOfficeSuppliesList   
	 * @Description: TODO 获取审批列表 
	 * @param: request
	 * @param: begTime
	 * @param: endTime
	 * @param: status
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getApplyOfficeSuppliesList",method=RequestMethod.POST)
	public RetDataBean getApplyOfficeSuppliesList(
			HttpServletRequest request,
			String begTime,
			String endTime,
			String status,
			PageParam pageParam
			)
		{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("A.CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=officeSuppliesApplyService.getApplyOfficeSuppliesList(pageParam,begTime,endTime,status);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
/**
 * 	
 * @Title: getGrantOfficeSuppliesList   
 * @Description: TODO 待发放用品列表   
 * @param: request
 * @param: begTime
 * @param: endTime
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
	@RequestMapping(value="/getGrantOfficeSuppliesList",method=RequestMethod.POST)
	public RetDataBean getGrantOfficeSuppliesList(
			HttpServletRequest request,
			String begTime,
			String endTime,
			PageParam pageParam
			)
		{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("A.CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=officeSuppliesApplyService.getGrantOfficeSuppliesList(pageParam,begTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMyApplyOfficeSuppliesList   
	 * @Description: TODO 获取个人历史申请记录 
	 * @param: request
	 * @param: begTime
	 * @param: endTime
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMyApplyOfficeSuppliesList",method=RequestMethod.POST)
	public RetDataBean getMyApplyOfficeSuppliesList(
			HttpServletRequest request,
			String begTime,
			String endTime,
			String status,
			PageParam pageParam
			)
		{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("A.CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		PageInfo<Map<String, String>> pageInfo=officeSuppliesApplyService.getMyApplyOfficeSuppliesList(pageParam,begTime,endTime,status);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getOfficeSuppliesApplyServiceById   
	 * @Description: TODO 获取申请详情
	 * @param: request
	 * @param: officeSuppliesApply
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getOfficeSuppliesApplyServiceById",method=RequestMethod.POST)
	public RetDataBean getOfficeSuppliesApplyServiceById(HttpServletRequest request,OfficeSuppliesApply officeSuppliesApply)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			officeSuppliesApply.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",officeSuppliesApplyService.selectOneOfficeSuppliesApply(officeSuppliesApply));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getOfficeSuppliesApprovalById   
	 * @Description: TODO 获取审批详情 
	 * @param: request
	 * @param: officeSuppliesApproval
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getOfficeSuppliesApprovalById",method=RequestMethod.POST)
	public RetDataBean getOfficeSuppliesApprovalById(HttpServletRequest request,OfficeSuppliesApproval officeSuppliesApproval)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			officeSuppliesApproval.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",officeSuppliesApprovalService.selectOneOfficeSuppliesApproval(officeSuppliesApproval));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getApplyOfficeSupplieslist   
	 * @Description: TODO 获取可以领用的办公用品列表   
	 * @param: request
	 * @param: sortId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getApplyOfficeSupplieslist",method=RequestMethod.POST)
	public RetDataBean getApplyOfficeSupplieslist(
			HttpServletRequest request,
			String sortId,
			PageParam pageParam
			)
		{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("SORT_NO");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			UserInfo userInfo = accountService.getRedisUserInfo(request);
		pageParam.setOrgId(userInfo.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=officeSuppliesService.getApplyOfficeSupplieslist(pageParam,sortId,userInfo.getDeptId());
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getOfficeSupplieslistBySortId   
	 * @Description: TODO 获取办公用品列表
	 * @param: request
	 * @param: sortId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getOfficeSupplieslistBySortId",method=RequestMethod.POST)
	public RetDataBean getOfficeSupplieslistBySortId(
			HttpServletRequest request,
			String sortId,
			PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("SORT_NO");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=officeSuppliesService.getOfficeSupplieslistBySortId(pageParam,sortId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getOfficeSuppliesUnitList   
	 * @Description: TODO 获取办公用品计量单位列表
	 * @param: request
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getOfficeSuppliesUnitList",method=RequestMethod.POST)
	public RetDataBean getOfficeSuppliesUnitList(
			HttpServletRequest request,
			PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("SORT_NO");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=officeSuppliesUnitService.getOfficeSuppliesUnitList(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getofficeSuppliesUnitById   
	 * @Description: TODO 获取办公用品计量单位详情  
	 * @param: request
	 * @param: officeSuppliesUnit
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getofficeSuppliesUnitById",method=RequestMethod.POST)
	public RetDataBean getofficeSuppliesUnitById(HttpServletRequest request,OfficeSuppliesUnit officeSuppliesUnit)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			officeSuppliesUnit.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",officeSuppliesUnitService.selectOneOfficeSuppliesUnit(officeSuppliesUnit));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getAllUnit   
	 * @Description: TODO 获取办公用品所有的计量单位  
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getAllUnit",method=RequestMethod.POST)
	public RetDataBean getAllUnit(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!",officeSuppliesUnitService.getAllUnit(account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getOfficeSuppliesSortTree   
	 * @Description: TODO获取办公用品分类
	 * @param: request
	 * @param: sortId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	@RequestMapping(value="/getOfficeSuppliesSortTree",method=RequestMethod.POST)
	public List<Map<String,String>> getOfficeSuppliesSortTree(HttpServletRequest request,String sortId)
	{
		try
		{
			String parentId = "0";
			if(StringUtils.isNotBlank(sortId))
			{
				parentId = sortId;
			}
			Account account=accountService.getRedisAccount(request);
			return officeSuppliesSortService.getOfficeSuppliesSortTree(account.getOrgId(), parentId);
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getofficeSuppliesById   
	 * @Description: TODO 获取办公用品详情
	 * @param: request
	 * @param: officeSupplies
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getofficeSuppliesById",method=RequestMethod.POST)
	public RetDataBean getofficeSuppliesById(HttpServletRequest request,OfficeSupplies officeSupplies)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			officeSupplies.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",officeSuppliesService.selectOneOfficeSupplies(officeSupplies));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getofficeSuppliesSortById   
	 * @Description: TODO 获取分类详情
	 * @param: request
	 * @param: officeSuppliesSort
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getofficeSuppliesSortById",method=RequestMethod.POST)
	public RetDataBean getofficeSuppliesSortById(HttpServletRequest request,OfficeSuppliesSort officeSuppliesSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			officeSuppliesSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",officeSuppliesSortService.selectOneOfficeSuppliesSort(officeSuppliesSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
