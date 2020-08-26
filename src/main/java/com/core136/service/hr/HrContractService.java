package com.core136.service.hr;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
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
import com.core136.bean.hr.HrContract;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrContractMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrContractService {
@Autowired
private HrContractMapper hrContractMapper;
@Autowired
private JdbcTemplate jdbcTemplate;

public int insertHrContract(HrContract hrContract)
{
	return hrContractMapper.insert(hrContract);
}

public int deleteHrContract(HrContract hrContract)
{
	return hrContractMapper.delete(hrContract);
}

public int updateHrContract(Example example ,HrContract hrContract)
{
	return hrContractMapper.updateByExampleSelective(hrContract, example);
}

public HrContract selectOneHrContract(HrContract hrContract)
{
	return hrContractMapper.selectOne(hrContract);
}
/**
 * 
 * @Title: getHrContractList   
 * @Description: TODO 获取合同列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param enterpries
 * @param contractType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrContractList(String orgId,String userId,String beginTime,String endTime,String enterpries,String contractType)
{
	return hrContractMapper.getHrContractList(orgId, userId, beginTime, endTime, enterpries, contractType);
}

/**
 * 
 * @Title: getMyHrContractList   
 * @Description: TODO 查询自己的合同列表
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getMyHrContractList(String orgId,String accountId)
{
	return hrContractMapper.getMyHrContractList(orgId, accountId);
}
/**
 * 
 * @Title: getHrContractList   
 * @Description: TODO 获取合同列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param enterpries
 * @param contractType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrContractList(PageParam pageParam,String userId,String beginTime,String endTime,String enterpries,String contractType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrContractList(pageParam.getOrgId(),userId, beginTime, endTime, enterpries, contractType);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMyHrContractList   
 * @Description: TODO 查询自己的合同列表
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyHrContractList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyHrContractList(pageParam.getOrgId(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: importHrContract   
 * @Description: TODO 人事合同导入
 * @param account
 * @param file
 * @return
 * @throws IOException
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM")
public RetDataBean importHrContract(Account account,MultipartFile file) throws IOException
{
	Map<String,String> fieldMap = new HashMap<String, String>();
	fieldMap.put("排序号", "SORT_NO");
	fieldMap.put("应聘岗位", "POOL_POSITION");
	fieldMap.put("合同编号", "CONTRACT_CODE");
	fieldMap.put("合同类型", "CONTRACT_TYPE");
	fieldMap.put("签约公司", "ENTERPRIES");
	fieldMap.put("签约方式", "SIGN_TYPE");
	fieldMap.put("关联档案人员", "USER_ID");
	fieldMap.put("雇佣者姓名", "USER_NAME");
	fieldMap.put("合同生效时间", "START_TIME");
	fieldMap.put("合同终止时间", "END_TIME");
	fieldMap.put("合同期限属性", "SPECIALIZATION");
	fieldMap.put("合同签订时间", "SIGN_TIME");
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
		String insertSql = "INSERT INTO HR_CONTRACT(CONTRACT_ID,"+fieldString+",CREATE_TIME,CREATE_USER,ORG_ID) values"+ "("+valueString+")";
		jdbcTemplate.execute(insertSql);
	}
	return RetDataTools.Ok("批量导入人事合同成功！");
}


/**
 * 
 * @Title: getDeskHrContractList   
 * @Description: TODO 获取快到期的合同列表
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getDeskHrContractList(String orgId)
{
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	 Date d = new Date();
	 GregorianCalendar gc = new GregorianCalendar();
	 gc.setTime(d);
	 gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
	 gc.add(2, 1);
	 String endTime = sf.format(gc.getTime());
	String beginTime = SysTools.getTime("yyyy-MM-dd");
	return hrContractMapper.getDeskHrContractList(orgId, beginTime, endTime);
}

}
