package com.core136.service.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpEquipmentSort;
import com.core136.mapper.erp.ErpEquipmentSortMapper;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  ErpEquipmentSortService   
 * @Description:TODO 设备分类
 * @author: 稠云信息
 * @date:   2019年1月21日 上午8:51:36   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ErpEquipmentSortService{
@Autowired
private ErpEquipmentSortMapper erpEquipmentSortMapper;

public int insertErpEquipmentSort(ErpEquipmentSort erpEquipMentSort)
{
	return erpEquipmentSortMapper.insert(erpEquipMentSort);
}

public int updateErpEquipmentSort(ErpEquipmentSort erpEquipment,Example example)
{
	return erpEquipmentSortMapper.updateByExample(erpEquipment, example);
}

public int deleteErpEquipmentSort(ErpEquipmentSort erpEquipmentSort)
{
	return erpEquipmentSortMapper.delete(erpEquipmentSort);
}

public ErpEquipmentSort selectOne(ErpEquipmentSort erpEquipment)
{
	return erpEquipmentSortMapper.selectOne(erpEquipment);
}
/**
 * 获取设备分类树结构
 */

public List<Map<String, Object>> getErpEquipmentSortTree(String sortLeave, String orgId) {
	// TODO Auto-generated method stub
	return erpEquipmentSortMapper.getErpEquipmentSortTree(sortLeave, orgId);
}
/**
 * 判断分类下是否有子分类
 */

public int isExistChild(String sortId, String orgId) {
	// TODO Auto-generated method stub
	return erpEquipmentSortMapper.isExistChild(sortId, orgId);
}
}
