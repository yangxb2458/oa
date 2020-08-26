package com.core136.controller.projectbuild;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/app/core/projectbuild/artificial")
public class PageProjectBuildArtificialController {
/**
 * 
 * @Title: goAddArtificialSort   
 * @Description: TODO 工种分类
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/addSort")
	public ModelAndView  goAddArtificialSort()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/artificial/addsort");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
}
