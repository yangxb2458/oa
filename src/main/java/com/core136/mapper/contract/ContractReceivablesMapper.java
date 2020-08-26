package com.core136.mapper.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.contract.ContractReceivables;

@Mapper
public interface ContractReceivablesMapper extends MyMapper<ContractReceivables>{
	
	/**
	 * 
	 * @Title: getContractReceivablesList   
	 * @Description: TODO 获取应收款列表
	 * @param: orgId
	 * @param: userPriv
	 * @param: beginTime
	 * @param: endTime
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,Object>>      
	 * @throws
	 */	
	public List<Map<String, Object>>getContractReceivablesList(
			@Param(value="orgId") String orgId,
			@Param(value="userPriv")String userPriv,
			@Param(value="beginTime") String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="status") String status,
			@Param(value="search") String search
			);
	
	public List<Map<String, String>>getDeskReceivablesList(@Param(value="orgId")String orgId,@Param(value="beginTime")String beginTime,
			@Param(value="opFlag")String opFlag,@Param(value="accountId")String accountId
			);
}
