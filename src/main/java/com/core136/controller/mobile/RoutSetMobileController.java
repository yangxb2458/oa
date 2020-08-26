/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetContractController.java   
 * @Package com.core136.controller.im   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年6月20日 下午2:36:05   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.mobile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.im.Dynamic;
import com.core136.bean.im.Inquiry;
import com.core136.bean.im.UserFriends;
import com.core136.bean.file.Attach;
import com.core136.service.account.AccountService;
import com.core136.service.im.DynamicService;
import com.core136.service.im.InquiryService;
import com.core136.service.im.UserFriendsService;
import com.core136.unit.fileutils.UploadUtils;
import com.dingtalk.api.response.OapiCalendarListResponse.User;

import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

/**
 * @ClassName: RoutSetContractController
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 稠云信息
 * @date: 2019年6月20日 下午2:36:05
 * @author lsq
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved.
 *             注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/mobile/mobileset")
public class RoutSetMobileController {
	@Value("${app.attachpath}")
	private String attachpath;
	@Value("${app.notallow}")
	private String notallow;
	@Autowired
	private UploadUtils uploadUtils;
	@Autowired
	private DynamicService dynamicService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private InquiryService inquiryService;
	@Autowired
	private UserFriendsService userFriendsService;
	
	/**
	 * 
	 * @Title: addUserFriends   
	 * @Description: TODO 添加好友
	 * @param request
	 * @param userFriends
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value = "/addUserFriends", method = RequestMethod.POST)
	public RetDataBean addUserFriends(HttpServletRequest request, UserFriends userFriends) {
		try {
			return RetDataTools.Ok("好友添加成功!", userFriendsService.addUserFriends(userFriends));
		} catch (Exception e) {
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
/**
 * 
 * @Title: sendDynamic   
 * @Description: TODO 发布个人动态
 * @param: request
 * @param: dynamic
 * @param: accountId
 * @param: passWord
 * @param: module
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
	@RequestMapping(value = "/sendDynamic", method = RequestMethod.POST)
	public RetDataBean sendDynamic(HttpServletRequest request, Dynamic dynamic,String accountId,String passWord,String module) {
		dynamic.setDynamicId(SysTools.getGUID());
		dynamic.setCreateTime(SysTools.getTime("yyyy-MM-dd HH-mm:ss"));
		String targetpath = SysTools.greateAttachDir(attachpath, "imdynamic");
		try {
			Account account = accountService.getLoginAccount(accountId, passWord);
			List<Attach> list = uploadUtils.ImUpload(request,account, notallow, targetpath);
			StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                stringBuffer.append(list.get(i).getAttachId()+ ":");
            }
            String imgPaths = stringBuffer.substring(0, stringBuffer.length() - 1).toString();
            dynamic.setAttach(imgPaths);
			return RetDataTools.Ok("文件上传成功!", dynamicService.insertDynamic(dynamic));
		} catch (Exception e) {
			e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
		}
	}
}
