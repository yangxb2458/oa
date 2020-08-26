/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ContractDetailsService.java   
 * @Package com.core136.service.contract   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月24日 上午9:30:41   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.contract;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.contract.ContractDetails;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.contract.ContractDetailsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  ContractDetailsService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月24日 上午9:30:41   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class ContractDetailsService{
@Autowired
private ContractDetailsMapper contractDetailsMapper;

public int insertContractDetails(ContractDetails contractDetails)
{
	return contractDetailsMapper.insert(contractDetails);
}

public int deleteContractDetails(ContractDetails contractDetails)
{
	return contractDetailsMapper.delete(contractDetails);
}

public ContractDetails selectOneContractDetails(ContractDetails contractDetails)
{
	return contractDetailsMapper.selectOne(contractDetails);
}

public int updateContractDetails(ContractDetails contractDetails,Example example)
{
	return contractDetailsMapper.updateByExampleSelective(contractDetails, example);
}

/**
 * 获取指定合同的产品明细
 */

public List<Map<String, Object>> getContractDetailsList(String orgId, String contractId,String search) {
	// TODO Auto-generated method stub
	return contractDetailsMapper.getContractDetailsList(orgId, contractId,"%"+search+"%");
}

/**
 * 
 * @Title: getContractDetailsList   
 * @Description: TODO 获取指定的合同明细
 * @param: pageParam
 * @param: contractId
 * @param: @return      
 * @return: PageInfo<Map<String,Object>>      
 * @throws
 */
public PageInfo<Map<String, Object>> getContractDetailsList(PageParam pageParam,String contractId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,Object>> datalist= contractDetailsMapper.getContractDetailsList(pageParam.getOrgId(),contractId,pageParam.getSearch());
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}
}
