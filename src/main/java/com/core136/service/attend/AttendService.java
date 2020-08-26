/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  AttendService.java   
 * @Package com.core136.service.attend   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月15日 上午8:39:32   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.attend;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.core136.bean.attend.Attend;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.attend.AttendMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class AttendService {
@Autowired
private AttendMapper attendMapper;

public int insertAttend(Attend attend)
{
	return attendMapper.insert(attend);
}

public int deleteAttend(Attend attend)
{
	return attendMapper.delete(attend);
}

public int updateAttend(Example example,Attend attend)
{
	return attendMapper.updateByExampleSelective(attend, example);
}

public Attend selectOneAttend(Attend attend)
{
	return attendMapper.selectOne(attend);
}
/**
 * 
 * @Title: getAttendYearList   
 * @Description: TODO 获取年份列表
 * @param: orgId
 * @param: accountId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getAttendYearList(String orgId,String accountId)
{
	return attendMapper.getAttendYearList(orgId, accountId);
}
/**
 * 
 * @Title: getMonthList   
 * @Description: TODO 获取月份
 * @param: orgId
 * @param: year
 * @param: accountId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getMonthList(String orgId,String year,String accountId)
{
	return attendMapper.getMonthList(orgId, year, accountId);
}

/**
 * 
 * @Title: getMyAttendList   
 * @Description: TODO 获取个人的考勤记录
 * @param: orgId
 * @param: year
 * @param: month
 * @param: accountId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getMyAttendList(String orgId,String year,String month,String accountId,String type)
{
	return attendMapper.getMyAttendList(orgId, year, month, accountId,type);
}
/**
 * 
 * @Title: getTotalAttendList   
 * @Description: TODO 考勤统计查询   
 * @param: orgId
 * @param: status
 * @param: beginTime
 * @param: endTime
 * @param: deptId
 * @param: createUser
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getTotalAttendList(String orgId,String type,String beginTime,String endTime,String deptId,String createUser)
{
	return attendMapper.getTotalAttendList(orgId, type, beginTime, endTime, deptId, createUser);
}

/**
 * 
 * @Title: getTotalAttendList   
 * @Description: TODO 考勤统计查询   
 * @param: pageParam
 * @param: status
 * @param: beginTime
 * @param: endTime
 * @param: deptId
 * @param: createUser
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getTotalAttendList(PageParam pageParam,String type,String beginTime,String endTime,String deptId,String createUser)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String, String>> datalist= getTotalAttendList(pageParam.getOrgId(),type,beginTime,endTime,deptId,createUser);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String, String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMyLeaveList   
 * @Description: TODO 获取人员请假列表
 * @param orgId
 * @param accountId
 * @param beginTime
 * @param endTime
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyLeaveList(String orgId,String accountId,String beginTime,String endTime,String type)
{
	return attendMapper.getMyLeaveList(orgId, accountId,beginTime,endTime,type);
}

/**
 * 
 * @Title: getMyLeaveList   
 * @Description: TODO 获取人员请假列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyLeaveList(PageParam pageParam,String beginTime,String endTime,String type)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String, String>> datalist= getMyLeaveList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime,type);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String, String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getMyTravelList   
 * @Description: TODO 获取出差列表
 * @param orgId
 * @param accountId
 * @param beginTime
 * @param endTime
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyTravelList(String orgId,String accountId,String beginTime,String endTime)
{
	return attendMapper.getMyTravelList(orgId, accountId,beginTime,endTime);
}

/**
 * 
 * @Title: getMyTravelList   
 * @Description: TODO 获取出差列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyTravelList(PageParam pageParam,String beginTime,String endTime)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String, String>> datalist= getMyTravelList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String, String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMyOutattendList   
 * @Description: TODO 获取外出列表
 * @param orgId
 * @param accountId
 * @param beginTime
 * @param endTime
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyOutattendList(String orgId,String accountId,String beginTime,String endTime)
{
	return attendMapper.getMyOutattendList(orgId, accountId,beginTime,endTime);
}
/**
 * 
 * @Title: getMyOutattendList   
 * @Description: TODO 获取外出列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyOutattendList(PageParam pageParam,String beginTime,String endTime)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String, String>> datalist= getMyOutattendList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String, String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getMyOverTimeList   
 * @Description: TODO 获取加班列表
 * @param orgId
 * @param accountId
 * @param beginTime
 * @param endTime
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyOverTimeList(String orgId,String accountId,String beginTime,String endTime)
{
	return attendMapper.getMyOverTimeList(orgId, accountId,beginTime,endTime);
}

/**
 * 
 * @Title: getMyOverTimeList   
 * @Description: TODO 获取加班列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyOverTimeList(PageParam pageParam,String beginTime,String endTime)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String, String>> datalist= getMyOverTimeList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String, String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMyDutyList   
 * @Description: TODO获取值班列表
 * @param orgId
 * @param accountId
 * @param beginTime
 * @param endTime
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyDutyList(String orgId,String accountId,String beginTime,String endTime)
{
	return attendMapper.getMyDutyList(orgId, accountId,beginTime,endTime);
}
/**
 * 
 * @Title: getMyDutyList   
 * @Description: TODO 获取值班列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyDutyList(PageParam pageParam,String beginTime,String endTime)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String, String>> datalist= getMyDutyList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String, String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMyAllAttendList   
 * @Description: TODO 获取个人一年内的考勤记录
 * @param: orgId
 * @param: year
 * @param: accountId
 * @param: @return      
 * @return: JSONObject      
 * @throws
 */
public JSONObject getMyAllAttendList(String orgId,String year,String accountId,String type)
{
	JSONObject json = new JSONObject();
	for(int i=1;i<=12;i++)
	{
		if(i<10)
		{
			json.put(i+"", getMyAttendList(orgId,year,"0"+i,accountId,type));
		}else
		{
		json.put(i+"", getMyAttendList(orgId,year,i+"",accountId,type));
		}
	}
	return json;
}



}
