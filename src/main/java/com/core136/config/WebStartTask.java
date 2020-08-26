package com.core136.config;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.core136.common.SysRunConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.core136.bean.account.Unit;
import com.core136.bean.sys.SysOrgConfig;
import com.core136.bean.sys.SysTimingTask;
import com.core136.service.account.AccountService;
import com.core136.service.account.UnitService;
import com.core136.service.sys.SysOrgConfigService;
import com.core136.service.sys.SysTimingTaskService;
import com.core136.unit.dbunit.DBInfoTools;

import net.core136.service.ImService;

import org.core136.common.utils.SysTools;
/**
 * 
 * @ClassName:  WebStartTask   
 * @Description:TODO 系统启动后初始化工作
 * @author: 稠云技术 
 * @date:   2019年10月30日 下午10:29:48   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
public class WebStartTask implements CommandLineRunner{
	@Value("${app.orgid}")
	private String orgid;
	@Value("${app.attachpath}")
	private String attachpath;
	@Value("${app.noCheckUrl}")
	private String noCheckUrl;
	@Autowired
	private UnitService unitService;
	@Autowired
	private SysOrgConfigService sysOrgConfigService;
	@Autowired
	private SysTimingTaskService sysTimingTaskService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ScheduledTask scheduledTask;
	@Autowired
	private DBInfoTools dBInfoTools;
	@Autowired  
    private Environment env;
	
	@Override
    public void run(String... args) throws Exception {
		try
		{
			loadingOtherConfig();
			loadingSysOrgConfig();
			System.out.println("---------------------加载机构列表完成---------------------");
	        checkIsRegist();
	        System.out.println("---------------------检查软件授权完成---------------------");
	        loadingSysTimingTask();
	        System.out.println("---------------------加载系统定时任务---------------------");
	        startSysTimingTask();
	        System.out.println("---------------------启动系统定时任务---------------------");
	        System.out.println("---------------------正在启动IM服务---------------------");
	        new ImService().startup();
	        System.out.println("---------------------启动IM服务完成---------------------");
	        System.out.println("智能办公系统已启动,相关初始化工作已完成,欢迎您对本系统的支持！");
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
	 * 
	 * @Title: checkIsRegist   
	 * @Description: TODO 校验注册信息
	 * @param:  注册识别主机构ID
	 * @return: void      
	 * @throws
	 */
	public void checkIsRegist() {
		Unit unit = new Unit ();
		unit.setOrgId(orgid);
		unit = unitService.selectOne(unit);
		List<SysOrgConfig> sysOrgConfigsList = SysRunConfig.getSysOrgConfigList();
		int orgCount = sysOrgConfigsList.size();
		int accountCount = accountService.getAllUserCount();
		boolean isRegist = SysTools.isRegist(attachpath, unit,orgCount,accountCount);
		SysRunConfig.setIsRegist(isRegist);
	}
	
	public void loadingSysOrgConfig()
	{
		List<SysOrgConfig> sysOrgConfigsList = sysOrgConfigService.getAllOrgConfig();
		SysRunConfig.setSysOrgConfigList(sysOrgConfigsList);
	}
	/**
	 * 
	 * @Title: loadingSysTimingTask   
	 * @Description: TODO 加载定时任务的规则
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	public void loadingSysTimingTask()
	{
		List<SysOrgConfig> sysOrgConfigsList = SysRunConfig.getSysOrgConfigList();
		for(int i=0;i<sysOrgConfigsList.size();i++)
		{
			String orgId = sysOrgConfigsList.get(i).getOrgId();
			if(StringUtils.isNotBlank(orgId))
			{
				List<SysTimingTask> sysTimingTaskList= sysTimingTaskService.getOrgSysTimingTaskList(orgId);
				if(sysOrgConfigsList!=null)
				{
					SysRunConfig.setSysTimingTaksMap(orgId, sysTimingTaskList);
				}
			}
		}
	}
	/**
	 * 
	 * @Title: startSysTimingTask   
	 * @Description: TODO 加载定时任务
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	public void startSysTimingTask()
	{
		List<SysOrgConfig> sysOrgConfigsList = SysRunConfig.getSysOrgConfigList();
		for(int i=0;i<sysOrgConfigsList.size();i++)
		{
			String orgId = sysOrgConfigsList.get(i).getOrgId();
			if(StringUtils.isNotBlank(orgId))
			{
				List<SysTimingTask> sysTimingTasksList = SysRunConfig.getSysTimingTaksMap(orgId);
				for(int k=0;k<sysTimingTasksList.size();k++)
				{
					if(sysTimingTasksList.get(k).getStatus().equals("1"))
					{
						String taskId = sysTimingTasksList.get(k).getTaskId();
						String rule = sysTimingTasksList.get(k).getRule();
						String method = sysTimingTasksList.get(k).getMethod();
						String taskOrgId = sysTimingTasksList.get(k).getOrgId();
						try {
							Runnable runnable = (Runnable)Class.forName(method).newInstance();
							Method setOrgId = runnable.getClass().getMethod("setOrgId", String.class);
							setOrgId.invoke(runnable,taskOrgId);
							scheduledTask.startCron(taskId, runnable, new CronTrigger(rule));
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: loadingOtherConfig   
	 * @Description: TODO 其它相关配置信息
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	public void loadingOtherConfig()
	{
		SysRunConfig.setDbType(dBInfoTools.getDbType());
		SysRunConfig.setNoCheckUrlList(noCheckUrl);
		SysRunConfig.setDbSetUpPath(dBInfoTools.getDbPath());
		SysRunConfig.setDbVserion(dBInfoTools.getDbVersion());
		SysRunConfig.setDbName(dBInfoTools.getDbName());
		SysRunConfig.setEnv(env);
	}
}
