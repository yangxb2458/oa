package com.core136.mapper.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.contract.ContractPayable;

@Mapper
public interface ContractPayableMapper extends MyMapper<ContractPayable>{
/**
 * 
 * @Title: getContractPayableList   
 * @Description: TODO 获取应付款列表
 * @param: orgId
 * @param: beginTime
 * @param: endTime
 * @param: status
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String,String>>getContractPayableList(
			@Param(value="orgId") String orgId,
			@Param(value="opFlag")String opFlag,
			@Param(value="accountId")String accountId,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="status") String status,
			@Param(value="search") String search
			);
	
	public List<Map<String, String>>getDeskPayableList(@Param(value="orgId")String orgId,@Param(value="beginTime")String beginTime,
			@Param(value="opFlag")String opFlag,@Param(value="accountId")String accountId
			);
}
