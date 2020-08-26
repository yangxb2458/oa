/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  MeetingRoomService.java   
 * @Package com.core136.service.meeting   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月17日 上午10:50:59   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.meeting;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.meeting.MeetingRoom;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.meeting.MeetingRoomMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class MeetingRoomService {
	@Autowired
	private MeetingRoomMapper meetingRoomMapper;
	
	public int insertMeetingRoom(MeetingRoom meetingRoom)
	{
		return meetingRoomMapper.insert(meetingRoom);
	}
	
	public int deleteMeetingRoom(MeetingRoom meetingRoom)
	{
		return meetingRoomMapper.delete(meetingRoom);
	}
	
	public int updateMeetingRoom(Example example,MeetingRoom meetingRoom)
	{
		return meetingRoomMapper.updateByExampleSelective(meetingRoom, example);
	}
	
	public MeetingRoom selectOneMeetingRoom(MeetingRoom meetingRoom)
	{
		return meetingRoomMapper.selectOne(meetingRoom);
	}
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
	public List<Map<String, String>> getMeetingRoomList(String orgId,String search)
	{
		return meetingRoomMapper.getMeetingRoomList(orgId, "%"+search+"%");
	}
	/**
	 * 
	 * @Title: getMeetingRoomList   
	 * @Description: TODO 获取会议室列表
	 * @param: pageParam
	 * @param: @return      
	 * @return: PageInfo<Map<String,String>>      
	 * @throws
	 */
	public PageInfo<Map<String, String>> getMeetingRoomList(PageParam pageParam) {
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getMeetingRoomList(pageParam.getOrgId(),pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	
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
	public List<Map<String, String>> getCanUseMeetingRoomList(String orgId,String deptId,String search)
	{
		return meetingRoomMapper.getCanUseMeetingRoomList(orgId, deptId,"%"+search+"%");
	}
}
