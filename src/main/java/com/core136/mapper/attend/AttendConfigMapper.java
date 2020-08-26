/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  AttendConfig.java   
 * @Package com.core136.mapper.attend   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月14日 下午3:50:52   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.attend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.attend.AttendConfig;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface AttendConfigMapper extends MyMapper<AttendConfig>{
/**
 * 
 * @Title: getAllAttendConfigList   
 * @Description: TODO 获取考勤列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getAllAttendConfigList(@Param(value = "orgId") String orgId);	

/**
 * 
 * @Title: getMyAttendConfigList   
 * @Description: TODO 获取考勤配置列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getMyAttendConfigList(@Param(value = "orgId") String orgId);	

}
