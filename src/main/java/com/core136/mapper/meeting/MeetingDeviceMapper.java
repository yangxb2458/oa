/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  MeetingDeviceMapper.java   
 * @Package com.core136.mapper.meeting   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月28日 下午2:30:12   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.meeting;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.meeting.MeetingDevice;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface MeetingDeviceMapper extends MyMapper<MeetingDevice>{

	/**
	 * 
	 * @Title: getMeetingDeviceList   
	 * @Description: TODO 获取会议设备列表
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getMeetingDeviceList(@Param(value="orgId")String orgId,@Param(value="search") String search);
	
	/**
	 * 
	 * @Title: getCanUseDeviceList   
	 * @Description: TODO 获取权限内可用的会议室设备
	 * @param: orgId
	 * @param: deptId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getCanUseDeviceList(@Param(value="orgId")String orgId,@Param(value="deptId") String deptId);
	/**
	 * 
	 * @Title: getDeviceListName   
	 * @Description: TODO 获取设备名称列表
	 * @param orgId
	 * @param list
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getDeviceListName(@Param(value="orgId") String orgId,@Param(value="list") List<String> list);
}
