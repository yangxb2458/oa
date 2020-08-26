package com.core136.service.echarts;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.mapper.echarts.EchartsHrMapper;

import cyunsoft.bi.option.bean.OptionConfig;
import cyunsoft.bi.option.property.OptionGrid;
import cyunsoft.bi.option.property.OptionSeries;
import cyunsoft.bi.option.property.OptionTitle;
import cyunsoft.bi.option.property.OptionTooltip;
import cyunsoft.bi.option.property.OptionXAxis;
import cyunsoft.bi.option.resdata.LegendData;
import cyunsoft.bi.option.resdata.SeriesData;
import cyunsoft.bi.option.style.AxisPointer;
import cyunsoft.bi.option.style.AxisTick;
import cyunsoft.bi.option.style.Emphasis;
import cyunsoft.bi.option.style.ItemStyle;
import cyunsoft.bi.option.style.Label;
import cyunsoft.bi.option.style.LabelLine;
import cyunsoft.bi.option.units.BarOption;
import cyunsoft.bi.option.units.PieOption;

@Service
public class EchartsHrService {
private PieOption pieOption = new PieOption();
private BarOption barOption = new BarOption();
@Autowired
private EchartsHrMapper echartsHrMapper;

/**
 * 
 * @Title: getCareTableForAnalysis   
 * @Description: TODO 人员关怀类型分析tabale
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getCareTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.CARE_TYPE";
		module="careType";
	}
	return echartsHrMapper.getCareAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getCareBarForAnalysis   
 * @Description: TODO 人员关怀类型柱状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getCareBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.CARE_TYPE";
		module="careType";
		optionSeriesName="人员关怀类型分析";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getCareAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getCarePieForAnalysis   
 * @Description: TODO人员关怀类型饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getCarePieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.CARE_TYPE";
		module="careType";
		optionSeriesName="人员关怀类型分析";
		optionTitleText="人员关怀类型统计";
		optionTitleSubtext="人员关怀类型占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getCareAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}

/**
 * 
 * @Title: getEvaluationTableForAnalysis   
 * @Description: TODO 职称分析tabale
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getEvaluationTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.GET_TYPE";
		module="getType";
	}else if(dataType.equals("2"))
	{
		groupBy="U.DEPT_ID";
		module="deptId";
	}else if(dataType.equals("3"))
	{
		groupBy="U.POST";
		module="post";
	}
	return echartsHrMapper.getEvaluationAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getEvaluationBarForAnalysis   
 * @Description: TODO 称职评定柱状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getEvaluationBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.GET_TYPE";
		module="getType";
		optionSeriesName="称职评定分析";
	}else if(dataType.equals("2"))
	{
		groupBy="U.DEPT_ID";
		module="deptId";
		optionSeriesName="称职评定分析";
	}else if(dataType.equals("3"))
	{
		groupBy="U.POST";
		module="post";
		optionSeriesName="称职评定分析";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getEvaluationAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getEvaluationPieForAnalysis   
 * @Description: TODO 称职评定饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getEvaluationPieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.GET_TYPE";
		module="getType";
		optionSeriesName="称职评定分析";
		optionTitleText="称职评定统计";
		optionTitleSubtext="称职评定占比";
	}else if(dataType.equals("2"))
	{
		groupBy="U.DEPT_ID";
		module="deptId";
		optionSeriesName="称职评定分析";
		optionTitleText="称职评定统计";
		optionTitleSubtext="称职评定占比";
	}else if(dataType.equals("3"))
	{
		groupBy="U.POST";
		module="post";
		optionSeriesName="称职评定分析";
		optionTitleText="称职评定统计";
		optionTitleSubtext="称职评定占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getEvaluationAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}

/**
 * 
 * @Title: getReinstatTableForAnalysis   
 * @Description: TODO 人员复职分析tabale
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getReinstatTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.REINSTATEMENT_TYPE";
		module="reinstatementType";
	}else if(dataType.equals("2"))
	{
		groupBy="U.DEPT_ID";
		module="deptId";
	}else if(dataType.equals("3"))
	{
		groupBy="U.LEVEL_ID";
		module="levelId";
	}
	return echartsHrMapper.getReinstatAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getReinstatBarForAnalysis   
 * @Description: TODO 人员复职柱状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getReinstatBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.REINSTATEMENT_TYPE";
		module="reinstatementType";
		optionSeriesName="人员复职分析";
	}else if(dataType.equals("2"))
	{
		groupBy="U.DEPT_ID";
		module="deptId";
		optionSeriesName="人员复职分析";
	}else if(dataType.equals("3"))
	{
		groupBy="U.LEVEL_ID";
		module="levelId";
		optionSeriesName="人员复职分析";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getReinstatAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getReinstatPieForAnalysis   
 * @Description: TODO 人员复职饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getReinstatPieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.REINSTATEMENT_TYPE";
		module="reinstatementType";
		optionSeriesName="人员复职分析";
		optionTitleText="人员复职统计";
		optionTitleSubtext="人员复职占比";
	}else if(dataType.equals("2"))
	{
		groupBy="U.DEPT_ID";
		module="deptId";
		optionSeriesName="人员复职分析";
		optionTitleText="人员复职统计";
		optionTitleSubtext="人员复职占比";
	}else if(dataType.equals("3"))
	{
		groupBy="U.LEVEL_ID";
		module="levelId";
		optionSeriesName="人员复职分析";
		optionTitleText="人员复职统计";
		optionTitleSubtext="人员复职占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getReinstatAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}


/**
 * 
 * @Title: getLeaveTableForAnalysis   
 * @Description: TODO 人员离职分析tabale
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getLeaveTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.LEAVE_TYPE";
		module="leaveType";
	}else if(dataType.equals("2"))
	{
		groupBy="U.DEPT_ID";
		module="deptId";
	}
	return echartsHrMapper.getLeaveAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getLeaveBarForAnalysis   
 * @Description: TODO 人员离职柱状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getLeaveBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.LEAVE_TYPE";
		module="leaveType";
		optionSeriesName="人员离职分析";
	}else if(dataType.equals("2"))
	{
		groupBy="U.DEPT_ID";
		module="deptId";
		optionSeriesName="人员离职分析";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getLeaveAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getLeavePieForAnalysis   
 * @Description: TODO 人员离职饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getLeavePieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.LEAVE_TYPE";
		module="leaveType";
		optionSeriesName="人员离职分析";
		optionTitleText="人员离职统计";
		optionTitleSubtext="人员离职占比";
	}else if(dataType.equals("2"))
	{
		groupBy="U.DEPT_ID";
		module="deptId";
		optionSeriesName="人员离职分析";
		optionTitleText="人员离职统计";
		optionTitleSubtext="人员离职占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getLeaveAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}

/**
 * 
 * @Title: getTransferTableForAnalysis   
 * @Description: TODO 调动类型分析tabale
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getTransferTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.TRANSFER_TYPE";
		module="transferType";
	}
	return echartsHrMapper.getTransferAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getTransferBarForAnalysis   
 * @Description: TODO 调动类型柱状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getTransferBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.TRANSFER_TYPE";
		module="transferType";
		optionSeriesName="调动类型分析";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getTransferAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getTransferPieForAnalysis   
 * @Description: TODO 调动类型饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getTransferPieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.TRANSFER_TYPE";
		module="transferType";
		optionSeriesName="调动类型分析";
		optionTitleText="调动类型统计";
		optionTitleSubtext="调动类型占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getTransferAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}

/**
 * 
 * @Title: getLearnTableForAnalysis   
 * @Description: TODO 劳动持能分析tabale
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getSkillsTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.SKILLS_LEVEL";
		module="skillsLevel";
	}
	return echartsHrMapper.getSkillsAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getSkillsBarForAnalysis   
 * @Description: TODO 劳动技能柱状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getSkillsBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.SKILLS_LEVEL";
		module="skillsLevel";
		optionSeriesName="劳动技能分析";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getSkillsAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getSkillsPieForAnalysis   
 * @Description: TODO 劳动技能饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getSkillsPieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.SKILLS_LEVEL";
		module="skillsLevel";
		optionSeriesName="劳动技能分析";
		optionTitleText="劳动技能统计";
		optionTitleSubtext="劳动技能占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getSkillsAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}

/**
 * 
 * @Title: getLearnTableForAnalysis   
 * @Description: TODO 学习经历分析tabale
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getLearnTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.HIGHSET_DEGREE";
		module="highsetDegree";
	}
	return echartsHrMapper.getLearnAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getLearnBarForAnalysis   
 * @Description: TODO 学习经历柱状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getLearnBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.HIGHSET_DEGREE";
		module="highsetDegree";
		optionSeriesName="证照分析";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getLearnAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getLearnPieForAnalysis   
 * @Description: TODO 学习经历饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getLearnPieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.HIGHSET_DEGREE";
		module="highsetDegree";
		optionSeriesName="证照分析";
		optionTitleText="证照分析统计";
		optionTitleSubtext="证照分析占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getLearnAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}
/**
 * 
 * @Title: getLicenceeTableForAnalysis   
 * @Description: TODO 学习经历 tabale
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getLicenceTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.LICENCE_TYPE";
		module="licenceType";
	}
	return echartsHrMapper.getLicenceAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getLicenceBarForAnalysis   
 * @Description: TODO 证照柱状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getLicenceBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.LICENCE_TYPE";
		module="licenceType";
		optionSeriesName="证照分析";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getLicenceAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getLicencePieForAnalysis   
 * @Description: TODO 证照饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getLicencePieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.LICENCE_TYPE";
		module="licenceType";
		optionSeriesName="证照分析";
		optionTitleText="证照分析统计";
		optionTitleSubtext="证照分析占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getLicenceAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}

/**
 * 
 * @Title: getIncentiveTableForAnalysis   
 * @Description: TODO 奖惩分析tabale
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getIncentiveTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.INCENTIVE_TYPE";
		module="incentiveType";
	}else if(dataType.equals("2"))
	{
		groupBy="U.INCENTIVE_ITEM";
		module="incentiveItem";
	}
	return echartsHrMapper.getIncentiveAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getIncentiveBarForAnalysis   
 * @Description: TODO 奖惩分析柱状图
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getIncentiveBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.INCENTIVE_TYPE";
		module="incentiveType";
		optionSeriesName="奖惩类型";
	}else if(dataType.equals("2"))
	{
		groupBy="U.INCENTIVE_ITEM";
		module="incentiveItem";
		optionSeriesName="处理事项";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getIncentiveAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getIncentivePieForAnalysis   
 * @Description: TODO 奖惩分析饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getIncentivePieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.INCENTIVE_TYPE";
		module="incentiveType";
		optionSeriesName="奖惩类型";
		optionTitleText="奖惩类型统计";
		optionTitleSubtext="奖惩类型占比";
	}else if(dataType.equals("2"))
	{
		groupBy="U.INCENTIVE_ITEM";
		module="incentiveItem";
		optionSeriesName="处理事项";
		optionTitleText="处理事项统计";
		optionTitleSubtext="处理事项占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getIncentiveAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}




/**
 * 
 * @Title: getContractTableForAnalysis   
 * @Description: TODO 合同分析tabale
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getContractTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.CONTRACT_TYPE";
		module="contractType";
	}else if(dataType.equals("2"))
	{
		groupBy="U.ENTERPRIES";
		module="enterpries";
	}else if(dataType.equals("3"))
	{
		groupBy="U.SIGN_TYPE";
		module="signType";
	}else if(dataType.equals("4"))
	{
		groupBy="U.POOL_POSITION";
		module="poolPosition";
	}
	return echartsHrMapper.getContractAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getContractBarForAnalysis   
 * @Description: TODO 合同柱状图
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getContractBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.CONTRACT_TYPE";
		module="contractType";
		optionSeriesName="合同类型";
	}else if(dataType.equals("2"))
	{
		groupBy="U.ENTERPRIES";
		module="enterpries";
		optionSeriesName="签约公司";
	}else if(dataType.equals("3"))
	{
		groupBy="U.SIGN_TYPE";
		module="signType";
		optionSeriesName="签约方式";
	}else if(dataType.equals("4"))
	{
		groupBy="U.POOL_POSITION";
		module="poolPosition";
		optionSeriesName="应聘岗位";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getContractAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getContractPieForAnalysis   
 * @Description: TODO 合同饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getContractPieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.CONTRACT_TYPE";
		module="contractType";
		optionSeriesName="合同类型";
		optionTitleText="合同类型统计";
		optionTitleSubtext="合同类型占比";
	}else if(dataType.equals("2"))
	{
		groupBy="U.ENTERPRIES";
		module="enterpries";
		optionSeriesName="签约公司";
		optionTitleText="签约公司统计";
		optionTitleSubtext="签约公司占比";
	}else if(dataType.equals("3"))
	{
		groupBy="U.SIGN_TYPE";
		module="signType";
		optionSeriesName="签约方式";
		optionTitleText="签约方式统计";
		optionTitleSubtext="签约方式占比";
	}else if(dataType.equals("4"))
	{
		groupBy="U.POOL_POSITION";
		module="poolPosition";
		optionSeriesName="应聘岗位";
		optionTitleText="应聘岗位统计";
		optionTitleSubtext="应聘岗位占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getContractAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}

/**
 * 
 * @Title: getBaseInfoTableForAnalysis   
 * @Description: TODO 人事档案分析Table
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getBaseInfoTableForAnalysis(String orgId,String deptId,String dataType)
{
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.WORK_TYPE";
		module="workType";
	}else if(dataType.equals("2"))
	{
		groupBy="U.HIGHSET_SHOOL";
		module="highsetShool";
	}else if(dataType.equals("3"))
	{
		groupBy="U.PERSENT_POSITION";
		module="persentPosition";
	}else if(dataType.equals("4"))
	{
		groupBy="U.NATIVE_PLACE";
		module="nativePlace";
	}else if(dataType.equals("5"))
	{
		groupBy="U.WAGES_LEVEL";
		module="wagesLevel";
	}else if(dataType.equals("6"))
	{
		groupBy="U.POLITICAL_STATUS";
		module="politicalStatus";
	}
	return echartsHrMapper.getBaseInfoAnalysis(orgId,deptId,module,groupBy);
}

/**
 * 
 * @Title: getBaseInfoBarForAnalysis   
 * @Description: TODO 人事档案柱状图
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getBaseInfoBarForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.WORK_TYPE";
		module="workType";
		optionSeriesName="工种";
	}else if(dataType.equals("2"))
	{
		groupBy="U.HIGHSET_SHOOL";
		module="highsetShool";
		optionSeriesName="学历";
	}else if(dataType.equals("3"))
	{
		groupBy="U.PERSENT_POSITION";
		module="persentPosition";
		optionSeriesName="职称";
	}else if(dataType.equals("4"))
	{
		groupBy="U.NATIVE_PLACE";
		module="nativePlace";
		optionSeriesName="地区";
	}else if(dataType.equals("5"))
	{
		groupBy="U.WAGES_LEVEL";
		module="wagesLevel";
		optionSeriesName="工资级别";
	}else if(dataType.equals("6"))
	{
		groupBy="U.POLITICAL_STATUS";
		module="politicalStatus";
		optionSeriesName="政治面貌";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getBaseInfoAnalysis(orgId,deptId,module,groupBy);
		return getBarForAnalysis(resdataList,optionSeriesName);
}

/**
 * 
 * @Title: getBaseInfoPieForAnalysis   
 * @Description: TODO 人事基本档案饼状图分析
 * @param orgId
 * @param deptId
 * @param dataType
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getBaseInfoPieForAnalysis(String orgId,String deptId,String dataType)
{
	String optionSeriesName="";
	String optionTitleText="";
	String optionTitleSubtext="";
	String module="";
	String groupBy="";
	if(dataType.equals("1"))
	{
		groupBy="U.WORK_TYPE";
		module="workType";
		optionSeriesName="工种";
		optionTitleText="人员工种统计";
		optionTitleSubtext="人员工种占比";
	}else if(dataType.equals("2"))
	{
		groupBy="U.HIGHSET_SHOOL";
		module="highsetShool";
		optionSeriesName="学历";
		optionTitleText="人员学历统计";
		optionTitleSubtext="人员学历占比";
	}else if(dataType.equals("3"))
	{
		groupBy="U.PERSENT_POSITION";
		module="persentPosition";
		optionSeriesName="职称";
		optionTitleText="人员职称统计";
		optionTitleSubtext="人员职称占比";
	}else if(dataType.equals("4"))
	{
		groupBy="U.NATIVE_PLACE";
		module="nativePlace";
		optionSeriesName="地区";
		optionTitleText="人员籍贯统计";
		optionTitleSubtext="人员籍贯占比";
	}else if(dataType.equals("5"))
	{
		groupBy="U.WAGES_LEVEL";
		module="wagesLevel";
		optionSeriesName="工资级别";
		optionTitleText="人员工资级别统计";
		optionTitleSubtext="人员工资级别占比";
	}else if(dataType.equals("6"))
	{
		groupBy="U.POLITICAL_STATUS";
		module="politicalStatus";
		optionSeriesName="政治面貌";
		optionTitleText="人员政治面貌统计";
		optionTitleSubtext="人员政治面貌占比";
	}
		List<Map<String, String>> resdataList = echartsHrMapper.getBaseInfoAnalysis(orgId,deptId,module,groupBy);
		return getPieForAnalysis(resdataList,optionSeriesName,optionTitleText,optionTitleSubtext);
}

/**
 * 
 * @Title: getBaseInfoBarForAnalysis   
 * @Description: TODO 档案分析柱状图
 * @param resdataList
 * @param seriesDataName
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getBarForAnalysis(List<Map<String, String>> resdataList,String seriesDataName)
{
	OptionConfig optionConfig = new OptionConfig();
	String[] xdata = new String[resdataList.size()];
	Double[] ydata = new Double[resdataList.size()];
	for(int i=0;i<resdataList.size();i++)
	{
		if(StringUtils.isNotBlank(resdataList.get(i).get("name")))
		{
			xdata[i]=resdataList.get(i).get("name");
		}else
		{
			xdata[i]="other"+i;
		}
		ydata[i]=Double.valueOf(String.valueOf(resdataList.get(i).get("value")));
	}
	OptionSeries optionSeries = new OptionSeries();
	optionSeries.setName(seriesDataName);
	optionSeries.setType("bar");
	optionSeries.setBarWidth("60%");
	optionSeries.setData(ydata);
	OptionXAxis xAxis = new OptionXAxis();
	xAxis.setType("category");
	xAxis.setData(xdata);
	AxisTick axisTick = new AxisTick();
	axisTick.setAlignWithLabel(true);
	xAxis.setAxisTick(axisTick);
	optionConfig = barOption.getBarTickAlignChartOption(new OptionXAxis[] {xAxis}, new OptionSeries[] {optionSeries});
	OptionTooltip optionTooltip = new OptionTooltip();
	optionTooltip.setTrigger("axis");
	AxisPointer axisPointer = new AxisPointer();
	axisPointer.setType("shadow");
	optionTooltip.setAxisPointer(axisPointer);
	optionConfig.setTooltip(optionTooltip);
	OptionGrid optionGrid = new OptionGrid();
	optionGrid.setLeft("3%");
	optionGrid.setRight("4%");
	optionGrid.setBottom("3%");
	optionGrid.setContainLabel(true);
	optionConfig.setGrid(optionGrid);
	return optionConfig;
}




/**
 * 
 * @Title: getBaseInfoPieForAnalysisPlacePie   
 * @Description: TODO 人事档案的饼状图分析
 * @param resdataList
 * @param optionSeriesName
 * @param optionTitleText
 * @param optionTitleSubtext
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getPieForAnalysis(List<Map<String, String>> resdataList,String optionSeriesName,String optionTitleText,String optionTitleSubtext)
{
	OptionConfig optionConfig = new OptionConfig();
	OptionSeries[] optionSeriesArr = new OptionSeries[1];
	SeriesData[] dataArr = new SeriesData[resdataList.size()];
	int selectedLeng=0;
	if(dataArr.length>=10)
	{
		selectedLeng=10;
	}else
	{
		selectedLeng = dataArr.length;
	}
	String[] selected = new String[selectedLeng];
	LegendData[] legendDatas = new LegendData[dataArr.length];
	for(int i=0;i<dataArr.length;i++)
	{
		if(StringUtils.isBlank(resdataList.get(i).get("name")))
		{
			resdataList.get(i).put("name", "other"+i);
		}
		if(i<selectedLeng)
		{
			selected[i]=resdataList.get(i).get("name").toString();
		}
		LegendData legendData = new LegendData();
		legendData.setName(resdataList.get(i).get("name").toString());
		legendDatas[i]=legendData;
		SeriesData seriesData = new SeriesData();
		seriesData.setName(resdataList.get(i).get("name").toString());
		seriesData.setValue(Double.valueOf(String.valueOf(resdataList.get(i).get("value"))));
		dataArr[i]=seriesData;
	}
	OptionSeries optionSeries = new OptionSeries();
	optionSeries.setName(optionSeriesName);
	optionSeries.setType("pie");
	optionSeries.setRadius("55%");
	optionSeries.setCenter(new String[]{"40%", "50%"});
	Emphasis emphasis = new Emphasis();
	ItemStyle itemStyle = new ItemStyle();
	itemStyle.setShadowBlur(10);
	itemStyle.setShadowOffsetX(0);
	itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
	emphasis.setItemStyle(itemStyle);
	optionSeries.setData(dataArr);
	optionSeriesArr[0] = optionSeries;
	optionConfig.setSeries(optionSeriesArr);
	optionConfig = pieOption.getPieLegendChartOption(legendDatas,selected,optionSeriesArr);
	OptionTitle optionTitle = new OptionTitle();
	optionTitle.setText(optionTitleText);
	optionTitle.setSubtext(optionTitleSubtext);
	optionTitle.setLeft("center");
	optionConfig.setTitle(optionTitle);
	return optionConfig;
}




/**
 * 
 * @Title: getNativePlacePie   
 * @Description: TODO HR人员籍贯占比
 * @param account
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getNativePlacePie(Account account)
{
	OptionConfig optionConfig = new OptionConfig();
	List<Map<String, String>> resdataList = echartsHrMapper.getNativePlacePie(account.getOrgId());
	OptionSeries[] optionSeriesArr = new OptionSeries[1];
	SeriesData[] dataArr = new SeriesData[resdataList.size()];
	int selectedLeng=0;
	if(dataArr.length>=10)
	{
		selectedLeng=10;
	}else
	{
		selectedLeng = dataArr.length;
	}
	String[] selected = new String[selectedLeng];
	LegendData[] legendDatas = new LegendData[dataArr.length];
	for(int i=0;i<dataArr.length;i++)
	{
		if(StringUtils.isBlank(resdataList.get(i).get("name")))
		{
			resdataList.get(i).put("name", "other"+i);
		}
		if(i<selectedLeng)
		{
			selected[i]=resdataList.get(i).get("name").toString();
		}
		LegendData legendData = new LegendData();
		legendData.setName(resdataList.get(i).get("name").toString());
		legendDatas[i]=legendData;
		SeriesData seriesData = new SeriesData();
		seriesData.setName(resdataList.get(i).get("name").toString());
		seriesData.setValue(Double.valueOf(String.valueOf(resdataList.get(i).get("value"))));
		dataArr[i]=seriesData;
	}
	OptionSeries optionSeries = new OptionSeries();
	optionSeries.setName("地区");
	optionSeries.setType("pie");
	optionSeries.setRadius("55%");
	optionSeries.setCenter(new String[]{"40%", "50%"});
	Emphasis emphasis = new Emphasis();
	ItemStyle itemStyle = new ItemStyle();
	itemStyle.setShadowBlur(10);
	itemStyle.setShadowOffsetX(0);
	itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
	emphasis.setItemStyle(itemStyle);
	optionSeries.setData(dataArr);
	optionSeriesArr[0] = optionSeries;
	optionConfig.setSeries(optionSeriesArr);
	optionConfig = pieOption.getPieLegendChartOption(legendDatas,selected,optionSeriesArr);
	OptionTitle optionTitle = new OptionTitle();
	optionTitle.setText("人员籍贯统计");
	optionTitle.setSubtext("人员籍贯占比");
	optionTitle.setLeft("center");
	optionConfig.setTitle(optionTitle);
	return optionConfig;
}

/**
 * 
 * @Title: getWorkTypeBar   
 * @Description: TODO HR人员工种对比 
 * @param account
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getWorkTypeBar(Account account)
{
	List<Map<String, Object>> resList = echartsHrMapper.getWorkTypeBar(account.getOrgId());
	OptionConfig optionConfig = new OptionConfig();
	String[] xAxisData = new String[resList.size()];
	Object[] data = new Object[resList.size()];
	for(int i=0;i<resList.size();i++)
	{
		if(resList.get(i).get("name")==null)
		{
			xAxisData[i]="其它";
		}else
		{
			xAxisData[i]=resList.get(i).get("name").toString();
		}
		data[i] = resList.get(i).get("value");
	}
	OptionSeries[] seriesDatas = new OptionSeries[1];
	OptionSeries optionSeries = new OptionSeries();
	optionSeries.setType("bar");
	optionSeries.setData(data);
	
	seriesDatas[0]=optionSeries;
	optionConfig=barOption.getBarSimpleChartOption(xAxisData, seriesDatas);
	return optionConfig;
}


/**
 * 
 * @Title: getHighsetShoolPie   
 * @Description: TODO 获取HR学历占比
 * @param account
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getHighsetShoolPie(Account account)
{
	List<Map<String, Object>> resList = echartsHrMapper.getHighsetShoolPie(account.getOrgId());
	OptionConfig optionConfig = new OptionConfig();
	OptionSeries[] optionSeriesArr = new OptionSeries[1];
	OptionSeries optionSeries = new OptionSeries();
	optionSeries.setName("学历占比");
	optionSeries.setType("pie");
	optionSeries.setRadius(new String[] {"50%","70%"});
	optionSeries.setAvoidLabelOverlap(false);
	Label label = new Label();
	label.setShow(false);
	label.setPosition("center");
	optionSeries.setLabel(label);
	Emphasis emphasis = new Emphasis();
	Label label1 = new Label();
	label1.setShow(true);
	label1.setFontSize(20);
	label1.setFontWeight("bold");
	emphasis.setLabel(label1);
	optionSeries.setEmphasis(emphasis);
	LabelLine labelLine = new LabelLine();
	labelLine.setShow(false);
	optionSeries.setLabelLine(labelLine);
	LegendData[] legendDatas = new LegendData[resList.size()];
	for(int i=0;i<resList.size();i++)
	{
		LegendData legendData = new LegendData();
		legendData.setName(resList.get(i).get("name").toString());
		legendDatas[i]=legendData;
	}
	optionSeries.setData(resList.toArray());
	optionSeriesArr[0]=optionSeries;
	optionConfig = pieOption.getDoughnutChartOption(legendDatas,optionSeriesArr);
	return optionConfig;
}

}
