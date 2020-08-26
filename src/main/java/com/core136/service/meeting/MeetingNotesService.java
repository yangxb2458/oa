/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  MeetingNotesService.java   
 * @Package com.core136.service.meeting   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月31日 上午10:57:00   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.meeting;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.meeting.MeetingNotes;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.meeting.MeetingNotesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class MeetingNotesService {
@Autowired
private MeetingNotesMapper meetingNotesMapper;

public int insertMeetingNotes(MeetingNotes meetingNotes)
{
	return meetingNotesMapper.insert(meetingNotes);
}

public int deleteMeetingNotes(MeetingNotes meetingNotes)
{
	return meetingNotesMapper.delete(meetingNotes);
}

public int updateMeetingNotes(Example example, MeetingNotes meetingNotes)
{
	return meetingNotesMapper.updateByExampleSelective(meetingNotes, example);
}

public MeetingNotes selectOneMeetingNotes(MeetingNotes meetingNotes)
{
	return meetingNotesMapper.selectOne(meetingNotes);
}
/**
 * 
 * @Title: getMeetingNotesList   
 * @Description: TODO 获取会议记要列表
 * @param: orgId
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getMeetingNotesList(String orgId,String opFlag,String accountId,String beginTime,String endTime,String search)
{
	return meetingNotesMapper.getMeetingNotesList(orgId, opFlag,accountId,beginTime, endTime, "%"+search+"%");
}
/**
 * 
 * @Title: queryMeetingNotesList   
 * @Description: TODO 会议记要查询
 * @param orgId
 * @param opFlag
 * @param accountId
 * @param deptId
 * @param leaveId
 * @param beginTime
 * @param endTime
 * @param roomId
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> queryMeetingNotesList(String orgId,String opFlag,String accountId,String deptId,String leaveId,String beginTime,String endTime,String search)
{
	return meetingNotesMapper.queryMeetingNotesList(orgId, opFlag,accountId,deptId,leaveId,beginTime, endTime, "%"+search+"%");
}


/**
 * 
 * @Title: getMeetingNotesList   
 * @Description: TODO 获取会议记要列表 
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getMeetingNotesList(PageParam pageParam,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMeetingNotesList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: queryMeetingNotesList   
 * @Description: TODO 会议记要查询
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param roomId
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> queryMeetingNotesList(PageParam pageParam,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= queryMeetingNotesList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),pageParam.getDeptId(),pageParam.getLevelId(),beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

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
public Map<String, String>getMeetingNotesInfo(String orgId,String notesId)
{
	return meetingNotesMapper.getMeetingNotesInfo(orgId, notesId);
}

}
