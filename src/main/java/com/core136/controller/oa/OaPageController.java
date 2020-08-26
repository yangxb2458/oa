/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  OaPageController.java   
 * @Package com.core136.controller.oa   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月14日 下午4:12:08   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.oa;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lsq
 *
 */
@Controller
@RequestMapping("/app/core/attend")
public class OaPageController {
	/**
	 * 
	 * @Title: goSetAttendConfig   
	 * @Description: TODO 跳转到考勤设置   
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/setconfig")
	public ModelAndView goSetAttendConfig(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/attend/config");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title: goStatistics   
	 * @Description: TODO 考勤汇总统计
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/statistics")
	public ModelAndView goStatistics(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/attend/statistics");
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: goMyattend   
	 * @Description: TODO 我的考勤 
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/myattend")
	public ModelAndView goMyattend(HttpServletRequest request,String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/attend/myattend");
		}else 
		{
			if(view.equals("mattend"))
			{
				mv = new ModelAndView("app/core/attend/mattend");
			}else if(view.equals("outattend"))
			{
				mv = new ModelAndView("app/core/attend/outattend");
			}else if(view.equals("leaveattend"))
			{
				mv = new ModelAndView("app/core/attend/leaveattend");
			}else if(view.equals("travelattend"))
			{
				mv = new ModelAndView("app/core/attend/travelattend");
			}else if(view.equals("travelattend"))
			{
				mv = new ModelAndView("app/core/attend/travelattend");
			}else if(view.equals("overtimeattend"))
			{
				mv = new ModelAndView("app/core/attend/overtimeattend");
			}else if(view.equals("dutyattend"))
			{
				mv = new ModelAndView("app/core/attend/dutyattend");
			}else if(view.equals("queryattend"))
			{
				mv = new ModelAndView("app/core/attend/queryattend");
			}
		}
		
		return mv;
		}catch (Exception e) {
			// TODO: handle exception
			mv = new ModelAndView("titps");
			return mv;
		}
	}
	
}
