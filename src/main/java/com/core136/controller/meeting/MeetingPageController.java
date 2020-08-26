package com.core136.controller.meeting;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sun.glass.ui.View;


@Controller
@RequestMapping("/app/core/meeting")
public class MeetingPageController {
	/**
	 * 
	 * @Title: goMeetingDetails   
	 * @Description: TODO 会议详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/meetingdetails")
	public ModelAndView  goMeetingDetails()
	{
		ModelAndView mv=null;
		try
		{
			mv = new ModelAndView("/app/core/meeting/meetingdetails");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: summarydetails   
	 * @Description: TODO 会议记要详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/summarydetails")
	public ModelAndView  summarydetails()
	{
		ModelAndView mv=null;
		try
		{
			mv = new ModelAndView("/app/core/meeting/summarydetails");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goSummary   
	 * @Description: TODO 会议记要
	 * @param: view
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/summary")
	public ModelAndView  goSummary(String view)
	{
		ModelAndView mv=null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("/app/core/meeting/summary");
		}else 
		{
			if(view.equals("manage"))
			{
				mv = new ModelAndView("/app/core/meeting/summarymanage");
			}else if(view.equals("edit"))
			{
				mv = new ModelAndView("/app/core/meeting/summaryedit");
			}
		}
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goSummaryQuery   
	 * @Description: TODO 会议纪要查询
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/summaryquery")
	public ModelAndView  goSummaryQuery()
	{
		ModelAndView mv=null;
		try
		{
			mv = new ModelAndView("/app/core/meeting/summaryquery");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goMyMeeting   
	 * @Description: TODO 我的会议列表
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/mymeeting")
	public ModelAndView  goMyMeeting(String view)
	{
		ModelAndView mv=null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("/app/core/meeting/mymeeting");
		}else if(view.equals("manage"))
		{
			mv = new ModelAndView("/app/core/meeting/mymeetingmanage");
		}
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		mv = new ModelAndView("titps");
		return mv;
	}
	}	
	/**
	 * 
	 * @Title goBommange   
	 * @Description TODO 添加会议室
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/addmeetingroom")
	public ModelAndView  addMeetingroom()
	{
		try
		{
		ModelAndView mv = new ModelAndView("/app/core/meeting/addroom");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: setmeetingdevice   
	 * @Description: TODO 设置会议室设备
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/setmeetingdevice")
	public ModelAndView  setmeetingdevice()
	{
		try
		{
		ModelAndView mv = new ModelAndView("/app/core/meeting/meetingdevice");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goApplymeeting   
	 * @Description: TODO 申请会议
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/applymeeting")
	public ModelAndView  goApplymeeting(String view)
	{
		ModelAndView mv = null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("/app/core/meeting/applymeeting");
			}else
			{
				if(view.equals("manage"))
				{
					mv = new ModelAndView("/app/core/meeting/applymeetingmanage");
				}
			}
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: manage   
	 * @Description: TODO 会议管理
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/manage")
	public ModelAndView manage(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			 mv = new ModelAndView("/app/core/meeting/manage");
		}else
		{
			if(view.equals("old"))
			{
				mv = new ModelAndView("/app/core/meeting/manageold");
			}
		}
		
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
}
