/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetHrController.java   
 * @Package com.core136.controller.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月14日 下午1:21:51   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.hr;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.core136.bean.account.Account;
import com.core136.bean.hr.HrCareRecord;
import com.core136.bean.hr.HrClassCode;
import com.core136.bean.hr.HrContract;
import com.core136.bean.hr.HrDepartment;
import com.core136.bean.hr.HrEvaluate;
import com.core136.bean.hr.HrIncentive;
import com.core136.bean.hr.HrKpiItem;
import com.core136.bean.hr.HrKpiPlan;
import com.core136.bean.hr.HrLearnRecord;
import com.core136.bean.hr.HrLeaveRecord;
import com.core136.bean.hr.HrLicence;
import com.core136.bean.hr.HrPersonnelTransfer;
import com.core136.bean.hr.HrRecruitNeeds;
import com.core136.bean.hr.HrRecruitPlan;
import com.core136.bean.hr.HrReinstatement;
import com.core136.bean.hr.HrSalaryRecord;
import com.core136.bean.hr.HrTitleEvaluation;
import com.core136.bean.hr.HrTrainRecord;
import com.core136.bean.hr.HrUserInfo;
import com.core136.bean.hr.HrUserLevel;
import com.core136.bean.hr.HrWagesLevel;
import com.core136.bean.hr.HrWelfareRecord;
import com.core136.bean.hr.HrWorkRecord;
import com.core136.bean.hr.HrWorkSkills;
import com.core136.service.account.AccountService;
import com.core136.service.hr.HrCareRecordService;
import com.core136.service.hr.HrClassCodeService;
import com.core136.service.hr.HrContractService;
import com.core136.service.hr.HrDepartmentService;
import com.core136.service.hr.HrEvaluateService;
import com.core136.service.hr.HrIncentiveService;
import com.core136.service.hr.HrKpiItemService;
import com.core136.service.hr.HrKpiPlanService;
import com.core136.service.hr.HrLearnRecordService;
import com.core136.service.hr.HrLeaveRecordService;
import com.core136.service.hr.HrLicenceService;
import com.core136.service.hr.HrPersonnelTransferService;
import com.core136.service.hr.HrRecruitNeedsService;
import com.core136.service.hr.HrRecruitPlanService;
import com.core136.service.hr.HrReinstatementService;
import com.core136.service.hr.HrSalaryRecordService;
import com.core136.service.hr.HrTitleEvaluationService;
import com.core136.service.hr.HrTrainRecordService;
import com.core136.service.hr.HrUserInfoService;
import com.core136.service.hr.HrUserLevelService;
import com.core136.service.hr.HrWagesLevelService;
import com.core136.service.hr.HrWelfareRecordService;
import com.core136.service.hr.HrWorkRecordService;
import com.core136.service.hr.HrWorkSkillsService;

import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/set/hrset")
public class RoutSetHrController {
	@Autowired
	private HrDepartmentService hrDepartmentService;
	@Autowired
	private HrUserInfoService hrUserInfoService;
	@Autowired
	private HrUserLevelService hrUserLevelService;
	@Autowired
	private HrWagesLevelService hrWagesLevelService;
	@Autowired
	private HrClassCodeService hrClassCodeService;
	@Autowired
	private HrContractService hrContractService;
	@Autowired
	private HrIncentiveService hrIncentiveService;
	@Autowired
	private HrLicenceService hrLicenceService;
	@Autowired
	private HrLearnRecordService hrLearnRecordService;
	@Autowired
	private HrWorkRecordService hrWorkRecordService;
	@Autowired
	private HrWorkSkillsService hrWorkSkillsService;
	@Autowired
	private HrPersonnelTransferService hrPersonnelTransferService;
	@Autowired
	private HrLeaveRecordService hrLeaveRecordService;
	@Autowired
	private HrReinstatementService hrReinstatementService;
	@Autowired
	private HrTitleEvaluationService hrTitleEvaluationService;
	@Autowired
	private HrCareRecordService hrCareRecordService;
	@Autowired
	private HrTrainRecordService hrTrainRecordService;
	@Autowired
	private HrRecruitNeedsService hrRecruitNeedsService;
	@Autowired
	private HrKpiItemService hrKpiItemService;
	@Autowired
	private HrKpiPlanService hrKpiPlanService;
	@Autowired
	private HrRecruitPlanService hrRecruitPlanService;
	@Autowired
	private HrSalaryRecordService hrSalaryRecordService;
	@Autowired
	private HrWelfareRecordService hrWelfareRecordService;
	@Autowired
	private HrEvaluateService hrEvaluateService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: insertHrKpiPlan
	 * @Description: TODO 创建考核计划
	 * @param request
	 * @param hrKpiPlan
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrKpiPlan",method=RequestMethod.POST)
	public RetDataBean insertHrKpiPlan (HttpServletRequest request,HrKpiPlan hrKpiPlan)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrKpiPlan.setPlanId(SysTools.getGUID());
			hrKpiPlan.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrKpiPlan.setCreateUser(account.getAccountId());
			hrKpiPlan.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrKpiPlanService.insertHrKpiPlan(hrKpiPlan));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrKpiPlan
	 * @Description: TODO 删除考核计划
	 * @param request
	 * @param hrKpiPlan
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrKpiPlan",method=RequestMethod.POST)
	public RetDataBean deleteHrKpiPlan(HttpServletRequest request,HrKpiPlan hrKpiPlan)
	{
		try
		{
			if(StringUtils.isBlank(hrKpiPlan.getPlanId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrKpiPlan.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrKpiPlanService.deleteHrKpiPlan(hrKpiPlan));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrKpiPlan
	 * @Description: TODO 更新考核计划
	 * @param request
	 * @param hrKpiPlan
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrKpiPlan",method=RequestMethod.POST)
	public RetDataBean updateHrKpiPlan(HttpServletRequest request,HrKpiPlan hrKpiPlan)
	{
		try
		{
			if(StringUtils.isBlank(hrKpiPlan.getPlanId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrKpiPlan.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("planId",hrKpiPlan.getPlanId());
			return RetDataTools.Ok("更新成功!",hrKpiPlanService.updateHrKpiPlan(example, hrKpiPlan));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertHrEvaluate
	 * @Description: TODO 添加人员评价记录
	 * @param request
	 * @param hrEvaluate
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrEvaluate",method=RequestMethod.POST)
	public RetDataBean insertHrEvaluate (HttpServletRequest request,HrEvaluate hrEvaluate)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrEvaluate.setRecordId(SysTools.getGUID());
			hrEvaluate.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrEvaluate.setCreateUser(account.getAccountId());
			hrEvaluate.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrEvaluateService.insertHrEvaluate(hrEvaluate));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrEvaluate
	 * @Description: TODO 删除人员评价记录
	 * @param request
	 * @param hrEvaluate
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrEvaluate",method=RequestMethod.POST)
	public RetDataBean deleteHrEvaluate(HttpServletRequest request,HrEvaluate hrEvaluate)
	{
		try
		{
			if(StringUtils.isBlank(hrEvaluate.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrEvaluate.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrEvaluateService.deleteHrEvaluate(hrEvaluate));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrEvaluate
	 * @Description: TODO 更新人员评价记录
	 * @param request
	 * @param hrEvaluate
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrEvaluate",method=RequestMethod.POST)
	public RetDataBean updateHrEvaluate(HttpServletRequest request,HrEvaluate hrEvaluate)
	{
		try
		{
			if(StringUtils.isBlank(hrEvaluate.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrEvaluate.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrEvaluate.getRecordId());
			return RetDataTools.Ok("更新成功!",hrEvaluateService.updateHrEvaluate(example, hrEvaluate));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: importHrWelfareRecord   
	 * @Description: TODO 批量导入人员福利
	 * @param request
	 * @param file
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping(value="/importHrWelfareRecord",method=RequestMethod.POST)
	public ModelAndView importHrWelfareRecord(HttpServletRequest request,MultipartFile file)
	{
		ModelAndView mv = null;
		try
		{
			Account account=accountService.getRedisAccount(request);
			mv = new ModelAndView("app/core/hr/salary/welfareimport");
			RetDataBean retDataBean = hrWelfareRecordService.importHrWelfareRecord(account,file);
			mv.addObject("retDataBean",retDataBean);
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
			mv.addObject("retDataBean",RetDataTools.NotOk("导入员工福利失败！"));
			return mv;
		}
	}
	/**
	 * 
	 * @Title: insertHrWelfareRecord
	 * @Description: TODO 添加人员福利记录
	 * @param request
	 * @param hrWelfareRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrWelfareRecord",method=RequestMethod.POST)
	public RetDataBean insertHrWelfareRecord (HttpServletRequest request,HrWelfareRecord hrWelfareRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrWelfareRecord.setRecordId(SysTools.getGUID());
			hrWelfareRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrWelfareRecord.setCreateUser(account.getAccountId());
			hrWelfareRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrWelfareRecordService.insertHrWelfareRecord(hrWelfareRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrWelfareRecord
	 * @Description: TODO 删除人员福利记录
	 * @param request
	 * @param hrWelfareRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrWelfareRecord",method=RequestMethod.POST)
	public RetDataBean deleteHrWelfareRecord(HttpServletRequest request,HrWelfareRecord hrWelfareRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrWelfareRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrWelfareRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrWelfareRecordService.deleteHrWelfareRecord(hrWelfareRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrWelfareRecord
	 * @Description: TODO 更新人员福利记录
	 * @param request
	 * @param hrWelfareRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrWelfareRecord",method=RequestMethod.POST)
	public RetDataBean updateHrWelfareRecord(HttpServletRequest request,HrWelfareRecord hrWelfareRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrWelfareRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrWelfareRecord.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrWelfareRecord.getRecordId());
			return RetDataTools.Ok("更新成功!",hrWelfareRecordService.updateHrWelfareRecord(example, hrWelfareRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: importHrSalaryRecord   
	 * @Description: TODO 导入工员薪资
	 * @param request
	 * @param file
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping(value="/importHrSalaryRecord",method=RequestMethod.POST)
	public ModelAndView importHrSalaryRecord(HttpServletRequest request,MultipartFile file)
	{
		ModelAndView mv = null;
		try
		{
			Account account=accountService.getRedisAccount(request);
			mv = new ModelAndView("app/core/hr/salary/salaryimport");
			RetDataBean retDataBean = hrSalaryRecordService.importHrSalaryRecord(account,file);
			mv.addObject("retDataBean",retDataBean);
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
			mv.addObject("retDataBean",RetDataTools.NotOk("导入工员薪资失败！"));
			return mv;
		}
	}
	/**
	 * 
	 * @Title: insertHrSalaryRecord
	 * @Description: TODO 添加人员薪资记录
	 * @param request
	 * @param hrSalaryRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrSalaryRecord",method=RequestMethod.POST)
	public RetDataBean insertHrSalaryRecord (HttpServletRequest request,HrSalaryRecord hrSalaryRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrSalaryRecord.setRecordId(SysTools.getGUID());
			hrSalaryRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrSalaryRecord.setCreateUser(account.getAccountId());
			hrSalaryRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrSalaryRecordService.insertHrSalaryRecord(hrSalaryRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrSalaryRecord
	 * @Description: TODO 删除人员薪资记录
	 * @param request
	 * @param hrSalaryRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrSalaryRecord",method=RequestMethod.POST)
	public RetDataBean deleteHrSalaryRecord(HttpServletRequest request,HrSalaryRecord hrSalaryRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrSalaryRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrSalaryRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrSalaryRecordService.deleteHrSalaryRecord(hrSalaryRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrSalaryRecord
	 * @Description: TODO 更新人员薪资记录
	 * @param request
	 * @param hrSalaryRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrSalaryRecord",method=RequestMethod.POST)
	public RetDataBean updateHrSalaryRecord(HttpServletRequest request,HrSalaryRecord hrSalaryRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrSalaryRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrSalaryRecord.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrSalaryRecord.getRecordId());
			return RetDataTools.Ok("更新成功!",hrSalaryRecordService.updateHrSalaryRecord(example, hrSalaryRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertHrKpiItem
	 * @Description: TODO 添加考核指标
	 * @param request
	 * @param hrKpiItem
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrKpiItem",method=RequestMethod.POST)
	public RetDataBean insertHrKpiItem (HttpServletRequest request,HrKpiItem hrKpiItem)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrKpiItem.setItemId(SysTools.getGUID());
			hrKpiItem.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrKpiItem.setCreateUser(account.getAccountId());
			hrKpiItem.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrKpiItemService.insertHrKpiItem(hrKpiItem));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrKpiItem
	 * @Description: TODO 删除考核指标
	 * @param request
	 * @param hrKpiItem
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrKpiItem",method=RequestMethod.POST)
	public RetDataBean deleteHrKpiItem(HttpServletRequest request,HrKpiItem hrKpiItem)
	{
		try
		{
			if(StringUtils.isBlank(hrKpiItem.getItemId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrKpiItem.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrKpiItemService.deleteHrKpiItem(hrKpiItem));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrKpiItem
	 * @Description: TODO 更新考核指标
	 * @param request
	 * @param hrKpiItem
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrKpiItem",method=RequestMethod.POST)
	public RetDataBean updateHrKpiItem(HttpServletRequest request,HrKpiItem hrKpiItem)
	{
		try
		{
			if(StringUtils.isBlank(hrKpiItem.getItemId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrKpiItem.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("itemId",hrKpiItem.getItemId());
			return RetDataTools.Ok("更新成功!",hrKpiItemService.updateHrKpiItem(example, hrKpiItem));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertHrRecruitPlan
	 * @Description: TODO 创建招聘计划
	 * @param request
	 * @param hrRecruitPlan
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrRecruitPlan",method=RequestMethod.POST)
	public RetDataBean insertHrRecruitPlan (HttpServletRequest request,HrRecruitPlan hrRecruitPlan)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrRecruitPlan.setPlanId(SysTools.getGUID());
			hrRecruitPlan.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrRecruitPlan.setCreateUser(account.getAccountId());
			hrRecruitPlan.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrRecruitPlanService.insertHrRecruitPlan(hrRecruitPlan));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrRecruitPlan
	 * @Description: TODO 删除招聘计划
	 * @param request
	 * @param hrRecruitPlan
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrRecruitPlan",method=RequestMethod.POST)
	public RetDataBean deleteHrRecruitPlan(HttpServletRequest request,HrRecruitPlan hrRecruitPlan)
	{
		try
		{
			if(StringUtils.isBlank(hrRecruitPlan.getPlanId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrRecruitPlan.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrRecruitPlanService.deleteHrRecruitPlan(hrRecruitPlan));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrRecruitPlan
	 * @Description: TODO 更新招聘计划
	 * @param request
	 * @param hrRecruitPlan
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrRecruitPlan",method=RequestMethod.POST)
	public RetDataBean updateHrRecruitPlan(HttpServletRequest request,HrRecruitPlan hrRecruitPlan)
	{
		try
		{
			if(StringUtils.isBlank(hrRecruitPlan.getPlanId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrRecruitPlan.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("planId",hrRecruitPlan.getPlanId());
			return RetDataTools.Ok("更新成功!",hrRecruitPlanService.updateHrRecruitPlan(example, hrRecruitPlan));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: importHrWorkSkills   
	 * @Description: TODO 劳动技能导入
	 * @param request
	 * @param file
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping(value="/importHrWorkSkills",method=RequestMethod.POST)
	public ModelAndView importHrWorkSkills(HttpServletRequest request,MultipartFile file)
	{
		ModelAndView mv = null;
		try
		{
			Account account=accountService.getRedisAccount(request);
			mv = new ModelAndView("app/core/hr/work/import");
			RetDataBean retDataBean = hrWorkSkillsService.importHrWorkSkills(account,file);
			mv.addObject("retDataBean",retDataBean);
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
			mv.addObject("retDataBean",RetDataTools.NotOk("导入劳动技能失败！"));
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: importHrWorkRecord   
	 * @Description: TODO 工作经历导入
	 * @param request
	 * @param file
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping(value="/importHrWorkRecord",method=RequestMethod.POST)
	public ModelAndView importHrWorkRecord(HttpServletRequest request,MultipartFile file)
	{
		ModelAndView mv = null;
		try
		{
			Account account=accountService.getRedisAccount(request);
			mv = new ModelAndView("app/core/hr/work/import");
			RetDataBean retDataBean = hrWorkRecordService.importHrWorkRecord(account,file);
			mv.addObject("retDataBean",retDataBean);
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
			mv.addObject("retDataBean",RetDataTools.NotOk("导入工作经历失败！"));
			return mv;
		}
	}
	/**
	 * 
	 * @Title: importHrLearnRecord   
	 * @Description: TODO 学习记录导入
	 * @param request
	 * @param file
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping(value="/importHrLearnRecord",method=RequestMethod.POST)
	public ModelAndView importHrLearnRecord(HttpServletRequest request,MultipartFile file)
	{
		ModelAndView mv = null;
		try
		{
			Account account=accountService.getRedisAccount(request);
			mv = new ModelAndView("app/core/hr/learn/import");
			RetDataBean retDataBean = hrLearnRecordService.importHrLearnRecord(account,file);
			mv.addObject("retDataBean",retDataBean);
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
			mv.addObject("retDataBean",RetDataTools.NotOk("导入学习经历失败！"));
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: importHrLicence   
	 * @Description: TODO 证照记录导入
	 * @param request
	 * @param file
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping(value="/importHrLicence",method=RequestMethod.POST)
	public ModelAndView importHrLicence(HttpServletRequest request,MultipartFile file)
	{
		ModelAndView mv = null;
		try
		{
			Account account=accountService.getRedisAccount(request);
			mv = new ModelAndView("app/core/hr/licence/import");
			RetDataBean retDataBean = hrLicenceService.importHrLicence(account,file);
			mv.addObject("retDataBean",retDataBean);
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
			mv.addObject("retDataBean",RetDataTools.NotOk("导入证照记录失败！"));
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: importHrIncentive   
	 * @Description: TODO 奖惩记录导入
	 * @param request
	 * @param file
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping(value="/importHrIncentive",method=RequestMethod.POST)
	public ModelAndView importHrIncentive(HttpServletRequest request,MultipartFile file)
	{
		ModelAndView mv = null;
		try
		{
			Account account=accountService.getRedisAccount(request);
			mv = new ModelAndView("app/core/hr/incentive/import");
			RetDataBean retDataBean = hrIncentiveService.importHrIncentive(account,file);
			mv.addObject("retDataBean",retDataBean);
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
			mv.addObject("retDataBean",RetDataTools.NotOk("导入奖惩失败！"));
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: importHrContract   
	 * @Description: TODO 人事合同导入
	 * @param request
	 * @param file
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping(value="/importHrContract",method=RequestMethod.POST)
	public ModelAndView importHrContract(HttpServletRequest request,MultipartFile file)
	{
		ModelAndView mv = null;
		try
		{
			Account account=accountService.getRedisAccount(request);
			mv = new ModelAndView("app/core/hr/contract/import");
			RetDataBean retDataBean = hrContractService.importHrContract(account,file);
			mv.addObject("retDataBean",retDataBean);
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
			mv.addObject("retDataBean",RetDataTools.NotOk("导入人事合同失败！"));
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: importHrUserInfo   
	 * @Description: TODO 人事档案导入
	 * @param request
	 * @param file
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/importHrUserInfo",method=RequestMethod.POST)
	public ModelAndView importHrUserInfo(HttpServletRequest request,MultipartFile file)
	{
		ModelAndView mv = null;
		try
		{
			Account account=accountService.getRedisAccount(request);
			mv = new ModelAndView("app/core/hr/baseinfo/baseinfoimport");
			RetDataBean retDataBean = hrUserInfoService.importHrUserInfo(account,file);
			mv.addObject("retDataBean",retDataBean);
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
			mv.addObject("retDataBean",RetDataTools.NotOk("导入人事档案失败！"));
			return mv;
		}
	}
	
	/**
	 * 
	 * @Title: insertHrRecruitNeeds   
	 * @Description: TODO 添加招聘需求
	 * @param request
	 * @param hrRecruitNeeds
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrRecruitNeeds",method=RequestMethod.POST)
	public RetDataBean insertHrRecruitNeeds (HttpServletRequest request,HrRecruitNeeds hrRecruitNeeds)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrRecruitNeeds.setRecordId(SysTools.getGUID());
			hrRecruitNeeds.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrRecruitNeeds.setCreateUser(account.getAccountId());
			hrRecruitNeeds.setStatus("0");
			hrRecruitNeeds.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrRecruitNeedsService.insertHrRecruitNeeds(hrRecruitNeeds));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrRecruitNeeds
	 * @Description: TODO 删除招聘需求
	 * @param request
	 * @param hrRecruitNeeds
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrRecruitNeeds",method=RequestMethod.POST)
	public RetDataBean deleteHrRecruitNeeds(HttpServletRequest request,HrRecruitNeeds hrRecruitNeeds)
	{
		try
		{
			if(StringUtils.isBlank(hrRecruitNeeds.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrRecruitNeeds.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrRecruitNeedsService.deleteHrRecruitNeeds(hrRecruitNeeds));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: approvedHrRecruitNeeds   
	 * @Description: TODO 招聘需求审批
	 * @param request
	 * @param hrRecruitNeeds
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/approvedHrRecruitNeeds",method=RequestMethod.POST)
	public RetDataBean approvedHrRecruitNeeds(HttpServletRequest request,HrRecruitNeeds hrRecruitNeeds)
	{
		try
		{
			if(StringUtils.isBlank(hrRecruitNeeds.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			hrRecruitNeeds.setApprovedTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrRecruitNeeds.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrRecruitNeeds.getRecordId());
			return RetDataTools.Ok("审批成功!",hrRecruitNeedsService.updateHrRecruitNeeds(example, hrRecruitNeeds));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrRecruitNeeds 
	 * @Description: TODO 更新招聘需求
	 * @param request
	 * @param hrRecruitNeeds
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrRecruitNeeds",method=RequestMethod.POST)
	public RetDataBean updateHrRecruitNeeds(HttpServletRequest request,HrRecruitNeeds hrRecruitNeeds)
	{
		try
		{
			if(StringUtils.isBlank(hrRecruitNeeds.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrRecruitNeeds.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrRecruitNeeds.getRecordId());
			return RetDataTools.Ok("更新成功!",hrRecruitNeedsService.updateHrRecruitNeeds(example, hrRecruitNeeds));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: insertHrTrainRecord   
	 * @Description: TODO 发起人员培训申请
	 * @param request
	 * @param hrTrainRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrTrainRecord",method=RequestMethod.POST)
	public RetDataBean insertHrTrainRecord (HttpServletRequest request,HrTrainRecord hrTrainRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrTrainRecord.setRecordId(SysTools.getGUID());
			hrTrainRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrTrainRecord.setCreateUser(account.getAccountId());
			hrTrainRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrTrainRecordService.insertHrTrainRecord(hrTrainRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrTrainRecord
	 * @Description: TODO 删除人员培训计划
	 * @param request
	 * @param hrTrainRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrTrainRecord",method=RequestMethod.POST)
	public RetDataBean deleteHrTrainRecord(HttpServletRequest request,HrTrainRecord hrTrainRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrTrainRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrTrainRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrTrainRecordService.deleteHrTrainRecord(hrTrainRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrTrainRecord 
	 * @Description: TODO 更新培训计划
	 * @param request
	 * @param hrTrainRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrTrainRecord",method=RequestMethod.POST)
	public RetDataBean updateHrTrainRecord(HttpServletRequest request,HrTrainRecord hrTrainRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrTrainRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrTrainRecord.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrTrainRecord.getRecordId());
			return RetDataTools.Ok("更新成功!",hrTrainRecordService.updateHrTrainRecord(example, hrTrainRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: approvedHrTrainRecord   
	 * @Description: TODO 审批培训计划
	 * @param request
	 * @param hrTrainRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/approvedHrTrainRecord",method=RequestMethod.POST)
	public RetDataBean approvedHrTrainRecord(HttpServletRequest request,HrTrainRecord hrTrainRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrTrainRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			hrTrainRecord.setApprovedTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrTrainRecord.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrTrainRecord.getRecordId());
			return RetDataTools.Ok("更新成功!",hrTrainRecordService.updateHrTrainRecord(example, hrTrainRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertHrCareRecord   
	 * @Description: TODO 添加员工关怀记录
	 * @param request
	 * @param hrCareRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrCareRecord",method=RequestMethod.POST)
	public RetDataBean insertHrCareRecord (HttpServletRequest request,HrCareRecord hrCareRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrCareRecord.setRecordId(SysTools.getGUID());
			hrCareRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrCareRecord.setCreateUser(account.getAccountId());
			hrCareRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrCareRecordService.insertHrCareRecord(hrCareRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrCareRecord
	 * @Description: TODO 删除员工关怀记录
	 * @param request
	 * @param hrCareRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrCareRecord",method=RequestMethod.POST)
	public RetDataBean deleteHrCareRecord(HttpServletRequest request,HrCareRecord hrCareRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrCareRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrCareRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrCareRecordService.deleteHrCareRecord(hrCareRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrCareRecord  
	 * @Description: TODO 更新员工关怀信息
	 * @param request
	 * @param hrCareRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrCareRecord",method=RequestMethod.POST)
	public RetDataBean updateHrCareRecord(HttpServletRequest request,HrCareRecord hrCareRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrCareRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrCareRecord.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrCareRecord.getRecordId());
			return RetDataTools.Ok("更新成功!",hrCareRecordService.updateHrCareRecord(example, hrCareRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertHrTitleEvaluation   
	 * @Description: TODO 添加人员评定记录
	 * @param request
	 * @param hrTitleEvaluation
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrTitleEvaluation",method=RequestMethod.POST)
	public RetDataBean insertHrTitleEvaluation (HttpServletRequest request,HrTitleEvaluation hrTitleEvaluation)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrTitleEvaluation.setRecordId(SysTools.getGUID());
			hrTitleEvaluation.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrTitleEvaluation.setCreateUser(account.getAccountId());
			hrTitleEvaluation.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrTitleEvaluationService.insertHrTitleEvaluation(hrTitleEvaluation));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrTitleEvaluation
	 * @Description: TODO 删除人员评定记录
	 * @param request
	 * @param hrTitleEvaluation
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrTitleEvaluation",method=RequestMethod.POST)
	public RetDataBean deleteHrTitleEvaluation(HttpServletRequest request,HrTitleEvaluation hrTitleEvaluation)
	{
		try
		{
			if(StringUtils.isBlank(hrTitleEvaluation.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrTitleEvaluation.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrTitleEvaluationService.deleteHrTitleEvaluation(hrTitleEvaluation));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrTitleEvaluation  
	 * @Description: TODO 更新人员评定信息
	 * @param request
	 * @param hrTitleEvaluation
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrTitleEvaluation",method=RequestMethod.POST)
	public RetDataBean updateHrTitleEvaluation(HttpServletRequest request,HrTitleEvaluation hrTitleEvaluation)
	{
		try
		{
			if(StringUtils.isBlank(hrTitleEvaluation.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrTitleEvaluation.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrTitleEvaluation.getRecordId());
			return RetDataTools.Ok("更新成功!",hrTitleEvaluationService.updateHrTitleEvaluation(example, hrTitleEvaluation));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertHrReinstatement   
	 * @Description: TODO 添加人员复职记录
	 * @param request
	 * @param HrReinstatement
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrReinstatement",method=RequestMethod.POST)
	public RetDataBean insertHrReinstatement (HttpServletRequest request,HrReinstatement hrReinstatement)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrReinstatement.setRecordId(SysTools.getGUID());
			hrReinstatement.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrReinstatement.setCreateUser(account.getAccountId());
			hrReinstatement.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrReinstatementService.insertHrReinstatement(hrReinstatement));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrReinstatement
	 * @Description: TODO 删除人员复职记录
	 * @param request
	 * @param HrReinstatement
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrReinstatement",method=RequestMethod.POST)
	public RetDataBean deleteHrReinstatement(HttpServletRequest request,HrReinstatement hrReinstatement)
	{
		try
		{
			if(StringUtils.isBlank(hrReinstatement.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrReinstatement.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrReinstatementService.deleteHrReinstatement(hrReinstatement));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrReinstatement  
	 * @Description: TODO 更新人员复职信息
	 * @param request
	 * @param HrReinstatement
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrReinstatement",method=RequestMethod.POST)
	public RetDataBean updateHrReinstatement(HttpServletRequest request,HrReinstatement hrReinstatement)
	{
		try
		{
			if(StringUtils.isBlank(hrReinstatement.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrReinstatement.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrReinstatement.getRecordId());
			return RetDataTools.Ok("更新成功!",hrReinstatementService.updateHrReinstatement(example, hrReinstatement));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertHrLeaveRecord   
	 * @Description: TODO 添加人员离职动记录
	 * @param request
	 * @param HrLeaveRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrLeaveRecord",method=RequestMethod.POST)
	public RetDataBean insertHrLeaveRecord (HttpServletRequest request,HrLeaveRecord hrLeaveRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrLeaveRecord.setRecordId(SysTools.getGUID());
			hrLeaveRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrLeaveRecord.setCreateUser(account.getAccountId());
			hrLeaveRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrLeaveRecordService.insertHrLeaveRecord(hrLeaveRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrLeaveRecord
	 * @Description: TODO 删除人员离职记录
	 * @param request
	 * @param HrLeaveRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrLeaveRecord",method=RequestMethod.POST)
	public RetDataBean deleteHrLeaveRecord(HttpServletRequest request,HrLeaveRecord hrLeaveRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrLeaveRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrLeaveRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrLeaveRecordService.deleteHrLeaveRecord(hrLeaveRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrLeaveRecord  
	 * @Description: TODO 更新人员离职信息
	 * @param request
	 * @param HrLeaveRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrLeaveRecord",method=RequestMethod.POST)
	public RetDataBean updateHrLeaveRecord(HttpServletRequest request,HrLeaveRecord hrLeaveRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrLeaveRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrPersonnelTransfer.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrLeaveRecord.getRecordId());
			return RetDataTools.Ok("更新成功!",hrLeaveRecordService.updateHrLeaveRecord(example, hrLeaveRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertHrPersonnelTransfer   
	 * @Description: TODO 添加人事调动记录
	 * @param request
	 * @param HrPersonnelTransfer
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrPersonnelTransfer",method=RequestMethod.POST)
	public RetDataBean insertHrPersonnelTransfer (HttpServletRequest request,HrPersonnelTransfer hrPersonnelTrans)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrPersonnelTrans.setTransferId(SysTools.getGUID());
			hrPersonnelTrans.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrPersonnelTrans.setCreateUser(account.getAccountId());
			hrPersonnelTrans.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrPersonnelTransferService.insertHrPersonnelTransfer(hrPersonnelTrans));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrPersonnelTransfer
	 * @Description: TODO 删除人事调动记录
	 * @param request
	 * @param HrPersonnelTransfer
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrPersonnelTransfer",method=RequestMethod.POST)
	public RetDataBean deleteHrPersonnelTransfer(HttpServletRequest request,HrPersonnelTransfer hrPersonnelTrans)
	{
		try
		{
			if(StringUtils.isBlank(hrPersonnelTrans.getTransferId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrPersonnelTrans.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrPersonnelTransferService.deleteHrPersonnelTransfer(hrPersonnelTrans));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrPersonnelTransfer  
	 * @Description: TODO 更新人事调动信息
	 * @param request
	 * @param HrPersonnelTransfer
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrPersonnelTransfer",method=RequestMethod.POST)
	public RetDataBean updateHrPersonnelTransfer(HttpServletRequest request,HrPersonnelTransfer hrPersonnelTrans)
	{
		try
		{
			if(StringUtils.isBlank(hrPersonnelTrans.getTransferId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrPersonnelTransfer.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("transferId",hrPersonnelTrans.getTransferId());
			return RetDataTools.Ok("更新成功!",hrPersonnelTransferService.updateHrPersonnelTransfer(example, hrPersonnelTrans));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertHrWorkSkills   
	 * @Description: TODO 添加劳动持能记录
	 * @param request
	 * @param HrWorkSkills
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrWorkSkills",method=RequestMethod.POST)
	public RetDataBean insertHrWorkSkills (HttpServletRequest request,HrWorkSkills hrWorkSkills)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrWorkSkills.setRecordId(SysTools.getGUID());
			hrWorkSkills.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrWorkSkills.setCreateUser(account.getAccountId());
			hrWorkSkills.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrWorkSkillsService.insertHrWorkSkills(hrWorkSkills));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrWorkSkills   
	 * @Description: TODO 删除劳动技能
	 * @param request
	 * @param HrWorkSkills
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrWorkSkills",method=RequestMethod.POST)
	public RetDataBean deleteHrWorkSkills(HttpServletRequest request,HrWorkSkills hrWorkSkills)
	{
		try
		{
			if(StringUtils.isBlank(hrWorkSkills.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrWorkSkills.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrWorkSkillsService.deleteHrWorkSkills(hrWorkSkills));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrWorkSkills  
	 * @Description: TODO 更新劳动技能
	 * @param request
	 * @param HrWorkSkills
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrWorkSkills",method=RequestMethod.POST)
	public RetDataBean updateHrWorkSkills(HttpServletRequest request,HrWorkSkills hrWorkSkills)
	{
		try
		{
			if(StringUtils.isBlank(hrWorkSkills.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrWorkSkills.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrWorkSkills.getRecordId());
			return RetDataTools.Ok("更新成功!",hrWorkSkillsService.updateHrWorkSkills(example, hrWorkSkills));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertHrWorkRecord   
	 * @Description: TODO 添加工作记录
	 * @param request
	 * @param HrWorkRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrWorkRecord",method=RequestMethod.POST)
	public RetDataBean insertHrWorkRecord (HttpServletRequest request,HrWorkRecord hrWorkRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrWorkRecord.setRecordId(SysTools.getGUID());
			hrWorkRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrWorkRecord.setCreateUser(account.getAccountId());
			hrWorkRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrWorkRecordService.insertHrWorkRecord(hrWorkRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrWorkRecord   
	 * @Description: TODO 删除工作记录
	 * @param request
	 * @param HrWorkRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrWorkRecord",method=RequestMethod.POST)
	public RetDataBean deleteHrWorkRecord(HttpServletRequest request,HrWorkRecord hrWorkRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrWorkRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrWorkRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrWorkRecordService.deleteHrWorkRecord(hrWorkRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrWorkRecord  
	 * @Description: TODO 更新工作记录
	 * @param request
	 * @param HrWorkRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrWorkRecord",method=RequestMethod.POST)
	public RetDataBean updateHrWorkRecord(HttpServletRequest request,HrWorkRecord hrWorkRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrWorkRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrWorkRecord.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrWorkRecord.getRecordId());
			return RetDataTools.Ok("更新成功!",hrWorkRecordService.updateHrWorkRecord(example, hrWorkRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertHrLearnRecord   
	 * @Description: TODO 添加学习记录
	 * @param request
	 * @param HrLearnRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrLearnRecord",method=RequestMethod.POST)
	public RetDataBean insertHrLearnRecord (HttpServletRequest request,HrLearnRecord hrLearnRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrLearnRecord.setRecordId(SysTools.getGUID());
			hrLearnRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrLearnRecord.setCreateUser(account.getAccountId());
			hrLearnRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrLearnRecordService.insertHrLearnRecord(hrLearnRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrLearnRecord   
	 * @Description: TODO 删除学习记录
	 * @param request
	 * @param HrLearnRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrLearnRecord",method=RequestMethod.POST)
	public RetDataBean deleteHrLearnRecord(HttpServletRequest request,HrLearnRecord hrLearnRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrLearnRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrLearnRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrLearnRecordService.deleteHrLearnRecord(hrLearnRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrLearnRecord  
	 * @Description: TODO 更新学习记录
	 * @param request
	 * @param HrLearnRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrLearnRecord",method=RequestMethod.POST)
	public RetDataBean updateHrLearnRecord(HttpServletRequest request,HrLearnRecord hrLearnRecord)
	{
		try
		{
			if(StringUtils.isBlank(hrLearnRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrLearnRecord.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",hrLearnRecord.getRecordId());
			return RetDataTools.Ok("更新成功!",hrLearnRecordService.updateHrLearnRecord(example, hrLearnRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertHrLicence   
	 * @Description: TODO 添加证照记录
	 * @param request
	 * @param HrLicence
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrLicence",method=RequestMethod.POST)
	public RetDataBean insertHrLicence(HttpServletRequest request,HrLicence hrLicence)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrLicence.setLicenceId(SysTools.getGUID());
			hrLicence.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrLicence.setCreateUser(account.getAccountId());
			hrLicence.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrLicenceService.insertHrLicence(hrLicence));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrLicence   
	 * @Description: TODO 删除证照记录
	 * @param request
	 * @param HrLicence
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrLicence",method=RequestMethod.POST)
	public RetDataBean deleteHrLicence(HttpServletRequest request,HrLicence hrLicence)
	{
		try
		{
			if(StringUtils.isBlank(hrLicence.getLicenceId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrLicence.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrLicenceService.deleteHrLicence(hrLicence));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrLicence   
	 * @Description: TODO 更新证照记录
	 * @param request
	 * @param HrLicence
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrLicence",method=RequestMethod.POST)
	public RetDataBean updateHrLicence(HttpServletRequest request,HrLicence hrLicence)
	{
		try
		{
			if(StringUtils.isBlank(hrLicence.getLicenceId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrLicence.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("licenceId",hrLicence.getLicenceId());
			return RetDataTools.Ok("更新成功!",hrLicenceService.updateHrLicence(example, hrLicence));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertHrIncentive   
	 * @Description: TODO 添加奖惩记录
	 * @param request
	 * @param hrContract
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrIncentive",method=RequestMethod.POST)
	public RetDataBean insertHrIncentive(HttpServletRequest request,HrIncentive hrIncentive)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrIncentive.setIncentiveId(SysTools.getGUID());
			hrIncentive.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrIncentive.setCreateUser(account.getAccountId());
			hrIncentive.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrIncentiveService.insertHrIncentive(hrIncentive));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrIncentive   
	 * @Description: TODO 删除奖惩记录
	 * @param request
	 * @param hrContract
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrIncentive",method=RequestMethod.POST)
	public RetDataBean deleteHrIncentive(HttpServletRequest request,HrIncentive hrIncentive)
	{
		try
		{
			if(StringUtils.isBlank(hrIncentive.getIncentiveId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrIncentive.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrIncentiveService.deleteHrIncentive(hrIncentive));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrIncentive   
	 * @Description: TODO 更新奖惩记录
	 * @param request
	 * @param hrContract
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrIncentive",method=RequestMethod.POST)
	public RetDataBean updateHrIncentive(HttpServletRequest request,HrIncentive hrIncentive)
	{
		try
		{
			if(StringUtils.isBlank(hrIncentive.getIncentiveId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrIncentive.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("incentiveId",hrIncentive.getIncentiveId());
			return RetDataTools.Ok("更新成功!",hrIncentiveService.updateHrIncentive(example, hrIncentive));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @Title: insertHrContract   
	 * @Description: TODO 添加HR合同
	 * @param request
	 * @param hrContract
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrContract",method=RequestMethod.POST)
	public RetDataBean insertHrContract(HttpServletRequest request,HrContract hrContract)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrContract.setContractId(SysTools.getGUID());
			hrContract.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrContract.setCreateUser(account.getAccountId());
			hrContract.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrContractService.insertHrContract(hrContract));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteHrContract   
	 * @Description: TODO 删除合同
	 * @param request
	 * @param hrContract
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrContract",method=RequestMethod.POST)
	public RetDataBean deleteHrContract(HttpServletRequest request,HrContract hrContract)
	{
		try
		{
			if(StringUtils.isBlank(hrContract.getContractId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrContract.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrContractService.deleteHrContract(hrContract));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrContract   
	 * @Description: TODO 更新合同
	 * @param request
	 * @param hrContract
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrContract",method=RequestMethod.POST)
	public RetDataBean updateHrContract(HttpServletRequest request,HrContract hrContract)
	{
		try
		{
			if(StringUtils.isBlank(hrContract.getContractId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrContract.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("contractId",hrContract.getContractId());
			return RetDataTools.Ok("更新成功!",hrContractService.updateHrContract(example, hrContract));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertHrWagesLevel
	 * @Description: TODO 添加新工资级别
	 * @param: request
	 * @param: hrWorkType
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertHrWagesLevel",method=RequestMethod.POST)
	public RetDataBean insertHrWagesLevel(HttpServletRequest request,HrWagesLevel hrWagesLevel)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrWagesLevel.setWagesId(SysTools.getGUID());
			hrWagesLevel.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrWagesLevel.setCreateUser(account.getAccountId());
			hrWagesLevel.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrWagesLevelService.insertHrWagesLevel(hrWagesLevel));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: deleteHrWagesLevel   
	 * @Description: TODO 删除工资级别
	 * @param: request
	 * @param: hrWorkType
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteHrWagesLevel",method=RequestMethod.POST)
	public RetDataBean deleteHrWagesLevel(HttpServletRequest request,HrWagesLevel hrWagesLevel)
	{
		try
		{
			if(StringUtils.isBlank(hrWagesLevel.getWagesId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrWagesLevel.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrWagesLevelService.deleteHrWagesLevel(hrWagesLevel));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrWagesLevel   
	 * @Description: TODO 更新工资级别
	 * @param: request
	 * @param: hrWorkType
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateHrWagesLevel",method=RequestMethod.POST)
	public RetDataBean updateHrWagesLevel(HttpServletRequest request,HrWagesLevel hrWagesLevel)
	{
		try
		{
			if(StringUtils.isBlank(hrWagesLevel.getWagesId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrWagesLevel.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("wagesId",hrWagesLevel.getWagesId());
			return RetDataTools.Ok("更新成功!",hrWagesLevelService.updateHrWagesLevel(example, hrWagesLevel));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertHrUserInfo   
	 * @Description: TODO 添加人员信息 
	 * @param: request
	 * @param: hrUserInfo
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertHrUserInfo",method=RequestMethod.POST)
	public RetDataBean insertHrUserInfo(HttpServletRequest request,HrUserInfo hrUserInfo)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrUserInfo.setUserId(SysTools.getGUID());
			hrUserInfo.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrUserInfo.setCreateUser(account.getAccountId());
			hrUserInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrUserInfoService.insertHrUserInfo(hrUserInfo));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteHrUserInfo   
	 * @Description: TODO 删除人员信息
	 * @param: request
	 * @param: hrUserInfo
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteHrUserInfo",method=RequestMethod.POST)
	public RetDataBean deleteHrUserInfo(HttpServletRequest request,HrUserInfo hrUserInfo)
	{
		try
		{
			if(StringUtils.isBlank(hrUserInfo.getUserId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrUserInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrUserInfoService.deleteHrUserInfo(hrUserInfo));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrUserInfo   
	 * @Description: TODO 更新人员信息
	 * @param: request
	 * @param: hrUserInfo
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateHrUserInfo",method=RequestMethod.POST)
	public RetDataBean updateHrUserInfo(HttpServletRequest request,HrUserInfo hrUserInfo)
	{
		try
		{
			if(StringUtils.isBlank(hrUserInfo.getUserId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrUserInfo.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("userId",hrUserInfo.getUserId());
			return RetDataTools.Ok("更新成功!",hrUserInfoService.updateHrUserInfo(example, hrUserInfo));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertHrDepartment   
	 * @Description: TODO 添加人员部门
	 * @param: request
	 * @param: hrDepartment
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertHrDepartment",method=RequestMethod.POST)
	public RetDataBean insertHrDepartment(HttpServletRequest request,HrDepartment hrDepartment)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			hrDepartment.setDeptId(SysTools.getGUID());
			hrDepartment.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrDepartmentService.insertHrDepartment(hrDepartment));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteHrDepartment   
	 * @Description: TODO 删除人员部门
	 * @param: request
	 * @param: hrDepartment
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteHrDepartment",method=RequestMethod.POST)
	public RetDataBean deleteHrDepartment(HttpServletRequest request,HrDepartment hrDepartment)
	{
		try
		{
			if(StringUtils.isBlank(hrDepartment.getDeptId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrDepartment.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrDepartmentService.deleteHrDepartment(hrDepartment));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrDepartment   
	 * @Description: TODO 更新人员部门信息
	 * @param: request
	 * @param: hrDepartment
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateHrDepartment",method=RequestMethod.POST)
	public RetDataBean updateHrDepartment(HttpServletRequest request,HrDepartment hrDepartment)
	{
		try
		{
			if(StringUtils.isBlank(hrDepartment.getDeptId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrDepartment.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("deptId",hrDepartment.getDeptId());
			return RetDataTools.Ok("更新成功!",hrDepartmentService.updateHrDepartment(example, hrDepartment));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertUserLevel   
	 * @Description: TODO 添加行政级别 
	 * @param: request
	 * @param: hrUserLevel
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertHrUserLevel",method=RequestMethod.POST)
	public RetDataBean insertHrUserLevel(HttpServletRequest request,HrUserLevel hrUserLevel)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(hrUserLevel.getSuperior()))
			{
				hrUserLevel.setSuperior("0");
			}
			hrUserLevel.setLevelId(SysTools.getGUID());
			hrUserLevel.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrUserLevel.setCreateUser(account.getAccountId());
			hrUserLevel.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrUserLevelService.insertHrUserLevel(hrUserLevel));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteHrUserLevel   
	 * @Description: TODO 删除行政级别   
	 * @param: request
	 * @param: hrUserLevel
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteHrUserLevel",method=RequestMethod.POST)
	public RetDataBean deleteHrUserLevel(HttpServletRequest request,HrUserLevel hrUserLevel)
	{
		try
		{
			if(StringUtils.isBlank(hrUserLevel.getLevelId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrUserLevel.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",hrUserLevelService.deleteHrUserLevel(hrUserLevel));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrUserLevel   
	 * @Description: TODO 更新
	 * @param: request
	 * @param: hrUserLevel
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateHrUserLevel",method=RequestMethod.POST)
	public RetDataBean updateHrUserLevel(HttpServletRequest request,HrUserLevel hrUserLevel)
	{
		try
		{
			if(StringUtils.isBlank(hrUserLevel.getLevelId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrUserLevel.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("sortId",hrUserLevel.getLevelId());
			return RetDataTools.Ok("更新成功!",hrUserLevelService.updateHrUserLevel(example, hrUserLevel));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertHrClassCode   
	 * @Description: TODO 添加HR分类代码
	 * @param request
	 * @param hrClassCode
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertHrClassCode",method=RequestMethod.POST)
	public RetDataBean insertHrClassCode(HttpServletRequest request,HrClassCode hrClassCode)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(hrClassCode.getParentId()))
			{
				hrClassCode.setParentId("");
			}
			hrClassCode.setCodeFlag("1");
			hrClassCode.setCodeId(SysTools.getGUID());
			hrClassCode.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			hrClassCode.setCreateUser(account.getAccountId());
			hrClassCode.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",hrClassCodeService.insertHrClassCode(hrClassCode));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateHrClassCode   
	 * @Description: TODO 更新HR分类代码
	 * @param request
	 * @param hrClassCode
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateHrClassCode",method=RequestMethod.POST)
	public RetDataBean updateHrClassCode(HttpServletRequest request,HrClassCode hrClassCode)
	{
		try
		{
			if(StringUtils.isBlank(hrClassCode.getCodeId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(HrClassCode.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("codeId",hrClassCode.getCodeId());
			return RetDataTools.Ok("更新成功!",hrClassCodeService.updateHrClassCode(example, hrClassCode));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteHrClassCode   
	 * @Description: TODO 删除分类码及以下的子集
	 * @param request
	 * @param hrClassCode
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteHrClassCode",method=RequestMethod.POST)
	public RetDataBean deleteHrClassCode(HttpServletRequest request,HrClassCode hrClassCode)
	{
		try
		{
			if(StringUtils.isBlank(hrClassCode.getCodeId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			hrClassCode.setOrgId(account.getOrgId());
			hrClassCode = hrClassCodeService.selectOneHrClassCode(hrClassCode);
			HrClassCode hrClassCode1 = new HrClassCode();
			hrClassCode1.setParentId(hrClassCode.getCodeId());
			hrClassCodeService.deleteHrClassCode(hrClassCode1);
			return RetDataTools.Ok("删除分类码成功!",hrClassCodeService.deleteHrClassCode(hrClassCode));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
