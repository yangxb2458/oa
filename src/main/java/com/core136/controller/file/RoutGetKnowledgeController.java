package com.core136.controller.file;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.file.Knowledge;
import com.core136.bean.file.KnowledgeLearn;
import com.core136.bean.file.KnowledgeSearch;
import com.core136.bean.file.KnowledgeSort;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.file.KnowledgeLearnService;
import com.core136.service.file.KnowledgeSearchService;
import com.core136.service.file.KnowledgeService;
import com.core136.service.file.KnowledgeSortService;
import com.github.pagehelper.PageInfo;

import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;

@RestController
@RequestMapping("/ret/knowledgeget")
public class RoutGetKnowledgeController {
	@Autowired
	private KnowledgeSortService knowledgeSortService;
	@Autowired
	private KnowledgeService knowledgeService;
	@Autowired
	private KnowledgeLearnService knowledgeLearnService;
	@Autowired
	private KnowledgeSearchService knowledgeSearchService;
	@Autowired
	private AccountService accountService;
	
	/**
	 * 
	 * @Title: getLearnCount   
	 * @Description: TODO 获取知识的学习次数
	 * @param request
	 * @param knowledgeLearn
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getLearnCount",method=RequestMethod.POST)
	public RetDataBean getLearnCount(HttpServletRequest request,KnowledgeLearn knowledgeLearn)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			knowledgeLearn.setOrgId(account.getOrgId());
			return  RetDataTools.Ok("请求数据成功!",knowledgeLearnService.getLearnCount(knowledgeLearn));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getAllKnowledgeList   
	 * @Description: TODO 获取所有知识列表
	 * @param request
	 * @param pageParam
	 * @param beginTime
	 * @param endTime
	 * @param sortId
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getAllKnowledgeList",method=RequestMethod.POST)
	public RetDataBean getAllKnowledgeList(
			HttpServletRequest request,
			PageParam pageParam,
			String beginTime,
			String endTime,
			String sortId
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("K.SORT_NO");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=knowledgeService.getAllKnowledgeList(pageParam, beginTime, endTime,sortId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getAllKnowledgeSortMap   
	 * @Description: TODO 获取所有知识分类
	 * @param request
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getAllKnowledgeSortMap",method=RequestMethod.POST)
	public RetDataBean getAllKnowledgeSortMap(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return  RetDataTools.Ok("请求数据成功!",knowledgeSortService.getAllKnowledgeSortMap(account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getHostKeyWords   
	 * @Description: TODO 获取热门关键字
	 * @param request
	 * @param keywords
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getHostKeyWords",method=RequestMethod.POST)
	public RetDataBean getHostKeyWords(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return  RetDataTools.Ok("请求数据成功!",knowledgeSearchService.getHostKeyWords(account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getMyCreateKnowledgeList   
	 * @Description: TODO 获取我可管理的知识列表  
	 * @param: request
	 * @param: pageParam
	 * @param: beginTime
	 * @param: endTime
	 * @param: sortId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMyCreateKnowledgeList",method=RequestMethod.POST)
	public RetDataBean getMyCreateKnowledgeList(
			HttpServletRequest request,
			PageParam pageParam,
			String beginTime,
			String endTime,
			String sortId
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("K.SORT_NO");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
			UserInfo userInfo = accountService.getRedisUserInfo(request);
		Account account=accountService.getRedisAccount(request);
		pageParam.setAccountId(userInfo.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrgId(userInfo.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=knowledgeService.getMyCreateKnowledgeList(pageParam, beginTime, endTime,sortId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getKnowledgeStudyList   
	 * @Description: TODO 获取学习的知识列表
	 * @param: request
	 * @param: pageParam
	 * @param: beginTime
	 * @param: endTime
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getKnowledgeStudyList",method=RequestMethod.POST)
	public RetDataBean getKnowledgeStudyList(
			HttpServletRequest request,
			PageParam pageParam,
			String beginTime,
			String endTime,
			String sortId
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("K.SORT_NO");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
			UserInfo userInfo = accountService.getRedisUserInfo(request);
		Account account=accountService.getRedisAccount(request);
		pageParam.setAccountId(userInfo.getAccountId());
		pageParam.setDeptId(userInfo.getDeptId());
		pageParam.setLevelId(userInfo.getLeadLeave());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrgId(userInfo.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=knowledgeService.getKnowledgeStudyList(pageParam, beginTime, endTime,sortId);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: searchIndex   
	 * @Description: TODO 全文检索
	 * @param: request
	 * @param: keywords
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/searchIndex",method=RequestMethod.POST)
	public RetDataBean searchIndex(HttpServletRequest request,String keywords)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			KnowledgeSearch knowledgeSearch = new KnowledgeSearch();
			knowledgeSearch.setKeyWord(keywords);
			knowledgeSearch.setOrgId(account.getOrgId());
			knowledgeSearchService.addKnowledgeSearch(account, knowledgeSearch);
			return  RetDataTools.Ok("请求数据成功!",knowledgeService.searchIndex(keywords));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getKnowledgeById   
	 * @Description: TODO 获取知识详情
	 * @param: request
	 * @param: knowledge
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getKnowledgeById",method=RequestMethod.POST)
	public RetDataBean getKnowledgeById(HttpServletRequest request,Knowledge knowledge)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			knowledge.setOrgId(account.getOrgId());
			return  RetDataTools.Ok("请求数据成功!",knowledgeService.selectOneKnowledge(knowledge));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getknowledgeSortTree   
	 * @Description: TODO 获取知识管理树  
	 * @param: request
	 * @param: sortId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	@RequestMapping(value="/getknowledgeSortTree",method=RequestMethod.POST)
	public List<Map<String,String>> getknowledgeSortTree(HttpServletRequest request,String sortId)
	{
		try
		{
			String sortLeave = "0";
			if(StringUtils.isNotBlank(sortId))
			{
				sortLeave = sortId;
			}
			Account account=accountService.getRedisAccount(request);
			return knowledgeSortService.getKnowledgeSortTree(account.getOrgId(), sortLeave);
		}catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 
	 * @Title: getKnowledgeSortById   
	 * @Description: TODO 获取知识管理数详情
	 * @param: request
	 * @param: knowledgeSort
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getKnowledgeSortById",method=RequestMethod.POST)
	public RetDataBean getKnowledgeSortById(HttpServletRequest request,KnowledgeSort knowledgeSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			knowledgeSort.setOrgId(account.getOrgId());
			return  RetDataTools.Ok("请求数据成功!",knowledgeSortService.selectOneKnowledgeSort(knowledgeSort));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
}
