/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  EmailConfigService.java   
 * @Package com.core136.service.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 刘绍全     
 * @date:   2019年1月14日 下午7:52:15   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.email.EmailConfig;
import com.core136.mapper.email.EmailConfigMapper;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  EmailConfigService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 刘绍全 
 * @date:   2019年1月14日 下午7:52:15   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云科信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class EmailConfigService{
@Autowired
private EmailConfigMapper emailConfigMapper;
public int insertEmailConfig(EmailConfig emailConfig)
{
	return emailConfigMapper.insert(emailConfig);
}
public int deleteEmailConfig(EmailConfig emailConfig)
{
	return emailConfigMapper.delete(emailConfig);
}
public EmailConfig selectOneEmailConfig(EmailConfig emailConfig)
{
	return emailConfigMapper.selectOne(emailConfig);
}
public int updateEmailConfig(EmailConfig emailConfig,Example example)
{
	return emailConfigMapper.updateByExampleSelective(emailConfig, example);
}
/**
 * 
* @Title: setEmailConfig 
* @Description: TODO 更新个人WEB邮件配置
* @param @param emailConfig
* @param @return 设定文件 
* @return int 返回类型
 */
public int setEmailConfig(EmailConfig emailConfig)
{
	EmailConfig newEmailConfig = new EmailConfig();
	newEmailConfig.setOrgId(emailConfig.getOrgId());
	newEmailConfig.setAccountId(emailConfig.getAccountId());
	newEmailConfig = selectOneEmailConfig(newEmailConfig);
	if(newEmailConfig==null)
	{
		emailConfig.setConfigId(SysTools.getGUID());
		return insertEmailConfig(emailConfig);
	}else
	{
		Example example = new Example(EmailConfig.class);
		example.createCriteria().andEqualTo("orgId",emailConfig.getOrgId()).andEqualTo("accountId",emailConfig.getAccountId());
		return updateEmailConfig(emailConfig,example);
	}
}


}
