package com.core136.service.contract;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.core136.bean.contract.ContractBill;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.contract.ContractBillMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ContractBillService {

	@Autowired
	private ContractBillMapper contractBillMapper;
	
	public int insertContractBill(ContractBill contractBill)
	{
		return contractBillMapper.insert(contractBill);
	}
	
	public int deleteContractBill(ContractBill contractBill)
	{
		return contractBillMapper.delete(contractBill);
	}
	
	public int updateContractBill(ContractBill contractBill,Example example)
	{
		return contractBillMapper.updateByExampleSelective(contractBill, example);
	}
	
	public ContractBill selectOneContractBill(ContractBill contractBill)
	{
		return contractBillMapper.selectOne(contractBill);
	}
	/**
	 * 
	 * @Title: getContractBillList   
	 * @Description: TODO获取发票列表
	 * @param: orgId
	 * @param: opFlag
	 * @param: accountId
	 * @param: userPriv
	 * @param: beginTime
	 * @param: endTime
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getContractBillList(String orgId,String opFlag,String accountId,String isOpen,String status,String billType,String beginTime,String endTime,String search)
	{
		return contractBillMapper.getContractBillList(orgId, opFlag, accountId, isOpen,status,billType, beginTime, endTime,"%"+search+"%");
	}
	
	/**
	 * 
	 * @Title: getContractBillList   
	 * @Description: TODO 获取发票列表   
	 * @param: pageParam
	 * @param: userPriv
	 * @param: beginTime
	 * @param: endTime
	 * @param: @return      
	 * @return: PageInfo<Map<String,String>>      
	 * @throws
	 */
	public PageInfo<Map<String, String>> getContractBillList(PageParam pageParam,String isOpen,String status,String billType,String beginTime, String endTime) {
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getContractBillList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),isOpen,status,billType,beginTime, endTime,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	
	/**
	 * 
	 * @Title: getDeskBillList   
	 * @Description: TODO 获取财务门户发票列表
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>> getDeskBillList(String orgId)
	{
		return contractBillMapper.getDeskBillList(orgId);
	}
	
	/**
	 * 
	 * @Title: getContractBillTop   
	 * @Description: TODO 获取近期发票列表
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getContractBillTop(String orgId)
	{
		return contractBillMapper.getContractBillTop(orgId);
	}
}
