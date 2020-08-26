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
import com.core136.bean.hr.HrIncentive;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrIncentiveMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrIncentiveService {
@Autowired
private HrIncentiveMapper hrIncentiveMapper;
@Autowired
private JdbcTemplate jdbcTemplate;

public int insertHrIncentive(HrIncentive hrIncentive)
{
	return hrIncentiveMapper.insert(hrIncentive);
}

public int deleteHrIncentive(HrIncentive hrIncentive)
{
	return hrIncentiveMapper.delete(hrIncentive);
}

public int updateHrIncentive(Example example,HrIncentive hrIncentive)
{
	return hrIncentiveMapper.updateByExampleSelective(hrIncentive, example);
}

public HrIncentive selectOneHrIncentive(HrIncentive hrIncentive)
{
	return hrIncentiveMapper.selectOne(hrIncentive);
}
/**
 * 
 * @Title: getHrIncentiveList   
 * @Description: TODO 获取奖惩记录列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param incentiveType
 * @param incentiveItem
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrIncentiveList(String orgId,String userId,String beginTime,String endTime,String incentiveType,String incentiveItem)
{
	return hrIncentiveMapper.getHrIncentiveList(orgId, userId, beginTime, endTime, incentiveType, incentiveItem);
}

/**
 * 
 * @Title: getMyHrIncentiveList   
 * @Description: TODO  个人查询奖惩记录
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyHrIncentiveList(String orgId,String accountId)
{
	return hrIncentiveMapper.getMyHrIncentiveList(orgId, accountId);
}
/**
 * 
 * @Title: getHrIncentiveList   
 * @Description: TODO 获取奖惩记录列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param incentiveType
 * @param incentiveItem
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrIncentiveList(PageParam pageParam,String userId,String beginTime,String endTime,String incentiveType,String incentiveItem) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrIncentiveList(pageParam.getOrgId(),userId, beginTime, endTime, incentiveType, incentiveItem);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMyHrIncentiveList   
 * @Description: TODO个人查询奖惩记录
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyHrIncentiveList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyHrIncentiveList(pageParam.getOrgId(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}


/**
 * 
 * @Title: importHrIncentive   
 * @Description: TODO 奖惩记录导入
 * @param account
 * @param file
 * @return
 * @throws IOException
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM")
public RetDataBean importHrIncentive(Account account,MultipartFile file) throws IOException
{
	Map<String,String> fieldMap = new HashMap<String, String>();
	fieldMap.put("排序号", "SORT_NO");
	fieldMap.put("奖惩类型", "INCENTIVE_TYPE");
	fieldMap.put("涉及人员", "USER_ID");
	fieldMap.put("处理事项", "INCENTIVE_ITEM");
	fieldMap.put("处理日期", "INCENTIVE_TIME");
	fieldMap.put("奖惩金额", "INCENTIVE_AMOUNT");
	fieldMap.put("工资月份", "SALARY_MONTH");
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
			if(titleList.get(k).equals("涉及人员"))
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
		String insertSql = "INSERT INTO HR_INCENTIVE(INCENTIVE_ID,"+fieldString+",CREATE_TIME,CREATE_USER,ORG_ID) values"+ "("+valueString+")";
		jdbcTemplate.execute(insertSql);
	}
	return RetDataTools.Ok("批量导入奖惩记录成功！");
}

}
