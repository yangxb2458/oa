package com.core136.mapper.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.contract.ContractSendgoods;

@Mapper
public interface ContractSendgoodsMapper extends MyMapper<ContractSendgoods>{

	/**
	 * 
	 * @Title: getContractSendgoodsList   
	 * @Description: TODO 获取发货列表
	 * @param orgId
	 * @param contractType
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getContractSendgoodsList(
			@Param(value="orgId")String orgId,@Param(value="contractType")String contractType,
			@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,
			@Param(value="search")String search
			);
}
