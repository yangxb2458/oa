package com.core136.controller.sys;


import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.core136.bean.account.Account;
import com.core136.bean.account.Unit;
import com.core136.bean.account.UserInfo;
import com.core136.bean.account.UserPriv;
import com.core136.bean.email.Email;
import com.core136.bean.email.EmailBox;
import com.core136.bean.sys.DdConfig;
import com.core136.bean.sys.SysConfig;
import com.core136.bean.sys.SysInterface;
import com.core136.bean.sys.WxConfig;
import com.core136.service.account.AccountService;
import com.core136.service.account.UnitService;
import com.core136.service.account.UserInfoService;
import com.core136.service.account.UserPrivService;
import com.core136.service.email.EmailBoxService;
import com.core136.service.email.EmailService;
import com.core136.service.sys.AppConfigService;
import com.core136.service.sys.DdConfigService;
import com.core136.service.sys.SysConfigService;
import com.core136.service.sys.SysInterfaceService;
import com.core136.service.sys.SysProfileService;
import com.core136.service.sys.WxConfigService;
import com.core136.unit.SpringUtils;

import org.apache.commons.lang.StringUtils;
import org.core136.common.auth.LoginAccountInfo;
import org.core136.common.utils.SysTools;
/**
 * 
 * @ClassName:  AppPageController   
 * @Description:系统管理的Controller   
 * @author: 刘绍全
 * @date:   2018年10月18日 下午1:20:32   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Controller
@RequestMapping("/app/core")
public class AppPageController {
	@Autowired
	private UserPrivService userPrivService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailBoxService emailBoxService;
	@Autowired
	private SysInterfaceService sysInterfaceService;
	@Autowired
	private DdConfigService ddConfigService;
	@Autowired
	private SysProfileService sysProfileService;
	@Autowired
	private AppConfigService appConfigService;
	@Autowired
	private WxConfigService wxConfigService;
	@Autowired
	private AccountService accountService;
	
@Value("${app.msgtime}")	
private int msgtime;
@Value("${app.attachpath}")	
private String attach;

/**
 * 
 * @Title: goWxConfig   
 * @Description: TODO 企业微信设置
 * @param request
 * @return
 * ModelAndView    
 * @throws
 */
@RequestMapping("/oa/wxconfig")
public ModelAndView goWxConfig(HttpServletRequest request)
{
	ModelAndView mv=null;
	try
	{
	Account account=accountService.getRedisAccount(request);
	WxConfig wxConfig = new WxConfig();
	wxConfig.setOrgId(account.getOrgId());
	wxConfig = wxConfigService.selectOneWxConfig(wxConfig);
	mv = new ModelAndView("app/core/sysset/wxconfig");
	if(wxConfig!=null)
	{
		mv.addObject("wxCorpId",wxConfig.getWxCorpId());
		mv.addObject("wxAgentId",wxConfig.getWxAgentId());
		mv.addObject("wxAppSecret",wxConfig.getWxAppSecret());
		mv.addObject("wxSyncSecret",wxConfig.getWxSyncSecret());
	}else
	{
		mv.addObject("wxCorpId","");
		mv.addObject("wxAgentId","");
		mv.addObject("wxAppSecret","");
		mv.addObject("wxSyncSecret","");
	}
	
	return mv;
	}catch (Exception e) {
		 mv = new ModelAndView("titps");
		 return mv;
	}
}

/**
 * 
 * @Title: goDdConfig   
 * @Description: TODO 钉钉应用相关设置 
 * @param: request
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
@RequestMapping("/oa/ddconfig")
public ModelAndView goDdConfig(HttpServletRequest request)
{
	ModelAndView mv=null;
	try
	{
	Account account=accountService.getRedisAccount(request);
	DdConfig ddConfig = new DdConfig();
	ddConfig.setOrgId(account.getOrgId());
	ddConfig = ddConfigService.selectOneDdConfig(ddConfig);
	mv = new ModelAndView("app/core/sysset/ddconfig");
	if(ddConfig!=null)
	{
		mv.addObject("dingdingCorpId",ddConfig.getDingdingCorpId());
		mv.addObject("dingdingAgentId",ddConfig.getDingdingAgentId());
		mv.addObject("dingdingAppKey",ddConfig.getDingdingAppKey());
		mv.addObject("dingdingAppSecret",ddConfig.getDingdingAppSecret());
	}else
	{
		mv.addObject("dingdingCorpId","");
		mv.addObject("dingdingAgentId","");
		mv.addObject("dingdingAppKey","");
		mv.addObject("dingdingAppSecret","");
	}
	return mv;
	}catch (Exception e) {
		 mv = new ModelAndView("titps");
		 return mv;
	}
}


/**
 * 
 * @Title: goMobilesms   
 * @Description: TODO 平台手机短信
 * @param: request
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
@RequestMapping("/oa/mobilesms")
public ModelAndView goMobilesms(HttpServletRequest request)
{
	ModelAndView mv = new ModelAndView();
	mv.setViewName("app/core/sms/mobilesms");
	return mv;
}
/**
 * 
 * @Title: goSysLog   
 * @Description: TODO 系统日志
 * @param: request
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
@RequestMapping("/syslog")
public ModelAndView goSysLog(HttpServletRequest request)
{
	ModelAndView mv = new ModelAndView();
	mv.setViewName("app/core/sysset/syslog");
	return mv;
}
/**
 * 
 * @Title: goSetOrg   
 * @Description: TODO 创建新机构
 * @param: request
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
@RequestMapping("/setorg")
public ModelAndView goSetOrg(HttpServletRequest request)
{
	ModelAndView mv = new ModelAndView();
	mv.setViewName("app/core/sysset/setorg");
	return mv;
}

	/**
	 * 
	 * @Title: goFrame
	 * @author:刘绍全
	 * @Description: 登陆成功后的系统主页面   
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      

	 */
	@RequestMapping("/frame")
	public ModelAndView goFrame(HttpServletRequest request)
	{
		ModelAndView mv = null;
		try
		{
		LoginAccountInfo loginAccountInfo=accountService.getRedisLoginAccountInfo(request);
		if(SysTools.isMobileDevice(request))
		{
			Account account=accountService.getRedisAccount(request);
			List<String> appList = loginAccountInfo.getMobilePrivList();
			JSONObject json = appConfigService.getMyAppList(account.getOrgId(),appList);
			mv = new ModelAndView("app/mobile/main/index");
			mv.addObject("appMenuList",json);
		}else
		{
		mv = new ModelAndView("app/core/frame");
		Account account=accountService.getRedisAccount(request);
		UserPriv userPriv = userPrivService.getUserPrivByPrivId(account.getUserPriv(), account.getOrgId());
		mv.addObject("USER_INFO", userInfoService.getUserInfoByAccountId(account.getAccountId(), account.getOrgId()));
		mv.addObject("USER_PRIV", userPriv);
		mv.addObject("sysMenuList",loginAccountInfo.getSysMenuList());
		mv.addObject("msgtime",msgtime);
		}
		return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title goDesk   
	 * @Description TODO 个人工作台   
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/desk")
	public ModelAndView goDesk(HttpServletRequest request,String view)
	{
		ModelAndView mv = null;
		try
		{
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			Account account=accountService.getRedisAccount(request);
			List<Map<String,String>> sysProfileList = sysProfileService.getMySysProfileList(userInfo.getOrgId(),account.getOpFlag(), userInfo.getAccountId(), userInfo.getDeptId(), userInfo.getLeadLeave());
			if(StringUtils.isBlank(view))
			{
				mv= new ModelAndView("app/core/desk");
			}else
			{
				if(view.equals("finance"))
				{
					mv= new ModelAndView("app/core/deskforfinance");
				}else if(view.equals("hr"))
				{
					mv= new ModelAndView("app/core/deskforhr");
				}else if(view.equals("com"))
				{
					mv= new ModelAndView("app/core/deskforcom");
				}else if(view.equals("bi"))
				{
					mv= new ModelAndView("app/core/deskforbi");
				}else if(view.equals("project"))
				{
					mv= new ModelAndView("app/core/deskforproject");
				}else if(view.equals("desk"))
				{
					mv= new ModelAndView("app/core/desk");
				}
			}
		mv.addObject("view",view);
		mv.addObject("profilelist",sysProfileList);
		return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title: goSetProfile   
	 * @Description: TODO 用户门户设置
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/sys/setprofile")
	public ModelAndView goSetProfile(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/sysset/setprofile");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title: goWeiXinSet   
	 * @Description: TODO 企业微信用户部门设置
	 * @param request
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/wxset")
	public ModelAndView goWeiXinSet(HttpServletRequest request,String view)
	{
		ModelAndView mv=null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv= new ModelAndView("app/core/sysset/weixindept");
			}else
			{
				if(view.equals("userimprot"))
				{
					mv= new ModelAndView("app/core/sysset/weixinuserimprot");
				}
			}
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: goDingDingUser   
	 * @Description: TODO 钉钉用户绑定
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/dingdingset")
	public ModelAndView goDingDingUser(HttpServletRequest request,String view)
	{
		ModelAndView mv=null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv= new ModelAndView("app/core/sysset/dingdingdept");
			}else
			{
				if(view.equals("userimprot"))
				{
					mv= new ModelAndView("app/core/sysset/dingdinguserimprot");
				}
			}
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title: goSysMonitor   
	 * @Description: TODO 系统健康监控
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/sysmonitor")
	public ModelAndView goSysMonitor(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/sysset/sysmonitor");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	* @Title: mydesk 
	* @Description: TODO 个人门户
	* @param @param request
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("/mydesk")
	public ModelAndView mydesk(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/deskmodule/mydesk");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}	
	/**
	 * 
	 * @Title: goNews
	 * @author:刘绍全
	 * @Description: 新闻管理  
	 * @param: @return      
	 * @return: String      

	 */
	@RequestMapping("/news")
	public ModelAndView goNews(String view) {
		ModelAndView mv=null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("app/core/news/index");
			}else
			{
				if(view.equals("edit"))
				{
					mv = new ModelAndView("app/core/news/editnews");
				}
			}
		return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	* @Title: mydiarylist 
	* @Description: TODO 他人分享的工作日志
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("/diary/sharediarylist")
	public ModelAndView sharediarylist() {
		try
		{
			ModelAndView mv = new ModelAndView("app/core/diary/sharediarylist");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: leaddiary   
	 * @Description: TODO 领导查看下属的工作日志
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/diary/leaddiary")
	public ModelAndView leaddiary(String view) 
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/core/diary/leaddiary");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	
	/**
	 * 
	* @Title: readdiary 
	* @Description: TODO 查看日志详情
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("/diary/readdiary")
	public ModelAndView readdiary() {
		try
		{
			ModelAndView mv = new ModelAndView("app/core/diary/readdiary");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	@RequestMapping("/notice/details")
	public ModelAndView details() {
		try
		{
			ModelAndView mv = new ModelAndView("app/core/notice/details");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	
	/**
	 * 
	* @Title: goMyNews 
	* @Description: TODO 跳转到我的新闻
	* @param @return 设定文件 
	* @return ModelAndView 返回类型 

	 */
	@RequestMapping("/news/mynews")
	public ModelAndView goMyNews() {
		try
		{
		ModelAndView mv = new ModelAndView("app/core/news/mynews");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	@RequestMapping("/news/readnews")
	public ModelAndView goReadNews() {
		try
		{
		ModelAndView mv = new ModelAndView("app/core/news/readnews");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title goNewsManage   
	 * @Description TODO 新闻维护
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/news/manage")
	public ModelAndView goNewsManage() {
		try
		{
		ModelAndView mv = new ModelAndView("app/core/news/manage");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}

	/**
	 * 
	 * @Title: goUnitinfo
	 * @author:刘绍全
	 * @Description: 单位信息设置 
	 * @param: @return      
	 * @return: String      

	 */
	@RequestMapping("/setunitinfo")
	public ModelAndView goUnitinfo(HttpServletRequest request) {
		try
		{
			Account account=accountService.getRedisAccount(request);
		ModelAndView mv = new ModelAndView("app/core/sysset/unit/index");
		Unit unit = unitService.getUnitByOrgId(account.getOrgId());
		mv.addObject("unit", unit);
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: sysInfo
	 * @author:刘绍全
	 * @Description: 跳转到返回系统信息页面   
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws Exception 

	 */
	@RequestMapping("/sysinfo")
	public ModelAndView sysInfo(HttpServletRequest request) throws Exception
	{
		ServletContext sc = request.getServletContext();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/sysset/sysinfo/index");
		mv.addObject("SERVERINFO",sc.getServerInfo());
		mv.addObject("sysInfo", SysTools.getSysInfo(attach));
		mv.addObject("ACCOUNT_COUNT",accountService.getAllUserCount());
		return mv;
	}
	
	/**
	 * 
	 * @Title: goDept   
	 * @Description: TODO 单位部门信息设置 
	 * @param request
	 * @return      
	 * @return: ModelAndView      

	 */
	@RequestMapping("/setdept")
	public ModelAndView goDept(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/sysset/unit/dept");
		return mv;
	}
	
	/**
	 * 
	 * @Title: goUserinfo   
	 * @Description: TODO 系统用户管理 
	 * @param request
	 * @return      
	 * @return: ModelAndView      

	 */
	@RequestMapping("/userinfo")
	public ModelAndView goUserinfo(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/sysset/unit/userinfo");
		return mv;
	}
	/**
	 * 
	 * @Title goUserpriv   
	 * @Description TODO 权限管理  
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/userpriv")
	public ModelAndView goUserpriv(HttpServletRequest request,String view,String userPrivId)
	{
		ModelAndView mv = null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv= new ModelAndView("app/core/sysset/unit/userpriv");
			}else {
				if(view.equals("pcsetpriv"))
				{
					Account account=accountService.getRedisAccount(request);
					mv = new ModelAndView("app/core/sysset/unit/setuserpriv");
					UserPriv userPriv = new UserPriv();
					userPriv.setUserPrivId(userPrivId);
					userPriv.setOrgId(account.getOrgId());
					userPriv=userPrivService.selectOne(userPriv);
					mv.addObject("userPriv",userPriv);
				}else if(view.equals("mobilesetpriv"))
				{
					Account account=accountService.getRedisAccount(request);
					mv = new ModelAndView("app/core/sysset/unit/mobilesetpriv");
					UserPriv userPriv = new UserPriv();
					userPriv.setUserPrivId(userPrivId);
					userPriv.setOrgId(account.getOrgId());
					userPriv=userPrivService.selectOne(userPriv);
					mv.addObject("userPriv",userPriv);
				}
			}
			
		}catch (Exception e) {
			mv = new ModelAndView("titps");
		}
		return mv;
	}
	/**
	 * 
	 * @Title goSysMenu   
	 * @Description TODO 跳转权限设置菜单设置
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/sysmenuindex")
	public ModelAndView goSysMenu()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/sysset/sysmenu/index");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title sendNotice   
	 * @Description TODO 跳转通知公告   
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping(value="/notice/index", method=RequestMethod.GET)
	public ModelAndView sendNotice(String view)
	{
		ModelAndView mv = null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv=new ModelAndView("app/core/notice/index");
			}else
			{
				if(view.equals("edit"))
				{
					 mv = new ModelAndView("app/core/notice/editnotice");
				}
			}
			return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	* @Title: setNoticePriv 
	* @Description: TODO 设置通知公告权限
	* @param @param request
	* @return ModelAndView 返回类型
	 */
	@RequestMapping(value="notice/setnoticepriv", method=RequestMethod.GET)
	public ModelAndView setNoticePriv(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/notice/priv");
		return mv;
	}
	
	/**
	 * 
	* @Title: setNoticePriv 
	* @Description: TODO 通知公告维护
	* @param @param request
	* @return ModelAndView 返回类型
	 */
	@RequestMapping(value="notice/managenotice", method=RequestMethod.GET)
	public ModelAndView ManageNotice(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/notice/managenotice");
		return mv;
	}
	
	/**
	 * 
	* @Title: setNoticePriv 
	* @Description: TODO 通知公告维护
	* @param @param request
	* @return ModelAndView 返回类型
	 */
	@RequestMapping(value="notice/readnotice", method=RequestMethod.GET)
	public ModelAndView ReadNotice(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/notice/readnotice");
		return mv;
	}
	
	/**
	 * 
	* @Title: doApprovalNotice 
	* @Description: TODO通知公告审批
	* @param @param request
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping(value="/notice/approvalnotice", method=RequestMethod.GET)
	public ModelAndView doApprovalNotice(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/notice/approvalnotice");
		return mv;
	}
/**
 * 
 * @Title: goDbSoucre   
 * @Description: TODO 系统数据源设置  
 * @param request
 * @return      
 * @return: ModelAndView      

 */
	@RequestMapping("/dbsource")
	public ModelAndView goDbSource(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/sysset/dbsource/index");
		return mv;
	}
	/**
	 * 
	 * @Title goSysconfig   
	 * @Description TODO 系统参数设置
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/sysconfig")
	public ModelAndView goSysconfig(HttpServletRequest request)
	{
		Account account=accountService.getRedisAccount(request);
		SysConfig sysConfig = new SysConfig();
		sysConfig.setOrgId(account.getOrgId());
		sysConfig = sysConfigService.selectOneSysConfig(sysConfig);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/sysset/sysconfig");
		mv.addObject("sysConfig",sysConfig);
		return mv;
	}
	
	/**
	 * 
	* @Title: goPublicfile 
	* @Description: TODO 公共文件柜
	* @param @param request
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("/file/publicfile")
	public ModelAndView goPublicfile(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/fileshare/public/index");
		return mv;
	}
	
	
/**
 * 	
 * @Title gonetdiskset   
 * @Description TODO 网盘设置
 * @param request
 * @return      
 * ModelAndView
 */
	@RequestMapping("/file/netdiskset")
	public ModelAndView gonetdiskset(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/fileshare/netdisk/seting");
		return mv;
	}
	/**
	 * 	
	 * @Title netdisk   
	 * @Description TODO 网盘
	 * @param request
	 * @return      
	 * ModelAndView
	 */	
	@RequestMapping("/file/netdisk")
	public ModelAndView gonetdisk(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/fileshare/netdisk/netdisk");
		return mv;
	}
	/**
	 * 
	 * @Title goSmsconfig   
	 * @Description TODO 系统消息提醒设置
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/smsconfig")
	public ModelAndView goSmsconfig(String view)
	{
		ModelAndView mv = null;
		try {
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("app/core/sysset/smsconfig");
			}else
			{
				if(view.equals("mustchecked"))
				{
					mv = new ModelAndView("app/core/sysset/smsconfigmust");
				}
			}
			 return mv;
		}catch (Exception e) {
			 mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title goFolderset   
	 * @Description TODO 公共文件柜设置 
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/folderset")
	public ModelAndView goFolderset(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/fileshare/public/folderset");
		return mv;
	}
	
	@RequestMapping("/personalfile")
	public ModelAndView personalfile(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/fileshare/personal/myfile");
		return mv;
	}
	/**
	 * 
	* @Title: readphoto 
	* @Description: TODO 查看图片
	* @param @param request
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("/file/photo")
	public ModelAndView readphoto(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/fileshare/photo/index");
		return mv;
	}
	
	/**
	 * 
	* @Title: setphoto 
	* @Description: TODO设置相册 
	* @param @param request
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("/file/setphoto")
	public ModelAndView setphoto(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/fileshare/photo/photoset");
		return mv;
	}
	
	/**
	 * 
	 * @Title goPersonseting   
	 * @Description TODO 个人信息设置
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/personseting")
	public ModelAndView goPersonseting(HttpServletRequest request,String view)
	{
		Account account=accountService.getRedisAccount(request);
		SysConfig sysConfig = new SysConfig();
		sysConfig.setOrgId(account.getOrgId());
		sysConfig=sysConfigService.selectOneSysConfig(sysConfig);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/personal/personseting");
		mv.addObject("sysConfig",sysConfig);
		mv.addObject("view",view);
		return mv;
	}
	
	/**
	 * 
	 * @Title goCodeClass   
	 * @Description TODO 系统编码设置  
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/codeclass")
	public ModelAndView goCodeClass(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/sysset/codeclass");
		return mv;
	}
	
	/**
	 * 
	 * @Title goMydiary  
	 * @Description TODO 工作日志
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/diary/mydiary")
	public ModelAndView goMydiary(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/diary/mydiary");
		return mv;
	}
	
	/**
	 * 
	* @Title: goOtherdiarylist 
	* @Description: TODO 他人的工作日志
	* @param @param request
	* @param @param accountId
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("/diary/otherdiarylist")
	public ModelAndView goOtherdiarylist(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/diary/otherdiarylist");
		return mv;
	}
	/**
	 * 
	* @Title: goDiarypriv 
	* @Description: TODO 设置工作日志
	* @param @param request
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("/diary/diarypriv")
	public ModelAndView goDiarypriv(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/diary/diarypriv");
		return mv;
	}
	/**
	 * 
	 * @Title goSetportal   
	 * @Description TODO 门户设置 
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("sys/setsysdesk")
	public ModelAndView goSetportal(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/sysset/setsysdesk");
		return mv;
	}
	/**
	 * 
	* @Title: setthmem 
	* @Description: TODO 登陆界面设置
	* @param @param request
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("sys/setthmem")
	public ModelAndView setthmem(HttpServletRequest request)
	{
		SysInterface sysInterface = sysInterfaceService.selectOneSysInterface(null);
		ModelAndView mv = new ModelAndView();
		mv.addObject("sysInterface",sysInterface);
		mv.setViewName("app/core/sysset/setthmem");
		return mv;
	}
	/**
	 * 
	 * @Title goMycalendar   
	 * @Description TODO 个人日程
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/oa/mycalendar")
	public ModelAndView goMycalendar(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/calendar/mycalendar");
		return mv;
	}
	/**
	 * 
	 * @Title: goCalendardetails   
	 * @Description: TODO 日程详情
	 * @param request
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/oa/calendardetails")
	public ModelAndView goCalendardetails(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/calendar/calendardetails");
		return mv;
	}
	/**
	 * 
	 * @Title: goMycalendarList   
	 * @Description: TODO 我的日程列表 
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/oa/mycalendarlist")
	public ModelAndView goMycalendarList(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/calendar/calendarlist");
		return mv;
	}
	
	/**
	 * 
	 * @Title goMyemail   
	 * @Description TODO 电子邮件 
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/oa/email")
	public ModelAndView goMyemail(HttpServletRequest request,String view,String bodyId,String flag)
	{
		ModelAndView mv =null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("app/core/email/email");
			}else
			{
				if(view.equals("sendemail"))
				{
					mv = new ModelAndView("app/core/email/sendemail");
				}else if(view.equals("staremail"))
				{
					mv = new ModelAndView("app/core/email/staremail");
				}else if(view.equals("sendemailbox"))
				{
					mv = new ModelAndView("app/core/email/sendbox");
				}else if(view.equals("draftbox"))
				{
					mv = new ModelAndView("app/core/email/draftbox");
				}else if(view.equals("delemail"))
				{
					mv = new ModelAndView("app/core/email/delemail");
				}else if(view.equals("emailbox"))
				{
					mv = new ModelAndView("app/core/email/emailbox");
				}else if(view.equals("reply"))
				{
					mv = new ModelAndView("app/core/email/reply");
					mv.addObject("bodyId",bodyId);
					mv.addObject("flag",flag);
				}
			}
			return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title goMyEmaildetails   
	 * @Description TODO 内部邮件详情 
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/oa/emaildetails")
	public ModelAndView goMyEmaildetails(HttpServletRequest request,String emailId)
	{
		ModelAndView mv = null;
		try
		{
			if(SysTools.isMobileDevice(request))
			{
				mv= new ModelAndView("app/mobile/main/email/details");
			}else
			{
				Account account=accountService.getRedisAccount(request);
				mv= new ModelAndView("app/core/email/emaildetails");
				Email email = new Email();
				email.setEmailId(emailId);
				email.setOrgId(account.getOrgId());
				email = emailService.selectOneEmail(email);
				mv.addObject("emailId", email.getEmailId());
				mv.addObject("bodyId",email.getBodyId());
			}
			return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	* @Title: goMysortemail 
	* @Description: TODO 邮件中的文件夹
	* @param @param request
	* @param @param boxId
	* @param @return 设定文件 
	* @return ModelAndView 返回类型 

	 */
	@RequestMapping("/oa/mysortemail")
	public ModelAndView goMysortemail(HttpServletRequest request,String boxId)
	{
		Account account=accountService.getRedisAccount(request);
		ModelAndView mv = new ModelAndView();
		EmailBox emailBox = new EmailBox();
		emailBox.setOrgId(account.getOrgId());
		emailBox.setBoxId(boxId);
		emailBox=emailBoxService.selectOneEmailBox(emailBox);
		mv.addObject("boxName",emailBox.getBoxName());
		mv.addObject("boxId",boxId);
		mv.setViewName("/app/core/email/mysortemail");
		return mv;
	}
	/**
	 * 
	 * @Title goMysms   
	 * @Description TODO 我的站内消息
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/oa/sms")
	public ModelAndView goMysms(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/core/sms/sms");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title goLeadlevel   
	 * @Description TODO 行政级别
	 * @param request
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/userlevel")
	public ModelAndView goLeadlevel(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/sysset/unit/userlevel");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title: setAppConfig   
	 * @Description: TODO 设置app
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/mobile/setAppConfig")
	public ModelAndView  setAppConfig()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/sysset/appconfig");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: setSysTimingTask   
	 * @Description: TODO 系统定时任务
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/systimingtask")
	public ModelAndView  setSysTimingTask()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/sysset/systimingtask");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goAttachManage   
	 * @Description: TODO 附件管理
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/attach/manage")
	public ModelAndView  goAttachManage()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/sysset/attachmanage");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goLeadScheduleMange   
	 * @Description: TODO 领导行程管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/leadactivity/manage")
	public ModelAndView  goLeadActivityMange(String view)
	{
		ModelAndView mv = null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv= new ModelAndView("app/core/leadactivity/manage");
			}else
			{
				mv= new ModelAndView("app/core/leadactivity/input");
			}
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goLeadActivityDetails   
	 * @Description: TODO 领导行程详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/leadactivity/details")
	public ModelAndView  goLeadActivityDetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/leadactivity/details");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goLeadActivityquery   
	 * @Description: TODO 领导行程查询
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/leadactivity/query")
	public ModelAndView  goLeadActivityquery()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/leadactivity/query");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
}
