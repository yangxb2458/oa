package com.core136.controller.crm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.crm.CrmContactRecord;
import com.core136.bean.crm.CrmContractInfo;
import com.core136.bean.crm.CrmCustomer;
import com.core136.bean.crm.CrmIndustry;
import com.core136.bean.crm.CrmInquiry;
import com.core136.bean.crm.CrmLinkMan;
import com.core136.bean.crm.CrmMyProduct;
import com.core136.bean.crm.CrmPriv;
import com.core136.bean.crm.CrmQuotation;
import com.core136.bean.crm.CrmQuotationMx;
import com.core136.bean.crm.CrmTags;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.crm.CrmContactRecordService;
import com.core136.service.crm.CrmContractInfoService;
import com.core136.service.crm.CrmCustomerService;
import com.core136.service.crm.CrmIndustryService;
import com.core136.service.crm.CrmInquiryDetailService;
import com.core136.service.crm.CrmInquiryService;
import com.core136.service.crm.CrmLinkManService;
import com.core136.service.crm.CrmMyProductService;
import com.core136.service.crm.CrmPrivService;
import com.core136.service.crm.CrmQuotationMxService;
import com.core136.service.crm.CrmQuotationService;
import com.core136.service.crm.CrmTagsService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
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
@RequestMapping("/ret/crmget")
public class RoutGetCrmController {
	@Autowired
	private CrmCustomerService crmCustomerService;
	@Autowired
	private CrmLinkManService crmLinkManService;
	@Autowired
	private CrmContactRecordService crmContactRecordService;
	@Autowired
	private CrmPrivService crmPrivService;
	@Autowired
	private CrmIndustryService crmIndustryService;
	@Autowired
	private CrmTagsService crmTagsService;
	@Autowired
	private CrmMyProductService crmMyProductService;
	@Autowired
	private CrmContractInfoService crmContractInfoService;
	@Autowired
	private CrmInquiryService crmInquiryService;
	@Autowired
	private CrmInquiryDetailService crmInquiryDetailService;
	@Autowired
	private CrmQuotationService crmQuotationService;
	@Autowired
	private CrmQuotationMxService crmQuotationMxService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: getCrmQuotationMxById   
	 * @Description: TODO 报价明细详情
	 * @param request
	 * @param crmQuotationMx
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getCrmQuotationMxById",method=RequestMethod.POST)
	public RetDataBean getCrmQuotationMxById(HttpServletRequest request,CrmQuotationMx crmQuotationMx)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmQuotationMx.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmQuotationMxService.selectOneCrmQuotationMx(crmQuotationMx));
		}catch (Exception e) {
			
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getCrmQuotationById   
	 * @Description: TODO 获取报价单详情
	 * @param request
	 * @param crmQuotation
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getCrmQuotationById",method=RequestMethod.POST)
	public RetDataBean getCrmQuotationById(HttpServletRequest request,CrmQuotation crmQuotation)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmQuotation.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmQuotationService.selectOneCrmQuotation(crmQuotation));
		}catch (Exception e) {
			
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getCrmCustomerList 
	 * @Description TODO 获取客户列表  
	 * @param request
	 * @param pageNumber
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @param sortOrder
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getCrmCustomerList",method=RequestMethod.POST)
	public RetDataBean getCrmCustomerList(
			HttpServletRequest request,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder,
			String industry,
			String model,
			String level,
			String intention,
			String country,
			String province,
			String city,
			String tags
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="CREATE_TIME";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
		String orderBy = sort+ " " + sortOrder;
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(CrmCustomer.class);
		example.setOrderByClause(orderBy);
		Criteria criteria = example.createCriteria();
		if(!account.getOpFlag().equals("1"))
		{
			criteria.andEqualTo("keepUser",account.getAccountId());
		}
		criteria.andEqualTo("orgId",account.getOrgId());
		if(StringUtils.isNotBlank(search))
		{
			Criteria criteria2 = example.createCriteria();
			criteria2.orLike("cnName", "%"+search+"%").orLike("enName", "%"+search+"%").orLike("focusProduct", "%"+search+"%");
			if(StringUtils.isNotBlank(industry))
			{
				criteria.andLike("industry", "%"+industry+"%");
			}
			
			if(StringUtils.isNotBlank(tags))
			{
				List<String> arrayList = new ArrayList<String>();
				if(StringUtils.isNotBlank(tags))
				{
					String [] tagsArr = null;
					if(tags.indexOf(",")>0)
					{
						tagsArr = tags.split(",");
					}else
					{
						tagsArr = new String[] {tags};
					}
					arrayList = Arrays.asList(tagsArr);
				}
				
				Criteria criteria3 = example.createCriteria();
				for(int i=0;i<arrayList.size();i++)
				{
					criteria3.orLike("tags", "%"+arrayList.get(i)+"%");
				}
				example.and(criteria3);
			}
			if(StringUtils.isNotBlank(model))
			{
				criteria.andLike("model", "%"+model+"%");
			}
			if(StringUtils.isNotBlank(level))
			{
				criteria.andLike("level", "%"+level+"%");
			}
			if(StringUtils.isNotBlank(intention))
			{
				criteria.andLike("intention", "%"+intention+"%");
			}
			if(StringUtils.isNotBlank(country))
			{
				if(!country.equals("0"))
				{
					criteria.andLike("country", "%"+country+"%");
				}
			}
			if(StringUtils.isNotBlank(province))
			{
				if(!province.equals("0"))
				{
					criteria.andLike("province", "%"+province+"%");
				}
			}
			if(StringUtils.isNotBlank(city))
			{
				if(!city.equals("0"))
				{
					criteria.andLike("city", "%"+city+"%");
				}
			}
			example.and(criteria2);
		}else
		{
			Criteria criteria2 = example.createCriteria();
			if(StringUtils.isNotBlank(industry))
			{
				criteria.andLike("industry", "%"+industry+"%");
			}
			if(StringUtils.isNotBlank(tags))
			{
				//criteria.andLike("tags", "%"+tags+"%");
				List<String> arrayList = new ArrayList<String>();
				if(StringUtils.isNotBlank(tags))
				{
					String [] tagsArr = null;
					if(tags.indexOf(",")>0)
					{
						tagsArr = tags.split(",");
					}else
					{
						tagsArr = new String[] {tags};
					}
					arrayList = Arrays.asList(tagsArr);
				}
				
				Criteria criteria3 = example.createCriteria();
				for(int i=0;i<arrayList.size();i++)
				{
					criteria3.orLike("tags", "%"+arrayList.get(i)+"%");
				}
				example.and(criteria3);
			}
			if(StringUtils.isNotBlank(model))
			{
				criteria.andLike("model", "%"+model+"%");
			}
			if(StringUtils.isNotBlank(level))
			{
				criteria.andLike("level", "%"+level+"%");
			}
			if(StringUtils.isNotBlank(intention))
			{
				criteria.andLike("intention", "%"+intention+"%");
			}
			if(StringUtils.isNotBlank(country))
			{
				if(!country.equals("0"))
				{
					criteria.andLike("country", "%"+country+"%");
				}
			}
			if(StringUtils.isNotBlank(province))
			{
				if(!province.equals("0"))
				{
					criteria.andLike("province", "%"+province+"%");
				}
			}
			if(StringUtils.isNotBlank(city))
			{
				if(!city.equals("0"))
				{
					criteria.andLike("city", "%"+city+"%");
				}
			}
			example.and(criteria2);
		}
		PageInfo<CrmCustomer> pageInfo=crmCustomerService.getCrmCustomerList(example, pageNumber, pageSize);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title getCrmCustomerById   
	 * @Description TODO 获取客户基本信息
	 * @param request
	 * @param crmCustomer
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getCrmCustomerById",method=RequestMethod.POST)
	public RetDataBean getCrmCustomerById(HttpServletRequest request,CrmCustomer crmCustomer)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmCustomer.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmCustomerService.selectOne(crmCustomer));
		}catch (Exception e) {
			
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getCrmLinkManList   
	 * @Description TODO 获取客户联系人
	 * @param request
	 * @param customerId
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getCrmLinkManList",method=RequestMethod.POST)
	public RetDataBean getCrmLinkManList(HttpServletRequest request,String customerId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", crmLinkManService.getCrmLinkManList(account.getOrgId(), customerId));
		}catch (Exception e) {
			
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title getCrmLinkMan   
	 * @Description TODO 获取联系人详情
	 * @param request
	 * @param crmLinkMan
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getCrmLinkMan",method=RequestMethod.POST)
	public RetDataBean getCrmLinkMan(HttpServletRequest request,CrmLinkMan crmLinkMan)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmLinkMan.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmLinkManService.selectOne(crmLinkMan));
		}catch (Exception e) {
			
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title getCrmLinkManInfo   
	 * @Description TODO 获取联系人信息
	 * @param request
	 * @param crmLinkMan
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getCrmLinkManInfo",method=RequestMethod.POST)
	public RetDataBean getCrmLinkManInfo(HttpServletRequest request,CrmLinkMan crmLinkMan)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmLinkMan.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmLinkManService.getCrmLinkManInfo(crmLinkMan.getOrgId(), crmLinkMan.getLinkManId()));
		}catch (Exception e) {
			
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getCrmLinkManByCustomerId   
	 * @Description TODO 获取企业下所有联系人
	 * @param request
	 * @param crmLinkMan
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getCrmLinkManByCustomerId",method=RequestMethod.POST)
	public RetDataBean getCrmLinkManByCustomerId(HttpServletRequest request,CrmLinkMan crmLinkMan)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmLinkMan.setOrgId(account.getOrgId());
			Example example = new Example(CrmLinkMan.class);
			example.createCriteria().andEqualTo("orgId",crmLinkMan.getOrgId()).andEqualTo("customerId",crmLinkMan.getCustomerId());
			return RetDataTools.Ok("请求成功!", crmLinkManService.getCrmLinkManByCustomerId(example));
		}catch (Exception e) {
			
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title getRecordByCustomerId   
	 * @Description TODO 获取企业联系记录 
	 * @param request
	 * @param pageNumber
	 * @param pageSize
	 * @param customerId
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getRecordByCustomerId",method=RequestMethod.POST)
	public RetDataBean getRecordByCustomerId(
			HttpServletRequest request,
			Integer pageNumber,
			Integer pageSize,
			String customerId
			)
		{
		try
		{
		Account account=accountService.getRedisAccount(request);
		PageInfo<Map<String, Object>> pageInfo=crmContactRecordService.getRecordListByCustomerId(pageNumber, pageSize,account.getOrgId(),customerId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getCrmInquiryDetailList   
	 * @Description: TODO 询价单产品详情
	 * @param request
	 * @param pageParam
	 * @param inquiryId
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getCrmInquiryDetailList",method=RequestMethod.POST)
	public RetDataBean getCrmInquiryDetailList(
			HttpServletRequest request,
			PageParam pageParam,
			String inquiryId
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("D.CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("DESC");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOpFlag(account.getOpFlag());
		String orderBy = pageParam.getSort()+ " " + pageParam.getSortOrder();
		pageParam.setOrderBy(orderBy);
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=crmInquiryDetailService.getCrmInquiryDetailList(pageParam,inquiryId );
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}

	
	/**
	 * 
	 * @Title getCrmLinkManAllList   
	 * @Description TODO 获取CRM联系人列表
	 * @param request
	 * @param sortId
	 * @param pageNumber
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @param sortOrder
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getCrmLinkManAllList",method=RequestMethod.POST)
	public RetDataBean getCrmLinkManAllList(
			HttpServletRequest request,
			PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("customerId");
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
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>> ();
		if(account.getOpFlag().equals("1"))
		{
			pageInfo=crmLinkManService.getCrmLinkManAllList(pageParam);	
		}else
		{
			CrmPriv crmPriv = new CrmPriv();
			crmPriv.setOrgId(account.getOrgId());
			crmPriv=crmPrivService.selectOneCrmPriv(crmPriv);
			if(StringUtils.isNotBlank(crmPriv.getManager()))
			{
				if((","+crmPriv.getManager()+",").indexOf(","+account.getAccountId()+",")>=0)
				{
					pageInfo=crmLinkManService.getCrmLinkManAllList(pageParam);	
				}
			}
		}
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * ]
	* @Title: getMyCrmLinkManAllList 
	* @Description: TODO 获取个人客户联系人列表
	* @param @param request
	* @param @param sortId
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getMyCrmLinkManAllList",method=RequestMethod.POST)
	public RetDataBean getMyCrmLinkManAllList(
			HttpServletRequest request,
			String sortId,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="customerId";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
			
			String orderBy = sort+ " " + sortOrder;
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求数据成功!", crmLinkManService.getMyCrmLinkManAllList(pageNumber, pageSize, orderBy, account.getOrgId(),account.getAccountId() ,"%"+search+"%"));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getCrmPriv   
	 * @Description TODO 获取CRM管理权限
	 * @param request
	 * @param crmContactRecord
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getCrmPriv",method=RequestMethod.POST)
	public RetDataBean getCrmPriv(HttpServletRequest request,CrmContactRecord crmContactRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			CrmPriv crmPriv = new CrmPriv();
			crmPriv.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmPrivService.selectOneCrmPriv(crmPriv));
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
/**
 * 	
 * @Title getAllCrmCustomerList   
 * @Description TODO获取权限内所有客户
 * @param request
 * @param pageNumber
 * @param pageSize
 * @param search
 * @param sort
 * @param sortOrder
 * @return      
 * RetDataBean
 */
	@RequestMapping(value="/getAllCrmCustomerList",method=RequestMethod.POST)
	public RetDataBean getAllCrmCustomerList(
			HttpServletRequest request,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder,
			String source,
			String model,
			String roles,
			String industry,
			String keepUser,
			String country,
			String province,
			String city,
			String level,
			String intention,
			String opponent,
			String tags
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="CREATE_TIME";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
			List<String> list = new ArrayList<String>();
			if(StringUtils.isNotBlank(tags))
			{
				String[] tagsArr=null;
				if(tags.indexOf(",")>0)
				{
					tagsArr = tags.split(",");
				}else
				{
					tagsArr = new String [] {tags};
				}
				list = Arrays.asList(tagsArr);
			}
		String orderBy = sort+ " " + sortOrder;
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			CrmPriv crmPriv = new CrmPriv();
			crmPriv.setOrgId(account.getOrgId());
			crmPriv = crmPrivService.selectOneCrmPriv(crmPriv);
			if((","+crmPriv.getManager()+",").indexOf(","+account.getAccountId()+",")>=0)
			{
				PageInfo<Map<String,Object>> pageInfo=crmCustomerService.getAllCrmCustomerList(pageNumber, pageSize, account.getOrgId(), "%"+source+"%", "%"+model+"%", "%"+roles+"%", "%"+industry+"%","%"+keepUser+"%", "%"+search+"%",
						"%"+country+"%", "%"+province+"%","%"+city+"%","%"+level+"%","%"+intention+"%","%"+opponent+"%",list,orderBy);
				return RetDataTools.Ok("请求数据成功!", pageInfo);
			}else
			{
				return RetDataTools.NotOk("对不起,您不是管理员或销售经理!");
			}
			
		}else
		{
		PageInfo<Map<String,Object>> pageInfo=crmCustomerService.getAllCrmCustomerList(pageNumber, pageSize, account.getOrgId(), "%"+source+"%", "%"+model+"%", "%"+roles+"%", "%"+industry+"%","%"+keepUser+"%", "%"+search+"%",
				"%"+country+"%", "%"+province+"%","%"+city+"%","%"+level+"%","%"+intention+"%","%"+opponent+"%",list,orderBy);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}
		}catch (Exception e) {
			
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getSelect2CustomerList   
	 * @Description TODO获取SELECT2客户列表
	 * @param request
	 * @param search
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getSelect2CustomerList",method=RequestMethod.POST)
	public RetDataBean getSelect2CustomerList(HttpServletRequest request,String search)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			CrmPriv crmPriv = new CrmPriv();
			crmPriv.setOrgId(account.getOrgId());
			crmPriv = crmPrivService.selectOneCrmPriv(crmPriv);
			if(account.getOpFlag().equals("1"))
			{
				return RetDataTools.Ok("请求成功!", crmCustomerService.getSelect2CustomerList(account.getOrgId(), "", "%"+search+"%"));
			}else
			{
				if(crmPriv.getManager().equals(account.getAccountId()))
				{
					return RetDataTools.Ok("请求成功!", crmCustomerService.getSelect2CustomerList(account.getOrgId(), "", "%"+search+"%"));
				}else
				{
					return RetDataTools.Ok("请求成功!", crmCustomerService.getSelect2CustomerList(account.getOrgId(), account.getAccountId(), "%"+search+"%"));
				}
			}
		}catch (Exception e) {
			
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title getCrmPriv   
	 * @Description TODO 获取业务列表
	 * @param request
	 * @return      
	 * RetDataBean
	 */
	@RequestMapping(value="/getCrmSaleList",method=RequestMethod.POST)
	public RetDataBean getCrmPriv(HttpServletRequest request,String search)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			CrmPriv crmPriv = new CrmPriv();
			crmPriv.setOrgId(account.getOrgId());
			crmPriv=crmPrivService.selectOneCrmPriv(crmPriv);
			String sales = crmPriv.getSale();
			if(StringUtils.isNotBlank(sales))
			{
				String [] accountArr;
				if(sales.indexOf(",")>=0)
				{
					accountArr = sales.split(",");
				}else
				{
					accountArr = new String [] {sales};
				}
				List<String> list = new ArrayList<String>();
				for(int i=0;i<accountArr.length;i++)
				{
					list.add(accountArr[i]);
				}
				return RetDataTools.Ok("请求成功!", accountService.getCrmSaleList(list, account.getOrgId(),"%"+search+"%"));
			}else
			{
				return RetDataTools.Ok("请求成功!", null);		
			}
		}catch (Exception e) {
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getCrmTagsbyIndustryList 
	* @Description: TODO按行业分类获取企业标签
	* @param @param request
	* @param @param industryId
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getCrmTagsbyIndustryList",method=RequestMethod.POST)
	public RetDataBean getCrmTagsbyIndustryList(HttpServletRequest request,String industryId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", crmTagsService.getAllTags(account.getOrgId(),industryId));
		}catch (Exception e) {
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: getCrmTagsList 
	* @Description: TODO 获取所有企业分类
	* @param @param request
	* @param @param industryId
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getCrmTagsList",method=RequestMethod.POST)
	public RetDataBean getCrmTagsList(HttpServletRequest request,String industryId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", crmTagsService.getAllTags(account.getOrgId(),industryId));
		}catch (Exception e) {
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: getCrmIndustryList 
	* @Description: TODO 获取行业分类列表
	* @param @param request
	* @param @param leaveId
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getCrmIndustryList",method=RequestMethod.POST)
	public RetDataBean getCrmIndustryList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(CrmIndustry.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmIndustryService.selectCrmIndustry(example));
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getCrmMyProductList 
	* @Description: TODO 获取公司可供产品列表
	* @param @param request
	* @param @param leaveId
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getCrmMyProductList",method=RequestMethod.POST)
	public RetDataBean getCrmMyProductList(HttpServletRequest request,String leaveId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", crmMyProductService.getAllMyProduct(account.getOrgId()));
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getMyProductNameStr 
	* @Description: TODO 按productId获取参应的产品名称
	* @param @param request
	* @param @param productIds
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getMyProductNameStr",method=RequestMethod.POST)
	public RetDataBean getMyProductNameStr(HttpServletRequest request,String productIds)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			String [] productIdArr;
			if(StringUtils.isNotBlank(productIds))
			{
				if(productIds.indexOf(",")>-1)
				{
					productIdArr = productIds.split(",");	
				}else
				{
					productIdArr = new String[] {productIds};
				}
				List<String> list = Arrays.asList(productIdArr);
				return RetDataTools.Ok("请求成功!", crmMyProductService.getMyProductNameStr(account.getOrgId(),list));
			}else
			{
				return RetDataTools.NotOk("请求参数有问题");
			}
			
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: getAllProduct 
	* @Description: TODO 获取所有产品列表
	* @param @param request
	* @param @param sortId
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getAllProduct",method=RequestMethod.POST)
	public RetDataBean getAllProduct(
			HttpServletRequest request,
			String sortId,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="sortNo";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
			String orderBy = sort+ " " + sortOrder;
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求数据成功!",crmMyProductService.getAllProductList(pageNumber, pageSize, orderBy, account.getOrgId(), "%"+search+"%"));	
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getMyProduct 
	* @Description: TODO 获取产品详情
	* @param @param request
	* @param @param crmMyProduct
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getMyProduct",method=RequestMethod.POST)
	public RetDataBean getMyProduct(HttpServletRequest request,CrmMyProduct crmMyProduct)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmMyProduct.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmMyProductService.selectOneCrmMyProdcut(crmMyProduct));
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getTags 
	* @Description: TODO 获取标签详情
	* @param @param request
	* @param @param crmTags
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getCrmTags",method=RequestMethod.POST)
	public RetDataBean getTags(HttpServletRequest request,CrmTags crmTags)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmTags.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmTagsService.selectOneCrmTags(crmTags));
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: getAllTags 
	* @Description: TODO 获取企业标签列表
	* @param @param request
	* @param @param sortId
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getAllTags",method=RequestMethod.POST)
	public RetDataBean getAllTags(
			HttpServletRequest request,
			String sortId,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="B.INDUSTRY_ID";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="DESC";
			}
			String orderBy = sort+ " " + sortOrder;
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求数据成功!",crmTagsService.getAllTagsList(pageNumber, pageSize, orderBy, account.getOrgId(), "%"+search+"%"));	
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: getCrmIndustry 
	* @Description: TODO 获取企业分类详情
	* @param @param request
	* @param @param crmIndustry
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getCrmIndustry",method=RequestMethod.POST)
	public RetDataBean getCrmIndustry(HttpServletRequest request,CrmIndustry crmIndustry)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmIndustry.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmIndustryService.selectOneCrmIndustry(crmIndustry));
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getAllIndustryList 
	* @Description: TODO 获取行业分类列表 
	* @param @param request
	* @param @param sortId
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getAllIndustryList",method=RequestMethod.POST)
	public RetDataBean getAllIndustryList(
			HttpServletRequest request,
			String sortId,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="SORT_NO";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="DESC";
			}
			String orderBy = sort+ " " + sortOrder;
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求数据成功!",crmIndustryService.getAllIndustryList(pageNumber, pageSize, orderBy, account.getOrgId(), "%"+search+"%"));	
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: getCrmContractInfoList 
	* @Description: TODO 获取银行信息列表
	* @param @param request
	* @param @param sortId
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getCrmContractInfoList",method=RequestMethod.POST)
	public RetDataBean getCrmContractInfoList(
			HttpServletRequest request,
			String sortId,
			Integer pageNumber,
			Integer pageSize,
			String search,
			String sort,
			String sortOrder
			)
	{
		try
		{
			if(StringUtils.isBlank(sort))
			{
				sort="C.SORT_NO";
			}else
			{
				sort=StrTools.upperCharToUnderLine(sort);
			}
			if(StringUtils.isBlank(sortOrder))
			{
				sortOrder="asc";
			}
		Account account=accountService.getRedisAccount(request);
		String orderBy = sort+ " " + sortOrder;
		PageInfo<Map<String, Object>> pageInfo=crmContractInfoService.getCrmContractInfoList(pageNumber, pageSize, orderBy, account.getOrgId(), "%"+search+"%");
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getCrmContractInfo 
	* @Description: TODO 获取对银行信息详情
	* @param @param request
	* @param @param crmContractInfo
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getCrmContractInfo",method=RequestMethod.POST)
	public RetDataBean getCrmContractInfo(HttpServletRequest request,CrmContractInfo crmContractInfo)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmContractInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmContractInfoService.selectOneCrmContractInfo(crmContractInfo));
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: selectCrmContractInfoList 
	* @Description: TODO 获取客户银行信息列表
	* @param @param request
	* @param @param crmContractInfo
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/selectCrmContractInfoList",method=RequestMethod.POST)
	public RetDataBean selectCrmContractInfoList(HttpServletRequest request,CrmContractInfo crmContractInfo)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmContractInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmContractInfoService.selectCrmContractInfoList(crmContractInfo));
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: getContractInfoById 
	* @Description: TODO 获取银行信息详情
	* @param @param request
	* @param @param contractInfoId
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getContractInfoById",method=RequestMethod.POST)
	public RetDataBean getContractInfoById(HttpServletRequest request,String contractInfoId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", crmContractInfoService.getContractInfoById(account.getOrgId(),contractInfoId));
		}catch (Exception e) {
			
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getAllSender 
	* @Description: TODO 获取所有销售人员列表
	* @param @param request
	* @param @param contractInfoId
	* @param @return 设定文件 
	* @return RetDataBean 返回类型 

	 */
	@RequestMapping(value="/getAllSender",method=RequestMethod.POST)
	public RetDataBean getAllSender(HttpServletRequest request,String search)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", crmPrivService.getAllSender(account.getOrgId(),"%"+search+"%"));
		}catch (Exception e) {
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: getCrmInquiryList 
	* @Description: TODO 获限权限内的询价单列表
	* @param @param request
	* @param @param sortId
	* @param @param pageNumber
	* @param @param pageSize
	* @param @param search
	* @param @param sort
	* @param @param sortOrder
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getCrmInquiryList",method=RequestMethod.POST)
	public RetDataBean getCrmInquiryList(
			HttpServletRequest request,
			PageParam pageParam,
			String beginTime,
			String endTime,
			String customerType,
			String status
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("I.CREATE_TIME");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
		Account account=accountService.getRedisAccount(request);
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrgId(account.getOrgId());
		CrmPriv crmPriv = new CrmPriv();
		crmPriv.setOrgId(account.getOrgId());
		crmPriv=crmPrivService.selectOneCrmPriv(crmPriv);
		if((","+crmPriv.getSender()+",").indexOf(","+account.getAccountId()+",")>=0)
		{
			pageParam.setOpFlag("1");
		}
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=crmInquiryService.getCrmInquiryList(pageParam,beginTime,endTime,customerType,status);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getCrmInquiry 
	* @Description: TODO 获取询价单基本信息
	* @param @param request
	* @param @param crmInquiry
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value="/getCrmInquiry",method=RequestMethod.POST)
	public RetDataBean getCrmInquiry(HttpServletRequest request,CrmInquiry crmInquiry)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			crmInquiry.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", crmInquiryService.selectOneCrmInquiry(crmInquiry));
		}catch (Exception e) {
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
