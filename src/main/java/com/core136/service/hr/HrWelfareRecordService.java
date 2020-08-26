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
import com.core136.bean.hr.HrWelfareRecord;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrWelfareRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrWelfareRecordService {
@Autowired
private HrWelfareRecordMapper hrWelfareRecordMapper;
@Autowired
private JdbcTemplate jdbcTemplate;

public int insertHrWelfareRecord(HrWelfareRecord hrWelfareRecord)
{
	return hrWelfareRecordMapper.insert(hrWelfareRecord);
}

public int deleteHrWelfareRecord(HrWelfareRecord hrWelfareRecord)
{
	return hrWelfareRecordMapper.delete(hrWelfareRecord);
}

public int updateHrWelfareRecord(Example example,HrWelfareRecord hrWelfareRecord)
{
	return hrWelfareRecordMapper.updateByExampleSelective(hrWelfareRecord, example);
}

public HrWelfareRecord selectOneHrWelfareRecord(HrWelfareRecord hrWelfareRecord)
{
	return hrWelfareRecordMapper.selectOne(hrWelfareRecord);
}

/**
 * 
 * @Title: getHrWelfareRecordList   
 * @Description: TODO 获取福利列表
 * @param orgId
 * @param beginTime
 * @param endTime
 * @param userId
 * @param type
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrWelfareRecordList(String orgId,String beginTime,String endTime,String userId,String type,String search)
{
	return hrWelfareRecordMapper.getHrWelfareRecordList(orgId, beginTime, endTime, type, userId, "%"+search+"%");
}

/**
 * 
 * @Title: getHrWelfareRecordList   
 * @Description: TODO 获取福利列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @param userId
 * @param type
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrWelfareRecordList(PageParam pageParam,String beginTime,String endTime,String userId,String type) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrWelfareRecordList(pageParam.getOrgId(),beginTime,endTime,userId,type,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 	
 * @Title: importHrWelfareRecord   
 * @Description: TODO 批量导入人员福利
 * @param account
 * @param file
 * @return
 * @throws IOException
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM")
public RetDataBean importHrWelfareRecord(Account account,MultipartFile file) throws IOException
{
	Map<String,String> fieldMap = new HashMap<String, String>();
	fieldMap.put("排序号", "SORT_NO");
	fieldMap.put("福利标题", "TITLE");
	fieldMap.put("福利年度", "YEAR");
	fieldMap.put("福利月份", "MONTH");
	fieldMap.put("人员姓名", "USER_ID");
	fieldMap.put("福利类型", "TYPE");
	fieldMap.put("福利金额", "AMOUNT");
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
		String insertSql = "INSERT INTO HR_WELFARE_RECORD(RECORD_ID,"+fieldString+",CREATE_TIME,CREATE_USER,ORG_ID) values"+ "("+valueString+")";
		jdbcTemplate.execute(insertSql);
	}
	return RetDataTools.Ok("批量导入人员福利成功！");
}
}
