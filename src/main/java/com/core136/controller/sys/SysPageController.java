package com.core136.controller.sys;

import org.apache.commons.lang.StringUtils;
import org.core136.common.enums.AppGobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core136.bean.sys.SysInterface;
import com.core136.service.account.AccountService;
import com.core136.service.sys.SysInterfaceService;

@Controller
public class SysPageController {
	@Autowired
	private SysInterfaceService sysInterfaceService;
	@Autowired
	private AccountService accountService;
	
	/**
	 * 
	 * @Title: goHome
	 * @author:刘绍全
	 * @Description: 系统首页面跳转地址
	 * @param: @return      
	 * @return: ModelAndView      

	 */
	@RequestMapping("/")
	public ModelAndView goHome() {
		try
		{
		ModelAndView mv = new ModelAndView("index");
		SysInterface sysInterface = new SysInterface();
		sysInterface = sysInterfaceService.selectOneSysInterface(sysInterface);
		JSONArray jsonArray = new JSONArray();
		if(StringUtils.isNotBlank(sysInterface.getBackgroundImg()))
		{
			String backGroundStr=sysInterface.getBackgroundImg();
			String[] arr= backGroundStr.split("\\*");;
			for(int i=0;i<arr.length;i++)
			{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("image", "/sys/file/getBackgroundImg?fileName="+arr[i]);
				jsonArray.add(jsonObject);
			}
		}
		if(jsonArray.size()==0)
		{
			mv.addObject("imgArr", "");	
		}else
		{
			mv.addObject("imgArr", jsonArray);
		}
		mv.addObject("SOFT_NAME", AppGobalConstant.SOFT_NAME);
		mv.addObject("sysInterface", sysInterface);
		return mv;
		}catch (Exception e) {
			e.printStackTrace();
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}

	/**
	 * 
	 * @Title: goNoAuth
	 * @author:刘绍全
	 * @Description: 无权限提示页面跳转地址  
	 * @param: @return      
	 * @return: String      

	 */
	@RequestMapping("/noauth")
	public String goNoAuth() {
		return "noauth";
	}
	/**
	 * 
	 * @Title: goReLogin
	 * @author:刘绍全
	 * @Description:重新登陆提示跳转地址
	 * @param: @return      
	 * @return: String      

	 */
	@RequestMapping("/relogin")
	public String goReLogin() {
		return "relogin";
	}
	
	/**
	 * 
	 * @Title: gotitps
	 * @author:刘绍全
	 * @Description:其它系统提示跳转地址 
	 * @param: @return      
	 * @return: String      

	 */
	@RequestMapping("/titps")
	public String gotitps() {
		return "titps";
	}
}
