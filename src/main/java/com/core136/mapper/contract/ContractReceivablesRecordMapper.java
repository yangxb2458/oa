package com.core136.mapper.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.contract.ContractReceivablesRecord;

@Mapper
public interface ContractReceivablesRecordMapper extends MyMapper<ContractReceivablesRecord>{

	/**
	 * 
	 * @Title: getContractReceivablesRecordList   
	 * @Description: TODO 获取收款记录
	 * @param orgId
	 * @param receivablesId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getContractReceivablesRecordList(@Param(value="orgId")String orgId,@Param(value="receivablesId")String receivablesId);
	
	/**
	 * 
	 * @Title: getReceivRecordTop   
	 * @Description: TODO 收款记录
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getReceivRecordTop(@Param(value="orgId")String orgId);
}
