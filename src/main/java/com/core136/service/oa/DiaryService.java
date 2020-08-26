package com.core136.service.oa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.core136.common.enums.GobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.oa.Diary;
import com.core136.bean.oa.DiaryPriv;
import com.core136.bean.sys.MsgBody;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.oa.DiaryMapper;
import com.core136.service.account.AccountService;
import com.core136.service.account.UserInfoService;
import com.core136.service.bpm.BpmListService;
import com.core136.service.file.AttachService;
import com.core136.service.sys.MessageUnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  DiaryService   
 * @Description:TODO 工作日志操作类
 * @author: 稠云技术 
 * @date:   2019年7月6日 下午11:38:45   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class DiaryService {
@Autowired
private DiaryMapper diaryMapper;
@Autowired
private DiaryCommentsService diaryCommentsService;
@Autowired
private MessageUnitService messageUnitService;
@Autowired
private AccountService accountService;
@Autowired
private UserInfoService userInfoService;
@Autowired
private BpmListService bpmListService;
@Autowired
private AttachService attachService;
/**
 * 
 * @Title: insertDiary   
 * @Description: TODO 创建工作日志
 * @param: diary
 * @param: @return      
 * @return: int      
 * @throws
 */
public int insertDiary(Diary diary)
{
	return diaryMapper.insert(diary);
}

public int createDiary(Account account,UserInfo userInfo,Diary diary,DiaryPriv diaryPriv)
{
	if(diaryPriv!=null)
	{
		if(diaryPriv.getShareStatus()==0)
		{
			diary.setUserPriv("@all");
			diary.setDeptPriv("");
			diary.setLeavePriv("");
		}else if(diaryPriv.getShareStatus()==1)
		{
			diary.setUserPriv("");
			diary.setDeptPriv(userInfo.getDeptId());
			diary.setLeavePriv("");
		}else if(diaryPriv.getShareStatus()==3){
			diary.setUserPriv("");
			diary.setDeptPriv("");
			diary.setLeavePriv("");
		}
	}else
	{
		diary.setUserPriv("");
		diary.setDeptPriv("");
		diary.setLeavePriv("");
	}
	if(StringUtils.isNotBlank(diary.getMsgType()))
	{
	List<Map<String, String>> userList = userInfoService.getAccountIdInPriv(diary.getOrgId(), diary.getUserPriv(), diary.getDeptPriv(), diary.getLeavePriv());
	List<MsgBody> msgBodyList = new ArrayList<MsgBody>();
	for(int i=0;i<userList.size();i++)
	{
		Account account2 = new Account();
		account2.setAccountId(userList.get(i).toString());
		account2.setOrgId(account.getOrgId());
		account2 = accountService.selectOneAccount(account2);
		MsgBody msgBody = new MsgBody();
		msgBody.setTitle("工作日志分享提醒");
		msgBody.setContent("标题为："+diary.getTitle()+"的工作日志分享提醒！");
		msgBody.setSendTime(diary.getCreateTime());
		msgBody.setAccount(account2);
		msgBody.setFromAccountId(account.getAccountId());
		msgBody.setFormUserName(userInfo.getUserName());
		msgBody.setRedirectUrl("/app/core/diary/readdiary?diaryId="+diary.getDiaryId());
		msgBody.setOrgId(account.getOrgId());
		msgBodyList.add(msgBody);
	}
	String smsStatus = diary.getMsgType();
	messageUnitService.sendMessage(smsStatus, GobalConstant.MSG_TYPE_DIARY, msgBodyList);
	}
	return insertDiary(diary);
	
}

/**
 * 
 * @Title: delDiary   
 * @Description: TODO 更新工作日志
 * @param: diary
 * @param: @return      
 * @return: int      
 * @throws
 */
public int delDiary(Diary diary)
{
	return diaryMapper.delete(diary);
}

public Diary selectOneDiary(Diary diary)
{
	return diaryMapper.selectOne(diary);
}

public int updateDiary(Account account,UserInfo userInfo,Example example,Diary diary,DiaryPriv diaryPriv)
{
	if(diaryPriv!=null)
	{
		if(diaryPriv.getShareStatus()==0)
		{
			diary.setUserPriv("@all");
			diary.setDeptPriv("");
			diary.setLeavePriv("");
		}else if(diaryPriv.getShareStatus()==1)
		{
			diary.setUserPriv("");
			diary.setDeptPriv(userInfo.getDeptId());
			diary.setLeavePriv("");
		}else if(diaryPriv.getShareStatus()==3){
			diary.setUserPriv("");
			diary.setDeptPriv("");
			diary.setLeavePriv("");
		}
	}else
	{
		diary.setUserPriv("");
		diary.setDeptPriv("");
		diary.setLeavePriv("");
	}
	if(StringUtils.isNotBlank(diary.getMsgType()))
	{
	List<Map<String, String>> userList = userInfoService.getAccountIdInPriv(diary.getOrgId(), diary.getUserPriv(), diary.getDeptPriv(), diary.getLeavePriv());
	List<MsgBody> msgBodyList = new ArrayList<MsgBody>();
	for(int i=0;i<userList.size();i++)
	{
		Account account2 = new Account();
		account2.setAccountId(userList.get(i).toString());
		account2.setOrgId(account.getOrgId());
		account2 = accountService.selectOneAccount(account2);
		MsgBody msgBody = new MsgBody();
		msgBody.setTitle("工作日志分享提醒");
		msgBody.setContent("标题为："+diary.getTitle()+"的工作日志分享提醒！");
		msgBody.setSendTime(diary.getCreateTime());
		msgBody.setAccount(account2);
		msgBody.setFromAccountId(account.getAccountId());
		msgBody.setFormUserName(userInfo.getUserName());
		msgBody.setRedirectUrl("/app/core/diary/readdiary?diaryId="+diary.getDiaryId());
		msgBody.setOrgId(account.getOrgId());
		msgBodyList.add(msgBody);
	}
	String smsStatus = diary.getMsgType();
	messageUnitService.sendMessage(smsStatus, GobalConstant.MSG_TYPE_DIARY, msgBodyList);
	}
	return diaryMapper.updateByExampleSelective(diary, example);
}
/**
 * 
 * @Title: getMyDiaryList   
 * @Description: TODO  获取当前用户历史工作日志
 * @param: orgId
 * @param: accountId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,Object>>      
 * @throws
 */
public List<Map<String, Object>>getMyDiaryList(String orgId,String accountId,String search,String diaryDay)
{
	return diaryMapper.getMyDiaryList(orgId,accountId,"%"+search+"%",diaryDay);
}

/**
 * 
 * @Title: getMyDiaryList   
 * @Description: TODO 获取当前用户历史工作日志
 * @param: pageParam
 * @param: createTime
 * @param: @return      
 * @return: PageInfo<Map<String,Object>>      
 * @throws
 */
public PageInfo<Map<String, Object>> getMyDiaryList(PageParam pageParam,String diaryDay)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,Object>> datalist= getMyDiaryList(pageParam.getOrgId(),pageParam.getAccountId(),pageParam.getSearch(),diaryDay);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}

/**
 * 
* @Title: getOtherDiaryList 
* @Description: TODO 获取他人的工作日志
* @param @param pageNumber
* @param @param pageSize
* @param @param orderBy
* @param @param orgId
* @param @param accountId
* @param @param beginTime
* @param @param endTime
* @param @param search
* @param @return 设定文件 
* @return PageInfo<Map<String,Object>> 返回类型
 */
public PageInfo<Map<String, Object>> getOtherDiaryList(int pageNumber,int pageSize,String orderBy,String orgId, String accountId,String beginTime,String endTime,String search)
{
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String,Object>> datalist= diaryMapper.getOtherDiaryList(orgId, accountId,search,beginTime,endTime);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMySubordinatesDiaryList   
 * @Description: TODO 获取我下属的工作晶志列表   
 * @param: orgId
 * @param: accountId
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getMySubordinatesDiaryList(String orgId,List<String> list,String beginTime,String endTime,String search )
{
	return diaryMapper.getMySubordinatesDiaryList(orgId, list, beginTime, endTime, "%"+search+"%");
}

/**
 * 
 * @Title: getMySubordinatesDiaryList   
 * @Description: TODO 获取我下属的工作晶志列表   
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getMySubordinatesDiaryList(PageParam pageParam,List<String> list,String beginTime,String endTime)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= diaryMapper.getMySubordinatesDiaryList(pageParam.getOrgId(), list,beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getMyDiaryCount   
 * @Description: TODO 获取个人日志总数
 * @param: orgId
 * @param: accountId
 * @param: @return      
 * @return: int      
 * @throws
 */
public int getMyDiaryCount(String orgId,String accountId)
{
	return diaryMapper.getMyDiaryCount(orgId, accountId);
}
/**
 * 
 * @Title: getDiaryCommentCount   
 * @Description: TODO 被他人评论数
 * @param: orgId
 * @param: accountId
 * @param: @return      
 * @return: Integer      
 * @throws
 */
public int getDiaryCommentCount(String orgId,String accountId)
{
	return diaryMapper.getDiaryCommentCount(orgId, accountId);
}

/**
 * 
 * @Title: getMyDiaryInfo   
 * @Description: TODO 获取个人日志信息
 * @param: orgId
 * @param: accountId
 * @param: @return      
 * @return: Map<String,Object>      
 * @throws
 */
public Map<String,Object> getMyDiaryInfo(String orgId,String accountId)
{
	Map<String, Object> tempMap = new HashMap<String, Object>();
	tempMap.put("diaryCount", getMyDiaryCount(orgId, accountId));
	tempMap.put("commToMeCount", getDiaryCommentCount(orgId, accountId));
	tempMap.put("commCount", diaryCommentsService.getMyDiaryCommentsCount(orgId, accountId));
	return tempMap;
}

/**
 * 
 * @Title: getShowDiaryList   
 * @Description: TODO 获取他人分享的工作日志
 * @param: orgId
 * @param: accountId
 * @param: deptId
 * @param: leaveId
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getShowDiaryList(String orgId,String accountId,String deptId,String leaveId,String beginTime,String endTime,String search)
{
	return diaryMapper.getShowDiaryList(orgId, accountId, deptId, leaveId, beginTime, endTime, "%"+search+"%");
}

/**
 * 
 * @Title: getShowDiaryList   
 * @Description: TODO 获取他人分享的工作日志
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getShowDiaryList(PageParam pageParam,String beginTime,String endTime)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getShowDiaryList(pageParam.getOrgId(), pageParam.getAccountId(),pageParam.getDeptId(),pageParam.getLevelId(),beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getHomePageByAccountId   
 * @Description: TODO 获取通讯录的中的个人主页信息
 * @param: orgId
 * @param: accountId
 * @param: @return      
 * @return: JSONObject      
 * @throws
 */
public JSONObject getHomePageByAccountId(String orgId,String accountId)
{
	
	JSONObject returnJson = new JSONObject();
	Map<String, String> userInfo = userInfoService.getAccountAndUserInfo(accountId, orgId);
	returnJson.put("userInfo", userInfo);
	returnJson.put("bpmTotal", bpmListService.getMyBpmCount(orgId,accountId));
	returnJson.put("attachTotal", attachService.getMyAttachCount(orgId, accountId));
	returnJson.put("diaryTotal", getMyDiaryCount(orgId, accountId));
	return returnJson;
}



}
