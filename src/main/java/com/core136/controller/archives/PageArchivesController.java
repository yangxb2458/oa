package com.core136.controller.archives;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/app/core/archives")
public class PageArchivesController {
	/**
	 * 
	 * @Title: goVolumeListDetails   
	 * @Description: TODO 借阅案卷详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/volumelistdetails")
	public ModelAndView  goVolumeListDetails()
	{
		ModelAndView mv = null;
		try
		{
			mv = new ModelAndView("app/core/archives/volumelistdetails");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goApproval   
	 * @Description: TODO 借阅审批
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/approval")
	public ModelAndView  goApproval(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/archives/approvalmanage");
		}else {
			if(view.equals("old"))
			{
				mv = new ModelAndView("app/core/archives/approval");
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
 * @Title: goDestroy   
 * @Description: TODO 档案销毁
 * @param view
 * @return
 * ModelAndView    
 * @throws
 */
	@RequestMapping("/destroy")
	public ModelAndView  goDestroy()
	{
		ModelAndView mv = null;
		try
		{
			mv = new ModelAndView("app/core/archives/destroymanage");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goQuery   
	 * @Description: TODO 档案查询
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/query")
	public ModelAndView  goQuery()
	{
		ModelAndView mv = null;
		try
		{
			mv = new ModelAndView("app/core/archives/query");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	
/**
 * 	
 * @Title: goBorrow   
 * @Description: TODO 档案借阅管理
 * @param view
 * @return
 * ModelAndView    
 * @throws
 */
	@RequestMapping("/borrow")
	public ModelAndView  goBorrow(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/archives/borrowmanage");
		}else {
			if(view.equals("create"))
			{
				mv = new ModelAndView("app/core/archives/borrow");
			} else if(view.equals("volume"))
			{
				mv = new ModelAndView("app/core/archives/borrowvolume");
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
 * @Title: goFile   
 * @Description: TODO 文件管理
 * @param view
 * @return
 * ModelAndView    
 * @throws
 */
	@RequestMapping("/files")
	public ModelAndView  goFiles(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/archives/filemanage");
		}else {
			if(view.equals("create"))
			{
				mv = new ModelAndView("app/core/archives/file");
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
	 * @Title: goVolume   
	 * @Description: TODO 案卷管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/volume")
	public ModelAndView  goVolume(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/archives/volumemanage");
		}else {
			if(view.equals("create"))
			{
				mv = new ModelAndView("app/core/archives/volume");
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
	 * @Title: goRepository   
	 * @Description: TODO 卷库管理
	 * @param view
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/repository")
	public ModelAndView  goRepository(String view)
	{
		ModelAndView mv = null;
		try
		{
		if(StringUtils.isBlank(view))
		{
			mv = new ModelAndView("app/core/archives/repositorymanage");
		}else {
			if(view.equals("create"))
			{
				mv = new ModelAndView("app/core/archives/repository");
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
	 * @Title: goRepositoryDetails   
	 * @Description: TODO 卷库详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/repositorydetails")
	public ModelAndView  goRepositoryDetails()
	{
		ModelAndView mv = null;
		try
		{
		mv = new ModelAndView("app/core/archives/repositorydetails");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goBorrowFileDetails   
	 * @Description: TODO 档案查询详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/borrowfiledetails")
	public ModelAndView  goBorrowFileDetails()
	{
		ModelAndView mv = null;
		try
		{
		mv = new ModelAndView("app/core/archives/borrowfiledetails");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}	

	/**
	 * 
	 * @Title: goFileDetails   
	 * @Description: TODO 文件详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/filedetails")
	public ModelAndView  goFileDetails()
	{
		ModelAndView mv = null;
		try
		{
		mv = new ModelAndView("app/core/archives/filedetails");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}

	/**
	 * 
	 * @Title: goFileDetails   
	 * @Description: TODO 文件详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/filedetailsforborrow")
	public ModelAndView  goFileDetails2()
	{
		ModelAndView mv = null;
		try
		{
		mv = new ModelAndView("app/core/archives/filedetails2");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goVolumeFileDetails   
	 * @Description: TODO 案卷文件借阅详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/volumefiledetails")
	public ModelAndView  goVolumeFileDetails()
	{
		ModelAndView mv = null;
		try
		{
		mv = new ModelAndView("app/core/archives/borrowvolumefiledetails");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: goVolumeDetails   
	 * @Description: TODO 案卷详情
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/volumedetails")
	public ModelAndView  goVolumeDetails()
	{
		ModelAndView mv = null;
		try
		{
		mv = new ModelAndView("app/core/archives/volumedetails");
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
}
