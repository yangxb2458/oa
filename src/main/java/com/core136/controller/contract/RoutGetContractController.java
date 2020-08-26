/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetContractController.java   
 * @Package com.core136.controller.contract   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月23日 下午1:57:12   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.contract;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.contract.Contract;
import com.core136.bean.contract.ContractBill;
import com.core136.bean.contract.ContractPayable;
import com.core136.bean.contract.ContractPayableRecord;
import com.core136.bean.contract.ContractPriv;
import com.core136.bean.contract.ContractReceivables;
import com.core136.bean.contract.ContractReceivablesRecord;
import com.core136.bean.contract.ContractSendgoods;
import com.core136.bean.contract.ContractSort;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.contract.ContractBillService;
import com.core136.service.contract.ContractDetailsService;
import com.core136.service.contract.ContractPayableRecordService;
import com.core136.service.contract.ContractPayableService;
import com.core136.service.contract.ContractPrivService;
import com.core136.service.contract.ContractReceivablesRecordService;
import com.core136.service.contract.ContractReceivablesService;
import com.core136.service.contract.ContractSendgoodsService;
import com.core136.service.contract.ContractService;
import com.core136.service.contract.ContractSortService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import org.core136.common.utils.SysTools;
import com.github.pagehelper.PageInfo;

/**   
 * @ClassName:  RoutGetContractController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月23日 下午1:57:12   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/ret/contractget")
public class RoutGetContractController {
@Autowired
private ContractSortService contractSortService;
@Autowired
private ContractService contractService;
@Autowired
private ContractDetailsService contractDetailsService;
@Autowired
private ContractPrivService contractPrivService;
@Autowired
private ContractReceivablesService contractReceivablesService;
@Autowired
private ContractPayableService contractPayableService;
@Autowired
private ContractBillService contractBillService;
@Autowired
private ContractSendgoodsService contractSendgoodsService;
@Autowired
private ContractReceivablesRecordService contractReceivablesRecordService;
@Autowired
private ContractPayableRecordService contractPayableRecordService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: getContractTop   
 * @Description: TODO 获取近期的合同列表
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getContractTop", method = RequestMethod.POST)
public RetDataBean getContractTop(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", contractService.getContractTop(account.getOrgId()));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getContractBillTop   
 * @Description: TODO 获取近期发票列表
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getContractBillTop", method = RequestMethod.POST)
public RetDataBean getContractBillTop(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", contractBillService.getContractBillTop(account.getOrgId()));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getPayableRecordTop   
 * @Description: TODO 近期付款记录
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getPayableRecordTop", method = RequestMethod.POST)
public RetDataBean getPayableRecordTop(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", contractPayableRecordService.getPayableRecordTop(account.getOrgId()));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getReceivRecordTop   
 * @Description: TODO 收款记录
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getReceivRecordTop", method = RequestMethod.POST)
public RetDataBean getReceivRecordTop(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", contractReceivablesRecordService.getReceivRecordTop(account.getOrgId()));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getDeskBillList   
 * @Description: TODO 获取财务门户发票列表
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value = "/getDeskBillList", method = RequestMethod.POST)
public RetDataBean getDeskBillList(HttpServletRequest request) {
	try {
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", contractBillService.getDeskBillList(account.getOrgId()));
	} catch (Exception e) {
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getDeskPayableList   
 * @Description: TODO 获取财务门户近期付款记录
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getDeskPayableList",method=RequestMethod.POST)
public RetDataBean getDeskPayableList(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		String beginTime = SysTools.getTime("yyyy-MM-dd HH:mm:ss");
		return RetDataTools.Ok("请求成功!",contractPayableService.getDeskPayableList(account.getOrgId(),beginTime,account.getOpFlag(),account.getAccountId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getDeskReceivablesList   
 * @Description: TODO 获取财务门户近期收款情况
 * @param request
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getDeskReceivablesList",method=RequestMethod.POST)
public RetDataBean getDeskReceivablesList(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		String beginTime = SysTools.getTime("yyyy-MM-dd HH:mm:ss");
		return RetDataTools.Ok("请求成功!",contractReceivablesService.getDeskReceivablesList(account.getOrgId(),beginTime,account.getOpFlag(),account.getAccountId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getContractPayableRecordById   
 * @Description: TODO 获取付款详情
 * @param request
 * @param contractPayableRecord
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getContractPayableRecordById",method=RequestMethod.POST)
public RetDataBean getContractPayableRecordById(HttpServletRequest request,ContractPayableRecord contractPayableRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractPayableRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",contractPayableRecordService.selectOneContractPayableRecord(contractPayableRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getContractReceivablesRecordById   
 * @Description: TODO 获取收款详情
 * @param request
 * @param contractReceivablesRecord
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getContractReceivablesRecordById",method=RequestMethod.POST)
public RetDataBean getContractReceivablesRecordById(HttpServletRequest request,ContractReceivablesRecord contractReceivablesRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractReceivablesRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",contractReceivablesRecordService.selectOneContractReceivablesRecord(contractReceivablesRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getContractSendgoodsList   
 * @Description: TODO 获取发货列表
 * @param request
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param contractType
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getContractSendgoodsList",method=RequestMethod.POST)
public RetDataBean getContractSendgoodsList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String contractType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("G.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=contractSendgoodsService.getContractSendgoodsList(pageParam,contractType,beginTime,endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getContractReceivablesRecordList   
 * @Description: TODO 获取收款记录
 * @param request
 * @param pageParam
 * @param receivablesId
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getContractReceivablesRecordList",method=RequestMethod.POST)
public RetDataBean getContractReceivablesRecordList(
		HttpServletRequest request,
		PageParam pageParam,
		String receivablesId
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=contractReceivablesRecordService.getContractReceivablesRecordList(pageParam,receivablesId);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getContractPayableRecordList   
 * @Description: TODO 获取付款记录
 * @param request
 * @param pageParam
 * @param receivablesId
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getContractPayableRecordList",method=RequestMethod.POST)
public RetDataBean getContractPayableRecordList(
		HttpServletRequest request,
		PageParam pageParam,
		String payableId
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=contractPayableRecordService.getContractPayableRecordList(pageParam,payableId);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}



/**
 * 
 * @Title: getContractSendgoodsById   
 * @Description: TODO 获取发货详情
 * @param request
 * @param contractSendgoods
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/getContractSendgoodsById",method=RequestMethod.POST)
public RetDataBean getContractSendgoodsById(HttpServletRequest request,ContractSendgoods contractSendgoods)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractSendgoods.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",contractSendgoodsService.selectOneContractSendgoods(contractSendgoods));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getContractBillList   
 * @Description: TODO 获取票据列表
 * @param: request
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: isOpen
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getContractBillList",method=RequestMethod.POST)
public RetDataBean getContractBillList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String isOpen,
		String billType,
		String status
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("B.CREATE_TIME");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=contractBillService.getContractBillList(pageParam,isOpen,status,billType,beginTime,endTime);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getContractBillById   
 * @Description: TODO 获取票据记录详情
 * @param: request
 * @param: contractBill
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getContractBillById",method=RequestMethod.POST)
public RetDataBean getContractBillById(HttpServletRequest request,ContractBill contractBill)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractBill.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",contractBillService.selectOneContractBill(contractBill));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getContractPayableList   
 * @Description: TODO 获取应付款列表
 * @param: request
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: status
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getContractPayableList",method=RequestMethod.POST)
public RetDataBean getContractPayableList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String status
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=contractPayableService.getContractPayableList(pageParam,beginTime,endTime,status);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getContractPayableById   
 * @Description: TODO 获取应付款详情
 * @param: request
 * @param: contractPayable
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getContractPayableById",method=RequestMethod.POST)
public RetDataBean getContractPayableById(HttpServletRequest request,ContractPayable contractPayable)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractPayable.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",contractPayableService.selectOneContractPayable(contractPayable));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getContractReceivablesList   
 * @Description: TODO 获取应收款列表
 * @param: request
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: status
 * @param: userPriv
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getContractReceivablesList",method=RequestMethod.POST)
public RetDataBean getContractReceivablesList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String status,
		String userPriv
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, Object>> pageInfo=contractReceivablesService.getContractReceivablesList(pageParam,userPriv,beginTime,endTime,status);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getSelect2ContractList   
 * @Description: TODO SELECT2的列表
 * @param: request
 * @param: search
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getSelect2ContractList",method=RequestMethod.POST)
public RetDataBean getSelect2ContractList(HttpServletRequest request,String search)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!",contractService.getSelect2ContractList(account.getOrgId(),search));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: getContractReceivablesById   
 * @Description: TODO 应收款详情
 * @param: request
 * @param: contractReceivables
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getContractReceivablesById",method=RequestMethod.POST)
public RetDataBean getContractReceivablesById(HttpServletRequest request,ContractReceivables contractReceivables)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractReceivables.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",contractReceivablesService.selectOneContractReceivables(contractReceivables));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: getContractManageList   
 * @Description: TODO 获取合同管理列表   
 * @param: request
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: contractType
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getContractManageList",method=RequestMethod.POST)
public RetDataBean getContractManageList(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String contractType
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=contractService.getContractManageList(pageParam,beginTime,endTime,contractType);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: queryContract   
 * @Description: TODO 合同查询列表
 * @param: request
 * @param: pageParam
 * @param: customerName
 * @param: beginTime
 * @param: endTime
 * @param: contractType
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/queryContract",method=RequestMethod.POST)
public RetDataBean queryContract(
		HttpServletRequest request,
		PageParam pageParam,
		String beginTime,
		String endTime,
		String contractType,
		String mySignUser
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOpFlag(account.getOpFlag());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, String>> pageInfo=contractService.queryContract(pageParam,beginTime,endTime,contractType,mySignUser);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title getErpBomSortTree   
 * @Description TODO 获取BOM 分类树结构
 * @param request
 * @param sortId
 * @return      
 * List<Map<String,Object>>
 */
@RequestMapping(value="/getContractSortTree",method=RequestMethod.POST)
public List<Map<String,String>> getContractSortTree(HttpServletRequest request,String sortId)
{
	try
	{
		String sortLeave = "0";
		if(StringUtils.isNotBlank(sortId))
		{
			sortLeave = sortId;
		}
		Account account=accountService.getRedisAccount(request);
		return contractSortService.getContractSortTree(account.getOrgId(), sortLeave);
	}catch (Exception e) {
		return null;
	}
}

/**
 * 
* @Title: getContractById 
* @Description: TODO 获取合同分类的详情信息
* @param @param request
* @param @param contractSort
* @param @return 设定文件 
* @return RetDataBean 返回类型 

 */
@RequestMapping(value="/getContractSortById",method=RequestMethod.POST)
public RetDataBean getContractSortById(HttpServletRequest request,ContractSort contractSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!",contractSortService.selectOneContractSort(contractSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getContractDetailsList   
 * @Description: TODO 获取指定的合同明细
 * @param: request
 * @param: contractId
 * @param: pageParam
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/getContractDetailsList",method=RequestMethod.POST)
public RetDataBean getContractDetailsList(
		HttpServletRequest request,
		String contractId,
		PageParam pageParam
		)
{
	try
	{
		if(StringUtils.isBlank(pageParam.getSort()))
		{
			pageParam.setSort("SORT_NO");
		}else
		{
			pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
		}
		if(StringUtils.isBlank(pageParam.getSortOrder()))
		{
			pageParam.setSortOrder("asc");
		}
	String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
	Account account=accountService.getRedisAccount(request);
	pageParam.setOrderBy(orderBy);
	pageParam.setAccountId(account.getAccountId());
	pageParam.setOrgId(account.getOrgId());
	PageInfo<Map<String, Object>> pageInfo=contractDetailsService.getContractDetailsList(pageParam,contractId);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
* @Title: createCode 
* @Description: TODO 获取合同编号
* @param @param request
* @param @return 设定文件 
* @return RetDataBean 返回类型 

 */
@RequestMapping(value="/createCode",method=RequestMethod.POST)
public RetDataBean createCode(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		String contractCode ="AIP-"+SysTools.getTime("yyyy")+SysTools.autoGenericCode((contractService.getContractCount(account.getOrgId())+""),3);
		return RetDataTools.Ok("生成合同编号成功!",contractCode);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
	/**
	 * 根据合同id获取合同对象
	 * @param contractId 合同id
	 * @return
	 */
	@RequestMapping("/getContractById")
	public RetDataBean getContractById(HttpServletRequest request,Contract contract) {
		try {
			Account account=accountService.getRedisAccount(request);
			contract.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", contractService.selectOneContract(contract));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: getContractPriv 
	* @Description: TODO 获取合同管理的权限详情
	* @param @param request
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getContractPriv",method=RequestMethod.POST)
	public RetDataBean getContractPriv(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			ContractPriv contractPriv = new ContractPriv();
			contractPriv.setOrgId(account.getOrgId());
			return RetDataTools.Ok("生成合同编号成功!",contractPrivService.selectOneContractPriv(contractPriv));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
