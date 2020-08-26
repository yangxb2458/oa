package com.core136.controller.crm;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.core136.bean.account.Account;
import com.core136.service.account.AccountService;

import org.apache.commons.lang3.StringUtils;
import org.core136.common.utils.SysTools;

@Controller
@RequestMapping("/app/core/crm")
public class CrmPageController {
	@Autowired
	private AccountService accountService;
	
	/**
	 * 
	 * @Title: goQuotationDetails   
	 * @Description: TODO 报价单详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/quotationdetails")
	public ModelAndView  goQuotationDetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/quotation/quotationdetails");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goQuotation   
	 * @Description: TODO 报价单管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/quotation")
	public ModelAndView  goQuotationManage(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/crm/quotation/quotationmanage");
		}else
		{
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/crm/quotation/quotation");
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
	 * @Title goCreateCustomer   
	 * @Description TODO 新建客户
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/manage/new")
	public ModelAndView  goCreateCustomer(String customerId)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/customer/createcustomer");
		mv.addObject("customerId",customerId);
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title goQueryCustomer   
	 * @Description TODO 个人管理客户
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/manage/query")
	public ModelAndView  goQueryCustomer()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/customer/query");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title goLinkmanr   
	 * @Description TODO 联系人管理 
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/linkman")
	public ModelAndView  goLinkmanr()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/linkman/index");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	* @Title: goMylinkman 
	* @Description: TODO业务员人个客户联系人
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("/mylinkman")
	public ModelAndView  goMylinkman()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/customer/mylinkman");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title createLinkman   
	 * @Description TODO 创建联系人 
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/createLinkman")
	public ModelAndView  createLinkman(String linkManId)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/linkman/add");
		mv.addObject("linkManId",linkManId);
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	* @Title: createcrmcontractinfo 
	* @Description: TODO 设置合同信息
	* @param @return 设定文件 
	* @return ModelAndView 返回类型 

	 */
	@RequestMapping("/crmcontractinfo")
	public ModelAndView  createcrmcontractinfo()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/set/contractinfo");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title goSetpriv   
	 * @Description TODO CRM权限设置
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/setpriv")
	public ModelAndView  goSetpriv()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/set/mangeset");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title goCrmmanage   
	 * @Description TODO 客户信息维护
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/crmmanage")
	public ModelAndView  goCrmmanage()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/manage/index");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title goCustomerdetails   
	 * @Description TODO 客户信息详情页
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/customerdetails")
	public ModelAndView  goCustomerdetails(String customerId)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/customer/customerdetails");
		mv.addObject("customerId",customerId);
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title goLinkMandetails   
	 * @Description TODO 企业联系人详情列表   
	 * @param customerId
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/linkMandetails")
	public ModelAndView  goLinkMandetails(String customerId)
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/crm/linkman/details");
		mv.addObject("customerId",customerId);
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
		
		/**
		 * 
		* @Title: goTags 
		* @Description: TODO 企来标签设置
		* @param @return 设定文件 
		* @return ModelAndView 返回类型 

		 */
		@RequestMapping("/tags")
		public ModelAndView  goTags()
		{
			try
			{
			ModelAndView mv = new ModelAndView("app/core/crm/set/tags");
			return mv;
			}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
		/**
		 * 
		* @Title: goMyproduct 
		* @Description: TODO 可供产品设置
		* @param @return 设定文件 
		* @return ModelAndView 返回类型 

		 */
		@RequestMapping("/myproduct")
		public ModelAndView  goMyproduct()
		{
			try
			{
			ModelAndView mv = new ModelAndView("app/core/crm/set/myproduct");
			return mv;
			}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
		/**
		 * 
		* @Title: goIndustry 
		* @Description: TODO 企业行业设置
		* @param @return 设定文件 
		* @return ModelAndView 返回类型 

		 */
		@RequestMapping("/industry")
		public ModelAndView  goIndustry()
		{
			try
			{
			ModelAndView mv = new ModelAndView("app/core/crm/set/industry");
			return mv;
			}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
		}
			
			
/**
 * 			
* @Title: goInquiry 
* @Description: TODO 新建询价单
* @param @return 设定文件 
* @return ModelAndView 返回类型 

 */
@RequestMapping("/inquiry")
public ModelAndView  goInquiry(HttpServletRequest request,String inquiryId)
	{
	try
	{
		Account account = accountService.getRedisAccount(request);
		String inquiryCode=SysTools.getCode(account,"[yyyy][MM][dd]-[HH][mm][ss]-[R]");
		ModelAndView mv = new ModelAndView("app/core/crm/inquiry/create");
		mv.addObject("inquiryCode",inquiryCode);
		mv.addObject("inquiryId",inquiryId);
		return mv;
	}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
}

/**
 * 
* @Title: goInquiryManage 
* @Description: TODO 询价单管理
* @param @return 设定文件 
* @return ModelAndView 返回类型 

 */
@RequestMapping("/inquiryManage")
public ModelAndView  goInquiryManage()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/core/crm/inquiry/manage");
			return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}

/**
 * 
 * @Title: goInquiryDetails   
 * @Description: TODO 询价单详情
 * @return
 * ModelAndView    
 * @throws
 */
@RequestMapping("/inquirydetails")
public ModelAndView  goInquiryDetails()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/core/crm/inquiry/inquirydetails");
			return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}

/**
 * 
* @Title: goCustomerbi 
* @Description: TODO 客户分析
* @param @return 设定文件 
* @return ModelAndView 返回类型
 */
@RequestMapping("/customerbi")
public ModelAndView  goCustomerbi()
{
	try
	{
	ModelAndView mv = new ModelAndView("app/core/crm/bi/customerbi");
	return mv;
	}catch (Exception e) {
	ModelAndView mv = new ModelAndView("titps");
	return mv;
}
}
}
