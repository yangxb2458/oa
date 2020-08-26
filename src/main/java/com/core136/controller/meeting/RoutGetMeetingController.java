/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetMeetingController.java   
 * @Package com.core136.controller.meeting   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月22日 上午10:11:20   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.meeting;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
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
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.meeting.MeetingDeviceService;
import com.core136.service.meeting.MeetingNotesService;
import com.core136.service.meeting.MeetingRoomService;
import com.core136.service.meeting.MeetingService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import org.core136.common.utils.SysTools;

import com.github.pagehelper.PageInfo;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/ret/meetingget")
public class RoutGetMeetingController {
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
	 * @Title: getMyMeetingListForDesk   
	 * @Description: TODO  获取桌面会议
	 * @param request
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMyMeetingListForDesk",method=RequestMethod.POST)
	public RetDataBean getMyMeetingListForDesk(HttpServletRequest request)
	{
		try
		{
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			return RetDataTools.Ok("请求成功!", meetingService.getMyMeetingListForDesk(userInfo.getOrgId(),userInfo.getAccountId(),userInfo.getDeptId(),userInfo.getLeadLeave()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMeetingNotesInfo   
	 * @Description: TODO 获取会议记要详情
	 * @param request
	 * @param neetingNotes
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMeetingNotesInfo",method=RequestMethod.POST)
	public RetDataBean getMeetingNotesInfo(HttpServletRequest request,MeetingNotes neetingNotes)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", meetingNotesService.getMeetingNotesInfo(account.getOrgId(),neetingNotes.getNotesId()));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMeetingNotesList   
	 * @Description: TODO  获取会议记要列表 
	 * @param: request
	 * @param: pageParam
	 * @param: beginTime
	 * @param: endTime
	 * @param: roomId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMeetingNotesList",method=RequestMethod.POST)
	public RetDataBean getMeetingNotesList(HttpServletRequest request,
			PageParam pageParam,
			String beginTime,
			String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("N.CREATE_TIME");
			}else
			{
				pageParam.setSort("N."+StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=meetingNotesService.getMeetingNotesList(pageParam, beginTime, endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: queryMeetingNotesList   
	 * @Description: TODO 会议记要查询
	 * @param request
	 * @param pageParam
	 * @param beginTime
	 * @param endTime
	 * @param roomId
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/queryMeetingNotesList",method=RequestMethod.POST)
	public RetDataBean queryMeetingNotesList(HttpServletRequest request,
			PageParam pageParam,
			String beginTime,
			String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("N.CREATE_TIME");
			}else
			{
				pageParam.setSort("N."+StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
		Account account=accountService.getRedisAccount(request);
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		pageParam.setOrgId(userInfo.getOrgId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setAccountId(userInfo.getAccountId());
		pageParam.setDeptId(userInfo.getDeptId());
		pageParam.setLevelId(userInfo.getLeadLeave());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=meetingNotesService.queryMeetingNotesList(pageParam, beginTime, endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getNotNotesMeetingList   
	 * @Description: TODO 获取没有会议纪要的会议列表  
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getNotNotesMeetingList",method=RequestMethod.POST)
	public RetDataBean getNotNotesMeetingList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", meetingService.getNotNotesMeetingList(account.getOrgId(),account.getAccountId()));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMeetingRoomById   
	 * @Description: TODO 获取会议室详情 
	 * @param: request
	 * @param: meetingRoom
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMeetingRoomById",method=RequestMethod.POST)
	public RetDataBean getMeetingRoomById(HttpServletRequest request,MeetingRoom meetingRoom)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			meetingRoom.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", meetingRoomService.selectOneMeetingRoom(meetingRoom));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getCanUseMeetingRoomList   
	 * @Description: TODO 获取当前用户可用的会议室
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getCanUseMeetingRoomList",method=RequestMethod.POST)
	public RetDataBean getCanUseMeetingRoomList(HttpServletRequest request,String search)
	{
		try
		{
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			return RetDataTools.Ok("请求成功!", meetingRoomService.getCanUseMeetingRoomList(userInfo.getOrgId(),userInfo.getDeptId(),search));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getMeetingRoomList   
	 * @Description: TODO 会议室列表
	 * @param: request
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMeetingRoomList",method=RequestMethod.POST)
	public RetDataBean getMeetingRoomList(HttpServletRequest request,PageParam pageParam)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("SORT_NO");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=meetingRoomService.getMeetingRoomList(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMeetingDeviceById   
	 * @Description: TODO 获取设备详情
	 * @param: request
	 * @param: meetingDevice
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMeetingDeviceById",method=RequestMethod.POST)
	public RetDataBean getMeetingRoomDeviceById(HttpServletRequest request,MeetingDevice meetingDevice)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			meetingDevice.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", meetingDeviceService.selectOneMeetingDevice(meetingDevice));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getDeviceListName   
	 * @Description: TODO 获取设备名称列表
	 * @param request
	 * @param deviceIds
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getDeviceListName",method=RequestMethod.POST)
	public RetDataBean getDeviceListName(HttpServletRequest request,String deviceIds)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", meetingDeviceService.getDeviceListName(account.getOrgId(),deviceIds));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getCanUseDeviceList   
	 * @Description: TODO 获取权限内可用的会议室设备
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getCanUseDeviceList",method=RequestMethod.POST)
	public RetDataBean getCanUseDeviceList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			if(account.getOpFlag().equals("1"))
			{
				return RetDataTools.Ok("请求成功!", meetingDeviceService.getCanUseDeviceList(userInfo.getOrgId(), null));	
			}else
			{
			return RetDataTools.Ok("请求成功!", meetingDeviceService.getCanUseDeviceList(userInfo.getOrgId(), userInfo.getDeptId()));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMeetingDeviceList   
	 * @Description: TODO 获取会议设置列表
	 * @param: request
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMeetingDeviceList",method=RequestMethod.POST)
	public RetDataBean getMeetingDeviceList(HttpServletRequest request,PageParam pageParam)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("SORT_NO");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=meetingDeviceService.getMeetingDeviceList(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMeetingById   
	 * @Description: TODO 获取会议详情
	 * @param: request
	 * @param: meeting
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMeetingById",method=RequestMethod.POST)
	public RetDataBean getMeetingById(HttpServletRequest request,Meeting meeting)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			meeting.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", meetingService.selectOneMeeting(meeting));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMeetingList   
	 * @Description: TODO 获取个人发起的会议申请
	 * @param: request
	 * @param: pageParam
	 * @param: roomId
	 * @param: beginTime
	 * @param: endTime
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMeetingList",method=RequestMethod.POST)
	public RetDataBean getMeetingList(HttpServletRequest request,PageParam pageParam,
			String roomId,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		PageInfo<Map<String, String>> pageInfo=meetingService.getMeetingList(pageParam,roomId,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getApplyMeetingList   
	 * @Description: TODO 获取待审批的会议列表
	 * @param request
	 * @param pageParam
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getApplyMeetingList",method=RequestMethod.POST)
	public RetDataBean getApplyMeetingList(HttpServletRequest request,PageParam pageParam,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("M.CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		PageInfo<Map<String, String>> pageInfo=meetingService.getApplyMeetingList(pageParam,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMyMeetingList   
	 * @Description: TODO 获取当前用户待参加会议
	 * @param request
	 * @param pageParam
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMyMeetingList",method=RequestMethod.POST)
	public RetDataBean getMyMeetingList(HttpServletRequest request,PageParam pageParam,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("M.CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
			UserInfo userInfo = accountService.getRedisUserInfo(request);
		pageParam.setOrgId(userInfo.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(userInfo.getAccountId());
		pageParam.setDeptId(userInfo.getDeptId());
		pageParam.setLevelId(userInfo.getLeadLeave());
		PageInfo<Map<String, String>> pageInfo=meetingService.getMyMeetingList(pageParam,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMyMeetingOldList   
	 * @Description: TODO 获取个人以往参加的会议记录
	 * @param request
	 * @param pageParam
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMyMeetingOldList",method=RequestMethod.POST)
	public RetDataBean getMyMeetingOldList(HttpServletRequest request,PageParam pageParam,String beginTime,String endTime)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("M.CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
			UserInfo userInfo = accountService.getRedisUserInfo(request);
		pageParam.setOrgId(userInfo.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(userInfo.getAccountId());
		pageParam.setDeptId(userInfo.getDeptId());
		pageParam.setLevelId(userInfo.getLeadLeave());
		PageInfo<Map<String, String>> pageInfo=meetingService.getMyMeetingOldList(pageParam,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getOldApplyMeetingList   
	 * @Description: TODO 获取历史审批记录
	 * @param request
	 * @param pageParam
	 * @param status
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getOldApplyMeetingList",method=RequestMethod.POST)
	public RetDataBean getOldApplyMeetingList(HttpServletRequest request,PageParam pageParam,String status,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("M.CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		PageInfo<Map<String, String>> pageInfo=meetingService.getOldApplyMeetingList(pageParam,status,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMyApplyMeetingList   
	 * @Description: TODO 获取个人历史会议申请记录
	 * @param request
	 * @param pageParam
	 * @param roomId
	 * @param beginTime
	 * @param endTime
	 * @param chair
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMyApplyMeetingList",method=RequestMethod.POST)
	public RetDataBean getMyApplyMeetingList(HttpServletRequest request,PageParam pageParam,
			String roomId,String beginTime,String endTime,String chair
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		PageInfo<Map<String, String>> pageInfo=meetingService.getMyApplyMeetingList(pageParam,chair,roomId,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMeetingNotesById   
	 * @Description: TODO 获取会议记要详情
	 * @param: request
	 * @param: meetingNotes
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMeetingNotesById",method=RequestMethod.POST)
	public RetDataBean getMeetingNotesById(HttpServletRequest request,MeetingNotes meetingNotes)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			meetingNotes.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", meetingNotesService.selectOneMeetingNotes(meetingNotes));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMeetingByDay   
	 * @Description: TODO 获取禁用的会议时间段
	 * @param request
	 * @param dayStr
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMeetingByDay",method=RequestMethod.POST)
	public RetDataBean getMeetingByDay(HttpServletRequest request,String dayStr)
	{
		try
		{
			if(StringUtils.isBlank(dayStr))
			{
				dayStr = SysTools.getTime("yyyy-MM-dd");
			}
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			return RetDataTools.Ok("请求成功!", meetingService.getMeetingByDay(userInfo.getOrgId(),userInfo.getDeptId(),dayStr));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
}
