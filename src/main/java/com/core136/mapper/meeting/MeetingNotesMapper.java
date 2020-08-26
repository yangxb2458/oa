/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  MeetingNotesMapper.java   
 * @Package com.core136.mapper.meeting   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月31日 上午10:55:49   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.meeting;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.meeting.MeetingNotes;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface MeetingNotesMapper extends MyMapper<MeetingNotes>{

/**
 * 
 * @Title: getMeetingNotesList   
 * @Description: TODO 获取会议记要列表
 * @param: orgId
 * @param: opFlag
 * @param: accountId
 * @param: beginTime
 * @param: endTime
 * @param: roomId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String,String>> getMeetingNotesList(
			@Param(value="orgId") String orgId,
			@Param(value="opFlag")String opFlag,
			@Param(value="accountId") String accountId,
			@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,
			@Param(value="search") String search
			);
	/**
	 * 
	 * @Title: queryMeetingNotesList   
	 * @Description: TODO 会议记要查询
	 * @param orgId
	 * @param opFlag
	 * @param accountId
	 * @param deptId
	 * @param leavePriv
	 * @param beginTime
	 * @param endTime
	 * @param roomId
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String,String>> queryMeetingNotesList(
			@Param(value="orgId") String orgId,
			@Param(value="opFlag")String opFlag,
			@Param(value="accountId") String accountId,
			@Param(value="deptId")String deptId,
			@Param(value="leavePriv")String leavePriv,
			@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,
			@Param(value="search") String search
			);
	
	
	/**
	 * 
	 * @Title: getMeetingNotesInfo   
	 * @Description: TODO 获取会议记要详情
	 * @param orgId
	 * @param notesId
	 * @return
	 * Map<String,String>    
	 * @throws
	 */
	public Map<String, String>getMeetingNotesInfo(@Param(value="orgId")String orgId,@Param(value="notesId") String notesId);
}
