package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrContract;
@Mapper
public interface HrContractMapper extends MyMapper<HrContract>{

	/**
	 * 
	 * @Title: getHrContractList   
	 * @Description: TODO 获取合同列表
	 * @param orgId
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param enterpries
	 * @param contractType
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrContractList(@Param(value="orgId")String orgId,
			@Param(value="userId")String userId,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="enterpries")String enterpries,
			@Param(value="contractType")String contractType
			);
	
/**
 * 
 * @Title: getDeskHrContractList   
 * @Description: TODO 获取快到期的合同列表
 * @param orgId
 * @param beginTime
 * @param endTime
 * @return
 * List<Map<String,String>>    
 * @throws
 */
	public List<Map<String, String>>getDeskHrContractList(@Param(value="orgId")String orgId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime);

	/**
	 * 
	 * @Title: getMyHrContractList   
	 * @Description: TODO 查询自己的合同列表
	 * @param orgId
	 * @param accountId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyHrContractList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId);
}
