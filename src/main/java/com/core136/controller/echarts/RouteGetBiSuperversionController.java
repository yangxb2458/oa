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
import com.core136.service.echarts.EchartsSuperversionService;

@RestController
@RequestMapping("/ret/echartssuperversionget")
public class RouteGetBiSuperversionController {
@Autowired
private EchartsSuperversionService echartsSuperversionService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: getBiSuperversionByLeadPie   
 * @Description: TODO 前10位领导的人员工作量占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiSuperversionByLeadPie", method = RequestMethod.POST)
public RetDataBean getBiSuperversionByLeadPie(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsSuperversionService.getBiSuperversionByLeadPie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getBiBpmFlowByMonthLine   
 * @Description: TODO 按月份统计工作量
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiSuperversionByMonthLine", method = RequestMethod.POST)
public RetDataBean getBiSuperversionByMonthLine(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsSuperversionService.getBiSuperversionByMonthLine(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getBiSuperversionTypePie   
 * @Description: TODO 获取BPM使用分类前10的占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiSuperversionTypePie", method = RequestMethod.POST)
public RetDataBean getBiSuperversionTypePie(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsSuperversionService.getBiSuperversionTypePie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getBiSuperversionStatusTypePie   
 * @Description: TODO 获取督查督办当前状态总数
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiSuperversionStatusTypePie", method = RequestMethod.POST)
public RetDataBean getBiSuperversionStatusTypePie(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsSuperversionService.getBiSuperversionStatusTypePie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

}
