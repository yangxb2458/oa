package com.core136.service.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.email.Email;
import com.core136.bean.email.EmailBody;
import com.core136.bean.sys.MsgBody;
import com.core136.mapper.email.EmailBodyMapper;
import com.core136.service.account.AccountService;
import com.core136.service.sys.MessageUnitService;

import org.core136.common.enums.GobalConstant;
import org.core136.common.utils.SysTools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  EmailBodyService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 刘绍全 
 * @date:   2019年1月14日 下午7:50:13   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云科信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class EmailBodyService{
@Autowired
private EmailBodyMapper emailBodyMapper;
@Autowired
private EmailService emailService;
@Autowired
private AccountService accountService;
@Autowired
private MessageUnitService messageUnitService;

public int insertEmailBody(EmailBody emailBody)
{
	return emailBodyMapper.insert(emailBody);
}
public int deleteEmailBody(EmailBody emailBody)
{
	return emailBodyMapper.delete(emailBody);
}
public EmailBody selectOneEmailBody(EmailBody emailBody)
{
	return emailBodyMapper.selectOne(emailBody);
}
public int updateEmailBody(EmailBody emailBody,Example example)
{
	return emailBodyMapper.updateByExampleSelective(emailBody, example);
}

/**
 * 
 * @Title sendEmail   
 * @Description TODO 发送电子邮件
 * @param emailBody
 * @param emailList
 * @return      
 * int
 */
public int sendEmail(EmailBody emailBody,UserInfo userInfo)
{
	if(StringUtils.isNotBlank(emailBody.getToId()))
	{
		List<Email> emailList = new ArrayList<Email>();
		List<MsgBody> msgList = new ArrayList<MsgBody>();
		
		String[] toIdArr=null;
		if(StringUtils.isNotBlank(emailBody.getToId()))
		{
			toIdArr = emailBody.getToId().split(",");
		}
		for(int i=0;i<toIdArr.length;i++)
		{
			
			Account account = accountService.getAccountByAccountId(toIdArr[i],emailBody.getOrgId());
			if(account!=null)
			{
				String emailId = SysTools.getGUID();
				Email email = new Email();
				email.setEmailId(emailId);
				email.setBodyId(emailBody.getBodyId());
				email.setToId(toIdArr[i]);
				email.setDelFlag("0");
				email.setBoxId("0");
				email.setOrgId(emailBody.getOrgId());
				emailList.add(email);
				//消息结构体
				MsgBody msgBody = new MsgBody();
				msgBody.setTitle(emailBody.getSubject());
				msgBody.setSendTime(emailBody.getSendTime());
				Document htmlDoc = Jsoup.parse(emailBody.getContent());
				String content = htmlDoc.text();
				if(content.length()>50)
				{
					content = content.substring(0,50)+"...";
				}
				msgBody.setFormUserName(userInfo.getUserName());
				msgBody.setFromAccountId(emailBody.getFromId());
				msgBody.setAttach(emailBody.getAttach());
				msgBody.setContent(content);
				msgBody.setRedirectUrl("/app/core/oa/emaildetails?emailId="+emailId);
				msgBody.setOrgId(emailBody.getOrgId());
				msgBody.setAccount(account);
				msgList.add(msgBody);
			}
		}
		for(int i=0;i<emailList.size();i++)
		{
			emailService.insertEmail(emailList.get(i));
		}
		int flag=insertEmailBody(emailBody);
		if(flag>0)
		{
			messageUnitService.sendMessage(emailBody.getMsgType(),GobalConstant.MSG_TYPE_EMAIL,msgList);
		}
		return flag;
	}else
	{
		return 0;
	}
}
/**
 * 获取发件箱内容
 */
public List<Map<String, Object>> getsendBoxEmail(String orgId, String accountId,String search) {
	// TODO Auto-generated method stub
	return emailBodyMapper.getsendBoxEmail(orgId, accountId,search);
}

public PageInfo<Map<String, Object>> getsendBoxEmail(Account account, Integer pageNumber,Integer pageSize, String orderBy,String search) {
	PageHelper.orderBy(orderBy);
	PageHelper.startPage(pageNumber, pageSize);
	List<Map<String,Object>> datalist= emailBodyMapper.getsendBoxEmail(account.getOrgId(),account.getAccountId(),search);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}
/**
 * 删除自己创建的邮件
 */

public int delMySendEmail(String orgId, String accountId, List<String> list) {
	// TODO Auto-generated method stub
	return emailBodyMapper.delMySendEmail(orgId, accountId, list);
}
/**
 * 获取草稿箱内容
 */

public List<Map<String, Object>> getDraftBoxEmail(String orgId, String accountId, String search) {
	// TODO Auto-generated method stub
	return emailBodyMapper.getDraftBoxEmail(orgId, accountId, search);
}

/**
 * 
* @Title: getDraftBoxEmail 
* @Description: TODO  获取草稿箱内容列表
* @param @param account
* @param @param pageNumber
* @param @param pageSize
* @param @param orderBy
* @param @param search
* @param @return 设定文件 
* @return PageInfo<Map<String,Object>> 返回类型 

 */
public PageInfo<Map<String, Object>> getDraftBoxEmail(Account account, Integer pageNumber,Integer pageSize, String orderBy,String search) {
	PageHelper.orderBy(orderBy);
	PageHelper.startPage(pageNumber, pageSize);
	List<Map<String,Object>> datalist= emailBodyMapper.getDraftBoxEmail(account.getOrgId(),account.getAccountId(),search);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}

}
