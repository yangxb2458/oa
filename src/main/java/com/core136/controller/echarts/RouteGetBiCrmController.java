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
import com.core136.service.echarts.EchartsCrmService;

@RestController
@RequestMapping("/ret/echartscrmget")
public class RouteGetBiCrmController {
@Autowired
private EchartsCrmService echartsCrmService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: getBiCustomerIndustryPie   
 * @Description: TODO 获取crm的客户行业占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiCustomerIndustryPie", method = RequestMethod.POST)
public RetDataBean getBiCustomerIndustryPie(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsCrmService.getBiCustomerIndustryPie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getBiCustomerKeepUserPie   
 * @Description: TODO 获取CRM销售人员的占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiCustomerKeepUserPie", method = RequestMethod.POST)
public RetDataBean getBiCustomerKeepUserPie(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsCrmService.getBiCustomerKeepUserPie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getBiCustomerAreaPie   
 * @Description: TODO 获取CRM地区占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiCustomerAreaPie", method = RequestMethod.POST)
public RetDataBean getBiCustomerAreaPie(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsCrmService.getBiCustomerAreaPie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getBiCustomerLevelPie   
 * @Description: TODO 获取CRM客户等级占比
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getBiCustomerLevelPie", method = RequestMethod.POST)
public RetDataBean getBiCustomerLevelPie(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", echartsCrmService.getBiCustomerLevelPie(account));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
}
