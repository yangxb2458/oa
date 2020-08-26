package com.core136.controller.projectbuild;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/core/projectbuild/config")
public class PageProjectBuildConfigController {
	/**
	 * 
	 * @Title: goBpmseting   
	 * @Description: TODO 设置工程项目审批流程 
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/bpmseting")
	public ModelAndView goBpmseting() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/config/bpmconfig");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
/**
 * 
 * @Title: goUnitseting   
 * @Description: TODO设置工程材料计量单位
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/unitseting")
	public ModelAndView goUnitseting() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/config/unitconfig");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	
}
