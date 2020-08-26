/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  SuperversionPageController.java   
 * @Package com.core136.controller.superversion   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月14日 下午1:17:49   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.superversion;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author lsq
 *
 */
@Controller
@RequestMapping("/app/core/superversion")
public class SuperversionPageController {
	@RequestMapping("/applydetails")
	public ModelAndView  goApplydetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("/app/core/superversion/applydetails");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goSuperversionprocessdetails   
	 * @Description: TODO 办理步骤详情
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/superversionprocessdetails")
	public ModelAndView  goSuperversionprocessdetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("/app/core/superversion/superversionprocessdetails");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goLeadmanage   
	 * @Description: TODO 领导管控
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/leadmanage")
	public ModelAndView  goLeadmanage(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("/app/core/superversion/leadmanage");
		}else
		{
			if(view.equals("delayapply"))
			{
				mv = new ModelAndView("/app/core/superversion/delayapply");
			}else if(view.equals("controlprocess"))
			{
				mv = new ModelAndView("/app/core/superversion/controlprocess");
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
	 * @Title: goQuery   
	 * @Description: TODO 督查查询
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/query")
	public ModelAndView  goQuery(String view)
	{
		ModelAndView mv = null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("/app/core/superversion/querydept");
			}else
			{
				if(view.equals("type"))
				{
					mv = new ModelAndView("/app/core/superversion/querytype");
				}else if(view.equals("bi"))
				{
					mv = new ModelAndView("/app/core/superversion/querybi");
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
	 * @Title: setSuperversionConfig   
	 * @Description: TODO 设置相关的领导
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/setsuperversionconfig")
	public ModelAndView  setSuperversionConfig()
	{
		try
		{
		ModelAndView mv = new ModelAndView("/app/core/superversion/setleadconfig");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goCreatesuperverion   
	 * @Description: TODO 发起督查项目
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/createsuperverion")
	public ModelAndView  goCreatesuperverion(String view,String superversionId)
	{
		try
		{
			ModelAndView mv=null;
		if(StringUtils.isBlank(view))
		{
			mv= new ModelAndView("/app/core/superversion/createsuperverion");
			
		}else if(view.equals("mysuperverionmanage"))
		{
			mv = new ModelAndView("/app/core/superversion/mysuperverionmanage");
		}else if(view.equals("mysuperverionedit"))
		{
			mv = new ModelAndView("/app/core/superversion/mysuperverionedit");
			mv.addObject("superversionId",superversionId);
		}
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goProcess   
	 * @Description: TODO 督查督办事件处理
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/process")
	public ModelAndView  goProcess(String view)
	{
		ModelAndView mv = null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv= new ModelAndView("/app/core/superversion/process");
				
			}else if(view.equals("queryprocess"))
			{
				mv = new ModelAndView("/app/core/superversion/queryprocess");
			}
		return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goSuperversionDetails   
	 * @Description: TODO 查看详情 
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/superversiondetails")
	public ModelAndView  goSuperversionDetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("/app/core/superversion/superversiondetails");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}

}
