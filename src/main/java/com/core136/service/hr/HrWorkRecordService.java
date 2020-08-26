package com.core136.service.hr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.ExcelUtil;
import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.core136.bean.account.Account;
import com.core136.bean.hr.HrWorkRecord;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrWorkRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrWorkRecordService {
@Autowired
private HrWorkRecordMapper hrWorkRecordMapper;
@Autowired
private JdbcTemplate jdbcTemplate;
public int insertHrWorkRecord(HrWorkRecord hrWorkRecord)
{
	return hrWorkRecordMapper.insert(hrWorkRecord);
}

public int deleteHrWorkRecord(HrWorkRecord hrWorkRecord)
{
	return hrWorkRecordMapper.delete(hrWorkRecord);
}

public int updateHrWorkRecord(Example example,HrWorkRecord hrWorkRecord)
{
	return hrWorkRecordMapper.updateByExampleSelective(hrWorkRecord, example);
}

public HrWorkRecord selectOneHrWorkRecord(HrWorkRecord hrWorkRecord)
{
	return hrWorkRecordMapper.selectOne(hrWorkRecord);
}
/**
 * 
 * @Title: getHrWorkRecordList   
 * @Description: TODO 获取工作经历列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrWorkRecordList(String orgId,String userId,String beginTime,String endTime,String nature,String search)
{
	return hrWorkRecordMapper.getHrWorkRecordList(orgId, userId, beginTime, endTime, nature,"%"+search+"%");
}
/**
 * 
 * @Title: getMyHrWorkRecordList   
 * @Description: TODO 查询个人工作经历
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyHrWorkRecordList(String orgId,String accountId)
{
	return hrWorkRecordMapper.getMyHrWorkRecordList(orgId, accountId);
}

/**
 * 
 * @Title: getHrWorkRecordList   
 * @Description: TODO 获取工作经历列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrWorkRecordList(PageParam pageParam,String userId,String beginTime,String endTime,String nature) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrWorkRecordList(pageParam.getOrgId(),userId, beginTime, endTime,nature, pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getMyHrWorkRecordList   
 * @Description: TODO 查询个人工作经历
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyHrWorkRecordList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyHrWorkRecordList(pageParam.getOrgId(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: importHrWorkRecord   
 * @Description: TODO 工作经历导入
 * @param account
 * @param file
 * @return
 * @throws IOException
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM")
public RetDataBean importHrWorkRecord(Account account,MultipartFile file) throws IOException
{
	Map<String,String> fieldMap = new HashMap<String, String>();
	fieldMap.put("排序号", "SORT_NO");
	fieldMap.put("关联档案人员", "USER_ID");
	fieldMap.put("公司名称", "COMP_NAME");
	fieldMap.put("所在部门", "DEPT_NAME");
	fieldMap.put("所属行业", "INDUSTRY");
	fieldMap.put("公司类型", "NATURE");
	fieldMap.put("担任职务", "POST");
	fieldMap.put("证明人", "CERIFIER");
	fieldMap.put("入职日期", "BEGIN_TIME");
	fieldMap.put("离职日期", "END_TIME");
	fieldMap.put("工作内容", "JOB_CONTENT");
	fieldMap.put("工作成就", "ACHIEVEMENT");
	fieldMap.put("离职原因", "REASON_FOR_LEAVE");
	fieldMap.put("备注", "REMARK");
	List<String> fieldList = new ArrayList<String>();
	List<String> titleList = new ArrayList<String>();
	for(Map.Entry<String, String> entry : fieldMap.entrySet()){
	    fieldList.add(entry.getValue());
	    titleList.add(entry.getKey());
	}
	String[] fieldArr = new String[fieldList.size()];
	fieldList.toArray(fieldArr);
	String fieldString=  StringUtils.join(fieldArr,",");
	List<Map<String, String>> recordList = ExcelUtil.readExcel(file);
	for(int i=0;i<recordList.size();i++)
	{
		Map<String,String> tempMap = recordList.get(i);
		String valueString = "'"+SysTools.getGUID()+"',";
		for(int k=0;k<titleList.size();k++)
		{
			if(titleList.get(k).equals("关联档案人员"))
			{
				if(StringUtils.isNotBlank(tempMap.get(titleList.get(k))))
				{
					String sql1 = "SELECT USER_ID FROM HR_USER_INFO WHERE USER_NAME ='"+tempMap.get(titleList.get(k))+"'";
					String userId = jdbcTemplate.queryForObject(sql1, String.class);
					valueString+="'"+userId+"',";
				}else
				{
					valueString+="'',";
				}
			}else
			{
				valueString+="'"+tempMap.get(titleList.get(k))+"',";
			}
		}
		valueString+="'"+SysTools.getTime("yyyy-MM-dd HH:mm:ss")+"',";
		valueString+="'"+account.getAccountId()+"',";
		valueString+="'"+account.getOrgId()+"'";
		String insertSql = "INSERT INTO HR_WORK_RECORD(RECORD_ID,"+fieldString+",CREATE_TIME,CREATE_USER,ORG_ID) values"+ "("+valueString+")";
		jdbcTemplate.execute(insertSql);
	}
	return RetDataTools.Ok("批量导入工作经历成功！");
}

}
