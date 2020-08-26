package com.core136.service.dingding;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.core136.common.SysRunConfig;
import org.core136.common.auth.LoginAccountInfo;
import org.core136.common.enums.AppGobalConstant;
import org.core136.common.enums.EventTypeEnums;
import org.core136.common.utils.StrTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.account.Unit;
import com.core136.bean.account.UserInfo;
import com.core136.bean.account.UserPriv;
import com.core136.bean.sys.SysMenu;
import com.core136.service.account.AccountService;
import com.core136.service.account.UnitService;
import com.core136.service.account.UserInfoService;
import com.core136.service.account.UserPrivService;
import com.core136.service.sys.OnLineUser;
import com.core136.service.sys.SysLogService;
import com.core136.service.sys.SysMenuService;
import com.core136.unit.RedisUtil;
import com.taobao.api.ApiException;

@Service
public class DingDingLoginService {
	@Value("${validity.landing}")
	private Integer validity;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private UserPrivService userPrivService;
	@Autowired
	private RedisUtil redisUtil;

/**
 * 
 * @Title: dingDingLogin   
 * @Description: TODO 钉钉客户端当前用户登陆
 * @param: request
 * @param: dAccountId
 * @param: orgId
 * @param: @throws ApiException      
 * @return: void      
 * @throws
 */
	public void dingDingLogin(HttpServletRequest request,String dAccountId,String orgId) throws ApiException
	{
		LoginAccountInfo loginAccountInfo = new LoginAccountInfo();
		boolean isRegist = SysRunConfig.getIsRegist();
		if(isRegist)
		{
		HttpSession session = request.getSession(true);
		Account account = accountService.dingLogin(orgId, dAccountId);
		if(account !=null) {
			String userPrivIds = account.getUserPriv();
			if(StringUtils.isNotBlank(userPrivIds))
			{
				String [] privIdArr = userPrivIds.split(",");
				String sysMenuIds="";
				String mobilePrivIds="";
				for(int i=0;i<privIdArr.length;i++)
				{
					if(StringUtils.isNotBlank(privIdArr[i]))
					{
					UserPriv userPriv = userPrivService.getUserPrivByPrivId(privIdArr[i], account.getOrgId());
					sysMenuIds+=userPriv.getUserPrivStr()+",";
					mobilePrivIds+=userPriv.getMobilePriv()+",";
					}
				}
				List<String> sysMenuIdList = StrTools.strToList(sysMenuIds);
				List<String> mobilePrivList = StrTools.strToList(mobilePrivIds);
				List<SysMenu> sysMenuList = sysMenuService.getSysMenuByAccount(sysMenuIdList, account.getOrgId());
				loginAccountInfo.setSysMenuList(sysMenuList);
				loginAccountInfo.setMobilePrivList(mobilePrivList);
				}
				Unit unit  = new Unit();
				unit.setOrgId(orgId);
				unit = unitService.selectOne(unit);
				session.setAttribute("UNIT", unit);
				if(unit.getOrgName().equals(""))
				{
					session.setAttribute("SOFT_NAME", AppGobalConstant.SOFT_NAME);
					loginAccountInfo.setSoftName(AppGobalConstant.SOFT_NAME);
				}else
				{
					session.setAttribute("SOFT_NAME", unit.getOrgName());
					loginAccountInfo.setSoftName(unit.getOrgName());
				}
				loginAccountInfo.setAccount(account);
				UserInfo userInfo  = userInfoService.getUserInfoByAccountId(account.getAccountId(), account.getOrgId());
				loginAccountInfo.setUserInfo(userInfo);
				redisUtil.set("account_"+session.getId(), loginAccountInfo);
				redisUtil.expire("account_"+session.getId(), validity*60);
				session.setAttribute("onLinUser", new OnLineUser(account.getAccountId()));
				accountService.updateLastLoginTime(account);
				sysLogService.createLog(request, account,EventTypeEnums.SYS_LOG_LOGIN,"钉钉登陆成功");
		}else
			{
				sysLogService.createLog(request, account,EventTypeEnums.SYS_LOG_LOGIN,"钉钉登陆失败");
			}
		}
	}
	
	/**
	 * 
	 * @Title: getDAccount   
	 * @Description: TODO 钉钉获取当前用户
	 * @param: request
	 * @param: dingUserId
	 * @param: dingDeviceId
	 * @param: orgId
	 * @param: @return
	 * @param: @throws ApiException      
	 * @return: Account      
	 * @throws
	 */
	public Account getDAccount(HttpServletRequest request,String dAccountId,String orgId) throws ApiException
	{
		Unit unit = new Unit();
		unit.setOrgId(orgId);
		unit = unitService.selectOne(unit);
		Account account=accountService.getRedisAccount(request);
		if(account==null)
		{
			dingDingLogin(request,dAccountId,orgId);
			account=accountService.getRedisAccount(request);
		}
		return account;
	}
	
	/**
	 * 
	 * @Title: getDUserinfo   
	 * @Description: TODO 获取用户信息
	 * @param: request
	 * @param: dingUserId
	 * @param: dingDeviceId
	 * @param: orgId
	 * @param: @return
	 * @param: @throws ApiException      
	 * @return: UserInfo      
	 * @throws
	 */
	public UserInfo getDUserinfo(HttpServletRequest request,String dAccountId,String orgId) throws ApiException
	{
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		if(userInfo==null)
		{
			dingDingLogin(request,dAccountId,orgId);
			userInfo=accountService.getRedisUserInfo(request);
		}
		return userInfo;
	}
	
	
}
