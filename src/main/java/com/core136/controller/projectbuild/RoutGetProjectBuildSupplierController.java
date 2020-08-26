package com.core136.controller.projectbuild;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.projectbuild.ProjectBuildSupplier;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.projectbuild.ProjectBuildSupplierService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;

@RestController
@RequestMapping("/ret/projectbuildsupplierget")
public class RoutGetProjectBuildSupplierController {
	@Autowired
	private ProjectBuildSupplierService projectBuildSupplierService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: getSupplierList   
	 * @Description: TODO 获取供应商列表
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getSupplierList",method=RequestMethod.POST)
	public RetDataBean getSupplierList(HttpServletRequest request,PageParam pageParam)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
			pageParam.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildSupplierService.getSupplierList(pageParam));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getProjectBuildSupplierById   
	 * @Description: TODO 获取供应商详情
	 * @param: request
	 * @param: projectBuildSupplier
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getProjectBuildSupplierById",method=RequestMethod.POST)
	public RetDataBean getProjectBuildSupplierById(HttpServletRequest request,ProjectBuildSupplier projectBuildSupplier)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			projectBuildSupplier.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",projectBuildSupplierService.selectOneProjectBuildSupplier(projectBuildSupplier));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getSelect2SupplierList   
	 * @Description: TODO 获取Select2供应商列表
	 * @param: request
	 * @param: search
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getSelect2SupplierList",method=RequestMethod.POST)
	public RetDataBean getSelect2SupplierList(HttpServletRequest request,String search)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!",projectBuildSupplierService.getSelect2SupplierList(account.getOrgId(),search));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
}
