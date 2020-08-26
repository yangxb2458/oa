/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetContractController.java   
 * @Package com.core136.controller.contract   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月23日 下午2:08:39   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.contract;

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
import com.core136.service.account.AccountService;
import com.core136.service.contract.ContractBillService;
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
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  RoutSetContractController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月23日 下午2:08:39   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/set/contractset")
public class RoutSetContractController {
@Autowired
private ContractService contractService;
@Autowired
private ContractSortService contractSortService;
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
 * @Title: insertContractPayableRecord
 * @Description: TODO 添加付款记录
 * @param: request
 * @param: contractPayableRecord
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertContractPayableRecord",method=RequestMethod.POST)
public RetDataBean insertContractPayableRecord(HttpServletRequest request,ContractPayableRecord contractPayableRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractPayableRecord.setRecordId(SysTools.getGUID());
		contractPayableRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		contractPayableRecord.setCreateUser(account.getAccountId());
		contractPayableRecord.setOrgId(account.getOrgId());
		return contractPayableRecordService.payableAmount(contractPayableRecord);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: deleteContractPayableRecord   
 * @Description: TODO 删除付款记录
 * @param: request
 * @param: contractPayableRecord
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteContractPayableRecord",method=RequestMethod.POST)
public RetDataBean deleteContractPayableRecord(HttpServletRequest request,ContractPayableRecord contractPayableRecord)
{
	try
	{
		if(StringUtils.isBlank(contractPayableRecord.getRecordId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		contractPayableRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除成功!",contractPayableRecordService.deleteContractPayableRecord(contractPayableRecord));
		}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: updateContractPayableRecord
 * @Description: TODO 更新付款记录
 * @param: request
 * @param: contractPayableRecord
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateContractPayableRecord",method=RequestMethod.POST)
public RetDataBean updateContractPayableRecord(HttpServletRequest request,ContractPayableRecord contractPayableRecord)
{
	try
	{
		if(StringUtils.isBlank(contractPayableRecord.getRecordId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ContractPayableRecord.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",contractPayableRecord.getRecordId());
		return RetDataTools.Ok("更新成功!",contractPayableRecordService.updateContractPayableRecord(example,contractPayableRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: insertContractReceivablesRecord
 * @Description: TODO 添加收款记录
 * @param: request
 * @param: contractReceivablesRecord
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertContractReceivablesRecord",method=RequestMethod.POST)
public RetDataBean insertContractReceivablesRecord(HttpServletRequest request,ContractReceivablesRecord contractReceivablesRecord)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractReceivablesRecord.setRecordId(SysTools.getGUID());
		contractReceivablesRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		contractReceivablesRecord.setCreateUser(account.getAccountId());
		contractReceivablesRecord.setOrgId(account.getOrgId());
		return contractReceivablesRecordService.receivableAmount(contractReceivablesRecord);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: deleteContractReceivablesRecord   
 * @Description: TODO 删除收款记录
 * @param: request
 * @param: contractReceivablesRecord
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteContractReceivablesRecord",method=RequestMethod.POST)
public RetDataBean deleteContractReceivablesRecord(HttpServletRequest request,ContractReceivablesRecord contractReceivablesRecord)
{
	try
	{
		if(StringUtils.isBlank(contractReceivablesRecord.getRecordId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		contractReceivablesRecord.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除成功!",contractReceivablesRecordService.deleteContractReceivablesRecord(contractReceivablesRecord));
		}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: updateContractReceivablesRecord  
 * @Description: TODO 更新收款记录
 * @param: request
 * @param: contractReceivablesRecord
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateContractReceivablesRecord",method=RequestMethod.POST)
public RetDataBean updateContractReceivablesRecord(HttpServletRequest request,ContractReceivablesRecord contractReceivablesRecord)
{
	try
	{
		if(StringUtils.isBlank(contractReceivablesRecord.getRecordId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ContractReceivablesRecord.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",contractReceivablesRecord.getRecordId());
		return RetDataTools.Ok("更新成功!",contractReceivablesRecordService.updateContractReceivablesRecord(example,contractReceivablesRecord));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: insertContractSendgoods
 * @Description: TODO 添加发货记录
 * @param: request
 * @param: contractSendgoods
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertContractSendgoods",method=RequestMethod.POST)
public RetDataBean insertContractSendgoods(HttpServletRequest request,ContractSendgoods contractSendgoods)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractSendgoods.setRecordId(SysTools.getGUID());
		contractSendgoods.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		contractSendgoods.setCreateUser(account.getAccountId());
		contractSendgoods.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加成功!",contractSendgoodsService.insertContractSendgoods(contractSendgoods));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: deleteContractSendgoods   
 * @Description: TODO 删除发货记录
 * @param: request
 * @param: contractSendgoods
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteContractSendgoods",method=RequestMethod.POST)
public RetDataBean deleteContractSendgoods(HttpServletRequest request,ContractSendgoods contractSendgoods)
{
	try
	{
		if(StringUtils.isBlank(contractSendgoods.getRecordId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		contractSendgoods.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除成功!",contractSendgoodsService.deleteContractSendgoods(contractSendgoods));
		}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: updatContractSendgoods  
 * @Description: TODO 更新发货记录
 * @param: request
 * @param: contractSendgoods
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateContractSendgoods",method=RequestMethod.POST)
public RetDataBean updateContractSendgoods(HttpServletRequest request,ContractSendgoods contractSendgoods)
{
	try
	{
		if(StringUtils.isBlank(contractSendgoods.getRecordId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ContractSendgoods.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",contractSendgoods.getRecordId());
		return RetDataTools.Ok("更新成功!",contractSendgoodsService.updateContractSendgoods(example,contractSendgoods));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: insertContractBill   
 * @Description: TODO 添加票据
 * @param: request
 * @param: contractBill
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertContractBill",method=RequestMethod.POST)
public RetDataBean insertContractBill(HttpServletRequest request,ContractBill contractBill)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractBill.setBillId(SysTools.getGUID());
		contractBill.setStatus("1");
		contractBill.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		contractBill.setCreateUser(account.getAccountId());
		contractBill.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加票据成功!",contractBillService.insertContractBill(contractBill));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: deleteContractBill   
 * @Description: TODO 删除票据
 * @param: request
 * @param: contractBill
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteContractBill",method=RequestMethod.POST)
public RetDataBean deleteContractBill(HttpServletRequest request,ContractBill contractBill)
{
	try
	{
		if(StringUtils.isBlank(contractBill.getBillId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(account.getOpFlag().equals("1"))
		{
			contractBill.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除票据成功!",contractBillService.deleteContractBill(contractBill));
		}else
		{
			return RetDataTools.NotOk("您没有权限删除，请与管理员联系！");
		}
		}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: updatContractBill    
 * @Description: TODO 更新票据记录
 * @param: request
 * @param: contractBill
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateContractBill",method=RequestMethod.POST)
public RetDataBean updatContractBill(HttpServletRequest request,ContractBill contractBill)
{
	try
	{
		if(StringUtils.isBlank(contractBill.getBillId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ContractBill.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("billId",contractBill.getBillId());
		return RetDataTools.Ok("票据更新成功!",contractBillService.updateContractBill(contractBill, example));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}



/**
 * 
 * @Title: insertContractPayable   
 * @Description: TODO 创建应付款记录
 * @param: request
 * @param: contractReceivables
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertContractPayable",method=RequestMethod.POST)
public RetDataBean insertContractPayable(HttpServletRequest request,ContractPayable contractPayable)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractPayable.setPayableId(SysTools.getGUID());
		contractPayable.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		contractPayable.setCreateUser(account.getAccountId());
		contractPayable.setPayabled(0.0);
		contractPayable.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加应收款成功!",contractPayableService.insertContractPayable(contractPayable));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: deleteContractPayable   
 * @Description: TODO 删除应付款记录
 * @param: request
 * @param: contractReceivables
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteContractPayable",method=RequestMethod.POST)
public RetDataBean deleteContractPayable(HttpServletRequest request,ContractPayable contractPayable)
{
	try
	{
		if(StringUtils.isBlank(contractPayable.getPayableId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(account.getOpFlag().equals("1"))
		{
			contractPayable.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除合同应付款成功!",contractPayableService.deleteContractPayable(contractPayable));
		}else
		{
			return RetDataTools.NotOk("您没有权限删除，请与管理员联系！");
		}
		}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: updateContractPayable    
 * @Description: TODO 更新应付款记录
 * @param: request
 * @param: contractReceivables
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateContractPayable",method=RequestMethod.POST)
public RetDataBean updateContractPayable(HttpServletRequest request,ContractPayable contractPayable)
{
	try
	{
		if(StringUtils.isBlank(contractPayable.getPayableId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ContractPayable.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("payableId",contractPayable.getPayableId());
		return RetDataTools.Ok("应付款更新成功!",contractPayableService.updateContractPayable(contractPayable, example));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}



/**
 * 
 * @Title: insertContractReceivables   
 * @Description: TODO 创建应收款记录
 * @param: request
 * @param: contractReceivables
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/insertContractReceivables",method=RequestMethod.POST)
public RetDataBean insertContractReceivables(HttpServletRequest request,ContractReceivables contractReceivables)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractReceivables.setReceivablesId(SysTools.getGUID());
		contractReceivables.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		contractReceivables.setCreateUser(account.getAccountId());
		contractReceivables.setReceived(0.0);
		contractReceivables.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加应收款成功!",contractReceivablesService.insertContractReceivables(contractReceivables));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: deleteContractReceivables   
 * @Description: TODO 删除应收款记录
 * @param: request
 * @param: contractReceivables
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/deleteContractReceivables",method=RequestMethod.POST)
public RetDataBean deleteContractReceivables(HttpServletRequest request,ContractReceivables contractReceivables)
{
	try
	{
		if(StringUtils.isBlank(contractReceivables.getReceivablesId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(account.getOpFlag().equals("1"))
		{
			contractReceivables.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除合同应收款成功!",contractReceivablesService.deleteContractReceivables(contractReceivables));
		}else
		{
			return RetDataTools.NotOk("您没有权限删除，请与管理员联系！");
		}
		}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: updateContractReceivables   
 * @Description: TODO 更新应收款记录
 * @param: request
 * @param: contractReceivables
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value="/updateContractReceivables",method=RequestMethod.POST)
public RetDataBean updateContractReceivables(HttpServletRequest request,ContractReceivables contractReceivables)
{
	try
	{
		if(StringUtils.isBlank(contractReceivables.getReceivablesId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ContractReceivables.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("receivablesId",contractReceivables.getReceivablesId());
		return RetDataTools.Ok("应付款更新成功!",contractReceivablesService.updateContractReceivables(contractReceivables, example));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
* @Title: delContractSort 
* @Description: TODO 删除分类
* @param @param request
* @param @param contractSort
* @param @return 设定文件 
* @return RetDataBean 返回类型 

 */
@RequestMapping(value="/delContractSort",method=RequestMethod.POST)
public RetDataBean delContractSort(HttpServletRequest request,ContractSort contractSort)
{
	try
	{
		if(StringUtils.isBlank(contractSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		contractSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除合同公类成功!",contractSortService.deleteContractSort(contractSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
* @Title: insertContractSort 
* @Description: TODO 创建合同分类
* @param @param request
* @param @param contractSort
* @param @return 设定文件 
* @return RetDataBean 返回类型 

 */
@RequestMapping(value="/insertContractSort",method=RequestMethod.POST)
public RetDataBean insertContractSort(HttpServletRequest request,ContractSort contractSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		contractSort.setSortId(SysTools.getGUID());
		contractSort.getSortLeave();
		contractSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		contractSort.setCreateUser(account.getAccountId());
		if(StringUtils.isBlank(contractSort.getSortLeave()))
		{
		contractSort.setSortLeave("0");
		}
		contractSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加合同分类成功!",contractSortService.insertContractSrot(contractSort));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
* @Title: updateContractSort 
* @Description: TODO 更新合同分类信息
* @param @param request
* @param @param contractSort
* @param @return 设定文件 
* @return RetDataBean 返回类型 

 */
@RequestMapping(value="/updateContractSort",method=RequestMethod.POST)
public RetDataBean updateContractSort(HttpServletRequest request,ContractSort contractSort)
{
	try
	{
		if(StringUtils.isBlank(contractSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(ContractSort.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("sortId",contractSort.getSortId());
		return RetDataTools.Ok("更新合同分类成功!",contractSortService.updateContractSort(contractSort, example));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
	/**
	 * 添加合同
	 * @param request
	 * @param contract
	 * @return
	 */
	@RequestMapping("/addContract")
	public RetDataBean addContract(HttpServletRequest request,Contract contract) {
		try {
			Account account=accountService.getRedisAccount(request);
			contract.setOrgId(account.getOrgId());
			contract.setCreateUser(account.getAccountId());
			contract.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			return RetDataTools.Ok("创建合同成功!", contractService.addContract(contract));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteContract   
	 * @Description: TODO 删除合同   
	 * @param: request
	 * @param: contract
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping("/deleteContract")
	public RetDataBean deleteContract(HttpServletRequest request,Contract contract) {
		try {
			if(StringUtils.isBlank(contract.getContractId())) {
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				contract.setCreateUser(account.getAccountId());
			}
			return RetDataTools.Ok("编辑成功!",contractService.deleteContract(contract));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 编辑合同
	 * @param request
	 * @param contract
	 * @return
	 */
	@RequestMapping("/updateContract")
	public RetDataBean updateContract(HttpServletRequest request,Contract contract) {
		try {
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(contract.getContractId())) {
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Example example = new Example(Contract.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("contractId",contract.getContractId());
			return RetDataTools.Ok("编辑成功!",contractService.updateContract(contract, example));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: setContractPriv 
	* @Description: TODO 设置合同管理权限
	* @param @param request
	* @param @param crmPriv
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/setContractPriv",method=RequestMethod.POST)
	public RetDataBean setContractPriv(HttpServletRequest request,ContractPriv contractPriv)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(account.getOpFlag().equals("1"))
			{
				contractPriv.setOrgId(account.getOrgId());
				int count = contractPrivService.isExistChild(account.getOrgId());
				if(count<=0)
				{
					contractPriv.setPrivId(SysTools.getGUID());
					return RetDataTools.Ok("更新权限成功!", contractPrivService.insertContractPriv(contractPriv));
				}else
				{
					Example example = new Example(ContractPriv.class);
					example.createCriteria().andEqualTo("orgId",account.getOrgId());
					return RetDataTools.Ok("更新权限成功!", contractPrivService.updateContractPriv(contractPriv, example));
				}
			}else
			{
				return RetDataTools.NotOk("对不起,您不是管理员!");
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
}
