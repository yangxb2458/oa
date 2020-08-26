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
import com.core136.service.echarts.EchartsBpmService;

@RestController
@RequestMapping("/ret/echartsbpmget")
public class RouteGetBiBpmController {
@Autowired
private EchartsBpmService echartsBpmService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: getBiBpmFlowByMonthLine   
 * @Description: TODO 按月份统计工作量
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiBpmFLowByMonthLine", method = RequestMethod.POST)
public RetDataBean getBiBpmFlowByMonthLine(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsBpmService.getBiBpmFlowByMonthLine(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getBiBpmFlowByAccountPie   
 * @Description: TODO  前10位流程处理最多的人员工作量占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiBpmFlowByAccountPie", method = RequestMethod.POST)
public RetDataBean getBiBpmFlowByAccountPie(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsBpmService.getBiBpmFlowByAccountPie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getBiBpmFlowPie   
 * @Description: TODO  获取BPM使用分类前10的占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiBpmFlowPie", method = RequestMethod.POST)
public RetDataBean getBiBpmFlowPie(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsBpmService.getBiBpmFlowPie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getBiBpmFlowByDeptPie   
 * @Description: TODO 部门BPM占比前10的占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiBpmFlowByDeptPie", method = RequestMethod.POST)
public RetDataBean getBiBpmFlowByDeptPie(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsBpmService.getBiBpmFlowByDeptPie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

}
