package com.core136.service.systimingtask;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.core136.common.SysRunConfig;
import org.core136.common.utils.DataBaseTools;
import org.springframework.core.env.Environment;

public class SysDbBackTimingRunnable implements Runnable{
	private String orgId;
	 public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Override
	public void run() {
		Environment env = SysRunConfig.getEnv();
		String mysqlPath = SysRunConfig.getDbSetUpPath();
		if (env.getProperty("spring.datasource.driver-class-name").equals("com.mysql.cj.jdbc.Driver")) {
			//MySql备份
			String root = env.getProperty("spring.datasource.username");
			String pwd = env.getProperty("spring.datasource.password");
			String backPath = env.getProperty("dbback.backpath");
			String dbName = SysRunConfig.getDbName();
			String backName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())+".sql";
			try
			{
				DataBaseTools.backUpForMySql(mysqlPath,root,pwd,dbName,backPath,backName);
			}catch (Exception e) {
				e.printStackTrace();
			}
		} else if (env.getProperty("spring.datasource.driver-class-name").equals("com.microsoft.sqlserver.jdbc.SQLServerDriver")) {
		
		} else if (env.getProperty("spring.datasource.driver-class-name").equals("oracle.jdbc.driver.OracleDriver")) {
		
		}
	}

}
