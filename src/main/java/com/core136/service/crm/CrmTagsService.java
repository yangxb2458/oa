/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  CrmTagsService.java   
 * @Package com.core136.service.crm   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月8日 上午9:42:41   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.crm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.crm.CrmTags;
import com.core136.mapper.crm.CrmTagsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  CrmTagsService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月8日 上午9:42:41   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class CrmTagsService{
@Autowired
private CrmTagsMapper crmTagsMapper;
/**
 * 
* @Title: getAllTags 
* @Description: TODO 获取所属行业的标签
* @param @param orgId
* @param @return 设定文件 
* @return List<CrmTags> 返回类型 

 */
public List<CrmTags> getAllTags(String orgId,String industryId)
{
	Example example = new Example(CrmTags.class);
	example.setOrderByClause("INDUSTRY_ID DESC");
	example.createCriteria().andEqualTo("orgId",orgId).andEqualTo("industryId",industryId);
	return crmTagsMapper.selectByExample(example);
}

/**
 * 
* @Title: getAllTags 
* @Description: TODO 获取所有行业标签
* @param @param orgId
* @param @return 设定文件 
* @return List<CrmTags> 返回类型 

 */
public List<CrmTags> getAllTags(String orgId)
{
	Example example = new Example(CrmTags.class);
	example.setOrderByClause("INDUSTRY_ID DESC");
	example.createCriteria().andEqualTo("orgId",orgId);
	return crmTagsMapper.selectByExample(example);
}


public CrmTags selectOneCrmTags(CrmTags crmTags)
{
	return crmTagsMapper.selectOne(crmTags);
}

public int insertCrmTags(CrmTags crmTags)
{
	return crmTagsMapper.insert(crmTags);
}

public int updateCrmTags(CrmTags crmTags,Example example)
{
	return crmTagsMapper.updateByExampleSelective(crmTags, example);
}

public int deleteCrmTags(CrmTags crmTags)
{
	return crmTagsMapper.delete(crmTags);
}


public PageInfo<Map<String, Object>> getAllTagsList(int pageNumber,int pageSize,String orderBy,String orgId, String search) {
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String, Object>> datalist= this.getAllTagsList(orgId, search);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(datalist);
	return pageInfo;
}


/*
 * 获取企业标签列表
 */

public List<Map<String, Object>> getAllTagsList(String orgId, String search) {
	// TODO Auto-generated method stub
	return crmTagsMapper.getAllTagsList(orgId, search);
}


}
