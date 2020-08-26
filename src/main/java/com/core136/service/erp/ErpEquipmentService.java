package com.core136.service.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpEquipment;
import com.core136.mapper.erp.ErpEquipmentMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class ErpEquipmentService{
@Autowired
private ErpEquipmentMapper erpEquipmentMapper;

public ErpEquipment selectOne(ErpEquipment erpEquipment)
{
	return erpEquipmentMapper.selectOne(erpEquipment);
}

public int deleteErpEquipment(ErpEquipment erpEquipment)
{
	return erpEquipmentMapper.delete(erpEquipment);
}

public int insertErpEquipment(ErpEquipment erpEquipment)
{
	return erpEquipmentMapper.insert(erpEquipment);
}

public int updateErpEquipment(ErpEquipment erpEquipment,Example example)
{
	return erpEquipmentMapper.updateByExample(erpEquipment, example);
}

}
