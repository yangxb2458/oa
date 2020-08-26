package com.core136.service.workplan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.core136.common.enums.GobalConstant;
import org.eclipse.jdt.internal.compiler.lookup.ReductionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.sys.MsgBody;
import com.core136.bean.sys.PageParam;
import com.core136.bean.workplan.WorkPlan;
import com.core136.mapper.workplan.WorkPlanMapper;
import com.core136.service.account.AccountService;
import com.core136.service.account.UserInfoService;
import com.core136.service.sys.MessageUnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class WorkPlanService {
@Autowired
private WorkPlanMapper workPlanMapper;
@Autowired
private MessageUnitService messageUnitService;
@Autowired
private AccountService accountService;

public int insertWorkPlan(WorkPlan workPlan)
{
	return workPlanMapper.insert(workPlan);
}

public int deleteWorkPlan(WorkPlan workPlan)
{
	return workPlanMapper.delete(workPlan);
}
public int updateWorkPlan(Example example,WorkPlan workPlan)
{
	return workPlanMapper.updateByExampleSelective(workPlan, example);
}
public WorkPlan selectOneWorkPlan(WorkPlan workPlan)
{
	return workPlanMapper.selectOne(workPlan);
}
/**
 * 
 * @Title: createWorkPlan   
 * @Description: TODO 创建工作计划
 * @param account
 * @param userInfo
 * @param workPlan
 * @return
 * int    
 * @throws
 */
public int createWorkPlan(Account account,UserInfo userInfo,WorkPlan workPlan)
{
	if(StringUtils.isNotBlank(workPlan.getMsgType()))
	{
	String holdUser = workPlan.getHoldUser();
	String joinUser = workPlan.getJoinUser();
	String supUser = workPlan.getSupUser();
	List<String> userList = new ArrayList<String>();
	List<String> arr2 = new ArrayList<String>();
	List<String> arr3 = new ArrayList<String>();
	if(StringUtils.isNotBlank(holdUser))
	{
		userList = new ArrayList<String>(Arrays.asList(holdUser.split(",")));
	}
	if(StringUtils.isNotBlank(joinUser))
	{
		arr2 = new ArrayList<String>(Arrays.asList(joinUser.split(",")));
	}
	if(StringUtils.isNotBlank(supUser))
	{
		arr3 = new ArrayList<String>(Arrays.asList(supUser.split(",")));
	}
	userList.addAll(arr2);
	userList.addAll(arr3);
	Set<String> set = new HashSet<String>();
	set.addAll(userList);     // 将list所有元素添加到set中    set集合特性会自动去重复
	userList.clear();
	userList.addAll(set);
	List<MsgBody> msgBodyList = new ArrayList<MsgBody>();
	for(int i=0;i<userList.size();i++)
	{
		Account account2 = new Account();
		account2.setAccountId(userList.get(i).toString());
		account2.setOrgId(account.getOrgId());
		account2 = accountService.selectOneAccount(account2);
		MsgBody msgBody = new MsgBody();
		msgBody.setTitle("工作计划提醒");
		msgBody.setContent("工作计划标题为："+workPlan.getTitle()+"的查看提醒！");
		msgBody.setSendTime(workPlan.getCreateTime());
		msgBody.setAccount(account2);
		msgBody.setFromAccountId(account.getAccountId());
		msgBody.setFormUserName(userInfo.getUserName());
		msgBody.setRedirectUrl("/app/core/workplan/workplandetails?planId="+workPlan.getPlanId());
		msgBody.setOrgId(account.getOrgId());
		msgBodyList.add(msgBody);
	}
	String smsStatus = workPlan.getMsgType();
	messageUnitService.sendMessage(smsStatus, GobalConstant.MSG_TYPE_WORK_PLAN, msgBodyList);
	}
	return insertWorkPlan(workPlan);
}

/**
 * 
 * @Title: getManageWorkPlanList   
 * @Description: TODO 获取工作列表
 * @param orgId
 * @param opFlag
 * @param createUser
 * @param beginTime
 * @param endTime
 * @param status
 * @param planType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getManageWorkPlanList(String orgId,String opFlag,String createUser,String beginTime,String endTime,String status,String planType,String search)
{
	return workPlanMapper.getManageWorkPlanList(orgId, createUser, opFlag, beginTime, endTime, status, planType, "%"+search+"%");
}

/**
 * 
 * @Title: getManageWorkPlanList   
 * @Description: TODO 获取工作列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param status
 * @param planType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getManageWorkPlanList(PageParam pageParam,String beginTime,String endTime,String status,String planType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getManageWorkPlanList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),beginTime,endTime,status,planType,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getTodayWorkPlanList   
 * @Description: TODO 获取今天的工作计划
 * @param orgId
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getTodayWorkPlanList(String orgId,String search)
{
	return workPlanMapper.getTodayWorkPlanList(orgId, "%"+search+"%");
}

/**
 * 
 * @Title: getTodayWorkPlanList   
 * @Description: TODO获取今天的工作计划
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getTodayWorkPlanList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getTodayWorkPlanList(pageParam.getOrgId(),pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMonthWorkPlanList   
 * @Description: TODO 查询本月工作计划
 * @param orgId
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMonthWorkPlanList(String orgId,String search)
{
	return workPlanMapper.getMonthWorkPlanList(orgId, "%"+search+"%");
}

/**
 * 
 * @Title: getMonthWorkPlanList   
 * @Description: TODO获取今天的工作计划
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMonthWorkPlanList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMonthWorkPlanList(pageParam.getOrgId(),pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getHoldWorkPlanList   
 * @Description: TODO 我负责的计划列表
 * @param orgId
 * @param opFlag
 * @param createUser
 * @param beginTime
 * @param endTime
 * @param status
 * @param planType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHoldWorkPlanList(String orgId,String opFlag,String accountId,String beginTime,String endTime,String status,String planType,String search)
{
	return workPlanMapper.getHoldWorkPlanList(orgId, accountId, opFlag, beginTime, endTime, status, planType, "%"+search+"%");
}
/**
 * 
 * @Title: getHoldWorkPlanList   
 * @Description: TODO 我负责的计划列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param status
 * @param planType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHoldWorkPlanList(PageParam pageParam,String beginTime,String endTime,String status,String planType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHoldWorkPlanList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),beginTime,endTime,status,planType,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getSupWorkPlanList   
 * @Description: TODO 我督查的计划列表
 * @param orgId
 * @param opFlag
 * @param createUser
 * @param beginTime
 * @param endTime
 * @param status
 * @param planType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getSupWorkPlanList(String orgId,String opFlag,String accountId,String beginTime,String endTime,String status,String planType,String search)
{
	return workPlanMapper.getSupWorkPlanList(orgId, accountId, opFlag, beginTime, endTime, status, planType, "%"+search+"%");
}

/**
 * 
 * @Title: getSupWorkPlanList   
 * @Description: TODO 我督查的计划列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param status
 * @param planType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getSupWorkPlanList(PageParam pageParam,String beginTime,String endTime,String status,String planType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getSupWorkPlanList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),beginTime,endTime,status,planType,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getMyWorkPlanList   
 * @Description: TODO 我参与的工作计划
 * @param orgId
 * @param opFlag
 * @param createUser
 * @param beginTime
 * @param endTime
 * @param status
 * @param planType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyWorkPlanList(String orgId,String opFlag,String accountId,String beginTime,String endTime,String status,String planType,String search)
{
	return workPlanMapper.getMyWorkPlanList(orgId, accountId, opFlag, beginTime, endTime, status, planType, "%"+search+"%");
}
/**
 * 
 * @Title: getMyWorkPlanList   
 * @Description: TODO 我参与的工作计划
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param status
 * @param planType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyWorkPlanList(PageParam pageParam,String beginTime,String endTime,String status,String planType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyWorkPlanList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),beginTime,endTime,status,planType,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getShareWorkPlanList   
 * @Description: TODO 获取分享的工作计划
 * @param orgId
 * @param opFlag
 * @param accountId
 * @param deptId
 * @param levelId
 * @param beginTime
 * @param endTime
 * @param status
 * @param planType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getShareWorkPlanList(String orgId,String opFlag,String accountId,String deptId,String levelId,String beginTime,String endTime,String status,String planType,String search)
{
	return workPlanMapper.getShareWorkPlanList(orgId, accountId,deptId,levelId ,opFlag, beginTime, endTime, status, planType, "%"+search+"%");
}
/**
 * 
 * @Title: getShareWorkPlanList   
 * @Description: TODO 获取分享的工作计划
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param status
 * @param planType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getShareWorkPlanList(PageParam pageParam,String beginTime,String endTime,String status,String planType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getShareWorkPlanList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),pageParam.getDeptId(),pageParam.getLevelId(),beginTime,endTime,status,planType,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
