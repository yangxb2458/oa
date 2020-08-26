package com.core136.service.contract;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.contract.ContractSendgoods;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.contract.ContractMapper;
import com.core136.mapper.contract.ContractSendgoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ContractSendgoodsService {
@Autowired
private ContractSendgoodsMapper contractSendgoodsMapper;

public int insertContractSendgoods(ContractSendgoods contractSendgoods)
{
	return contractSendgoodsMapper.insert(contractSendgoods);
}
public int deleteContractSendgoods(ContractSendgoods contractSendgoods)
{
	return contractSendgoodsMapper.delete(contractSendgoods);
}
public int updateContractSendgoods(Example example,ContractSendgoods contractSendgoods)
{
	return contractSendgoodsMapper.updateByExampleSelective(contractSendgoods, example);
}
public ContractSendgoods selectOneContractSendgoods(ContractSendgoods contractSendgoods)
{
	return contractSendgoodsMapper.selectOne(contractSendgoods);
}

/**
 * 
 * @Title: getContractSendgoodsList   
 * @Description: TODO 获取发货列表
 * @param orgId
 * @param contractType
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getContractSendgoodsList(String orgId,String contractType,String beginTime,String endTime,String search)
{
	return contractSendgoodsMapper.getContractSendgoodsList(orgId, contractType, beginTime, endTime, "%"+search+"%");
}
/**
 * 
 * @Title: getContractSendgoodsList   
 * @Description: TODO 获取发货列表
 * @param pageParam
 * @param contractType
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getContractSendgoodsList(PageParam pageParam,String contractType,String beginTime, String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getContractSendgoodsList(pageParam.getOrgId(),contractType,beginTime, endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
