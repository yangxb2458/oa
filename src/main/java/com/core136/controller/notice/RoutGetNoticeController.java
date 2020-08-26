/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetNoticeController.java   
 * @Package com.core136.controller.notice   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年7月20日 上午10:17:20   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.notice;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.notice.Notice;
import com.core136.bean.notice.NoticeTemplate;
import com.core136.bean.sys.PageParam;
import com.core136.service.account.AccountService;
import com.core136.service.notice.NoticeConfigService;
import com.core136.service.notice.NoticeService;
import com.core136.service.notice.NoticeTemplateService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import org.core136.common.utils.SysTools;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: RoutGetNoticeController
 * @Description:TODO 通知公告接口Controller
 * @author: 稠云信息
 * @date: 2019年7月20日 上午10:17:20
 * @author lsq
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved.
 *             注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/ret/noticeget")
public class RoutGetNoticeController {
	@Autowired
	private NoticeTemplateService noticeTemplateService;
	@Autowired
	private NoticeConfigService noticeConfigService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: getMyNoticeListForDesk   
	 * @Description: TODO 获取桌面的通知消息
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMyNoticeListForDesk",method=RequestMethod.POST)
	public RetDataBean getMyNoticeListForDesk(HttpServletRequest request)
	{
		try
		{
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			return RetDataTools.Ok("请求成功!", noticeService.getMyNoticeListForDesk(userInfo.getOrgId(),SysTools.getTime("yyyy-MM-dd"), userInfo.getAccountId(), userInfo.getDeptId(), userInfo.getLeadLeave()));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMobileNoticeInfo   
	 * @Description: TODO 获取移动端通知公告详情
	 * @param: request
	 * @param: notice
	 * @param: dingUserId
	 * @param: dingDeviceId
	 * @param: orgId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value = "/getMobileNoticeInfo", method = RequestMethod.POST)
	public RetDataBean getMobileNoticeInfo(HttpServletRequest request, Notice notice) {
		try {
			
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(notice.getNoticeId()))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			notice.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", noticeService.getNoticeInfo(account, notice));
		} catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
/**
* @Title: getMyNoticeList 
* @Description: TODO 获取个人的通知公告
* @param @param request
* @param @param sortId
* @param @param pageParam
* @param @param noticeType
* @param @param beginTime
* @param @param endTime
* @param @return 设定文件 
* @return RetDataBean 返回类型
 */
	@RequestMapping(value="/getMyNoticeList",method=RequestMethod.POST)
	public RetDataBean getNewsManageList(
			HttpServletRequest request,
			PageParam pageParam,
			String noticeType,
			String beginTime,
			String endTime,
			String readStatus
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("SEND_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("asc");
			}
			
			UserInfo userInfo = accountService.getRedisUserInfo(request);
		pageParam.setAccountId(userInfo.getAccountId());
		pageParam.setDeptId(userInfo.getDeptId());
		pageParam.setLevelId(userInfo.getLeadLeave());
		pageParam.setOrgId(userInfo.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=noticeService.getMyNoticeList(pageParam, noticeType, beginTime, endTime,readStatus);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getNoticeInfo 
	* @Description: TODO 获取通知公告的具体内容
	* @param @param request
	* @param @param notice
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value = "/getNoticeInfo", method = RequestMethod.POST)
	public RetDataBean getNoticeInfo(HttpServletRequest request, Notice notice) {
		try {
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(notice.getNoticeId()))
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
			notice.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", noticeService.getNoticeInfo(account, notice));
		} catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getRedHeadListByType
	 * @Description: TODO 按分类获取红头列表
	 * @param @param request
	 * @param @param noticeType
	 * @param @return 设定文件
	 * @return RetDataBean 返回类型
	 */
	@RequestMapping(value = "/getRedHeadListByType", method = RequestMethod.POST)
	public RetDataBean getRedHeadListByType(HttpServletRequest request, String noticeType) {
		try {
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", noticeTemplateService.getRedHeadListByType(account.getOrgId(), noticeType));
		} catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: getNoticeTemplateList
	 * @Description: TODO 获取通知公告模版
	 * @param @param request
	 * @param @param pageParam
	 * @param @return 设定文件
	 * @return RetDataBean 返回类型
	 */
	@RequestMapping(value = "/getNoticeTemplateList", method = RequestMethod.POST)
	public RetDataBean getNoticeTemplateList(HttpServletRequest request,PageParam pageParam) {
		try {
			if (StringUtils.isBlank(pageParam.getSort())) {
				pageParam.setSort("SORT_NO");
			} else {
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if (StringUtils.isBlank(pageParam.getSortOrder())) {
				pageParam.setSortOrder("asc");
			}
			Account account=accountService.getRedisAccount(request);
			String orderBy = pageParam.getSort() + " " + pageParam.getSortOrder();
			pageParam.setOrderBy(orderBy);
			pageParam.setAccountId(account.getAccountId());
			pageParam.setOrgId(account.getOrgId());
			PageInfo<Map<String, String>> pageInfo = noticeTemplateService.getNoticeTemplateList(pageParam);
			return RetDataTools.Ok("请求数据成功!", pageInfo);
		} catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: getNoticeManageList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
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
	@RequestMapping(value="/getNoticeManageList",method=RequestMethod.POST)
	public RetDataBean getNoticeManageList(
			HttpServletRequest request,
			PageParam pageParam,
			String noticeType,
			String beginTime,
			String endTime
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
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, Object>> pageInfo=noticeService.getNoticeManageList(pageParam,noticeType,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getNoticeApproverList 
	* @Description: TODO 获取审批列表
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
	@RequestMapping(value="/getNoticeApproverList",method=RequestMethod.POST)
	public RetDataBean getNoticeApproverList(
			HttpServletRequest request,
			PageParam pageParam
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
				pageParam.setSortOrder("ASC");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, Object>> pageInfo=noticeService.getNoticeApproverList(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getNoticeTemplate
	 * @Description: TODO 获取红头模版信息
	 * @param @param request
	 * @param @param noticeTemplate
	 * @param @return 设定文件
	 * @return RetDataBean 返回类型
	 */
	@RequestMapping(value = "/getNoticeTemplate", method = RequestMethod.POST)
	public RetDataBean getNoticeTemplate(HttpServletRequest request, NoticeTemplate noticeTemplate) {
		try {
			Account account=accountService.getRedisAccount(request);
			noticeTemplate.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", noticeTemplateService.selectOneNoticeTemplate(noticeTemplate));
		} catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: getApproverUserList
	 * @Description: TODO 审批人员列表
	 * @param @param request
	 * @param @param sortId
	 * @param @param pageNumber
	 * @param @param pageSize
	 * @param @param sort
	 * @param @param sortOrder
	 * @param @return 设定文件
	 * @return RetDataBean 返回类型
	 */
	@RequestMapping(value = "/getApproverUserList", method = RequestMethod.POST)
	public RetDataBean getApproverUserList(HttpServletRequest request, String sortId, Integer pageNumber,
			Integer pageSize, String sort, String sortOrder) {
		try {
			if (StringUtils.isBlank(sort)) {
				sort = "SORT_NO";
			} else {
				sort = StrTools.upperCharToUnderLine(sort);
			}
			if (StringUtils.isBlank(sortOrder)) {
				sortOrder = "asc";
			}
			Account account=accountService.getRedisAccount(request);
			String orderBy = sort + " " + sortOrder;
			PageInfo<Map<String, String>> pageInfo = noticeConfigService.getApproverUserList(pageNumber, pageSize,
					orderBy, account.getOrgId());
			return RetDataTools.Ok("请求数据成功!", pageInfo);
		} catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: getApproverUserList
	 * @Description: TODO 免审人员列表
	 * @param @param request
	 * @param @param sortId
	 * @param @param pageNumber
	 * @param @param pageSize
	 * @param @param sort
	 * @param @param sortOrder
	 * @param @return 设定文件
	 * @return RetDataBean 返回类型
	 */
	@RequestMapping(value = "/getNotApproverUserList", method = RequestMethod.POST)
	public RetDataBean getNotApproverUserList(HttpServletRequest request, String sortId, Integer pageNumber,
			Integer pageSize, String sort, String sortOrder) {
		try {
			if (StringUtils.isBlank(sort)) {
				sort = "SORT_NO";
			} else {
				sort = StrTools.upperCharToUnderLine(sort);
			}
			if (StringUtils.isBlank(sortOrder)) {
				sortOrder = "asc";
			}
			Account account=accountService.getRedisAccount(request);
			String orderBy = sort + " " + sortOrder;
			PageInfo<Map<String, String>> pageInfo = noticeConfigService.getNotApproverUserList(pageNumber, pageSize,
					orderBy, account.getOrgId());
			return RetDataTools.Ok("请求数据成功!", pageInfo);
		} catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
