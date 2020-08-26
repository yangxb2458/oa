package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrPersonnelTransfer;

@Mapper
public interface HrPersonnelTransferMapper extends MyMapper<HrPersonnelTransfer>{
	/**
	 * 
	 * @Title: getHrPersonnelTransferList   
	 * @Description: TODO 获取人员调动列表
	 * @param orgId
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param transferType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
public List<Map<String, String>>getHrPersonnelTransferList(@Param(value="orgId")String orgId,@Param(value="userId")String userId,
		@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,@Param(value="transferType")String transferType,
		@Param(value="search")String search);

/**
 * 
 * @Title: getMyHrPersonnelTransferList   
 * @Description: TODO 人个工作调动记录
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyHrPersonnelTransferList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId);

}
