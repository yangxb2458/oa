/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ContractService.java   
 * @Package com.core136.service.contract   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月23日 上午11:21:17   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.contract;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.contract.Contract;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.contract.ContractMapper;
import com.core136.mapper.file.AttachMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  ContractService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月23日 上午11:21:17   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class ContractService{
	@Autowired
	private ContractMapper contractMapper;
	@Autowired
	private AttachMapper attachMapper;

public Contract selectOneContract(Contract contract)
{
	return contractMapper.selectOne(contract);
}

public int insertContract(Contract contract)
{
	return contractMapper.insert(contract);
}

public int deleteContract(Contract contract)
{
	return contractMapper.delete(contract);
}

public int updateContract(Contract contract,Example example)
{
	return contractMapper.updateByExampleSelective(contract, example);
}
	/**
	 * 获取年分的合同总数
	 */
	
	public int getContractCount(String orgId) {
		// TODO Auto-generated method stub
		return contractMapper.getContractCount(orgId);
	}

	/**
	 * 添加合同
	 */
	public int addContract(Contract contract) {
		contract.setContractId(SysTools.getGUID());
		return contractMapper.insert(contract);
	}
	
	/**
	 * 
	 * @Title: queryContract   
	 * @Description: TODO 合同查询
	 * @param: orgId
	 * @param: beginTime
	 * @param: endTime
	 * @param: contractType
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>queryContract(String orgId,String beginTime, String endTime, String contractType,String mySignUser, String search)
	{
		return contractMapper.queryContract(orgId, beginTime, endTime, contractType, mySignUser,"%"+search+"%");
	}
	/**
	 * 
	 * @Title: queryContract   
	 * @Description: TODO 合同查询
	 * @param: pageParam
	 * @param: beginTime
	 * @param: endTime
	 * @param: contractType
	 * @param: @return      
	 * @return: PageInfo<Map<String,String>>      
	 * @throws
	 */
	public PageInfo<Map<String, String>> queryContract(PageParam pageParam,String beginTime, String endTime, String contractType,String mySignUser) {
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= queryContract(pageParam.getOrgId(),beginTime, endTime,mySignUser,contractType,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	
	/**
	 * 
	 * @Title: getContractManageList   
	 * @Description: TODO 获取合同管理列表  
	 * @param: orgId
	 * @param: opFlag
	 * @param: accountId
	 * @param: beginTime
	 * @param: endTime
	 * @param: contractType
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getContractManageList(String orgId,String opFlag,String accountId,String beginTime, String endTime, String contractType, String search)
	{
		return contractMapper.getContractManageList(orgId,opFlag,accountId, beginTime, endTime, contractType, "%"+search+"%");
	}
	
	/**
	 * 
	 * @Title: getContractManageList   
	 * @Description: TODO 获取合同管理列表  
	 * @param: pageParam
	 * @param: beginTime
	 * @param: endTime
	 * @param: contractType
	 * @param: @return      
	 * @return: PageInfo<Map<String,String>>      
	 * @throws
	 */
	public PageInfo<Map<String, String>> getContractManageList(PageParam pageParam,String beginTime, String endTime, String contractType) {
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getContractManageList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),beginTime, endTime, contractType,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	/**
	 * 
	 * @Title: getSelect2ContractList   
	 * @Description: TODO SELECT2的列表
	 * @param: orgId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getSelect2ContractList(String orgId,String search)
	{
		return contractMapper.getSelect2ContractList(orgId, "%"+search+"%");
	}
/**
 * 
 * @Title: getContractTop   
 * @Description: TODO 获取近期的合同列表
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
	public List<Map<String, String>>getContractTop(String orgId)
	{
		return contractMapper.getContractTop(orgId);
	}
}
