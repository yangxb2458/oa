/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedassetsPageController.java   
 * @Package com.core136.controller.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月25日 下午5:18:00   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.fixedassets;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lsq
 *
 */
@Controller
@RequestMapping("/app/core/fixedassets")
public class FixedAssetsPageController {
	/**
	 * 
	 * @Title: goRepairmange   
	 * @Description: TODO 固定资产维修管理
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/repairmange")
	public ModelAndView  goRepairmange()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/fixedassets/repairmange");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}	
	/**
	 * 
	 * @Title: goApprove   
	 * @Description: TODO 领用审批
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/approve")
	public ModelAndView  goApprove()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/fixedassets/approve");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}		
	
	/**
	 * 
	 * @Title: goAllocationfixedassets   
	 * @Description: TODO 固定资产调拨
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/allocation")
	public ModelAndView  goAllocation()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/fixedassets/allocationfixedassets");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goApplyfixedassets   
	 * @Description: TODO 固定资产申请
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/applyfixedassets")
	public ModelAndView  goApplyfixedassets()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/fixedassets/applyfixedassets");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goSetSort   
	 * @Description: TODO 固定资产分类设置
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/setsort")
	public ModelAndView  goSetSort()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/fixedassets/sort");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goSetSort   
	 * @Description: TODO 固定资产查询
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/query")
	public ModelAndView  goQuery()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/fixedassets/query");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goDetailsfixedassets   
	 * @Description: TODO 固定资产详情
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/detailsfixedassets")
	public ModelAndView  goDetailsfixedassets()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/fixedassets/detailsfixedassets");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goDetailsfixedassetsandapply   
	 * @Description: TODO 调拨的产品与申请详情
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/detailsfixedassetsandapply")
	public ModelAndView  goDetailsfixedassetsandapply()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/fixedassets/detailsfixedassetsandapply");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goSetstorage   
	 * @Description: TODO 设置仓库  
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/setstorage")
	public ModelAndView  goSetstorage()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/fixedassets/setstorage");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
/**
 * 
 * @Title: goManage   
 * @Description: TODO 跳转到固定资产管理
 * @param: view
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/manage")
	public ModelAndView  goManage(String view)
	{
	try
	{
		ModelAndView mv = null;
		if(StringUtils.isBlank(view))
	{
			mv=new ModelAndView("app/core/fixedassets/addfixedassets");
	}else {
		if(view.equals("manage"))
		{
			mv=new ModelAndView("app/core/fixedassets/managefixedassets");	
		}else if(view.equals("edit"))
		{
			mv=new ModelAndView("app/core/fixedassets/editfixedassets");
		}
	}
	return mv;
	}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
}
