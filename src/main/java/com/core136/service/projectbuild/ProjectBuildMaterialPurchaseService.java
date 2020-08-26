package com.core136.service.projectbuild;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core136.bean.bpm.BpmFlow;
import com.core136.bean.bpm.BpmForm;
import com.core136.bean.bpm.BpmList;
import com.core136.bean.bpm.BpmProcess;
import com.core136.bean.bpm.BpmRunProcess;
import com.core136.bean.projectbuild.ProjectBuildBpmConfig;
import com.core136.bean.projectbuild.ProjectBuildMaterialMx;
import com.core136.bean.projectbuild.ProjectBuildMaterialPurchase;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildMaterialPurchaseMapper;
import com.core136.service.bpm.BpmFlowService;
import com.core136.service.bpm.BpmFormService;
import com.core136.service.bpm.BpmListService;
import com.core136.service.bpm.BpmProcessService;
import com.core136.service.bpm.BpmRunProcessService;
import com.core136.service.bpm.BpmTableService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ProjectBuildMaterialPurchaseService {
@Autowired
private ProjectBuildMaterialPurchaseMapper projectBuildMaterialPurchaseMapper;
@Autowired
private ProjectBuildBpmConfigService projectBuildBpmConfigService;
@Autowired
private BpmListService bpmListService;
@Autowired
private BpmProcessService bpmProcessService;
@Autowired
private BpmRunProcessService bpmRunProcessService;
@Autowired
private BpmFlowService bpmFlowService;
@Autowired
private BpmFormService bpmFormService;
@Autowired
private BpmTableService bpmTableService;
@Autowired
private ProjectBuildMaterialMxService projectBuildMaterialMxService;


public int insertProjectBuildMaterialPurchase(ProjectBuildMaterialPurchase projectBuildMaterialPurchase)
{
	return projectBuildMaterialPurchaseMapper.insert(projectBuildMaterialPurchase);
}

public int deleteProjectBuildMaterialPurchase(ProjectBuildMaterialPurchase projectBuildMaterialPurchase)
{
	return projectBuildMaterialPurchaseMapper.delete(projectBuildMaterialPurchase);
}

public int updateProjectBuildMaterialPurchase(Example example,ProjectBuildMaterialPurchase projectBuildMaterialPurchase)
{
	return projectBuildMaterialPurchaseMapper.updateByExampleSelective(projectBuildMaterialPurchase, example);
}

public ProjectBuildMaterialPurchase selectOneProjectBuildMaterialPurchase(ProjectBuildMaterialPurchase projectBuildMaterialPurchase)
{
	return projectBuildMaterialPurchaseMapper.selectOne(projectBuildMaterialPurchase);
}
/**
 * 
 * @Title: getMaterialPurchase   
 * @Description: TODO 获取材料审批的列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getMaterialPurchaseList(String orgId,String search)
{
	return projectBuildMaterialPurchaseMapper.getMaterialPurchaseList(orgId,"%"+search+"%");
}

/**
 * 
 * @Title: getQueryPurchaseList   
 * @Description: TODO 获取查询采购单表列
 * @param: orgId
 * @param: accountId
 * @param: projectTitle
 * @param: beginTime
 * @param: endTime
 * @param: companyName
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getQueryPurchaseList(String orgId, String createUser, String projectTitle, String beginTime, String endTime, String companyName)
{
	return projectBuildMaterialPurchaseMapper.getQueryPurchaseList(orgId, createUser, "%"+projectTitle+"%", beginTime, endTime, "%"+companyName+"%");
}

/**
 * 
 * @Title: getQueryPurchaseList   
 * @Description: TODO 获取查询采购单表列分页
 * @param: pageParam
 * @param: projectTitle
 * @param: beginTime
 * @param: endTime
 * @param: companyName
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getQueryPurchaseList(PageParam pageParam,String projectTitle, String beginTime, String endTime, String companyName) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getQueryPurchaseList(pageParam.getOrgId(),pageParam.getAccountId(),projectTitle,beginTime,endTime,companyName);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: purchaseAndSendBpm   
 * @Description: TODO 发起申请 
 * @param: projectBuildSupplier
 * @param: @return      
 * @return: int      
 * @throws
 */
@Transactional(value = "generalTM")
public RetDataBean purchaseAndSendBpm(ProjectBuildMaterialPurchase projectBuildMaterialPurchase,String materialMx)
{
	ProjectBuildBpmConfig projectBuildBpmConfig = new ProjectBuildBpmConfig();
	projectBuildBpmConfig.setEvent("purchase");
	projectBuildBpmConfig.setOrgId(projectBuildMaterialPurchase.getOrgId());
	projectBuildBpmConfig=projectBuildBpmConfigService.selectOneProjectBuildBpmConfig(projectBuildBpmConfig);
	if(StringUtils.isBlank(projectBuildBpmConfig.getFlowId()))
	{
		return RetDataTools.NotOk("请先项目参数中设置对应的审批流程!");
	}
	insertProjectBuildMaterialPurchase(projectBuildMaterialPurchase);
	JSONArray jsonArr = JSON.parseArray(materialMx);
	for(int i=0;i<jsonArr.size();i++)
	{
		JSONObject json = jsonArr.getJSONObject(i);
		ProjectBuildMaterialMx projectBuildMaterialMx = new ProjectBuildMaterialMx();
		projectBuildMaterialMx.setMaterialMxId(SysTools.getGUID());
		projectBuildMaterialMx.setProjectId(projectBuildMaterialPurchase.getProjectId());
		projectBuildMaterialMx.setStageId(projectBuildMaterialPurchase.getStageId());
		projectBuildMaterialMx.setPurchaseId(projectBuildMaterialPurchase.getPurchaseId());
		projectBuildMaterialMx.setMaterialId(json.getString("materialId"));
		projectBuildMaterialMx.setSortNo(json.getInteger("sortNo"));
		projectBuildMaterialMx.setQuantity(json.getInteger("quantity"));
		projectBuildMaterialMx.setPrice(json.getDouble("price"));
		projectBuildMaterialMx.setRemark(json.getString("remark"));
		projectBuildMaterialMx.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		projectBuildMaterialMx.setCreateUser(projectBuildMaterialPurchase.getCreateUser());
		projectBuildMaterialMx.setOrgId(projectBuildMaterialPurchase.getOrgId());
		projectBuildMaterialMxService.insertProjectBuildMaterialMx(projectBuildMaterialMx);
	}
	String flowId = projectBuildBpmConfig.getFlowId();
	BpmFlow bpmFlow = new BpmFlow();
	bpmFlow.setFlowId(flowId);
	bpmFlow.setOrgId(projectBuildMaterialPurchase.getOrgId());
	bpmFlow = bpmFlowService.selectOne(bpmFlow);
	BpmList bpmList = new BpmList(); 
	bpmList.setRunId(SysTools.getGUID());
	bpmList.setFlowTitle(projectBuildMaterialPurchase.getTitle());
	bpmList.setDelFlag("0");
	bpmList.setUrgency("0");
	bpmList.setStatus("0");
	bpmList.setOpUserStr(projectBuildMaterialPurchase.getCreateUser());
	bpmList.setFlowId(flowId);
	bpmList.setAttach(projectBuildMaterialPurchase.getAttach());
	bpmList.setCreateUser(projectBuildMaterialPurchase.getCreateUser());
	bpmList.setCreateTime(projectBuildMaterialPurchase.getCreateTime());
	bpmList.setOrgId(projectBuildMaterialPurchase.getOrgId());
	BpmProcess bpmProcess = new BpmProcess();
	bpmProcess.setFlowId(bpmList.getFlowId());
	bpmProcess.setPrcsType("1");
	bpmProcess.setOrgId(bpmList.getOrgId());
	BpmProcess retBpmProcess = bpmProcessService.selectOneBpmProcess(bpmProcess);
	bpmListService.insertBpmList(bpmList);
	BpmRunProcess bpmRunProcess = new BpmRunProcess();
	bpmRunProcess.setRunProcessId(SysTools.getGUID());
	bpmRunProcess.setRunId(bpmList.getRunId());
	bpmRunProcess.setProcessId(retBpmProcess.getProcessId());
	bpmRunProcess.setAccountId(bpmList.getCreateUser());
	bpmRunProcess.setCreateUser(bpmList.getCreateUser());
	bpmRunProcess.setOpFlag("0");
	bpmRunProcess.setStatus("0");
	bpmRunProcess.setCreateTime(bpmList.getCreateTime());
	bpmRunProcess.setRecTime(bpmList.getCreateTime());
	bpmRunProcess.setCreateUser(bpmList.getCreateUser());
	bpmRunProcess.setOrgId(bpmList.getOrgId());
	bpmRunProcessService.insertBpmRunRrocess(bpmRunProcess);
	BpmForm bpmForm = new BpmForm();
	bpmForm.setFormId(bpmFlow.getFormId());
	bpmForm.setOrgId(bpmFlow.getOrgId());
	bpmForm = bpmFormService.selectOneBpmForm(bpmForm);
	//bpmTableService.insertFormData("B_" + bpmForm.getTableName().toUpperCase(), bpmList.getRunId(),bpmList.getOrgId());
	//确保kList 与 vList 两个长度一样，同时要保证kList中要有RUN_ID 与 ORG_ID 两个记录
	List<String> kList = new ArrayList<String>();
	kList.add("RUN_ID");
	kList.add("ORG_ID");
	kList.add("DATA_NUM10");
	List<String> vList = new ArrayList<String>();
	vList.add(bpmList.getRunId());
	vList.add(bpmList.getOrgId());
	vList.add(projectBuildMaterialPurchase.getPurchaseId());
	bpmTableService.insertFormDataKVList("B_" + bpmForm.getTableName().toUpperCase(), kList, vList);
	return RetDataTools.Ok("创建BPM成功！","/app/core/bpm/dowork?runId=" + bpmList.getRunId() + "&runProcessId=" + bpmRunProcess.getRunProcessId()+"&purchaseId="+projectBuildMaterialPurchase.getPurchaseId(),"1");
}

}
