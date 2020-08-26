package com.core136.service.systimingtask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.core136.common.enums.GobalConstant;
import org.core136.common.utils.SysTools;
import org.springframework.context.ApplicationContext;

import com.core136.bean.account.Account;
import com.core136.bean.oa.Calendar;
import com.core136.bean.sys.MsgBody;
import com.core136.service.account.AccountService;
import com.core136.service.oa.CalendarService;
import com.core136.service.sys.MessageUnitService;
import com.core136.unit.SpringUtils;

import tk.mybatis.mapper.entity.Example;

public class CalendarTimgingRunnable implements Runnable {
	private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
	private CalendarService calendarService = applicationContext.getBean(CalendarService.class);
	private AccountService accountService = applicationContext.getBean(AccountService.class);
	private MessageUnitService messageUnitService = applicationContext.getBean(MessageUnitService.class);
	public String orgId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
	public void run() {
		String nowTime = SysTools.getTime("yyyy-MM-dd HH:mm:ss");
		List<Calendar> calendarlist = calendarService.getTimgingCalendarList(orgId,nowTime);
		Long nowTimeStamp=0L;
		try {
			nowTimeStamp = SysTools.getTimeStamp(nowTime, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < calendarlist.size(); i++) {
			String msgType = calendarlist.get(i).getMsgType();
			if (StringUtils.isNotBlank(msgType)) {
				String advance = calendarlist.get(i).getAdvance();
				Boolean flag = false;
				String smsTime = calendarlist.get(i).getSmsTime();
				if (StringUtils.isNotBlank(advance)&&StringUtils.isNotBlank(smsTime)) {
					Long smsTimeStamp = 0L;
					try {
						smsTimeStamp = SysTools.getTimeStamp(smsTime, "yyyy-MM-dd HH:mm:ss");
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(nowTimeStamp>smsTimeStamp)
					{
						flag = true;
					}
				}
				if (flag) {
						Calendar calendar = new Calendar();
						calendar.setOrgId(orgId);
						calendar.setCalendarId(calendarlist.get(i).getCalendarId());
						calendar.setStatus("1");
						Example example = new Example(Calendar.class);
						example.createCriteria().andEqualTo("orgId",orgId).andEqualTo("calendarId",calendar.getCalendarId());
						calendarService.updateCalendar(calendar, example);
						List<MsgBody> msgBodyList = new ArrayList<MsgBody>();
						Account account2 = new Account();
						account2.setAccountId(calendarlist.get(i).getAccountId());
						account2.setOrgId(orgId);
						account2 = accountService.selectOneAccount(account2);
						MsgBody msgBody = new MsgBody();
						msgBody.setTitle("日程提醒");
						msgBody.setContent("内容为："+calendarlist.get(i).getContent());
						msgBody.setSendTime(nowTime);
						msgBody.setAccount(account2);
						msgBody.setFormUserName("系统消息");
						msgBody.setRedirectUrl("/app/core/oa/calendardetails?calendarId="+calendar.getCalendarId());
						msgBody.setOrgId(orgId);
						msgBodyList.add(msgBody);
						messageUnitService.sendMessage(msgType, GobalConstant.MSG_TYPE_CALENDAR, msgBodyList);
				}
			}

		}
		System.out.println("CalendarTimgingRunnable.run()，" + new Date() + orgId);

	}
}
