package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrSalaryRecord;

@Mapper
public interface HrSalaryRecordMapper extends MyMapper<HrSalaryRecord>{

	/**
	 * 
	 * @Title: getHrSalaryRecordList   
	 * @Description: TODO 获取人员薪资列表
	 * @param orgId
	 * @param userId
	 * @param year
	 * @param month
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrSalaryRecordList(
			@Param(value="orgId")String orgId,
			@Param(value="userId")String userId,
			@Param(value="year")String year,
			@Param(value="month")String month
			);
	/**
	 * 
	 * @Title: getMyHrSalaryRecordList   
	 * @Description: TODO 个人薪资查询
	 * @param orgId
	 * @param accountId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyHrSalaryRecordList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId);
}
