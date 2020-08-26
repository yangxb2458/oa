package com.core136.service.systimingtask;

import java.util.Date;

import org.springframework.context.ApplicationContext;

import com.core136.unit.SpringUtils;

public class BiTimgingRunnable implements Runnable{
	private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
	//private CalendarService calendarService = applicationContext.getBean(CalendarService.class);
	public String orgId;
	 public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	 @Override
     public void run() {
		 
         System.out.println("BiTimgingRunnable.run()，" + new Date());
     }
}
