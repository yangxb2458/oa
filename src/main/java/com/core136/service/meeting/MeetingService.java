/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  MeetingService.java   
 * @Package com.core136.service.meeting   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月17日 上午10:47:26   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.core136.common.enums.GobalConstant;
import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.meeting.Meeting;
import com.core136.bean.meeting.MeetingRoom;
import com.core136.bean.sys.MsgBody;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.meeting.MeetingMapper;
import com.core136.service.account.AccountService;
import com.core136.service.sys.MessageUnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class MeetingService {
@Autowired
private MeetingMapper meetingMapper;
@Autowired
private MessageUnitService messageUnitService;
@Autowired
private AccountService accountService;
@Autowired
private MeetingRoomService meetingRoomService;

public int insertMeeting(Meeting meeting)
{
	return meetingMapper.insert(meeting);
}

public int deleteMeeting(Meeting meeting)
{
	return meetingMapper.delete(meeting);
}

public int updateMeeting(Example example,Meeting meeting)
{
	return meetingMapper.updateByExampleSelective(meeting, example);
}

public Meeting selectOneMeeting(Meeting meeting)
{
	return meetingMapper.selectOne(meeting);
}

public void sendCancelMeetingMsg(UserInfo userInfo,Meeting meeting)
{
	String msgType = meeting.getMsgType();
	
	if(StringUtils.isNotBlank(msgType))
	{
		String roomId = meeting.getRoomId();
		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setRoomId(roomId);
		meetingRoom.setOrgId(userInfo.getOrgId());
		meetingRoom = meetingRoomService.selectOneMeetingRoom(meetingRoom);
	String sendTime = SysTools.getTime("yyyy-MM-dd HH:mm:ss");
	List<MsgBody> msgList = new ArrayList<MsgBody>();
	String chair = meeting.getChair();
	Account account = accountService.getAccountByAccountId(chair, meeting.getOrgId());
	if(account!=null)
	{
		MsgBody msgBody = new MsgBody();
		msgBody.setAccount(account);
		msgBody.setFormUserName(userInfo.getUserName());
		msgBody.setFromAccountId(userInfo.getAccountId());
		msgBody.setOrgId(userInfo.getOrgId());
		msgBody.setSendTime(sendTime);
		msgBody.setTitle(meeting.getSubject()+" 取消通知！");
		msgBody.setRedirectUrl("/app/core/meeting/meetingdetails?meetingId="+meeting.getMeetingId());
		msgBody.setContent("时间原定于："+meeting.getBeginTime()+"-"+meeting.getEndTime()+
				"，主题为："+meeting.getSubject()+"的会议已取消");
		msgList.add(msgBody);
	}
	String userJoin = meeting.getUserJoin();
	String deptJoin = meeting.getDeptJoin();
	String leaveJoin = meeting.getLeaveJoin();
	List<Account> inPrivAccountList = accountService.getAccountInPriv(userInfo.getOrgId(), userJoin, deptJoin, leaveJoin);
	for(int i=0;i<inPrivAccountList.size();i++)
	{
		MsgBody msgBody = new MsgBody();
		msgBody.setAccount(inPrivAccountList.get(i));
		msgBody.setFormUserName(userInfo.getUserName());
		msgBody.setFromAccountId(userInfo.getAccountId());
		msgBody.setOrgId(userInfo.getOrgId());
		msgBody.setSendTime(sendTime);
		msgBody.setTitle(meeting.getSubject()+" 取消通知！");
		msgBody.setRedirectUrl("/app/core/meeting/meetingdetails?meetingId="+meeting.getMeetingId());
		msgBody.setContent("时间原定于："+meeting.getBeginTime()+"-"+meeting.getEndTime()+
				"，主题为："+meeting.getSubject()+"的会议已取消");
		msgList.add(msgBody);
	}
	messageUnitService.sendMessage(msgType, GobalConstant.MSG_TYPE_MEETING, msgList);
	}
}


/**
 * 
 * @Title: sendMeetingMsg   
 * @Description: TODO 发送会议提醒
 * @param userInfo
 * @param meeting
 * void    
 * @throws
 */
public void sendMeetingMsg(UserInfo userInfo,Meeting meeting)
{
	String msgType = meeting.getMsgType();
	
	if(StringUtils.isNotBlank(msgType))
	{
		String roomId = meeting.getRoomId();
		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setRoomId(roomId);
		meetingRoom.setOrgId(userInfo.getOrgId());
		meetingRoom = meetingRoomService.selectOneMeetingRoom(meetingRoom);
	String sendTime = SysTools.getTime("yyyy-MM-dd HH:mm:ss");
	List<MsgBody> msgList = new ArrayList<MsgBody>();
	String chair = meeting.getChair();
	Account account = accountService.getAccountByAccountId(chair, meeting.getOrgId());
	if(account!=null)
	{
		MsgBody msgBody = new MsgBody();
		msgBody.setAccount(account);
		msgBody.setFormUserName(userInfo.getUserName());
		msgBody.setFromAccountId(userInfo.getAccountId());
		msgBody.setOrgId(userInfo.getOrgId());
		msgBody.setSendTime(sendTime);
		msgBody.setTitle(meeting.getSubject());
		msgBody.setAttach(meeting.getAttach());
		msgBody.setRedirectUrl("/app/core/meeting/meetingdetails?meetingId="+meeting.getMeetingId());
		msgBody.setContent("主题为："+meeting.getSubject()+"的会议，您将是该会的主持。请您提前做好准备工作。会议时间："+meeting.getBeginTime()+"-"+meeting.getEndTime()+
				"开会地址："+meetingRoom.getName()+"具体地址："+meetingRoom.getAddress());
		msgList.add(msgBody);
	}
	String userJoin = meeting.getUserJoin();
	String deptJoin = meeting.getDeptJoin();
	String leaveJoin = meeting.getLeaveJoin();
	List<Account> inPrivAccountList = accountService.getAccountInPriv(userInfo.getOrgId(), userJoin, deptJoin, leaveJoin);
	for(int i=0;i<inPrivAccountList.size();i++)
	{
		MsgBody msgBody = new MsgBody();
		msgBody.setAccount(inPrivAccountList.get(i));
		msgBody.setFormUserName(userInfo.getUserName());
		msgBody.setFromAccountId(userInfo.getAccountId());
		msgBody.setOrgId(userInfo.getOrgId());
		msgBody.setSendTime(sendTime);
		msgBody.setTitle(meeting.getSubject());
		msgBody.setAttach(meeting.getAttach());
		msgBody.setRedirectUrl("/app/core/meeting/meetingdetails?meetingId="+meeting.getMeetingId());
		msgBody.setContent("主题为："+meeting.getSubject()+"的会议，邀请您参加。请您提前做好准备工作。会议时间："+meeting.getBeginTime()+"-"+meeting.getEndTime()+ 
			"开会地址："+meetingRoom.getName()+"具体地址："+meetingRoom.getAddress());
		msgList.add(msgBody);
	}
	messageUnitService.sendMessage(msgType, GobalConstant.MSG_TYPE_MEETING, msgList);
	}
}
/**
 * 
 * @Title: sendApplyMeeingMsg   
 * @Description: TODO 会议申请审批提醒
 * @param userInfo
 * @param meeting
 * void    
 * @throws
 */
public void sendApplyMeeingMsg(UserInfo userInfo,Meeting meeting)
{
	String roomId = meeting.getRoomId();
	MeetingRoom meetingRoom = new MeetingRoom();
	meetingRoom.setRoomId(roomId);
	meetingRoom.setOrgId(userInfo.getOrgId());
	meetingRoom = meetingRoomService.selectOneMeetingRoom(meetingRoom);
	String manager = meetingRoom.getManager();
	if(StringUtils.isNotBlank(manager))
	{
		String sendTime = SysTools.getTime("yyyy-MM-dd HH:mm:ss");
		String msgType = meeting.getMsgType();
		List<MsgBody> msgList = new ArrayList<MsgBody>();
		MsgBody msgBody = new MsgBody();
		Account account = accountService.getAccountByAccountId(manager, meeting.getOrgId());
		if(account!=null)
		{
		msgBody.setAccount(account);
		msgBody.setFormUserName(userInfo.getUserName());
		msgBody.setFromAccountId(userInfo.getAccountId());
		msgBody.setOrgId(userInfo.getOrgId());
		msgBody.setSendTime(sendTime);
		msgBody.setContent("主题为："+meeting.getSubject()+"的会议，需要您审批。");
		msgList.add(msgBody);
		messageUnitService.sendMessage(msgType, GobalConstant.MSG_TYPE_MEETING, msgList);
		}
	}
}


/**
 * 
 * @Title: getMeetingList   
 * @Description: TODO 获取个人发起的会议申请
 * @param: orgId
 * @param: opFlag
 * @param: createUser
 * @param: roomId
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getMeetingList(String orgId,String opFlag,String createUser,String roomId,String beginTime,String endTime,String search)
{
	return meetingMapper.getMeetingList(orgId, opFlag, createUser, roomId, beginTime, endTime, "%"+search+"%");
}
/**
 * 
 * @Title: getMeetingList   
 * @Description: TODO 获取个人发起的会议申请
 * @param: pageParam
 * @param: roomId
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getMeetingList(PageParam pageParam,String roomId,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMeetingList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),roomId,beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getApplyMeetingList   
 * @Description: TODO 获取待审批的会议列表
 * @param orgId
 * @param opFlag
 * @param accountId
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getApplyMeetingList(String orgId,String opFlag,String accountId,String beginTime,String endTime,String search)
{
	return meetingMapper.getApplyMeetingList(orgId, opFlag, accountId, beginTime, endTime, "%"+search+"%");
}
/**
 * 
 * @Title: getApplyMeetingList   
 * @Description: TODO 获取待审批的会议列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getApplyMeetingList(PageParam pageParam,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getApplyMeetingList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMyMeetingListForDesk   
 * @Description: TODO 获取桌面会议
 * @param orgId
 * @param accountId
 * @param deptId
 * @param leaveId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getMyMeetingListForDesk(String orgId,String accountId,String deptId,String levelId)
{
	String nowTime = SysTools.getTime("yyyy-MM-dd HH:mm");
	return meetingMapper.getMyMeetingListForDesk(orgId, accountId, deptId, levelId, nowTime);
}

/**
 * 
 * @Title: getMobileMyMeetingList   
 * @Description: TODO  获取移动端待办会议
 * @param orgId
 * @param accountId
 * @param deptId
 * @param levelId
 * @param page
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getMobileMyMeetingList(String orgId,String accountId,String deptId,String levelId,Integer page)
{
	String nowTime = SysTools.getTime("yyyy-MM-dd HH:mm");
	return meetingMapper.getMobileMyMeetingList(orgId, accountId, deptId, levelId, nowTime,page);
}
/**
 * 
 * @Title: getMyMeetingList   
 * @Description: TODO 获取当前用户待参加会议
 * @param orgId
 * @param accountId
 * @param deptId
 * @param leaveId
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getMyMeetingList(String orgId,String accountId,String deptId,String leaveId,String beginTime,String endTime,String search)
{
	String nowTime = SysTools.getTime("yyyy-MM-dd HH:mm");
	return meetingMapper.getMyMeetingList(orgId, accountId, deptId, leaveId, beginTime, endTime, nowTime, "%"+search+"%");
}

/**
 * 
 * @Title: getMyMeetingList   
 * @Description: TODO 获取当前用户待参加会议
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyMeetingList(PageParam pageParam,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyMeetingList(pageParam.getOrgId(),pageParam.getAccountId(),pageParam.getDeptId(),pageParam.getLevelId(),beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}


/**
 * 
 * @Title: getMyMeetingOldList   
 * @Description: TODO 获取个人以往参加的会议记录
 * @param orgId
 * @param accountId
 * @param deptId
 * @param leaveId
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getMyMeetingOldList(String orgId,String accountId,String deptId,String leaveId,String beginTime,String endTime,String search)
{
	String nowTime = SysTools.getTime("yyyy-MM-dd HH:mm");
	return meetingMapper.getMyMeetingOldList(orgId, accountId, deptId, leaveId, beginTime, endTime, nowTime, "%"+search+"%");
}
/**
 * 
 * @Title: getMyMeetingOldList   
 * @Description: TODO 获取个人以往参加的会议记录
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyMeetingOldList(PageParam pageParam,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyMeetingOldList(pageParam.getOrgId(),pageParam.getAccountId(),pageParam.getDeptId(),pageParam.getLevelId(),beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getOldApplyMeetingList   
 * @Description: TODO 获取历史审批记录
 * @param orgId
 * @param opFlag
 * @param status
 * @param accountId
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getOldApplyMeetingList(String orgId,String opFlag,String status,String accountId,String beginTime,String endTime,String search)
{
	return meetingMapper.getOldApplyMeetingList(orgId, opFlag, status, accountId, beginTime, endTime, "%"+search+"%");
}

/**
 * 
 * @Title: getOldApplyMeetingList   
 * @Description: TODO 获取历史审批记录
 * @param pageParam
 * @param status
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getOldApplyMeetingList(PageParam pageParam,String status,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getOldApplyMeetingList(pageParam.getOrgId(),pageParam.getOpFlag(),status,pageParam.getAccountId(),beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getNotNotesMeetingList   
 * @Description: TODO 获取没有会议纪要的会议列表
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getNotNotesMeetingList(String orgId,String accountId)
{
	return meetingMapper.getNotNotesMeetingList(orgId,accountId);
}
/**
 * 
 * @Title: getMeetingByDay   
 * @Description: TODO  获取禁用的会议时间段
 * @param orgId
 * @param deptId
 * @param dayStr
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMeetingByDay(String orgId,String deptId,String dayStr)
{
	return meetingMapper.getMeetingByDay(orgId, deptId, dayStr+" 00:00", dayStr+" 24:00");
}
/**
 * 
 * @Title: getMyApplyMeetingList   
 * @Description: TODO 获取个人历史会议申请记录
 * @param orgId
 * @param accountId
 * @param chair
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyApplyMeetingList(String orgId,String accountId,String chair,String roomId,String beginTime,String endTime,String search)
{
	return meetingMapper.getMyApplyMeetingList(orgId, accountId, chair,roomId, beginTime, endTime, "%"+search+"%");
}

/**
 * 
 * @Title: getMyApplyMeetingList   
 * @Description: TODO 获取个人历史会议申请记录
 * @param pageParam
 * @param chair
 * @param roomId
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyApplyMeetingList(PageParam pageParam,String chair,String roomId,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyApplyMeetingList(pageParam.getOrgId(),pageParam.getAccountId(),chair,roomId,beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
