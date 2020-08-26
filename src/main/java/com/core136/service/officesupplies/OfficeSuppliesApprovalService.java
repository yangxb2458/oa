/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  OfficeSuppliesApprovalService.java   
 * @Package com.core136.service.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月25日 上午9:57:36   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.officesupplies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core136.bean.officesupplies.OfficeSuppliesApply;
import com.core136.bean.officesupplies.OfficeSuppliesApproval;
import com.core136.mapper.officesupplies.OfficeSuppliesApprovalMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class OfficeSuppliesApprovalService {
@Autowired
private OfficeSuppliesApprovalMapper officeSuppliesApprovalMapper;
@Autowired
private OfficeSuppliesApplyService officeSuppliesApplyService;

public int insertOfficeSuppliesApproval(OfficeSuppliesApproval officeSuppliesApproval)
{
	return officeSuppliesApprovalMapper.insert(officeSuppliesApproval);
}

public int deleteOfficeSuppliesApproval(OfficeSuppliesApproval officeSuppliesApproval)
{
	return officeSuppliesApprovalMapper.delete(officeSuppliesApproval);
}

public int updateOfficeSuppliesApproval(Example example,OfficeSuppliesApproval officeSuppliesApproval)
{
	return officeSuppliesApprovalMapper.updateByExampleSelective(officeSuppliesApproval, example);
}

public OfficeSuppliesApproval selectOneOfficeSuppliesApproval(OfficeSuppliesApproval officeSuppliesApproval)
{
	return officeSuppliesApprovalMapper.selectOne(officeSuppliesApproval);
}

/**
 * 
 * @Title: approvalOfficeSuppliesApply   
 * @Description: TODO 审批办公用品申请
 * @param: officeSuppliesApproval
 * @param: @return      
 * @return: int      
 * @throws
 */
@Transactional(value="generalTM")
public int approvalOfficeSuppliesApply(OfficeSuppliesApproval officeSuppliesApproval)
{
	OfficeSuppliesApply officeSuppliesApply = new OfficeSuppliesApply();
	officeSuppliesApply.setStatus(officeSuppliesApproval.getStatus());
	officeSuppliesApply.setOrgId(officeSuppliesApproval.getOrgId());
	officeSuppliesApply.setApplyId(officeSuppliesApproval.getApplyId());
	this.insertOfficeSuppliesApproval(officeSuppliesApproval);
	Example example = new Example(OfficeSuppliesApply.class);
	example.createCriteria().andEqualTo("orgId",officeSuppliesApply.getOrgId()).andEqualTo("applyId",officeSuppliesApply.getApplyId());
	return officeSuppliesApplyService.updateOfficeSuppliesApply(example, officeSuppliesApply);
}

}
