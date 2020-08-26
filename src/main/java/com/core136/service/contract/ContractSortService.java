/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ContractSortService.java   
 * @Package com.core136.service.contract   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月23日 上午11:25:58   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.contract;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.contract.ContractSort;
import com.core136.mapper.contract.ContractSortMapper;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  ContractSortService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月23日 上午11:25:58   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class ContractSortService{
	@Autowired
	private ContractSortMapper contractSortMapper;
	
	public int insertContractSrot(ContractSort contractSort)
	{
		return contractSortMapper.insert(contractSort);
	}
	
	public ContractSort selectOneContractSort(ContractSort contractSort)
	{
		return contractSortMapper.selectOne(contractSort);
	}
	
	public int updateContractSort(ContractSort contractSort,Example example)
	{
		return contractSortMapper.updateByExampleSelective(contractSort, example);
	}

	public int deleteContractSort(ContractSort contractSort)
	{
		return contractSortMapper.delete(contractSort);
	}

	/**
	 * 获取合同的分类树结构
	 */
	
	public List<Map<String, String>> getContractSortTree(String orgId, String sortLeave) {
		return contractSortMapper.getContractSortTree(orgId, sortLeave);
	}

	/**
	 * 判断是否还有子集
	 */
	
	public int isExistChild(String orgId, String sortId) {
		// TODO Auto-generated method stub
		return contractSortMapper.isExistChild(orgId, sortId);
	}
}
