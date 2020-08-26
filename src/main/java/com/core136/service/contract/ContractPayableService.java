package com.core136.service.contract;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.contract.ContractPayable;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.contract.ContractPayableMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ContractPayableService {
@Autowired
private ContractPayableMapper contractPayableMapper;

public int insertContractPayable(ContractPayable contractPayable)
{
	return contractPayableMapper.insert(contractPayable);
}

public int deleteContractPayable(ContractPayable contractPayable)
{
	return contractPayableMapper.delete(contractPayable);
}

public int updateContractPayable(ContractPayable contractPayable,Example example)
{
	return contractPayableMapper.updateByExampleSelective(contractPayable, example);
}
public ContractPayable selectOneContractPayable(ContractPayable contractPayable)
{
	return contractPayableMapper.selectOne(contractPayable);
}
/**
 * 
 * @Title: getContractPayableList   
 * @Description: TODO 获取应付款列表 
 * @param: orgId
 * @param: userPriv
 * @param: beginTime
 * @param: endTime
 * @param: status
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getContractPayableList(String orgId,String opFlag,String accountId,String beginTime,String endTime,String status,String search)
{
	return contractPayableMapper.getContractPayableList(orgId, opFlag,accountId, beginTime, endTime, status,"%"+search+"%");
}
/**
 * 
 * @Title: getContractPayableList   
 * @Description: TODO 获取应付款列表 
 * @param: pageParam
 * @param: opFlag
 * @param: beginTime
 * @param: endTime
 * @param: status
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getContractPayableList(PageParam pageParam,String beginTime, String endTime,String status) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getContractPayableList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),beginTime, endTime,status,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getDeskPayableList   
 * @Description: TODO 获取财务门户近期付款记录
 * @param orgId
 * @param beginTime
 * @param opFlag
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getDeskPayableList(String orgId,String beginTime,String opFlag,String accountId)
{
	return contractPayableMapper.getDeskPayableList(orgId, beginTime, opFlag, accountId);
}

}
