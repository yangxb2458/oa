package com.core136.service.superversion;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.superversion.SuperversionConfig;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.superversion.SuperversionConfigMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class SuperversionConfigService {
@Autowired
private SuperversionConfigMapper superversionConfigMapper;

public int insertSuperversionConfig(SuperversionConfig superversionConfig) {
	return superversionConfigMapper.insert(superversionConfig);
}

public int deleteSuperversionConfig(SuperversionConfig superversionConfig)
{
	return superversionConfigMapper.delete(superversionConfig);
}

public int updateSuperversionConfig(Example example,SuperversionConfig superversionConfig)
{
	return superversionConfigMapper.updateByExampleSelective(superversionConfig, example);
}

public SuperversionConfig selectOneSuperversionConfig(SuperversionConfig superversionConfig)
{
	return superversionConfigMapper.selectOne(superversionConfig);
}

public List<SuperversionConfig> getAllConfig(Example example)
{
	return superversionConfigMapper.selectByExample(example);
}

public PageInfo<SuperversionConfig> getSuperversionConfigList(PageParam pageParam) {
	Example example = new Example(SuperversionConfig.class);
	example.createCriteria().andEqualTo("orgId",pageParam.getOrgId());
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<SuperversionConfig> datalist= getAllConfig(example);
	PageInfo<SuperversionConfig> pageInfo = new PageInfo<SuperversionConfig>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getAllSuperversionConfigList   
 * @Description: TODO 获取类型与领导列表  
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getAllSuperversionConfigList(String orgId)
{
	return superversionConfigMapper.getAllSuperversionConfigList(orgId);
}
/**
 * 
 * @Title: getMySuperversionConfigList   
 * @Description: TODO与我有关的分类
 * @param: orgId
 * @param: accountId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getMySuperversionConfigList(String orgId,String accountId)
{
	return superversionConfigMapper.getMySuperversionConfigList(orgId,accountId);
}
/**
 * 
 * @Title: getQuerySuperversionForType   
 * @Description: TODO 按类型汇总
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getQuerySuperversionForType(String orgId)
{
	return superversionConfigMapper.getQuerySuperversionForType(orgId);
}

}
