/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetNoticeController.java   
 * @Package com.core136.controller.notice   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年7月20日 上午10:17:48   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.notice;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.notice.Notice;
import com.core136.bean.notice.NoticeConfig;
import com.core136.bean.notice.NoticeTemplate;
import com.core136.service.account.AccountService;
import com.core136.service.notice.NoticeConfigService;
import com.core136.service.notice.NoticeService;
import com.core136.service.notice.NoticeTemplateService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**   
 * @ClassName:  RoutSetNoticeController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年7月20日 上午10:17:48   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/set/noticeset")
public class RoutSetNoticeController {
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeTemplateService noticeTemplateService;
	@Autowired
	private NoticeConfigService noticeConfigService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	* @Title: sendNotice 
	* @Description: TODO 发布公告
	* @param @param request
	* @param @param notice
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/sendNotice",method=RequestMethod.POST)
	public RetDataBean sendNotice(HttpServletRequest request,Notice notice)
	{
		try
		{
			if(StringUtils.isBlank(notice.getNoticeType()))
			{
				return RetDataTools.NotOk("通知公告类型不能为空");
			}
			Document htmlDoc = Jsoup.parse(notice.getContent());
			String subheading = htmlDoc.text();
			if(subheading.length()>50)
			{
				subheading = subheading.substring(0,50)+"...";
			}
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			notice.setNoticeId(SysTools.getGUID());
			notice.setCreateUser(account.getAccountId());
			notice.setDelFlag("0");
			notice.setApprovalStatus("0");
			notice.setOnclickCount("0");
			notice.setSubheading(subheading);
			notice.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			notice.setOrgId(account.getOrgId());
			return RetDataTools.Ok("发布成功!", noticeService.sendNotice(notice,userInfo));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: updateNotice 
	* @Description: TODO 更新通知公告
	* @param @param request
	* @param @param notice
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/updateNotice",method=RequestMethod.POST)
	public RetDataBean updateNotice(HttpServletRequest request,Notice notice)
	{
		try
		{
			if(StringUtils.isBlank(notice.getNoticeId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Document htmlDoc = Jsoup.parse(notice.getContent());
			String subheading = htmlDoc.text();
			if(subheading.length()>50)
			{
				subheading = subheading.substring(0,50)+"...";
			}
			notice.setSubheading(subheading);
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			notice.setOrgId(account.getOrgId());
			Example example = new Example(Notice.class);
			Criteria criteria =example.createCriteria();
			if(!account.getOpFlag().equals("1"))
			{
				criteria.andEqualTo("createUser",account.getAccountId());
			}
			criteria.andEqualTo("noticeId",notice.getNoticeId()).andEqualTo("orgId",account.getOrgId());
			notice.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			return RetDataTools.Ok("公告更新成功!", noticeService.reEditNotice(userInfo,notice, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: approval 
	* @Description: TODO 审批通知公告
	* @param @param request
	* @param @param notice
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	
	@RequestMapping(value="/approval",method=RequestMethod.POST)
	public RetDataBean approval(HttpServletRequest request,Notice notice)
	{
		try
		{
			if(StringUtils.isBlank(notice.getNoticeId()))
			{
				return RetDataTools.NotOk("请求的参数有问题!");
			}
			if(StringUtils.isBlank(notice.getStatus()))
			{
				notice.setStatus("0");
			}
			Account account=accountService.getRedisAccount(request);
			notice.setOrgId(account.getOrgId());
			Example example = new Example(Notice.class);
			example.createCriteria().andEqualTo("noticeId",notice.getNoticeId()).andEqualTo("orgId",notice.getOrgId());
			return RetDataTools.Ok("审批完成", noticeService.updateNotice(notice, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: inertNoticeTemplate 
	* @Description: TODO 添加通知公告红头模版
	* @param @param request
	* @param @param noticeTemplate
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/addNoticeTemplate",method=RequestMethod.POST)
	public RetDataBean inertNoticeTemplate(HttpServletRequest request,NoticeTemplate noticeTemplate)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			noticeTemplate.setTemplateId(SysTools.getGUID());
			noticeTemplate.setOrgId(account.getOrgId());
			noticeTemplate.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			noticeTemplate.setCreateUser(account.getAccountId());	
			return RetDataTools.Ok("添加红头模版成功!", noticeTemplateService.insertNoticeTemplate(noticeTemplate));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: initNocticeConfig 
	* @Description: TODO 初始化通知公告配置
	* @param @param request
	* @param @param noticeTemplate
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/initNocticeConfig",method=RequestMethod.POST)
	public RetDataBean initNocticeConfig(HttpServletRequest request,NoticeTemplate noticeTemplate)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("您不是系统管理员,请与管理员联系!");
			}
			return RetDataTools.Ok("初始化通知公告成功!", noticeConfigService.initNocticeConfig(account,"notice"));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: delNoticeTemplate 
	* @Description: TODO 删除红头模版
	* @param @param request
	* @param @param noticeTemplate
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/delNoticeTemplate",method=RequestMethod.POST)
	public RetDataBean delNoticeTemplate(HttpServletRequest request,NoticeTemplate noticeTemplate)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(noticeTemplate.getTemplateId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			if(!account.getOpFlag().equals("1"))
			{
				noticeTemplate.setCreateUser(account.getAccountId());
			}
			noticeTemplate.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除红头模版成功!", noticeTemplateService.delNoticeTemplate(noticeTemplate));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: delNotice   
	 * @Description: TODO 删除通知公告
	 * @param: request
	 * @param: notice
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/delNotice",method=RequestMethod.POST)
	public RetDataBean delNotice(HttpServletRequest request,Notice notice)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(notice.getNoticeId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			if(!account.getOpFlag().equals("1"))
			{
				notice.setCreateUser(account.getAccountId());
			}
			notice.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除通知公告成功!", noticeService.deleteNotice(notice));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: updateNoticeTemplate 
	* @Description: TODO 更新模版信息
	* @param @param request
	* @param @param noticeTemplate
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/updateNoticeTemplate",method=RequestMethod.POST)
	public RetDataBean updateNoticeTemplate(HttpServletRequest request,NoticeTemplate noticeTemplate)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(noticeTemplate.getTemplateId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Example example = new Example(NoticeTemplate.class);
			if(!account.getOpFlag().equals("1"))
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("templateId",noticeTemplate.getTemplateId()).andEqualTo("createUser",account.getAccountId());
			}else
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("templateId",noticeTemplate.getTemplateId());
			}
			noticeTemplate.setOrgId(account.getOrgId());
			return RetDataTools.Ok("更新模版信息成功!", noticeTemplateService.updateNoticeTemplate(noticeTemplate, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: updateNoticeConfig 
	* @Description: TODO 更新配置
	* @param @param request
	* @param @param noticeConfig
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/updateNoticeConfig",method=RequestMethod.POST)
	public RetDataBean updateNoticeConfig(HttpServletRequest request,NoticeConfig noticeConfig)
	{
		try
		{
			if(StringUtils.isBlank(noticeConfig.getConfigId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("您不是系统管理员,请与管理员联系!");
			}
			noticeConfig.setOrgId(account.getOrgId());
			Example example = new Example(NoticeConfig.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("configId",noticeConfig.getConfigId());
			return RetDataTools.Ok("更新配置成功!", noticeConfigService.updateNoticeConfig(noticeConfig, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
