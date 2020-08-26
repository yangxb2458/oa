package com.core136.controller.bi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/core")
public class AppBiController {
	/**
	 * 
	 * @Title gobisort   
	 * @Description TODO 跳转报表BI分类设置
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/bisort")
	public ModelAndView  gobisort()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/bi/set/bisort");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 跳转报表BI设置
	 * @Title gocreatebi   
	 * @Description TODO(这里用一句话描述这个方法的作用)   
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/createbi")
	public ModelAndView  gocreatebi()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/bi/set/createbi");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
/**
 * 
 * @Title goBiread   
 * @Description TODO 报表查看
 * @return      
 * ModelAndView
 */
	@RequestMapping("/biread")
	public ModelAndView  goBiread()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/bi/report/index");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
}
