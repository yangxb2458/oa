package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * 
 * @ClassName: AppGo
 * @Description:WEB启动，主程序入口
 * @author: 刘绍全
 * @date: 2018年10月18日 下午1:03:28
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved.
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@EnableTransactionManagement // 开启事务管理h
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication
public class AppGo extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(AppGo.class, args);
	}
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        return builder.sources(AppGo.class);
	    }
}
