/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetHrController.java   
 * @Package com.core136.controller.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月14日 下午1:21:05   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.hr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
import com.core136.bean.sys.PageParam;
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
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/ret/hrget")
public class RoutGetHrController {
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
 * @Title: getMyHrSalaryRecordList   
 * @Description: TODO 获取人员薪资列表
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyHrSalaryRecordList",method=RequestMethod.POST)
public RetDataBean getMyHrSalaryRecordList(HttpServletRequest request,PageParam pageParam)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("S.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrSalaryRecordService.getMyHrSalaryRecordList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getMyHrPersonnelTransferList   
 * @Description: TODO 人个工作调动记录
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyHrPersonnelTransferList",method=RequestMethod.POST)
public RetDataBean getMyHrPersonnelTransferList(HttpServletRequest request,PageParam pageParam)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("P.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrPersonnelTransferService.getMyHrPersonnelTransferList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getMyHrWorkRecordList   
 * @Description: TODO 查询个人工作经历
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyHrWorkRecordList",method=RequestMethod.POST)
public RetDataBean getMyHrWorkRecordList(HttpServletRequest request,PageParam pageParam)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("W.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrWorkRecordService.getMyHrWorkRecordList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyHrLearnRecordList   
 * @Description: TODO 个人查询学习记录
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyHrLearnRecordList",method=RequestMethod.POST)
public RetDataBean getMyHrLearnRecordList(HttpServletRequest request,PageParam pageParam)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("L.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrLearnRecordService.getMyHrLearnRecordList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyHrLicenceList   
 * @Description: TODO 查询个人证照信息
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyHrLicenceList",method=RequestMethod.POST)
public RetDataBean getMyHrLicenceList(HttpServletRequest request,PageParam pageParam)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("L.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrLicenceService.getMyHrLicenceList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyHrIncentiveList   
 * @Description: TODO 个人查询奖惩记录
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyHrIncentiveList",method=RequestMethod.POST)
public RetDataBean getMyHrIncentiveList(HttpServletRequest request,PageParam pageParam
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("I.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrIncentiveService.getMyHrIncentiveList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyHrContractList   
 * @Description: TODO 查询自己的合同列表
 * @param request
 * @param pageParam
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyHrContractList",method=RequestMethod.POST)
public RetDataBean getMyHrContractList(HttpServletRequest request,PageParam pageParam
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("C.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrContractService.getMyHrContractList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getDeskHrContractList   
 * @Description: TODO 获取快到期的合同列表
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getDeskHrContractList",method=RequestMethod.POST)
public RetDataBean getDeskHrContractList(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("数据请求成功!",hrContractService.getDeskHrContractList(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getDeskHrUserInfo   
 * @Description: TODO 获取人力资源门户人员信息
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getDeskHrUserInfo",method=RequestMethod.POST)
public RetDataBean getDeskHrUserInfo(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("数据请求成功!",hrUserInfoService.getDeskHrUserInfo(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrKpiPlanById   
 * @Description: TODO 获取考核计划详情
 * @param request
 * @param hrKpiPlan
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrKpiPlanById",method=RequestMethod.POST)
public RetDataBean getHrKpiPlanById(HttpServletRequest request,HrKpiPlan hrKpiPlan)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrKpiPlan.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrKpiPlanService.selectOneHrKpiPlan(hrKpiPlan));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getHrEvaluateById   
 * @Description: TODO 获取人员评价详情
 * @param request
 * @param hrEvaluate
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrEvaluateById",method=RequestMethod.POST)
public RetDataBean getHrEvaluateById(HttpServletRequest request,HrEvaluate hrEvaluate)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrEvaluate.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrEvaluateService.selectOneHrEvaluate(hrEvaluate));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrWelfareRecordById   
 * @Description: TODO 人员福利详情
 * @param request
 * @param hrWelfareRecord
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrWelfareRecordById",method=RequestMethod.POST)
public RetDataBean getHrWelfareRecordById(HttpServletRequest request,HrWelfareRecord hrWelfareRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrWelfareRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrWelfareRecordService.selectOneHrWelfareRecord(hrWelfareRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrSalaryRecordById   
 * @Description: TODO 获取人员薪资详情
 * @param request
 * @param hrSalaryRecord
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrSalaryRecordById",method=RequestMethod.POST)
public RetDataBean getHrSalaryRecordById(HttpServletRequest request,HrSalaryRecord hrSalaryRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrSalaryRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrSalaryRecordService.selectOneHrSalaryRecord(hrSalaryRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrRecruitPlanById   
 * @Description: TODO 获取招聘计划详情
 * @param request
 * @param hrRecruitPlan
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrRecruitPlanById",method=RequestMethod.POST)
public RetDataBean getHrRecruitPlanById(HttpServletRequest request,HrRecruitPlan hrRecruitPlan)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrRecruitPlan.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrRecruitPlanService.selectOneHrRecruitPlan(hrRecruitPlan));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrRecruitPlanForSelect   
 * @Description: TODO 获取当前可填报的招聘计划
 * @param request
 * @param hrRecruitPlan
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrRecruitPlanForSelect",method=RequestMethod.POST)
public RetDataBean getHrRecruitPlanForSelect(HttpServletRequest request,HrRecruitPlan hrRecruitPlan)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("数据请求成功!",hrRecruitPlanService.getHrRecruitPlanForSelect(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrKpiItemById   
 * @Description: TODO 获取考核指标详情
 * @param request
 * @param hrKpiItem
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrKpiItemById",method=RequestMethod.POST)
public RetDataBean getHrKpiItemById(HttpServletRequest request,HrKpiItem hrKpiItem)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrKpiItem.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrKpiItemService.selectOneHrKpiItem(hrKpiItem));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrRecruitNeedsById   
 * @Description: TODO 获取招聘需求详情
 * @param request
 * @param hrRecruitNeeds
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrRecruitNeedsById",method=RequestMethod.POST)
public RetDataBean getHrRecruitNeedsById(HttpServletRequest request,HrRecruitNeeds hrRecruitNeeds)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrRecruitNeeds.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrRecruitNeedsService.selectOneHrRecruitNeeds(hrRecruitNeeds));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrTrainRecordById   
 * @Description: TODO 获取人员培训详情
 * @param request
 * @param hrTrainRecord
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrTrainRecordById",method=RequestMethod.POST)
public RetDataBean getHrTrainRecordById(HttpServletRequest request,HrTrainRecord hrTrainRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrTrainRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrTrainRecordService.selectOneHrTrainRecord(hrTrainRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrCareRecordById   
 * @Description: TODO 获取员工关怀详情
 * @param request
 * @param hrCareRecord
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrCareRecordById",method=RequestMethod.POST)
public RetDataBean getHrCareRecordById(HttpServletRequest request,HrCareRecord hrCareRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrCareRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrCareRecordService.selectOneHrCareRecord(hrCareRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrTitleEvaluationById   
 * @Description: TODO 获取职称评定详情
 * @param request
 * @param hrTitleEvaluation
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrTitleEvaluationById",method=RequestMethod.POST)
public RetDataBean getHrTitleEvaluationById(HttpServletRequest request,HrTitleEvaluation hrTitleEvaluation)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrTitleEvaluation.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrTitleEvaluationService.selectOneHrTitleEvaluation(hrTitleEvaluation));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getHrReinstatementById   
 * @Description: TODO 获取复职详情
 * @param request
 * @param hrReinstatement
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrReinstatementById",method=RequestMethod.POST)
public RetDataBean getHrReinstatementById(HttpServletRequest request,HrReinstatement hrReinstatement)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrReinstatement.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrReinstatementService.selectOneHrReinstatement(hrReinstatement));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrLeaveRecordById   
 * @Description: TODO 获取离职记录详情
 * @param request
 * @param hrLeaveRecord
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrLeaveRecordById",method=RequestMethod.POST)
public RetDataBean getHrLeaveRecordById(HttpServletRequest request,HrLeaveRecord hrLeaveRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrLeaveRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrLeaveRecordService.selectOneHrLeaveRecord(hrLeaveRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrPersonnelTransferById   
 * @Description: TODO 获取人事调动详情
 * @param request
 * @param hrPersonnelTransfer
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrPersonnelTransferById",method=RequestMethod.POST)
public RetDataBean getHrPersonnelTransferById(HttpServletRequest request,HrPersonnelTransfer hrPersonnelTransfer)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrPersonnelTransfer.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrPersonnelTransferService.selectOneHrPersonnelTransfer(hrPersonnelTransfer));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrWorkSkillsById   
 * @Description: TODO 获取工作特长详情
 * @param request
 * @param hrWorkSkills
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrWorkSkillsById",method=RequestMethod.POST)
public RetDataBean getHrWorkSkillsById(HttpServletRequest request,HrWorkSkills hrWorkSkills)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrWorkSkills.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrWorkSkillsService.selectOneHrWorkSkills(hrWorkSkills));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrWorkRecordById   
 * @Description: TODO 获取工作记录详情
 * @param request
 * @param hrWorkRecord
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrWorkRecordById",method=RequestMethod.POST)
public RetDataBean getHrWorkRecordById(HttpServletRequest request,HrWorkRecord hrWorkRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrWorkRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrWorkRecordService.selectOneHrWorkRecord(hrWorkRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrLearnRecordById   
 * @Description: TODO 获取学习记录详情
 * @param request
 * @param hrLearnRecord
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrLearnRecordById",method=RequestMethod.POST)
public RetDataBean getHrLearnRecordById(HttpServletRequest request,HrLearnRecord hrLearnRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrLearnRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrLearnRecordService.selectOneHrLearnRecord(hrLearnRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrLicenceById   
 * @Description: TODO 获取证照详情
 * @param request
 * @param hrLicence
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrLicenceById",method=RequestMethod.POST)
public RetDataBean getHrLicenceById(HttpServletRequest request,HrLicence hrLicence)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrLicence.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrLicenceService.selectOneHrLicence(hrLicence));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getHrIncentiveById   
 * @Description: TODO 获取奖惩记录详情
 * @param request
 * @param hrIncentive
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrIncentiveById",method=RequestMethod.POST)
public RetDataBean getHrIncentiveById(HttpServletRequest request,HrIncentive hrIncentive)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrIncentive.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrIncentiveService.selectOneHrIncentive(hrIncentive));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrContractById   
 * @Description: TODO 获取合同详情
 * @param request
 * @param hrContract
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrContractById",method=RequestMethod.POST)
public RetDataBean getHrContractById(HttpServletRequest request,HrContract hrContract)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrContract.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrContractService.selectOneHrContract(hrContract));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrUserInfoBySearchuser   
 * @Description: TODO 查询HR人员
 * @param request
 * @param searchuser
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrUserInfoBySearchuser",method=RequestMethod.POST)
public RetDataBean getHrUserInfoBySearchuser(HttpServletRequest request,String searchuser)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("获取数据成功!",hrUserInfoService.getHrUserInfoBySearchuser(account.getOrgId(),searchuser));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getUserNamesByUserIds   
 * @Description: TODO 获取HR人员列表
 * @param request
 * @param userIds
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getUserNamesByUserIds",method=RequestMethod.POST)
public RetDataBean getUserNamesByUserIds(HttpServletRequest request,String userIds)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("获取数据成功!",hrUserInfoService.getUserNamesByUserIds(account.getOrgId(),userIds));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getWagesLevelListForSelect   
 * @Description: TODO 获取工资级别列表
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getWagesLevelListForSelect",method=RequestMethod.POST)
public RetDataBean getWagesLevelListForSelect(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("获取数据成功!",hrWagesLevelService.getWagesLevelListForSelect(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getWagesLevelList   
 * @Description: TODO 获取工资级别列表
 * @param: request
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getWagesLevelList",method=RequestMethod.POST)
public RetDataBean getWagesLevelList(HttpServletRequest request,PageParam pageParam)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrWagesLevelService.getWagesLevelList(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrUserInfoByBeptIdInWorkList   
 * @Description: TODO 获取部门下的人员列表
 * @param request
 * @param pageParam
 * @param deptId
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrUserInfoByBeptIdInWorkList",method=RequestMethod.POST)
public RetDataBean getHrUserInfoByBeptIdInWorkList(HttpServletRequest request,PageParam pageParam,String deptId,
		String workStatus,String employedTime,String staffCardNo)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setDeptId(deptId);
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrUserInfoService.getHrUserInfoByBeptIdInWorkList(pageParam,workStatus,employedTime,staffCardNo);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrWagesLevelById   
 * @Description: TODO 获取工资级别详情
 * @param: request
 * @param: hrWorkType
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getHrWagesLevelById",method=RequestMethod.POST)
public RetDataBean getHrWagesLevelById(HttpServletRequest request,HrWagesLevel hrWagesLevel)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrWagesLevel.setOrgId(account.getOrgId());
		return RetDataTools.Ok("获取数据成功!",hrWagesLevelService.selectOneHrWagesLevel(hrWagesLevel));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrUserInfoByDeptId   
 * @Description: TODO 获取部门下的人员列表
 * @param: request
 * @param: pageParam
 * @param: deptId
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getHrUserInfoByDeptId",method=RequestMethod.POST)
public RetDataBean getHrUserInfoByDeptId(HttpServletRequest request,String deptId)
{
	try
	{
	Account account=accountService.getRedisAccount(request);
	return RetDataTools.Ok("请求数据成功!", hrUserInfoService.getHrUserInfoByDeptId(account.getOrgId(),deptId));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrDepartmentTree   
 * @Description: TODO 人员基本信息树结构
 * @param: request
 * @param: deptId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
@RequestMapping(value="/getHrUserInfoDepartmentTree",method=RequestMethod.POST)
public List<Map<String,String>> getHrUserInfoDepartmentTree(HttpServletRequest request,String deptId)
{
	try
	{
		String orgLevelId = "0";
		if(StringUtils.isNotBlank(deptId))
		{
			orgLevelId = deptId;
		}
		Account account=accountService.getRedisAccount(request);
		return hrDepartmentService.getHrUserInfoDepartmentTree(account.getOrgId(),orgLevelId);
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return null;
	}
}

/**
 * 
 * @Title: getHrDepartmentTree   
 * @Description: TODO获取HR部门树
 * @param: request
 * @param: deptId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
@RequestMapping(value="/getHrDepartmentTree",method=RequestMethod.POST)
public List<Map<String,String>> getHrDepartmentTree(HttpServletRequest request,String deptId)
{
	try
	{
		String orgLevelId = "0";
		if(StringUtils.isNotBlank(deptId))
		{
			orgLevelId = deptId;
		}
		Account account=accountService.getRedisAccount(request);
		return hrDepartmentService.getHrDepartmentTree(account.getOrgId(),orgLevelId);
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return null;
	}
}
/**
 * 
 * @Title: getHrDepartmentById   
 * @Description: TODO 获取部门详情
 * @param: request
 * @param: hrDepartment
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getHrDepartmentById",method=RequestMethod.POST)
public RetDataBean getHrDepartmentById(HttpServletRequest request,HrDepartment hrDepartment)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrDepartment.setOrgId(account.getOrgId());
		return RetDataTools.Ok("获取数据成功!",hrDepartmentService.selectOneHrDepartment(hrDepartment));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrUserInfoById   
 * @Description: TODO 获取HR的用户详情
 * @param: request
 * @param: hrUserInfo
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getHrUserInfoById",method=RequestMethod.POST)
public RetDataBean getHrUserInfoById(HttpServletRequest request,HrUserInfo hrUserInfo)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrUserInfo.setOrgId(account.getOrgId());
		return RetDataTools.Ok("获取数据成功!",hrUserInfoService.selectOneHrUserInfo(hrUserInfo));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyHrUserInfo   
 * @Description: TODO 获取个人人事信息详情
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyHrUserInfo",method=RequestMethod.POST)
public RetDataBean getMyHrUserInfo(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		HrUserInfo hrUserInfo = new HrUserInfo();
		hrUserInfo.setAccountId(account.getAccountId());
		hrUserInfo.setOrgId(account.getOrgId());
		return RetDataTools.Ok("获取数据成功!",hrUserInfoService.selectOneHrUserInfo(hrUserInfo));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}



/**
 * 
 * @Title: getHrUserInfoForTree   
 * @Description: TODO 人员基本信息树结构
 * @param: request
 * @param: deptId
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getHrUserInfoForTree",method=RequestMethod.POST)
public RetDataBean getHrUserInfoForTree(HttpServletRequest request,String deptId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("获取部门下人员成功!",hrUserInfoService.getHrUserInfoForTree(account.getOrgId(),deptId));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getHrUserLevelById   
 * @Description: TODO 获取行政级别详情
 * @param: request
 * @param: hrUserLevel
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getHrUserLevelById",method=RequestMethod.POST)
public RetDataBean getHrUserLevelById(HttpServletRequest request,HrUserLevel hrUserLevel)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrUserLevel.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",hrUserLevelService.selectOneHrUserLevel(hrUserLevel));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrUserLevelChart   
 * @Description: TODO 获取行政级别CHART数据   
 * @param: request
 * @param: leaveId
 * @param: @return      
 * @return: Object      
 * @throws
 */
@RequestMapping(value="/getHrUserLevelChart",method=RequestMethod.POST)
public Object getHrUserLevelChart(HttpServletRequest request,String leaveId)
{
	try
	{
		if(StringUtils.isBlank(leaveId))
		{
			leaveId = "0";
		}
		Account account=accountService.getRedisAccount(request);
		List<Map<String, Object>> ListMaper = hrUserLevelService.getAllHrUserLevelChart(account.getOrgId(),leaveId);
		if(ListMaper.size()>0)
		{
			return ListMaper.get(0);	
		}else
		{
			return null;
		}
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return null;
	}
}

/**
 * 
 * @Title: getHrUserLevelList   
 * @Description: TODO 获取所有行政级别
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getHrUserLevelList",method=RequestMethod.POST)
public RetDataBean getHrUserLevelList(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(HrUserLevel.class);
		example.setOrderByClause("LEVEL_NO_ID ASC");
		example.createCriteria().andEqualTo("orgId",account.getOrgId());
		return RetDataTools.Ok("请求成功！",hrUserLevelService.selectByExample(example));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getAllParentCodeList   
 * @Description: TODO获取所有的主分类
 * @param request
 * @param hrWorkType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getAllParentCodeList",method=RequestMethod.POST)
public RetDataBean getAllParentCodeList(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("获取数据成功!",hrClassCodeService.getAllParentCodeList(account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getCodeListByModule   
 * @Description: TODO 获取子
 * @param request
 * @param hrClassCode
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getCodeListByModule",method=RequestMethod.POST)
public RetDataBean getCodeListByModule(HttpServletRequest request,String module)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("获取数据成功!",hrClassCodeService.getCodeListByModule(account.getOrgId(),module));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrClassCodeById   
 * @Description: TODO 获取分类详情
 * @param request
 * @param hrClassCode
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrClassCodeById",method=RequestMethod.POST)
public RetDataBean getHrClassCodeById(HttpServletRequest request,HrClassCode hrClassCode)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		hrClassCode.setOrgId(account.getOrgId());
		return RetDataTools.Ok("获取数据成功!",hrClassCodeService.selectOneHrClassCode(hrClassCode));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrDeptList   
 * @Description: TODO 获取子部门列表
 * @param request
 * @param hrDepartment
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrDeptList",method=RequestMethod.POST)
public RetDataBean getHrDeptList(HttpServletRequest request,HrDepartment hrDepartment)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(HrDepartment.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("orgLevelId",hrDepartment.getOrgLevelId());
		return RetDataTools.Ok("请求成功！",hrDepartmentService.getHrDeptList(example));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrDeptNameByStr   
 * @Description: TODO 获取部门名称
 * @param request
 * @param deptIds
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrDeptNameByStr",method=RequestMethod.POST)
public RetDataBean getHrDeptNameByStr(HttpServletRequest request,String deptIds)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功！",hrDepartmentService.getHrDeptNameByStr(account.getOrgId(),deptIds));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrUserLevelByStr   
 * @Description: TODO 获取HR的行政级别名称
 * @param request
 * @param levelIds
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrUserLevelByStr",method=RequestMethod.POST)
public RetDataBean getHrUserLevelByStr(HttpServletRequest request,String levelIds)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功！",hrUserLevelService.getHrUserLevelByStr(account.getOrgId(),levelIds));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrDeptBySearchdept   
 * @Description: TODO 查询HR部门
 * @param request
 * @param searchdept
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrDeptBySearchdept",method=RequestMethod.POST)
public RetDataBean getHrDeptBySearchdept(HttpServletRequest request,String searchdept)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功！",hrDepartmentService.getHrDeptBySearchdept(account.getOrgId(),searchdept));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrContractList   
 * @Description: TODO 获取合同列表
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param enterpries
 * @param contractType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrContractList",method=RequestMethod.POST)
public RetDataBean getHrContractList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,String endTime,
		String enterpries,String contractType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrContractService.getHrContractList(pageParam,userId,beginTime,endTime,enterpries,contractType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrIncentiveList   
 * @Description: TODO 获取奖惩记录列表
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param incentiveType
 * @param incentiveItem
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrIncentiveList",method=RequestMethod.POST)
public RetDataBean getHrIncentiveList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,String endTime,
		String incentiveType,String incentiveItem
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrIncentiveService.getHrIncentiveList(pageParam,userId,beginTime,endTime,incentiveType,incentiveItem);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrClassCodeName   
 * @Description: TODO 获取分类码名称
 * @param request
 * @param module
 * @param codeValue
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrClassCodeName",method=RequestMethod.POST)
public RetDataBean getHrClassCodeName(HttpServletRequest request,String module,String codeValue)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功！",hrClassCodeService.getHrClassCodeName(account.getOrgId(),module,codeValue));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrLicenceList   
 * @Description: TODO 获取证照列表
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param licenceType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrLicenceList",method=RequestMethod.POST)
public RetDataBean getHrLicenceList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,String endTime,
		String licenceType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrLicenceService.getHrLicenceList(pageParam,userId,beginTime,endTime,licenceType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrLearnRecordList   
 * @Description: TODO 获取教育经历列表
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrLearnRecordList",method=RequestMethod.POST)
public RetDataBean getHrLearnRecordList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrLearnRecordService.getHrLearnRecordList(pageParam,userId,beginTime,endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrWorkRecordList   
 * @Description: TODO 获取工作记录
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrWorkRecordList",method=RequestMethod.POST)
public RetDataBean getHrWorkRecordList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,
		String endTime,String nature
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrWorkRecordService.getHrWorkRecordList(pageParam,userId,beginTime,endTime,nature);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrReinstatementList   
 * @Description: TODO 获取复值列表
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param reinstatementType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrReinstatementList",method=RequestMethod.POST)
public RetDataBean getHrReinstatementList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,
		String endTime,String reinstatementType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrReinstatementService.getHrReinstatementList(pageParam,userId,beginTime,endTime,reinstatementType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrWorkSkillsList   
 * @Description: TODO 工作特长列表
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param skillsLevel
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrWorkSkillsList",method=RequestMethod.POST)
public RetDataBean getHrWorkSkillsList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,String endTime,
		String skillsLevel
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrWorkSkillsService.getHrWorkSkillsList(pageParam,userId,beginTime,endTime,skillsLevel);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrPersonnelTransferList   
 * @Description: TODO  获取人员调动列表
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param transferType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrPersonnelTransferList",method=RequestMethod.POST)
public RetDataBean getHrPersonnelTransferList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,String endTime,
		String transferType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrPersonnelTransferService.getHrPersonnelTransferList(pageParam,userId,beginTime,endTime,transferType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getHrSalaryRecordList   
 * @Description: TODO 获取人员薪资列表
 * @param request
 * @param pageParam
 * @param userId
 * @param year
 * @param month
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrSalaryRecordList",method=RequestMethod.POST)
public RetDataBean getHrSalaryRecordList(HttpServletRequest request,PageParam pageParam,String userId,String year,String month
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrSalaryRecordService.getHrSalaryRecordList(pageParam,userId,year,month);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrWelfareRecordList   
 * @Description: TODO 获取福利列表
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param type
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrWelfareRecordList",method=RequestMethod.POST)
public RetDataBean getHrWelfareRecordList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,
		String endTime,String type
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("H.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrWelfareRecordService.getHrWelfareRecordList(pageParam,beginTime,endTime,userId,type);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrLeaveRecordList   
 * @Description: TODO 获取离职人员列表
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param leaveType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrLeaveRecordList",method=RequestMethod.POST)
public RetDataBean getHrLeaveRecordList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,
		String endTime,String leaveType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrLeaveRecordService.getHrLeaveRecordList(pageParam,userId,beginTime,endTime,leaveType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrTitleEvaluationList   
 * @Description: TODO 获取人员职称评定列表
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param getType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrTitleEvaluationList",method=RequestMethod.POST)
public RetDataBean getHrTitleEvaluationList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,
		String endTime,String getType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrTitleEvaluationService.getHrTitleEvaluationList(pageParam,userId,beginTime,endTime,getType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrCareRecordList   
 * @Description: TODO getHrCareRecordList
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param careType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrCareRecordList",method=RequestMethod.POST)
public RetDataBean getHrCareRecordList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,String endTime,
		String careType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrCareRecordService.getHrCareRecordList(pageParam,userId,beginTime,endTime,careType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrTrainRecordList   
 * @Description: TODO 获取培训列表
 * @param request
 * @param pageParam
 * @param createUser
 * @param channel
 * @param courseType
 * @param status
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrTrainRecordList",method=RequestMethod.POST)
public RetDataBean getHrTrainRecordList(HttpServletRequest request,PageParam pageParam,
		String channel,String courseType,String status,String beginTime,String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrTrainRecordService.getHrTrainRecordList(pageParam, channel, courseType, status, beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrTrainRecordApprovedList   
 * @Description: TODO(这里用一句话描述这个方法的作用)
 * @param request
 * @param pageParam
 * @param channel
 * @param courseType
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrTrainRecordApprovedList",method=RequestMethod.POST)
public RetDataBean getHrTrainRecordApprovedList(HttpServletRequest request,PageParam pageParam,
		String channel,String courseType,String beginTime,String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrTrainRecordService.getHrTrainRecordApprovedList(pageParam, channel, courseType, beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrTrainRecordApprovedOldList   
 * @Description: TODO 获取历史审批记录
 * @param request
 * @param pageParam
 * @param channel
 * @param courseType
 * @param status
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrTrainRecordApprovedOldList",method=RequestMethod.POST)
public RetDataBean getHrTrainRecordApprovedOldList(HttpServletRequest request,PageParam pageParam,
		String channel,String courseType,String status,String beginTime,String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrTrainRecordService.getHrTrainRecordApprovedOldList(pageParam, channel, courseType, status, beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrRecruitNeedsList   
 * @Description: TODO 获取招聘需求列表
 * @param request
 * @param pageParam
 * @param highsetShool
 * @param occupation
 * @param status
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrRecruitNeedsList",method=RequestMethod.POST)
public RetDataBean getHrRecruitNeedsList(HttpServletRequest request,PageParam pageParam,
		String highsetShool,String occupation,String status,String beginTime,String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrRecruitNeedsService.getHrRecruitNeedsList(pageParam, occupation, highsetShool, status, beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getApprovedHrRecruitNeedsList   
 * @Description: TODO 获取待审批需求列表
 * @param request
 * @param pageParam
 * @param highsetShool
 * @param occupation
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getApprovedHrRecruitNeedsList",method=RequestMethod.POST)
public RetDataBean getApprovedHrRecruitNeedsList(HttpServletRequest request,PageParam pageParam,
		String highsetShool,String occupation,String beginTime,String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("N.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrRecruitNeedsService.getApprovedHrRecruitNeedsList(pageParam, occupation, highsetShool, beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getOldApprovedHrRecruitNeedsList   
 * @Description: TODO  获取历史审批记录
 * @param request
 * @param pageParam
 * @param status
 * @param highsetShool
 * @param occupation
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getOldApprovedHrRecruitNeedsList",method=RequestMethod.POST)
public RetDataBean getOldApprovedHrRecruitNeedsList(HttpServletRequest request,PageParam pageParam,String status,
		String highsetShool,String occupation,String beginTime,String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("N.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrRecruitNeedsService.getOldApprovedHrRecruitNeedsList(pageParam,status, occupation, highsetShool, beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrRecruitPlanList   
 * @Description: TODO 获取招聘计划列表
 * @param request
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrRecruitPlanList",method=RequestMethod.POST)
public RetDataBean getHrRecruitPlanList(HttpServletRequest request,PageParam pageParam,String beginTime,String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrRecruitPlanService.getHrRecruitPlanList(pageParam, beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrKpiItemList   
 * @Description: TODO 获取考核指标列表
 * @param request
 * @param pageParam
 * @param createUser
 * @param kpiType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrKpiItemList",method=RequestMethod.POST)
public RetDataBean getHrKpiItemList(HttpServletRequest request,PageParam pageParam,String createUser,String kpiType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrKpiItemService.getHrKpiItemList(pageParam, createUser, kpiType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getHrUserInfoListByDeptId   
 * @Description: TODO 获取部门下的人员列表
 * @param request
 * @param pageParam
 * @param deptId
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrUserInfoListByDeptId",method=RequestMethod.POST)
public RetDataBean getHrUserInfoListByDeptId(HttpServletRequest request,PageParam pageParam,String deptId)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("U.SORT_NO");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setDeptId(deptId);
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrUserInfoService.getHrUserInfoListByDeptId(pageParam);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrEvaluateByUserIdList   
 * @Description: TODO 获取人员评价列表
 * @param request
 * @param pageParam
 * @param userId
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrEvaluateByUserIdList",method=RequestMethod.POST)
public RetDataBean getHrEvaluateByUserIdList(HttpServletRequest request,PageParam pageParam,String userId)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("E.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrEvaluateService.getHrEvaluateByUserIdList(pageParam,userId);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHrEvaluateQueryList   
 * @Description: TODO getHrEvaluateQueryList
 * @param request
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param status
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getHrEvaluateQueryList",method=RequestMethod.POST)
public RetDataBean getHrEvaluateQueryList(HttpServletRequest request,PageParam pageParam,String userId,String beginTime,String endTime,String status)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("E.CREATE_TIME");
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("desc");
		}
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=hrEvaluateService.getHrEvaluateQueryList(pageParam,userId,beginTime,endTime,status);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

}
