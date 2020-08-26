package com.core136.controller.dataupload;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.core136.bean.account.UserInfo;
import com.core136.service.account.AccountService;

@Controller
@RequestMapping("/app/core/dataupload")
public class DataPageController {
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: goQueryInfo   
	 * @Description: TODO 上报信息查询
	 * @param request
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/queryinfo")
	public ModelAndView  goQueryInfo(HttpServletRequest request,String view)
	{
		ModelAndView mv = null;
	try
	{
		mv = new ModelAndView("app/core/dataupload/queryinfo");
		return mv;
	}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goHandle   
	 * @Description: TODO 上报信息处理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/handle")
	public ModelAndView  goHandle(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/dataupload/handle");
		}else {
			if(view.equals("manage"))
			{
				mv = new ModelAndView("app/core/dataupload/handlemanage");
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
	 * @Title: goUploadInfo   
	 * @Description: TODO 信息上报管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/uploadinfo")
	public ModelAndView  goUploadInfo(HttpServletRequest request,String view)
	{
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/dataupload/datauploadinfomanage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/dataupload/datauploadinfo");
			}
		}
		mv.addObject("userInfo",userInfo);
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goUploadInfoDetails   
	 * @Description: TODO 上报信息详情
	 * @param request
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/uploadinfodetails")
	public ModelAndView  goUploadInfoDetails(HttpServletRequest request,String view)
	{
		ModelAndView mv = null;
	try
	{
		mv = new ModelAndView("app/core/dataupload/datauploadinfodetails");
		return mv;
	}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
}
