package com.core136.controller.workplan;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/core/workplan")
public class WorkPlanPageController {
	/**
	 * 
	 * @Title: goPlanquery   
	 * @Description: TODO 工作计划查询
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/planquery")
	public ModelAndView  goPlanquery(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/workplan/planquery");
		}else {
			if(view.equals("month"))
			{
				mv = new ModelAndView("app/core/workplan/planquery1");
			}else if(view.equals("query"))
			{
				mv = new ModelAndView("app/core/workplan/planquery2");
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
	 * @Title: goMyWorkPlan   
	 * @Description: TODO 我的工作计划
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/myworkplan")
	public ModelAndView  goMyWorkPlan(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/workplan/myplan");
		}else {
			if(view.equals("share"))
			{
				mv = new ModelAndView("app/core/workplan/shareplan");
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
	 * @Title: goPlanMange   
	 * @Description: TODO 工作计划管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/planmange")
	public ModelAndView  goPlanMange(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/workplan/planmange");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/workplan/plan");
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
	 * @Title: goHoldWorkPlan   
	 * @Description: TODO 我负责的计划
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/holdworkplan")
	public ModelAndView  goHoldWorkPlan()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/workplan/holdplan");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goSupWorkPlan   
	 * @Description: TODO 我督查计划
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/supworkplan")
	public ModelAndView  goSupWorkPlan()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/workplan/supplan");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goWorkPlandetails   
	 * @Description: TODO 工作计划详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/workplandetails")
	public ModelAndView  goWorkPlandetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/workplan/workplandetails");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
}
