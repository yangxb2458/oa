/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetEmailController.java   
 * @Package com.core136.controller.email   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年7月17日 上午10:31:47   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.email;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.email.Email;
import com.core136.bean.email.EmailBody;
import com.core136.bean.email.EmailBox;
import com.core136.bean.email.EmailConfig;
import com.core136.service.account.AccountService;
import com.core136.service.email.EmailBodyService;
import com.core136.service.email.EmailBoxService;
import com.core136.service.email.EmailConfigService;
import com.core136.service.email.EmailService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  RoutGetEmailController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年7月17日 上午10:31:47   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/ret/oaget")
public class RoutGetEmailController {
	@Autowired
	private EmailBoxService emailBoxService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailBodyService emailBodyService;
	@Autowired
	private EmailConfigService emailConfigService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	* @Title: getMyEmailConfig 
	* @Description: TODO 获取个人邮件配置信息
	* @param @param request
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getMyEmailConfig",method=RequestMethod.POST)
	public RetDataBean getMyEmailConfig(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			EmailConfig emailConfig = new EmailConfig();
			emailConfig.setOrgId(account.getOrgId());
			emailConfig.setAccountId(account.getAccountId());
			return RetDataTools.Ok("请求成功!", emailConfigService.selectOneEmailConfig(emailConfig));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getEmailBoxList   
	 * @Description TODO 获取邮件文件夹列表
	 * @param request
	 * @param emailBox
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getEmailBoxList",method=RequestMethod.POST)
	public RetDataBean getEmailBoxList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", emailBoxService.getEmailBoxList(account.getOrgId(),account.getAccountId()));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getEmailBox 
	* @Description: TODO 获取文件夹详情
	* @param @param request
	* @param @param emailBox
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getEmailBox",method=RequestMethod.POST)
	public RetDataBean getEmailBox(HttpServletRequest request,EmailBox emailBox)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			emailBox.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", emailBoxService.selectOneEmailBox(emailBox));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getEmailListForDesk   
	 * @Description: TODO 获取桌面个人邮件列表
	 * @param: request
	 * @param: emailBox
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getEmailListForDesk",method=RequestMethod.POST)
	public RetDataBean getEmailListForDesk(HttpServletRequest request,EmailBox emailBox)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", emailService.getEmailListForDesk(account.getOrgId(),account.getAccountId()));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title getMyEmailAll   
	 * @Description TODO 获取个人邮件
	 * @param request
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getMyEmailAll",method=RequestMethod.POST)
	public RetDataBean getMyEmailAll(HttpServletRequest request,
			String sortLeave,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder,
			String boxId 
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="ID";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", emailService.getMyEmailAll(account, pageNumber, pageSize, sort+" "+sortOrder ,"%"+search+"%", boxId));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	

	/**
	 * 
	* @Title: getStarEmail 
	* @Description: TODO 获取个人标星邮件
	* @param @param request
	* @param @param sortLeave
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getStarEmail",method=RequestMethod.POST)
	public RetDataBean getStarEmail(HttpServletRequest request,
			String sortLeave,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="ID";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", emailService.getMyStarEmail(account, pageNumber, pageSize, sort+" "+sortOrder ,"%"+search+"%"));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getMyDelEmailAll 
	* @Description: TODO 获取回收站邮件列表
	* @param @param request
	* @param @param sortLeave
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getMyDelEmailAll",method=RequestMethod.POST)
	public RetDataBean getMyDelEmailAll(HttpServletRequest request,
			String sortLeave,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="ID";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", emailService.getMyDelEmailAll(account, pageNumber, pageSize, sort+" "+sortOrder ,"%"+search+"%"));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getsendBoxEmail   
	 * @Description TODO 获取发件箱内容
	 * @param request
	 * @param boxId
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getsendBoxEmail",method=RequestMethod.POST)
	public RetDataBean getsendBoxEmail(HttpServletRequest request,
			String sortLeave,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="ID";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", emailBodyService.getsendBoxEmail(account, pageNumber, pageSize, sort+" "+sortOrder ,"%"+search+"%"));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: getDraftBoxEmail 
	* @Description: TODO 获取草稿箱内容列表 
	* @param @param request
	* @param @param sortLeave
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getDraftBoxEmail",method=RequestMethod.POST)
	public RetDataBean getDraftBoxEmail(HttpServletRequest request,
			String sortLeave,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="ID";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", emailBodyService.getDraftBoxEmail(account, pageNumber, pageSize, sort+" "+sortOrder ,"%"+search+"%"));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getEmailBoxManageList 
	* @Description: TODO 获取个人邮件文件夹列表
	* @param @param request
	* @param @param sortLeave
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getEmailBoxManageList",method=RequestMethod.POST)
	public RetDataBean getEmailBoxManageList(HttpServletRequest request,
			String sortLeave,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="SORT_NO";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="DESC";
			}
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", emailBoxService.getEmailBoxManageList(account, pageNumber, pageSize, sort+" "+sortOrder ,"%"+search+"%"));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getEmailBody 
	* @Description: TODO 获取BODY_EMAIL详情
	* @param @param request
	* @param @param emailBody
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getEmailBody",method=RequestMethod.POST)
	public RetDataBean getEmailBody(HttpServletRequest request,EmailBody emailBody)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			emailBody.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", emailBodyService.selectOneEmailBody(emailBody));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getEmailDetails   
	 * @Description TODO 获取内部邮件详情 
	 * @param request
	 * @param emailId
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getEmailDetails",method=RequestMethod.POST)
	public RetDataBean getEmailDetails(HttpServletRequest request,String emailId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			Email email = new Email();
			email.setEmailId(emailId);
			email.setReadTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			email.setOrgId(account.getOrgId());
			Example example = new Example(Email.class);
			example.createCriteria().andEqualTo("emailId",email.getEmailId()).andEqualTo("orgId",account.getOrgId());
			emailService.updateEmail(email, example);
			return RetDataTools.Ok("请求成功!", emailService.getEmailDetails(account.getOrgId(),account.getAccountId(),emailId));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getEmailCount   
	 * @Description TODO 获取各类型邮件总数  
	 * @param request
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getEmailCount",method=RequestMethod.POST)
	public RetDataBean getEmailCount(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", emailService.getEmailCount(account.getOrgId(), account.getAccountId()));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
}
