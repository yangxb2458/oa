package com.core136.mapper.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.contract.ContractPayableRecord;

@Mapper
public interface ContractPayableRecordMapper extends MyMapper<ContractPayableRecord>{

	public List<Map<String, String>>getContractPayableRecordList(@Param(value="orgId")String orgId,@Param(value="payableId")String payableId);
	
	public List<Map<String, String>>getPayableRecordTop(@Param(value="orgId")String orgId);
}
