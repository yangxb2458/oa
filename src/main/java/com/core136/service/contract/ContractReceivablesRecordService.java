package com.core136.service.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core136.bean.contract.ContractReceivables;
import com.core136.bean.contract.ContractReceivablesRecord;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.contract.ContractReceivablesRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ContractReceivablesRecordService {
@Autowired
private ContractReceivablesRecordMapper contractReceivablesRecordMapper;
@Autowired
private ContractReceivablesService contractReceivablesService;

/**
 * 
 * @Title: receivableAmount   
 * @Description: TODO 收款
 * @param contractReceivablesRecord
 * @return
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM") 
public RetDataBean receivableAmount(ContractReceivablesRecord contractReceivablesRecord)
{
	String receivablesId = contractReceivablesRecord.getReceivablesId();
	ContractReceivables contractReceivables = new ContractReceivables();
	contractReceivables.setReceivablesId(receivablesId);
	contractReceivables.setOrgId(contractReceivablesRecord.getOrgId());
	contractReceivables = contractReceivablesService.selectOneContractReceivables(contractReceivables);
	Double unrecevided = contractReceivables.getUnReceived();
	Double receivded = contractReceivables.getReceived();
	if(unrecevided<contractReceivablesRecord.getAmount())
	{
		return RetDataTools.NotOk("收款金额大于未收款金额！");
	}else
	{
		unrecevided = unrecevided - contractReceivablesRecord.getAmount();
		receivded = receivded+contractReceivablesRecord.getAmount();
		ContractReceivables contractReceivables1 = new ContractReceivables();
		contractReceivables1.setReceivablesId(contractReceivablesRecord.getReceivablesId());
		contractReceivables1.setUnReceived(unrecevided);
		contractReceivables1.setReceived(receivded);
		contractReceivables1.setReceivedTime(contractReceivablesRecord.getPayeeTime());
		contractReceivables1.setOrgId(contractReceivablesRecord.getOrgId());
		Example example = new Example(ContractReceivables.class);
		example.createCriteria().andEqualTo("orgId",contractReceivablesRecord.getOrgId()).andEqualTo("receivablesId",contractReceivables1.getReceivablesId());
		contractReceivablesService.updateContractReceivables(contractReceivables1, example);
		return RetDataTools.Ok("添加成功!",insertContractReceivablesRecord(contractReceivablesRecord));
	}
}



public int insertContractReceivablesRecord(ContractReceivablesRecord contractReceivablesRecord)
{
	return contractReceivablesRecordMapper.insert(contractReceivablesRecord);
}

public int deleteContractReceivablesRecord(ContractReceivablesRecord contractReceivablesRecord)
{
	return contractReceivablesRecordMapper.delete(contractReceivablesRecord);
}

public int updateContractReceivablesRecord(Example example,ContractReceivablesRecord contractReceivablesRecord)
{
	return contractReceivablesRecordMapper.updateByExampleSelective(contractReceivablesRecord, example);
}

public ContractReceivablesRecord selectOneContractReceivablesRecord(ContractReceivablesRecord contractReceivablesRecord)
{
	return contractReceivablesRecordMapper.selectOne(contractReceivablesRecord);
}

/**
 * 
 * @Title: getContractReceivablesRecordList   
 * @Description: TODO  获取收款记录
 * @param orgId
 * @param receivablesId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getContractReceivablesRecordList(String orgId,String receivablesId)
{
	return contractReceivablesRecordMapper.getContractReceivablesRecordList(orgId, receivablesId);
}
/**
 * 
 * @Title: getContractReceivablesRecordList   
 * @Description: TODO 获取收款记录
 * @param pageParam
 * @param receivablesId
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getContractReceivablesRecordList(PageParam pageParam,String receivablesId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getContractReceivablesRecordList(pageParam.getOrgId(),receivablesId);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getReceivRecordTop   
 * @Description: TODO 收款记录
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getReceivRecordTop(@Param(value="orgId")String orgId)
{
	return contractReceivablesRecordMapper.getReceivRecordTop(orgId);
}

}
