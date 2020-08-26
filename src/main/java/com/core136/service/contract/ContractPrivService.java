/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ContractPrivService.java   
 * @Package com.core136.service.contract   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月26日 上午9:14:28   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.contract.ContractPriv;
import com.core136.mapper.contract.ContractPrivMapper;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  ContractPrivService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月26日 上午9:14:28   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class ContractPrivService{
	@Autowired
	private ContractPrivMapper contractPrivMapper;
	
	public ContractPriv selectOneContractPriv(ContractPriv contractPriv)
	{
		return contractPrivMapper.selectOne(contractPriv);
	}
	
	public int insertContractPriv(ContractPriv contractPriv)
	{
		return contractPrivMapper.insert(contractPriv);
	}
	
	public int deleteContractPriv(ContractPriv contractPriv)
	{
		return contractPrivMapper.delete(contractPriv);
	}
	
	public int updateContractPriv(ContractPriv contractPriv,Example example)
	{
		return contractPrivMapper.updateByExample(contractPriv, example);
	}

	/**
	 * 判断是否存在
	 */
	
	public int isExistChild(String orgId) {
		// TODO Auto-generated method stub
		return contractPrivMapper.isExistChild(orgId);
	}
}
