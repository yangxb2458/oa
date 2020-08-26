package com.core136.service.vehicle;

import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.vehicle.VehicleOperator;
import com.core136.mapper.vehicle.VehicleOperatorMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class VehicleOperatorService {
@Autowired
private VehicleOperatorMapper vehicleOperatorMapper;

public int insertVehicleOperator(VehicleOperator vehicleOperator)
{
	return vehicleOperatorMapper.insert(vehicleOperator);
}

public int deleteVehicleOperator(VehicleOperator vehicleOperator)
{
	return vehicleOperatorMapper.delete(vehicleOperator);
}
	
public int updateVehicleOperator(Example example,VehicleOperator vehicleOperator)
{
	return vehicleOperatorMapper.updateByExampleSelective(vehicleOperator, example);
}

public VehicleOperator selectOneVehicleOperator(VehicleOperator vehicleOperator)
{
	return vehicleOperatorMapper.selectOne(vehicleOperator);
}
/**
 * 
 * @Title: setVehicleOperator   
 * @Description: TODO 设置调度员
 * @param account
 * @param optUser
 * @return
 * RetDataBean    
 * @throws
 */
public RetDataBean setVehicleOperator(Account account,String optUser)
{
	if(account.getOpFlag().equals("1"))
	{
	VehicleOperator vehicleOperator = new VehicleOperator();
	vehicleOperator.setOrgId(account.getOrgId());
	vehicleOperator = selectOneVehicleOperator(vehicleOperator);
	if(vehicleOperator==null)
	{
		VehicleOperator newVehicleOperator = new VehicleOperator();
		newVehicleOperator.setConfigId(SysTools.getGUID());
		newVehicleOperator.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		newVehicleOperator.setCreateUser(account.getAccountId());
		newVehicleOperator.setOptUser(optUser);
		newVehicleOperator.setOrgId(account.getOrgId());
		return RetDataTools.Ok("设置调度人员成功",insertVehicleOperator(newVehicleOperator));
	}else
	{
		Example example = new Example(VehicleOperator.class);
		example.createCriteria().andEqualTo("configId",vehicleOperator.getConfigId()).andEqualTo("orgId",account.getOrgId());
		vehicleOperator.setOptUser(optUser);
		return RetDataTools.Ok("设置调度人员成功",updateVehicleOperator(example, vehicleOperator));
	}
	}else
	{
		return RetDataTools.NotOk("对不起，您不是管理员，请与管理员联系!");
	}
}

}
