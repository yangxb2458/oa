package com.core136.controller.vehicle;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/app/core")
public class VehiclePageController {
	/**
	 * 
	 * @Title: goRepari   
	 * @Description: TODO 车辆维修管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/vehicle/repair")
	public ModelAndView  goRepair(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/vehicle/vehiclerepairmange");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/vehicle/vehiclerepair");
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
	 * @Title: goApproved   
	 * @Description: TODO 车辆审批
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/vehicle/approved")
	public ModelAndView  goApproved(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/vehicle/approved");
		}else {
			if(view.equals("manage"))
			{
				mv = new ModelAndView("app/core/vehicle/approvedmanage");
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
	 * @Title: goOilmange   
	 * @Description: TODO 油卡管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/vehicle/oilmange")
	public ModelAndView  goOilmange(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/vehicle/oilcardmange");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/vehicle/oilcard");
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
	 * @Title: goApplyvehicle   
	 * @Description: TODO 车辆使用申请
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/vehicle/applyvehicle")
	public ModelAndView  goApplyvehicle(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/vehicle/applyvehiclemanage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/vehicle/applyvehicle");
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
	 * @Title: goVehicleinfo   
	 * @Description: TODO 车辆信息管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/vehicle/vehicleinfo")
	public ModelAndView  goVehicleinfo(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/vehicle/managevehicleinfo");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/vehicle/addvehicleinfo");
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
	 * @Title: goCareDetails   
	 * @Description: TODO 车辆详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/vehicle/vehicleinfodetails")
	public ModelAndView  goVehicleInfoDetails()
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/vehicle/vehicleinfodetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goApplyVehicleDetails   
	 * @Description: TODO 申请记录详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/vehicle/applyvehicledetails")
	public ModelAndView  goApplyVehicleDetails()
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/vehicle/applyvehicledetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}

	/**
	 * 
	 * @Title: goVehicleRepairdetails   
	 * @Description: TODO 维修详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/vehicle/vehicleRepairdetails")
	public ModelAndView  goVehicleRepairdetails()
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/vehicle/vehicleRepairdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goOilCardDetails   
	 * @Description: TODO油卡详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/vehicle/oilcarddetails")
	public ModelAndView  goOilCardDetails()
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/vehicle/oilcarddetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goOperator   
	 * @Description: TODO 调度人员设置
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/vehicle/operator")
	public ModelAndView  goOperator()
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/vehicle/operator");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
}
