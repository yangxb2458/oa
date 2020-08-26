/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  NoticeConfigService.java   
 * @Package com.core136.service.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年7月8日 下午12:32:19   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.notice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core136.bean.account.Account;
import com.core136.bean.notice.NoticeConfig;
import com.core136.bean.sys.CodeClass;
import com.core136.mapper.notice.NoticeConfigMapper;
import com.core136.service.sys.CodeClassService;
import org.core136.common.utils.SysTools;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  NoticeConfigService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年7月8日 下午12:32:19   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class NoticeConfigService {
@Autowired
private NoticeConfigMapper noticeConfigMapper;
@Autowired
private CodeClassService codeClassService;

/**
 * 
* @Title: insertNoticeConfig 
* @Description: TODO 创建通知公告配置
* @param @param noticeConfig
* @param @return 设定文件 
* @return int 返回类型
 */
public int insertNoticeConfig(NoticeConfig noticeConfig)
{
	return noticeConfigMapper.insert(noticeConfig);
}

public NoticeConfig selectOneNoticeConfig(NoticeConfig noticeConfig)
{
	return noticeConfigMapper.selectOne(noticeConfig);
}

/**
 * 
* @Title: updateNoticeConfig 
* @Description: TODO 更新通知公告配置
* @param @param noticeConfig
* @param @param example
* @param @return 设定文件 
* @return int 返回类型
 */
public int updateNoticeConfig(NoticeConfig noticeConfig,Example example)
{
	return noticeConfigMapper.updateByExampleSelective(noticeConfig, example);
}
/**
 * 
* @Title: isExist 
* @Description: TODO 判断当前机构是否有通知公告配置
* @param @param noticeConfig
* @param @return 设定文件 
* @return int 返回类型
 */
public int isExist(NoticeConfig noticeConfig)
{
	return noticeConfigMapper.selectCount(noticeConfig);
}

/**
 * 
* @Title: initNocticeConfig 
* @Description: TODO 初始化通知公告配置
* @param @param account
* @param @param module 设定文件 
* @return void 返回类型
*/
@Transactional(value="generalTM")
public int initNocticeConfig(Account account,String module)
{
	CodeClass codeClass = new CodeClass();
	codeClass.setOrgId(account.getOrgId());
	codeClass.setModule(module);
	List<CodeClass> codeClassList = codeClassService.getCodeClassList(codeClass);
	for(int i=0;i<codeClassList.size();i++)
	{
		NoticeConfig noticeConfig = new NoticeConfig();
		noticeConfig.setOrgId(account.getOrgId());
		noticeConfig.setCodeClassId(codeClassList.get(i).getCodeClassId());
		noticeConfig.setApproverType("0");
		if(isExist(noticeConfig)==0)
		{
			noticeConfig.setConfigId(SysTools.getGUID());
			noticeConfig.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			noticeConfig.setCreateUser(account.getAccountId());
			noticeConfig.setApproverType("0");
			noticeConfig.setNoticeType(codeClassList.get(i).getCodeValue());
			insertNoticeConfig(noticeConfig);
		}
		NoticeConfig noticeConfig1 = new NoticeConfig();
		noticeConfig1.setOrgId(account.getOrgId());
		noticeConfig1.setCodeClassId(codeClassList.get(i).getCodeClassId());
		noticeConfig1.setApproverType("1");
		if(isExist(noticeConfig1)==0)
		{
			noticeConfig1.setConfigId(SysTools.getGUID());
			noticeConfig1.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			noticeConfig1.setCreateUser(account.getAccountId());
			noticeConfig1.setNoticeType(codeClassList.get(i).getCodeValue());
			noticeConfig1.setApproverType("1");
			insertNoticeConfig(noticeConfig1);
		}
	}
	return 1;
}


public PageInfo<Map<String, String>> getApproverUserList(int pageNumber,int pageSize,String orderBy,String orgId) {
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String,String>> datalist= getApproverUserList(orgId);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
* @Title: getApproverUserList 
* @Description: TODO 审批人员列表
* @param @param orgId
* @param @return 设定文件 
* @return List<Map<String,String>> 返回类型
 */
public List<Map<String,String>> getApproverUserList(String orgId)
{
	return noticeConfigMapper.getApproverUserList(orgId);
}

/**
 * 
* @Title: getNotApproverUserList 
* @Description: TODO 免审人员列表
* @param @param orgId
* @param @return 设定文件 
* @return List<Map<String,String>> 返回类型
 */
public List<Map<String,String>> getNotApproverUserList(String orgId)
{
	return noticeConfigMapper.getNotApproverUserList(orgId);
}

public PageInfo<Map<String, String>> getNotApproverUserList(int pageNumber,int pageSize,String orderBy,String orgId) {
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String,String>> datalist= getNotApproverUserList(orgId);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
