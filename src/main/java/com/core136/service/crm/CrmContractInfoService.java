/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  CrmContractInfoService.java   
 * @Package com.core136.service.crm   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月24日 下午3:28:31   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.crm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.crm.CrmContractInfo;
import com.core136.mapper.crm.CrmContractInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  CrmContractInfoService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月24日 下午3:28:31   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class CrmContractInfoService{
@Autowired
private CrmContractInfoMapper crmContractInfoMapper;

public int insertCrmContractInfoMapper(CrmContractInfo crmContractInfo)
{
	return crmContractInfoMapper.insert(crmContractInfo);
}

public CrmContractInfo selectOneCrmContractInfo(CrmContractInfo crmContractInfo)
{
	return crmContractInfoMapper.selectOne(crmContractInfo);
}

public List<CrmContractInfo> selectCrmContractInfoList(CrmContractInfo crmContractInfo)
{
	return crmContractInfoMapper.select(crmContractInfo);
}

public int deleteCrmContractInfo(CrmContractInfo crmContractInfo)
{
	return crmContractInfoMapper.delete(crmContractInfo);
}

public int updateCrmContractInfo(CrmContractInfo crmContractInfo,Example example)
{
	return crmContractInfoMapper.updateByExampleSelective(crmContractInfo, example);
}

/**
 * 获取客户银行账户信息 
 */

public List<Map<String, Object>> getCrmContractInfoList(String orgId, String search) {
	return crmContractInfoMapper.getCrmContractInfoList(orgId, search);
}


/**
 * 获取客户银行账户信息 
 */

public PageInfo<Map<String, Object>> getCrmContractInfoList(int pageNumber,int pageSize,String orderBy,String orgId,String search) {
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String,Object>> datalist= this.getCrmContractInfoList(orgId,search);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}

/**
 * 判断是否已经存在
 */

public int isExistChild(String orgId, String customerId) {
	// TODO Auto-generated method stub
	return crmContractInfoMapper.isExistChild(orgId, customerId);
}

/**
 * 获取银行信息详情
 */

public Map<String, Object> getContractInfoById(String orgId, String contractInfoId) {
	// TODO Auto-generated method stub
	return crmContractInfoMapper.getContractInfoById(orgId, contractInfoId);
}

}
