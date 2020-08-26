package com.core136.service.workplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.workplan.WorkPlanProcess;
import com.core136.mapper.workplan.WorkPlanProcessMapper;
import com.sun.corba.se.spi.orbutil.threadpool.Work;

import tk.mybatis.mapper.entity.Example;

@Service
public class WorkPlanProcessService {
@Autowired
private WorkPlanProcessMapper workPlanProcessMapper;

public int insertWorkPlanProcess(WorkPlanProcess workPlanProcess)
{
	return workPlanProcessMapper.insert(workPlanProcess);
}

public int deleteWorkPlanProcess(WorkPlanProcess workPlanProcess)
{
	return workPlanProcessMapper.delete(workPlanProcess);
}

public int updateWorkPlanProcess(Example example,WorkPlanProcess workPlanProcess)
{
	return workPlanProcessMapper.updateByExampleSelective(workPlanProcess, example);
}

public WorkPlanProcess selectOneWorkPlanProcess(WorkPlanProcess workPlanProcess)
{
	return workPlanProcessMapper.selectOne(workPlanProcess);
}

}
