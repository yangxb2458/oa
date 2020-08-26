/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrLicenceService.java   
 * @Package com.core136.service.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年12月31日 上午10:09:25   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
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
import com.core136.bean.hr.HrLicence;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrLicenceMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class HrLicenceService {
@Autowired
private HrLicenceMapper hrLicenceMapper;
@Autowired
private JdbcTemplate jdbcTemplate;

public int insertHrLicence(HrLicence hrLicence)
{
	return hrLicenceMapper.insert(hrLicence);
}

public int deleteHrLicence(HrLicence hrLicence)
{
	return hrLicenceMapper.delete(hrLicence);
}

public int updateHrLicence(Example example,HrLicence hrLicence)
{
	return hrLicenceMapper.updateByExampleSelective(hrLicence, example);
}

public HrLicence selectOneHrLicence(HrLicence hrLicence)
{
	return hrLicenceMapper.selectOne(hrLicence);
}
/**
 * 
 * @Title: getHrLicenceList   
 * @Description: TODO 获取证照列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param licenceType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrLicenceList(String orgId,String userId,String beginTime,String endTime,String licenceType,String search)
{
	return hrLicenceMapper.getHrLicenceList(orgId, userId, beginTime, endTime,licenceType, "%"+search+"%");
}
/**
 * 
 * @Title: getMyHrLicenceList   
 * @Description: TODO 查询个人证照信息
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyHrLicenceList(String orgId,String accountId)
{
	return hrLicenceMapper.getMyHrLicenceList(orgId, accountId);
}

/**
 * 
 * @Title: getHrLicenceList   
 * @Description: TODO 获取证照列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param licenceType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrLicenceList(PageParam pageParam,String userId,String beginTime,String endTime,String licenceType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrLicenceList(pageParam.getOrgId(),userId, beginTime, endTime, licenceType, pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMyHrLicenceList   
 * @Description: TODO 查询个人证照信息
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyHrLicenceList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyHrLicenceList(pageParam.getOrgId(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: importHrLicence   
 * @Description: TODO 证照记录导入
 * @param account
 * @param file
 * @return
 * @throws IOException
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM")
public RetDataBean importHrLicence(Account account,MultipartFile file) throws IOException
{
	Map<String,String> fieldMap = new HashMap<String, String>();
	fieldMap.put("排序号", "SORT_NO");
	fieldMap.put("持证人员", "USER_ID");
	fieldMap.put("证照名称", "NAME");
	fieldMap.put("证照编号", "LICENCE_CODE");
	fieldMap.put("生效日期", "BEGIN_TIME");
	fieldMap.put("截止日期", "END_TIME");
	fieldMap.put("发证机构", "NOTIFIED_BODY");
	fieldMap.put("证照类型", "LICENCE_TYPE");
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
			if(titleList.get(k).equals("持证人员"))
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
		String insertSql = "INSERT INTO HR_LICENCE(LICENCE_ID,"+fieldString+",CREATE_TIME,CREATE_USER,ORG_ID) values"+ "("+valueString+")";
		jdbcTemplate.execute(insertSql);
	}
	return RetDataTools.Ok("批量导入证照记录成功！");
}

}
