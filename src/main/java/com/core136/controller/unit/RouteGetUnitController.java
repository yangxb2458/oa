/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RouteGetUnitController.java   
 * @Package com.core136.controller.unit   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 刘绍全     
 * @date:   2018年12月23日 下午10:13:33   
 * @version V1.0 
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.core136.bean.account.Account;
import com.core136.bean.account.Unit;
import com.core136.bean.account.UnitDept;
import com.core136.bean.account.UserGroup;
import com.core136.bean.account.UserLevel;
import com.core136.bean.account.UserPriv;
import com.core136.bean.sys.PageParam;
import com.core136.bean.sys.SysMenu;
import com.core136.service.account.AccountService;
import com.core136.service.account.UnitDeptService;
import com.core136.service.account.UnitService;
import com.core136.service.account.UserGroupService;
import com.core136.service.account.UserInfoService;
import com.core136.service.account.UserLevelService;
import com.core136.service.account.UserPrivService;
import com.core136.service.oa.DiaryService;
import com.core136.service.sys.SysMenuService;

import org.core136.common.SessionMap;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  RouteGetUnitController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 刘绍全 
 * @date:   2018年12月23日 下午10:13:33   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云科信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/ret/unitget")
public class RouteGetUnitController {
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private UnitDeptService unitDeptService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserPrivService userPrivService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private UserLevelService userLevelService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private DiaryService diaryService;
	/**
	 * 
	 * @Title: getUserOnLineList   
	 * @Description: TODO 获取在线人员列表
	 * @param request
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getUserOnLineList",method=RequestMethod.POST)
	public RetDataBean getUserOnLineList(HttpServletRequest request)
	{
		try
		{
			return RetDataTools.Ok("请求成功！",SessionMap.getOnLineAccountList());
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getUserOnLineCount   
	 * @Description: TODO 获取在线人数
	 * @param request
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getUserOnLineCount",method=RequestMethod.POST)
	public RetDataBean getUserOnLineCount(HttpServletRequest request)
	{
		try
		{
			return RetDataTools.Ok("请求成功！",SessionMap.getOnLineAccountCount());
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getSelectUserByGroupId   
	 * @Description: TODO 获取个人分组人员列表
	 * @param request
	 * @param userGroup
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getSelectUserByGroupId",method=RequestMethod.POST)
	public RetDataBean getSelectUserByGroupId(HttpServletRequest request,UserGroup userGroup)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			userGroup.setOrgId(account.getOrgId());
			userGroup = userGroupService.selectOneUserGroup(userGroup);
			return RetDataTools.Ok("请求成功！",userInfoService.getSelectUserByGroupId(account.getOrgId(),userGroup));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getUserGroupListByAccountId   
	 * @Description: TODO 获取个人用户分组列表
	 * @param request
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getUserGroupListByAccountId",method=RequestMethod.POST)
	public RetDataBean getUserGroupListByAccountId(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",userGroupService.getUserGroupListByAccountId(account.getOrgId(),account.getAccountId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getSelectUserByLevelId   
	 * @Description: TODO 获取职务内的人员
	 * @param request
	 * @param levelId
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getSelectUserByLevelId",method=RequestMethod.POST)
	public RetDataBean getSelectUserByLevelId(HttpServletRequest request,String levelId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",userInfoService.getSelectUserByLevelId(account.getOrgId(),levelId));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMyUserGroup   
	 * @Description: TODO 获取我的好友分组
	 * @param request
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMyUserGroup",method=RequestMethod.POST)
	public RetDataBean getMyUserGroup(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",userGroupService.getMyUserGroup(account.getOrgId(),account.getAccountId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getUserGroupById   
	 * @Description: TODO 获取好友分组详情
	 * @param request
	 * @param userGroup
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getUserGroupById",method=RequestMethod.POST)
	public RetDataBean getUserGroupById(HttpServletRequest request,UserGroup userGroup)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			userGroup.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功！",userGroupService.selectOneUserGroup(userGroup));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getSearchUserForMobile   
	 * @Description: TODO 移动端查询人员 
	 * @param: request
	 * @param: search
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getSearchUserForMobile",method=RequestMethod.POST)
	public RetDataBean getSearchUserForMobile(HttpServletRequest request,String search)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",userInfoService.getSearchUserForMobile(account.getOrgId(),search));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getHomePageByAccountId   
	 * @Description: TODO 获取通讯录的中的个人主页信息
	 * @param: request
	 * @param: accountId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getHomePageByAccountId",method=RequestMethod.POST)
	public RetDataBean getHomePageByAccountId(HttpServletRequest request,String accountId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",diaryService.getHomePageByAccountId(account.getOrgId(),accountId));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @Title: getDeptTableTreeList   
	 * @Description: TODO 获取公司部门表树结构
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getDeptTableTreeList",method=RequestMethod.POST)
	public RetDataBean getDeptTableTreeList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",unitDeptService.getDeptTableTreeList(account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getMyHomePage   
	 * @Description: TODO 获取个人的桌面设置   
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMyHomePage",method=RequestMethod.POST)
	public RetDataBean getMyHomePage(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",JSONObject.parse(account.getHomePage()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getAllUserInfoByAccountList   
	 * @Description: TODO 获取账户字符串对应的人员信息  
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getAllUserInfoByAccountList",method=RequestMethod.POST)
	public RetDataBean getAllUserInfoByAccountList(HttpServletRequest request,String accountStrs)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isNotBlank(accountStrs))
			{
				List<String> list = new ArrayList<String>();
				String [] aArr=null;
				if(accountStrs.indexOf(",")>0)
				{
					aArr = accountStrs.split(",");
				}else
				{
					aArr = new String[] {accountStrs};
				}
				list = new ArrayList<String>(Arrays.asList(aArr));
				return RetDataTools.Ok("请求成功！",userInfoService.getAllUserInfoByAccountList(account.getOrgId(), list));
			}else
			{
				return RetDataTools.NotOk("请求的参数有问题！请检查！"); 
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getUnitInfo 
	* @Description: TODO 获取单位信息
	* @param @param request
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getUnitInfo",method=RequestMethod.POST)
	public RetDataBean getUnitInfo(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			Unit unit = new Unit();
			unit.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功！",unitService.selectOne(unit));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getUnitDeptTree   
	 * @Description: TODO 获取部门树结构   
	 * @param request
	 * @param sortId
	 * @return      
	 * @return: List<Map<String,Object>>      

	 */
	@RequestMapping(value="/getUnitDeptTree",method=RequestMethod.POST)
	public List<Map<String,Object>> getUnitDeptTree(HttpServletRequest request,String deptId)
	{
		try
		{
			String orgLeaveId = "0";
			if(StringUtils.isNotBlank(deptId))
			{
				orgLeaveId = deptId;
			}
			Account account=accountService.getRedisAccount(request);
			return unitDeptService.getUnitDeptTree(orgLeaveId,account.getOrgId());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getUnitDeptForUserInfoTree   
	 * @Description: TODO 部门作为父级的树 
	 * @param: request
	 * @param: deptId
	 * @param: @return      
	 * @return: List<Map<String,Object>>      
	 * @throws
	 */
	@RequestMapping(value="/getUnitDeptForUserInfoTree",method=RequestMethod.POST)
	public List<Map<String,Object>> getUnitDeptForUserInfoTree(HttpServletRequest request,String deptId)
	{
		try
		{
			String orgLeaveId = "0";
			if(StringUtils.isNotBlank(deptId))
			{
				orgLeaveId = deptId;
			}
			Account account=accountService.getRedisAccount(request);
			return unitDeptService.getUnitDeptForUserInfoTree(orgLeaveId,account.getOrgId());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
/**
 * 
 * @Title getUserPrivNamesByIds   
 * @Description TODO 获取权限名称
 * @param request
 * @param userPrivIds
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getUserPrivNamesByIds",method=RequestMethod.POST)
public RetDataBean getUserPrivNamesByIds(HttpServletRequest request,String userPrivIds)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(userPrivIds))
			{
				return RetDataTools.Error("参数错误！");
			}else
			{
				String [] tmp = userPrivIds.split(",");
				List<String> list = Arrays.asList(tmp);
				return RetDataTools.Ok("请求成功！",userPrivService.getUserPrivNamesByIds(list, account.getOrgId()));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}	
	/**
	 * 
	 * @Title: getUnitDeptById   
	 * @Description: TODO 按DeptId 获取部门信息
	 * @param request
	 * @param unitDept
	 * @return      
	 * @return: RetDataBean      

	 */
	@RequestMapping(value="/getUnitDeptById",method=RequestMethod.POST)
	public RetDataBean getUnitDeptById(HttpServletRequest request,UnitDept unitDept)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			unitDept.setOrgId(account.getOrgId());
			return RetDataTools.Ok("后台请求成功！",unitDeptService.getUnitDeptInfo(unitDept.getOrgId(),unitDept.getDeptId())) ;
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getLeaveUserInfo   
	 * @Description: TODO 获人员状态异常人员列表
	 * @param request
	 * @param pageParam
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getLeaveUserInfo",method=RequestMethod.POST)
	public RetDataBean getLeaveUserInfo(
			HttpServletRequest request,
			PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("U.SORT_NO");
			}else
			{
				pageParam.setSort("A."+StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(orderBy);
 		PageInfo<Map<String, String>> pageInfo=userInfoService.getLeaveUserInfoList(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @Title: getUserInfoByDeptId   
	 * @Description: TODO 获取部门下的所有人员
	 * @param request
	 * @param deptId
	 * @param pageNumber
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @param sortOrder
	 * @return      
	 * @return: RetDataBean      

	 */
	@RequestMapping(value="/getUserInfoByDeptId",method=RequestMethod.POST)
	public RetDataBean getUserInfoByDeptId(
			HttpServletRequest request,
			PageParam pageParam,
			String deptId
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("U.SORT_NO");
			}else
			{
				pageParam.setSort("A."+StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(orderBy);
 		PageInfo<Map<String, Object>> pageInfo=userInfoService.getUserInfoByDeptId(pageParam,deptId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getDingDingUserInfoByDeptId   
	 * @Description: TODO 获取钉钉账户绑定列表 
	 * @param: request
	 * @param: pageParam
	 * @param: deptId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getDingDingUserInfoByDeptId",method=RequestMethod.POST)
	public RetDataBean getDingDingUserInfoByDeptId(
			HttpServletRequest request,
			PageParam pageParam,
			String deptId
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("accountId");
			}else
			{
				pageParam.setSort("A."+StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(orderBy);
 		PageInfo<Map<String, Object>> pageInfo=userInfoService.getDingDingUserInfoByDeptId(pageParam,deptId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getWeiXinUserInfoByDeptId   
	 * @Description: TODO 获取微信账户绑定列表
	 * @param request
	 * @param pageParam
	 * @param deptId
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getWeiXinUserInfoByDeptId",method=RequestMethod.POST)
	public RetDataBean getWeiXinUserInfoByDeptId(
			HttpServletRequest request,
			PageParam pageParam,
			String deptId
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("accountId");
			}else
			{
				pageParam.setSort("A."+StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(orderBy);
 		PageInfo<Map<String, Object>> pageInfo=userInfoService.getWeiXinUserInfoByDeptId(pageParam,deptId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title getSysMenuTree   
	 * @Description TODO 获取菜单树结构   
	 * @param request
	 * @return      
	 * List<Map<String,Object>>
	 */
	@RequestMapping(value="/getSysMenuTree",method=RequestMethod.POST)
	public List<Map<String,Object>> getSysMenuTree(HttpServletRequest request)
	{
		String sysMenuLeave = request.getParameter("sysMenuId");
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return null;
		}
		try
		{
			if(StringUtils.isBlank(sysMenuLeave))
			{
				sysMenuLeave="0";
			}
			return sysMenuService.getSysMenuByLeave(sysMenuLeave, account.getOrgId());
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @Title getSysMenuById   
	 * @Description TODO 获取菜单通过ID  
	 * @param request
	 * @param sysMenu
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getSysMenuById",method=RequestMethod.POST)
	public RetDataBean getSysMenuById(HttpServletRequest request,SysMenu sysMenu)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			sysMenu.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功！",sysMenuService.selectOne(sysMenu));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title getUserInfoByDeptId   
	 * @Description TODO 获取部门下所有人员  
	 * @param request
	 * @param sysMenu
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getSelectUserByDeptId",method=RequestMethod.POST)
	public RetDataBean getUserInfoByDeptId(HttpServletRequest request,String deptId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",userInfoService.getSelectUserByDeptId(deptId, account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMyUserInfo   
	 * @Description: TODO 获取个人的基本信息
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMyUserInfo",method=RequestMethod.POST)
	public RetDataBean getMyUserInfo(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功！",userInfoService.getMyUserInfo(account.getOrgId(),account.getAccountId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getUserNamesByAccountIds   
	 * @Description TODO 按账户字符串获取用户姓名 
	 * @param request
	 * @param accountIds
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getUserNamesByAccountIds",method=RequestMethod.POST)
	public RetDataBean getUserNamesByAccountIds(HttpServletRequest request,String accountIds)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isNotBlank(accountIds))
			{	
				String[] paramsList;
				if(accountIds.indexOf(",")>-1)
				{
					paramsList = accountIds.split(",");
				}else
				{
					paramsList = new String[] {accountIds};
				}
				List<String> list = Arrays.asList(paramsList);
				return RetDataTools.Ok("请求成功！",accountService.getUserNamesByAccountIds(list, account.getOrgId()));
			}else
			{
				return RetDataTools.NotOk("请求失败！");
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title getUserInfoBySearchuser   
	 * @Description TODO 选择人员的人员查询
	 * @param request
	 * @param searchuser
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getUserInfoBySearchuser",method=RequestMethod.POST)
	public RetDataBean getUserInfoBySearchuser(HttpServletRequest request,String searchuser)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isNotBlank(searchuser))
			{
				return RetDataTools.Ok("请求成功！",accountService.getUserInfoBySearchuser(searchuser, account.getOrgId()));
			}else
			{
				return RetDataTools.NotOk("请输入查询条件!");
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @Title getDeptList   
	 * @Description TODO 按条件获取部门列表
	 * @param request
	 * @param unitDept
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getDeptList",method=RequestMethod.POST)
	public RetDataBean getDeptList(HttpServletRequest request,UnitDept unitDept)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(UnitDept.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("orgLeaveId",unitDept.getOrgLeaveId());
			return RetDataTools.Ok("请求成功！",unitDeptService.getDeptList(example));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getUnitDeptByDeptIds   
	 * @Description TODO 按部门ID字符串获取部门列表
	 * @param request
	 * @param deptIds
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getUnitDeptByDeptIds",method=RequestMethod.POST)
	public RetDataBean getUnitDeptByDeptIds(HttpServletRequest request,String deptIds)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isNotBlank(deptIds))
			{
				String[] paramsList = deptIds.split(",");
				List<String> list = Arrays.asList(paramsList);
				return RetDataTools.Ok("请求成功！",unitDeptService.getUnitDeptByDeptIds(list, account.getOrgId()));
			}else
			{
				return RetDataTools.NotOk("请求失败！");
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title getUnitDeptBySearchdept   
	 * @Description TODO 按名称查询部门  
	 * @param request
	 * @param searchdept
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getUnitDeptBySearchdept",method=RequestMethod.POST)
	public RetDataBean getUnitDeptBySearchdept(HttpServletRequest request,String searchdept)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isNotBlank(searchdept))
			{
				return RetDataTools.Ok("请求成功！",unitDeptService.getUnitDeptBySearchdept("%"+searchdept+"%", account.getOrgId()));
			}else
			{
				return RetDataTools.NotOk("请输入查询条件!");
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getSelectPrivList   
	 * @Description: TODO 获取备选权限列表
	 * @param request
	 * @return      
	 * @return: RetDataBean      

	 */
	@RequestMapping(value="/getSelectPrivList",method=RequestMethod.POST)
	public RetDataBean getSelectPrivList(HttpServletRequest request,String privIds)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			List<String> list = new ArrayList<String>();
			if(StringUtils.isNotBlank(privIds))
			{
				String[] privIdArr = privIds.split(",");
				list = Arrays.asList(privIdArr);
			}
			return RetDataTools.Ok("请求成功！",userPrivService.getSelectPrivList(list,account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getAllUserPriv   
	 * @Description: TODO获取用户权限列表  
	 * @param: request
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getAllUserPriv",method=RequestMethod.POST)
	public RetDataBean getAllUserPriv(HttpServletRequest request,PageParam pageParam)
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
		 pageParam.setOrderBy(pageParam.getSort()+" "+pageParam.getSortOrder());
		 Account account=accountService.getRedisAccount(request);
		 pageParam.setAccountId(account.getAccountId());
		 pageParam.setOrgId(account.getOrgId());
		 pageParam.setOpFlag(account.getOpFlag());
		 if(!pageParam.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("您不是系统管理员，请与系统管理员联系！");
			}else
			{
				PageInfo<UserPriv> pageInfo=userPrivService.getAllUserPriv(pageParam);
				return RetDataTools.Ok("请求数据成功!", pageInfo);
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title getUserPriv   
	 * @Description TODO 按条件获取对应的权限实例 
	 * @param request
	 * @param userPriv
	 * @return      
	 * RetDataBean
	 */
	@Transactional(value="generalTM")
	@RequestMapping(value="/getUserPriv",method=RequestMethod.POST)
	public RetDataBean getUserPriv(HttpServletRequest request,UserPriv userPriv)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("您不是系统管理员，请与系统管理员联系！");
			}else
			{
				userPriv.setOrgId(account.getOrgId());
				return RetDataTools.Ok("获取权限成功！",userPrivService.selectOne(userPriv));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getUserPrivTree   
	 * @Description TODO 获取权限设置时的菜单树与是否已选中 
	 * @param request
	 * @param userPrivId
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getUserPrivTree",method=RequestMethod.POST)
	public RetDataBean getUserPrivTree(HttpServletRequest request,String userPrivId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("您不是系统管理员，请与系统管理员联系！");
			}else
			{
				return RetDataTools.Ok("获取权限成功！",sysMenuService.getUserPrivTree(userPrivId, account.getOrgId()));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getAccountAndUserInfo   
	 * @Description TODO 获取账户与用户信息
	 * @param request
	 * @param accountId
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getAccountAndUserInfo",method=RequestMethod.POST)
	public RetDataBean getAccountAndUserInfo(HttpServletRequest request,String accountId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("您不是系统管理员，请与系统管理员联系！");
			}else
			{
				return RetDataTools.Ok("请求成功！",userInfoService.getAccountAndUserInfo(accountId, account.getOrgId()));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getUserLevelChart   
	 * @Description TODO 获取行政级别CHART数据
	 * @param request
	 * @param leaveId
	 * @return      
	 * List<Map<String,Object>>
	 */
	@RequestMapping(value="/getUserLevelChart",method=RequestMethod.POST)
	public Object getUserLevelChart(HttpServletRequest request,String levelId)
	{
		try
		{
			if(StringUtils.isBlank(levelId))
			{
				levelId = "0";
			}
			Account account=accountService.getRedisAccount(request);
			List<Map<String, Object>> ListMaper = userLevelService.getAllUserLevelChart(account.getOrgId(),levelId);
			if(ListMaper.size()>0)
			{
				return ListMaper.get(0);	
			}else
			{
				return null;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @Title getUserLevelList   
	 * @Description TODO 获取所有行政级别
	 * @param request
	 * @param accountId
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getUserLevelList",method=RequestMethod.POST)
	public RetDataBean getUserLevelList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(UserLevel.class);
			example.setOrderByClause("LEVEL_NO_ID DESC");
			example.createCriteria().andEqualTo("orgId",account.getOrgId());
			return RetDataTools.Ok("请求成功！",userLevelService.selectByExample(example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getUserLevel   
	 * @Description TODO 获取所有行政级别
	 * @param request
	 * @param accountId
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getUserLevel",method=RequestMethod.POST)
	public RetDataBean getUserLevel(HttpServletRequest request,UserLevel userLevel)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			userLevel.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功！",userLevelService.selectOne(userLevel));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title getUserLevelByLevelIds   
	 * @Description TODO 获取行政级别所有选择列表 
	 * @param request
	 * @param userLevelIds
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getUserLevelByLevelIds",method=RequestMethod.POST)
	public RetDataBean getUserLevelByLevelIds (HttpServletRequest request,String userLevelIds)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isNotBlank(userLevelIds))
			{
				String [] leaveArr;
				if(userLevelIds.indexOf(",")>=0)
				{
					leaveArr = userLevelIds.split(",");
				}else
				{
					leaveArr = new String[] {userLevelIds};
				}
				List<String> list = Arrays.asList(leaveArr);
				return RetDataTools.Ok("请求成功!",userLevelService.getUserLevelByLevelIds(account.getOrgId(), list));
			}else
			{
				return RetDataTools.NotOk("请求参数问题!请检查!"); 
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getUserInfoDeatilsByAccountId   
	 * @Description: TODO 获取用户详情信息
	 * @param: request
	 * @param: accountId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getUserInfoDeatilsByAccountId",method=RequestMethod.POST)
	public RetDataBean getUserInfoDeatilsByAccountId(HttpServletRequest request,String accountId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
				return RetDataTools.Ok("获取用户详情成功！",userInfoService.getUserInfoDeatilsByAccountId(account.getOrgId(),accountId));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
