package com.core136.service.officesupplies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.officesupplies.OfficeSuppliesGrant;
import com.core136.mapper.officesupplies.OfficeSuppliesGrantMapper;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  OfficeSuppliesGrantService   
 * @Description:TODO 办公用品发放
 * @author: 稠云技术 
 * @date:   2019年12月1日 下午9:29:21   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class OfficeSuppliesGrantService {
@Autowired
private OfficeSuppliesGrantMapper officeSuppliesGrantMapper;

public int insertOfficeSuppliesGrant(OfficeSuppliesGrant officeSuppliesGrant)
{
	return officeSuppliesGrantMapper.insert(officeSuppliesGrant);
}

public int deleteOfficeSuppliesGrant(OfficeSuppliesGrant officeSuppliesGrant)
{
	return officeSuppliesGrantMapper.delete(officeSuppliesGrant);
}

public int updateOfficeSuppliesGrant(Example example,OfficeSuppliesGrant officeSuppliesGrant)
{
	return officeSuppliesGrantMapper.updateByExampleSelective(officeSuppliesGrant, example);
}

public OfficeSuppliesGrant selectOneOfficeSuppliesGrant(OfficeSuppliesGrant officeSuppliesGrant)
{
	return officeSuppliesGrantMapper.selectOne(officeSuppliesGrant);
}
/**
 * 
 * @Title: getGrantCount   
 * @Description: TODO 统计已发放了多少办公用品 
 * @param: orgId
 * @param: applyId
 * @param: @return      
 * @return: int      
 * @throws
 */
public int getGrantCount(String orgId,String applyId)
{
	return officeSuppliesGrantMapper.getGrantCount(orgId, applyId);
}

}
