/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  MeetingMapper.java   
 * @Package com.core136.mapper.meeting   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月17日 上午10:45:01   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.meeting;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.meeting.Meeting;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface MeetingMapper extends MyMapper<Meeting>{
	/**
	 * 
	 * @Title: getMeetingList   
	 * @Description: TODO 获取个人发起的会议申请
	 * @param: orgId
	 * @param: opFlag
	 * @param: roomId
	 * @param: beginTime
	 * @param: endTime
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
public List<Map<String, String>>getMeetingList(@Param(value="orgId") String orgId,@Param(value="opFlag") String opFlag,
		@Param(value="createUser") String createUser,@Param(value="roomId") String roomId,
		@Param(value="beginTime") String beginTime,@Param(value="endTime") String endTime,@Param(value="search") String search);

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
public List<Map<String, String>>getApplyMeetingList(@Param(value="orgId") String orgId,@Param(value="opFlag") String opFlag,
		@Param(value="accountId") String accountId,@Param(value="beginTime") String beginTime,@Param(value="endTime") String endTime,@Param(value="search") String search);

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
public List<Map<String, String>>getNotNotesMeetingList(@Param(value="orgId")String orgId,String accountId);
/**
 * 
 * @Title: getMeetingByDay   
 * @Description: TODO 获取禁用的会议时间段
 * @param orgId
 * @param beginTime
 * @param endTime
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMeetingByDay(@Param(value="orgId")String orgId,@Param(value="deptId")String deptId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime);

/**
 * 
 * @Title: getMyApplyMeetingList   
 * @Description: TODO 获取个人历史会议申请记录
 * @param orgId
 * @param accountId
 * @param chair
 * @param roomId
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyApplyMeetingList(
		@Param(value="orgId")String orgId,
		@Param(value="accountId")String accountId,
		@Param(value="chair") String chair,
		@Param(value="roomId")String roomId,
		@Param(value="beginTime")String beginTime,
		@Param(value="endTime")String endTime,
		@Param(value="search") String search
		);

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
public List<Map<String, String>>getMyMeetingList(
		@Param(value="orgId")String orgId,
		@Param(value="accountId")String accountId,
		@Param(value="deptId")String deptId,
		@Param(value="leaveId")String leaveId,
		@Param(value="beginTime")String beginTime,
		@Param(value="endTime")String endTime,
		@Param(value="nowTime")String nowTime,
		@Param(value="search") String search
		);
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
 * @param nowTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyMeetingOldList(
		@Param(value="orgId")String orgId,
		@Param(value="accountId")String accountId,
		@Param(value="deptId")String deptId,
		@Param(value="leaveId")String leaveId,
		@Param(value="beginTime")String beginTime,
		@Param(value="endTime")String endTime,
		@Param(value="nowTime")String nowTime,
		@Param(value="search") String search
		);

/**
 * 
 * @Title: getOldApplyMeetingList   
 * @Description: TODO 历史审批记录列表
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
public List<Map<String, String>>getOldApplyMeetingList(@Param(value="orgId") String orgId,
		@Param(value="opFlag") String opFlag,@Param(value="status")String status,
		@Param(value="accountId") String accountId,@Param(value="beginTime") String beginTime,@Param(value="endTime") String endTime,@Param(value="search") String search);

/**
 * 
 * @Title: getMyMeetingListForDesk   
 * @Description: TODO 获取桌面会议
 * @param orgId
 * @param accountId
 * @param deptId
 * @param levelId
 * @param beginTime
 * @param endTime
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyMeetingListForDesk(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
		@Param(value="deptId")String deptId,@Param(value="levelId")String levelId,@Param(value="nowTime")String nowTime);

/**
 * 
 * @Title: getMobileMyMeetingList   
 * @Description: TODO 获取移动端待办会议
 * @param orgId
 * @param accountId
 * @param deptId
 * @param levelId
 * @param nowTime
 * @param page
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMobileMyMeetingList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
		@Param(value="deptId")String deptId,@Param(value="levelId")String levelId,@Param(value="nowTime")String nowTime,@Param(value="page")Integer page);


}
