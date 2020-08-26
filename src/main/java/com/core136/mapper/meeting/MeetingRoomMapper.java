/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  MeetingRoomMapper.java   
 * @Package com.core136.mapper.meeting   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月17日 上午10:45:56   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.meeting;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.meeting.MeetingRoom;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface MeetingRoomMapper extends MyMapper<MeetingRoom>{

	/**
	 * 
	 * @Title: getMeetingRoomList   
	 * @Description: TODO 获取会议室列表
	 * @param: orgId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getMeetingRoomList(@Param(value="orgId") String orgId,@Param(value="search") String search);
	
	/**
	 * 
	 * @Title: getCanUseMeetingRoomList   
	 * @Description: TODO 获取当前用户可用的会议室
	 * @param: orgId
	 * @param: deptId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getCanUseMeetingRoomList(@Param(value="orgId") String orgId,@Param(value="deptId") String deptId,@Param(value="search")String search);
}
