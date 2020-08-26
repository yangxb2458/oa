package com.core136.controller.unit;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.core136.bean.account.Account;
import com.core136.bean.account.Unit;
import com.core136.bean.account.UnitDept;
import com.core136.bean.account.UserGroup;
import com.core136.bean.account.UserInfo;
import com.core136.bean.account.UserLevel;
import com.core136.bean.account.UserPriv;
import com.core136.bean.sys.DdConfig;
import com.core136.bean.sys.SysMenu;
import com.core136.bean.sys.WxConfig;
import com.core136.service.account.AccountService;
import com.core136.service.account.UnitDeptService;
import com.core136.service.account.UnitService;
import com.core136.service.account.UserGroupService;
import com.core136.service.account.UserInfoService;
import com.core136.service.account.UserLevelService;
import com.core136.service.account.UserPrivService;
import com.core136.service.sys.DdConfigService;
import com.core136.service.sys.SysMenuService;
import com.core136.service.sys.WxConfigService;
import com.core136.unit.RedisUtil;

import org.core136.common.auth.LoginAccountInfo;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.Md5CaculateUtil;
import org.core136.common.utils.StrTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  RouteSetUnitController   
 * @Description:TODO 单位相关接口
 * @author: 稠云信息
 * @date:   2019年1月3日 上午9:30:00   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/set/unitset")
public class RouteSetUnitController {
@Autowired
private UnitService unitService;
@Autowired
private UnitDeptService unitDeptService;
@Autowired
private UserInfoService userInfoService;
@Autowired
private SysMenuService sysMenuService;
@Autowired
private UserPrivService userPrivService;
@Autowired
private AccountService accountService;
@Autowired
private UserGroupService userGroupService;
@Autowired
private UserLevelService userLevelService;
@Autowired
private DdConfigService dbConfigService;
@Autowired
private WxConfigService wxConfigService;
@Autowired
private RedisUtil redisUtil;

/**
 * 
 * @Title: insertUserGroup   
 * @Description: TODO 添加好友分组
 * @param request
 * @param userGroup
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/insertUserGroup",method=RequestMethod.POST)
public RetDataBean insertUserGroup(HttpServletRequest request,UserGroup userGroup)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		userGroup.setGroupId(SysTools.getGUID());
		userGroup.setOrgId(account.getOrgId());
		userGroup.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		userGroup.setCreateUser(account.getAccountId());	
		return RetDataTools.Ok("添加好友分组成功!", userGroupService.insertUserGroup(userGroup));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: deleteUserGroup   
 * @Description: TODO 删除好友分组
 * @param request
 * @param userGroup
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/deleteUserGroup",method=RequestMethod.POST)
public RetDataBean deleteUserGroup(HttpServletRequest request,UserGroup userGroup)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(userGroup.getGroupId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		userGroup.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除好友分组成功!", userGroupService.deleteUserGroup(userGroup));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateUserGroup   
 * @Description: TODO 更新好友分组
 * @param request
 * @param userGroup
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/updateUserGroup",method=RequestMethod.POST)
public RetDataBean updateUserGroup(HttpServletRequest request,UserGroup userGroup)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(userGroup.getGroupId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Example example = new Example(UserGroup.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("groupId",userGroup.getGroupId());
		userGroup.setOrgId(account.getOrgId());
		return RetDataTools.Ok("更新好友分组成功!", userGroupService.updateUserGroup(example,userGroup));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateWxConfig   
 * @Description: TODO 设置企业微信配置
 * @param: request
 * @param: ddConfig
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateWxConfig",method=RequestMethod.POST)
public RetDataBean updateWxConfig(HttpServletRequest request,WxConfig wxConfig)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("您不是管理,请与管理员联系！");
		}else
		{
			wxConfig.setConfigId(SysTools.getGUID());
			wxConfig.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			wxConfig.setCreateUser(account.getAccountId());
			wxConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("更新企业微信应用信息成功！",wxConfigService.setWxConfig(wxConfig));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateDdConfig   
 * @Description: TODO 设置钉钉配置
 * @param: request
 * @param: ddConfig
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateDdConfig",method=RequestMethod.POST)
public RetDataBean updateDdConfig(HttpServletRequest request,DdConfig ddConfig)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("您不是管理,请与管理员联系！");
		}else
		{
			ddConfig.setConfigId(SysTools.getGUID());
			ddConfig.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			ddConfig.setCreateUser(account.getAccountId());
			ddConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("更新钉钉应用信息成功！",dbConfigService.setDdConfig(ddConfig));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: deleteWeiXinAccount   
 * @Description: TODO 删除企业微信用户
 * @param request
 * @param accountId
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/deleteWeiXinAccount",method=RequestMethod.POST)
public RetDataBean deleteWeiXinAccount(HttpServletRequest request,String accountId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		Account account1 = new Account();
		account1.setAccountId(accountId);
		account1.setOrgId(account.getOrgId());
		account1=accountService.selectOneAccount(account1);
		return userInfoService.deleteWxAccount(account1);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: deleteDingDingAccount   
 * @Description: TODO 删除钉钉用户
 * @param: request
 * @param: accountId
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteDingDingAccount",method=RequestMethod.POST)
public RetDataBean deleteDingDingAccount(HttpServletRequest request,String accountId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		Account account1 = new Account();
		account1.setAccountId(accountId);
		account1.setOrgId(account.getOrgId());
		account1=accountService.selectOneAccount(account1);
		return userInfoService.deleteDingDingAccount(account1);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: createWeiXinAccount   
 * @Description: TODO 创建微信账号
 * @param request
 * @param accountId
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/createWeiXinAccount",method=RequestMethod.POST)
public RetDataBean createWeiXinAccount(HttpServletRequest request,String accountId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		UserInfo userInfo = new UserInfo();
		userInfo.setAccountId(accountId);
		userInfo.setOrgId(account.getOrgId());
		userInfo=userInfoService.selectOneUserInfo(userInfo);
		if(StringUtils.isBlank(userInfo.getMobileNo()))
		{
			return RetDataTools.NotOk(userInfo.getUserName()+"的手机号码不能为空！");
		}else {
			if(!StrTools.isMobile(userInfo.getMobileNo()))
			{
				return RetDataTools.NotOk(userInfo.getUserName()+"的手机号码不下确！");
			}
		}
		return userInfoService.createWeiXinAccount(userInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: createDingDingAccount   
 * @Description: TODO 创建钉钉用户
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/createDingDingAccount",method=RequestMethod.POST)
public RetDataBean createDingDingAccount(HttpServletRequest request,String accountId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		UserInfo userInfo = new UserInfo();
		userInfo.setAccountId(accountId);
		userInfo.setOrgId(account.getOrgId());
		userInfo=userInfoService.selectOneUserInfo(userInfo);
		if(StringUtils.isBlank(userInfo.getMobileNo()))
		{
			return RetDataTools.NotOk(userInfo.getUserName()+"的手机号码不能为空！");
		}else {
			if(!StrTools.isMobile(userInfo.getMobileNo()))
			{
				return RetDataTools.NotOk(userInfo.getUserName()+"的手机号码不下确！");
			}
		}
		return userInfoService.createDingDingAccount(userInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateUnit   
 * @Description: TODO 更新单位信息 
 * @param request
 * @param unit
 * @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/updateUnit",method=RequestMethod.POST)
public RetDataBean updateUnit(HttpServletRequest request, Unit unit)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}
		unit.setOrgId(account.getOrgId());
		Example example = new Example(Unit.class);
		example.createCriteria().andEqualTo("orgId", account.getOrgId());
		return RetDataTools.Ok("更新单位信息成功！",unitService.updateUnit(unit, example));
	}catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
		return RetDataTools.Error();
	}
}

/**
 * 
 * @Title: stopAccount   
 * @Description: TODO 账号禁用
 * @param: request
 * @param: accountId
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/stopAccount",method=RequestMethod.POST)
public RetDataBean stopAccount(HttpServletRequest request,String accountId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}
		if(account.getAccountId().equals("admin"))
		{
			return RetDataTools.NotOk("对不起,系统管理员账户不能被禁用!"); 
		}
		Account accountNew = new Account();
		accountNew.setNotLogin("1");
		Example example = new Example(Account.class);
		example.createCriteria().andEqualTo("orgId", account.getOrgId()).andEqualTo("accountId",accountId);
		return RetDataTools.Ok("账号禁用成功！",accountService.updateAccount(accountNew, example));
	}catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
		return RetDataTools.Error();
	}
}

@RequestMapping(value="/openAccount",method=RequestMethod.POST)
public RetDataBean openAccount(HttpServletRequest request,String accountId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}
		Account accountNew = new Account();
		accountNew.setNotLogin("0");
		Example example = new Example(Account.class);
		example.createCriteria().andEqualTo("orgId", account.getOrgId()).andEqualTo("accountId",accountId);
		return RetDataTools.Ok("账号启用成功！",accountService.updateAccount(accountNew, example));
	}catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
		return RetDataTools.Error();
	}
}

/**
 * 
 * @Title: goWeiXinSyncUnitDept   
 * @Description: TODO 微信同步部门
 * @param request
 * @param unitDept
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/weixinsync",method=RequestMethod.POST)
public RetDataBean goWeiXinSyncUnitDept(HttpServletRequest request,UnitDept unitDept)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		unitDept.setOrgId(account.getOrgId());
		unitDept = unitDeptService.selectOneUnitDept(unitDept);
		return unitDeptService.wxSyncUnitDept(unitDept) ;
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: goDingDingSyncUnitDept   
 * @Description: TODO 钉钉同步部门
 * @param: request
 * @param: unitDept
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/dingdingsync",method=RequestMethod.POST)
public RetDataBean goDingDingSyncUnitDept(HttpServletRequest request,UnitDept unitDept)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		unitDept.setOrgId(account.getOrgId());
		unitDept = unitDeptService.selectOneUnitDept(unitDept);
		return unitDeptService.dingDingSyncUnitDept(unitDept) ;
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: goDingDingUpdateUnitDept   
 * @Description: TODO 钉钉更新部门
 * @param: request
 * @param: unitDept
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/dingdingupdate",method=RequestMethod.POST)
public RetDataBean goDingDingUpdateUnitDept(HttpServletRequest request,UnitDept unitDept)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		unitDept.setOrgId(account.getOrgId());
		unitDept = unitDeptService.selectOneUnitDept(unitDept);
		return unitDeptService.dingDingUpdateUnitDept(unitDept) ;
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: goWxUpdateUnitDept   
 * @Description: TODO 微信更新部门信息
 * @param request
 * @param unitDept
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/weixinupdate",method=RequestMethod.POST)
public RetDataBean goWxUpdateUnitDept(HttpServletRequest request,UnitDept unitDept)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		unitDept.setOrgId(account.getOrgId());
		unitDept = unitDeptService.selectOneUnitDept(unitDept);
		return unitDeptService.wxUpdateUnitDept(unitDept) ;
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: addUnitDept   
 * @Description: TODO 创建新部门
 * @param request
 * @param unitDept
 * @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/addUnitDept",method=RequestMethod.POST)
public RetDataBean addUnitDept(HttpServletRequest request,UnitDept unitDept)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		unitDept.setDeptId(SysTools.getGUID());
		if(StringUtils.isBlank(unitDept.getOrgLeaveId()))
		{
			unitDept.setOrgLeaveId("0");
		}
		unitDept.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建部门成功！",unitDeptService.insertUnitDept(unitDept)) ;
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: importUnitDept   
 * @Description: TODO 批量导入部门
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/importUnitDept",method=RequestMethod.POST)
public RetDataBean importUnitDept(HttpServletRequest request,MultipartFile file)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return unitDeptService.importUnitDept(account,file);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: importUserInfo   
 * @Description: TODO 指量导入人员账号  
 * @param: request
 * @param: file
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/importUserInfo",method=RequestMethod.POST)
public RetDataBean importUserInfo(HttpServletRequest request,MultipartFile file)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return userInfoService.importUserInfo(account,file);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateUnitDept   
 * @Description: TODO 更新部门信息
 * @param request
 * @param unitDept
 * @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/updateUnitDept",method=RequestMethod.POST)
public RetDataBean updateUnitDept(HttpServletRequest request,UnitDept unitDept)
{
	try
	{
		if(StringUtils.isBlank(unitDept.getDeptId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(unitDept.getOrgLeaveId()))
		{
			unitDept.setOrgLeaveId("0");
		}
		unitDept.setOrgId(account.getOrgId());
		Example example = new Example(UnitDept.class);
		example.createCriteria().andEqualTo("deptId",unitDept.getDeptId()).andEqualTo("orgId",unitDept.getOrgId());
		return RetDataTools.Ok("更新部门成功！",unitDeptService.updateUnitDept(unitDept,example)) ;
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: delUnitDept   
 * @Description: 删除部门信息
 * @param request
 * @param unitDept
 * @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/delUnitDept",method=RequestMethod.POST)
public RetDataBean delUnitDept(HttpServletRequest request,UnitDept unitDept)
{
	try
	{
		if(StringUtils.isBlank(unitDept.getDeptId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		unitDept.setOrgId(account.getOrgId());
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}
		if(unitDeptService.isExistChild(unitDept.getDeptId(), account.getOrgId())>0)
		{
			return RetDataTools.NotOk("当前部门下还有子部门,若要删除,请先删除子部门!");
		}else
		{
			if(userInfoService.isExistChild(unitDept.getDeptId(),account.getOrgId())>0)
			{
				return RetDataTools.NotOk("当前部门下还有用户,若要删除,请先删除用户!");
			}else
			{
				return RetDataTools.Ok("删除部门成功！",unitDeptService.deleteUnitDept(unitDept));
			}
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title delSysMenu   
 * @Description TODO 删除系统菜单
 * @param request
 * @param sysMenu
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/delSysMenu",method=RequestMethod.POST)
public RetDataBean delSysMenu(HttpServletRequest request,SysMenu sysMenu)
{
	try
	{
		if(StringUtils.isBlank(sysMenu.getSysMenuId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		sysMenu.setOrgId(account.getOrgId());
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}
		if(sysMenuService.isExistChild(sysMenu.getSysMenuId(), account.getOrgId())>0)
		{
			return RetDataTools.NotOk("当前菜单下还有子菜单,若要删除,请先删除子菜单!");
		}else
		{
				return RetDataTools.Ok("删除菜单成功！",sysMenuService.delSysMenu(sysMenu));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title updateSysMenu   
 * @Description TODO 更新菜单信息  
 * @param request
 * @param sysMenu
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/updateSysMenu",method=RequestMethod.POST)
public RetDataBean updateSysMenu(HttpServletRequest request,SysMenu sysMenu)
{
	try
	{
		if(StringUtils.isBlank(sysMenu.getSysMenuId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		sysMenu.setOrgId(account.getOrgId());
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			Example example = new Example(SysMenu.class);
			example.createCriteria().andEqualTo("sysMenuId",sysMenu.getSysMenuId()).andEqualTo("orgId",sysMenu.getOrgId());
			return RetDataTools.Ok("更新菜单成功！",sysMenuService.updateSysMenu(sysMenu, example));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title insertSysMenu   
 * @Description TODO 创建新菜单
 * @param request
 * @param sysMenu
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertSysMenu",method=RequestMethod.POST)
public RetDataBean insertSysMenu(HttpServletRequest request,SysMenu sysMenu)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		//sysMenu.setSysMenuId(SysTools.getGUID());
		if(StringUtils.isBlank(sysMenu.getSysMenuLeave()))
		{
			sysMenu.setSysMenuLeave("0");
		}
		sysMenu.setOrgId(account.getOrgId());
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			return RetDataTools.Ok("添加菜单成功！",sysMenuService.insertSysMenu(sysMenu));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: deleteUserPriv   
 * @Description: TODO 删除权限
 * @param request
 * @param userPriv
 * @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/deleteUserPriv",method=RequestMethod.POST)
public RetDataBean deleteUserPriv(HttpServletRequest request,UserPriv userPriv)
{
	try
	{
		if(StringUtils.isBlank(userPriv.getUserPrivId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		userPriv.setOrgId(account.getOrgId());
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			return RetDataTools.Ok("删除权限成功！",userPrivService.deleteUserPriv(userPriv));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: insertUserPriv   
 * @Description: TODO 添加权限
 * @param request
 * @param userPriv
 * @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/insertUserPriv",method=RequestMethod.POST)
public RetDataBean insertUserPriv(HttpServletRequest request,UserPriv userPriv)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		userPriv.setOrgId(account.getOrgId());
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			userPriv.setUserPrivId(SysTools.getGUID());
			userPriv.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			userPriv.setCreateUser(account.getAccountId());
			userPriv.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加权限成功！",userPrivService.insertUserPriv(userPriv));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title updateUserPriv   
 * @Description TODO按条件更新对应的权限实例 
 * @param request
 * @param userPriv
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/updateUserPriv",method=RequestMethod.POST)
public RetDataBean updateUserPriv (HttpServletRequest request,UserPriv userPriv)
{
	try
	{
		if(StringUtils.isBlank(userPriv.getUserPrivId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(UserPriv.class);
		example.createCriteria().andEqualTo("userPrivId",userPriv.getUserPrivId()).andEqualTo("orgId",account.getOrgId());
		return RetDataTools.Ok("更新权限成功！",userPrivService.updateUserPriv(userPriv, example));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title copyPriv   
 * @Description TODO 克隆权限
 * @param request
 * @param userPriv
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/copyPriv",method=RequestMethod.POST)
public RetDataBean copyPriv (HttpServletRequest request,UserPriv userPriv)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
		userPriv.setOrgId(account.getOrgId());
		if(userPrivService.copyPriv(userPriv)>0)
		{
			return RetDataTools.Ok("克隆权限成功！");
		}else
		{
			
			return RetDataTools.NotOk("克隆权限失败！");
		}
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: deleteUserInfos   
 * @Description: TODO 批量删除账户
 * @param request
 * @param accountIds
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/deleteUserInfos",method=RequestMethod.POST)
public RetDataBean deleteUserInfos (HttpServletRequest request,String accountIds)
{
	try
	{
		if(StringUtils.isBlank(accountIds))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		String [] accountIdArr = accountIds.split(",");
		List<String> accountIdList = Arrays.asList(accountIdArr);
		if(accountIdList.contains("admin"))
		{
			return RetDataTools.NotOk("批量删除账号是不能包含系统管理员账号！");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			return RetDataTools.Ok("册除用户信息成功!",userInfoService.deleteAccountAndUserInfos(account,accountIdList));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title deleteUserInfo   
 * @Description TODO 删除账户与用户信息
 * @param request
 * @param userInfo
 * @param uAccount
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/deleteUserInfo",method=RequestMethod.POST)
public RetDataBean deleteUserInfo (HttpServletRequest request,UserInfo userInfo,Account uAccount)
{
	try
	{
		if(StringUtils.isBlank(userInfo.getAccountId())&&StringUtils.isBlank(uAccount.getAccountId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			if(uAccount.getAccountId().equals("admin"))
			{
				return RetDataTools.NotOk("对不起,系统管理员账号为系统账户不能被删除!"); 
			}else
			{
			userInfo.setOrgId(account.getOrgId());
			uAccount.setOrgId(account.getOrgId());
			return RetDataTools.Ok("册除用户信息成功!",userInfoService.deleteAccountAndUserInfo(uAccount,userInfo));
			}
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title insertAccountAndUserInfo   
 * @Description TODO 添加账户与用户
 * @param request
 * @param userInfo
 * @param uAccount
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertAccountAndUserInfo",method=RequestMethod.POST)
public RetDataBean insertAccountAndUserInfo (HttpServletRequest request,UserInfo userInfo,Account uAccount)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			userInfo.setUserInfoId(SysTools.getGUID());
			userInfo.setPinYin(StrTools.getPingYin(userInfo.getUserName()));
			userInfo.setOrgId(account.getOrgId());
			uAccount.setOrgId(account.getOrgId());
			uAccount.setPassWord(Md5CaculateUtil.MD5(uAccount.getAccountId()+uAccount.getPassWord()));
			uAccount.setNotLogin("0");
			return userInfoService.insertAccountAndUserInfo(uAccount,userInfo);
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: updateMyUserInfo   
 * @Description: TODO 更新个人信息
 * @param: request
 * @param: userInfo
 * @param: uAccount
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateMyUserInfo",method=RequestMethod.POST)
public RetDataBean updateMyUserInfo (HttpServletRequest request,UserInfo userInfo,Account uAccount)
{
	try
	{
			Account account=accountService.getRedisAccount(request);
			userInfo.setOrgId(account.getOrgId());
			userInfo.setPinYin(StrTools.getPingYin(userInfo.getUserName()));
			userInfo.setAccountId(account.getAccountId());
			uAccount.setOrgId(account.getOrgId());
			uAccount.setAccountId(account.getAccountId());
			if(StringUtils.isNotBlank(uAccount.getPassWord()))
			{
				uAccount.setPassWord(null);
			}
			Example aexample = new Example(Account.class);
			Example uexample = new Example(UserInfo.class);
			aexample.createCriteria().andEqualTo("accountId",uAccount.getAccountId()).andEqualTo("orgId",uAccount.getOrgId());
			uexample.createCriteria().andEqualTo("accountId",userInfo.getAccountId()).andEqualTo("orgId",userInfo.getOrgId());
			return RetDataTools.Ok("个人信息更新成功!",userInfoService.updateAccountAndUserInfo(uAccount, aexample, userInfo, uexample));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title updateAccountAndUserInfo   
 * @Description TODO更新账户与用户信息
 * @param request
 * @param userInfo
 * @param uAccount
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/updateAccountAndUserInfo",method=RequestMethod.POST)
public RetDataBean updateAccountAndUserInfo (HttpServletRequest request,UserInfo userInfo,Account uAccount)
{
	try
	{
		if(StringUtils.isBlank(userInfo.getAccountId())&&StringUtils.isBlank(uAccount.getAccountId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			userInfo.setOrgId(account.getOrgId());
			uAccount.setOrgId(account.getOrgId());
			if(StringUtils.isBlank(uAccount.getPassWord()))
			{
				uAccount.setPassWord("123456");
			}
			uAccount.setPassWord(Md5CaculateUtil.MD5(uAccount.getAccountId()+uAccount.getPassWord()));
			if(StringUtils.isBlank(uAccount.getPassWord()))
			{
				uAccount.setPassWord(null);
			}
			Example aexample = new Example(Account.class);
			Example uexample = new Example(UserInfo.class);
			aexample.createCriteria().andEqualTo("accountId",uAccount.getAccountId()).andEqualTo("orgId",uAccount.getOrgId());
			uexample.createCriteria().andEqualTo("accountId",userInfo.getAccountId()).andEqualTo("orgId",userInfo.getOrgId());
			return RetDataTools.Ok("更新账户与用户信息成功!",userInfoService.updateAccountAndUserInfo(uAccount, aexample, userInfo, uexample));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title addUserLevel   
 * @Description TODO 创建用户行政级别
 * @param request
 * @param userLeave
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/addUserLevel",method=RequestMethod.POST)
public RetDataBean addUserLevel (HttpServletRequest request,UserLevel userLevel)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			userLevel.setLevelId(SysTools.getGUID());
			userLevel.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			userLevel.setCreateUser(account.getAccountId());
			userLevel.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建用户行政级别成功!",userLevelService.insertUserLevel(userLevel));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title updateUserLevel   
 * @Description TODO 更新用户行政级别
 * @param request
 * @param userLeave
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/updateUserLevel",method=RequestMethod.POST)
public RetDataBean updateUserLevel (HttpServletRequest request,UserLevel userLevel)
{
	try
	{
		if(StringUtils.isBlank(userLevel.getLevelId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			userLevel.setOrgId(account.getOrgId());
			Example example = new Example(UserLevel.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("LevelId",userLevel.getLevelId());
			return RetDataTools.Ok("更新用户行政级别成功!",userLevelService.updateUserLevel(userLevel, example));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title delUserLevel   
 * @Description TODO删除用户行政经别
 * @param request
 * @param userLeave
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/delUserLevel",method=RequestMethod.POST)
public RetDataBean delUserLevel (HttpServletRequest request,UserLevel userLevel)
{
	try
	{
		if(StringUtils.isBlank(userLevel.getLevelId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			userLevel.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除用户行政级别成功!",userLevelService.deleteUserLevel(userLevel));
		}
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title resetPassWord   
 * @Description TODO 更改密码
 * @param request
 * @param firstPassWord
 * @param newPassWord
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/resetPassWord",method=RequestMethod.POST)
public RetDataBean resetPassWord (HttpServletRequest request,String firstPassWord,String newPassWord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		String accountId = account.getAccountId();
		return accountService.resetPassWord(account,Md5CaculateUtil.MD5(accountId+firstPassWord),newPassWord);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: setDeskConfig   
 * @Description: TODO 桌面设置
 * @param: request
 * @param: homePage
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/setDeskConfig",method=RequestMethod.POST)
public RetDataBean setDeskConfig (HttpServletRequest request,String homePage)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		Account newAccount = new Account();
		newAccount.setHomePage(homePage);
		newAccount.setOrgId(account.getOrgId());
		newAccount.setAccountId(account.getAccountId());
		Example example = new Example(Account.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("accountId",account.getAccountId());
		if(accountService.updateAccount(newAccount, example)==1)
		{
		HttpSession session = request.getSession(true);
		account.setHomePage(homePage);
		LoginAccountInfo loginAccountInfo = accountService.getRedisLoginAccountInfo(request);
		loginAccountInfo.setAccount(account);
		redisUtil.set("account_"+session.getId(), loginAccountInfo);
		}
		return RetDataTools.Ok("桌面设置成功!","");
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

}
