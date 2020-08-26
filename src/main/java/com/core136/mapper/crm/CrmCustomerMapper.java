package com.core136.mapper.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.crm.CrmCustomer;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface CrmCustomerMapper extends MyMapper<CrmCustomer>{

	/**
	 * 
	 * @Title getAllCrmCustomerList   
	 * @Description TODO获取权限内所有客户
	 * @param orgId
	 * @param source
	 * @param model
	 * @param roles
	 * @param industry
	 * @param search
	 * @return      
	 * List<Map<String,Object>>
	 */
public List<Map<String,Object>> getAllCrmCustomerList(@Param(value="orgId")String orgId,@Param(value="source")String source,
		@Param(value="model") String model,@Param(value="roles") String roles,@Param(value="industry") String industry,@Param(value="keepUser") String keepUser,@Param(value="search") String search,
		@Param(value="country") String country,@Param(value="province") String province,@Param(value="city") String city,@Param(value="level") String level,@Param(value="intention") String intention,@Param(value="opponent") String opponent,@Param(value="list")List<String> list);

/**
 * 
 * @Title getSelect2CustomerList   
 * @Description TODO 获取SELECT2客户列表
 * @param orgId
 * @param keepUser
 * @param search
 * @return      
 * List<Map<String,Object>>
 */
public List<Map<String,Object>> getSelect2CustomerList(@Param(value="orgId")String orgId,@Param(value="keepUser")String keepUser,@Param(value="search")String search);
}
