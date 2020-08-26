package com.core136.controller.mobile;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.core136.bean.sys.DdConfig;
import com.core136.service.sys.DdConfigService;



/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  DemoPageController.java   
 * @Package    
 * @Description:    TODO 自己动登陆
 * @author: 稠云信息    
 * @date:   2019年5月23日 下午2:35:31   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Controller
@RequestMapping("/dingding")
public class DingdiingPageController {
	@Autowired
	private DdConfigService ddConfigService;
	/**
	 * 
	 * @Title: goDingDingIndex   
	 * @Description: TODO 钉钉系统界面
	 * @param: request
	 * @param: ddConfig
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/index")
	public ModelAndView goDingDingIndex(HttpServletRequest request,DdConfig ddConfig)
	{
		ddConfig = ddConfigService.selectOneDdConfig(ddConfig);
		ModelAndView mv = new ModelAndView("app/mobile/dingding/index");
		mv.addObject("ddConfig",ddConfig);
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: goMobilesms   
	 * @Description: TODO PC版钉钉客户端免登陆地址 
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/indexdd")
	public ModelAndView goDingdingPcIndex(HttpServletRequest request,DdConfig ddConfig)
	{
		ddConfig = ddConfigService.selectOneDdConfig(ddConfig);
		ModelAndView mv = new ModelAndView("app/mobile/dingding/indexdd");
		mv.addObject("ddConfig",ddConfig);
		return mv;
	}
/**
 * 
 * @Title: goDingdingAutoLogin   
 * @Description: TODO 消息跳转用
 * @param: request
 * @param: ddConfig
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/ddlogin")
	public ModelAndView goDingdingAutoLogin(HttpServletRequest request,DdConfig ddConfig)
	{
		ddConfig = ddConfigService.selectOneDdConfig(ddConfig);
		ModelAndView mv = new ModelAndView("app/mobile/dingding/ddlogin");
		mv.addObject("ddConfig",ddConfig);
		return mv;
	}
}
