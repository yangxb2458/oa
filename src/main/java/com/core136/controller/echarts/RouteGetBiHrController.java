package com.core136.controller.echarts;

import javax.servlet.http.HttpServletRequest;

import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.service.account.AccountService;
import com.core136.service.echarts.EchartsHrService;

@RestController
@RequestMapping("/ret/echartshrget")
public class RouteGetBiHrController {
@Autowired
private EchartsHrService echartsHrService;
@Autowired
private AccountService accountService;

/**
 * 
 * @Title: getCareTableForAnalysis   
 * @Description: TODO 人员关怀复职分析tabale
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getCareTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getCareTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getCareTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getCareBarForAnalysis   
 * @Description: TODO 人员关怀复职柱状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getCareBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getCareBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getCareBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getCarePieForAnalysis   
 * @Description: TODO 人员关怀复饼状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getCarePieForAnalysis", method = RequestMethod.POST)
public RetDataBean getCarePieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getCarePieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getReinstatTableForAnalysis   
 * @Description: TODO 人员复职分析tabale
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getReinstatTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getReinstatTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getReinstatTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getReinstatBarForAnalysis   
 * @Description: TODO 人员复职柱状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getReinstatBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getReinstatBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getReinstatBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getReinstatPieForAnalysis   
 * @Description: TODO 人员复饼状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getReinstatPieForAnalysis", method = RequestMethod.POST)
public RetDataBean getReinstatPieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getReinstatPieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getEvaluationTableForAnalysis   
 * @Description: TODO 职称评定分析tabale
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getEvaluationTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getEvaluationTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getEvaluationTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getEvaluationBarForAnalysis   
 * @Description: TODO 职称评定柱状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getEvaluationBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getEvaluationBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getEvaluationBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getEvaluationPieForAnalysis   
 * @Description: TODO 职称评定饼状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getEvaluationPieForAnalysis", method = RequestMethod.POST)
public RetDataBean getEvaluationPieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getEvaluationPieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getLeaveTableForAnalysis   
 * @Description: TODO 人员离职分析tabale
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getLeaveTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getLeaveTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getLeaveTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getLeaveBarForAnalysis   
 * @Description: TODO 人员离职柱状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getLeaveBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getLeaveBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getLeaveBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getLeavePieForAnalysis   
 * @Description: TODO 人员离职饼状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getLeavePieForAnalysis", method = RequestMethod.POST)
public RetDataBean getLeavePieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getLeavePieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getTransferTableForAnalysis   
 * @Description: TODO 调动类型分析tabale
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getTransferTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getTransferTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getTransferTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getTransferBarForAnalysis   
 * @Description: TODO 调动类型柱状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getTransferBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getTransferBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getTransferBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getTransferPieForAnalysis   
 * @Description: TODO 调动类型饼状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getTransferPieForAnalysis", method = RequestMethod.POST)
public RetDataBean getTransferPieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getTransferPieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}



/**
 * 
 * @Title: getSkillsTableForAnalysis   
 * @Description: TODO 劳动技能分析tabale
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getSkillsTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getSkillsTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getSkillsTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getSkillsBarForAnalysis   
 * @Description: TODO劳动技能柱状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getSkillsBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getSkillsBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getSkillsBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getSkillsPieForAnalysis   
 * @Description: TODO 劳动技能饼状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getSkillsPieForAnalysis", method = RequestMethod.POST)
public RetDataBean getSkillsPieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getSkillsPieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getLearnTableForAnalysis   
 * @Description: TODO 学习经功分析tabale
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getLearnTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getLearnTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getLearnTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getLearnBarForAnalysis   
 * @Description: TODO 学习经历柱状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getLearnBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getLearnBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getLearnBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getLearnPieForAnalysis   
 * @Description: TODO 学习经历饼状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getLearnPieForAnalysis", method = RequestMethod.POST)
public RetDataBean getLearnPieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getLearnPieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}



/**
 * 
 * @Title: getLicenceTableForAnalysis   
 * @Description: TODO 证照分析tabale
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getLicenceTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getLicenceTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getLicenceTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getLicenceBarForAnalysis   
 * @Description: TODO 证照柱状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getLicenceBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getLicenceBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getLicenceBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getLicencePieForAnalysis   
 * @Description: TODO 证照饼状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getLicencePieForAnalysis", method = RequestMethod.POST)
public RetDataBean getLicencePieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getLicencePieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getIncentiveTableForAnalysis   
 * @Description: TODO 奖惩分析tabale
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getIncentiveTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getIncentiveTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getIncentiveTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getIncentiveBarForAnalysis   
 * @Description: TODO 奖惩柱状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getIncentiveBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getIncentiveBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getIncentiveBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getIncentivePieForAnalysis   
 * @Description: TODO 奖惩饼状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getIncentivePieForAnalysis", method = RequestMethod.POST)
public RetDataBean getIncentivePieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getIncentivePieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getContractTableForAnalysis   
 * @Description: TODO 合同分析tabale
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getContractTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getContractTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getContractTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getContractBarForAnalysis   
 * @Description: TODO 合同柱状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getContractBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getContractBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getContractBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getContractPieForAnalysis   
 * @Description: TODO 合同饼状图分析
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getContractPieForAnalysis", method = RequestMethod.POST)
public RetDataBean getContractPieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getContractPieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getBaseInfoTableForAnalysis   
 * @Description: TODO 人事档案分析Table
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBaseInfoTableForAnalysis", method = RequestMethod.POST)
public RetDataBean getBaseInfoTableForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getBaseInfoTableForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getBaseInfoBarForAnalysis   
 * @Description: TODO 柱状图
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBaseInfoBarForAnalysis", method = RequestMethod.POST)
public RetDataBean getBaseInfoBarForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getBaseInfoBarForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getBaseInfoPieForAnalysisPlacePie   
 * @Description: TODO 人事档案分析饼状图
 * @param request
 * @param deptId
 * @param dataType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBaseInfoPieForAnalysis", method = RequestMethod.POST)
public RetDataBean getBaseInfoPieForAnalysis(HttpServletRequest request,String deptId,String dataType) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getBaseInfoPieForAnalysis(account.getOrgId(),deptId,dataType));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getNativePlacePie   
 * @Description: TODO HR人员籍贯占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getNativePlacePie", method = RequestMethod.POST)
public RetDataBean getNativePlacePie(HttpServletRequest request) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getNativePlacePie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getWorkTypeBar   
 * @Description: TODO  HR人员工种对比 
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getWorkTypeBar", method = RequestMethod.POST)
public RetDataBean getWorkTypeBar(HttpServletRequest request) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getWorkTypeBar(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getHighsetShoolPie   
 * @Description: TODO 获取HR学历占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getHighsetShoolPie", method = RequestMethod.POST)
public RetDataBean getHighsetShoolPie(HttpServletRequest request) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsHrService.getHighsetShoolPie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

}
