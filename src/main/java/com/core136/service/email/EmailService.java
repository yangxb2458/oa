/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  EmailService.java   
 * @Package com.core136.service.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 刘绍全     
 * @date:   2019年1月14日 下午7:48:08   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.email;

import java.io.File;
import java.security.Security;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.email.Email;
import com.core136.bean.email.EmailConfig;
import com.core136.bean.sys.SysConfig;
import com.core136.mapper.email.EmailMapper;
import com.core136.unit.MyAuthenticator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.mail.util.MailSSLSocketFactory;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: EmailService
 * @Description:TODO 内部电子邮件主表
 * @author: 刘绍全
 * @date: 2019年1月14日 下午7:48:08
 * 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved.
 *             注意：本内容仅限于江苏稠云科信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class EmailService{
	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	@Autowired
	private EmailMapper emailMapper;
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${mail.fromMail.addr}")
	private String from;

	public int insertEmail(Email email) {
		return emailMapper.insert(email);
	}

	public int deleteEmail(Email email) {
		return emailMapper.delete(email);
	}

	public Email selectOneEmail(Email email) {
		return emailMapper.selectOne(email);
	}

	public int updateEmail(Email email, Example example) {
		return emailMapper.updateByExampleSelective(email, example);
	}
	
	/**
	 * 
	 * @Title: getEmailListForDesk   
	 * @Description: TODO 获取桌面个人邮件列表
	 * @param: orgId
	 * @param: accountId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>> getEmailListForDesk(String orgId,String accountId)
	{
		return emailMapper.getEmailListForDesk(orgId, accountId);
	}

	/**
	 * 获取个人邮件列表
	 */
	
	public List<Map<String, Object>> getMyEmailAll(String orgId, String accountId, String boxId,String search  ) {
		// TODO Auto-generated method stub
		return emailMapper.getMyEmailAll(orgId, accountId, boxId,search);
	}
	/**
	 * 获取个人邮件列表
	 */
	public PageInfo<Map<String, Object>> getMyEmailAll(Account account, Integer pageNumber,Integer pageSize, String orderBy,String search, String boxId) {
		PageHelper.orderBy(orderBy);
		PageHelper.startPage(pageNumber, pageSize);
		List<Map<String,Object>> datalist= emailMapper.getMyEmailAll(account.getOrgId(),account.getAccountId(),boxId,search);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
		return pageInfo;
	}
	/**
	 * 
	* @Title: getMyStarEmail 
	* @Description: TODO 获取个人标星邮件列表
	* @param @param account
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param orderBy
	* @param @param search
	* @param @return 设定文件 
	* @return PageInfo<Map<String,Object>> 返回类型 

	 */
	public PageInfo<Map<String, Object>> getMyStarEmail(Account account, Integer pageNumber,Integer pageSize, String orderBy,String search) {
		PageHelper.orderBy(orderBy);
		PageHelper.startPage(pageNumber, pageSize);
		List<Map<String,Object>> datalist= emailMapper.getMyStarEmail(account.getOrgId(),account.getAccountId(),search);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
		return pageInfo;
	}
	
	/**
	 * 
	* @Title: getMyDelEmailAll 
	* @Description: TODO 获取回收站邮件列表
	* @param @param account
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param orderBy
	* @param @param search
	* @param @return 设定文件 
	* @return PageInfo<Map<String,Object>> 返回类型 

	 */
	public PageInfo<Map<String, Object>> getMyDelEmailAll(Account account, Integer pageNumber,Integer pageSize, String orderBy,String search) {
		PageHelper.orderBy(orderBy);
		PageHelper.startPage(pageNumber, pageSize);
		List<Map<String,Object>> datalist= emailMapper.getMyDelEmailAll(account.getOrgId(),account.getAccountId(),search);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
		return pageInfo;
	}
	
	/**
	 * 获取内部邮件详情
	 */
	
	public Map<String, Object> getEmailDetails(String orgId, String accountId, String emailId) {
		// TODO Auto-generated method stub
		return emailMapper.getEmailDetails(orgId, accountId, emailId);
	}

	/**
	 * 获取各类型邮件总数
	 */
	
	public Map<String, Object> getEmailCount(String orgId, String accountId) {
		// TODO Auto-generated method stub
		return emailMapper.getEmailCount(orgId, accountId);
	}

	/**
	 * 平台发送简单邮件
	 */
	
	public void sendSimpleWebEmail(String to, String subject, String content) {
		try {
			if (StringUtils.isNotBlank(to)) {
				SimpleMailMessage message = new SimpleMailMessage();// 创建简单邮件消息
				message.setFrom(from);// 设置发送人
				if (to.indexOf(",") >-1) {
					String[] adds = to.split(","); // 同时发送给多人
					message.setTo(adds);
				} else {
					message.setTo(to);// 设置收件人
				}
				message.setSubject(subject);// 设置主题
				message.setText(content);// 设置内容
				mailSender.send(message);// 执行发送邮件
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
/**
 * 平台发送html格式邮件
 */
	
	public void sendHtmlWebEmail(String to, String subject, String content) {
		if (StringUtils.isNotBlank(to)) {
			MimeMessage message = mailSender.createMimeMessage();// 创建一个MINE消息
			try {
				// true表示需要创建一个multipart message
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setFrom(from);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(content, true);
				mailSender.send(message);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
/**
 * 平台发送带附件的邮件
 */
	
	public void sendAttachmentsWebEmail(String to, String subject, String content, List<String> filePath) {
		MimeMessage message = mailSender.createMimeMessage();// 创建一个MINE消息
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);// true表示这个邮件是有附件的
			for(int i=0;i<filePath.size();i++)
			{
				FileSystemResource file = new FileSystemResource(new File(filePath.get(i)));// 创建文件系统资源
				String fileName = filePath.get(i).substring(filePath.get(i).lastIndexOf(File.separator));
				helper.addAttachment(fileName, file);// 添加附件
			}
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
/**
 * 平台发送带静态资源的邮件
 */
	
	public void sendInlineResourceWebEmail(String to, String subject, String content, String rscPath, String rscId) {
		// TODO Auto-generated method stub
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			FileSystemResource res = new FileSystemResource(new File(rscPath));
			// 添加内联资源，一个id对应一个资源，最终通过id来找到该资源
			helper.addInline(rscId, res);// 添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 helper.addInline(rscId, res)
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title sendWebMailPerson   
	 * @Description TODO 走用户自己的邮件账号
	 * @param emailConfig
	 * @param toEmail
	 * @param subject
	 * @param content
	 * @param files
	 * @return
	 * boolean
	 */
	@SuppressWarnings("restriction")
	public boolean sendWebMailPerson(EmailConfig emailConfig, String toEmail, String subject, String content,
			List<String> files) throws AddressException, MessagingException {
		try {
			Properties properties = new Properties();
			Session session = null;
			if (emailConfig.getType().equals("1")) {
				Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				properties.setProperty("mail.smtp.host", emailConfig.getSmtp());
				properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
				properties.setProperty("mail.smtp.socketFactory.fallback", "false");
				properties.setProperty("mail.smtp.port", emailConfig.getPort());
				properties.setProperty("mail.smtp.socketFactory.port", emailConfig.getPort());
				properties.setProperty("mail.smtp.auth", "true");
				MailSSLSocketFactory sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true);
				properties.put("mail.smtp.ssl.enable", "true");
				properties.put("mail.smtp.ssl.socketFactory", sf);
				session = Session.getInstance(properties, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(emailConfig.getEmail(), emailConfig.getPassWord());
					}
				});
			} else {
				properties.setProperty("mail.smtp.auth", "true");// 设置验证机制
				properties.setProperty("mail.transport.protocol", "smtp");// 发送邮件协议
				properties.setProperty("mail.smtp.host", emailConfig.getSmtp());// 设置邮箱服务器地址
				properties.setProperty("mail.smtp.port", emailConfig.getPort());
				session = Session.getInstance(properties,
						new MyAuthenticator(emailConfig.getEmail(), emailConfig.getPassWord()));
			}
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailConfig.getEmail()));
			message.setSubject(subject);
			MimeMultipart mimeMuti = new MimeMultipart("mixed");
			MimeBodyPart contentBodyPart = new MimeBodyPart();
			if(StringUtils.isNotBlank(emailConfig.getSign()))
			{
				contentBodyPart.setContent(content+"<p>"+emailConfig.getSign()+"</p>", "text/html;charset=utf-8");
			}else
			{
				contentBodyPart.setContent(content, "text/html;charset=utf-8");
			}
			mimeMuti.addBodyPart(contentBodyPart);
			for (int i = 0; i < files.size(); i++) {
				File file = new File(files.get(i));
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(file);
				attachmentBodyPart.setDataHandler(new DataHandler(source));
				attachmentBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
				mimeMuti.addBodyPart(attachmentBodyPart);
			}
			message.setContent(mimeMuti);
			message.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail));// 接收人
			message.saveChanges();
			try {
				Transport.send(message);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @Title sendWebMailPersonByOrg   
	 * @Description TODO 机构内的邮件服务器发送个人邮件   
	 * @param sysConfig
	 * @param toEmail
	 * @param subject
	 * @param content
	 * @param files
	 * @return
 AddressException
 MessagingException      
	 * boolean
	 */
	
	@SuppressWarnings("restriction")
	public boolean sendWebMailPersonByOrg(SysConfig sysConfig, String toEmail, String subject, String content,
			List<String> files) throws AddressException, MessagingException {
		try {
			Properties properties = new Properties();
			Session session = null;
			if (sysConfig.getEmailType().equals("1")) {
				Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				properties.setProperty("mail.smtp.host", sysConfig.getSmtp());
				properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
				properties.setProperty("mail.smtp.socketFactory.fallback", "false");
				properties.setProperty("mail.smtp.port", sysConfig.getPort());
				properties.setProperty("mail.smtp.socketFactory.port", sysConfig.getPort());
				properties.setProperty("mail.smtp.auth", "true");
				MailSSLSocketFactory sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true);
				properties.put("mail.smtp.ssl.enable", "true");
				properties.put("mail.smtp.ssl.socketFactory", sf);
				session = Session.getInstance(properties, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(sysConfig.getEmail(), sysConfig.getPassWord());
					}
				});
			} else {
				properties.setProperty("mail.smtp.auth", "true");// 设置验证机制
				properties.setProperty("mail.transport.protocol", "smtp");// 发送邮件协议
				properties.setProperty("mail.smtp.host", sysConfig.getSmtp());// 设置邮箱服务器地址
				properties.setProperty("mail.smtp.port", sysConfig.getPort());
				session = Session.getInstance(properties,
						new MyAuthenticator(sysConfig.getEmail(), sysConfig.getPassWord()));
			}
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sysConfig.getEmail()));
			message.setSubject(subject);
			MimeMultipart mimeMuti = new MimeMultipart("mixed");
			MimeBodyPart contentBodyPart = new MimeBodyPart();
			contentBodyPart.setContent(content, "text/html;charset=utf-8");
			mimeMuti.addBodyPart(contentBodyPart);
			for (int i = 0; i < files.size(); i++) {
				File file = new File(files.get(i));
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(file);
				attachmentBodyPart.setDataHandler(new DataHandler(source));
				attachmentBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
				mimeMuti.addBodyPart(attachmentBodyPart);
			}
			message.setContent(mimeMuti);
			message.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail));// 接收人
			message.saveChanges();
			try {
				Transport.send(message);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除个人的邮件(逻辑删除)
	 */
	
	public int delMyEmail(String orgId, String accountId, List<String> list) {
		// TODO Auto-generated method stub
		return emailMapper.delMyEmail(orgId, accountId, list);
	}

	/**
	 * 批量取消星标记
	 */
	
	public int updateSetStars(String orgId, String accountId, List<String> list) {
		// TODO Auto-generated method stub
		return emailMapper.updateSetStars(orgId, accountId, list);
	}

/**
 * 获取标星邮件
 */
	
	public List<Map<String, Object>> getMyStarEmail(String orgId, String accountId, String search) {
		// TODO Auto-generated method stub
		return emailMapper.getMyStarEmail(orgId, accountId, search);
	}

	/**
	 * 获取回收站邮件
	 */

public List<Map<String, Object>> getMyDelEmailAll(String orgId, String accountId, String search) {
	// TODO Auto-generated method stub
	return emailMapper.getMyDelEmailAll(orgId, accountId, search);
}

	/**
	 * 物事删除个人邮件
	 */
	
	public int delMyEmailPhysics(String orgId, String accountId, List<String> list) {
		// TODO Auto-generated method stub
		return emailMapper.delMyEmailPhysics(orgId, accountId, list);
	}

	/**
	 * 恢复回收站中的个人邮件
	 */
	
	public int recoveryMyEmail(String orgId, String accountId, List<String> list) {
		// TODO Auto-generated method stub
		return emailMapper.recoveryMyEmail(orgId, accountId, list);
	}

	/**
	 * 设置个人邮件文件夹
	 */
	
	public int setMyEmailBox(String boxId, String orgId, String accountId, List<String> list) {
		// TODO Auto-generated method stub
		return emailMapper.setMyEmailBox(boxId, orgId, accountId, list);
	}
	/**
	 * 
	 * @Title: getMyEmailAllForMobile   
	 * @Description: TODO 移动端内部邮件
	 * @param orgId
	 * @param accountId
	 * @param page
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyEmailAllForMobile(String orgId,String accountId,Integer page)
	{
		return emailMapper.getMyEmailAllForMobile(orgId, accountId, page);
	}
}
