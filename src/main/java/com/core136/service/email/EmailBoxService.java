/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  EmailBoxService.java   
 * @Package com.core136.service.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 刘绍全     
 * @date:   2019年1月14日 下午7:53:33   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.email;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.email.EmailBox;
import com.core136.mapper.email.EmailBoxMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  EmailBoxService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 刘绍全 
 * @date:   2019年1月14日 下午7:53:33   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云科信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class EmailBoxService{

	@Autowired
	private EmailBoxMapper emailBoxMapper;
	
	public int insertEmailBox(EmailBox emailBox)
	{
		return emailBoxMapper.insert(emailBox);
	}
	
	public int updateEmailBox(EmailBox emailBox,Example example)
	{
		return emailBoxMapper.updateByExampleSelective(emailBox, example);
	}
	
	public int deleteEmailBox(EmailBox emailBox)
	{
		return emailBoxMapper.delete(emailBox);
	}
	
	public EmailBox selectOneEmailBox(EmailBox emailBox)
	{
		return emailBoxMapper.selectOne(emailBox);
	}
	public List<EmailBox> selectAll(Example example)
	{
		return emailBoxMapper.selectByExample(example);
	}

	/**
	 * 获取内部邮件自定义文件夹
	 */
	
	public List<Map<String, Object>> getEmailBoxList(String orgId, String accountId) {
		// TODO Auto-generated method stub
		return emailBoxMapper.getEmailBoxList(orgId, accountId);
	}
	
	
	public PageInfo<Map<String, Object>> getEmailBoxManageList(Account account, Integer pageNumber,Integer pageSize, String orderBy,String search) {
		PageHelper.orderBy(orderBy);
		PageHelper.startPage(pageNumber, pageSize);
		List<Map<String, Object>> datalist= emailBoxMapper.getEmailBoxManageList(account.getOrgId(),account.getAccountId(),search);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(datalist);
		return pageInfo;
	}

	/**
	 * 个人邮件文件夹管理列表
	 */
	
	public List<Map<String, Object>> getEmailBoxManageList(String orgId, String accountId, String search) {
		// TODO Auto-generated method stub
		return emailBoxMapper.getEmailBoxManageList(orgId, accountId, search);
	}
}
