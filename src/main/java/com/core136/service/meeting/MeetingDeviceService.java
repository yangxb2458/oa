/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  MeetingDeviceService.java   
 * @Package com.core136.service.meeting   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月28日 下午2:31:31   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.meeting.MeetingDevice;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.meeting.MeetingDeviceMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class MeetingDeviceService {
	@Autowired
	private MeetingDeviceMapper meetingDeviceMapper;
	
	public int insertMeetingDevice(MeetingDevice meetingDevice)
	{
		return meetingDeviceMapper.insert(meetingDevice);
	}
	
	public int deleteMeetingDevice(MeetingDevice meetingDevice)
	{
		return meetingDeviceMapper.delete(meetingDevice);
	}

	public int updateMeetingDevice(Example example,MeetingDevice meetingDevice)
	{
		return meetingDeviceMapper.updateByExampleSelective(meetingDevice, example);
	}
	
	public MeetingDevice selectOneMeetingDevice(MeetingDevice meetingDevice)
	{
		return meetingDeviceMapper.selectOne(meetingDevice);
	}
	
	/**
	 * 
	 * @Title: getMeetingDeviceList   
	 * @Description: TODO 获取会议设备列表
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>> getMeetingDeviceList(String orgId,String search)
	{
		return meetingDeviceMapper.getMeetingDeviceList(orgId,"%"+search+"%");
	}
	
	/**
	 * 
	 * @Title: getMeetingDeviceList   
	 * @Description: TODO 获取会议设备列表
	 * @param: pageParam
	 * @param: @return      
	 * @return: PageInfo<Map<String,String>>      
	 * @throws
	 */
	public PageInfo<Map<String, String>> getMeetingDeviceList(PageParam pageParam) {
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getMeetingDeviceList(pageParam.getOrgId(),pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	
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
	public List<Map<String, String>>getCanUseDeviceList(String orgId,String deptId)
	{
		return meetingDeviceMapper.getCanUseDeviceList(orgId, deptId);
	}
	
	/**
	 * 
	 * @Title: getDeviceListName   
	 * @Description: TODO 获取设备名称列表
	 * @param orgId
	 * @param deviceId
	 * @return
	 * String    
	 * @throws
	 */
	public String getDeviceListName(String orgId,String deviceIds)
	{
		if(StringUtils.isNotBlank(deviceIds))
		{
			String [] deviceIdArr = deviceIds.split(",");
			List<String> list = new ArrayList<String>();
			list = Arrays.asList(deviceIdArr);
			List<Map<String, String>> listMap = meetingDeviceMapper.getDeviceListName(orgId,list);
			List<String> list2 = new ArrayList<String>();
			for(int i=0;i<listMap.size();i++)
			{
				list2.add(listMap.get(i).get("deviceName"));
			}
			return StringUtils.join(list2,",");
		}else
		{
			return "";
		}
	}
}
