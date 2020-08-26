package com.core136.mapper.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.crm.CrmLinkMan;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface CrmLinkManMapper extends MyMapper<CrmLinkMan>{

	/**
	 * 
	 * @Title getCrmLinkManList   
	 * @Description TODO 获取客户联系人列表
	 * @param orgId
	 * @param customerId
	 * @return      
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getCrmLinkManList(@Param (value="orgId") String orgId,@Param (value="customerId") String customerId);
	
	
	/**
	 * 
	 * @Title getCrmLinkManAllList   
	 * @Description TODO 获取CRM联系人列表
	 * @param orgId
	 * @param search
	 * @return      
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getCrmLinkManAllList(@Param (value="orgId") String orgId,@Param (value="search") String search);
	
	/**
	 * 
	* @Title: getMyCrmLinkManAllList 
	* @Description: TODO 业务员客户联系人
	* @param @param orgId
	* @param @param keepUser
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型
	 */
	public List<Map<String,Object>> getMyCrmLinkManAllList(@Param (value="orgId") String orgId,@Param (value="keepUser") String keepUser,@Param (value="search") String search);
	
	/**
	 * 
	 * @Title getCrmLinkManInfo   
	 * @Description TODO 联系人基本信息
	 * @param orgId
	 * @param customerId
	 * @param linkManId
	 * @return      
	 * List<Map<String,Object>>
	 */
	public Map<String,Object> getCrmLinkManInfo(@Param (value="orgId") String orgId,@Param (value="linkManId") String linkManId);
	
	
	
}
