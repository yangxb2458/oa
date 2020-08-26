/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  CrmInquiryService.java   
 * @Package com.core136.service.crm   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年5月5日 上午10:27:34   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.crm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core136.bean.crm.CrmInquiry;
import com.core136.bean.crm.CrmInquiryDetail;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.crm.CrmInquiryMapper;
import org.core136.common.utils.SysTools;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  CrmInquiryService   
 * @Description:TODO 询价单主表
 * @author: 稠云信息 
 * @date:   2019年5月5日 上午10:27:34   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class CrmInquiryService{
@Autowired
private CrmInquiryMapper crmInquiryMapper;
@Autowired
private CrmInquiryDetailService crmInquiryDetailService;

public CrmInquiry selectOneCrmInquiry(CrmInquiry crmInquiry)
{
	return crmInquiryMapper.selectOne(crmInquiry);
}

public int insertCrmInquiry(CrmInquiry crmInquiry)
{
	return crmInquiryMapper.insert(crmInquiry);
}

public int deleteCrmInquiry(CrmInquiry crmInquiry)
{
	return crmInquiryMapper.delete(crmInquiry);
}

public int updateCrmInquiry(CrmInquiry crmInquiry,Example example)
{
	return crmInquiryMapper.updateByExampleSelective(crmInquiry, example);
}

/**
 * 
 * @Title: updateInquiryAndDetail   
 * @Description: TODO 更新询价单
 * @param crmInquiry
 * @param example
 * @param jsonArr
 * @return
 * int    
 * @throws
 */
@Transactional(value="generalTM")
public int updateInquiryAndDetail(CrmInquiry crmInquiry,Example example,JSONArray jsonArr)
{
	CrmInquiryDetail crmInquiryDetaildel = new CrmInquiryDetail();
	crmInquiryDetaildel.setInquiryId(crmInquiry.getInquiryId());
	crmInquiryDetaildel.setOrgId(crmInquiry.getOrgId());
	crmInquiryDetailService.deleteCrmInquiryDetail(crmInquiryDetaildel);
	for(int i=0;i<jsonArr.size();i++)
	{
		CrmInquiryDetail crmInquiryDetail=JSONObject.parseObject(jsonArr.get(i).toString(),CrmInquiryDetail.class);
		crmInquiryDetail.setDetailId(SysTools.getGUID());
		crmInquiryDetail.setInquiryId(crmInquiry.getInquiryId());
		crmInquiryDetail.setOrgId(crmInquiry.getOrgId());
		crmInquiryDetail.setCreateTime(crmInquiry.getCreateTime());
		crmInquiryDetail.setCreateUser(crmInquiry.getCreateUser());
		crmInquiryDetailService.insertCrmInquiryDetail(crmInquiryDetail);
	}
	return updateCrmInquiry(crmInquiry,example);
}


/**
 * 
* @Title: saveCrmInquiry 
* @Description: TODO 保存询价单
* @param @param crmInquiry
* @param @param detailList
* @param @return 设定文件 
* @return int 返回类型 

 */
@Transactional(value="generalTM")
public int saveCrmInquiry(CrmInquiry crmInquiry,JSONArray jsonArr)
{
	for(int i=0;i<jsonArr.size();i++)
	{
		CrmInquiryDetail crmInquiryDetail=JSONObject.parseObject(jsonArr.get(i).toString(),CrmInquiryDetail.class);
		crmInquiryDetail.setDetailId(SysTools.getGUID());
		crmInquiryDetail.setInquiryId(crmInquiry.getInquiryId());
		crmInquiryDetail.setOrgId(crmInquiry.getOrgId());
		crmInquiryDetail.setCreateTime(crmInquiry.getCreateTime());
		crmInquiryDetail.setCreateUser(crmInquiry.getCreateUser());
		crmInquiryDetailService.insertCrmInquiryDetail(crmInquiryDetail);
	}
	return insertCrmInquiry(crmInquiry);
}

/** 
* @Title: getCrmInquiryList 
* @Description: TODO获限权限内的询价单列表 
* @param @param orgId
* @param @param opFlag
* @param @param accountId
* @param @param search
* @param @return 设定文件 
*/ 

public List<Map<String, String>> getCrmInquiryList(String orgId, String opFlag, String accountId,String beginTime,String endTime,String customerType,String status, String search) {
	// TODO Auto-generated method stub
	return crmInquiryMapper.getCrmInquiryList(orgId, opFlag, accountId,beginTime,endTime,customerType,status, "%"+search+"%");
}
/**
 *  
* @Title: getCrmInquiryList 
* @Description: TODO获限权限内的询价单列表 
* @param @param pageNumber
* @param @param pageSize
* @param @param orderBy
* @param @param orgId
* @param @param opFlag
* @param @param accountId
* @param @param search
* @param @return 设定文件 
* @return PageInfo<Map<String,String>> 返回类型
 */
public PageInfo<Map<String, String>> getCrmInquiryList(PageParam pageParam,String beginTime,String endTime,String customerType,String status) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= crmInquiryMapper.getCrmInquiryList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(), beginTime,endTime,customerType,status,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
