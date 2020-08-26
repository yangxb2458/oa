/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetEmailController.java   
 * @Package com.core136.controller.email   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年7月17日 上午10:32:06   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.email;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
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
import org.core136.common.utils.SysTools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  RoutSetEmailController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年7月17日 上午10:32:06   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/set/oaset")
public class RoutSetEmailController {
	@Autowired
	private EmailBoxService emailBoxService;
	@Autowired
	private EmailBodyService emailBodyService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailConfigService emailConfigService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title createEmailBox   
	 * @Description TODO 创建邮件文件夹 
	 * @param request
	 * @param emailBox
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/createEmailBox",method=RequestMethod.POST)
	public RetDataBean createEmailBox(HttpServletRequest request,EmailBox emailBox)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			emailBox.setBoxId(SysTools.getGUID());
			emailBox.setAccountId(account.getAccountId());
			emailBox.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建文件夹成功!", emailBoxService.insertEmailBox(emailBox));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title sendEmail   
	 * @Description TODO 发送电子邮件 
	 * @param request
	 * @param bmailBody
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/sendEmail",method=RequestMethod.POST)
	public RetDataBean sendEmail(HttpServletRequest request,EmailBody emailBody)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			emailBody.setSendTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			emailBody.setFromId(account.getAccountId());
			Document htmlDoc = Jsoup.parse(emailBody.getContent());
			String subheading = htmlDoc.text();
			if(subheading.length()>50)
			{
				subheading = subheading.substring(0,50)+"...";
			}
			emailBody.setSubheading(subheading);
			emailBody.setDelFlag("0");
			emailBody.setSendFlag("1");
			emailBody.setBodyId(SysTools.getGUID());
			emailBody.setOrgId(account.getOrgId());
			if(emailBodyService.sendEmail(emailBody,userInfo)>0)
			{
				return RetDataTools.Ok("邮件发送成功！");
			}else
			{
				return RetDataTools.NotOk("邮件缺少接收人，发送失败！");
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: saveEmail 
	* @Description: TODO 保存草稿
	* @param @param request
	* @param @param emailBody
	* @param @param toId
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/saveEmail",method=RequestMethod.POST)
	public RetDataBean saveEmail(HttpServletRequest request,EmailBody emailBody)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			emailBody.setSendTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			emailBody.setFromId(account.getAccountId());
			emailBody.setSendFlag("0");
			emailBody.setDelFlag("0");
			emailBody.setBodyId(SysTools.getGUID());
			emailBody.setOrgId(account.getOrgId());
			return RetDataTools.Ok("邮件发送成功!", emailBodyService.insertEmailBody(emailBody));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title sendEmail   
	 * @Description TODO 更新邮件状态
	 * @param request
	 * @param email
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/updateEmail",method=RequestMethod.POST)
	public RetDataBean sendEmail(HttpServletRequest request,Email email)
	{
		try
		{
			if(StringUtils.isBlank(email.getEmailId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(Email.class);
			example.createCriteria().andEqualTo("emailId",email.getEmailId()).andEqualTo("orgId",account.getOrgId());
			return RetDataTools.Ok("设置成功!", emailService.updateEmail(email, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: delEmailBox 
	* @Description: TODO 删除邮件文件夹成功
	* @param @param request
	* @param @param emailBox
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/delEmailBox",method=RequestMethod.POST)
	public RetDataBean delEmailBox(HttpServletRequest request,EmailBox emailBox)
	{
		try
		{
			if(StringUtils.isBlank(emailBox.getBoxId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			emailBox.setAccountId(account.getAccountId());
			emailBox.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除文件夹成功!", emailBoxService.deleteEmailBox(emailBox));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: updateEmailBox 
	* @Description: TODO 邮件文件夹更新
	* @param @param request
	* @param @param emailBox
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/updateEmailBox",method=RequestMethod.POST)
	public RetDataBean updateEmailBox(HttpServletRequest request,EmailBox emailBox)
	{
		try
		{
			if(StringUtils.isBlank(emailBox.getBoxId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(EmailBox.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("accountId",account.getAccountId()).andEqualTo("boxId",emailBox.getBoxId());
			return RetDataTools.Ok("文件夹修改成功!", emailBoxService.updateEmailBox(emailBox,example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title deleteEmail   
	 * @Description TODO 删除邮件 
	 * @param request
	 * @param email
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/deleteEmail",method=RequestMethod.POST)
	public RetDataBean deleteEmail(HttpServletRequest request,Email email)
	{
		try
		{
			if(StringUtils.isBlank(email.getEmailId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			email.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除邮件成功!", emailService.deleteEmail(email));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title delMyEmail   
	 * @Description TODO 删除个人邮件(逻辑删除)
	 * @param request
	 * @param emailIds
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/delMyEmail",method=RequestMethod.POST)
	public RetDataBean delMyEmail(HttpServletRequest request,String emailIds)
	{
		try
		{
			if(StringUtils.isBlank(emailIds))
			{
				return RetDataTools.Ok("请求参数有问题,请检查!");
			}else
			{
			String[] emailIdsArr;
			if(emailIds.indexOf(",")>-1)
			{
				emailIdsArr = emailIds.split(",");
			}else
			{
				emailIdsArr = new String[] {emailIds};
			}
			List<String> list = Arrays.asList(emailIdsArr);
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("删除个人邮件成功!", emailService.delMyEmail(account.getOrgId(), account.getAccountId(), list));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: recoveryMyEmail 
	* @Description: TODO 恢复回收站中的个人邮件
	* @param @param request
	* @param @param emailIds
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/recoveryMyEmail",method=RequestMethod.POST)
	public RetDataBean recoveryMyEmail(HttpServletRequest request,String emailIds)
	{
		try
		{
			if(StringUtils.isBlank(emailIds))
			{
				return RetDataTools.Ok("请求参数有问题,请检查!");
			}else
			{
			String[] emailIdsArr;
			if(emailIds.indexOf(",")>-1)
			{
				emailIdsArr = emailIds.split(",");
			}else
			{
				emailIdsArr = new String[] {emailIds};
			}
			List<String> list = Arrays.asList(emailIdsArr);
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("恢复已删除的邮件成功!", emailService.recoveryMyEmail(account.getOrgId(), account.getAccountId(), list));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: setMyEmailBox 
	* @Description: TODO  设置个人邮件文件夹
	* @param @param request
	* @param @param emailIds
	* @param @param boxId
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/setMyEmailBox",method=RequestMethod.POST)
	public RetDataBean setMyEmailBox(HttpServletRequest request,String emailIds,String boxId)
	{
		try
		{
			if(StringUtils.isBlank(boxId))
			{
				return RetDataTools.Ok("请求参数有问题,请检查!");
			}
			if(StringUtils.isBlank(emailIds))
			{
				return RetDataTools.Ok("请求参数有问题,请检查!");
			}else
			{
			String[] emailIdsArr;
			if(emailIds.indexOf(",")>-1)
			{
				emailIdsArr = emailIds.split(",");
			}else
			{
				emailIdsArr = new String[] {emailIds};
			}
			List<String> list = Arrays.asList(emailIdsArr);
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("邮件转移成功!", emailService.setMyEmailBox(boxId,account.getOrgId(), account.getAccountId(), list));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: delMyEmailPhysics 
	* @Description: TODO 物理删除个人邮件
	* @param @param request
	* @param @param emailIds
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/delMyEmailPhysics",method=RequestMethod.POST)
	public RetDataBean delMyEmailPhysics(HttpServletRequest request,String emailIds)
	{
		try
		{
			if(StringUtils.isBlank(emailIds))
			{
				return RetDataTools.Ok("请求参数有问题,请检查!");
			}else
			{
			String[] emailIdsArr;
			if(emailIds.indexOf(",")>-1)
			{
				emailIdsArr = emailIds.split(",");
			}else
			{
				emailIdsArr = new String[] {emailIds};
			}
			List<String> list = Arrays.asList(emailIdsArr);
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("删除个人邮件成功!", emailService.delMyEmailPhysics(account.getOrgId(), account.getAccountId(), list));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: updateSetStars 
	* @Description: TODO 批量取消星标记
	* @param @param request
	* @param @param emailIds
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/updateSetStars",method=RequestMethod.POST)
	public RetDataBean updateSetStars(HttpServletRequest request,String emailIds)
	{
		try
		{
			if(StringUtils.isBlank(emailIds))
			{
				return RetDataTools.Ok("请求参数有问题,请检查!");
			}else
			{
			String[] emailIdsArr;
			if(emailIds.indexOf(",")>-1)
			{
				emailIdsArr = emailIds.split(",");
			}else
			{
				emailIdsArr = new String[] {emailIds};
			}
			List<String> list = Arrays.asList(emailIdsArr);
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("取消邮件星标记成功!", emailService.updateSetStars(account.getOrgId(), account.getAccountId(), list));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: delMySendEmail 
	* @Description: TODO 删除邮件
	* @param @param request
	* @param @param emailBodyIds
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/delMySendEmail",method=RequestMethod.POST)
	public RetDataBean delMySendEmail(HttpServletRequest request,String emailBodyIds)
	{
		try
		{
			if(StringUtils.isBlank(emailBodyIds))
			{
				return RetDataTools.Ok("请求参数有问题,请检查!");
			}else
			{
			String[] emailIdsArr;
			if(emailBodyIds.indexOf(",")>-1)
			{
				emailIdsArr = emailBodyIds.split(",");
			}else
			{
				emailIdsArr = new String[] {emailBodyIds};
			}
			List<String> list = Arrays.asList(emailIdsArr);
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("删除邮件成功!", emailBodyService.delMySendEmail(account.getOrgId(), account.getAccountId(), list));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: setEmailConfig 
	* @Description: TODO 设置个人邮WEB邮件信息
	* @param @param request
	* @param @param emailConfig
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/setEmailConfig",method=RequestMethod.POST)
	public RetDataBean setEmailConfig(HttpServletRequest request,EmailConfig emailConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			emailConfig.setAccountId(account.getAccountId());
			emailConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("邮件醒置修改成功!", emailConfigService.setEmailConfig(emailConfig));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
}
