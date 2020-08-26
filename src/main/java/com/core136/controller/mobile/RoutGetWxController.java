package com.core136.controller.mobile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.core136.common.weixin.WXutils;
import org.core136.common.weixin.entity.AccessToken;
import org.core136.common.weixin.entity.WUserIdInfo;
import org.core136.common.weixin.entity.WUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.core136.bean.account.Account;
import com.core136.bean.sys.WxConfig;
import com.core136.service.sys.AppConfigService;
import com.core136.service.sys.WxConfigService;
import com.core136.service.weixin.WeiXinLoginService;

@RestController
@RequestMapping("/weixin/weixinget")
public class RoutGetWxController {
	
	
	
}
