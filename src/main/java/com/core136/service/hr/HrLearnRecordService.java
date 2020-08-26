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
import com.core136.bean.hr.HrLearnRecord;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrLearnRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrLearnRecordService {
@Autowired
private HrLearnRecordMapper hrLearnRecordMapper;
@Autowired
private JdbcTemplate jdbcTemplate;

public int insertHrLearnRecord(HrLearnRecord hrLearnRecord)
{
	return hrLearnRecordMapper.insert(hrLearnRecord);
}

public int deleteHrLearnRecord(HrLearnRecord hrLearnRecord)
{
	return hrLearnRecordMapper.delete(hrLearnRecord);
}

public int updateHrLearnRecord(Example example,HrLearnRecord hrLearnRecord)
{
	return hrLearnRecordMapper.updateByExampleSelective(hrLearnRecord, example);
}

public HrLearnRecord selectOneHrLearnRecord(HrLearnRecord hrLearnRecord)
{
	return hrLearnRecordMapper.selectOne(hrLearnRecord);
}
/**
 * 
 * @Title: getHrLearnRecordList   
 * @Description: TODO 获取教育经历列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrLearnRecordList(String orgId,String userId,String beginTime,String endTime,String search)
{
	return hrLearnRecordMapper.getHrLearnRecordList(orgId, userId, beginTime, endTime, "%"+search+"%");
}

/**
 * 
 * @Title: getMyHrLearnRecordList   
 * @Description: TODO 个人查询学习记录
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyHrLearnRecordList(String orgId,String accountId)
{
	return hrLearnRecordMapper.getMyHrLearnRecordList(orgId, accountId);
}

/**
 * 
 * @Title: getHrLearnRecordList   
 * @Description: TODO 获取教育经历列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrLearnRecordList(PageParam pageParam,String userId,String beginTime,String endTime) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrLearnRecordList(pageParam.getOrgId(),userId, beginTime, endTime, pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getMyHrLearnRecordList   
 * @Description: TODO 个人查询学习记录
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyHrLearnRecordList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyHrLearnRecordList(pageParam.getOrgId(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: importHrLearnRecord   
 * @Description: TODO 学习记录导入
 * @param account
 * @param file
 * @return
 * @throws IOException
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM")
public RetDataBean importHrLearnRecord(Account account,MultipartFile file) throws IOException
{
	Map<String,String> fieldMap = new HashMap<String, String>();
	fieldMap.put("排序号", "SORT_NO");
	fieldMap.put("关联档案人员", "USER_ID");
	fieldMap.put("学校名称", "SHOOL_NAME");
	fieldMap.put("所学专业", "MAJOR");
	fieldMap.put("入学日期", "BEGIN_TIME");
	fieldMap.put("毕业日期", "END_TIME");
	fieldMap.put("所获学位", "HIGHSET_DEGREE");
	fieldMap.put("证明人", "CERIFIER");
	fieldMap.put("获取证书", "CERIFICATE");
	fieldMap.put("获奖情况", "HONOR");
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
		String insertSql = "INSERT INTO HR_LEARN_RECORD(RECORD_ID,"+fieldString+",CREATE_TIME,CREATE_USER,ORG_ID) values"+ "("+valueString+")";
		jdbcTemplate.execute(insertSql);
	}
	return RetDataTools.Ok("批量导入学习经历成功！");
}
}
