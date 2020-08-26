package com.core136.controller.sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.oa.Shortcut;
import com.core136.bean.sys.AppConfig;
import com.core136.bean.sys.CodeClass;
import com.core136.bean.sys.SmsConfig;
import com.core136.bean.sys.SysConfig;
import com.core136.bean.sys.SysDbSource;
import com.core136.bean.sys.SysDeskConfig;
import com.core136.bean.sys.SysInterface;
import com.core136.bean.sys.SysOrgConfig;
import com.core136.bean.sys.SysProfile;
import com.core136.bean.sys.SysTimingTask;
import com.core136.service.account.AccountService;
import com.core136.service.oa.ShortcutService;
import com.core136.service.sys.AppConfigService;
import com.core136.service.sys.CodeClassService;
import com.core136.service.sys.SmsConfigService;
import com.core136.service.sys.SysConfigService;
import com.core136.service.sys.SysDbSourceService;
import com.core136.service.sys.SysDeskConfigService;
import com.core136.service.sys.SysInterfaceService;
import com.core136.service.sys.SysOrgConfigService;
import com.core136.service.sys.SysProfileService;
import com.core136.service.sys.SysTimingTaskService;

import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  RouteSetSysController   
 * @Description:TODO 系统
 * @author: 稠云信息
 * @date:   2019年1月3日 上午9:31:29   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/set/sysset")
public class RouteSetSysController {
@Autowired
private SysDbSourceService SysDbSourceSerive;
@Autowired
private SysConfigService sysConfigService;
@Autowired
private CodeClassService codeClassService;
@Autowired
private SmsConfigService smsConfigService;
@Autowired
private SysDeskConfigService sysDeskConfigService;
@Autowired
private SysOrgConfigService sysOrgConfigService;
@Autowired
private SysInterfaceService sysInterfaceService;
@Autowired
private AppConfigService appConfigService;
@Autowired
private ShortcutService shortcutService;
@Autowired
private SysTimingTaskService sysTimingTaskService;
@Autowired
private SysProfileService sysProfileService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: updateAppConfig   
 * @Description: TODO 更新APP应用
 * @param: request
 * @param: appConfig
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateAppConfig",method=RequestMethod.POST)
public RetDataBean updateAppConfig(HttpServletRequest request,AppConfig appConfig)
{
	try
	{
		if(StringUtils.isBlank(appConfig.getAppId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("您不是管理员，请与管理员联系!");
		}
		Example example = new Example(AppConfig.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("appId",appConfig.getAppId());
		return RetDataTools.Ok("APP应用更新成功！",appConfigService.updateAppConfig(example, appConfig));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: deleteAppConfig   
 * @Description: TODO 删除APP应用
 * @param: request
 * @param: appConfig
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteAppConfig",method=RequestMethod.POST)
public RetDataBean deleteAppConfig(HttpServletRequest request,AppConfig appConfig)
{
	try
	{
		if(StringUtils.isBlank(appConfig.getAppId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("您不是管理员，请与管理员联系!");
		}
		appConfig.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建成功！",appConfigService.deleteAppConfig(appConfig));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertAppConfig   
 * @Description: TODO 添加APP应用
 * @param: request
 * @param: appConfig
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertAppConfig",method=RequestMethod.POST)
public RetDataBean insertAppConfig(HttpServletRequest request,AppConfig appConfig)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		appConfig.setAppId(SysTools.getGUID());
		appConfig.setCreateUser(account.getAccountId());
		appConfig.setStatus("0");
		appConfig.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		appConfig.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加成功！",appConfigService.insertAppConfig(appConfig));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: setInterface   
 * @Description: TODO 设置系统登陆界面
 * @param: request
 * @param: sysInterface
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/setInterface",method=RequestMethod.POST)
public RetDataBean setInterface(HttpServletRequest request,SysInterface sysInterface)
{
	try
	{
		return RetDataTools.Ok("设置成功！",sysInterfaceService.setSysInterface(sysInterface));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: addOrgConfig   
 * @Description: TODO 创建新机构  
 * @param: request
 * @param: sysOrgConfig
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/addOrgConfig",method=RequestMethod.POST)
public RetDataBean addOrgConfig(HttpServletRequest request,SysOrgConfig sysOrgConfig,String orgName,String password)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(orgName))
		{
			return RetDataTools.NotOk("新机构名称不能为空!");	
		}
		if(StringUtils.isBlank(sysOrgConfig.getOrgAdmin()))
		{
			return RetDataTools.NotOk("新机构的管理员账户不能为空!");	
		}
		if(StringUtils.isBlank(password))
		{
			return RetDataTools.NotOk("新机构的管理员密码不能为空!");	
		}
		sysOrgConfig.setConfigId(SysTools.getGUID());
		sysOrgConfig.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		sysOrgConfig.setCreateUser(account.getAccountId());
		sysOrgConfig.setOrgId(SysTools.getGUID());
		return sysOrgConfigService.addOrg(account.getOrgId(), sysOrgConfig,orgName,password);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title insertDbSource   
 * @Description TODO 添加数据源
 * @param request
 * @param sysDbSource
 * @return      
 * RetDataBean
 */
	@RequestMapping(value="/insertDbSource",method=RequestMethod.POST)
	public RetDataBean insertDbSource(HttpServletRequest request,SysDbSource sysDbSource)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			sysDbSource.setDbSourceId(SysTools.getGUID());
			sysDbSource.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			sysDbSource.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建成功！",SysDbSourceSerive.insertSysDbSource(sysDbSource));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
/**
 * 	
 * @Title updateDbSource   
 * @Description TODO 更新数据  
 * @param request
 * @param sysDbSource
 * @return      
 * RetDataBean
 */
	@RequestMapping(value="/updateDbSource",method=RequestMethod.POST)
	public RetDataBean updateDbSource(HttpServletRequest request,SysDbSource sysDbSource)
	{
		try
		{
			if(StringUtils.isBlank(sysDbSource.getDbSourceId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			sysDbSource.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			sysDbSource.setOrgId(account.getOrgId());
			Example example = new Example(SysDbSource.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("dbSourceId",sysDbSource.getDbSourceId());
			return RetDataTools.Ok("更新成功！",SysDbSourceSerive.updateSysDbSource(sysDbSource, example));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title deleteDbSource   
	 * @Description TODO 删除数据源  
	 * @param request
	 * @param sysDbSource
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/deleteDbSource",method=RequestMethod.POST)
	public RetDataBean deleteDbSource(HttpServletRequest request,SysDbSource sysDbSource)
	{
		try
		{
			if(StringUtils.isBlank(sysDbSource.getDbSourceId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("您不是管理员，请与管理员联系!");
			}
			sysDbSource.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功！",SysDbSourceSerive.deleteSysDbSource(sysDbSource));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title insertSysConfig   
	 * @Description TODO 添加系统配置
	 * @param request
	 * @param sysConfig
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/updateSysConfig",method=RequestMethod.POST)
	public RetDataBean updateSysConfig(HttpServletRequest request,SysConfig sysConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			sysConfig.setOrgId(account.getOrgId());
			SysConfig newSysConfig = new SysConfig();
			newSysConfig.setOrgId(sysConfig.getOrgId());
			newSysConfig =sysConfigService.selectOneSysConfig(newSysConfig);
			if(newSysConfig!=null)
			{
				Example example = new Example(SysConfig.class);
				example.createCriteria().andEqualTo("orgId",sysConfig.getOrgId());
				return RetDataTools.Ok("更新系统配置成功！",sysConfigService.updateSysConfig(sysConfig,example));
				
			}else
			{
				sysConfig.setSysConfigId(SysTools.getGUID());
				return RetDataTools.Ok("添加系统配置成功！",sysConfigService.insertSysConfig(sysConfig));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title deleteCodeClass   
	 * @Description TODO删除系统分类
	 * @param request
	 * @param codeClass
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/deleteCodeClass",method=RequestMethod.POST)
	public RetDataBean deleteCodeClass(HttpServletRequest request,CodeClass codeClass)
	{
		try
		{
			if(StringUtils.isBlank(codeClass.getCodeClassId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			codeClass.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功！",codeClassService.deleteCodeClass(codeClass));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteCodeClassBatch   
	 * @Description: TODO 批量删除分类码
	 * @param: request
	 * @param: codeClass
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteCodeClassBatch",method=RequestMethod.POST)
	public RetDataBean deleteCodeClassBatch(HttpServletRequest request,@RequestParam(value = "classCodeArr[]") String[] classCodeArr)
	{
		try
		{
			if(classCodeArr.length==0)
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("对不起,您不是系统管理员,请与管理员联系!");
			}
			List<String> list = new ArrayList<String>(Arrays.asList(classCodeArr));
			Example example = new Example(CodeClass.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andIn("classCodeId", list);
			return RetDataTools.Ok("删除成功！",codeClassService.deleteCodeClass(example));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title insertCodeClass   
	 * @Description TODO 添加系统分类
	 * @param request
	 * @param codeClass
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/insertCodeClass",method=RequestMethod.POST)
	public RetDataBean insertCodeClass(HttpServletRequest request,CodeClass codeClass)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			codeClass.setCodeClassId(SysTools.getGUID());
			codeClass.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功！",codeClassService.insertCodeClass(codeClass));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title updateCodeClass   
	 * @Description TODO 更新系统编码
	 * @param request
	 * @param codeClass
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/updateCodeClass",method=RequestMethod.POST)
	public RetDataBean updateCodeClass(HttpServletRequest request,CodeClass codeClass)
	{
		try
		{
			if(StringUtils.isBlank(codeClass.getCodeClassId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(CodeClass.class);
			example.createCriteria().andEqualTo("codeClassId",codeClass.getCodeClassId()).andEqualTo("orgId",account.getOrgId());
			return RetDataTools.Ok("更新成功！",codeClassService.updateCodeClass(codeClass, example));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title updateSmsConfig   
	 * @Description TODO 设置消息提醒权限 
	 * @param request
	 * @param config
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/updateSmsConfig",method=RequestMethod.POST)
	public RetDataBean updateSmsConfig(HttpServletRequest request,String config,String mustChecked)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(account.getOpFlag().equals("1"))
			{
			SmsConfig smsConfig = new SmsConfig();
			smsConfig.setOrgId(account.getOrgId());
			if(smsConfigService.isExist(smsConfig)>0)
			{
				smsConfig.setConfig(config);
				smsConfig.setMustChecked(mustChecked);
				Example example = new Example(SmsConfig.class);
				example.createCriteria().andEqualTo("orgId",account.getOrgId());
				return RetDataTools.Ok("更新成功！",smsConfigService.updateSmsConfig(smsConfig, example));
			}else
			{
				smsConfig.setConfig(config);
				smsConfig.setMustChecked(mustChecked);
				return RetDataTools.Ok("更新成功！",smsConfigService.insertSmsConfig(smsConfig));
			}
			}else
			{
				return RetDataTools.NotOk("非系统管理员,请与系统管理员联系!");
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: createDeskConfig 
	* @Description: TODO 创建系统桌面模块
	* @param @param request
	* @param @param sysDeskConfig
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/createDeskConfig",method=RequestMethod.POST)
	public RetDataBean createDeskConfig(HttpServletRequest request,SysDeskConfig sysDeskConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("非系统管理员,请与系统管理员联系!");
			}else
			{
				sysDeskConfig.setDeskConfigId(SysTools.getGUID());
				sysDeskConfig.setUseUserPriv("");
				sysDeskConfig.setUseDeptPriv("");
				sysDeskConfig.setUseLeavePriv("");
				sysDeskConfig.setMustUseUserPriv("");
				sysDeskConfig.setMustUseDeptPriv("");
				sysDeskConfig.setMustUseLeavePriv("");
				sysDeskConfig.setUnUseUserPriv("");
				sysDeskConfig.setUnUseDeptPriv("");
				sysDeskConfig.setUnUseLeavePriv("");
				sysDeskConfig.setOrgId(account.getOrgId());
				return RetDataTools.Ok("创建系统桌面模块成功!",sysDeskConfigService.insertSysDeskConfig(sysDeskConfig));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: updateDeskConfig 
	* @Description: TODO 更新系统桌面模块
	* @param @param request
	* @param @param sysDeskConfig
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/updateDeskConfig",method=RequestMethod.POST)
	public RetDataBean updateDeskConfig(HttpServletRequest request,SysDeskConfig sysDeskConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(sysDeskConfig.getDeskConfigId()))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("非系统管理员,请与系统管理员联系!");
			}else
			{
				sysDeskConfig.setOrgId(account.getOrgId());
				Example example = new Example(SysDeskConfig.class);
				example.createCriteria().andEqualTo("deskConfigId",sysDeskConfig.getDeskConfigId()).andEqualTo("orgId",sysDeskConfig.getOrgId());
				return RetDataTools.Ok("更新系统桌面模块成功!",sysDeskConfigService.updateSysDeskConfig(sysDeskConfig, example));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: delDeskConfig 
	* @Description: TODO 删除系统桌面模块
	* @param @param request
	* @param @param sysDeskConfig
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/delDeskConfig",method=RequestMethod.POST)
	public RetDataBean delDeskConfig(HttpServletRequest request,SysDeskConfig sysDeskConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(sysDeskConfig.getDeskConfigId()))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			if(account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("非系统管理员,请与系统管理员联系!");
			}else
			{
				sysDeskConfig.setOrgId(account.getOrgId());
				return RetDataTools.Ok("系统桌面模块删除成功!",sysDeskConfigService.delSysDeskConfig(sysDeskConfig));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: setShortcut   
	 * @Description: TODO 设置个人快捷方式
	 * @param: request
	 * @param: shortcut
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/setShortcut",method=RequestMethod.POST)
	public RetDataBean setShortcut(HttpServletRequest request,Shortcut shortcut)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			Shortcut shortcut1 = new Shortcut();
			shortcut1.setCreateUser(account.getAccountId());
			shortcut1.setOrgId(account.getOrgId());
			shortcut1 = shortcutService.selectOneShortcut(shortcut1);
			if(shortcut1==null)
			{
				shortcut.setConfigId(SysTools.getGUID());
				shortcut.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
				shortcut.setCreateUser(account.getAccountId());
				shortcut.setOrgId(account.getOrgId());
				return RetDataTools.Ok("设置成功！",shortcutService.insertShortcut(shortcut));
			}else
			{
				Example example = new Example(Shortcut.class);
				example.createCriteria().andEqualTo("configId",shortcut1.getConfigId()).andEqualTo("createUser",account.getAccountId());
				return RetDataTools.Ok("设置成功！",shortcutService.updateShortuct(example, shortcut));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertSysTimingTask   
	 * @Description: TODO 创建定时任务
	 * @param: request
	 * @param: sysTimingTask
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertSysTimingTask",method=RequestMethod.POST)
	public RetDataBean insertSysTimingTask(HttpServletRequest request,SysTimingTask sysTimingTask)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			sysTimingTask.setTaskId(SysTools.getGUID());
			sysTimingTask.setCreateUser(account.getAccountId());
			sysTimingTask.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			sysTimingTask.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加任务成功！",sysTimingTaskService.insertSysTimingTask(sysTimingTask));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: delSysTimingTask   
	 * @Description: TODO 删除定时任务
	 * @param: request
	 * @param: sysTimingTask
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/delSysTimingTask",method=RequestMethod.POST)
	public RetDataBean delSysTimingTask(HttpServletRequest request,SysTimingTask sysTimingTask)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(sysTimingTask.getTaskId()))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			if(account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("非系统管理员,请与系统管理员联系!");
			}else
			{
				sysTimingTask.setOrgId(account.getOrgId());
				return RetDataTools.Ok("删除任务成功!",sysTimingTaskService.deleteSysTimingTask(sysTimingTask));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateSysTimingTask   
	 * @Description: TODO 更新任务
	 * @param: request
	 * @param: sysTimingTask
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateSysTimingTask",method=RequestMethod.POST)
	public RetDataBean updateSysTimingTask(HttpServletRequest request,SysTimingTask sysTimingTask)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(sysTimingTask.getTaskId()))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("非系统管理员,请与系统管理员联系!");
			}else
			{
				sysTimingTask.setOrgId(account.getOrgId());
				Example example = new Example(SysTimingTask.class);
				example.createCriteria().andEqualTo("taskId",sysTimingTask.getTaskId()).andEqualTo("orgId",sysTimingTask.getOrgId());
				return RetDataTools.Ok("定时任务更新成功!",sysTimingTaskService.updateSysTimingTask(example,sysTimingTask));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @Title: updateSysProfile   
	 * @Description: TODO 更新门户权限
	 * @param: request
	 * @param: sysProfile
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateSysProfile",method=RequestMethod.POST)
	public RetDataBean updateSysProfile(HttpServletRequest request,SysProfile sysProfile)
	{
		try
		{
			if(StringUtils.isBlank(sysProfile.getProfileId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("您不是管理员，请与管理员联系!");
			}
			Example example = new Example(SysProfile.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("profileId",sysProfile.getProfileId());
			return RetDataTools.Ok("门户权限更新成功！",sysProfileService.updateSysProfile(example, sysProfile));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
