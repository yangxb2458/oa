package com.core136.controller.projectbuild;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/app/core/projectbuild/contract")
public class PageProjectBuildContractController {
/**
 * 
 * @Title: goSetsort   
 * @Description: TODO 工程合同分类
 * @param: request
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/setsort")
	public ModelAndView goSetsort(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/contract/sort");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title: goAddcontract   
	 * @Description: TODO创建合同
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/addcontract")
	public ModelAndView goAddcontract(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/contract/addcontract");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}	
	
	/**
	 * 
	 * @Title: goDetails   
	 * @Description: TODO 合同详情  
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/details")
	public ModelAndView goDetails(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/contract/details");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}	
	
	
	@RequestMapping("/manage")
	public ModelAndView goManage(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/contract/manage");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}	
}
