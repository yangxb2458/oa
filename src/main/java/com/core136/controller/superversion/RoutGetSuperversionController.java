/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetSuperversionController.java   
 * @Package com.core136.controller.superversion   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月14日 下午1:18:15   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.superversion;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.superversion.Superversion;
import com.core136.bean.superversion.SuperversionConfig;
import com.core136.bean.superversion.SuperversionDelay;
import com.core136.bean.superversion.SuperversionProcess;
import com.core136.bean.superversion.SuperversionScore;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.superversion.SuperversionConfigService;
import com.core136.service.superversion.SuperversionDelayService;
import com.core136.service.superversion.SuperversionProcessService;
import com.core136.service.superversion.SuperversionScoreService;
import com.core136.service.superversion.SuperversionService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/ret/superversionget")
public class RoutGetSuperversionController {
@Autowired
private SuperversionService superversionService;
@Autowired
private SuperversionConfigService superversionConfigService;
@Autowired
private SuperversionDelayService superversionDelayService;
@Autowired
private SuperversionProcessService superversionProcessService;
@Autowired
private SuperversionScoreService superversionScoreService;
@Autowired
private AccountService accountService;
@RequestMapping(value="/getSuperversionScoreById",method=RequestMethod.POST)
public RetDataBean getSuperversionScoreById(HttpServletRequest request,SuperversionScore superversionScore)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		superversionScore.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",superversionScoreService.selectOneSuperversionScore(superversionScore));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

@RequestMapping(value="/getSuperversionProcessById",method=RequestMethod.POST)
public RetDataBean getSuperversionProcessById(HttpServletRequest request,SuperversionProcess superversionProcess)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		superversionProcess.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",superversionProcessService.selectOneSuperversionProcess(superversionProcess));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


@RequestMapping(value="/getSuperversionDelayById",method=RequestMethod.POST)
public RetDataBean getSuperversionDelayById(HttpServletRequest request,SuperversionDelay superversionDelay)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		superversionDelay.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",superversionDelayService.selectOneSuperversionDelay(superversionDelay));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getSuperversionConfigById   
 * @Description: TODO 获取分类详情
 * @param: request
 * @param: superversionConfig
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getSuperversionConfigById",method=RequestMethod.POST)
public RetDataBean getSuperversionConfigById(HttpServletRequest request,SuperversionConfig superversionConfig)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		superversionConfig.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",superversionConfigService.selectOneSuperversionConfig(superversionConfig));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getSuperversionById   
 * @Description: TODO 获取督查项目详情
 * @param: request
 * @param: superversion
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getSuperversionById",method=RequestMethod.POST)
public RetDataBean getSuperversionById(HttpServletRequest request,Superversion superversion)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		superversion.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",superversionService.selectOneSuperversion(superversion));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getSuperversionConfigList   
 * @Description: TODO 获取类型与领导列表  
 * @param: request
 * @param: superversion
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getAllSuperversionConfigList",method=RequestMethod.POST)
public RetDataBean getSuperversionConfigList(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!",superversionConfigService.getAllSuperversionConfigList(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getMySuperversionConfigList   
 * @Description: TODO 与我有关的分类
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getMySuperversionConfigList",method=RequestMethod.POST)
public RetDataBean getMySuperversionConfigList(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!",superversionConfigService.getMySuperversionConfigList(account.getOrgId(),account.getAccountId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getSuperversionConfigList   
 * @Description: TODO 获取类型列表
 * @param: request
 * @param: sortId
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getSuperversionConfigList",method=RequestMethod.POST)
public RetDataBean getSuperversionConfigList(
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
	PageInfo<SuperversionConfig> pageInfo=superversionConfigService.getSuperversionConfigList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getLeadManageSupperversionList   
 * @Description: TODO 获取当前用户管控的事件列表
 * @param: request
 * @param: beginTime
 * @param: endTime
 * @param: type
 * @param: handedUser
 * @param: status
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getLeadManageSupperversionList",method=RequestMethod.POST)
public RetDataBean getLeadManageSupperversionList(
		HttpServletRequest request,
		String beginTime,
		String endTime,
		String type,
		String handedUser,
		String status,
		PageParam pageParam
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String,String>> pageInfo=superversionService.getLeadManageSupperversionList(pageParam,type,handedUser,beginTime,endTime,status);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getSupperversionList   
 * @Description: TODO 获取我创建的督查事件
 * @param: request
 * @param: beginTime
 * @param: endTime
 * @param: type
 * @param: handedUser
 * @param: status
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getSupperversionList",method=RequestMethod.POST)
public RetDataBean getSupperversionList(
		HttpServletRequest request,
		String beginTime,
		String endTime,
		String type,
		String handedUser,
		String status,
		PageParam pageParam
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String,String>> pageInfo=superversionService.getSupperversionList(pageParam,type,handedUser,beginTime,endTime,status);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getDelayApplyList   
 * @Description: TODO 获取延期审批列表
 * @param: request
 * @param: beginTime
 * @param: endTime
 * @param: type
 * @param: createUser
 * @param: status
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getDelayApplyList",method=RequestMethod.POST)
public RetDataBean getDelayApplyList(
		HttpServletRequest request,
		String beginTime,
		String endTime,
		String type,
		String createUser,
		String status,
		PageParam pageParam
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("D.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String,String>> pageInfo=superversionDelayService.getDelayApplyList(pageParam,status,type,beginTime,endTime,createUser);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getSupperversionPorcessList   
 * @Description: TODO 获取待处理的督查列表  
 * @param: request
 * @param: beginTime
 * @param: endTime
 * @param: type
 * @param: handedUser
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getSupperversionPorcessList",method=RequestMethod.POST)
public RetDataBean getSupperversionPorcessList(
		HttpServletRequest request,
		String beginTime,
		String endTime,
		String type,
		String handedUser,
		String status,
		PageParam pageParam
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String,String>> pageInfo=superversionService.getSupperversionPorcessList(pageParam,type,handedUser,beginTime,endTime,status);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyCompleteProcessList 
 * @Description: TODO 获取事件处理过程列表
 * @param: request
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getMyCompleteProcessList",method=RequestMethod.POST)
public RetDataBean getMyCompleteProcessList(
		HttpServletRequest request,
		String beginTime,
		String endTime,
		String type,
		PageParam pageParam
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("P.FINISH_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=superversionProcessService.getMyCompleteProcessList(pageParam,beginTime,endTime,type);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getControlProcessList   
 * @Description: TODO 获取我所管控的任务列表  
 * @param: request
 * @param: beginTime
 * @param: endTime
 * @param: type
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getControlProcessList",method=RequestMethod.POST)
public RetDataBean getControlProcessList(
		HttpServletRequest request,
		String beginTime,
		String endTime,
		String type,
		PageParam pageParam
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("P.SUPERVERSION_ID");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=superversionProcessService.getControlProcessList(pageParam,beginTime,endTime,type);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getQuerySuperversionForDept   
 * @Description: TODO 按部门汇总
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getQuerySuperversionForDept",method=RequestMethod.POST)
public RetDataBean getQuerySuperversionForDept(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功！",superversionService.getQuerySuperversionForDept(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getQuerySuperversionForType   
 * @Description: TODO 按类型汇总
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getQuerySuperversionForType",method=RequestMethod.POST)
public RetDataBean getQuerySuperversionForType(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功！",superversionConfigService.getQuerySuperversionForType(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

}
