package com.core136.controller.projectbuild;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/core/projectbuild/project")
public class PageProjectBuildController {
	/**
	 * 
	 * @Title: goAddProjectsort 
	 * @Description: TODO 工程项目分类 
	 * @param: @return 
	 * @return:
	 * ModelAndView @throws
	 */
	@RequestMapping("/addSort")
	public ModelAndView goAddProjectsort() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/porject/addsort");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
/**
 * 	
 * @Title: goapproval  
 * @Description: TODO 工程项目立项 
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/approval")
	public ModelAndView goapproval() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/porject/approval");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: goDetalis   
	 * @Description: TODO 工程项目详情
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/detalis")
	public ModelAndView goDetalis() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/porject/details");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}

	/**
	 * 设置工程项目节点
	 * @return
	 */
	@RequestMapping("/setStage")
	public ModelAndView goSetStage() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/porject/stage");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	

	/**
	 * 设置工程项目节点
	 * @return
	 */
	@RequestMapping("/stagedetails")
	public ModelAndView goStagedetails() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/porject/stagedetails");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title: goProjectdaily   
	 * @Description: TODO 项目日报
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/projectdaily")
	public ModelAndView goProjectdaily() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/porject/projectdaily");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
}
