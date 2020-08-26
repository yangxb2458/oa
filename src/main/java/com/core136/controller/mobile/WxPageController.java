package com.core136.controller.mobile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.core136.common.weixin.WXutils;
import org.core136.common.weixin.entity.AccessToken;
import org.core136.common.weixin.entity.WUserIdInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.core136.bean.account.Account;
import com.core136.bean.sys.DdConfig;
import com.core136.bean.sys.WxConfig;
import com.core136.service.account.AccountService;
import com.core136.service.sys.AppConfigService;
import com.core136.service.sys.WxConfigService;
import com.core136.service.weixin.WeiXinLoginService;

import bsh.Console;

@Controller
@RequestMapping("/weixin")
public class WxPageController {
	@Value("${app.host}")
	private String host;
	@Autowired
	private WxConfigService wxConfigService;
	@Autowired
	private AppConfigService appConfigService;
	@Autowired
	private WeiXinLoginService weiXinLoginService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: goWXIndex   
	 * @Description: TODO 企业微信系统界面
	 * @param: request
	 * @param: ddConfig
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/index")
	public ModelAndView goWXIndex(HttpServletRequest request,WxConfig wxConfig)
	{
		wxConfig = wxConfigService.selectOneWxConfig(wxConfig);
		ModelAndView mv = new ModelAndView("app/mobile/weixin/index");
		mv.addObject("wxConfig",wxConfig);
		mv.addObject("host",host);
		return mv;
	}
	
	/**
	 * 
	 * @Title: goWxAutoLogin   
	 * @Description: TODO 微信自动登陆
	 * @param request
	 * @param wxConfig
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/wxlogin")
	public ModelAndView goWxAutoLogin(HttpServletRequest request,WxConfig wxConfig)
	{
		wxConfig = wxConfigService.selectOneWxConfig(wxConfig);
		ModelAndView mv = new ModelAndView("app/mobile/weixin/wxlogin");
		mv.addObject("wxConfig",wxConfig);
		mv.addObject("host",host);
		return mv;
	}
	
	/**
	 * 
	 * @Title: goToMobileIndex   
	 * @Description: TODO 跳转到主页
	 * @param request
	 * @param code
	 * @param orgId
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping(value="/main/index",method=RequestMethod.GET)
	public ModelAndView goToMobileIndex(HttpServletRequest request,String code,String orgId)
	{
		try
		{
		Account account=accountService.getRedisAccount(request);
		if(account==null)
		{
			WxConfig wxConfig = new WxConfig();
			wxConfig = wxConfigService.selectOneWxConfig(wxConfig);
			AccessToken accessToken = WXutils.getAccessToken(wxConfig.getWxCorpId(), wxConfig.getWxAppSecret());
			WUserIdInfo wUserIdInfo = WXutils.getUserId(accessToken.getAccessToken(), code);
			weiXinLoginService.weiXinLogin(request, wUserIdInfo.getUserId(), orgId);
			account=accountService.getRedisAccount(request);
		}
		List<String> appList = accountService.getRedisLoginAccountInfo(request).getMobilePrivList();
		JSONObject appMenuList = appConfigService.getMyAppList(account.getOrgId(),appList);
		ModelAndView mv = new ModelAndView("app/mobile/main/index");
		mv.addObject("appMenuList",appMenuList);
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goDetails   
	 * @Description: TODO 微信消息的详情页跳转
	 * @param request
	 * @param code
	 * @param orgId
	 * @param detailsUrl
	 * @return
	 * String    
	 * @throws
	 */
	@RequestMapping(value="/details",method=RequestMethod.GET)
	public String goDetails(HttpServletRequest request,String code,String orgId,String detailsUrl)
	{
		System.out.println(detailsUrl);
		try
		{
		Account account=accountService.getRedisAccount(request);
		if(account==null)
		{
			WxConfig wxConfig = new WxConfig();
			wxConfig = wxConfigService.selectOneWxConfig(wxConfig);
			AccessToken accessToken = WXutils.getAccessToken(wxConfig.getWxCorpId(), wxConfig.getWxAppSecret());
			WUserIdInfo wUserIdInfo = WXutils.getUserId(accessToken.getAccessToken(), code);
			weiXinLoginService.weiXinLogin(request, wUserIdInfo.getUserId(), orgId);
		}
		}catch (Exception e) {
			e.printStackTrace();
	}
		return detailsUrl;
	}
}
