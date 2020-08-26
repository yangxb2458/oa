/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrTrainRecordService.java   
 * @Package com.core136.service.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月28日 上午9:05:35   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrTrainRecord;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrTrainRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 人员培训记录
 * @author lsq
 *
 */
@Service
public class HrTrainRecordService {
@Autowired
private HrTrainRecordMapper hrTrainRecordMapper;

public int insertHrTrainRecord(HrTrainRecord hrTrainRecord)
{
	return hrTrainRecordMapper.insert(hrTrainRecord);
}

public int deleteHrTrainRecord(HrTrainRecord hrTrainRecord)
{
	return hrTrainRecordMapper.delete(hrTrainRecord);
}

public int updateHrTrainRecord(Example example,HrTrainRecord hrTrainRecord)
{
	return hrTrainRecordMapper.updateByExampleSelective(hrTrainRecord, example);
}

public HrTrainRecord selectOneHrTrainRecord(HrTrainRecord hrTrainRecord)
{
	return hrTrainRecordMapper.selectOne(hrTrainRecord);
}

/**
 * 
 * @Title: getHrTrainRecordList   
 * @Description: TODO 获取培训列表
 * @param orgId
 * @param createUser
 * @param channel
 * @param courseType
 * @param status
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrTrainRecordList(String orgId,String createUser,String channel,String courseType,String status,String beginTime,String endTime,String search)
{
	return hrTrainRecordMapper.getHrTrainRecordList(orgId, createUser, channel, courseType, status, beginTime, endTime, "%"+search+"%");
}
/**
 * 
 * @Title: getHrTrainRecordList   
 * @Description: TODO 获取培训列表
 * @param pageParam
 * @param channel
 * @param courseType
 * @param status
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrTrainRecordList(PageParam pageParam,String channel,String courseType,String status,String beginTime,String endTime) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrTrainRecordList(pageParam.getOrgId(),pageParam.getAccountId(),channel, courseType,status,beginTime, endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getHrTrainRecordApprovedList   
 * @Description: TODO 获取待审批记录
 * @param orgId
 * @param accountId
 * @param channel
 * @param courseType
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrTrainRecordApprovedList(String orgId,String accountId,String channel,String courseType,String beginTime,String endTime,String search)
{
	return hrTrainRecordMapper.getHrTrainRecordApprovedList(orgId, accountId, channel, courseType, beginTime, endTime, search);
}
/**
 * 
 * @Title: getHrTrainRecordApprovedList   
 * @Description: TODO 获取待审批记录
 * @param pageParam
 * @param channel
 * @param courseType
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrTrainRecordApprovedList(PageParam pageParam,String channel,String courseType,String beginTime,String endTime) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrTrainRecordApprovedList(pageParam.getOrgId(),pageParam.getAccountId(),channel, courseType,beginTime, endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getHrTrainRecordApprovedOldList   
 * @Description: TODO 获取历史审批记录
 * @param orgId
 * @param accountId
 * @param channel
 * @param courseType
 * @param status
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrTrainRecordApprovedOldList(String orgId,String accountId,String channel,String courseType,String status,String beginTime,String endTime,String search)
{
	return hrTrainRecordMapper.getHrTrainRecordApprovedOldList(orgId, accountId, channel, courseType, status, beginTime, endTime, "%"+search+"%");
}
/**
 * 
 * @Title: getHrTrainRecordApprovedOldList   
 * @Description: TODO 获取历史审批记录
 * @param pageParam
 * @param channel
 * @param courseType
 * @param status
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrTrainRecordApprovedOldList(PageParam pageParam,String channel,String courseType,String status,String beginTime,String endTime) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrTrainRecordApprovedOldList(pageParam.getOrgId(),pageParam.getAccountId(),channel, courseType,status,beginTime, endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
