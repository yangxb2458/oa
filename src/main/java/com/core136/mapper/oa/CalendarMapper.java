package com.core136.mapper.oa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.oa.Calendar;
import org.core136.common.dbutils.MyMapper;
/**
 * 
 * @ClassName:  CalendarMapper   
 * @Description:TODO 个人日程
 * @author: 稠云信息
 * @date:   2019年2月28日 下午3:05:34   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Mapper
public interface CalendarMapper extends MyMapper<Calendar>{

	/**
	 * 
	 * @Title getMyCalendarList   
	 * @Description TODO 获取人个当前日程
	 * @param orgId
	 * @param accountId
	 * @param nowDate
	 * @return      
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getMyCalendarList(@Param(value="orgId")String orgId,@Param(value="accountId") String accountId,@Param(value="nowDate") String nowDate);
	
	/**
	 * 
	 * @Title: getAllCalendarList   
	 * @Description: TODO 统计个人日程
	 * @param: orgId
	 * @param: accountId
	 * @param: beginTime
	 * @param: endTime
	 * @param: type
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getAllCalendarList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value = "type") String type,@Param(value="search") String search);
	/**
	 * 
	 * @Title: getMyDeskCalendarList   
	 * @Description: TODO 获取个人桌面日程列表
	 * @param: orgId
	 * @param: accountId
	 * @param: beginTime
	 * @param: endTime
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getMyCalendarListForDesk(@Param(value="orgId") String orgId,
			@Param(value="accountId") String accountId,@Param(value="beginTime")String beginTime,@Param(value="endTime") String endTime
			);
	/**
	 * 
	 * @Title: getTimgingCalendarList   
	 * @Description: TODO 有效的日程
	 * @param orgId
	 * @param nowTime
	 * @return
	 * List<Calendar>    
	 * @throws
	 */
	public List<Calendar>getTimgingCalendarList(@Param(value="orgId")String orgId,@Param(value="nowTime")String nowTime);
}
