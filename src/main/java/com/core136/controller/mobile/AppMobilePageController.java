package com.core136.controller.mobile;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.core136.bean.account.Account;
import com.core136.service.mobile.MobileLoginService;

@Controller
@RequestMapping("/appmobile")
public class AppMobilePageController {
	/**
	 * 
	 * @Title: goAppIndex   
	 * @Description: TODO 移动端登陆
	 * @param request
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/index")
	public ModelAndView goAppIndex(HttpServletRequest request)
	{
		ModelAndView mv =null;
		try
		{
		mv= new ModelAndView("app/mobile/app/index");
		return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
			return mv;
		}
		
	}
	
}
