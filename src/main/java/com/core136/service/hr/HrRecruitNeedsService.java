package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrRecruitNeeds;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrRecruitNeedsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrRecruitNeedsService {

	@Autowired
	private HrRecruitNeedsMapper hrRecruitNeedsMapper;
	
	public int insertHrRecruitNeeds(HrRecruitNeeds hrRecruitNeeds)
	{
		return  hrRecruitNeedsMapper.insert(hrRecruitNeeds);
	}
	
	public int deleteHrRecruitNeeds(HrRecruitNeeds hrRecruitNeeds)
	{
		return hrRecruitNeedsMapper.delete(hrRecruitNeeds);
	}
	
	public int updateHrRecruitNeeds(Example example,HrRecruitNeeds hrRecruitNeeds)
	{
		return hrRecruitNeedsMapper.updateByExampleSelective(hrRecruitNeeds, example);
	}
	
	public HrRecruitNeeds selectOneHrRecruitNeeds(HrRecruitNeeds hrRecruitNeeds)
	{
		return hrRecruitNeedsMapper.selectOne(hrRecruitNeeds);
	}
	/**
	 * 
	 * @Title: getHrRecruitNeedsList   
	 * @Description: TODO  获取招聘需求列表
	 * @param orgId
	 * @param occupation
	 * @param highsetShool
	 * @param status
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrRecruitNeedsList(String orgId,String accountId,String occupation,String highsetShool,String status,String beginTime,String endTime,String search)
	{
		return hrRecruitNeedsMapper.getHrRecruitNeedsList(orgId,accountId, occupation, highsetShool, status, beginTime, endTime, "%"+search+"%");
	}
	/**
	 * 
	 * @Title: getHrRecruitNeedsList   
	 * @Description: TODO 获取需求列表
	 * @param pageParam
	 * @param occupation
	 * @param highsetShool
	 * @param status
	 * @param beginTime
	 * @param endTime
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getHrRecruitNeedsList(PageParam pageParam,String occupation,String highsetShool,String status,String beginTime,String endTime) 
	{
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getHrRecruitNeedsList(pageParam.getOrgId(),pageParam.getAccountId(),occupation, highsetShool,status,beginTime, endTime,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	
	/**
	 * 
	 * @Title: getApprovedHrRecruitNeedsList   
	 * @Description: TODO 获取待审批需求列表
	 * @param orgId
	 * @param accountId
	 * @param occupation
	 * @param highsetShool
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getApprovedHrRecruitNeedsList(String orgId,String accountId,String occupation,String highsetShool,String beginTime,String endTime,String search)
			{
				return hrRecruitNeedsMapper.getApprovedHrRecruitNeedsList(orgId, accountId, occupation, highsetShool, beginTime, endTime, "%"+search+"%");
			}
	/**
	 * 
	 * @Title: getApprovedHrRecruitNeedsList   
	 * @Description: TODO 获取待审批需求列表
	 * @param pageParam
	 * @param occupation
	 * @param highsetShool
	 * @param beginTime
	 * @param endTime
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getApprovedHrRecruitNeedsList(PageParam pageParam,String occupation,String highsetShool,String beginTime,String endTime) 
	{
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getApprovedHrRecruitNeedsList(pageParam.getOrgId(),pageParam.getAccountId(),occupation, highsetShool,beginTime, endTime,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	/**
	 * 
	 * @Title: getOldApprovedHrRecruitNeedsList   
	 * @Description: TODO 获取历史审批记录
	 * @param orgId
	 * @param accountId
	 * @param status
	 * @param occupation
	 * @param highsetShool
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getOldApprovedHrRecruitNeedsList(String orgId,String accountId,String status,String occupation,String highsetShool,String beginTime,String endTime,String search)
	{
		return hrRecruitNeedsMapper.getOldApprovedHrRecruitNeedsList(orgId, accountId,status, occupation, highsetShool, beginTime, endTime, "%"+search+"%");
	}
	
	/**
	 * 
	 * @Title: getOldApprovedHrRecruitNeedsList   
	 * @Description: TODO 获取历史审批记录
	 * @param pageParam
	 * @param status
	 * @param occupation
	 * @param highsetShool
	 * @param beginTime
	 * @param endTime
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getOldApprovedHrRecruitNeedsList(PageParam pageParam,String status,String occupation,String highsetShool,String beginTime,String endTime) 
	{
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getOldApprovedHrRecruitNeedsList(pageParam.getOrgId(),pageParam.getAccountId(),status,occupation, highsetShool,beginTime, endTime,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	
}
