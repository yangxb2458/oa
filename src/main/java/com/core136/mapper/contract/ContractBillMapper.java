package com.core136.mapper.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.contract.ContractBill;

@Mapper
public interface ContractBillMapper extends MyMapper<ContractBill>{

	/**
	 * 
	 * @Title: getContractBillList   
	 * @Description: TODO 获取发票列表
	 * @param: orgId
	 * @param: opFlag
	 * @param: accountId
	 * @param: isOpen
	 * @param: beginTime
	 * @param: endTime
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getContractBillList(
			@Param(value="orgId")String orgId,
			@Param(value="opFlag")String opFlag,
			@Param(value="accountId")String accountId,
			@Param(value="isOpen")String isOpen,
			@Param(value="status") String status,
			@Param(value="billType")String billType,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="search")String search
			);
	/**
	 * 
	 * @Title: getDeskBillList   
	 * @Description: TODO 获取财务门户发票列表
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getDeskBillList(@Param(value="orgId")String orgId);
	
	/**
	 * 
	 * @Title: getContractBillTop   
	 * @Description: TODO 获取近期发票列表
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getContractBillTop(@Param(value="orgId")String orgId);
}
