/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrUserInfoService.java   
 * @Package com.core136.service.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月29日 上午9:35:19   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.hr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.core136.bean.hr.HrUserInfo;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrUserInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */

@Service
public class HrUserInfoService {
@Autowired
private HrUserInfoMapper hrUserInfoMapper;
@Autowired
private JdbcTemplate jdbcTemplate;

public int insertHrUserInfo(HrUserInfo hrUserInfo)
{
	return hrUserInfoMapper.insert(hrUserInfo);
}

public int deleteHrUserInfo(HrUserInfo hrUserInfo)
{
	return hrUserInfoMapper.delete(hrUserInfo);
}

public int updateHrUserInfo(Example example,HrUserInfo hrUserInfo)
{
	return hrUserInfoMapper.updateByExampleSelective(hrUserInfo, example);
}
public HrUserInfo selectOneHrUserInfo(HrUserInfo hrUserInfo)
{
	return hrUserInfoMapper.selectOne(hrUserInfo);
}
/**
 * 
 * @Title: getHrUserInfoByDeptId   
 * @Description: TODO 获取部门下的人员列表
 * @param: orgId
 * @param: deptId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getHrUserInfoByDeptId(String orgId,String deptId)
{
	return hrUserInfoMapper.getHrUserInfoByDeptId(orgId, deptId);
}

/**
 * 
 * @Title: getHrUserInfoByBeptIdInWorkList   
 * @Description: TODO 获取部门下的人员列表
 * @param orgId
 * @param deptId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getHrUserInfoByBeptIdInWorkList(String orgId,String deptId,String workStatus,String employedTime,String staffCardNo,String search)
{
	return hrUserInfoMapper.getHrUserInfoByBeptIdInWorkList(orgId, deptId,workStatus,employedTime,staffCardNo,"%"+search+"%");
}


/**
 * 
 * @Title: getHrUserInfoByBeptIdInWorkList   
 * @Description: TODO 获取部门下的人员列表
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrUserInfoByBeptIdInWorkList(PageParam pageParam,String workStatus,String employedTime,String staffCardNo) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrUserInfoByBeptIdInWorkList(pageParam.getOrgId(),pageParam.getDeptId(),workStatus,employedTime,staffCardNo,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getHrUserInfoListByDeptId   
 * @Description: TODO 获取部门下的人员列表
 * @param orgId
 * @param deptId
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>> getHrUserInfoListByDeptId(String orgId,String deptId,String search)
{
	return hrUserInfoMapper.getHrUserInfoListByDeptId(orgId, deptId,"%"+search+"%");
}
/**
 * 
 * @Title: getHrUserInfoListByDeptId   
 * @Description: TODO 获取部门下的人员列表
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrUserInfoListByDeptId(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrUserInfoListByDeptId(pageParam.getOrgId(),pageParam.getDeptId(),pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getHrUserInfoForTree   
 * @Description: TODO 按部门获取人员列表  
 * @param: orgId
 * @param: deptId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getHrUserInfoForTree(String orgId,String deptId)
{
	return hrUserInfoMapper.getHrUserInfoForTree(orgId, deptId);
}
/**
 * 
 * @Title: getUserNamesByUserIds   
 * @Description: TODO 获取HR人员列表
 * @param orgId
 * @param userIds
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getUserNamesByUserIds(String orgId,String userIds)
{
	if(StringUtils.isNotBlank(userIds))
	{
		String[] userIdArr = userIds.split(",");
		List<String> list = Arrays.asList(userIdArr);
		return hrUserInfoMapper.getUserNamesByUserIds(orgId, list);
	}else
	{
		return null;
	}
}

/**
 * 
 * @Title: getHrUserInfoBySearchuser   
 * @Description: TODO 查询HR人员
 * @param orgId
 * @param searchuser
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrUserInfoBySearchuser(String orgId,String searchuser)
{
	return hrUserInfoMapper.getHrUserInfoBySearchuser(orgId, "%"+searchuser+"%");
}

/**
 * 
 * @Title: getDeskHrUserInfo   
 * @Description: TODO 获取人力资源门户人员信息
 * @param orgId
 * @return
 * Map<String,String>    
 * @throws
 */
public Map<String, String>getDeskHrUserInfo(String orgId)
{
	return hrUserInfoMapper.getDeskHrUserInfo(orgId);
}


/**
 * 
 * @Title: importHrUserInfo   
 * @Description: TODO 人事档案信息导入
 * @param account
 * @param file
 * @return
 * @throws IOException
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM")
public RetDataBean importHrUserInfo(Account account,MultipartFile file) throws IOException
{
	Map<String,String> fieldMap = new HashMap<String, String>();
	fieldMap.put("排序号", "SORT_NO");
	fieldMap.put("姓名", "USER_NAME");
	fieldMap.put("OA用户名", "ACCOUNT_ID");
	fieldMap.put("部门", "DEPT_ID");
	fieldMap.put("职务", "LEAVE_ID");
	fieldMap.put("英文名", "USER_NAME_EN");
	fieldMap.put("曾用名", "BEFORE_USER_NAME");
	fieldMap.put("性别", "SEX");
	fieldMap.put("工号", "WORK_NO");
	fieldMap.put("编号", "STAFF_NO");
	fieldMap.put("在职状态", "WORK_STATUS");
	fieldMap.put("身份证号", "STAFF_CARD_NO");
	fieldMap.put("出生日期", "BIRTH_DAY");
	fieldMap.put("籍贯", "NATIVE_PLACE");
	fieldMap.put("民族", "NATIONALTY");
	fieldMap.put("婚姻状况", "MARITAL_STATUS");
	fieldMap.put("政治面貌", "POLITICAL_STATUS");
	fieldMap.put("入党时间", "JOIN_PARTY_TIME");
	fieldMap.put("岗位", "WORK_JOB");
	fieldMap.put("入职时间", "EMPLOYED_TIME");
	fieldMap.put("手机号码", "MOBILE_NO");
	fieldMap.put("学历", "HIGHSET_SHOOL");
	fieldMap.put("毕业时间", "GRADUATION_TIME");
	fieldMap.put("毕业学校", "GRADUATION_SHOOL");
	fieldMap.put("专业", "MAJOR");
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
			if(titleList.get(k).equals("部门"))
			{
				if(StringUtils.isNotBlank(tempMap.get(titleList.get(k))))
				{
					String sql1 = "SELECT DEPT_ID FROM HR_DEPARTMENT WHERE DEPT_NAME ='"+tempMap.get(titleList.get(k))+"'";
					String deptId = jdbcTemplate.queryForObject(sql1, String.class);
					valueString+="'"+deptId+"',";
				}else {
					valueString+="'',";	
				}
			}else if(titleList.get(k).equals("职务"))
			{
				if(StringUtils.isNotBlank(tempMap.get(titleList.get(k))))
				{
					String sql2 = "SELECT LEVEL_ID FROM HR_USER_LEVEL WHERE LEVEL_NAME ='"+tempMap.get(titleList.get(k))+"'";
					String levelId = jdbcTemplate.queryForObject(sql2, String.class);
					valueString+="'"+levelId+"',";
				}else
				{
					valueString+="'',";	
				}
			}else if(titleList.get(k).equals("姓名"))
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
		String insertSql = "INSERT INTO HR_USER_INFO(USER_ID,"+fieldString+",CREATE_TIME,CREATE_USER,ORG_ID) values"+ "("+valueString+")";
		jdbcTemplate.execute(insertSql);
	}
	return RetDataTools.Ok("批量导入人事档案成功！");
}


}
