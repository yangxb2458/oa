package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrLeaveRecord;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrLeaveRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrLeaveRecordService {
@Autowired
private HrLeaveRecordMapper hrLeaveRecordMapper;

public int insertHrLeaveRecord(HrLeaveRecord hrLeaveRecord)
{
	return hrLeaveRecordMapper.insert(hrLeaveRecord);
}

public int deleteHrLeaveRecord(HrLeaveRecord hrLeaveRecord)
{
	return hrLeaveRecordMapper.delete(hrLeaveRecord);
}

public int updateHrLeaveRecord(Example example,HrLeaveRecord hrLeaveRecord)
{
	return hrLeaveRecordMapper.updateByExample(hrLeaveRecord, example);
}

public HrLeaveRecord selectOneHrLeaveRecord(HrLeaveRecord hrLeaveRecord)
{
	return hrLeaveRecordMapper.selectOne(hrLeaveRecord);
}
/**
 * 
 * @Title: getHrLeaveRecordList   
 * @Description: TODO 获取离职人员列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param leaveType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrLeaveRecordList(String orgId,String userId,String beginTime,String endTime,String leaveType)
{
	return hrLeaveRecordMapper.getHrLeaveRecordList(orgId, userId, beginTime, endTime, leaveType);
}
/**
 * 
 * @Title: getHrLeaveRecordList   
 * @Description: TODO 获取离职人员列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param leaveType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrLeaveRecordList(PageParam pageParam,String userId,String beginTime,String endTime,String leaveType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrLeaveRecordList(pageParam.getOrgId(),userId, beginTime, endTime,leaveType);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
