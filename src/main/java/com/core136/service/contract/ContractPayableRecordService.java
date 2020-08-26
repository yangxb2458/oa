package com.core136.service.contract;

import java.util.List;
import java.util.Map;

import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core136.bean.contract.ContractPayable;
import com.core136.bean.contract.ContractPayableRecord;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.contract.ContractPayableRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ContractPayableRecordService {
@Autowired
private ContractPayableRecordMapper contractPayableRecordMapper;
@Autowired
private ContractPayableService contractPayableService;
/**
 * 
 * @Title: payableAmount   
 * @Description: TODO 添加付款记录
 * @param contractPayableRecord
 * @return
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM")
public RetDataBean payableAmount(ContractPayableRecord contractPayableRecord)
{
	ContractPayable contractPayable = new ContractPayable();
	contractPayable.setPayableId(contractPayableRecord.getPayableId());
	contractPayable.setOrgId(contractPayableRecord.getOrgId());
	contractPayable = contractPayableService.selectOneContractPayable(contractPayable);
	Double payabled = contractPayable.getPayabled();
	Double  unPayabled = contractPayable.getUnPayabled();
	if(unPayabled<contractPayableRecord.getAmount())
	{
		return RetDataTools.NotOk("付款金额大于应付款金额！");
	}else
	{
		unPayabled = unPayabled-contractPayableRecord.getAmount();
		payabled = payabled+contractPayableRecord.getAmount();
		ContractPayable contractPayable1 = new ContractPayable();
		contractPayable1.setPayableId(contractPayable.getPayableId());
		contractPayable1.setUnPayabled(unPayabled);
		contractPayable1.setPayabled(payabled);
		contractPayable1.setOrgId(contractPayable.getOrgId());
		Example example = new Example(ContractPayable.class);
		example.createCriteria().andEqualTo("orgId",contractPayable.getOrgId()).andEqualTo("payableId",contractPayable1.getPayableId());
		contractPayableService.updateContractPayable(contractPayable1, example);
		return RetDataTools.Ok("添加成功!",insertContractPayableRecord(contractPayableRecord));
	}
}


public int insertContractPayableRecord(ContractPayableRecord contractPayableRecord)
{
	return contractPayableRecordMapper.insert(contractPayableRecord);
}

public int deleteContractPayableRecord(ContractPayableRecord contractPayableRecord)
{
	return contractPayableRecordMapper.delete(contractPayableRecord);
}
public int updateContractPayableRecord(Example example,ContractPayableRecord contractPayableRecord)
{
	return contractPayableRecordMapper.updateByExampleSelective(contractPayableRecord, example);
}

public ContractPayableRecord selectOneContractPayableRecord(ContractPayableRecord contractPayableRecord)
{
	return contractPayableRecordMapper.selectOne(contractPayableRecord);
}

public List<Map<String,String>>getContractPayableRecordList(String orgId,String payableId)
{
	return contractPayableRecordMapper.getContractPayableRecordList(orgId, payableId);
}
/**
 * 
 * @Title: getContractPayableRecordList   
 * @Description: TODO 获取付款记录
 * @param pageParam
 * @param receivablesId
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getContractPayableRecordList(PageParam pageParam,String payableId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getContractPayableRecordList(pageParam.getOrgId(),payableId);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getPayableRecordTop   
 * @Description: TODO 近期付款记录
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getPayableRecordTop(String orgId)
{
	return contractPayableRecordMapper.getPayableRecordTop(orgId);
}

}
