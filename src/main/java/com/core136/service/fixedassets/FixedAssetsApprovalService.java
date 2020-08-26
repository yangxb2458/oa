/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedAssetApprovalService.java   
 * @Package com.core136.service.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年12月26日 下午3:04:16   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.fixedassets.FixedAssetsApply;
import com.core136.bean.fixedassets.FixedAssetsApproval;
import com.core136.mapper.fixedassets.FixedAssetsApprovalMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class FixedAssetsApprovalService {
	@Autowired
	private FixedAssetsApplyService fixedAssetsApplyService;

	@Autowired
	private FixedAssetsApprovalMapper fixedAssetsApprovalMapper;
	
	public int insertFixedAssetsApproval(FixedAssetsApproval fixedAssetsApproval)
	{
		return fixedAssetsApprovalMapper.insert(fixedAssetsApproval);
	}
	
	public int deleteFixedAssetsApproval(FixedAssetsApproval fixedAssetsApproval)
	{
		return fixedAssetsApprovalMapper.delete(fixedAssetsApproval);
	}
	
	public int updateFixedAssetsApproval(Example example,FixedAssetsApproval fixedAssetsApproval)
	{
		return fixedAssetsApprovalMapper.updateByExampleSelective(fixedAssetsApproval, example);
	}
	
	public FixedAssetsApproval selectOneFixedAssetsApproval(FixedAssetsApproval fixedAssetsApproval)
	{
		return fixedAssetsApprovalMapper.selectOne(fixedAssetsApproval);
	}
	
	/**
	 * 
	 * @Title: approveFixedAssets   
	 * @Description: TODO 资产审批
	 * @param: fixedAssetsApproval
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int approveFixedAssets(FixedAssetsApproval fixedAssetsApproval)
	{
		FixedAssetsApply fixedAssetsApply = new FixedAssetsApply();
		fixedAssetsApply.setApplyId(fixedAssetsApproval.getApplyId());
		fixedAssetsApply.setOrgId(fixedAssetsApproval.getOrgId());
		fixedAssetsApply.setStatus("1");
		Example example = new Example(FixedAssetsApply.class);
		example.createCriteria().andEqualTo("orgId",fixedAssetsApply.getOrgId()).andEqualTo("applyId",fixedAssetsApply.getApplyId());
		fixedAssetsApplyService.updateFixedAssetsApply(example, fixedAssetsApply);
		return insertFixedAssetsApproval(fixedAssetsApproval);
	}
}
