/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  NoticeTemaplateService.java   
 * @Package com.core136.service.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年7月12日 上午11:31:55   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.notice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.notice.NoticeTemplate;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.notice.NoticeTemplateMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  NoticeTemaplateService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年7月12日 上午11:31:55   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class NoticeTemplateService {
@Autowired
private NoticeTemplateMapper noticeTemplateMapper;

public int insertNoticeTemplate(NoticeTemplate noticeTemplate)
{
	return noticeTemplateMapper.insert(noticeTemplate);
}

public int updateNoticeTemplate(NoticeTemplate noticeTemplate,Example example)
{
	return noticeTemplateMapper.updateByExampleSelective(noticeTemplate, example);
}

public int delNoticeTemplate(NoticeTemplate noticeTemplate)
{
	return noticeTemplateMapper.delete(noticeTemplate);
}

public NoticeTemplate selectOneNoticeTemplate(NoticeTemplate noticeTemplate)
{
	return noticeTemplateMapper.selectOne(noticeTemplate);
}
/**
 * 
 * @Title: getNoticeTemplateList   
 * @Description: TODO 获取通知公告模版
 * @param: orgId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getNoticeTemplateList(String orgId,String search)
{
	return noticeTemplateMapper.getNoticeTemplateList(orgId, "%"+search+"%");
}

/**
 * 
* @Title: getNoticeTemplateList 
* @Description: TODO 获取通知公告模版
* @param @param pageParam
* @param @return 设定文件 
* @return PageInfo<Map<String,String>> 返回类型
 */
public PageInfo<Map<String, String>> getNoticeTemplateList(PageParam pageParam) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getNoticeTemplateList(pageParam.getOrgId(),pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
* @Title: getRedHeadListByType 
* @Description: TODO 按分类获取红头列表
* @param @param orgId
* @param @param noticeType
* @param @return 设定文件 
* @return List<Map<String,String>> 返回类型
 */
public List<Map<String,String>> getRedHeadListByType(String orgId,String noticeType)
{
	return noticeTemplateMapper.getRedHeadListByType(orgId, noticeType);
}
}
