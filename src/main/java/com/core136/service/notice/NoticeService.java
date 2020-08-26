package com.core136.service.notice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.notice.Notice;
import com.core136.bean.notice.NoticeConfig;
import com.core136.bean.sys.MsgBody;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.notice.NoticeMapper;
import com.core136.service.account.AccountService;
import com.core136.service.sys.MessageUnitService;

import org.core136.common.enums.GobalConstant;
import org.core136.common.utils.SysTools;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class NoticeService{
@Autowired
private NoticeMapper noticeMapper;
@Autowired
private NoticeConfigService noticeConfigService;
@Autowired
private MessageUnitService messageUnitService;
@Autowired
private AccountService accountService;

public int insertNotice(Notice notice)
{
	return noticeMapper.insert(notice);
}

public int deleteNotice(Notice notice)
{
	return noticeMapper.delete(notice);
}

public int updateNotice(Notice notice,Example example)
{
	return noticeMapper.updateByExampleSelective(notice, example);
}

public int reEditNotice(UserInfo userinfo,Notice notice,Example example)
{
	NoticeConfig noticeConfig0 = new NoticeConfig();
	noticeConfig0.setOrgId(notice.getOrgId());
	noticeConfig0.setApproverType("0");
	noticeConfig0.setNoticeType(notice.getNoticeType());
	noticeConfig0 =noticeConfigService.selectOneNoticeConfig(noticeConfig0);
	
	NoticeConfig noticeConfig1 = new NoticeConfig();
	noticeConfig1.setOrgId(notice.getOrgId());
	noticeConfig1.setApproverType("1");
	noticeConfig1.setNoticeType(notice.getNoticeType());
	noticeConfig1 =noticeConfigService.selectOneNoticeConfig(noticeConfig1);
	
	String createUser = userinfo.getAccountId();
	if(StringUtils.isNotBlank(noticeConfig1.getApprover()))
	{
		if((","+noticeConfig1.getApprover()+",").indexOf(","+createUser+",")<0)
		{
			if(StringUtils.isNotBlank(noticeConfig0.getApprover()))
			{
				notice.setApprovalStatus("0");
				notice.setStatus("0");
			}else
			{
				notice.setStatus("1");
			}
		}else
		{
			notice.setStatus("1");
		}
	}else
	{
		if(StringUtils.isNotBlank(noticeConfig0.getApprover()))
		{
			notice.setApprovalStatus("0");
			notice.setStatus("0");
		}else
		{
			notice.setStatus("1");
		}
	}
	
	return noticeMapper.updateByExampleSelective(notice, example);
}

public Notice selectOneNotice(Notice notice)
{
	return noticeMapper.selectOne(notice);
}
/**
 * 
* @Title: getNoticeManageList 
* @Description: TODO 获取通知公告的维护列表
* @param @param orgId
* @param @param opFlag
* @param @param accountId
* @param @param search
* @param @return 设定文件 
* @return List<Map<String,Object>> 返回类型
 */
public List<Map<String, Object>> getNoticeManageList(String orgId, String opFlag, 
		String accountId,String search,String notictType,String beginTime,String endTime) {
	return noticeMapper.getNoticeManageList(orgId, opFlag, accountId,search,notictType,beginTime,endTime);
}


/**
 * 
* @Title: getNoticeApproverList 
* @Description: TODO 获取审批列表
* @param @param orgId
* @param @param accountId
* @param @param search
* @param @return 设定文件 
* @return List<Map<String,Object>> 返回类型
 */
public List<Map<String, Object>> getNoticeApproverList(String orgId,String accountId,String search) {
	return noticeMapper.getNoticeApproverList(orgId, accountId,"%"+search+"%");
}
/**
 * 
 * @Title: getNoticeApproverList   
 * @Description: TODO 获取审批列表
 * @param: pageParam
 * @param: @return      
 * @return: PageInfo<Map<String,Object>>      
 * @throws
 */
public PageInfo<Map<String, Object>> getNoticeApproverList(PageParam pageParam) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,Object>> datalist= getNoticeApproverList(pageParam.getOrgId(), pageParam.getAccountId(),pageParam.getSearch());
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getNoticeManageList   
 * @Description: TODO 获取通知公告的维护列表  
 * @param: pageParam
 * @param: noticeType
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,Object>>      
 * @throws
 */
	public PageInfo<Map<String, Object>> getNoticeManageList(PageParam pageParam,String noticeType,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,Object>> datalist= getNoticeManageList(pageParam.getOrgId(), pageParam.getOpFlag(), pageParam.getAccountId(),
			"%"+pageParam.getSearch()+"%",noticeType,beginTime,endTime);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}


/**
 * 
* @Title: sendNotice 
* @Description: TODO 按权限发布通知公告
* @param @param notice
* @param @return 设定文件 
* @return int 返回类型
 */
public int sendNotice(Notice notice,UserInfo userInfo)
{
	NoticeConfig noticeConfig0 = new NoticeConfig();
	noticeConfig0.setOrgId(notice.getOrgId());
	noticeConfig0.setApproverType("0");
	noticeConfig0.setNoticeType(notice.getNoticeType());
	noticeConfig0 =noticeConfigService.selectOneNoticeConfig(noticeConfig0);
	
	NoticeConfig noticeConfig1 = new NoticeConfig();
	noticeConfig1.setOrgId(notice.getOrgId());
	noticeConfig1.setApproverType("1");
	noticeConfig1.setNoticeType(notice.getNoticeType());
	noticeConfig1 =noticeConfigService.selectOneNoticeConfig(noticeConfig1);
	
	String createUser = notice.getCreateUser();
	if(StringUtils.isNotBlank(noticeConfig1.getApprover()))
	{
		if((","+noticeConfig1.getApprover()+",").indexOf(","+createUser+",")<0)
		{
			if(StringUtils.isNotBlank(noticeConfig0.getApprover()))
			{
				notice.setApprovalStatus("0");
				notice.setStatus("0");
			}else
			{
				notice.setStatus("1");
			}
		}else
		{
			notice.setStatus("1");
		}
	}else
	{
		if(StringUtils.isNotBlank(noticeConfig0.getApprover()))
		{
			notice.setApprovalStatus("0");
			notice.setStatus("0");
		}else
		{
			notice.setStatus("1");
		}
	}
	int flag = insertNotice(notice);
	if(flag>0)
	{
		if(notice.getStatus().equals("1"))
		{
			if(StringUtils.isNotBlank(notice.getMsgType()))
			{
				List<MsgBody> msgBodyList = new ArrayList<MsgBody>();
				List<Account> accountList = accountService.getAccountInPriv(notice.getOrgId(), notice.getUserPriv(), notice.getDeptPriv(), notice.getLeavePriv());
				for(int i=0;i<accountList.size();i++)
				{
					MsgBody msgBody = new MsgBody();
					msgBody.setFromAccountId(notice.getCreateUser());
					msgBody.setFormUserName(userInfo.getUserName());
					msgBody.setTitle(notice.getNoticeTitle());
					msgBody.setContent(notice.getSubheading());
					msgBody.setSendTime(notice.getCreateTime());
					msgBody.setAccount(accountList.get(i));
					msgBody.setRedirectUrl("/mobile/notice/details?noticeId="+notice.getNoticeId());
					msgBody.setAttach(notice.getAttach());
					msgBody.setOrgId(notice.getOrgId());
					msgBodyList.add(msgBody);
				}
				messageUnitService.sendMessage(notice.getMsgType(), GobalConstant.MSG_TYPE_NOTICE, msgBodyList);
			}
		}
	}
	return flag;
}
/**
 * 
* @Title: addReadUser 
* @Description: TODO 添加查看人
* @param @param account 设定文件 
* @return void 返回类型
 */
public void addReadUser(Account account,Notice notice)
{
	String readuser = notice.getReader();
	List<String> list = new ArrayList<String>();
	if(StringUtils.isNotBlank(readuser))
	{
		String[] userArr;
		if(readuser.indexOf(",")>0)
		{
			userArr =  readuser.split(",");
		}else
		{
			userArr = new String[] {readuser};
		}
		for(int i=0;i<userArr.length;i++)
		{
			if(StringUtils.isNotBlank(userArr[i]))
			{
				list.add(userArr[i]);
			}
		}
	}
	list.add(account.getAccountId());
	HashSet<String> h = new HashSet<String>(list);   
    list.clear();   
    list.addAll(h);
	String join = String.join(",",list);
	notice.setReader(join);
	Example example = new Example(Notice.class);
	example.createCriteria().andEqualTo("orgId",notice.getOrgId()).andEqualTo("noticeId",notice.getNoticeId());
	updateNotice(notice, example);
}

/**
 * 
* @Title: setOnClickCount 
* @Description: TODO 设置查看次数
* @param @param orgId
* @param @param noticeId
* @param @return 设定文件 
* @return int 返回类型
 */
public int setOnClickCount(String orgId,String noticeId)
{
	return noticeMapper.setOnClickCount(orgId, noticeId);
}
/**
 * 
* @Title: getNoticeInfo 
* @Description: TODO 获取通知公告内容
* @param @param account
* @param @param notice
* @param @return 设定文件 
* @return Notice 返回类型
 */
@Transactional(value="generalTM")
public Notice getNoticeInfo(Account account,Notice notice)
{
	notice = selectOneNotice(notice);
	addReadUser(account,notice);
	setOnClickCount(account.getOrgId(),notice.getNoticeId());
	notice.setOrgId("");
	return notice;
}

/**
 * 
* @Title: isNoSendStatus 
* @Description: TODO 判断是否有效
* @param @param orgId
* @param @param noticeId
* @param @param endTime
* @param @return 设定文件 
* @return int 返回类型
 */
public int isNoSendStatus(String orgId,String noticeId,String endTime)
{
	return noticeMapper.isNoSendStatus(orgId, noticeId, endTime);
}
/**
 * 
* @Title: deleteNoticeStrong 
* @Description: TODO 强力删除通知公告
* @param @param notice
* @param @return 设定文件 
* @return int 返回类型
 */
public int deleteNoticeStrong(Notice notice)
{
	return noticeMapper.delete(notice);
}

/**
 * 
* @Title: deleteNotice 
* @Description: TODO 删除工作日志
* @param @param orgId
* @param @param noticeId
* @param @return 设定文件 
* @return int 返回类型
 */
public int deleteNotice(String orgId,String noticeId)
{
	String endTime = SysTools.getTime("yyyy-MM-dd");
	if(isNoSendStatus(orgId,noticeId,endTime)==1)
	{
		Notice notice = new Notice();
		notice.setOrgId(orgId);
		notice.setNoticeId(noticeId);
		return noticeMapper.delete(notice);
	}else
	{
		return 0;
	}
}
/**
 * 
* @Title: getMyNoticeList 
* @Description: TODO 获取个人的通知公告
* @param @param orgId
* @param @param accountId
* @param @param deptId
* @param @param leaveId
* @param @param noticeType
* @param @param beginTime
* @param @param endTime
* @param @param search
* @param @return 设定文件 
* @return List<Map<String,String>> 返回类型
 */
public List<Map<String,String>> getMyNoticeList(String orgId,String accountId, String deptId,String leaveId,String noticeType, String beginTime,
		String endTime, String readStatus,String search
		)
{
	return noticeMapper.getMyNoticeList(orgId, accountId, deptId, leaveId, noticeType, beginTime, endTime,readStatus, "%"+search+"%");
}

/**
 * 获取个人的通知公告
 */
public PageInfo<Map<String, String>> getMyNoticeList(PageParam pageParam,String noticeType, String beginTime,String endTime,String readStatus) {
	
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= noticeMapper.getMyNoticeList(pageParam.getOrgId(), pageParam.getAccountId(), pageParam.getDeptId(), pageParam.getLevelId(), noticeType, beginTime, endTime,readStatus, pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMyNoticeListForDesk   
 * @Description: TODO 获取桌面的通知消息
 * @param: orgId
 * @param: endTime
 * @param: accountId
 * @param: deptId
 * @param: leaveId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getMyNoticeListForDesk(String orgId,String endTime,String accountId,String deptId,String leaveId)
{
	return noticeMapper.getMyNoticeListForDesk(orgId,endTime, accountId, deptId, leaveId);
}

/**
 * 
 * @Title: getMobileMyNoticeList   
 * @Description: TODO 移动端下滑加载更多
 * @param: orgId
 * @param: accountId
 * @param: deptId
 * @param: leaveId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getMobileMyNoticeList(String orgId,String accountId,String deptId,String leaveId,Integer page)
{
	return noticeMapper.getMobileMyNoticeList(orgId, accountId, deptId, leaveId,page);
}




}
