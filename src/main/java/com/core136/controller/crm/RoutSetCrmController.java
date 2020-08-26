package com.core136.controller.crm;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.crm.CrmContactRecord;
import com.core136.bean.crm.CrmContractInfo;
import com.core136.bean.crm.CrmCustomer;
import com.core136.bean.crm.CrmIndustry;
import com.core136.bean.crm.CrmInquiry;
import com.core136.bean.crm.CrmLinkMan;
import com.core136.bean.crm.CrmMyProduct;
import com.core136.bean.crm.CrmPriv;
import com.core136.bean.crm.CrmQuotation;
import com.core136.bean.crm.CrmTags;
import com.core136.service.account.AccountService;
import com.core136.service.crm.CrmContactRecordService;
import com.core136.service.crm.CrmContractInfoService;
import com.core136.service.crm.CrmCustomerService;
import com.core136.service.crm.CrmIndustryService;
import com.core136.service.crm.CrmInquiryService;
import com.core136.service.crm.CrmLinkManService;
import com.core136.service.crm.CrmMyProductService;
import com.core136.service.crm.CrmPrivService;
import com.core136.service.crm.CrmQuotationMxService;
import com.core136.service.crm.CrmQuotationService;
import com.core136.service.crm.CrmTagsService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  RoutSetCrmController   
 * @Description: CRM管理数据接口
 * @author: 稠云信息
 * @date:   2019年2月12日 上午9:25:07   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/set/crmset")
public class RoutSetCrmController {
	@Autowired
	private CrmCustomerService crmCustomerService;
	@Autowired
	private CrmLinkManService crmLinkManService;
	@Autowired
	private CrmContactRecordService crmContactRecordService;
	@Autowired
	private CrmPrivService crmPrivService;
	@Autowired
	private CrmMyProductService crmMyProductService;
	@Autowired
	private CrmIndustryService crmIndustryService;
	@Autowired
	private CrmTagsService crmTagsService;
	@Autowired
	private CrmContractInfoService crmContractInfoService;
	@Autowired
	private CrmInquiryService crmInquiryService;
	@Autowired
	private CrmQuotationService crmQuotationService;
	@Autowired
	private CrmQuotationMxService crmQuotationMxService;
	@Autowired
	private AccountService accountService;
	
	/**
	 * 
	* @Title: insertCrmQuotation
	* @Description: TODO 创建报价单
	* @param @param request
	* @param @param crmQuotation
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/insertCrmQuotation",method=RequestMethod.POST)
	public RetDataBean insertCrmQuotation(HttpServletRequest request,CrmQuotation crmQuotation)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmQuotation.setQuotationId(SysTools.getGUID());
			crmQuotation.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			crmQuotation.setCreateUser(account.getAccountId());
			crmQuotation.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建成功!", crmQuotationService.insertCrmQuotation(crmQuotation));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: deleteCrmQuotation 
	* @Description: TODO 删除报价单
	* @param @param request
	* @param @param crmQuotation
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/deleteCrmQuotation",method=RequestMethod.POST)
	public RetDataBean deleteCrmQuotation(HttpServletRequest request,CrmQuotation crmQuotation)
	{
		try
		{
			if(StringUtils.isBlank(crmQuotation.getQuotationId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			crmQuotation.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除报价单成功!",crmQuotationService.deleteCrmQuotation(crmQuotation));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: updateCrmQuotation 
	* @Description: TODO 更新产品信息
	* @param @param request
	* @param @param crmQuotation
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/updateCrmQuotation",method=RequestMethod.POST)
	public RetDataBean updateCrmQuotation(HttpServletRequest request,CrmQuotation crmQuotation)
	{
		try
		{
			if(StringUtils.isBlank(crmQuotation.getQuotationId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(CrmQuotation.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("quotationId",crmQuotation.getQuotationId());
			return RetDataTools.Ok("更新报价单成功!", crmQuotationService.updateCrmQuotation(example,crmQuotation));
			}
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title createCrmCustomer   
	 * @Description TODO 创建客户 
	 * @param request
	 * @param crmCustomer
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/createCrmCustomer",method=RequestMethod.POST)
	public RetDataBean createCrmCustomer(HttpServletRequest request,CrmCustomer crmCustomer)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmCustomer.setCustomerId(SysTools.getGUID());
			crmCustomer.setCreateUser(account.getAccountId());
			crmCustomer.setKeepUser(account.getAccountId());
			crmCustomer.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			crmCustomer.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建客户成功!", crmCustomerService.insertCrmCustomer(crmCustomer));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title deleteCrmCustomer   
	 * @Description TODO 删除客户 
	 * @param request
	 * @param crmCustomer
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/deleteCrmCustomer",method=RequestMethod.POST)
	public RetDataBean deleteCrmCustomer(HttpServletRequest request,CrmCustomer crmCustomer)
	{
		try
		{
			if(StringUtils.isBlank(crmCustomer.getCustomerId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
			}else
			{
			return RetDataTools.Ok("客户删除成功!", crmCustomerService.deleteCrmCustomer(crmCustomer));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title toSetKeeepUser   
	 * @Description TODO 客户信息分配
	 * @param request
	 * @param customerIdArr
	 * @param keepUser
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/toSetKeeepUser",method=RequestMethod.POST)
	public RetDataBean toSetKeeepUser(HttpServletRequest request,@RequestParam(value = "customerIdArr[]") String[] customerIdArr,String toKeepUser)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(!account.getOpFlag().equals("1"))
			{
				CrmPriv crmPriv = new CrmPriv();
				crmPriv.setOrgId(account.getOrgId());
				crmPriv = crmPrivService.selectOneCrmPriv(crmPriv);
				if((","+crmPriv.getSender()+","+crmPriv.getManager()+",").indexOf(","+account.getAccountId()+",")>=0)
				{
					for(int i=0;i<customerIdArr.length;i++)
					{
					CrmCustomer crmCustomer = new CrmCustomer();
					crmCustomer.setCustomerId(customerIdArr[i]);
					crmCustomer.setKeepUser(toKeepUser);
					crmCustomer.setOrgId(account.getOrgId());
					Example example = new Example(CrmCustomer.class);
					example.createCriteria().andEqualTo("orgId",crmCustomer.getOrgId()).andEqualTo("customerId",crmCustomer.getCustomerId());
					crmCustomerService.UpdateCrmCustomer(crmCustomer, example);
					}
					return RetDataTools.Ok("分配成功");
				}else
				{
					return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
				}
			}else
			{
				for(int i=0;i<customerIdArr.length;i++)
				{
					CrmCustomer crmCustomer = new CrmCustomer();
					crmCustomer.setCustomerId(customerIdArr[i]);
					crmCustomer.setKeepUser(toKeepUser);
					crmCustomer.setOrgId(account.getOrgId());
					Example example = new Example(CrmCustomer.class);
					example.createCriteria().andEqualTo("orgId",crmCustomer.getOrgId()).andEqualTo("customerId",crmCustomer.getCustomerId());
					crmCustomerService.UpdateCrmCustomer(crmCustomer, example);
				}
				return RetDataTools.Ok("分配成功");
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title createCrmLinkMan   
	 * @Description TODO 添加客户联系人
	 * @param request
	 * @param crmLinkMan
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/createCrmLinkMan",method=RequestMethod.POST)
	public RetDataBean createCrmLinkMan(HttpServletRequest request,CrmLinkMan crmLinkMan)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmLinkMan.setLinkManId(SysTools.getGUID());
			crmLinkMan.setCreateUser(account.getAccountId());
			crmLinkMan.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			crmLinkMan.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建联系人成功!", crmLinkManService.insertCrmLinkMan(crmLinkMan));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title deleteCrmLinkMan   
	 * @Description TODO 删除联系人
	 * @param request
	 * @param crmLinkMan
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/deleteCrmLinkMan",method=RequestMethod.POST)
	public RetDataBean deleteCrmLinkMan(HttpServletRequest request,CrmLinkMan crmLinkMan)
	{
		try
		{
			if(StringUtils.isBlank(crmLinkMan.getLinkManId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			crmLinkMan.setOrgId(account.getOrgId());
			if(account.getOpFlag().equals("1"))
			{
				Example example = new Example(CrmLinkMan.class);
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("linkManId",crmLinkMan.getLinkManId());
				return RetDataTools.Ok("删除联系人成功!", crmLinkManService.deleteCrmLinkMan(crmLinkMan));
			}else
			{
				CrmPriv crmPriv = new CrmPriv();
				crmPriv.setOrgId(account.getOrgId());
				crmPriv = crmPrivService.selectOneCrmPriv(crmPriv);
				if((","+crmPriv.getManager()+",").indexOf(","+account.getAccountId()+",")>=0)
				{
					return RetDataTools.Ok("删除联系人成功!", crmLinkManService.deleteCrmLinkMan(crmLinkMan));
				}else
				{
					CrmLinkMan newCrmLinkMan = new CrmLinkMan();
					newCrmLinkMan.setOrgId(account.getOrgId());
					newCrmLinkMan.setLinkManId(crmLinkMan.getLinkManId());
					newCrmLinkMan = crmLinkManService.selectOne(newCrmLinkMan);
					CrmCustomer crmCustomer = new CrmCustomer();
					crmCustomer.setOrgId(account.getOrgId());
					crmCustomer.setCustomerId(newCrmLinkMan.getCustomerId());
					crmCustomer = crmCustomerService.selectOne(crmCustomer);
					if(crmCustomer.getKeepUser().equals(account.getAccountId()))
					{
						return RetDataTools.Ok("删除联系人成功!", crmLinkManService.deleteCrmLinkMan(crmLinkMan));
					}else
					{
						return RetDataTools.NotOk("对不起，您没有更新权限,请与系统管理员联系！");
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title updateCrmLinkMan   
	 * @Description TODO 更新联系人
	 * @param request
	 * @param crmLinkMan
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/updateCrmLinkMan",method=RequestMethod.POST)
	public RetDataBean updateCrmLinkMan(HttpServletRequest request,CrmLinkMan crmLinkMan)
	{
		try
		{
			if(StringUtils.isBlank(crmLinkMan.getLinkManId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			crmLinkMan.setOrgId(account.getOrgId());
			if(account.getOpFlag().equals("1"))
			{
				Example example = new Example(CrmLinkMan.class);
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("linkManId",crmLinkMan.getLinkManId());
				return RetDataTools.Ok("更新联系人成功!", crmLinkManService.updateCrmLinkMan(crmLinkMan, example));
			}else
			{
				CrmPriv crmPriv = new CrmPriv();
				crmPriv.setOrgId(account.getOrgId());
				crmPriv = crmPrivService.selectOneCrmPriv(crmPriv);
				if((","+crmPriv.getManager()+",").indexOf(","+account.getAccountId()+",")>=0)
				{
					Example example = new Example(CrmLinkMan.class);
					example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("linkManId",crmLinkMan.getLinkManId());
					return RetDataTools.Ok("更新联系人成功!", crmLinkManService.updateCrmLinkMan(crmLinkMan, example));
				}else
				{
					CrmLinkMan newCrmLinkMan = new CrmLinkMan();
					newCrmLinkMan.setOrgId(account.getOrgId());
					newCrmLinkMan.setLinkManId(crmLinkMan.getLinkManId());
					newCrmLinkMan = crmLinkManService.selectOne(newCrmLinkMan);
					CrmCustomer crmCustomer = new CrmCustomer();
					crmCustomer.setOrgId(account.getOrgId());
					crmCustomer.setCustomerId(newCrmLinkMan.getCustomerId());
					crmCustomer = crmCustomerService.selectOne(crmCustomer);
					if(crmCustomer.getKeepUser().equals(account.getAccountId()))
					{
						Example example = new Example(CrmLinkMan.class);
						example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("linkManId",crmLinkMan.getLinkManId());
						return RetDataTools.Ok("更新联系人成功!", crmLinkManService.updateCrmLinkMan(crmLinkMan, example));
					}else
					{
						return RetDataTools.NotOk("对不起，您没有更新权限,请与系统管理员联系！");
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title createCrmContactRecord   
	 * @Description TODO 创建联系记录  
	 * @param request
	 * @param crmLinkMan
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/createCrmContactRecord",method=RequestMethod.POST)
	public RetDataBean createCrmContactRecord(HttpServletRequest request,CrmContactRecord crmContactRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmContactRecord.setRecordId(SysTools.getGUID());
			crmContactRecord.setCreateUser(account.getAccountId());
			crmContactRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			crmContactRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加维护记录成功!", crmContactRecordService.addRecordAndCalendar(crmContactRecord));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title updateCrmCustomer   
	 * @Description TODO 更新客户信息
	 * @param request
	 * @param crmCustomer
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/updateCrmCustomer",method=RequestMethod.POST)
	public RetDataBean updateCrmCustomer(HttpServletRequest request,CrmCustomer crmCustomer)
	{
		try
		{
			if(StringUtils.isBlank(crmCustomer.getCustomerId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			crmCustomer.setOrgId(account.getOrgId());
			Example example = new Example(CrmCustomer.class);
			example.createCriteria().andEqualTo("customerId",crmCustomer.getCustomerId()).andEqualTo("orgId",crmCustomer.getOrgId());
			return RetDataTools.Ok("更新客户成功!", crmCustomerService.UpdateCrmCustomer(crmCustomer, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title setCrmPriv   
	 * @Description TODO 设置CRM权限
	 * @param request
	 * @param crmPriv
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/setCrmPriv",method=RequestMethod.POST)
	public RetDataBean setCrmPriv(HttpServletRequest request,CrmPriv crmPriv)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(account.getOpFlag().equals("1"))
			{
				CrmPriv newCrmPriv = new CrmPriv();
				newCrmPriv.setOrgId(account.getOrgId());
				crmPriv.setOrgId(account.getOrgId());
				int count = crmPrivService.selectCount(newCrmPriv);
				if(count<=0)
				{
					crmPriv.setPrivId(SysTools.getGUID());
					return RetDataTools.Ok("更新权限成功!", crmPrivService.insertCrmPriv(crmPriv));
				}else
				{
					Example example = new Example(CrmPriv.class);
					example.createCriteria().andEqualTo("orgId",account.getOrgId());
					return RetDataTools.Ok("更新权限成功!", crmPrivService.updateCrmPriv(crmPriv, example));
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
	
	/**
	 * 
	 * @Title sendWebMail   
	 * @Description TODO 发送电子邮件
	 * @param request
	 * @param crmPriv
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/sendWebMail",method=RequestMethod.POST)
	public RetDataBean sendWebMail(HttpServletRequest request,String to,String subject,String content,String attachId,String sendServiceType)
	{
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			return crmLinkManService.sendWebMail(account, to, subject, content, attachId, sendServiceType,userInfo);
	}
	
	/**
	 * 
	* @Title: addProduct 
	* @Description: TODO 添加企业可供产品
	* @param @param request
	* @param @param crmMyProduct
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/addProduct",method=RequestMethod.POST)
	public RetDataBean addProduct(HttpServletRequest request,CrmMyProduct crmMyProduct)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmMyProduct.setProductId(SysTools.getGUID());
			crmMyProduct.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加产品成功!", crmMyProductService.insertCrmMyProduct(crmMyProduct));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: delProduct 
	* @Description: TODO 删除产品 
	* @param @param request
	* @param @param crmMyProduct
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/delProduct",method=RequestMethod.POST)
	public RetDataBean delProduct(HttpServletRequest request,CrmMyProduct crmMyProduct)
	{
		try
		{
			if(StringUtils.isBlank(crmMyProduct.getProductId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			crmMyProduct.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除产品成功!", crmMyProductService.deleteCrmMyProduct(crmMyProduct));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: updateMyProduct 
	* @Description: TODO 更新产品信息
	* @param @param request
	* @param @param crmMyProduct
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/updateMyProduct",method=RequestMethod.POST)
	public RetDataBean updateMyProduct(HttpServletRequest request,CrmMyProduct crmMyProduct)
	{
		try
		{
			if(StringUtils.isBlank(crmMyProduct.getProductId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(CrmMyProduct.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("productId",crmMyProduct.getProductId());
			return RetDataTools.Ok("更新产品成功!", crmMyProductService.updateCrmMyProduct(crmMyProduct, example));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: addTags 
	* @Description: TODO 添加企来标签
	* @param @param request
	* @param @param crmTags
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/addTags",method=RequestMethod.POST)
	public RetDataBean addTags(HttpServletRequest request,CrmTags crmTags)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmTags.setTagsId(SysTools.getGUID());
			crmTags.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加企业标签成功!", crmTagsService.insertCrmTags(crmTags));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}	
	
	/**
	 * 
	* @Title: delTags 
	* @Description: TODO 删除企来标签
	* @param @param request
	* @param @param crmTags
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/delTags",method=RequestMethod.POST)
	public RetDataBean delTags(HttpServletRequest request,CrmTags crmTags)
	{
		try
		{
			if(StringUtils.isBlank(crmTags.getTagsId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			crmTags.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除企业标签成功!", crmTagsService.deleteCrmTags(crmTags));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: updateTags 
	* @Description: TODO 更新企业标签
	* @param @param request
	* @param @param crmTags
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/updateTags",method=RequestMethod.POST)
	public RetDataBean updateTags(HttpServletRequest request,CrmTags crmTags)
	{
		try
		{
			if(StringUtils.isBlank(crmTags.getTagsId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(CrmTags.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("tagsId",crmTags.getTagsId());
			return RetDataTools.Ok("更新企业标签成功!", crmTagsService.updateCrmTags(crmTags, example));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: addIndustry 
	* @Description: TODO 添加行业分类
	* @param @param request
	* @param @param crmIndustry
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/addIndustry",method=RequestMethod.POST)
	public RetDataBean addIndustry(HttpServletRequest request,CrmIndustry crmIndustry)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmIndustry.setIndustryId(SysTools.getGUID());
			crmIndustry.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加行业分类成功!", crmIndustryService.insertCrmIndustry(crmIndustry));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}	
	/**
	 * 
	* @Title: delIndustry 
	* @Description: TODO 删除企业分类
	* @param @param request
	* @param @param crmIndustry
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/delIndustry",method=RequestMethod.POST)
	public RetDataBean delIndustry(HttpServletRequest request,CrmIndustry crmIndustry)
	{
		try
		{
			if(StringUtils.isBlank(crmIndustry.getIndustryId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			crmIndustry.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除行业分类成功!", crmIndustryService.deleteCrmIndustry(crmIndustry));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: updateTags 
	* @Description: TODO 更新行业分类
	* @param @param request
	* @param @param crmIndustry
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/updateIndustry",method=RequestMethod.POST)
	public RetDataBean updateIndustry(HttpServletRequest request,CrmIndustry crmIndustry)
	{
		try
		{
			if(StringUtils.isBlank(crmIndustry.getIndustryId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(CrmIndustry.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("industryId",crmIndustry.getIndustryId());
			return RetDataTools.Ok("更新行业分类成功!", crmIndustryService.updateCrmIndustry(crmIndustry, example));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: addCrmContractInfo 
	* @Description: TODO 添加客户银行信息
	* @param @param request
	* @param @param crmcontractInfo
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/addCrmContractInfo",method=RequestMethod.POST)
	public RetDataBean addCrmContractInfo(HttpServletRequest request,CrmContractInfo crmContractInfo)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmContractInfo.setContractInfoId(SysTools.getGUID());
			crmContractInfo.setCreateUser(account.getAccountId());
			crmContractInfo.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			crmContractInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加客户银行信息成功!", crmContractInfoService.insertCrmContractInfoMapper(crmContractInfo));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: delCrmContractInfo 
	* @Description: TODO 删除银行信息
	* @param @param request
	* @param @param crmContractInfo
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/delCrmContractInfo",method=RequestMethod.POST)
	public RetDataBean delCrmContractInfo(HttpServletRequest request,CrmContractInfo crmContractInfo)
	{
		try
		{
			if(StringUtils.isBlank(crmContractInfo.getContractInfoId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			crmContractInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除银行信息成功!", crmContractInfoService.deleteCrmContractInfo(crmContractInfo));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: updateCrmContractInfo 
	* @Description: TODO 更新银行信息
	* @param @param request
	* @param @param crmContractInfo
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/updateCrmContractInfo",method=RequestMethod.POST)
	public RetDataBean updateCrmContractInfo(HttpServletRequest request,CrmContractInfo crmContractInfo)
	{
		try
		{
			if(StringUtils.isBlank(crmContractInfo.getContractInfoId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			crmContractInfo.setOrgId(account.getOrgId());
			Example example =  new Example(CrmContractInfo.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("contractInfoId",crmContractInfo.getContractInfoId());
			return RetDataTools.Ok("更新银行信息成功!", crmContractInfoService.updateCrmContractInfo(crmContractInfo,example));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: createCrmInquiry 
	* @Description: TODO 创建询价单
	* @param @param request
	* @param @param crmInquiry
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/createCrmInquiry",method=RequestMethod.POST)
	public RetDataBean createCrmInquiry(HttpServletRequest request,CrmInquiry crmInquiry,String detail)
	{
		try
		{
			JSONArray jsonArr = JSONObject.parseArray(detail);
			Account account=accountService.getRedisAccount(request);
			crmInquiry.setInquiryId(SysTools.getGUID());
			crmInquiry.setCreateUser(account.getAccountId());
			crmInquiry.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			crmInquiry.setOrgId(account.getOrgId());
			crmInquiry.setStatus("0");
			return RetDataTools.Ok("创建询价单成功!",crmInquiryService.saveCrmInquiry(crmInquiry, jsonArr));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteCrmInquiry   
	 * @Description: TODO 删除询价单
	 * @param request
	 * @param crmInquiry
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteCrmInquiry",method=RequestMethod.POST)
	public RetDataBean deleteCrmInquiry(HttpServletRequest request,CrmInquiry crmInquiry)
	{
		try
		{
			if(StringUtils.isBlank(crmInquiry.getInquiryId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			crmInquiry.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除银行信息成功!", crmInquiryService.deleteCrmInquiry(crmInquiry));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateCrmInquiry   
	 * @Description: TODO 更新询价单
	 * @param request
	 * @param crmInquiry
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateCrmInquiry",method=RequestMethod.POST)
	public RetDataBean updateCrmInquiry(HttpServletRequest request,CrmInquiry crmInquiry,String detail)
	{
		try
		{
			JSONArray jsonArr = JSONObject.parseArray(detail);
			if(StringUtils.isBlank(crmInquiry.getInquiryId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}else
			{
			Account account=accountService.getRedisAccount(request);
			crmInquiry.setOrgId(account.getOrgId());
			Example example =  new Example(CrmInquiry.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("inquiryId",crmInquiry.getInquiryId());
			return RetDataTools.Ok("更新询价单信息成功!", crmInquiryService.updateInquiryAndDetail(crmInquiry,example,jsonArr));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
}
