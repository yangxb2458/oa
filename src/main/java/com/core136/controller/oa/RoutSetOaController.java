/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetOaController.java   
 * @Package com.core136.controller.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 刘绍全     
 * @date:   2019年1月14日 下午7:55:31   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.oa;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.attend.Attend;
import com.core136.bean.attend.AttendConfig;
import com.core136.bean.oa.Calendar;
import com.core136.bean.oa.Diary;
import com.core136.bean.oa.DiaryComments;
import com.core136.bean.oa.DiaryPriv;
import com.core136.bean.oa.LeadActivity;
import com.core136.bean.oa.News;
import com.core136.bean.oa.NewsComments;
import com.core136.bean.sys.Sms;
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
import com.core136.service.sys.SmsService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**   
 * @ClassName:  RoutSetOaController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 刘绍全 
 * @date:   2019年1月14日 下午7:55:31   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云科信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/set/oaset")
public class RoutSetOaController {
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
	private LeadActivityService leadActivityService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: sendSms   
	 * @Description: TODO 发送内部短信息
	 * @param request
	 * @param toUser
	 * @param content
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/sendSms",method=RequestMethod.POST)
	public RetDataBean sendSms (HttpServletRequest request,String toUser,String content)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("消息发送成功!",smsService.sendSms(account,toUser,content));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertLeadActivity
	 * @Description: TODO 创建领导行程
	 * @param request
	 * @param hrKpiPlan
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertLeadActivity",method=RequestMethod.POST)
	public RetDataBean insertLeadActivity (HttpServletRequest request,LeadActivity leadActivity)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			leadActivity.setRecordId(SysTools.getGUID());
			leadActivity.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			leadActivity.setCreateUser(account.getAccountId());
			leadActivity.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加成功!",leadActivityService.insertLeadActivity(leadActivity));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteLeadActivity
	 * @Description: TODO 删除领导行程
	 * @param request
	 * @param hrKpiPlan
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteLeadActivity",method=RequestMethod.POST)
	public RetDataBean deleteLeadActivity(HttpServletRequest request,LeadActivity leadActivity)
	{
		try
		{
			if(StringUtils.isBlank(leadActivity.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			leadActivity.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!",leadActivityService.deleteLeadActivity(leadActivity));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateLeadActivity
	 * @Description: TODO 更新领导行程
	 * @param request
	 * @param hrKpiPlan
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateLeadActivity",method=RequestMethod.POST)
	public RetDataBean updateLeadActivity(HttpServletRequest request,LeadActivity leadActivity)
	{
		try
		{
			if(StringUtils.isBlank(leadActivity.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(LeadActivity.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",leadActivity.getRecordId());
			return RetDataTools.Ok("更新成功!",leadActivityService.updateLeadActivity(example, leadActivity));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertDiaryComments   
	 * @Description: TODO 添加日志评论
	 * @param: request
	 * @param: diaryComments
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertDiaryComments",method=RequestMethod.POST)
	public RetDataBean insertDiaryComments(HttpServletRequest request,DiaryComments diaryComments)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			diaryComments.setCommId(SysTools.getGUID());
			diaryComments.setCreateUser(account.getAccountId());
			diaryComments.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			diaryComments.setOrgId(account.getOrgId());
			return RetDataTools.Ok("发表评论成功!", diaryCommentsService.insertDiaryComments(diaryComments));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @Title: delDiary   
	 * @Description: TODO 删除日志
	 * @param: request
	 * @param: diary
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/delDiary",method=RequestMethod.POST)
	public RetDataBean delDiary(HttpServletRequest request,Diary diary)
	{
		try
		{
			if(StringUtils.isBlank(diary.getDiaryId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			diary.setCreateUser(account.getAccountId());
			diary.setOrgId(account.getOrgId());
			return RetDataTools.Ok("日志删除成功!", diaryService.delDiary(diary));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertNewsComments   
	 * @Description: TODO 添加新闻评论 
	 * @param: request
	 * @param: newsComments
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertNewsComments",method=RequestMethod.POST)
	public RetDataBean insertNewsComments(HttpServletRequest request,NewsComments newsComments)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			newsComments.setCommId(SysTools.getGUID());
			newsComments.setCreateUser(account.getAccountId());
			newsComments.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			newsComments.setOrgId(account.getOrgId());
			return RetDataTools.Ok("发表评论成功!", newsCommentsService.insertNewsComments(newsComments));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @Title: insertAttend   
	 * @Description: TODO 添加考勤记录
	 * @param: request
	 * @param: attend
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertAttend",method=RequestMethod.POST)
	public RetDataBean insertAttend(HttpServletRequest request,Attend attend)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			attend.setAttendId(SysTools.getGUID());
			attend.setCreateUser(account.getAccountId());
			attend.setType("0");
			attend.setSource("pc");
			String nowTimeStr = SysTools.getTime("yyyy-MM-dd HH:mm:ss");
			attend.setCreateTime(nowTimeStr);
			attend.setYear(nowTimeStr.substring(0,4));
			attend.setMonth(nowTimeStr.substring(5,7));
			attend.setDay(nowTimeStr.substring(8,10));
			attend.setNowTime(nowTimeStr.split(" ")[0]);
			attend.setOrgId(account.getOrgId());
			return RetDataTools.Ok("打卡成功!", attendService.insertAttend(attend));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title sendNews   
	 * @Description TODO 新闻发送
	 * @param request
	 * @param news
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/updateNews",method=RequestMethod.POST)
	public RetDataBean updateNews(HttpServletRequest request,News news)
	{
		try
		{
			if(StringUtils.isBlank(news.getNewsId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			news.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			Document htmlDoc = Jsoup.parse(news.getContent());
			String subheading = htmlDoc.text();
			if(subheading.length()>50)
			{
				subheading = subheading.substring(0,50)+"...";
			}
			news.setSubheading(subheading);
			Example example = new Example(News.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("newsId",news.getNewsId());
			return RetDataTools.Ok("新闻更新成功!", newsService.updateNews(news, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: sendNews 
	* @Description: TODO 发布新闻
	* @param @param request
	* @param @param news
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/sendNews",method=RequestMethod.POST)
	public RetDataBean sendNews(HttpServletRequest request,News news)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			news.setNewsId(SysTools.getGUID());
			String createTime = SysTools.getTime("yyyy-MM-dd HH:mm:ss");
			news.setCreateUser(account.getAccountId());
			news.setDelFlag("0");
			news.setStatus("0");
			news.setOnclickCount(0);
			if(StringUtils.isBlank(news.getSendTime()))
			{
				news.setSendTime(createTime.split(" ")[0]);
			}
			Document htmlDoc = Jsoup.parse(news.getContent());
			String subheading = htmlDoc.text();
			if(subheading.length()>50)
			{
				subheading = subheading.substring(0,50)+"...";
			}
			news.setSubheading(subheading);
			news.setCreateTime(createTime);
			news.setOrgId(account.getOrgId());
			return RetDataTools.Ok("新闻发布成功!", newsService.sendNews(news,userInfo));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: delNews 
	* @Description: TODO 删除新闻
	* @param @param request
	* @param @param news
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/delNews",method=RequestMethod.POST)
	public RetDataBean delNews(HttpServletRequest request,News news)
	{
		try
		{
			if(StringUtils.isBlank(news.getNewsId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			news.setOrgId(account.getOrgId());
			if(!account.getOpFlag().equals("1"))
			{
				news.setCreateUser(account.getAccountId());
			}
			return RetDataTools.Ok("新闻删除成功!", newsService.deleteNews(news));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: stopNews 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param request
	* @param @param news
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/stopNews",method=RequestMethod.POST)
	public RetDataBean stopNews(HttpServletRequest request,News news)
	{
		try
		{
			if(StringUtils.isBlank(news.getNewsId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			news.setOrgId(account.getOrgId());
			news.setStatus("1");
			Example example = new Example(News.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("orgId",account.getOrgId()).andEqualTo("newsId",news.getNewsId());
			if(!account.getOpFlag().equals("1"))
			{
				criteria.andEqualTo("createUser",account.getAccountId());
				
			}
			if(newsService.updateNews(news, example)>0)
			{
				return RetDataTools.Ok("终止新闻成功!");
			}else
			{
				return RetDataTools.Ok("您没有权限!");
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: sotpNews 
	* @Description: TODO 终止新闻的发布
	* @param @param request
	* @param @param news
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/sotpNews",method=RequestMethod.POST)
	public RetDataBean sotpNews(HttpServletRequest request,News news)
	{
		try
		{
			if(StringUtils.isBlank(news.getNewsId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			news.setDelFlag("1");
			Example example = new Example(News.class);
			if(!account.getOpFlag().equals("1"))
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("newId",news.getNewsId()).andEqualTo("createUser",account.getAccountId());
			}else
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("newId",news.getNewsId());
			}
			return RetDataTools.Ok("新闻删除成功!", newsService.updateNews(news, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title delSms   
	 * @Description TODO删除消息
	 * @param request
	 * @param sms
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/delSms",method=RequestMethod.POST)
	public RetDataBean delSms(HttpServletRequest request,Sms sms)
	{
		try
		{
			if(StringUtils.isBlank(sms.getSmsId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			sms.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除消息成功!", smsService.deleteSms(sms));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: updateSms   
	 * @Description: TODO 更新消息状态
	 * @param request
	 * @param sms
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateSms",method=RequestMethod.POST)
	public RetDataBean updateSms(HttpServletRequest request,Sms sms)
	{
		try
		{
			if(StringUtils.isBlank(sms.getSmsId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			sms.setOrgId(account.getOrgId());
			Example example = new Example(Sms.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("smsId",sms.getSmsId());
			return RetDataTools.Ok("删除消息成功!", smsService.updateSms(sms, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title addCalendar   
	 * @Description TODO 添加日程  
	 * @param request
	 * @param calendar
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/addCalendar",method=RequestMethod.POST)
	public RetDataBean addCalendar(HttpServletRequest request,Calendar calendar)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			calendar.setCalendarId(SysTools.getGUID());
			calendar.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			calendar.setAccountId(account.getAccountId());
			calendar.setCreateUser(account.getAccountId());
			String smsTime="";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String startTime=calendar.getStart();
			Date date = sdf.parse(startTime); 
			if(!calendar.getAdvance().equals("0"))
			{
				if(calendar.getAdvance().equals("1"))
				{
					Long times = date.getTime()+1*60*60*1000;
					smsTime = sdf.format(times); 
				}else if(calendar.getAdvance().equals("2"))
				{
					Long times = date.getTime()+2*60*60*1000;
					smsTime = sdf.format(times);
				}else if(calendar.getAdvance().equals("3"))
				{
					Long times = date.getTime()+6*60*60*1000;
					smsTime = sdf.format(times);
				}else if(calendar.getAdvance().equals("4"))
				{
					Long times = date.getTime()+24*60*60*1000;
					smsTime = sdf.format(times);
				}else if(calendar.getAdvance().equals("5"))
				{
					Long times = date.getTime()+48*60*60*1000;
					smsTime = sdf.format(times);
				}
				calendar.setStatus("0");
			}else
			{
				calendar.setStatus("1");
			}
			calendar.setSmsTime(smsTime);
			calendar.setOrgId(account.getOrgId());
			return RetDataTools.Ok("日程添加成功!", calendarService.insertCalendar(calendar));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title updateCalendar   
	 * @Description TODO 日程更新
	 * @param request
	 * @param calendar
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/updateCalendar",method=RequestMethod.POST)
	public RetDataBean updateCalendar(HttpServletRequest request,Calendar calendar)
	{
		try
		{
			if(StringUtils.isBlank(calendar.getCalendarId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			calendar.setAccountId(account.getAccountId());
			calendar.setOrgId(account.getOrgId());
			String smsTime="";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String startTime=calendar.getStart();
			Date date = sdf.parse(startTime); 
			if(!calendar.getAdvance().equals("0"))
			{
				if(calendar.getAdvance().equals("1"))
				{
					Long times = date.getTime()+1*60*60*1000;
					smsTime = sdf.format(times); 
				}else if(calendar.getAdvance().equals("2"))
				{
					Long times = date.getTime()+2*60*60*1000;
					smsTime = sdf.format(times);
				}else if(calendar.getAdvance().equals("3"))
				{
					Long times = date.getTime()+6*60*60*1000;
					smsTime = sdf.format(times);
				}else if(calendar.getAdvance().equals("4"))
				{
					Long times = date.getTime()+24*60*60*1000;
					smsTime = sdf.format(times);
				}else if(calendar.getAdvance().equals("5"))
				{
					Long times = date.getTime()+48*60*60*1000;
					smsTime = sdf.format(times);
				}
				calendar.setStatus("0");
			}else
			{
				calendar.setStatus("1");
			}
			calendar.setSmsTime(smsTime);
			calendar.setStatus("0");
			Example example = new Example(Calendar.class);
			example.createCriteria().andEqualTo("orgId",calendar.getOrgId()).andEqualTo("calendarId",calendar.getCalendarId());
			return RetDataTools.Ok("日程更新成功!", calendarService.updateCalendar(calendar, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title delCalendar   
	 * @Description TODO 删除日程   
	 * @param request
	 * @param calendar
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/delCalendar",method=RequestMethod.POST)
	public RetDataBean delCalendar(HttpServletRequest request,Calendar calendar)
	{
		try
		{
			if(StringUtils.isBlank(calendar.getCalendarId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			calendar.setAccountId(account.getAccountId());
			calendar.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除日程成功!", calendarService.deleteCalendar(calendar));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	* @Title: setDiaryPriv 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param request
	* @param @param diaryPriv
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/setDiaryPriv",method=RequestMethod.POST)
	public RetDataBean setDiaryPriv(HttpServletRequest request,DiaryPriv diaryPriv)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("您没有权限设置,请与系统管理员联系!");
			}
			diaryPriv.setCreateUser(account.getAccountId());
			diaryPriv.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			diaryPriv.setOrgId(account.getOrgId());
			return RetDataTools.Ok("设置工作日志权限成功!", diaryPrivService.setDiaryPriv(diaryPriv));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: sendDiary 
	* @Description: TODO 发布工作日志
	* @param @param request
	* @param @param diary
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/sendDiary",method=RequestMethod.POST)
	public RetDataBean sendDiary(HttpServletRequest request,Diary diary)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			DiaryPriv diaryPriv = new DiaryPriv();
			diaryPriv.setOrgId(account.getOrgId());
			diaryPriv=diaryPrivService.selectOneDiaryPriv(diaryPriv);
			diary.setDiaryId(SysTools.getGUID());
			diary.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			diary.setCreateUser(account.getAccountId());
			diary.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建工作日志!", diaryService.createDiary(account,userInfo,diary,diaryPriv));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateDiary   
	 * @Description: TODO 工作日志更新 
	 * @param: request
	 * @param: diary
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateDiary",method=RequestMethod.POST)
	public RetDataBean updateDiary(HttpServletRequest request,Diary diary)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(diary.getDiaryId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			diary.setCreateUser(account.getAccountId());
			diary.setOrgId(account.getOrgId());
			DiaryPriv diaryPriv = new DiaryPriv();
			diaryPriv.setOrgId(account.getOrgId());
			diaryPriv=diaryPrivService.selectOneDiaryPriv(diaryPriv);
			Example example = new Example(Diary.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("diaryId",diary.getDiaryId());
			return RetDataTools.Ok("更新工作日志!", diaryService.updateDiary(account,userInfo,example, diary,diaryPriv));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertAttendConfig   
	 * @Description: TODO 添加考勤规则
	 * @param: request
	 * @param: attendConfig
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertAttendConfig",method=RequestMethod.POST)
	public RetDataBean insertAttendConfig(HttpServletRequest request,AttendConfig attendConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			attendConfig.setConfigId(SysTools.getGUID());;
			attendConfig.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			attendConfig.setCreateUser(account.getAccountId());
			attendConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("考勤规则添加成功!", attendConfigService.insertAttendConfig(attendConfig));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateAttendConfig   
	 * @Description: TODO更新考勤规则
	 * @param: request
	 * @param: attendConfig
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateAttendConfig",method=RequestMethod.POST)
	public RetDataBean updateAttendConfig(HttpServletRequest request,AttendConfig attendConfig)
	{
		try
		{
			if(StringUtils.isBlank(attendConfig.getConfigId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			attendConfig.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			Example example = new Example(AttendConfig.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("configId",attendConfig.getConfigId());
			return RetDataTools.Ok("考勤规则成功!", attendConfigService.updateAttendConfig(attendConfig, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteAttendConfig   
	 * @Description: TODO 删除考勤规则
	 * @param: request
	 * @param: attendConfig
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteAttendConfig",method=RequestMethod.POST)
	public RetDataBean deleteAttendConfig(HttpServletRequest request,AttendConfig attendConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(attendConfig.getConfigId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			attendConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("考勤规则删除成功!", attendConfigService.deleteAttendConfig(attendConfig));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
}
