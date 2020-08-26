package com.core136.controller.projectbuild;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/core/projectbuild/material")
public class PagePorjectBuildMaterialController {
	/**
	 * 
	 * @Title: goAddsMaterialort   
	 * @Description: TODO 设置工程材料分类
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/addsort")
	public ModelAndView  goAddMaterialsort()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/addsort");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goAddmaterial   
	 * @Description: TODO 录入工程材料
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/addmaterial")
	public ModelAndView  goAddmaterial()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/addmaterial");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}	
	
	/**
	 * 
	 * @Title: goAddMaterialdetails   
	 * @Description: TODO 材料详情  
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/materialdetails")
	public ModelAndView  goAddMaterialdetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/materialdetails");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	@RequestMapping("/materialstagedetails")
	public ModelAndView  goAddMaterialstagedetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/materialstagedetails");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	
	/**
	 * 
	 * @Title: goPurchasematerialMaterialdetails   
	 * @Description: TODO 采购申请
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/purchase")
	public ModelAndView  goPurchasematerialMaterialdetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/purchasematerial");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goApprovalMaterialdetails   
	 * @Description: TODO 领导审批
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/approval")
	public ModelAndView  goApprovalMaterialdetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/approval");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	
	/**
	 * 
	 * @Title: goApprovalMaterialdetails   
	 * @Description: TODO 材料预警设置
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/warning")
	public ModelAndView  goApprovalMaterialWarning()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/warning");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goMaterialIn   
	 * @Description: TODO 材料入库
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/materialin")
	public ModelAndView  goMaterialIn()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/materialin");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goMaterialInlist   
	 * @Description: TODO 入库记录详情
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/materialinlist")
	public ModelAndView  goMaterialInlist()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/materialinlist");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goMaterialout   
	 * @Description: TODO 材料出库
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/materialout")
	public ModelAndView  goMaterialOut()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/materialout");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goMaterialoutlist   
	 * @Description: TODO 出库记录详情
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/materialoutlist")
	public ModelAndView  goMaterialoutlist()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/materialoutlist");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goQueryMaterialOut   
	 * @Description: TODO 出库查询 
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/querymaterialout")
	public ModelAndView  goQueryMaterialOut()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/querymaterialout");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goQueryMaterialIn   
	 * @Description: TODO 入库查询
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/querymaterialin")
	public ModelAndView  goQueryMaterialIn()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/querymaterialin");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goQueryPurchase   
	 * @Description: TODO 采购查询
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/querypurchase")
	public ModelAndView  goQueryPurchase()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/querypurchase");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goPurchasedetails   
	 * @Description: TODO 采购申请详情
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/purchasedetails")
	public ModelAndView  goPurchasedetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/projectbuild/material/purchasedetails");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
}
