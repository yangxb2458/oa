/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrPageController.java   
 * @Package com.core136.controller.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月14日 下午1:21:33   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.hr;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lsq
 *
 */
@Controller
@RequestMapping("/app/core/hr")
public class HrPageController {
	/**
	 * 
	 * @Title: goBaseQuery   
	 * @Description: TODO 基本档案查询
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/query")
	public ModelAndView  goBaseQuery()
	{
		ModelAndView mv = null;
		try
		{
		mv = new ModelAndView("app/core/hr/baseinfo/querybaseinfo");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goAnalysis   
	 * @Description: TODO 人事数据分析
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/analysis")
	public ModelAndView  goAnalysis(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/analysis/analysisbaseinfo");
		}else {
			if(view.equals("contract"))
			{
				mv = new ModelAndView("app/core/hr/analysis/analysiscontract");
			}else if(view.equals("incentive"))
			{
				mv = new ModelAndView("app/core/hr/analysis/analysisincentive");
			}else if(view.equals("licence"))
			{
				mv = new ModelAndView("app/core/hr/analysis/analysislicence");
			}else if(view.equals("learn"))
			{
				mv = new ModelAndView("app/core/hr/analysis/analysislearn");
			}else if(view.equals("skills"))
			{
				mv = new ModelAndView("app/core/hr/analysis/analysisskills");
			}else if(view.equals("transfer"))
			{
				mv = new ModelAndView("app/core/hr/analysis/analysistransfer");
			}else if(view.equals("leave"))
			{
				mv = new ModelAndView("app/core/hr/analysis/analysisleave");
			}else if(view.equals("reinstat"))
			{
				mv = new ModelAndView("app/core/hr/analysis/analysisreinstat");
			}else if(view.equals("evaluation"))
			{
				mv = new ModelAndView("app/core/hr/analysis/analysisevaluation");
			}else if(view.equals("care"))
			{
				mv = new ModelAndView("app/core/hr/analysis/analysiscare");
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
	 * @Title: goEvaluateQuery   
	 * @Description: TODO 人员评价查询
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/evaluatequery")
	public ModelAndView  goEvaluateQuery()
	{
		ModelAndView mv = null;
		try
		{
		mv = new ModelAndView("app/core/hr/evaluate/evaluatequery");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: evaluate   
	 * @Description: TODO 人员评价
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/evaluate")
	public ModelAndView  goEvaluate()
	{
		ModelAndView mv = null;
		try
		{
		mv = new ModelAndView("app/core/hr/evaluate/evaluate");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goWelfare   
	 * @Description: TODO 人员福利
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/welfare")
	public ModelAndView  goWelfare(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/salary/welfaremanage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/salary/welfare");
			}else if(view.equals("import"))
			{
				mv = new ModelAndView("app/core/hr/salary/welfareimport");
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
	 * @Title: goSalary   
	 * @Description: TODO 人员薪资管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/salary")
	public ModelAndView  goSalary(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/salary/salarymanage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/salary/salary");
			}else if(view.equals("import"))
			{
				mv = new ModelAndView("app/core/hr/salary/salaryimport");
				mv.addObject("retDataBean",null);
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
	 * @Title: goKpiPlan   
	 * @Description: TODO 考核计划
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/kpi/kpiplan")
	public ModelAndView  goKpiPlan(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/kpi/kpiplanmanage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/kpi/kpiplan");
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
	 * @Title: goPlanApproved   
	 * @Description: TODO 招聘需求审批
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/planapproved")
	public ModelAndView  goPlanApproved(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/requirements/planapproved");
		}else {
			if(view.equals("manage"))
			{
				mv = new ModelAndView("app/core/hr/requirements/planapprovedmanage");
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
	 * @Title: goKpiItem   
	 * @Description: TODO KPI 考核指标
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/kpi/kpiitem")
	public ModelAndView  goKpiItem(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/kpi/kpiitemmanage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/kpi/kpiitem");
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
	 * @Title: goRecruitplan   
	 * @Description: TODO 招聘计划管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/recruitplan")
	public ModelAndView  goRecruitplan(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/requirements/recruitplanmanage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/requirements/recruitplan");
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
	 * @Title: goRequirements   
	 * @Description: TODO 招聘需求
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/requirements")
	public ModelAndView  goRequirements(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/requirements/requirementsmanage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/requirements/requirements");
			}else if(view.equals("queryplan"))
			{
				mv = new ModelAndView("app/core/hr/requirements/queryplan");
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
	 * @Title: goTrainApproved   
	 * @Description: TODO 培训审批
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/trainapproved")
	public ModelAndView  goTrainApproved(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/train/approved");
		}else {
			if(view.equals("manage"))
			{
				mv = new ModelAndView("app/core/hr/train/approvedmanage");
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
	 * @Title: goTrain   
	 * @Description: TODO 培训管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/train")
	public ModelAndView  goTrain(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/train/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/train/input");
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
	 * @Title: goCare   
	 * @Description: TODO 员工关怀
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/care")
	public ModelAndView  goCare(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/care/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/care/input");
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
	 * @Title: goTitleEvaluation   
	 * @Description: TODO 人员职称评定
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/titleevaluation")
	public ModelAndView  goTitleEvaluation(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/evaluation/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/evaluation/input");
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
	 * @Title: goReinstatement   
	 * @Description: TODO 复职管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/reinstatement")
	public ModelAndView  goReinstatement(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/reinstatement/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/reinstatement/input");
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
	 * @Title: goLeave   
	 * @Description: TODO 人员离职
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/leave")
	public ModelAndView  goLeave(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/leave/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/leave/input");
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
	 * @Title: goTranSfer   
	 * @Description: TODO 人事调动
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/transfer")
	public ModelAndView  goTranSfer(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/transfer/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/transfer/input");
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
	 * @Title: goLaborSkills   
	 * @Description: TODO 劳动技能管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/laborskills")
	public ModelAndView  goLaborSkills(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/skills/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/skills/input");
			}else if(view.equals("import"))
			{
				mv = new ModelAndView("app/core/hr/skills/import");
				mv.addObject("retDataBean",null);
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
	 * @Title: goWorkExperience   
	 * @Description: TODO 工作经历
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/workexperience")
	public ModelAndView  goWorkExperience(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/work/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/work/input");
			}else if(view.equals("import"))
			{
				mv = new ModelAndView("app/core/hr/work/import");
				mv.addObject("retDataBean",null);
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
	 * @Title: goLearn   
	 * @Description: TODO 学习经历
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/learn")
	public ModelAndView  goLearn(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/learn/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/learn/input");
			}else if(view.equals("import"))
			{
				mv = new ModelAndView("app/core/hr/learn/import");
				mv.addObject("retDataBean",null);
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
	 * @Title: goLicense   
	 * @Description: TODO 证照管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/licence")
	public ModelAndView  goLicense(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/licence/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/licence/input");
			}else if(view.equals("import"))
			{
				mv = new ModelAndView("app/core/hr/licence/import");
				mv.addObject("retDataBean",null);
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
	 * @Title: contract   
	 * @Description: TODO HR合同管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/contract")
	public ModelAndView  contract(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/contract/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/contract/input");
			}else if(view.equals("import"))
			{
				mv = new ModelAndView("app/core/hr/contract/import");
				mv.addObject("retDataBean",null);
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
	 * @Title: incentive   
	 * @Description: TODO 奖惩管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/incentive")
	public ModelAndView  incentive(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/incentive/manage");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/incentive/input");
			}else if(view.equals("import"))
			{
				mv = new ModelAndView("app/core/hr/incentive/import");
				mv.addObject("retDataBean",null);
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
	 * @Title: setBaseinfo   
	 * @Description: TODO 人员基本信息
	 * @param: view
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/baseinfo")
	public ModelAndView  setBaseinfo(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/hr/baseinfo/baseinfo");
		}else {
			if(view.equals("input"))
			{
				mv = new ModelAndView("app/core/hr/baseinfo/baseinfoinput");
			}else if(view.equals("import"))
			{
				mv = new ModelAndView("app/core/hr/baseinfo/baseinfoimport");
				mv.addObject("retDataBean",null);
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
 * @Title: goUserInofDetails   
 * @Description: TODO HR用户信息详情
 * @param: view
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/userinfodetails")
	public ModelAndView  goUserInfoDetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/baseinfo/userinfodetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goReinstatementDetails   
	 * @Description: TODO 复职详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/reinstatementdetails")
	public ModelAndView  goReinstatementDetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/reinstatement/reinstatementdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goHrRecruitPlandetails   
	 * @Description: TODO 招聘计划详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/hrrecruitplandetails")
	public ModelAndView  goHrRecruitPlandetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/requirements/hrrecruitplandetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goLeaveDetails   
	 * @Description: TODO 人员离职详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/leavedetails")
	public ModelAndView  goLeaveDetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/leave/leavedetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goKpiItemDetails   
	 * @Description: TODO KPI考核指标详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/kpi/kpiitemdetails")
	public ModelAndView  goKpiItemDetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/kpi/kpiitemdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goTrainDetails   
	 * @Description: TODO 培训详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/traindetails")
	public ModelAndView  goTrainDetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/train/traindetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goTransferDetails   
	 * @Description: TODO 人事调动详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/transferdetails")
	public ModelAndView  goTransferDetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/transfer/transferdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goLearnetails   
	 * @Description: TODO 学习经历详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/learndetails")
	public ModelAndView  goLearnetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/learn/learndetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goLicencedetails   
	 * @Description: TODO 证照详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/licencedetails")
	public ModelAndView  goLicencedetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/licence/licencedetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goIncentivedetails   
	 * @Description: TODO 奖惩详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/incentivedetails")
	public ModelAndView  goIncentivedetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/incentive/incentivedetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goContractDetails   
	 * @Description: TODO 合同详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/contractdetails")
	public ModelAndView  goContractDetails()
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/contract/contractdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goCareDetails   
	 * @Description: TODO 员工关怀详情页
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/caredetails")
	public ModelAndView  goCareDetails()
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/care/caredetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goWorkDetails   
	 * @Description: TODO 工作经历详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/workdetails")
	public ModelAndView  goWorkDetails()
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/work/workdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goSkillsDetails   
	 * @Description: TODO 劳动技能详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/skillsdetails")
	public ModelAndView  goSkillsDetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/skills/skillsdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goEvaluationDetails   
	 * @Description: TODO 人员职称评定详情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/evaluationdetails")
	public ModelAndView  goEvaluationDetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/evaluation/evaluationdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goRequirementsDetails   
	 * @Description: TODO 招聘需求想情
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/requirementsdetails")
	public ModelAndView  goRequirementsDetails(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/requirements/requirementsdetails");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	
	/**
	 * 
	 * @Title: goWagesLevel   
	 * @Description: TODO 工资级别设置
	 * @param: view
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/wageslevel")
	public ModelAndView  goWagesLevel(String view)
	{
		try {
		ModelAndView mv = new ModelAndView("app/core/hr/set/wageslevel");
		return mv;
		}catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: addhrdept   
	 * @Description: TODO 添加HR人员部门
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/hrdept")
	public ModelAndView  addhrdept()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/hr/set/hrdept");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
/**
 * 	
 * @Title: addhruserinfo   
 * @Description: TODO 人员录入
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/hruserinfo")
	public ModelAndView  addhruserinfo()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/hr/set/hruserinfo");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goSalarydetails   
	 * @Description: TODO 人员薪资详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/salarydetails")
	public ModelAndView  goSalarydetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/hr/salary/salarydetails");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goWelfaredetails   
	 * @Description: TODO 人员福利详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/welfaredetails")
	public ModelAndView  goWelfaredetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/hr/salary/welfaredetails");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
/**
 * 	
 * @Title: addhruserleave   
 * @Description: TODO 行政职务
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/hruserlevel")
	public ModelAndView  addhruserlevel()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/hr/set/hruserlevel");
		return mv;
		}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	
	/**
	 * 
	 * @Title: setClassCode   
	 * @Description: TODO 设置HR系统编码
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/hrclasscode")
	public ModelAndView  setClassCode()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/hr/set/classcode");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goMyHrInfo   
	 * @Description: TODO 员工自助查询
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/myhrinfo")
	public ModelAndView  goMyHrInfo(String view)
	{
		ModelAndView mv = null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("app/core/hr/query/myhrinfo");
			}else
			{
				if(view.equals("contract"))
				{
					mv = new ModelAndView("app/core/hr/query/mycontract");
				}else if(view.equals("incentive"))
				{
					mv = new ModelAndView("app/core/hr/query/myincentive");
				}else if(view.equals("licence"))
				{
					mv = new ModelAndView("app/core/hr/query/mylicence");
				}else if(view.equals("learn"))
				{
					mv = new ModelAndView("app/core/hr/query/mylearn");
				}else if(view.equals("work"))
				{
					mv = new ModelAndView("app/core/hr/query/mywork");
				}else if(view.equals("transfer"))
				{
					mv = new ModelAndView("app/core/hr/query/mytransfer");
				}else if(view.equals("salary"))
				{
					mv = new ModelAndView("app/core/hr/query/mysalary");
				}else if(view.equals("kpi"))
				{
					mv = new ModelAndView("app/core/hr/query/mykpi");
				}
			}
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
}
