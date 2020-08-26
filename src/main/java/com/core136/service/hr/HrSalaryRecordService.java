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
import com.core136.bean.hr.HrSalaryRecord;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrSalaryRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrSalaryRecordService {
@Autowired
private HrSalaryRecordMapper hrSalaryRecordMapper;
@Autowired
private JdbcTemplate jdbcTemplate;

public int insertHrSalaryRecord(HrSalaryRecord hrSalaryRecord)
{
	return hrSalaryRecordMapper.insert(hrSalaryRecord);
}

public int deleteHrSalaryRecord(HrSalaryRecord hrSalaryRecord)
{
	return hrSalaryRecordMapper.delete(hrSalaryRecord);
}

public int updateHrSalaryRecord(Example example,HrSalaryRecord hrSalaryRecord)
{
	return hrSalaryRecordMapper.updateByExampleSelective(hrSalaryRecord, example);
}

public HrSalaryRecord selectOneHrSalaryRecord(HrSalaryRecord hrSalaryRecord)
{
	return hrSalaryRecordMapper.selectOne(hrSalaryRecord);
}
/**
 * 
 * @Title: getHrSalaryRecordList   
 * @Description: TODO 获取人员薪资列表
 * @param orgId
 * @param userId
 * @param year
 * @param month
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getHrSalaryRecordList(String orgId,String userId,String year,String month)
{
	return hrSalaryRecordMapper.getHrSalaryRecordList(orgId, userId, year, month);
}

/**
 * 
 * @Title: getMyHrSalaryRecordList   
 * @Description: TODO 个人薪资查询
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyHrSalaryRecordList(String orgId,String accountId)
{
	return hrSalaryRecordMapper.getMyHrSalaryRecordList(orgId, accountId);
}

/**
 * 
 * @Title: getHrSalaryRecordList   
 * @Description: TODO 获取人员薪资列表
 * @param pageParam
 * @param userId
 * @param year
 * @param month
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrSalaryRecordList(PageParam pageParam,String userId,String year,String month) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrSalaryRecordList(pageParam.getOrgId(),userId,year, month);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getMyHrSalaryRecordList   
 * @Description: TODO 获取人员薪资列表
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyHrSalaryRecordList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyHrSalaryRecordList(pageParam.getOrgId(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: importHrSalaryRecord   
 * @Description: TODO 导入人员薪资
 * @param account
 * @param file
 * @return
 * @throws IOException
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM")
public RetDataBean importHrSalaryRecord(Account account,MultipartFile file) throws IOException
{
	Map<String,String> fieldMap = new HashMap<String, String>();
	fieldMap.put("排序号", "SORT_NO");
	fieldMap.put("薪资年度", "YEAR");
	fieldMap.put("薪资月份", "MONTH");
	fieldMap.put("人员姓名", "USER_ID");
	fieldMap.put("岗位工资", "POST_SALARY");
	fieldMap.put("薪级工资", "LEVEL_SALARY");
	fieldMap.put("粮油补贴", "FOOD_SALARY");
	fieldMap.put("特殊岗位津贴", "OTHER_POST_SALARY");
	fieldMap.put("交通补贴", "TRANSPORT_SALARY");
	fieldMap.put("岗位津贴", "POST_ALLOWANCE");
	fieldMap.put("应发合计数", "SUM_AMOUNT");
	fieldMap.put("退休金", "PENSOIN");
	fieldMap.put("失业保险", "UNEMPLOYMENT");
	fieldMap.put("医保", "MEDICAL");
	fieldMap.put("公积金", "ACCUMULATION_FUND");
	fieldMap.put("个人所得税", "TAX");
	fieldMap.put("其它费用", "COST_OTHER");
	fieldMap.put("实扣合计数", "REAL_COST");
	fieldMap.put("实发合计数", "REAL_SALARY");
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
			if(titleList.get(k).equals("人员姓名"))
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
		String insertSql = "INSERT INTO HR_SALARY_RECORD(RECORD_ID,"+fieldString+",CREATE_TIME,CREATE_USER,ORG_ID) values"+ "("+valueString+")";
		jdbcTemplate.execute(insertSql);
	}
	return RetDataTools.Ok("批量导入人员薪资成功！");
}

}
