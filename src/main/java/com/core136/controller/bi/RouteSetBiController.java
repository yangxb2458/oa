package com.core136.controller.bi;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.bi.BiSort;
import com.core136.bean.bi.BiTemplate;
import com.core136.service.account.AccountService;
import com.core136.service.bi.BiSortService;
import com.core136.service.bi.BiTemplateService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/set/biset")
public class RouteSetBiController {
@Autowired
private BiSortService biSortService;
@Autowired
private BiTemplateService biTemplateService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title insertBiSort   
 * @Description TODO 创建BI分类
 * @param request
 * @param biSort
 * @return      
 * RetDataBean
 */
	@RequestMapping(value="/insertBiSort",method=RequestMethod.POST)
	public RetDataBean insertBiSort(HttpServletRequest request,BiSort biSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			biSort.setSortId(SysTools.getGUID());
			if(StringUtils.isBlank(biSort.getLevelId()))
			{
				biSort.setLevelId("0");
			}
			biSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			biSort.setCreateUser(account.getAccountId());
			biSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建BI分类成功！", biSortService.insertBiSort(biSort));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title deleteBiSort   
	 * @Description TODO 删除BI分类
	 * @param request
	 * @param biSort
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/deleteBiSort",method=RequestMethod.POST)
	public RetDataBean deleteBiSort(HttpServletRequest request,BiSort biSort)
	{
		try
		{
			if(StringUtils.isBlank(biSort.getSortId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			biSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除BI分类成功！", biSortService.deleteBiSort(biSort));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title updateBiSort   
	 * @Description TODO 更新分类
	 * @param request
	 * @param biSort
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/updateBiSort",method=RequestMethod.POST)
	public RetDataBean updateBiSort(HttpServletRequest request,BiSort biSort)
	{
		try
		{
			if(StringUtils.isBlank(biSort.getSortId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			biSort.setOrgId(account.getOrgId());
			Example example = new Example(BiSort.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("sortId",biSort.getSortId());
			return RetDataTools.Ok("更新BI分类成功！", biSortService.updateBiSort(biSort, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title deleteBiTemplate   
	 * @Description TODO 删除BI模版
	 * @param request
	 * @param biTemplate
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/deleteBiTemplate",method=RequestMethod.POST)
	public RetDataBean deleteBiTemplate(HttpServletRequest request,BiTemplate biTemplate)
	{
		try
		{
			if(StringUtils.isBlank(biTemplate.getTemplateId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			biTemplate.setOrgId(account.getOrgId());
			return RetDataTools.Ok("模版删除成功！", biTemplateService.deleteBiTemplate(biTemplate));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title insertBiTemplate   
	 * @Description TODO 添加BI模版
	 * @param request
	 * @param biTemplate
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/insertBiTemplate",method=RequestMethod.POST)
	public RetDataBean insertBiTemplate(HttpServletRequest request,BiTemplate biTemplate)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			biTemplate.setTemplateId(SysTools.getGUID());
			biTemplate.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			biTemplate.setCreateUser(account.getAccountId());
			biTemplate.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建模版成功！", biTemplateService.insertBiTemplate(biTemplate));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title updateBiTemplate   
	 * @Description TODO 更新BI模版
	 * @param request
	 * @param biTemplate
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/updateBiTemplate",method=RequestMethod.POST)
	public RetDataBean updateBiTemplate(HttpServletRequest request,BiTemplate biTemplate)
	{
		try
		{
			if(StringUtils.isBlank(biTemplate.getTemplateId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			biTemplate.setOrgId(account.getOrgId());
			Example example = new Example(BiTemplate.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("templateId",biTemplate.getTemplateId());
			return RetDataTools.Ok("更新BI模版成功！", biTemplateService.updateBiTemplate(biTemplate, example));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
