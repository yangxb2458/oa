package com.core136.service.contract;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.contract.ContractReceivables;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.contract.ContractReceivablesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ContractReceivablesService {
@Autowired
private ContractReceivablesMapper contractReceivablesMapper;

public int insertContractReceivables(ContractReceivables contractReceivables)
{
	return contractReceivablesMapper.insert(contractReceivables);
}

public int deleteContractReceivables(ContractReceivables contractReceivables)
{
	return contractReceivablesMapper.delete(contractReceivables);
}
public int updateContractReceivables(ContractReceivables contractReceivables,Example example)
{
	return contractReceivablesMapper.updateByExampleSelective(contractReceivables, example);
}
public ContractReceivables selectOneContractReceivables(ContractReceivables contractReceivables)
{
	return contractReceivablesMapper.selectOne(contractReceivables);
}
/**
 * 	
 * @Title: getContractReceivablesList   
 * @Description: TODO  获取应收款列表 
 * @param: orgId
 * @param: opFlag
 * @param: userPriv
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,Object>>getContractReceivablesList(String orgId,String userPriv,String beginTime,String endTime,String status,String search)
{
	return contractReceivablesMapper.getContractReceivablesList(orgId,userPriv, beginTime, endTime,status, "%"+search+"%");
}

/**
 * 
 * @Title: getContractReceivablesList   
 * @Description: TODO 获取应收款列表 
 * @param: pageParam
 * @param: status
 * @param: userPriv
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, Object>> getContractReceivablesList(PageParam pageParam,String userPriv,String beginTime, String endTime,String status) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,Object>> datalist= getContractReceivablesList(pageParam.getOrgId(),userPriv,beginTime, endTime,status,pageParam.getSearch());
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}

public List<Map<String, String>>getDeskReceivablesList(String orgId,String beginTime,String opFlag,String accountId)
{
	return contractReceivablesMapper.getDeskReceivablesList(orgId, beginTime, opFlag, accountId);
}

}
