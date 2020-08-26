package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrCareRecord;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrCareRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrCareRecordService {

	@Autowired
	private HrCareRecordMapper hrCareRecordMapper;
	
	public int insertHrCareRecord(HrCareRecord hrCareRecord)
	{
		return hrCareRecordMapper.insert(hrCareRecord);
	}
	
	
	public int deleteHrCareRecord(HrCareRecord hrCareRecord)
	{
		return hrCareRecordMapper.delete(hrCareRecord);
	}
	
	
	public int updateHrCareRecord(Example example,HrCareRecord hrCareRecord)
	{
		return hrCareRecordMapper.updateByExampleSelective(hrCareRecord, example);
	}
	
	public HrCareRecord selectOneHrCareRecord(HrCareRecord hrCareRecord)
	{
		return hrCareRecordMapper.selectOne(hrCareRecord);
	}
	/**
	 * 
	 * @Title: getHrCareRecordList   
	 * @Description: TODO 获取人员关怀列表
	 * @param orgId
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param careType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrCareRecordList(String orgId,String userId,String beginTime,String endTime,String careType,String search)
	{
		return hrCareRecordMapper.getHrCareRecordList(orgId, userId, beginTime, endTime, careType, "%"+search+"%");
	}
	
	/**
	 * 
	 * @Title: getHrCareRecordList   
	 * @Description: TODO 获取人员关怀列表
	 * @param pageParam
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param careType
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getHrCareRecordList(PageParam pageParam,String userId,String beginTime,String endTime,String careType) 
	{
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getHrCareRecordList(pageParam.getOrgId(),userId, beginTime, endTime, careType,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
}
