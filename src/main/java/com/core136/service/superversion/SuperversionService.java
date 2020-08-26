/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  SuperversionService.java   
 * @Package com.core136.service.superversion   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月28日 下午4:44:21   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.superversion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.core136.common.enums.GobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.superversion.Superversion;
import com.core136.bean.sys.MsgBody;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.superversion.SuperversionMapper;
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
public class SuperversionService {
@Autowired
private SuperversionMapper superversionMapper;
@Autowired
private AccountService accountService;
@Autowired
private MessageUnitService messageUnitService;

public int insertSuperversion(Superversion superversion)
{
	return superversionMapper.insert(superversion);
}



public int deleteSuperversion(Superversion superversion)
{
	return superversionMapper.delete(superversion);
}
public int updateSuperversion(Example example,Superversion superversion)
{
	return superversionMapper.updateByExampleSelective(superversion, example);
}

public Superversion selectOneSuperversion(Superversion superversion)
{
	return superversionMapper.selectOne(superversion);
}

/**
 * 
 * @Title: createSuperversion   
 * @Description: TODO 创建督查督办
 * @param account
 * @param userInfo
 * @param superversion
 * @return
 * int    
 * @throws
 */
public int createSuperversion(Account account,UserInfo userInfo,Superversion superversion)
{
	if(StringUtils.isNotBlank(superversion.getMsgType()))
	{
		String leadId = superversion.getLeadId();
		String handedUser = superversion.getHandedUser();
		String joinUser = superversion.getJoinUser();
		List<String> userList =new ArrayList<String>();
		List<String> arr2 =new ArrayList<String>();
		List<String> arr3 =new ArrayList<String>();
		if(StringUtils.isNotBlank(leadId))
		{
			userList = new ArrayList<String>(Arrays.asList(leadId.split(",")));
		}
		if(StringUtils.isNotBlank(handedUser))
		{
			arr2 = new ArrayList<String>(Arrays.asList(handedUser.split(",")));
		}
		if(StringUtils.isNotBlank(joinUser))
		{
			arr3 = new ArrayList<String>(Arrays.asList(joinUser.split(",")));
		}
		userList.addAll(arr2);
		userList.addAll(arr3);
		Set<String> set = new HashSet<String>();
		set.addAll(userList);     // 将list所有元素添加到set中    set集合特性会自动去重复
		userList.clear();
		userList.addAll(set);
		List<MsgBody> msgBodyList = new ArrayList<MsgBody>();
		for(int i=0;i<userList.size();i++)
		{
			Account account2 = new Account();
			account2.setAccountId(userList.get(i).toString());
			account2.setOrgId(account.getOrgId());
			account2 = accountService.selectOneAccount(account2);
			MsgBody msgBody = new MsgBody();
			msgBody.setTitle("督查督办提醒");
			msgBody.setContent("任务标题为："+superversion.getTitle()+"的查看提醒！");
			msgBody.setSendTime(superversion.getCreateTime());
			msgBody.setAccount(account2);
			msgBody.setFromAccountId(account.getAccountId());
			msgBody.setFormUserName(userInfo.getUserName());
			msgBody.setRedirectUrl("/app/core/superversion/superversiondetails?superversionId=="+superversion.getSuperversionId());
			msgBody.setOrgId(account.getOrgId());
			msgBodyList.add(msgBody);
		}
		String smsStatus = superversion.getMsgType();
		messageUnitService.sendMessage(smsStatus, GobalConstant.MSG_TYPE_SUPERVERSION, msgBodyList);
		}
	return superversionMapper.insert(superversion);
}


public int updateSuperversion(Account account,UserInfo userInfo,Example example,Superversion superversion)
{
	if(StringUtils.isNotBlank(superversion.getMsgType()))
	{
		String leadId = superversion.getLeadId();
		String handedUser = superversion.getHandedUser();
		String joinUser = superversion.getJoinUser();
		List<String> userList =new ArrayList<String>();
		List<String> arr2 =new ArrayList<String>();
		List<String> arr3 =new ArrayList<String>();
		if(StringUtils.isNotBlank(leadId))
		{
			userList = new ArrayList<String>(Arrays.asList(leadId.split(",")));
		}
		if(StringUtils.isNotBlank(handedUser))
		{
			arr2 = new ArrayList<String>(Arrays.asList(handedUser.split(",")));
		}
		if(StringUtils.isNotBlank(joinUser))
		{
			arr3 = new ArrayList<String>(Arrays.asList(joinUser.split(",")));
		}
		userList.addAll(arr2);
		userList.addAll(arr3);
		Set<String> set = new HashSet<String>();
		set.addAll(userList);     // 将list所有元素添加到set中    set集合特性会自动去重复
		userList.clear();
		userList.addAll(set);
		List<MsgBody> msgBodyList = new ArrayList<MsgBody>();
		for(int i=0;i<userList.size();i++)
		{
			Account account2 = new Account();
			account2.setAccountId(userList.get(i).toString());
			account2.setOrgId(account.getOrgId());
			account2 = accountService.selectOneAccount(account2);
			MsgBody msgBody = new MsgBody();
			msgBody.setTitle("督查督办提醒");
			msgBody.setContent("任务标题为："+superversion.getTitle()+"的查看提醒！");
			msgBody.setSendTime(superversion.getCreateTime());
			msgBody.setAccount(account2);
			msgBody.setFromAccountId(account.getAccountId());
			msgBody.setFormUserName(userInfo.getUserName());
			msgBody.setRedirectUrl("/app/core/superversion/superversiondetails?superversionId=="+superversion.getSuperversionId());
			msgBody.setOrgId(account.getOrgId());
			msgBodyList.add(msgBody);
		}
		String smsStatus = superversion.getMsgType();
		messageUnitService.sendMessage(smsStatus, GobalConstant.MSG_TYPE_SUPERVERSION, msgBodyList);
		}
	return superversionMapper.updateByExampleSelective(superversion, example);
}
/**
 * 
 * @Title: getSupperversionList   
 * @Description: TODO 我的督查列表
 * @param: orgId
 * @param: accountId
 * @param: type
 * @param: handedUser
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getSupperversionList(String orgId,String accountId,String type,String handedUser,String beginTime,String endTime,String status,String search)
{
	return superversionMapper.getSupperversionList(orgId, accountId, type, handedUser, beginTime, endTime,status, "%"+search+"%");
}
/**
 * 
 * @Title: getLeadManageSupperversionList   
 * @Description: TODO 获取当前用户管控的事件列表
 * @param: orgId
 * @param: accountId
 * @param: type
 * @param: handedUser
 * @param: beginTime
 * @param: endTime
 * @param: status
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getLeadManageSupperversionList(String orgId,String accountId,String type,String handedUser,String beginTime,String endTime,String status,String search)
{
	return superversionMapper.getLeadManageSupperversionList(orgId, accountId, type, handedUser, beginTime, endTime,status, "%"+search+"%");
}

/**
 * 
 * @Title: getLeadManageSupperversionList   
 * @Description: TODO 获取当前用户管控的事件列表
 * @param: pageParam
 * @param: type
 * @param: handedUser
 * @param: beginTime
 * @param: endTime
 * @param: status
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getLeadManageSupperversionList(PageParam pageParam,String type,String handedUser,String beginTime,String endTime,String status) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getLeadManageSupperversionList(pageParam.getOrgId(),pageParam.getAccountId(),type,handedUser,beginTime,endTime,status,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getSupperversionList   
 * @Description: TODO 我的督查列表  
 * @param: pageParam
 * @param: type
 * @param: handedUser
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getSupperversionList(PageParam pageParam,String type,String handedUser,String beginTime,String endTime,String status) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getSupperversionList(pageParam.getOrgId(),pageParam.getAccountId(),type,handedUser,beginTime,endTime,status,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}


/**
 * 
 * @Title: getSupperversionPorcessList   
 * @Description: TODO 获取待处理的督查列表
 * @param: orgId
 * @param: accountId
 * @param: type
 * @param: handedUser
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getSupperversionPorcessList(String orgId,String accountId,String type,String handedUser,String beginTime,String endTime,String status,String search)
{
	return superversionMapper.getSupperversionPorcessList(orgId, accountId, type, handedUser, beginTime, endTime, status,"%"+search+"%");
}

/**
 * 
 * @Title: getSupperversionPorcessList   
 * @Description: TODO 获取待处理的督查列表
 * @param: pageParam
 * @param: type
 * @param: handedUser
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getSupperversionPorcessList(PageParam pageParam,String type,String handedUser,String beginTime,String endTime,String status) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getSupperversionPorcessList(pageParam.getOrgId(),pageParam.getAccountId(),type,handedUser,beginTime,endTime,status,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getQuerySuperversionForDept   
 * @Description: TODO按部门汇总
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getQuerySuperversionForDept(String orgId)
{
	return superversionMapper.getQuerySuperversionForDept(orgId);
}

}
