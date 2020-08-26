/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetContractController.java   
 * @Package com.core136.controller.im   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年6月20日 下午2:36:34   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.mobile;

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
import com.core136.bean.account.UserInfo;
import com.core136.bean.im.Dynamic;
import com.core136.bean.im.Inquiry;
import com.core136.bean.im.UserFriends;
import com.core136.bean.oa.News;
import com.core136.service.account.AccountService;
import com.core136.service.bpm.BpmFlowService;
import com.core136.service.bpm.BpmListService;
import com.core136.service.bpm.BpmRunProcessService;
import com.core136.service.bpm.BpmSendToService;
import com.core136.service.document.DocumentListService;
import com.core136.service.document.DocumentRunProcessService;
import com.core136.service.document.DocumentSendToService;
import com.core136.service.email.EmailService;
import com.core136.service.im.DynamicService;
import com.core136.service.im.InquiryService;
import com.core136.service.im.UserFriendsService;
import com.core136.service.meeting.MeetingService;
import com.core136.service.mobile.MobileLoginService;
import com.core136.service.mobile.MobileOrgService;
import com.core136.service.notice.NoticeService;
import com.core136.service.oa.NewsService;

import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  RoutGetContractController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年6月20日 下午2:36:34   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/mobile/mobileget")
public class RoutGetMobileController {
	@Autowired
	private DynamicService dynamicService;
	@Autowired
	private UserFriendsService userFriendsService;
	@Autowired
	private InquiryService inquiryService;
	@Autowired
	private NewsService newService;
	@Autowired
	private BpmRunProcessService bpmRunProcessService;
	@Autowired
	private BpmListService bpmListService;
	@Autowired
	private BpmFlowService bpmFlowService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private BpmSendToService bpmSendToService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private MobileLoginService mobileLoginService;
	@Autowired
	private DocumentRunProcessService documentRunProcessService;
	@Autowired
	private DocumentListService documentListService;
	@Autowired
	private DocumentSendToService documentSendToService;
	@Autowired
	private MeetingService meetingService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private MobileOrgService mobileOrgService;
	
	/**
	 * 
	 * @Title: getDeptAndUserInfoForMobile   
	 * @Description: TODO APP获取组织机构
	 * @param request
	 * @param orgId
	 * @param deptId
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getDeptAndUserInfoForMobile",method=RequestMethod.POST)
	public RetDataBean getDeptAndUserInfoForMobile(HttpServletRequest request,String orgId,String deptId)
	{
		try
		{
		return RetDataTools.Ok("请求数据成功!", mobileOrgService.getDeptAndUserInfoForMobile(orgId,deptId));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}	
	
	
	
	
	/**
	 * 
	 * @Title: doMobileLogin   
	 * @Description: TODO 移动端登陆
	 * @param request
	 * @param code
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public RetDataBean doMobileLogin(HttpServletRequest request,String code)
	{
		try {
			Account account = accountService.getRedisAccount(request);
			if(account!=null)
			{
				if(StringUtils.isNotBlank(account.getAccountId()))
				{
					return RetDataTools.Ok("请求成功!");
				}else
				{
					code = SysTools.decode(code);
					if(StringUtils.isNotBlank(code))
					{
						String[] codeArr = code.split("&");
						String accountId = codeArr[0];
						String passWord = codeArr[2];
						if(mobileLoginService.mobileLogin(request, accountId, passWord))
						{
							return RetDataTools.Ok("请求成功!");
						}else
						{
							return RetDataTools.NotOk("登陆失败!");
						}
					}else
					{
						return RetDataTools.NotOk("登陆失败!");
					}
				}
			}else
			{
				code = SysTools.decode(code);
				if(StringUtils.isNotBlank(code))
				{
					String[] codeArr = code.split("&");
					String accountId = codeArr[0];
					String passWord = codeArr[2];
					if(mobileLoginService.mobileLogin(request, accountId, passWord))
					{
						return RetDataTools.Ok("请求成功!");
					}else
					{
						return RetDataTools.NotOk("登陆失败!");
					}
					
				}else
				{
					return RetDataTools.NotOk("登陆失败!");
				}
			}
		}catch (Exception e)
		{
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMyEmailAllForMobile   
	 * @Description: TODO 移动端内部邮件
	 * @param request
	 * @param page
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMyEmailAllForMobile",method=RequestMethod.POST)
	public RetDataBean getMyEmailAllForMobile(HttpServletRequest request,Integer page)
	{
		try
		{
		page = (page-1)*10;
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		List<Map<String, String>> pageInfo=emailService.getMyEmailAllForMobile(userInfo.getOrgId(),userInfo.getAccountId(), page);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}	
	/**
	 * 
	 * @Title: getSendToMeBpmListForMobile   
	 * @Description: TODO 移动端抄送列表
	 * @param request
	 * @param page
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getSendToMeBpmListForMobile",method=RequestMethod.POST)
	public RetDataBean getSendToMeBpmListForMobile(HttpServletRequest request,Integer page)
	{
		try
		{
		page = (page-1)*10;
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		List<Map<String, String>> pageInfo=bpmSendToService.getSendToMeBpmListForMobile(userInfo.getOrgId(),userInfo.getAccountId(), page);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/getSendToMeDocumentListForMobile",method=RequestMethod.POST)
	public RetDataBean getSendToMeDocumentListForMobile(HttpServletRequest request,Integer page)
	{
		try
		{
		page = (page-1)*10;
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		List<Map<String, String>> pageInfo=documentSendToService.getSendToMeDocumentListForMobile(userInfo.getOrgId(),userInfo.getAccountId(), page);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: searchBpmListForMobile   
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param page
	 * @param flowId
	 * @param id
	 * @param createUser
	 * @param status
	 * @param managePriv
	 * @param title
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/searchBpmListForMobile",method=RequestMethod.POST)
	public RetDataBean searchBpmListForMobile(HttpServletRequest request,Integer page,String flowId,Integer id,String createUser,String status,String managePriv,String title)
	{
		try
		{
		page = (page-1)*10;
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		List<Map<String, Object>> pageInfo=bpmListService.searchBpmListForMobile(userInfo.getOrgId(), userInfo.getAccountId(), flowId, id, createUser, status, managePriv, title, page);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/searchDocumentListForMobile",method=RequestMethod.POST)
	public RetDataBean searchDocumentListForMobile(HttpServletRequest request,Integer page,String flowId,Integer id,String createUser,String status,String managePriv,String title)
	{
		try
		{
		page = (page-1)*10;
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		List<Map<String, Object>> pageInfo=documentListService.searchDocumentListForMobile(userInfo.getOrgId(), userInfo.getAccountId(), flowId, id, createUser, status, managePriv, title, page);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMobileMyNewsList   
	 * @Description: TODO 获取移动端企业新闻的列表
	 * @param: request
	 * @param: page
	 * @param: dingUserId
	 * @param: dingDeviceId
	 * @param: orgId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMobileMyNewsList",method=RequestMethod.POST)
	public RetDataBean getMobileMyNewsList(HttpServletRequest request,Integer page)
	{
		try
		{
		page = (page-1)*10;
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		List<Map<String, String>> pageInfo=newsService.getMobileMyNewsList(userInfo.getOrgId(),userInfo.getAccountId(),userInfo.getDeptId(), userInfo.getLeadLeave(), page);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
/**
* 	
* @Title: getMobileMyNoticeList   
* @Description: TODO 移动端下滑加载更多
* @param: request
* @param: page
* @param: @return      
* @return: RetDataBean      
* @throws
*/
@RequestMapping(value="/getMobileMyNoticeList",method=RequestMethod.POST)
public RetDataBean getMobileMyNoticeList(HttpServletRequest request,Integer page)
{
	try
	{
	page = (page-1)*10;
	UserInfo userInfo = accountService.getRedisUserInfo(request);
	List<Map<String, String>> pageInfo=noticeService.getMobileMyNoticeList(userInfo.getOrgId(),userInfo.getAccountId(),userInfo.getDeptId(), userInfo.getLeadLeave(), page);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: getMobileMyBpmFlowList   
 * @Description: TODO 钉钉获取新建BPM 列表  
 * @param: request
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
@RequestMapping(value = "/getMobileMyBpmFlowList", method = RequestMethod.POST)
public RetDataBean getMobileMyBpmFlowList(HttpServletRequest request,Integer page) {
	try {
		page = (page-1)*10;
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		List<Map<String, String>> pageInfo=bpmFlowService.getMobileMyBpmFlowList(userInfo.getOrgId(),userInfo.getAccountId(),userInfo.getDeptId(), userInfo.getLeadLeave(), page);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
	} catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
	/**
	 * 
	 * @Title: getGoMobileBpmProcessList   
	 * @Description: TODO 获取移动端待办BPM列表
	 * @param: request
	 * @param: page
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value = "/getGoMobileBpmProcessList", method = RequestMethod.POST)
	public RetDataBean getMobileProcessList(HttpServletRequest request,Integer page) {
		try {
			page = (page-1)*10;
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			List<Map<String, String>> pageInfo=bpmRunProcessService.getGoMobileProcessList(userInfo.getOrgId(),userInfo.getAccountId(), page);
			return RetDataTools.Ok("请求数据成功!", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getMobileMyMeetingList   
	 * @Description: TODO  获取移动端待办会议
	 * @param request
	 * @param page
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value = "/getMobileMyMeetingList", method = RequestMethod.POST)
	public RetDataBean getMobileMyMeetingList(HttpServletRequest request,Integer page) {
		try {
			page = (page-1)*10;
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			List<Map<String, String>> pageInfo=meetingService.getMobileMyMeetingList(userInfo.getOrgId(),userInfo.getAccountId(),userInfo.getDeptId(),userInfo.getLeadLeave(), page);
			return RetDataTools.Ok("请求数据成功!", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getGoMobileDocumentProcessList   
	 * @Description: TODO  获取公文待办列表
	 * @param request
	 * @param page
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value = "/getGoMobileDocumentProcessList", method = RequestMethod.POST)
	public RetDataBean getGoMobileDocumentProcessList(HttpServletRequest request,Integer page) {
		try {
			page = (page-1)*10;
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			List<Map<String, String>> pageInfo=documentRunProcessService.getGoMobileProcessList(userInfo.getOrgId(),userInfo.getAccountId(), page);
			return RetDataTools.Ok("请求数据成功!", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getNewsByTabsType 
	* @Description: TODO 获取新闻列表
	* @param @param request
	* @param @param tabsType
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value = "/getNewsByTabsType", method = RequestMethod.POST)
	public RetDataBean getNewsByTabsType(HttpServletRequest request, String tabsType) {
		try {
			Example example = new Example(News.class);
			example.createCriteria().andEqualTo("newsType", tabsType);
			return RetDataTools.Ok("请求成功!", newService.selectNewsList(example));
		} catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @Title: getFriendsDynamic 
	* @Description: TODO 获取好友动态
	* @param @param request
	* @param @param dynamic
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value = "/getDynamicByAccountId", method = RequestMethod.POST)
	public RetDataBean getFriendsDynamic(HttpServletRequest request, Dynamic dynamic) {
		try {
			return RetDataTools.Ok("请求成功!", dynamicService.getDynamicList(dynamic));
		} catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getFriendsDynamic 
	* @Description: TODO 取当前用户下的所有好友动态
	* @param @param request
	* @param @param orgId
	* @param @param accountId
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value = "/getAllDynamicList", method = RequestMethod.POST)
	public RetDataBean getFriendsDynamic(HttpServletRequest request, String orgId,String accountId) {
		try {
			if(StringUtils.isNotBlank(orgId)&&StringUtils.isNotBlank(accountId))
			{
				UserFriends userFriends = new UserFriends();
				userFriends.setOrgId(orgId);
				userFriends.setAccountId(accountId);
				userFriends = userFriendsService.selectOneUserFriends(userFriends);
				String friendsStr = userFriends.getFriendsAccountId();
				List<String> list = new ArrayList<String>();
				if(StringUtils.isNotBlank(friendsStr))
				{
					String[] tmpArr ;
					if(friendsStr.indexOf(",")>0)
					{
						tmpArr = friendsStr.split(",");
					}else
					{
						tmpArr = new String[] {friendsStr};
					}
					list = Arrays.asList(tmpArr);
				}
				return RetDataTools.Ok("请求成功!", dynamicService.getAllDynamicList(orgId,list));
			}else
			{
				return RetDataTools.NotOk("请求参数有问题!");
			}
		} catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	* @Title: getInquiryById 
	* @Description: TODO 获取询价单详情
	* @param @param request
	* @param @param inquiry
	* @param @return 设定文件 
	* @return RetDataBean 返回类型
	 */
	@RequestMapping(value = "/getInquiryById", method = RequestMethod.POST)
	public RetDataBean getInquiryById(HttpServletRequest request, Inquiry inquiry) {
		try {
			return RetDataTools.Ok("请求成功!", inquiryService.getInquiryById(inquiry.getInquiryId()));
		} catch (Exception e) {
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
