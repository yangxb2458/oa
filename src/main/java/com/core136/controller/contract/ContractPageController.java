/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ContractPageController.java   
 * @Package com.core136.controller.sys   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月23日 上午11:03:55   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.contract;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @ClassName:  ContractPageController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月23日 上午11:03:55   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
@RequestMapping("/app/core")
public class ContractPageController {
	/**
	 * 
	 * @Title: goContracttotal   
	 * @Description: TODO 合同汇总
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/contract/contracttotal")
	public ModelAndView goContracttotal(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/contract/contracttotal");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: goReceivablesdetails   
	 * @Description: TODO 收款详情
	 * @param request
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/contract/receivablesdetails")
	public ModelAndView goReceivablesdetails(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/contract/receivablesdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: goPayablesdetails   
	 * @Description: TODO 付款详情
	 * @param request
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/contract/payablesdetails")
	public ModelAndView goPayablesdetails(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/contract/payablesdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: goBilldetails   
	 * @Description: TODO 票据详情
	 * @param request
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/contract/billdetails")
	public ModelAndView goBilldetails(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/contract/billdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: goContractBill   
	 * @Description: TODO 票据管理
	 * @param: request
	 * @param: view
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/contract/bill")
	public ModelAndView goContractBill(HttpServletRequest request,String view)
	{
		ModelAndView mv =null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv= new ModelAndView("app/core/contract/bill");
			}else
			{
				if(view.equals("manage"))
				{
					mv= new ModelAndView("app/core/contract/billmanage");
				}else if(view.equals("edit"))
				{
					mv= new ModelAndView("app/core/contract/billedit");
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
	 * @Title: goContractPayable   
	 * @Description: TODO 合同付款
	 * @param: request
	 * @param: view
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/contract/payable")
	public ModelAndView goContractPayable(HttpServletRequest request,String view)
	{
		ModelAndView mv =null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv= new ModelAndView("app/core/contract/payable");
			}else
			{
				if(view.equals("manage"))
				{
					mv= new ModelAndView("app/core/contract/payablemanage");
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
	 * @Title: goSendGoods   
	 * @Description: TODO 合同的发货管理
	 * @param request
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/contract/sendgoods")
	public ModelAndView goSendGoods(HttpServletRequest request,String view)
	{
		ModelAndView mv =null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv= new ModelAndView("app/core/contract/sendgoods");
			}else
			{
				if(view.equals("manage"))
				{
					mv= new ModelAndView("app/core/contract/sendgoodsmanage");
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
	 * @Title: goContractReceviables   
	 * @Description: TODO 应收款列表
	 * @param: request
	 * @param: view
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/contract/receivables")
	public ModelAndView goContractReceviables(HttpServletRequest request,String view)
	{
		ModelAndView mv =null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv= new ModelAndView("app/core/contract/receivables");
			}else
			{
				if(view.equals("manage"))
				{
					mv= new ModelAndView("app/core/contract/receivablesmanage");
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
	 * @Title: goContractdetails   
	 * @Description: TODO 合同详情
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/contract/contractdetails")
	public ModelAndView goContractdetails(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/contract/contractdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	
	/**
	 * 
	 * @Title: goQuery   
	 * @Description: TODO 合同查询
	 * @param: request
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/contract/query")
	public ModelAndView goQuery(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/contract/query");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title: goSendGoodsDetails   
	 * @Description: TODO 发货详情
	 * @param request
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/contract/sendgoodsdetails")
	public ModelAndView goSendGoodsDetails(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/contract/sendgoodsdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	* @Title: goSetpriv 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param request
	* @param @return 设定文件 
	* @return ModelAndView 返回类型 

	 */
	@RequestMapping("/contract/setpriv")
	public ModelAndView goSetpriv(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/contract/seting/setpriv");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	* @Title: goSetsort 
	* @Description: TODO 合同分类设置
	* @param @param request
	* @param @return 设定文件 
	* @return ModelAndView 返回类型 

	 */
	@RequestMapping("/contract/sort")
	public ModelAndView goSetsort(HttpServletRequest request)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/contract/seting/sort");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	* @Title: goCreateContract 
	* @Description: TODO 创建合同
	* @param @param request
	* @param @return 设定文件 
	* @return ModelAndView 返回类型 

	 */
	@RequestMapping("/contract/create")
	public ModelAndView goCreateContract(HttpServletRequest request,String view){
		ModelAndView mv=null;
		try{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("app/core/contract/createcontract");
			}else
			{
				if(view.equals("manage"))
				{
					mv = new ModelAndView("app/core/contract/contractmanage");
				}else if(view.equals("edit"))
				{
					mv = new ModelAndView("app/core/contract/editcontract");
				}
			}
			
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
			mv = new ModelAndView("titps");
			return mv;
		}
	}
}
