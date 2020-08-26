package com.core136.controller.officesupplies;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/core/officesupplies")
public class OfficesuppliesPageController {

	/**
	 * 
	 * @Title goSetSort   
	 * @Description TODO 跳转办公用品分类管理页面   
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/setofficesupplies")
	public ModelAndView  goSetSort()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/officesupplies/sortofficesupplies");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goAddofficesupplies   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/addofficesupplies")
	public ModelAndView  goAddofficesupplies()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/officesupplies/addofficesupplies.html");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: setOfficesppliesunit   
	 * @Description: TODO设置办公用品的计量单位
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/setofficesppliesunit")
	public ModelAndView  setOfficesppliesunit()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/officesupplies/officesppliesunit");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
/**
 * 	
 * @Title: goApplyofficesupplies   
 * @Description: TODO 办公领用申请
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/applyofficesupplies")
	public ModelAndView  goApplyofficesupplies(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/officesupplies/applyofficesupplies");
			
		}else if(view.equals("myapplyofficesupplies"))
		{
			mv = new ModelAndView("app/core/officesupplies/myapplyofficesupplies");
		}
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goApprovalofficesupplies   
	 * @Description: TODO 办公用品领用审批
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/approvalofficesupplies")
	public ModelAndView  goApprovalofficesupplies()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/officesupplies/approvalofficesupplies");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goManageofficesupplies   
	 * @Description: TODO 办公用品统计
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/manageofficesupplies")
	public ModelAndView  goManageofficesupplies()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/officesupplies/manageofficesupplies");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	
	/**
	 * 
	 * @Title: goGetofficesupplies   
	 * @Description: TODO 办公用品发放
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/grantofficesupplies")
	public ModelAndView  goGrantofficesupplies()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/officesupplies/grantofficesupplies");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
}
