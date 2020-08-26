/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrUserLeaveMapper.java   
 * @Package com.core136.mapper.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月30日 上午10:41:09   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.hr.HrUserLevel;

import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface HrUserLevelMapper extends MyMapper<HrUserLevel>{
	/**
	 * 
	 * @Title: getHrUserLevelChart   
	 * @Description: TODO 获取行政级别CHART数据
	 * @param: orgId
	 * @param: leaveId
	 * @param: @return      
	 * @return: List<Map<String,Object>>      
	 * @throws
	 */
	public List<Map<String,Object>> getHrUserLevelChart(@Param(value="orgId") String orgId,@Param(value="leaveId") String leaveId);

	/**
	 * 
	 * @Title: getHrUserLevelByStr   
	 * @Description: TODO 获取HR的行政级别名称
	 * @param orgId
	 * @param list
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrUserLevelByStr(@Param(value="orgId")String orgId,@Param(value="list")List<String>list);
}
