package com.core136.service.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.oa.CalendarType;
import com.core136.mapper.oa.CalendarTypeMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * 日程分类
 * @author lsq
 *
 */
@Service
public class CalendarTyperService{
@Autowired
private CalendarTypeMapper calendarTypeMapper;
public int insertCalendarType(CalendarType calendarType)
{
	return calendarTypeMapper.insert(calendarType);
}
public int deleteCalendarType(CalendarType calendarType)
{
	return calendarTypeMapper.delete(calendarType);
}
public int updateCalendarType(Example example,CalendarType calendarType)
{
	return calendarTypeMapper.updateByExampleSelective(calendarType, example);
}
public CalendarType selectOneCalendarType(CalendarType calendarType)
{
	return calendarTypeMapper.selectOne(calendarType);
}


}
