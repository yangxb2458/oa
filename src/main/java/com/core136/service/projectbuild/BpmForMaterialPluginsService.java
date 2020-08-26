/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  BpmForMaterialPluginsService.java   
 * @Package com.core136.service.projectbuild   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2020年1月6日 下午4:24:26   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.projectbuild;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.bpm.BpmList;
import com.core136.bean.bpm.BpmRunLog;
import com.core136.bean.bpm.BpmRunProcess;
import com.core136.service.bpm.BpmRunLogService;
import com.core136.unit.bpm.BpmTaskPlugin;

/**
 * @author lsq
 *工程材料BPM插件
 */
@Service
public class BpmForMaterialPluginsService implements BpmTaskPlugin{
@Autowired
private BpmRunLogService bpmRunLogService;
	@Override
	public boolean beforeToDo(Account account, BpmList bpmList, BpmRunProcess bpmRunProcess, Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		BpmRunLog bpmRunLog = new BpmRunLog();
		bpmRunLog.setRemark("aaaaaaaaaaaaaaaaaaaaa");
		bpmRunLogService.insertBpmRunLog(bpmRunLog);
		return false;
	}

	@Override
	public boolean afterToDo(Account account, BpmList bpmList, BpmRunProcess bpmRunProcess, Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

}
