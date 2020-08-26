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
import com.core136.bean.hr.HrWorkSkills;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrWorkSkillsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrWorkSkillsService {
@Autowired
private HrWorkSkillsMapper hrWorkSkillsMapper;
@Autowired
private JdbcTemplate jdbcTemplate;
public int insertHrWorkSkills(HrWorkSkills hrWorkSkills)
{
	return hrWorkSkillsMapper.insert(hrWorkSkills);
}
public int deleteHrWorkSkills(HrWorkSkills hrWorkSkills)
{
	return hrWorkSkillsMapper.delete(hrWorkSkills);
}
public int updateHrWorkSkills(Example example,HrWorkSkills hrWorkSkills)
{
	return hrWorkSkillsMapper.updateByExampleSelective(hrWorkSkills, example);
}

public HrWorkSkills selectOneHrWorkSkills(HrWorkSkills hrWorkSkills)
{
	return hrWorkSkillsMapper.selectOne(hrWorkSkills);
}
/**
 * 
 * @Title: getHrWorkSkillsList   
 * @Description: TODO 工作特长列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param skillsLevel
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrWorkSkillsList(String orgId,String userId,String beginTime,String endTime,String skillsLevel,String search)
{
	return hrWorkSkillsMapper.getHrWorkSkillsList(orgId, userId, beginTime, endTime, skillsLevel, "%"+search+"%");
}

/**
 * 
 * @Title: getHrWorkSkillsList   
 * @Description: TODO 工作特长列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param skillsLevel
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrWorkSkillsList(PageParam pageParam,String userId,String beginTime,String endTime,String skillsLevel) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrWorkSkillsList(pageParam.getOrgId(),userId, beginTime, endTime, skillsLevel,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: importHrWorkSkills   
 * @Description: TODO 工作技能导入
 * @param account
 * @param file
 * @return
 * @throws IOException
 * RetDataBean    
 * @throws
 */
@Transactional(value="generalTM")
public RetDataBean importHrWorkSkills(Account account,MultipartFile file) throws IOException
{
	Map<String,String> fieldMap = new HashMap<String, String>();
	fieldMap.put("排序号", "SORT_NO");
	fieldMap.put("关联档案人员", "USER_ID");
	fieldMap.put("技能名称", "NAME");
	fieldMap.put("技能等级", "SKILLS_LEVEL");
	fieldMap.put("是否有证书", "SKILLS_CERIFICATE");
	fieldMap.put("发证机构", "NOTIFIE_BODY");
	fieldMap.put("发证日期", "BEGIN_TIME");
	fieldMap.put("终止日期", "END_TIME");
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
			}else if(titleList.get(k).equals("是否有证书"))
			{
				if(StringUtils.isNotBlank(tempMap.get(titleList.get(k))))
				{
					valueString+="'1',";
				}else
				{
					valueString+="'0',";
				}
			}else
			{
				valueString+="'"+tempMap.get(titleList.get(k))+"',";
			}
		}
		valueString+="'"+SysTools.getTime("yyyy-MM-dd HH:mm:ss")+"',";
		valueString+="'"+account.getAccountId()+"',";
		valueString+="'"+account.getOrgId()+"'";
		String insertSql = "INSERT INTO HR_WORK_SKILLS(RECORD_ID,"+fieldString+",CREATE_TIME,CREATE_USER,ORG_ID) values"+ "("+valueString+")";
		jdbcTemplate.execute(insertSql);
	}
	return RetDataTools.Ok("批量导入劳动技能成功！");
}
}
