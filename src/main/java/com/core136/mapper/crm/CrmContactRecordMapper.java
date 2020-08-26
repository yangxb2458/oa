package com.core136.mapper.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.crm.CrmContactRecord;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface CrmContactRecordMapper extends MyMapper<CrmContactRecord>{
/**
 * 
 * @Title getRecordListByCustomerId   
 * @Description TODO 获取客户所有联系记录
 * @param orgId
 * @param customerId
 * @return      
 * List<Map<String,Object>>
 */
	public List<Map<String,Object>> getRecordListByCustomerId(@Param(value="orgId") String orgId,@Param(value="customerId") String customerId);
}
