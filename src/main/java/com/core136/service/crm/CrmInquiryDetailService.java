/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  CrmInquiryDetailService.java   
 * @Package com.core136.service.crm   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年5月5日 上午10:29:16   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.crm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.crm.CrmInquiryDetail;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.crm.CrmInquiryDetailMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  CrmInquiryDetailService   
 * @Description:TODO 询价单详情
 * @author: 稠云信息 
 * @date:   2019年5月5日 上午10:29:16   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class CrmInquiryDetailService{
@Autowired
private CrmInquiryDetailMapper crmInquiryDetailMapper;

public CrmInquiryDetail selectOneCrmInquiryDetail (CrmInquiryDetail crmInquiryDetail)
{
	return crmInquiryDetailMapper.selectOne(crmInquiryDetail);
}

public int insertCrmInquiryDetail(CrmInquiryDetail crmInquiryDetail)
{
	return crmInquiryDetailMapper.insert(crmInquiryDetail);
}
public int deleteCrmInquiryDetail(CrmInquiryDetail crmInquiryDetail)
{
	return crmInquiryDetailMapper.delete(crmInquiryDetail);
}
public int updateCrmInquiryDetail(CrmInquiryDetail crmInquiryDetail,Example example)
{
	return crmInquiryDetailMapper.updateByExampleSelective(crmInquiryDetail, example);
}

/**
 * 
 * @Title: getCrmInquiryDetailList   
 * @Description: TODO  询价单产品详情
 * @param orgId
 * @param inquiryId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getCrmInquiryDetailList(String orgId,String inquiryId)
{
	return crmInquiryDetailMapper.getCrmInquiryDetailList(orgId, inquiryId);
}
/**
 * 
 * @Title: getCrmInquiryDetailList   
 * @Description: TODO询价单产品详情
 * @param pageParam
 * @param inquiryId
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getCrmInquiryDetailList(PageParam pageParam,String inquiryId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getCrmInquiryDetailList(pageParam.getOrgId(), inquiryId);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}



}
