/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  OfficeSuppliesSortService.java   
 * @Package com.core136.service.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月18日 上午10:48:00   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.officesupplies;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.officesupplies.OfficeSuppliesSort;
import com.core136.mapper.officesupplies.OfficeSuppliesSortMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class OfficeSuppliesSortService {
@Autowired
private OfficeSuppliesSortMapper officeSuppliesSortMapper;

public int insertOfficeSuppliesSort(OfficeSuppliesSort officeSuppliesSort)
{
	return officeSuppliesSortMapper.insert(officeSuppliesSort);
}

public int deleteOfficeSuppliesSort(OfficeSuppliesSort officeSuppliesSort)
{
	return officeSuppliesSortMapper.delete(officeSuppliesSort);
}

public int updateOfficeSuppliesSort(Example example,OfficeSuppliesSort officeSuppliesSort)
{
	return officeSuppliesSortMapper.updateByExampleSelective(officeSuppliesSort, example);
}

public OfficeSuppliesSort selectOneOfficeSuppliesSort(OfficeSuppliesSort officeSuppliesSort)
{
	return officeSuppliesSortMapper.selectOne(officeSuppliesSort);
}

/**
 * 
 * @Title: getOfficeSuppliesSortTree   
 * @Description: TODO 获取固定资产分类
 * @param: orgId
 * @param: parentId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getOfficeSuppliesSortTree(String orgId,String parentId)
{
	return officeSuppliesSortMapper.getOfficeSuppliesSortTree(orgId, parentId);
}
/**
 * 
 * @Title: isExistChild   
 * @Description: TODO 判断分类下是否有子节点
 * @param: orgId
 * @param: parentId
 * @param: @return      
 * @return: int      
 * @throws
 */
public int isExistChild(String orgId,String parentId)
{
	return officeSuppliesSortMapper.isExistChild(orgId, parentId);
}

}
