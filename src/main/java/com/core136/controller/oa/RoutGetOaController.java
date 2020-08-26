/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetOaController.java   
 * @Package com.core136.controller.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 刘绍全     
 * @date:   2019年1月14日 下午7:55:10   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.oa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.attend.AttendConfig;
import com.core136.bean.oa.Calendar;
import com.core136.bean.oa.Diary;
import com.core136.bean.oa.DiaryPriv;
import com.core136.bean.oa.LeadActivity;
import com.core136.bean.oa.News;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.attend.AttendConfigService;
import com.core136.service.attend.AttendService;
import com.core136.service.oa.CalendarService;
import com.core136.service.oa.DiaryCommentsService;
import com.core136.service.oa.DiaryPrivService;
import com.core136.service.oa.DiaryService;
import com.core136.service.oa.LeadActivityService;
import com.core136.service.oa.NewsCommentsService;
import com.core136.service.oa.NewsService;
import com.core136.service.sys.MobileSmsService;
import com.core136.service.sys.SmsService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import org.core136.common.utils.SysTools;
import com.github.pagehelper.PageInfo;

/**   
 * @ClassName:  RoutGetOaController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 刘绍全 
 * @date:   2019年1月14日 下午7:55:10   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云科信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/ret/oaget")
public class RoutGetOaController {
@Autowired
private NewsService newsService;
@Autowired
private NewsCommentsService newsCommentsService;
@Autowired
private SmsService smsService;
@Autowired
private CalendarService calendarService;
@Autowired
private DiaryPrivService diaryPrivService;
@Autowired
private DiaryService diaryService;
@Autowired
private DiaryCommentsService diaryCommentsService;
@Autowired
private AttendConfigService attendConfigService;
@Autowired
private AttendService attendService;
@Autowired
private AccountService accountservice;
@Autowired
private MobileSmsService mobileSmsService;
@Autowired
private LeadActivityService leadActivityService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: getLeadActivityLsit   
 * @Description: TODO 获取领导行程列表
 * @param request
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param leader
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getLeadActivityLsit",method=RequestMethod.POST)
public RetDataBean getLeadActivityLsit(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String leader
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("DESC");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setOpFlag(account.getOpFlag());
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	pageParam.setOrderBy(orderBy);
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=leadActivityService.getLeadActivityLsit(pageParam, beginTime, endTime, leader);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getLeadActivityById   
 * @Description: TODO 获取领导行程详情
 * @param request
 * @param leadActivity
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getLeadActivityById",method=RequestMethod.POST)
public RetDataBean getLeadActivityById(HttpServletRequest request,LeadActivity leadActivity)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		leadActivity.setOrgId(account.getOrgId());
		return RetDataTools.Ok("数据请求成功!",leadActivityService.selectOneLeadActivity(leadActivity));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getMyDiaryInfo   
 * @Description: TODO 获取个人日志信息   
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getMyDiaryInfo",method=RequestMethod.POST)
public RetDataBean getMyDiaryInfo(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", diaryService.getMyDiaryInfo(account.getOrgId(),account.getAccountId()));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getDiaryCommentsList   
 * @Description: TODO 获取日志评论
 * @param: request
 * @param: diaryId
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getDiaryCommentsList",method=RequestMethod.POST)
public RetDataBean getDiaryCommentsList(HttpServletRequest request,String diaryId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", diaryCommentsService.getDiaryCommentsList(account.getOrgId(),diaryId));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getCommentsList   
 * @Description: TODO 获取新闻下的所有评论
 * @param: request
 * @param: newsId
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getCommentsList",method=RequestMethod.POST)
public RetDataBean getCommentsList(HttpServletRequest request,String newsId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", newsCommentsService.getCommentsList(account.getOrgId(),newsId));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getTotalAttendList   
 * @Description: TODO 获取考勤列表
 * @param: request
 * @param: pageParam
 * @param: type
 * @param: beginTime
 * @param: endTime
 * @param: deptId
 * @param: createUser
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getTotalAttendList",method=RequestMethod.POST)
public RetDataBean getTotalAttendList(
		HttpServletRequest request,
		PageParam pageParam,
		String type,
		String beginTime,
		String endTime,
		String deptId,
		String createUser
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("DESC");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setOpFlag(account.getOpFlag());
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	pageParam.setOrderBy(orderBy);
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=attendService.getTotalAttendList(pageParam, type, beginTime, endTime, deptId,createUser);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyTravelList   
 * @Description: TODO 获取出差列表
 * @param request
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyTravelList",method=RequestMethod.POST)
public RetDataBean getMyTravelList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("R.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("DESC");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setOpFlag(account.getOpFlag());
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=attendService.getMyTravelList(pageParam,beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getMyOutattendList   
 * @Description: TODO 获取外出列表
 * @param request
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyOutattendList",method=RequestMethod.POST)
public RetDataBean getMyOutattendList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("R.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("DESC");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setOpFlag(account.getOpFlag());
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=attendService.getMyOutattendList(pageParam,beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyOverTimeList   
 * @Description: TODO 获取加班列表
 * @param request
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyOverTimeList",method=RequestMethod.POST)
public RetDataBean getMyOverTimeList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("R.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("DESC");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setOpFlag(account.getOpFlag());
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=attendService.getMyOverTimeList(pageParam,beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyDutyList   
 * @Description: TODO 获取值班列表
 * @param request
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyDutyList",method=RequestMethod.POST)
public RetDataBean getMyDutyList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("R.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("DESC");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setOpFlag(account.getOpFlag());
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=attendService.getMyDutyList(pageParam,beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyLeaveList   
 * @Description: TODO 获取人员请假列表
 * @param request
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getMyLeaveList",method=RequestMethod.POST)
public RetDataBean getMyLeaveList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String type
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("R.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("DESC");
		}
		
	Account account=accountService.getRedisAccount(request);
	pageParam.setOpFlag(account.getOpFlag());
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=attendService.getMyLeaveList(pageParam,beginTime, endTime,type);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getShowDiaryList   
 * @Description: TODO获取他人分享的工作日志
 * @param: request
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getShowDiaryList",method=RequestMethod.POST)
public RetDataBean getShowDiaryList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		Account account=accountService.getRedisAccount(request);
		UserInfo userInfo = accountService.getRedisUserInfo(request);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setDeptId(userInfo.getDeptId());
	pageParam.setLevelId(userInfo.getLeadLeave());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=diaryService.getShowDiaryList(pageParam,beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


@RequestMapping(value="/getMySubordinatesDiaryList",method=RequestMethod.POST)
public RetDataBean getMySubordinatesDiaryList(
		HttpServletRequest request,
		PageParam pageParam,
		String accountId,
		String beginTime,
		String endTime
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
		Account account=accountService.getRedisAccount(request);
		List<String> list = new ArrayList<String>();
		if(StringUtils.isBlank(accountId))
		{
			List<Map<String, String>> tempList = accountservice.getMySubordinates(account.getOrgId(),account.getAccountId());
			for(int i=0;i<tempList.size();i++)
			{
				list.add(tempList.get(i).get("accountId"));
			}
		}else
		{
			String [] arr=null;
			if(accountId.indexOf(",")>0)
			{
				arr = accountId.split(",");
			}else
			{
				arr = new String[] {accountId};
			}
			list = new ArrayList<String>(Arrays.asList(arr)) ;
		}
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
	PageInfo<Map<String, String>> pageInfo=diaryService.getMySubordinatesDiaryList(pageParam,list,beginTime, endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getAttendYearList   
 * @Description: TODO 获取年份列表
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getAttendYearList",method=RequestMethod.POST)
public RetDataBean getAttendYearList(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", attendService.getAttendYearList(account.getOrgId(),account.getAccountId()));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMonthList   
 * @Description: TODO 获取月份
 * @param: request
 * @param: year
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getMonthList",method=RequestMethod.POST)
public RetDataBean getMonthList(HttpServletRequest request,String year)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", attendService.getMonthList(account.getOrgId(),year,account.getAccountId()));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyAllAttendList   
 * @Description: TODO 获取个人一年内的考勤记录
 * @param: request
 * @param: year
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getMyAllAttendList",method=RequestMethod.POST)
public RetDataBean getMyAllAttendList(HttpServletRequest request,String year,String type)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", attendService.getMyAllAttendList(account.getOrgId(),year,account.getAccountId(),type));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getMyNewsListForDesk   
 * @Description: TODO(这里用一句话描述这个方法的作用)   
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getMyNewsListForDesk",method=RequestMethod.POST)
public RetDataBean getMyNewsListForDesk(HttpServletRequest request)
{
	try
	{
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		return RetDataTools.Ok("请求成功!", newsService.getMyNewsListForDesk(userInfo.getOrgId(),SysTools.getTime("yyyy-MM-dd"), userInfo.getAccountId(), userInfo.getDeptId(), userInfo.getLeadLeave()));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
	/**
	 * 
	 * @Title getNewsManageList   
	 * @Description TODO 获取新闻维护列表
	 * @param request
	 * @param sortId
	 * @param pageNumber
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @param sortOrder
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getNewsManageList",method=RequestMethod.POST)
	public RetDataBean getNewsManageList(
			HttpServletRequest request,
			PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("DESC");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrgId(account.getOrgId());
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
		pageParam.setOrderBy(orderBy);
		PageInfo<Map<String, Object>> pageInfo=newsService.getNewsManageList(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
/**
 * 
 * @Title: getMyNewsList   
 * @Description: TODO 获取个人新闻列表
 * @param: request
 * @param: pageParam
 * @param: newsType
 * @param: status
 * @param: beginTime
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
	@RequestMapping(value="/getMyNewsList",method=RequestMethod.POST)
	public RetDataBean getMyNewsList(
			HttpServletRequest request,
			PageParam pageParam,
			String newsType,
			String status,
			String beginTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("ASC");
			}
		String endTime = SysTools.getTime("yyyy-MM-dd");
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
		pageParam.setOrgId(userInfo.getOrgId());
		pageParam.setAccountId(userInfo.getAccountId());
		pageParam.setOrderBy(orderBy);
		PageInfo<Map<String, Object>> pageInfo=newsService.getMyNewsList(pageParam,userInfo.getDeptId(),userInfo.getLeadLeave(),newsType,status,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: getReadNews 
	* @Description: TODO 获取查阅的新闻详情 
	* @param @param request
	* @param @param news
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getReadNews",method=RequestMethod.POST)
	public RetDataBean getReadNews(HttpServletRequest request,News news)
	{
		try
		{
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			return RetDataTools.Ok("请求成功!", newsService.getReadNews(userInfo.getOrgId(),userInfo.getAccountId(), userInfo.getDeptId(), userInfo.getLeadLeave(), news.getNewsId()));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}	
	
	/**
	 * 获取人个当前日程
	 * @Title getMyCalendarList   
	 * @Description TODO(这里用一句话描述这个方法的作用)   
	 * @param request
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getMyCalendarList",method=RequestMethod.POST)
	public RetDataBean getMyCalendarList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			String nowDate = SysTools.getTime("yyyy-MM-dd")+" 00:00:00";
			return RetDataTools.Ok("请求成功!", calendarService.getMyCalendarList(account.getOrgId(), account.getAccountId(), nowDate));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: getMyCalendarListForDesk   
	 * @Description: TODO 获取个人桌面日程列表
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMyCalendarListForDesk",method=RequestMethod.POST)
	public RetDataBean getMyCalendarListForDesk(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			String beginTime = SysTools.getTime("yyyy-MM-dd")+" 00:00:00";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			Date date = sdf.parse(beginTime); 
			Long times = date.getTime()+7*24*60*60*1000;
			String endTime = sdf.format(times); 
			return RetDataTools.Ok("请求成功!", calendarService.getMyCalendarListForDesk(account.getOrgId(), account.getAccountId(), beginTime,endTime));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getCalendarById   
	 * @Description: TODO 获取日程详情 
	 * @param: request
	 * @param: calendar
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getCalendarById",method=RequestMethod.POST)
	public RetDataBean getCalendarById(HttpServletRequest request,Calendar calendar)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			calendar.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", calendarService.selectOneCalendar(calendar));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getAllCalendarList   
	 * @Description: TODO统计个人日程  
	 * @param: request
	 * @param: pageParam
	 * @param: beginTime
	 * @param: endTime
	 * @param: type
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getAllCalendarList",method=RequestMethod.POST)
	public RetDataBean getAllCalendarList(
			HttpServletRequest request,
			PageParam pageParam,
			String beginTime,
			String endTime,
			String type
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("START");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=calendarService.getAllCalendarList(pageParam, beginTime, endTime, type);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getNewsById 
	* @Description: TODO 按新闻ID获取新闻
	* @param @param request
	* @param @param news
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getNewsById",method=RequestMethod.POST)
	public RetDataBean getNewsById(HttpServletRequest request,News news)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			news.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", newsService.selectOneNews(news));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMobileNewsInfo   
	 * @Description: TODO 移动端获取新闻详情
	 * @param: request
	 * @param: news
	 * @param: orgId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value = "/getMobileNewsInfo", method = RequestMethod.POST)
	public RetDataBean getMobileNewsInfo(HttpServletRequest request, News news) {
		try {
			
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			if(StringUtils.isBlank(news.getNewsId()))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			news.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", newsService.getReadNews(account.getOrgId(), userInfo.getAccountId(), userInfo.getDeptId(), userInfo.getLeadLeave(), news.getNewsId()));
		} catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
/**
 * 
 * @Title: getMySms   
 * @Description: TODO 获取个人消息列表
 * @param: request
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
	@RequestMapping(value="/getMySms",method=RequestMethod.POST)
	public RetDataBean getMySms(
			HttpServletRequest request,
			PageParam pageParam,
			String type,
			String beginTime,
			String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("SMS_SEND_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("ASC");
			}
		Account account=accountService.getRedisAccount(request);
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
		pageParam.setOrgId(account.getOrgId());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrderBy(orderBy);
		PageInfo<Map<String, String>> pageInfo=smsService.getMySms(pageParam,type,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMySendMoblieSms   
	 * @Description: TODO(描述这个方法的作用)   
	 * @param: request
	 * @param: pageParam
	 * @param: module
	 * @param: beginTime
	 * @param: endTime
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMySendMoblieSms",method=RequestMethod.POST)
	public RetDataBean getMySendMoblieSms(
			HttpServletRequest request,
			PageParam pageParam,
			String module,
			String beginTime,
			String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("SEND_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("ASC");
			}
		Account account=accountService.getRedisAccount(request);
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
		pageParam.setOrgId(account.getOrgId());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrderBy(orderBy);
		PageInfo<Map<String, String>> pageInfo=mobileSmsService.getMySendMoblieSms(pageParam, beginTime, endTime, module);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	* @Title: getDiaryPriv 
	* @Description: TODO 获取工作日志设置
	* @param @param request
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getDiaryPriv",method=RequestMethod.POST)
	public RetDataBean getDiaryPriv(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			DiaryPriv diaryPriv = new DiaryPriv();
			diaryPriv.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",diaryPrivService.selectOneDiaryPriv(diaryPriv));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getMyDiaryList 
	* @Description: TODO 获取当前用户的历史工作日志
	* @param @param request
	* @param @param sortId
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getMyDiaryList",method=RequestMethod.POST)
	public RetDataBean getMyDiaryList(
			HttpServletRequest request,
			PageParam pageParam,
			String diaryDay,
			String accountId
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
		Account account=accountService.getRedisAccount(request);
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
		pageParam.setOrgId(account.getOrgId());
		if(StringUtils.isNotBlank(accountId))
		{
			pageParam.setAccountId(accountId);
		}else
		{
			pageParam.setAccountId(account.getAccountId());
		}
		pageParam.setOrderBy(orderBy);
		PageInfo<Map<String, Object>> pageInfo=diaryService.getMyDiaryList(pageParam,diaryDay);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: getOtherDiaryList 
	* @Description: TODO 获取他人的工作日志列表
	* @param @param request
	* @param @param sortId
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @param accountId
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getOtherDiaryList",method=RequestMethod.POST)
	public RetDataBean getOtherDiaryList(
			HttpServletRequest request,
			String sortId,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder,
			String accountId,
			String beginTime,
			String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="CREATE_TIME";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
		Account account=accountService.getRedisAccount(request);
		String orderBy = sort+ " " + sortOrder;
		PageInfo<Map<String, Object>> pageInfo=diaryService.getOtherDiaryList(pageNumber, pageSize, orderBy, account.getOrgId(),accountId,beginTime,endTime,"%"+search+"%");
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getDiaryById 
	* @Description: TODO 获取工作日志详情
	* @param @param request
	* @param @param diary
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getDiaryById",method=RequestMethod.POST)
	public RetDataBean getDiaryById(HttpServletRequest request,Diary diary)
	{
		try
		{
			if(StringUtils.isBlank(diary.getDiaryId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			diary.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",diaryService.selectOneDiary(diary));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getAttendConfigById   
	 * @Description: TODO 获取考勤配置详情
	 * @param: request
	 * @param: attendConfig
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getAttendConfigById",method=RequestMethod.POST)
	public RetDataBean getAttendConfigById(HttpServletRequest request,AttendConfig attendConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			attendConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", attendConfigService.selectOneAttendConfig(attendConfig));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getAllAttendConfigList   
	 * @Description: TODO 获取考勤列表
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getAllAttendConfigList",method=RequestMethod.POST)
	public RetDataBean getAllAttendConfigList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", attendConfigService.getAllAttendConfigList(account.getOrgId()));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMyAttendConfigList   
	 * @Description: TODO 获取所有的考勤记录 
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMyAttendConfigList",method=RequestMethod.POST)
	public RetDataBean getMyAttendConfigList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", attendConfigService.getMyAttendConfigList(account.getOrgId()));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getAttendConfigList   
	 * @Description: TODO 获取考勤规则列表
	 * @param: request
	 * @param: sortId
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getAttendConfigList",method=RequestMethod.POST)
	public RetDataBean getAttendConfigList(
			HttpServletRequest request,
			String sortId,
			PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("SORT_NO");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		AttendConfig attendConfig = new AttendConfig();
		attendConfig.setOrgId(account.getOrgId());
		PageInfo<AttendConfig> pageInfo=attendConfigService.getAttendConfigList(pageParam,attendConfig);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
