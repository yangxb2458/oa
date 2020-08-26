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
import com.core136.service.echarts.EchartsFinanceService;

@RestController
@RequestMapping("/ret/echartsfinanceget")
public class RouteGetBiFinanceController {
@Autowired
private EchartsFinanceService echartsFinanceService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: getARAPOptionConfig   
 * @Description: TODO 获取财务门户的收支
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getARAPOptionConfig", method = RequestMethod.POST)
public RetDataBean getARAPOptionConfig(HttpServletRequest request) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsFinanceService.getARAPOptionConfig(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getPayReceivTotalData   
 * @Description: TODO 获取应收应付总数
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getPayReceivTotalData", method = RequestMethod.POST)
public RetDataBean getPayReceivTotalData(HttpServletRequest request) {
	try {
		Account account = accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsFinanceService.getPayReceivTotalData(account.getOrgId()));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

}