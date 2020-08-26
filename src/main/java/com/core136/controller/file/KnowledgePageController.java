/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  AppKnowledgePageController.java   
 * @Package com.core136.controller.sys   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月10日 上午11:02:48   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.file;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @ClassName:  AppKnowledgePageController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年9月10日 上午11:02:48   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
@RequestMapping("/app/core")
public class KnowledgePageController {
	/**
	 * 
	 * @Title: goKnowledgestudylist   
	 * @Description: TODO 跳转到知识分类清单
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/file/knowledgestudylist")
	public ModelAndView  goKnowledgestudylist()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/knowledge/knowledgestudylist");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}	
	/**
	 * 
	 * @Title: goDetailKnowledge   
	 * @Description: TODO 知识详情
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/file/detailknowledge")
	public ModelAndView  goDetailKnowledge()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/knowledge/knowledgedetail");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}	
/**
 * 	
 * @Title: goKnowledgeIntegral   
 * @Description: TODO知识积分
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/file/knowledgeintegral")
	public ModelAndView  goKnowledgeIntegral()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/knowledge/knowledgeintegral");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}		
/**
 * 
 * @Title: goKnowledgestudy   
 * @Description: TODO 知识学习
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping("/file/knowledgestudy")
	public ModelAndView  goKnowledgestudy()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/knowledge/knowledgestudy");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}	
	/**
	 * 
	 * @Title: goIndexSearch   
	 * @Description: TODO 全文检索页面
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/file/indexsearch")
	public ModelAndView  goIndexSearch()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/indexsearch");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	* @Title: goKnowledgesort 
	* @Description: TODO 设置知识分类
	* @param @return 设定文件 
	* @return ModelAndView 返回类型
	 */
	@RequestMapping("/file/knowledgesort")
	public ModelAndView  goKnowledgesort()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/knowledge/sort");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: goKnowledgeManage   
	 * @Description: TODO 知识记录管理
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/file/knowledgeinit")
	public ModelAndView  goKnowledgeManage(String view)
	{
		ModelAndView mv =null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("app/core/knowledge/knowledgeinit");
			}else
			{
				if(view.equals("init"))
				{
					mv = new ModelAndView("app/core/knowledge/knowledgeintegral");
				}else if(view.equals("total"))
				{
					mv = new ModelAndView("app/core/knowledge/knowledgetotal");
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
	 * @Title: goKnowledgecreate   
	 * @Description: TODO 添加知识
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/file/knowledgecreate")
	public ModelAndView  goKnowledgecreate(String view)
	{
		ModelAndView mv = null;
		try
		{
			if(StringUtils.isBlank(view))
			{
				mv = new ModelAndView("app/core/knowledge/knowledgecreate");
			}else
			{
				if(view.equals("manage"))
				{
					mv = new ModelAndView("app/core/knowledge/knowledgemanage");	
				}else if(view.equals("edit"))
				{
					mv = new ModelAndView("app/core/knowledge/knowledgeedit");	
				}
			}
		return mv;
		}catch (Exception e) {
		mv = new ModelAndView("titps");
		return mv;
	}
	}
}
