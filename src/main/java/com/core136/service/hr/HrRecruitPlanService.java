package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrRecruitPlan;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrRecruitPlanMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrRecruitPlanService {
@Autowired
private HrRecruitPlanMapper hrRecruitPlanMapper;

public int insertHrRecruitPlan(HrRecruitPlan hrRecruitPlan)
{
	return hrRecruitPlanMapper.insert(hrRecruitPlan);
}

public int deleteHrRecruitPlan(HrRecruitPlan hrRecruitPlan)
{
	return hrRecruitPlanMapper.delete(hrRecruitPlan);
}

public int updateHrRecruitPlan(Example example, HrRecruitPlan hrRecruitPlan)
{
	return hrRecruitPlanMapper.updateByExampleSelective(hrRecruitPlan, example);
}

public HrRecruitPlan selectOneHrRecruitPlan(HrRecruitPlan hrRecruitPlan)
{
	return hrRecruitPlanMapper.selectOne(hrRecruitPlan);
}

/**
 * 
 * @Title: getHrRecruitPlanList   
 * @Description: TODO 获取招聘计划列表
 * @param orgId
 * @param opFlag
 * @param accountId
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrRecruitPlanList(String orgId,String opFlag,String accountId,String beginTime,String endTime,String search)
{
	return hrRecruitPlanMapper.getHrRecruitPlanList(orgId, opFlag, accountId, beginTime, endTime, "%"+search+"%");
}
/**
 * 
 * @Title: getHrRecruitPlanList   
 * @Description: TODO 获取招聘计划列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrRecruitPlanList(PageParam pageParam,String beginTime,String endTime) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrRecruitPlanList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),beginTime, endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getHrRecruitPlanForSelect   
 * @Description: TODO 获取当前可填报的招聘计划
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrRecruitPlanForSelect(String orgId)
{
	String endTime = SysTools.getTime("yyyy-MM-dd");
	return hrRecruitPlanMapper.getHrRecruitPlanForSelect(orgId, endTime);
}
}
