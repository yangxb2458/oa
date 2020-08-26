/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrWorkTypeMapper.java   
 * @Package com.core136.mapper.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2020年1月6日 下午3:46:37   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrWagesLevel;

/**
 * @author lsq
 *
 */
@Mapper
public interface HrWagesLevelMapper extends MyMapper<HrWagesLevel>{
/**
 * 
 * @Title: getWorkTypeList   
 * @Description: TODO 获取工资级别列表
 * @param: orgId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String, String>>getWagesLevelList(@Param(value="orgId") String orgId,@Param(value="search") String search);
	
	public List<Map<String, String>>getWagesLevelListForSelect(@Param(value="orgId")String orgId);
}
