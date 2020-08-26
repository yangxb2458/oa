package com.core136.controller.task;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/core/task")
public class PageTaskContoller {
	/**
	 * 
	 * @Title: goTaskDataDetails   
	 * @Description: TODO 任务处理详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/taskdatadetails")
	public ModelAndView  goTaskDataDetails()
	{
		ModelAndView mv =null;
		try
		{
				mv = new ModelAndView("app/core/task/taskdatadetails");
		return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goGanttDetails   
	 * @Description: TODO 整体进度甘特图
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/ganttdetails")
	public ModelAndView  goGanttDetails()
	{
		ModelAndView mv =null;
		try
		{
				mv = new ModelAndView("app/core/task/ganttdetails");
		return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goProcessDetails   
	 * @Description: TODO 处理事件详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/processdetails")
	public ModelAndView  goProcessDetails()
	{
		ModelAndView mv =null;
		try
		{
				mv = new ModelAndView("app/core/task/taskprocessdetails");
		return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goTaskMonitor   
	 * @Description: TODO 领导管控 
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/taskmonitor")
	public ModelAndView  goTaskMonitor(String view)
	{
		ModelAndView mv =null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("app/core/task/taskmonitor");
			}else 
			{
				if(view.equals("supervisor"))
				{
					mv = new ModelAndView("app/core/task/taskmonitorsupervisor");
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
	 * @Title: goMyTask   
	 * @Description: TODO 我的作务列表
	 * @param: view
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/myTask")
	public ModelAndView  goMyTask(String view)
	{
		ModelAndView mv =null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("app/core/task/mytask");
			}else 
			{
				if(view.equals("manage"))
				{
					mv = new ModelAndView("app/core/task/managemytask");
				}else if(view.equals("edit"))
				{
					mv = new ModelAndView("app/core/task/editmytask");
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
	 * @Title: goAddTask   
	 * @Description: TODO 创建任务
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/createtask")
	public ModelAndView  goCreatetask(String view)
	{
		ModelAndView mv =null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("app/core/task/createtask");
			}else 
			{
				if(view.equals("manage"))
				{
					mv = new ModelAndView("app/core/task/managetask");
				}else if(view.equals("edit"))
				{
					mv = new ModelAndView("app/core/task/edittask");
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
	 * @Title: goTaskDetails   
	 * @Description: TODO 任务详情
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/taskdetails")
	public ModelAndView  goTaskDetails()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/task/taskdetails");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goAssignmentTask   
	 * @Description: TODO 任务分解
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/assignmentTask")
	public ModelAndView  goAssignmentTask(String view)
	{
		ModelAndView mv = null;
		try
		{
			if(StringUtils.isBlank(view))
			{
			mv = new ModelAndView("app/core/task/assignmenttask");
			}else
			{
				if(view.equals("distribution"))
				{
					mv = new ModelAndView("app/core/task/distribution");
				}else if(view.equals("editdistribution"))
				{
					mv = new ModelAndView("app/core/task/editdistribution");
				}else if(view.equals("gantt"))
				{
					mv = new ModelAndView("app/core/task/assigngantt");
				}
			}
		return mv;
		}catch (Exception e) {
			mv = new ModelAndView("titps");
		return mv;
	}
	}
}
