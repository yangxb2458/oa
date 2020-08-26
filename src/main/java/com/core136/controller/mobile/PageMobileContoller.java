package com.core136.controller.mobile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.core136.bean.account.Account;
import com.core136.bean.sys.DdConfig;
import com.core136.service.account.AccountService;
import com.core136.service.sys.AppConfigService;

@Controller
@RequestMapping("/mobile")
public class PageMobileContoller {
	@Autowired
	private AppConfigService appConfigService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: goToMobileIndex   
	 * @Description: TODO 跳转到移动端首页
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/main/index")
	public ModelAndView  goToMobileIndex(HttpServletRequest request)
	{
		try
		{
		Account account=accountService.getRedisAccount(request);
		List<String> appList = accountService.getRedisLoginAccountInfo(request).getMobilePrivList();
		JSONObject appMenuList = appConfigService.getMyAppList(account.getOrgId(),appList);
		ModelAndView mv = new ModelAndView("app/mobile/main/index");
		mv.addObject("appMenuList",appMenuList);
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	
	
	/**
	 * 
	 * @Title: getMobileBpmCreate   
	 * @Description: TODO 移动端BPM列表 
	 * @param: unit
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/bpm/mybpmlist")
	public ModelAndView getMobileMyBpmList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			System.out.println(account.getAccountId());
			ModelAndView mv = new ModelAndView("app/mobile/main/bpm/mybpmlist");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: getMobileMyDocumentList   
	 * @Description: TODO 待办公文列表
	 * @param request
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/document/mydocumentlist")
	public ModelAndView getMobileMyDocumentList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			System.out.println(account.getAccountId());
			ModelAndView mv = new ModelAndView("app/mobile/main/document/mydocumentlist");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	@RequestMapping("/bpm/bpmquerylist")
	public ModelAndView getMobileBpmQueryList()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/bpm/bpmquerylist");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	@RequestMapping("/document/documentquerylist")
	public ModelAndView getMobileDocumentQueryList()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/document/documentquerylist");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	@RequestMapping("/document/documentquery")
	public ModelAndView getMobileDocumentQuery()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/document/documentquery");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: getMobileBpmCreate   
	 * @Description: TODO 钉钉创建BPM列表 
	 * @param: unit
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/bpm/create")
	public ModelAndView getMobileBpmCreate()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/bpm/createbpm");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: getMobileBpmQuery   
	 * @Description: TODO BPM查询
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/bpm/bpmquery")
	public ModelAndView getMobileBpmQuery()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/bpm/bpmquery");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: getMobileBpmBegin   
	 * @Description: TODO发起BPM流程  
	 * @param: unit
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/bpm/bpmbegin")
	public ModelAndView getMobileBpmBegin()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/bpm/bpmbegin");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: getMobileBpmSendTo   
	 * @Description: TODO 抄送给我的BPM流程
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/bpm/sendto")
	public ModelAndView getMobileBpmSendTo()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/bpm/bpmsendto");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	
	@RequestMapping("/document/sendto")
	public ModelAndView getMobileDocumentendTo()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/document/documentsendto");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: ModelAndView   
	 * @Description: TODO 移动端通知公告应用
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/notice/index")
	public ModelAndView getMobileMyNotice()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/notice/index");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: getMobileMyEmail   
	 * @Description: TODO个人电子邮件
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/email/index")
	public ModelAndView getMobileMyEmail()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/email/index");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: getMobileMyDiary   
	 * @Description: TODO个人工作日志
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/diary/index")
	public ModelAndView getMobileMyDiary()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/diary/index");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	/**
	 * 
	 * @Title: getMobileMyNoticeDetails   
	 * @Description: TODO 移动端通知公告详情
	 * @param: unit
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/notice/details")
	public ModelAndView getMobileMyNoticeDetails()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/notice/details");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: getMobileMyNews   
	 * @Description: TODO 移动端新闻
	 * @param: unit
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/news/index")
	public ModelAndView getMobileMyNews()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/news/index");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: getMobileMyNewsDetails   
	 * @Description: TODO 移动端新闻详情
	 * @param: unit
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("/news/details")
	public ModelAndView getMobileMyNewsDetails()
	{
		try
		{
			ModelAndView mv = new ModelAndView("app/mobile/main/news/details");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title: getMobileMyMeeting   
	 * @Description: TODO 我的会议
	 * @return
	 * ModelAndView    
	 * @throws
	 */
	@RequestMapping("/meeting/mymeeting")
	public ModelAndView getMobileMyMeeting()
	{
		try
		{
			ModelAndView mv = new ModelAndView("/app/mobile/main/meeting/index");
			return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
}
