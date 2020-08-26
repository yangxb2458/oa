/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetMeetingController.java   
 * @Package com.core136.controller.meeting   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月22日 上午10:11:35   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.meeting;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.meeting.Meeting;
import com.core136.bean.meeting.MeetingDevice;
import com.core136.bean.meeting.MeetingNotes;
import com.core136.bean.meeting.MeetingRoom;
import com.core136.service.account.AccountService;
import com.core136.service.meeting.MeetingDeviceService;
import com.core136.service.meeting.MeetingNotesService;
import com.core136.service.meeting.MeetingRoomService;
import com.core136.service.meeting.MeetingService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/set/meetingset")
public class RoutSetMeetingController {
@Autowired
private MeetingService meetingService;
@Autowired
private MeetingRoomService meetingRoomService;
@Autowired
private MeetingDeviceService meetingDeviceService;
@Autowired
private MeetingNotesService meetingNotesService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: cancelmeeting   
 * @Description: TODO 会议取消通知
 * @param request
 * @param meeting
 * @param isSendMsg
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/cancelmeeting",method=RequestMethod.POST)
public RetDataBean cancelmeeting(HttpServletRequest request,Meeting meeting,String isSendMsg)
{
	try
	{
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		meeting.setOrgId(userInfo.getOrgId());
		meeting = meetingService.selectOneMeeting(meeting);
		meeting.setStatus("3");
		Example example = new Example(Meeting.class);
		example.createCriteria().andEqualTo("orgId",meeting.getOrgId()).andEqualTo("meetingId",meeting.getMeetingId());
		meetingService.sendCancelMeetingMsg(userInfo,meeting);
		return RetDataTools.Ok("取消会议通知已发送!",meetingService.updateMeeting(example, meeting));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: sendMeetingMsg   
 * @Description: TODO 发送会议通知
 * @param request
 * @param meeting
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/sendMeetingMsg",method=RequestMethod.POST)
public RetDataBean sendMeetingMsg(HttpServletRequest request,Meeting meeting)
{
	try
	{
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		meeting.setOrgId(userInfo.getOrgId());
		meeting = meetingService.selectOneMeeting(meeting);
		meetingService.sendMeetingMsg(userInfo,meeting);
		return RetDataTools.Ok("会议通知已发送!");
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertMeetingRoom   
 * @Description: TODO 添加会议室
 * @param: request
 * @param: meetingRoom
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMeetingRoom",method=RequestMethod.POST)
public RetDataBean insertMeetingRoom(HttpServletRequest request,MeetingRoom meetingRoom)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		meetingRoom.setRoomId(SysTools.getGUID());
		meetingRoom.setStatus("0");
		meetingRoom.setCreateUser(account.getAccountId());
		meetingRoom.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		meetingRoom.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建会议室成功!", meetingRoomService.insertMeetingRoom(meetingRoom));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: deleteMeetingRoom   
 * @Description: TODO 删除会议室
 * @param: request
 * @param: meetingRoom
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteMeetingRoom",method=RequestMethod.POST)
public RetDataBean deleteMeetingRoom(HttpServletRequest request,MeetingRoom meetingRoom)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(meetingRoom.getRoomId()))
		{
			return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
		}
		meetingRoom.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除会议室成功!", meetingRoomService.deleteMeetingRoom(meetingRoom));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateMeetingRoom   
 * @Description: TODO 更新会议室
 * @param: request
 * @param: meetingRoom
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateMeetingRoom",method=RequestMethod.POST)
public RetDataBean updateMeetingRoom(HttpServletRequest request,MeetingRoom meetingRoom)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(meetingRoom.getRoomId()))
		{
			return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
		}
		Example example = new Example(MeetingRoom.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("roomId",meetingRoom.getRoomId());
		meetingRoom.setOrgId(account.getOrgId());
		return RetDataTools.Ok("更新会议室成功!", meetingRoomService.updateMeetingRoom(example,meetingRoom));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}



/**
 * 
 * @Title: insertMeetingDevice   
 * @Description: TODO 添加会议室设备
 * @param: request
 * @param: meetingRoom
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMeetingDevice",method=RequestMethod.POST)
public RetDataBean insertMeetingDevice(HttpServletRequest request,MeetingDevice meetingDevice)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		meetingDevice.setDeviceId(SysTools.getGUID());
		meetingDevice.setStatus("0");
		meetingDevice.setCreateUser(account.getAccountId());
		meetingDevice.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		meetingDevice.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加会议室设备成功!", meetingDeviceService.insertMeetingDevice(meetingDevice));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: deleteMeetingDevice   
 * @Description: TODO 删除会议室
 * @param: request
 * @param: meetingRoom
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteMeetingDevice",method=RequestMethod.POST)
public RetDataBean deleteMeetingDevice(HttpServletRequest request,MeetingDevice meetingDevice)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(meetingDevice.getDeviceId()))
		{
			return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
		}
		meetingDevice.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除设备成功!", meetingDeviceService.deleteMeetingDevice(meetingDevice));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateMeetingRoom   
 * @Description: TODO 更新设备信息
 * @param: request
 * @param: meetingRoom
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateMeetingDevice",method=RequestMethod.POST)
public RetDataBean updateMeetingDevice(HttpServletRequest request,MeetingDevice meetingDevice)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(meetingDevice.getDeviceId()))
		{
			return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
		}
		Example example = new Example(MeetingDevice.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("deviceId",meetingDevice.getDeviceId());
		meetingDevice.setOrgId(account.getOrgId());
		return RetDataTools.Ok("更新设备成功!", meetingDeviceService.updateMeetingDevice(example,meetingDevice));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertMeeting   
 * @Description: TODO 申请会议
 * @param: request
 * @param: meeting
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMeeting",method=RequestMethod.POST)
public RetDataBean insertMeeting(HttpServletRequest request,Meeting meeting)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		meeting.setMeetingId(SysTools.getGUID());
		meeting.setCreateUser(account.getAccountId());
		meeting.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		meeting.setOrgId(account.getOrgId());
		meeting.setStatus("0");
		return RetDataTools.Ok("申请会议成功!", meetingService.insertMeeting(meeting));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: deleteMeeting   
 * @Description: TODO 撤销会议
 * @param: request
 * @param: meeting
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteMeeting",method=RequestMethod.POST)
public RetDataBean deleteMeeting(HttpServletRequest request,Meeting meeting)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(meeting.getMeetingId()))
		{
			return RetDataTools.NotOk("请求参数不正确！请检查相关参数！"); 
		}
		meeting.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除会议成功!", meetingService.deleteMeeting(meeting));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: updateMeeting   
 * @Description: TODO 更新会议
 * @param: request
 * @param: meeting
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateMeeting",method=RequestMethod.POST)
public RetDataBean updateMeeting(HttpServletRequest request,Meeting meeting)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(meeting.getMeetingId()))
		{
			return RetDataTools.NotOk("请求参数不正确！请检查相关参数！"); 
		}
		Example example = new Example(Meeting.class);
		if(account.getOpFlag().equals("1"))
		{
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("meetingId",meeting.getMeetingId());
		}else
		{
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("meetingId",meeting.getMeetingId()).andEqualTo("createUser",meeting.getCreateUser());
		}
		return RetDataTools.Ok("更新会议成功!", meetingService.updateMeeting(example, meeting));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertMeetingNotes   
 * @Description: TODO 添加会议记要 
 * @param: request
 * @param: meetingNotes
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertMeetingNotes",method=RequestMethod.POST)
public RetDataBean insertMeetingNotes(HttpServletRequest request,MeetingNotes meetingNotes)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		meetingNotes.setNotesId(SysTools.getGUID());
		meetingNotes.setCreateUser(account.getAccountId());
		meetingNotes.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		meetingNotes.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加会议纪要成功!", meetingNotesService.insertMeetingNotes(meetingNotes));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: deleteMeetingNotes   
 * @Description: TODO 删除会议记要
 * @param: request
 * @param: meetingNotes
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteMeetingNotes",method=RequestMethod.POST)
public RetDataBean deleteMeetingNotes(HttpServletRequest request,MeetingNotes meetingNotes)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(meetingNotes.getNotesId()))
		{
			return RetDataTools.NotOk("请求参数不正确！请检查相关参数！"); 
		}
		meetingNotes.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除会议成功!", meetingNotesService.deleteMeetingNotes(meetingNotes));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateMeetingNotes   
 * @Description: TODO 更新会议记要
 * @param: request
 * @param: meetingNotes
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateMeetingNotes",method=RequestMethod.POST)
public RetDataBean updateMeetingNotes(HttpServletRequest request,MeetingNotes meetingNotes)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(meetingNotes.getNotesId()))
		{
			return RetDataTools.NotOk("请求参数不正确！请检查相关参数！"); 
		}
		Example example = new Example(MeetingNotes.class);
		if(account.getOpFlag().equals("1"))
		{
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("notesId",meetingNotes.getNotesId());
		}else
		{
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("notesId",meetingNotes.getNotesId()).andEqualTo("createUser",meetingNotes.getCreateUser());
		}
		return RetDataTools.Ok("更新会议记要成功!", meetingNotesService.updateMeetingNotes(example, meetingNotes));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

}
