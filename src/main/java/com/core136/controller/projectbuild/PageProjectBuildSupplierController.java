package com.core136.controller.projectbuild;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/core/projectbuild/supplier")
public class PageProjectBuildSupplierController {
	/**
	 * 
	 * @Title: insertsupplier   
	 * @Description: TODO 供应商录入
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/insertsupplier")
	public ModelAndView insertsupplier() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/supplier/addsupplier");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: managesupplier   
	 * @Description: TODO 供应商维护
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/manage")
	public ModelAndView managesupplier() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/supplier/managesupplier");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title: supplierdetails   
	 * @Description: TODO 供应商详情
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/supplierdetails")
	public ModelAndView supplierdetails() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/supplier/supplierdetails");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	/**
	 * 
	 * @Title: supplieredit   
	 * @Description: TODO 修改供应商信息
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/supplieredit")
	public ModelAndView supplieredit() {
		try {
			ModelAndView mv = new ModelAndView("app/core/projectbuild/supplier/supplieredit");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
	
}
