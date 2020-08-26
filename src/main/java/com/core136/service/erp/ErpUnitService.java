package com.core136.service.erp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpUnit;
import com.core136.mapper.erp.ErpUnitMapper;
/**
 * 
 * @ClassName:  ErpUnitService   
 * @Description:TODO Erp计量单位操作类
 * @author: 稠云信息
 * @date:   2018年12月12日 上午9:13:43   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ErpUnitService{
@Autowired
private ErpUnitMapper erpUnitMapper;
/**
 * 获取所有的计量单位
 */

public List<ErpUnit> getAllUnit(String orgId) {
	// TODO Auto-generated method stub
	return erpUnitMapper.getAllUnit(orgId);
}
/**
 * 
 * @Title: selectOne   
 * @Description: TODO 获取单个计量单位
 * @param: erpUnit
 * @param: @return      
 * @return: ErpUnit      

 */
public ErpUnit selectOne(ErpUnit erpUnit)
{
	return erpUnitMapper.selectOne(erpUnit);
}

}
