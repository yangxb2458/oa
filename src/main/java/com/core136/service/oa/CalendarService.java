package com.core136.service.oa;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.oa.Calendar;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.oa.CalendarMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  CalendarService   
 * @Description:TODO 个人日程
 * @author: 稠云信息
 * @date:   2019年2月28日 下午3:18:26   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class CalendarService{
	@Autowired
	private CalendarMapper calendarMapper;
	
	public int insertCalendar(Calendar calendar)
	{
		return calendarMapper.insert(calendar);
	}
	
	public int updateCalendar(Calendar calendar,Example example)
	{
		return calendarMapper.updateByExampleSelective(calendar, example);
	}
	
	public int deleteCalendar(Calendar calendar)
	{
		return calendarMapper.delete(calendar);
	}
	
	public Calendar selectOneCalendar(Calendar calendar)
	{
		return calendarMapper.selectOne(calendar);
	}
	
	public List<Calendar> getTimgingCalendarList(String orgId,String nowTime)
	{
		return calendarMapper.getTimgingCalendarList(orgId,nowTime);
	}
	
	/**
	 * 获取人个当前日程
	 */
	
	public List<Map<String, Object>> getMyCalendarList(String orgId, String accountId, String nowDate) {
		// TODO Auto-generated method stub
		return calendarMapper.getMyCalendarList(orgId, accountId, nowDate);
	}
	
	/**
	 * 
	 * @Title: getAllCalendarList   
	 * @Description: TODO 统计个人日程
	 * @param: orgId
	 * @param: accountId
	 * @param: beginTime
	 * @param: endTime
	 * @param: type
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getAllCalendarList(String orgId,String accountId,String beginTime,String endTime,String type,String search){
		return calendarMapper.getAllCalendarList(orgId, accountId, beginTime, endTime, type, "%"+search+"%");
	}
	
	/**
	 * 
	 * @Title: getAllCalendarList   
	 * @Description: TODO 统计个人日程
	 * @param: pageParam
	 * @param: beginTime
	 * @param: endTime
	 * @param: type
	 * @param: search
	 * @param: @return      
	 * @return: PageInfo<Map<String,String>>      
	 * @throws
	 */
	public PageInfo<Map<String, String>> getAllCalendarList(PageParam pageParam,String beginTime,String endTime,String type) {
		PageHelper.startPage(pageParam.getPageNumber(),pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getAllCalendarList(pageParam.getOrgId(), pageParam.getAccountId(), beginTime, endTime,type, pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	
	/**
	 * 
	 * @Title: getMyDeskCalendarList   
	 * @Description: TODO 获取个人桌面日程列表
	 * @param: orgId
	 * @param: accountId
	 * @param: beginTime
	 * @param: endTime
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getMyCalendarListForDesk(String orgId,String accountId,String beginTime,String endTime)
	{
		return calendarMapper.getMyCalendarListForDesk(orgId, accountId, beginTime, endTime);
	}
	
}
