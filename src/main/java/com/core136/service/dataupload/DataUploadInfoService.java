package com.core136.service.dataupload;

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
import com.core136.bean.dataupload.DataUploadInfo;
import com.core136.bean.sys.MsgBody;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.dataupload.DataUploadInfoMapper;
import com.core136.service.account.AccountService;
import com.core136.service.sys.MessageUnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class DataUploadInfoService {
@Autowired
private DataUploadInfoMapper dataUploadInfoMapper;
@Autowired
private MessageUnitService messageUnitService;
@Autowired
private AccountService accountService;

public int insertDataUploadInfo(DataUploadInfo dataUploadInfo)
{
	return dataUploadInfoMapper.insert(dataUploadInfo);
}

public int deleteDataUploadInfo(DataUploadInfo dataUploadInfo)
{
	return dataUploadInfoMapper.delete(dataUploadInfo);
}

public int updateDataUploadInfo(Example example,DataUploadInfo dataUploadInfo)
{
	return dataUploadInfoMapper.updateByExampleSelective(dataUploadInfo, example);
}

public int updateDataUploadInfo(Account account,UserInfo userInfo,Example example,DataUploadInfo dataUploadInfo)
{
	if(StringUtils.isNotBlank(dataUploadInfo.getMsgType()))
	{
		String toUser = dataUploadInfo.getToUser();
		String approvedUser = dataUploadInfo.getApprovedUser();
		List<String> userList =new ArrayList<String>();
		List<String> arr2 =new ArrayList<String>();
		if(StringUtils.isNotBlank(toUser))
		{
			userList = new ArrayList<String>(Arrays.asList(toUser.split(",")));
		}
		if(StringUtils.isNotBlank(approvedUser))
		{
			arr2 = new ArrayList<String>(Arrays.asList(approvedUser.split(",")));
		}
		userList.addAll(arr2);
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
			msgBody.setTitle("数据上报");
			msgBody.setContent("上报标题为："+dataUploadInfo.getTitle()+"的查看提醒！");
			msgBody.setSendTime(dataUploadInfo.getCreateTime());
			msgBody.setAccount(account2);
			msgBody.setFromAccountId(account.getAccountId());
			msgBody.setFormUserName(userInfo.getUserName());
			msgBody.setRedirectUrl("/app/core/dataupload/uploadinfodetails?recordId="+dataUploadInfo.getRecordId());
			msgBody.setOrgId(account.getOrgId());
			msgBodyList.add(msgBody);
		}
		String smsStatus = dataUploadInfo.getMsgType();
		messageUnitService.sendMessage(smsStatus, GobalConstant.MSG_TYPE_DATA_UPLOAD, msgBodyList);
		}
	return dataUploadInfoMapper.updateByExampleSelective(dataUploadInfo, example);
}

public DataUploadInfo selectOneDataUploadInfo(DataUploadInfo dataUploadInfo)
{
	return dataUploadInfoMapper.selectOne(dataUploadInfo);
}

/**
 * 
 * @Title: dataUploadInfo   
 * @Description: TODO 信息上报
 * @param account
 * @param userInfo
 * @param dataUploadInfo
 * @return
 * int    
 * @throws
 */
public int dataUploadInfo(Account account,UserInfo userInfo,DataUploadInfo dataUploadInfo)
{
	
	if(StringUtils.isNotBlank(dataUploadInfo.getMsgType()))
	{
		String toUser = dataUploadInfo.getToUser();
		String approvedUser = dataUploadInfo.getApprovedUser();
		List<String> userList =new ArrayList<String>();
		List<String> arr2 =new ArrayList<String>();
		if(StringUtils.isNotBlank(toUser))
		{
			userList = new ArrayList<String>(Arrays.asList(toUser.split(",")));
		}
		if(StringUtils.isNotBlank(approvedUser))
		{
			arr2 = new ArrayList<String>(Arrays.asList(approvedUser.split(",")));
		}
		userList.addAll(arr2);
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
			msgBody.setTitle("数据上报");
			msgBody.setContent("上报标题为："+dataUploadInfo.getTitle()+"的查看提醒！");
			msgBody.setSendTime(dataUploadInfo.getCreateTime());
			msgBody.setAccount(account2);
			msgBody.setFromAccountId(account.getAccountId());
			msgBody.setFormUserName(userInfo.getUserName());
			msgBody.setRedirectUrl("/app/core/dataupload/uploadinfodetails?recordId="+dataUploadInfo.getRecordId());
			msgBody.setOrgId(account.getOrgId());
			msgBodyList.add(msgBody);
		}
		String smsStatus = dataUploadInfo.getMsgType();
		messageUnitService.sendMessage(smsStatus, GobalConstant.MSG_TYPE_DATA_UPLOAD, msgBodyList);
		}
	return dataUploadInfoMapper.insert(dataUploadInfo);
}

/**
 * 
 * @Title: getDataUploadInfoList   
 * @Description: TODO 获取上报信息列表
 * @param orgId
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dataType
 * @param approvedType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getDataUploadInfoList(String orgId,String deptId,String fromAccountId,String beginTime,String endTime,String dataType,String approvedType,String search)
{
	return dataUploadInfoMapper.getDataUploadInfoList(orgId, deptId, fromAccountId, beginTime, endTime, dataType, approvedType, "%"+search+"%");
}

/**
 * 
 * @Title: getDataUploadInfoList   
 * @Description: TODO 获取上报信息列表
 * @param pageParam
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dataType
 * @param approvedType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getDataUploadInfoList(PageParam pageParam,String deptId,String fromAccountId,String beginTime,String endTime,String dataType,String approvedType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getDataUploadInfoList(pageParam.getOrgId(),deptId,fromAccountId, beginTime, endTime, dataType,approvedType,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getToProcessInfoList   
 * @Description: TODO 获取持处理的信息列表
 * @param orgId
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dataType
 * @param approvedType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getToProcessInfoList(String orgId,String accountId,String deptId,String fromAccountId,String beginTime,String endTime,String dataType,String approvedType,String search)
{
	return dataUploadInfoMapper.getToProcessInfoList(orgId,accountId, deptId, fromAccountId, beginTime, endTime, dataType, approvedType, "%"+search+"%");
}

/**
 * 
 * @Title: getToProcessInfoList   
 * @Description: TODO 获取持处理的信息列表
 * @param pageParam
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dataType
 * @param approvedType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getToProcessInfoList(PageParam pageParam,String deptId,String fromAccountId,String beginTime,String endTime,String dataType,String approvedType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getToProcessInfoList(pageParam.getOrgId(),pageParam.getAccountId(),deptId,fromAccountId, beginTime, endTime, dataType,approvedType,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getOldProcessInfoList   
 * @Description: TODO 信息处理历史列表
 * @param orgId
 * @param accountId
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dataType
 * @param approvedType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getOldProcessInfoList(String orgId,String accountId,String deptId,String fromAccountId,String beginTime,String endTime,String dataType,String approvedType,String search)
{
	return dataUploadInfoMapper.getOldProcessInfoList(orgId,accountId, deptId, fromAccountId, beginTime, endTime, dataType, approvedType, "%"+search+"%");
}
/**
 * 
 * @Title: getOldProcessInfoList   
 * @Description: TODO 信息处理历史列表
 * @param pageParam
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dataType
 * @param approvedType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getOldProcessInfoList(PageParam pageParam,String deptId,String fromAccountId,String beginTime,String endTime,String dataType,String approvedType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getOldProcessInfoList(pageParam.getOrgId(),pageParam.getAccountId(),deptId,fromAccountId, beginTime, endTime, dataType,approvedType,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
