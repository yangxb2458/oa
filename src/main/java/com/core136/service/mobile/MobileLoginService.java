package com.core136.service.mobile;

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
public class MobileLoginService {
	@Value("${validity.landing}")
	private Integer validity;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserPrivService userPrivService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 
	 * @Title: mobileLogin
	 * @Description: TODO 移动端登陆
	 * @param request
	 * @param accountId
	 * @param orgId
	 * @throws ApiException
	 * void    
	 * @throws
	 */
	public boolean mobileLogin(HttpServletRequest request,String accountId,String passWord) throws ApiException
	{
		LoginAccountInfo loginAccountInfo = new LoginAccountInfo();
		boolean isRegist = SysRunConfig.getIsRegist();
		if(isRegist)
		{
		HttpSession session = request.getSession(true);
		Account account = accountService.mobileLogin(passWord, accountId);
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
				loginAccountInfo.setAccount(account);
				UserInfo userInfo  = userInfoService.getUserInfoByAccountId(account.getAccountId(), account.getOrgId());
				loginAccountInfo.setUserInfo(userInfo);
				Unit unit  = new Unit();
				unit.setOrgId(userInfo.getOrgId());
				unit = unitService.selectOne(unit);
				session.setAttribute("UNIT", unit);
				loginAccountInfo.setUnit(unit);
				if(unit.getOrgName().equals(""))
				{
					session.setAttribute("SOFT_NAME", AppGobalConstant.SOFT_NAME);
					loginAccountInfo.setSoftName(AppGobalConstant.SOFT_NAME);
				}else
				{
					session.setAttribute("SOFT_NAME", unit.getOrgName());
					loginAccountInfo.setSoftName(unit.getOrgName());
				}
				redisUtil.set("account_"+session.getId(), loginAccountInfo);
				redisUtil.expire("account_"+session.getId(), validity*60);
				session.setAttribute("onLinUser", new OnLineUser(account.getAccountId()));
				accountService.updateLastLoginTime(account);
				sysLogService.createLog(request, account,EventTypeEnums.SYS_LOG_LOGIN,"移动端登陆成功");
				return true;
		}else
			{
				sysLogService.createLog(request, account,EventTypeEnums.SYS_LOG_LOGIN,"移动端登陆失败");
				return false;
			}
		}else
		{
			return false;
		}
	}
}
