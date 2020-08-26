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
import com.core136.bean.projectbuild.ProjectBuildMaterial;
import com.core136.bean.projectbuild.ProjectBuildMaterialMx;
import com.core136.bean.projectbuild.ProjectBuildMaterialPurchase;
import com.core136.bean.projectbuild.ProjectBuildMaterialSort;
import com.core136.bean.projectbuild.ProjectBuildMaterialStage;
import com.core136.bean.sys.PageParam;
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
/**
 * 
 * @ClassName:  RoutGetProjectBuildController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云技术 
 * @date:   2019年9月8日 下午7:46:37   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;
@RestController
@RequestMapping("/ret/projectbuildmaterialget")
public class RoutGetProjectBuildMaterialController {
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
	 * @Title: getMaterialSortTree   
	 * @Description: TODO 获取工程材料分类树结构
	 * @param: request
	 * @param: sortId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	@RequestMapping(value="/getMaterialSortTree",method=RequestMethod.POST)
	public List<Map<String,String>> getMaterialSortTree(HttpServletRequest request,String sortId)
	{
		try
		{
			String sortLeave = "0";
			if(StringUtils.isNotBlank(sortId))
			{
				sortLeave = sortId;
			}
			Account account=accountService.getRedisAccount(request);
			return projectBuildMaterialSortService.getMaterialSortTree(account.getOrgId(), sortLeave);
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getMaterialSortById   
	 * @Description: TODO 获取工程材料分类详情
	 * @param: request
	 * @param: projectBuildMaterialSort
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMaterialSortById",method=RequestMethod.POST)
	public RetDataBean getMaterialSortById(HttpServletRequest request,ProjectBuildMaterialSort projectBuildMaterialSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildMaterialSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildMaterialSortService.selectOneProjectBuildMaterialSort(projectBuildMaterialSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getmateriallist   
	 * @Description: TODO 搂分类获取料材明细
	 * @param: request
	 * @param: sortId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getmateriallist",method=RequestMethod.POST)
	public RetDataBean getmateriallist(
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
		PageInfo<Map<String, String>> pageInfo=projectBuildMaterialService.getmateriallist(pageParam,sortId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getselect2materiallist   
	 * @Description: TODO 获取材料选择列表
	 * @param: request
	 * @param: search
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getselect2materiallist",method=RequestMethod.POST)
	public RetDataBean getselect2materiallist(HttpServletRequest request,String search)
	{
		try
		{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求数据成功!", projectBuildMaterialService.getMaterialListForSelet2(account.getOrgId(),"%"+search+"%"));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getMaterialById   
	 * @Description: TODO 获取工程材料详情
	 * @param: request
	 * @param: projectBuildMaterial
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMaterialById",method=RequestMethod.POST)
	public RetDataBean getMaterialById(HttpServletRequest request,ProjectBuildMaterial projectBuildMaterial)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildMaterial.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildMaterialService.selectOneProjectBuildMaterial(projectBuildMaterial));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMaterialStageById   
	 * @Description: TODO 获取材料预警详情
	 * @param: request
	 * @param: projectBuildMaterialStage
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMaterialStageById",method=RequestMethod.POST)
	public RetDataBean getMaterialStageById(HttpServletRequest request,ProjectBuildMaterialStage projectBuildMaterialStage)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildMaterialStage.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildMaterialStageService.selectOneProjectBuildMaterialStage(projectBuildMaterialStage));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMaterialListInStage   
	 * @Description: TODO 获取当前节点可选择的材料列表
	 * @param: request
	 * @param: stageId
	 * @param: search
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMaterialListInStage",method=RequestMethod.POST)
	public RetDataBean getMaterialListInStage(HttpServletRequest request,String stageId,String search)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!",projectBuildMaterialStageService.getMaterialListInStage(account.getOrgId(),stageId,search));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getmaterialStagelist   
	 * @Description: TODO 获取工程节点材料预警列表
	 * @param: request
	 * @param: stageId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getmaterialStagelist",method=RequestMethod.POST)
	public RetDataBean getmaterialStagelist(
			HttpServletRequest request,
			String stageId,
			PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("P.SORT_NO");
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
		PageInfo<Map<String, String>> pageInfo=projectBuildMaterialStageService.getMaterialStageList(pageParam,stageId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMaterialPurchaseById   
	 * @Description: TODO 获取材料采购详情
	 * @param: request
	 * @param: projectBuildMaterialPurchase
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMaterialPurchaseById",method=RequestMethod.POST)
	public RetDataBean getMaterialPurchaseById(HttpServletRequest request,ProjectBuildMaterialPurchase projectBuildMaterialPurchase)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildMaterialPurchase.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildMaterialPurchaseService.selectOneProjectBuildMaterialPurchase(projectBuildMaterialPurchase));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMaterialPurchaseList   
	 * @Description: TODO 搂分类获取料材明细
	 * @param: request
	 * @param: sortId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMaterialPurchaseList",method=RequestMethod.POST)
	public RetDataBean getMaterialPurchaseList(HttpServletRequest request,String search)
	{
		try
		{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求数据成功!", projectBuildMaterialPurchaseService.getMaterialPurchaseList(account.getOrgId(),search));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getPurchaseMaterialMxList   
	 * @Description: TODO 获取材料采购明细 
	 * @param: request
	 * @param: purchaseId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getPurchaseMaterialMxList",method=RequestMethod.POST)
	public RetDataBean getPurchaseMaterialMxList(
			HttpServletRequest request,
			String purchaseId,
			PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("P.SORT_NO");
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
		PageInfo<Map<String, String>> pageInfo=projectBuildMaterialMxService.getPurchaseMaterialMxList(pageParam,purchaseId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMaterialMxById   
	 * @Description: TODO 获取材料采购的明细详情
	 * @param: request
	 * @param: projectBuildMaterialMx
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMaterialMxById",method=RequestMethod.POST)
	public RetDataBean getMaterialMxById(HttpServletRequest request,ProjectBuildMaterialMx projectBuildMaterialMx)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildMaterialMx.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildMaterialMxService.selectOneProjectBuildMaterialMx(projectBuildMaterialMx));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: sumMaterialById   
	 * @Description: TODO 获取已入库数量
	 * @param: request
	 * @param: purchaseId
	 * @param: materialId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/sumMaterialById",method=RequestMethod.POST)
	public RetDataBean sumMaterialById(HttpServletRequest request,String purchaseId,String materialId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!",projectBuildMaterialInService.sumMaterialById(account.getOrgId(), purchaseId, materialId));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMaterialByProjectId   
	 * @Description: TODO 获取项目中可领用的材料列表
	 * @param: request
	 * @param: projectId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMaterialByProjectId",method=RequestMethod.POST)
	public RetDataBean getMaterialByProjectId(
			HttpServletRequest request,
			String projectId,
			PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("P.CREATE_TIME");
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
		PageInfo<Map<String, String>> pageInfo=projectBuildMaterialInService.getMaterialByProjectId(pageParam, projectId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMaterialInList   
	 * @Description: TODO 获取材料入库台账
	 * @param: request
	 * @param: materialId
	 * @param: projectId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMaterialInList",method=RequestMethod.POST)
	public RetDataBean getMaterialInList(
			HttpServletRequest request,
			String materialId,
			String projectId,
			PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("createTime");
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
		PageInfo<Map<String, String>> pageInfo=projectBuildMaterialInService.getMaterialInList(pageParam,projectId,materialId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getQueryPurchaseList   
	 * @Description: TODO 获取查询采购单表列分页
	 * @param: request
	 * @param: pageParam
	 * @param: beginTime
	 * @param: endTime
	 * @param: createUser
	 * @param: projectTitle
	 * @param: companyName
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getQueryPurchaseList",method=RequestMethod.POST)
	public RetDataBean getQueryPurchaseList(
			HttpServletRequest request,
			PageParam pageParam,
			String beginTime,
			String endTime,
			String createUser,
			String projectTitle,
			String companyName
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("createTime");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
		pageParam.setAccountId(createUser);	
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=projectBuildMaterialPurchaseService.getQueryPurchaseList(pageParam,projectTitle,beginTime,endTime,companyName);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getQueryMaterialInList   
	 * @Description: TODO查询入库记录
	 * @param: request
	 * @param: pageParam
	 * @param: beginTime
	 * @param: endTime
	 * @param: inUser
	 * @param: materialName
	 * @param: purchaseTitle
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getQueryMaterialInList",method=RequestMethod.POST)
	public RetDataBean getQueryMaterialInList(
			HttpServletRequest request,
			PageParam pageParam,
			String beginTime,
			String endTime,
			String inUser,
			String materialName,
			String purchaseTitle
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("createTime");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=projectBuildMaterialInService.getQueryMaterialInList(pageParam,inUser,beginTime,endTime,materialName,purchaseTitle);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getQueryMaterialOutList   
	 * @Description: TODO 获取材料出库记录 
	 * @param: request
	 * @param: pageParam
	 * @param: beginTime
	 * @param: endTime
	 * @param: outUser
	 * @param: materialName
	 * @param: projectTitle
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getQueryMaterialOutList",method=RequestMethod.POST)
	public RetDataBean getQueryMaterialOutList(
			HttpServletRequest request,
			PageParam pageParam,
			String beginTime,
			String endTime,
			String outUser,
			String materialName,
			String projectTitle
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("createTime");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=projectBuildMaterialOutService.getQueryMaterialOutList(pageParam, beginTime, endTime, materialName, projectTitle, outUser);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
