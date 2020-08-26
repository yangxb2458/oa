package com.core136.service.crm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.crm.CrmLinkMan;
import com.core136.bean.email.EmailBody;
import com.core136.bean.email.EmailConfig;
import com.core136.bean.file.Attach;
import com.core136.bean.sys.PageParam;
import com.core136.bean.sys.SysConfig;
import com.core136.mapper.crm.CrmLinkManMapper;
import com.core136.service.email.EmailBodyService;
import com.core136.service.email.EmailConfigService;
import com.core136.service.email.EmailService;
import com.core136.service.file.AttachService;
import com.core136.service.sys.SysConfigService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  CrmLinkManService   
 * @Description:TODO 客户联系人管理
 * @author: 稠云信息
 * @date:   2019年2月12日 下午5:01:31   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class CrmLinkManService{
@Autowired
private CrmLinkManMapper crmLinkManMapper;
@Autowired
private EmailService emailService;
@Autowired
private AttachService attachService;
@Autowired
private EmailConfigService emailConfigService;
@Autowired
private SysConfigService sysConfigService;
@Autowired
private EmailBodyService emailBodyService;

public int insertCrmLinkMan(CrmLinkMan crmLinkMan)
{
	return crmLinkManMapper.insert(crmLinkMan);
}

public CrmLinkMan selectOne(CrmLinkMan crmLinkMan)
{
	return crmLinkManMapper.selectOne(crmLinkMan);
}

public int updateCrmLinkMan(CrmLinkMan crmLinkMan,Example example)
{
	return crmLinkManMapper.updateByExampleSelective(crmLinkMan, example);
}

public int deleteCrmLinkMan(CrmLinkMan crmLinkMan)
{
	return crmLinkManMapper.delete(crmLinkMan);
}

/**
 * 获取客户联系人列表
 */

public List<Map<String, Object>> getCrmLinkManList(String orgId, String customerId) {
	// TODO Auto-generated method stub
	return crmLinkManMapper.getCrmLinkManList(orgId, customerId);
}

/**
 * 
 * @Title getCrmLinkManByCustomerId   
 * @Description TODO 获取企业所有联系人  
 * @param example
 * @return      
 * List<CrmLinkMan>
 */
public List<CrmLinkMan> getCrmLinkManByCustomerId(Example example)
{
	return crmLinkManMapper.selectByExample(example);
}
/**
 * 获取CRM联系人列表
 */

public List<Map<String, Object>> getCrmLinkManAllList(String orgId, String search) {
	// TODO Auto-generated method stub
	return crmLinkManMapper.getCrmLinkManAllList(orgId, "%"+search+"%");
}



public PageInfo<Map<String, Object>> getCrmLinkManAllList(PageParam pageParam) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,Object>> datalist= getCrmLinkManAllList(pageParam.getOrgId(), pageParam.getSearch());
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}

/**
 * 联系人基本信息
 */

public Map<String, Object> getCrmLinkManInfo(String orgId, String linkManId) {
	// TODO Auto-generated method stub
	return crmLinkManMapper.getCrmLinkManInfo(orgId, linkManId);
}
/**
 * 
 * @Title sendWebMail   
 * @Description TODO 邮件服务
 * @param account
 * @param to
 * @param subject
 * @param content
 * @param attachId
 * @param sendServiceType
 * @return      
 * RetDataBean
 */
public RetDataBean sendWebMail(Account account,String to,String subject,String content,String attachId,String sendServiceType,UserInfo userInfo)
{
	try
	{
		EmailBody emailBody = new EmailBody();
		emailBody.setBodyId(SysTools.getGUID());
		emailBody.setAttach(attachId);
		emailBody.setFromId(account.getAccountId());
		emailBody.setSubject(subject);
		emailBody.setWebEmailTo(to);
		emailBody.setContent(content);
		emailBody.setSendTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		emailBody.setOrgId(account.getOrgId());
		List<String> list = new ArrayList<String>();
		List<String>  paths = new ArrayList<String>();
		if(StringUtils.isNotBlank(attachId))
		{
			if(attachId.endsWith(","))
			{
				attachId = attachId.substring(0,attachId.length()-1);
			}
			String [] attachIds;
			if(attachId.indexOf(",")>-1)
			{
				attachIds=attachId.split(",");
			}else
			{
				attachIds=new String[] {attachId};
			}
			list = Arrays.asList(attachIds);
			List<Attach> attachList = attachService.getAttachList(list, account.getOrgId());
			
			for(int i=0;i<attachList.size();i++)
			{
				paths.add(attachList.get(i).getPath());
			}
		}
		if(sendServiceType.equals("0"))
		{
			if(StringUtils.isNotBlank(attachId))
				{
					emailService.sendAttachmentsWebEmail(to, subject, content, paths);
				}else
				{
					emailService.sendSimpleWebEmail(to, subject, content);
				}
			emailBody.setWebEmailFlag("0");
		}else if(sendServiceType.equals("1"))
		{
			SysConfig sysConfig = new SysConfig();
			sysConfig.setOrgId(account.getOrgId());
			sysConfig = sysConfigService.selectOneSysConfig(sysConfig);
			emailService.sendWebMailPersonByOrg(sysConfig, to, subject, content, paths);
			emailBody.setWebEmailFlag("1");
		}else if(sendServiceType.equals("2"))
		{
			EmailConfig emailConfig = new EmailConfig();
			emailConfig.setAccountId(account.getAccountId());
			emailConfig.setOrgId(account.getOrgId());
			emailConfig = emailConfigService.selectOneEmailConfig(emailConfig);
			emailService.sendWebMailPerson(emailConfig, to, subject, content, paths);
			emailBody.setWebEmailFlag("2");
		}
		emailBodyService.sendEmail(emailBody,userInfo);
		return RetDataTools.Ok("邮件发送成功!");
	}catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/** 
* @Title: getMyCrmLinkManAllList 
* @Description: TODO 业务员客户联系人
* @param @param orgId
* @param @param keepUser
* @param @param search
* @param @return 设定文件 
*/ 

public List<Map<String, Object>> getMyCrmLinkManAllList(String orgId, String keepUser, String search) {
	// TODO Auto-generated method stub
	return crmLinkManMapper.getMyCrmLinkManAllList(orgId, keepUser, search);
}

/**
 * 
* @Title: getMyCrmLinkManAllList 
* @Description: TODO 业务员客户联系人
* @param @param pageNumber
* @param @param pageSize
* @param @param orderBy
* @param @param orgId
* @param @param keepUser
* @param @param search
* @param @return 设定文件 
* @return PageInfo<Map<String,Object>> 返回类型
 */
public PageInfo<Map<String, Object>> getMyCrmLinkManAllList(int pageNumber,int pageSize,String orderBy,String orgId,String keepUser, String search) {
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String,Object>> datalist= crmLinkManMapper.getMyCrmLinkManAllList(orgId, keepUser,search);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}

}
