package com.core136.controller.sys;

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
import com.core136.bean.account.UserPriv;
import com.core136.bean.oa.Shortcut;
import com.core136.bean.sys.AppConfig;
import com.core136.bean.sys.CodeClass;
import com.core136.bean.sys.PageParam;
import com.core136.bean.sys.SmsConfig;
import com.core136.bean.sys.SysDbSource;
import com.core136.bean.sys.SysDeskConfig;
import com.core136.bean.sys.SysMenu;
import com.core136.bean.sys.SysProfile;
import com.core136.bean.sys.SysTimingTask;
import com.core136.service.account.AccountService;
import com.core136.service.account.UserGroupService;
import com.core136.service.account.UserPrivService;
import com.core136.service.oa.ShortcutService;
import com.core136.service.sys.AppConfigService;
import com.core136.service.sys.CodeClassService;
import com.core136.service.sys.SmsConfigService;
import com.core136.service.sys.SmsService;
import com.core136.service.sys.SysDbSourceService;
import com.core136.service.sys.SysDeskConfigService;
import com.core136.service.sys.SysInterfaceService;
import com.core136.service.sys.SysLogService;
import com.core136.service.sys.SysMenuService;
import com.core136.service.sys.SysMonitorService;
import com.core136.service.sys.SysOrgConfigService;
import com.core136.service.sys.SysProfileService;
import com.core136.service.sys.SysTimingTaskService;

import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
/**
 * 
 * @ClassName:  RouteGetSysController   
 * @Description:TODO 系统相关的接口 
 * @author: 稠云信息
 * @date:   2019年1月3日 上午9:28:48   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/ret/sysget")
public class RouteGetSysController {
@Autowired
private SmsService smsService;
@Autowired
private SysDbSourceService sysDbSourceService;
@Autowired
private CodeClassService codeClassService;
@Autowired
private SmsConfigService smsConfigService;
@Autowired
private AccountService accountService;
@Autowired
private SysDeskConfigService sysDeskConfigService;
@Autowired
private SysMonitorService sysMonitorService;
@Autowired
private SysLogService sysLogService;
@Autowired
private SysOrgConfigService sysOrgConfigService;
@Autowired
private SysInterfaceService sysInterfaceService;
@Autowired
private AppConfigService appConfigService;
@Autowired
private UserPrivService userPrivService;
@Autowired
private SysMenuService sysMenuService;
@Autowired
private ShortcutService shortcutService;
@Autowired
private SysTimingTaskService sysTimingTaskService;
@Autowired
private SysProfileService sysProfileService;

/**
 * 
 * @Title: getAllAppList   
 * @Description: TODO  获取所有的APP应用  
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getAllAppList",method=RequestMethod.POST)
public RetDataBean getAllAppList(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功！",appConfigService.getAllAppList(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getAppConfigById   
 * @Description: TODO 获取APP详情
 * @param: request
 * @param: appConfig
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getAppConfigById",method=RequestMethod.POST)
public RetDataBean getAppConfigById(HttpServletRequest request,AppConfig appConfig)
{
	try
	{	
		return RetDataTools.Ok("请求成功！",appConfigService.selectOne(appConfig));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getAppConfigList   
 * @Description: TODO 获取APP应用列表
 * @param: request
 * @param: pageNumber
 * @param: pageSize
 * @param: search
 * @param: sort
 * @param: sortOrder
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getAppConfigList",method=RequestMethod.POST)
public RetDataBean getAppConfigList(
		HttpServletRequest request,
		Integer pageNumber,
		Integer pageSize,
		String search,
		String sort,
		String sortOrder
		)
{
	try
	{
		if(StringUtils.isBlank(sort))
		{
			sort="SORT_NO";
		}else
		{
			sort=StrTools.upperCharToUnderLine(sort);
		}
		if(StringUtils.isBlank(sortOrder))
		{
			sortOrder="asc";
		}
	String orderBy = sort+ " " + sortOrder;
	Account account=accountService.getRedisAccount(request);
	Example example = new Example(AppConfig.class);
	example.setOrderByClause(orderBy);
	Criteria criteria = example.createCriteria();
	if(!account.getOpFlag().equals("1"))
	{
		criteria.andEqualTo("createUser",account.getAccountId());
	}
	criteria.andEqualTo("orgId",account.getOrgId());
	if(StringUtils.isNotEmpty(search))
	{
		Criteria criteria2 = example.createCriteria();
		criteria2.orLike("title", "%"+search+"%").orLike("module", "%"+search+"%");
		example.and(criteria2);
	}
	PageInfo<AppConfig> pageInfo=appConfigService.getAppConfigList(example, pageNumber, pageSize);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getSysInterface   
 * @Description: TODO 获取系统界面设置
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getSysInterface",method=RequestMethod.POST)
public RetDataBean getSysInterface(HttpServletRequest request)
{
	try
	{
		return RetDataTools.Ok("请求成功！",sysInterfaceService.selectOneSysInterface(null));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getOrgConfigLsit   
 * @Description: TODO 获取机构列表
 * @param: request
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getOrgConfigLsit",method=RequestMethod.POST)
public RetDataBean getOrgConfigLsit(
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
			pageParam.setSortOrder("desc");
		}
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=sysOrgConfigService.getOrgConfigLsit(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getAllSysLogList   
 * @Description: TODO 获取日志列表 
 * @param: request
 * @param: pageParam
 * @param: eventType
 * @param: beginTime
 * @param: endTime
 * @param: accountId
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getAllSysLogList",method=RequestMethod.POST)
public RetDataBean getAllSysLogList(
		HttpServletRequest request,
		PageParam pageParam,
		String eventType,
		String beginTime,
		String endTime,
		String accountId
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
			pageParam.setSortOrder("desc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(accountId);
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=sysLogService.getSysLogList(pageParam, eventType, beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMySysLogList   
 * @Description: TODO 获取个人的系统日志 
 * @param: request
 * @param: pageParam
 * @param: eventType
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getMySysLogList",method=RequestMethod.POST)
public RetDataBean getMySysLogList(
		HttpServletRequest request,
		PageParam pageParam,
		String eventType,
		String beginTime,
		String endTime
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
			pageParam.setSortOrder("desc");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=sysLogService.getSysLogList(pageParam, eventType, beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title getNoReadSms   
 * @Description TODO 获取前10未读消息  
 * @param request
 * @return      
 * RetDataBean
 */
	@RequestMapping(value="/getNoReadSms",method=RequestMethod.POST)
	public RetDataBean getNoReadSms(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",smsService.getNoReadSms(account.getAccountId(),account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title getMySubordinates   
	 * @Description TODO 获取本人下属列表
	 * @param request
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getMySubordinates",method=RequestMethod.POST)
	public RetDataBean getMySubordinates(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",accountService.getMySubordinates(account.getOrgId(),account.getAccountId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title getNoReadSmsCount   
	 * @Description TODO 获取个人未读消息的总数
	 * @param request
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getNoReadSmsCount",method=RequestMethod.POST)
	public RetDataBean getNoReadSmsCount(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",smsService.getNoReadSmsCount(account.getAccountId(),account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getDbSourceList   
	 * @Description TODO 获取下拉菜单的数据源列表
	 * @param request
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getDbSourceList",method=RequestMethod.POST)
	public RetDataBean getDbSourceList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",sysDbSourceService.getDbSourceList(account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getAllSysProfileList   
	 * @Description: TODO 获取系统所有的门户列表
	 * @param: request
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getAllSysProfileList",method=RequestMethod.POST)
	public RetDataBean getAllSysProfileList(
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
				pageParam.setSortOrder("desc");
			}
			Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=sysProfileService.getAllSysProfileList(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getErpOrderList   
	 * @Description: TODO 获取当数据源列表
	 * @param request
	 * @param pageNumber
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @param sortOrder
	 * @return      
	 * @return: RetDataBean      

	 */
	@RequestMapping(value="/getSysDbSourctList",method=RequestMethod.POST)
	public RetDataBean getSysDbSourctList(
			HttpServletRequest request,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="SORT_NO";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
		String orderBy = sort+ " " + sortOrder;
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(SysDbSource.class);
		example.setOrderByClause(orderBy);
		Criteria criteria = example.createCriteria();
		if(!account.getOpFlag().equals("1"))
		{
			criteria.andEqualTo("createUser",account.getAccountId());
		}
		criteria.andEqualTo("orgId",account.getOrgId());
		if(StringUtils.isNotEmpty(search))
		{
			Criteria criteria2 = example.createCriteria();
			criteria2.orLike("dbSourctName", "%"+search+"%").orLike("dbLink", "%"+search+"%");
			example.and(criteria2);
		}
		PageInfo<SysDbSource> pageInfo=sysDbSourceService.getSysDbSourctList(example, pageNumber, pageSize);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
/**
 * 
 * @Title getDbSource   
 * @Description TODO 获取数据源信息
 * @param request
 * @param sysDbSource
 * @return      
 * RetDataBean
 */
	@RequestMapping(value="/getDbSource",method=RequestMethod.POST)
	public RetDataBean getDbSource(HttpServletRequest request,SysDbSource sysDbSource)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			sysDbSource.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功！",sysDbSourceService.selectOne(sysDbSource));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	@RequestMapping(value="/getCodeClassList",method=RequestMethod.POST)
	public RetDataBean getCodeClassList(
			HttpServletRequest request,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="MODULE";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
		String orderBy = sort+ " " + sortOrder;
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(CodeClass.class);
		example.setOrderByClause(orderBy);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("orgId",account.getOrgId());
		if(StringUtils.isNotEmpty(search))
		{
			Criteria criteria2 = example.createCriteria();
			criteria2.orLike("codeName", "%"+search+"%").orLike("module", "%"+search+"%");
			example.and(criteria2);
		}
		PageInfo<CodeClass> pageInfo=codeClassService.getCodeClassList(example, pageNumber, pageSize);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title getCodeClassByModule   
	 * @Description TODO 按模块获取分类列表 
	 * @param request
	 * @param module
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getCodeClassByModule",method=RequestMethod.POST)
	public RetDataBean getCodeClassByModule(HttpServletRequest request,String module)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",codeClassService.getCodeClassByModule(account.getOrgId(), module));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/getCodeClass",method=RequestMethod.POST)
	public RetDataBean getCodeClass(HttpServletRequest request,CodeClass codeClass)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			codeClass.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功！",codeClassService.selectOneCodeClass(codeClass));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getSmsConfig   
	 * @Description TODO 获取系统配置
	 * @param request
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getSmsConfig",method=RequestMethod.POST)
	public RetDataBean getSmsConfig(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			SmsConfig smsConfig = new SmsConfig();
			smsConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功！",smsConfigService.selectOne(smsConfig));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getSysDeskConfig 
	* @Description: TODO 获取桌面模块详情
	* @param @param request
	* @param @param sysDeskConfig
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getSysDeskConfig",method=RequestMethod.POST)
	public RetDataBean getSysDeskConfig(HttpServletRequest request,SysDeskConfig sysDeskConfig)
	{
		try
		{
			if(StringUtils.isBlank(sysDeskConfig.getDeskConfigId()))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			Account account=accountService.getRedisAccount(request);
			sysDeskConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功！",sysDeskConfigService.selectOneSysDeskConfig(sysDeskConfig));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getDeskConfigList   
	 * @Description: TODO 获取可用桌面模块列表
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getDeskConfigList",method=RequestMethod.POST)
	public RetDataBean getDeskConfigList(HttpServletRequest request)
	{
		try
		{
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			return RetDataTools.Ok("请求成功！",sysDeskConfigService.getDeskConfigList(userInfo.getOrgId(),userInfo.getAccountId(),userInfo.getDeptId(),userInfo.getLeadId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: getAllSysDeskConfig 
	* @Description: TODO 获取桌面列表
	* @param @param request
	* @param @param sortId
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getAllSysDeskConfig",method=RequestMethod.POST)
	public RetDataBean getAllSysDeskConfig(
			HttpServletRequest request,
			String sortId,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="SORT_NO";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
			
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("您不是管理员,请与管理员联系!");
		}
		String orderBy = sort+ " " + sortOrder;
		PageInfo<Map<String, String>> pageInfo=sysDeskConfigService.getAllSysDeskConfig(pageNumber, pageSize, orderBy, account.getOrgId(), "%"+search+"%");
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getMemoryStatus   
	 * @Description: TODO 获取当前服务器内存使用情况 
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMemoryStatus",method=RequestMethod.POST)
	public RetDataBean getMemoryStatus(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			return RetDataTools.Ok("请求成功！",sysMonitorService.getMemoryStatus());
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getDiskStatus   
	 * @Description: TODO 获取服务器硬盘使用情况  
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getDiskStatus",method=RequestMethod.POST)
	public RetDataBean getDiskStatus(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			return RetDataTools.Ok("请求成功！",sysMonitorService.getDiskStatus());
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getCpuInfo   
	 * @Description: TODO 获取服务器CPU的基本信息
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getCpuInfo",method=RequestMethod.POST)
	public RetDataBean getCpuInfo(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			return RetDataTools.Ok("请求成功！",sysMonitorService.getCpuInfo());
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getCpuStatus   
	 * @Description: TODO 获取服务器的CPU每个核心的使用情况  
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getCpuStatus",method=RequestMethod.POST)
	public RetDataBean getCpuStatus(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			return RetDataTools.Ok("请求成功！",sysMonitorService.getCpuStatus());
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getMyMenuInPriv   
	 * @Description: TODO 获取当用户权限内的菜单
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping("/getMyMenuInPriv")
	public RetDataBean getMyMenuInPriv(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			String userPrivIds = account.getUserPriv();
			if(StringUtils.isNotBlank(userPrivIds))
			{
				String [] privIdArr = userPrivIds.split(",");
				String sysMenuIds="";
				for(int i=0;i<privIdArr.length;i++)
				{
					if(StringUtils.isNotBlank(privIdArr[i]))
					{
					UserPriv userPriv = userPrivService.getUserPrivByPrivId(privIdArr[i], account.getOrgId());
					sysMenuIds+=userPriv.getUserPrivStr()+",";
					}
				}
				List<String> sysMenuIdList = StrTools.strToList(sysMenuIds);
				List<SysMenu> sysMenuList = sysMenuService.getSysMenuInPriv(sysMenuIdList, account.getOrgId());
				return RetDataTools.Ok("请求成功！",sysMenuList);
			}else
			{
				return RetDataTools.NotOk("当前用户权限没有菜单！");
			}
		}catch(Exception e)
		{
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getMyShortcutMenu   
	 * @Description: TODO 获取个人的快捷方式
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMyShortcutMenu",method=RequestMethod.POST)
	public RetDataBean getMyShortcutMenu(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			Shortcut shortcut = new Shortcut();
			shortcut.setCreateUser(account.getAccountId());
			shortcut.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功！",shortcutService.selectOneShortcut(shortcut));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getSysTimingTaskList   
	 * @Description: TODO  获取系统定时任务 
	 * @param: request
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getSysTimingTaskList",method=RequestMethod.POST)
	public RetDataBean getSysTimingTaskList(
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
				pageParam.setSortOrder("desc");
			}
			Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=sysTimingTaskService.getSysTimingTaskList(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getSysTimingTaskById   
	 * @Description: TODO 获取定时任务详情 
	 * @param: request
	 * @param: sysTimingTask
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getSysTimingTaskById",method=RequestMethod.POST)
	public RetDataBean getSysTimingTaskById(HttpServletRequest request,SysTimingTask sysTimingTask)
	{
		try
		{	
			Account account=accountService.getRedisAccount(request);
			sysTimingTask.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功！",sysTimingTaskService.selectOneSysTimingTask(sysTimingTask));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getSysProfileById   
	 * @Description: TODO 获取门户详情  
	 * @param: request
	 * @param: sysProfile
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getSysProfileById",method=RequestMethod.POST)
	public RetDataBean getSysProfileById(HttpServletRequest request,SysProfile sysProfile)
	{
		try
		{	
			return RetDataTools.Ok("请求成功！",sysProfileService.selectOneSysProfile(sysProfile));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
