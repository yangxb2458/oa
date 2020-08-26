package com.core136.service.hr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrKpiPlan;
import com.core136.mapper.hr.HrKpiPlanMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrKpiPlanService {
@Autowired
private HrKpiPlanMapper hrKpiPlanMapper;

public int insertHrKpiPlan(HrKpiPlan hrKpiPlan)
{
	return hrKpiPlanMapper.insert(hrKpiPlan);
}

public int deleteHrKpiPlan(HrKpiPlan hrKpiPlan)
{
	return hrKpiPlanMapper.delete(hrKpiPlan);
}

public int updateHrKpiPlan(Example example, HrKpiPlan hrKpiPlan)
{
	return hrKpiPlanMapper.updateByExampleSelective(hrKpiPlan, example);
}

public HrKpiPlan selectOneHrKpiPlan(HrKpiPlan hrKpiPlan)
{
	return hrKpiPlanMapper.selectOne(hrKpiPlan);
}

}
