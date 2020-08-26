/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  AttendMapper.java   
 * @Package com.core136.mapper.attend   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月15日 上午8:36:19   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.attend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.attend.Attend;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface AttendMapper extends MyMapper<Attend>{

	/**
	 * 
	 * @Title: getAttendYearList   
	 * @Description: TODO 获取年份列表
	 * @param: orgId
	 * @param: accountId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getAttendYearList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId);
	
	
	/**
	 * 
	 * @Title: getMonthList   
	 * @Description: TODO 获取月份
	 * @param: orgId
	 * @param: year
	 * @param: accountId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getMonthList(@Param(value="orgId") String orgId,@Param(value="year") String year,@Param(value="accountId") String accountId);
	
	/**
	 * 
	 * @Title: getMyAttendList   
	 * @Description: TODO 获取个人的考勤记录
	 * @param: orgId
	 * @param: year
	 * @param: month
	 * @param: accountId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getMyAttendList(@Param(value="orgId") String orgId,@Param(value="year") String year,@Param(value="month") String month, @Param(value="accountId") String accountId,@Param(value="type") String type);

	/**
	 * 
	 * @Title: getTotalAttendList   
	 * @Description: TODO 考勤统计查询
	 * @param: orgId
	 * @param: type
	 * @param: beginTimeString
	 * @param: endTime
	 * @param: deptId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getTotalAttendList(@Param(value = "orgId") String orgId,@Param(value="type") String type,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="deptId") String deptId,@Param(value="createUser") String createUser);
	

	/**
	 * 
	 * @Title: getMyLeaveList   
	 * @Description: TODO 获取人员请假列表
	 * @param orgId
	 * @param accountId
	 * @param beginTime
	 * @param endTime
	 * @param type
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyLeaveList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,@Param(value="type")String type);
	
	/**
	 * 
	 * @Title: getMyTravelList   
	 * @Description: TODO 获取出差列表
	 * @param orgId
	 * @param accountId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyTravelList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime);

	/**
	 * 
	 * @Title: getMyOverTimeList   
	 * @Description: TODO 获取加班列表
	 * @param orgId
	 * @param accountId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyOverTimeList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime);

	/**
	 * 
	 * @Title: getMyDutyList   
	 * @Description: TODO 获取值班列表
	 * @param orgId
	 * @param accountId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyDutyList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime);

	/**
	 * 
	 * @Title: getMyOutattendList   
	 * @Description: TODO 获取外出列表
	 * @param orgId
	 * @param accountId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyOutattendList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime);

	
}
