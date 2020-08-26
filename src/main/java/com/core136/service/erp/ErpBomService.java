package com.core136.service.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpBom;
import com.core136.bean.erp.ErpMateriel;
import com.core136.mapper.erp.ErpBomMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  ErpBomService   
 * @Description:TODO BOM清单操作服务类  
 * @author: 稠云信息
 * @date:   2018年12月14日 下午2:45:30   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ErpBomService{
@Autowired
private ErpBomMapper erpBomMapper;

/**
 * 
 * @Title getErpErpBomBySort   
 * @Description TODO 按条件获取BOM表单  
 * @param example
 * @param pageNumber
 * @param pageSize
 * @return      
 * PageInfo<ErpBom>
 */
public PageInfo<ErpBom> getErpBomBySort(Example example,int pageNumber,int pageSize)
{
	PageHelper.startPage(pageNumber, pageSize);
	List<ErpBom> datalist=erpBomMapper.selectByExample(example);
	PageInfo<ErpBom> pageInfo = new PageInfo<ErpBom>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title insertErpBom   
 * @Description TODO 创建BOM清单  
 * @param erpBom
 * @return      
 * int
 */
public int insertErpBom(ErpBom erpBom)
{
	return erpBomMapper.insert(erpBom);
}
/**
 * 
 * @Title updateErpBom   
 * @Description TODO 更新BOM清单信息
 * @param erpBom
 * @param example
 * @return      
 * int
 */
public int updateErpBom(ErpBom erpBom,Example example)
{
	return erpBomMapper.updateByExample(erpBom, example);
}
/**
 * 
 * @Title delErpBom   
 * @Description TODO 删除BOM清单
 * @param erpBom
 * @return      
 * int
 */
public int delErpBom(ErpBom erpBom)
{
	return erpBomMapper.delete(erpBom);
}

/**
 * 
 * @Title selectOne   
 * @Description TODO 按条件查询一条BOM 
 * @param erpBom
 * @return      
 * ErpBom
 */
public ErpBom selectOne(ErpBom erpBom)
{
	return erpBomMapper.selectOne(erpBom);
}

/**
 * 按分类获取BOM树结构
 */

public List<Map<String, Object>> getErpBomTreeBySortId(String sortId, String orgId) {
	// TODO Auto-generated method stub
	return erpBomMapper.getErpBomTreeBySortId(sortId, orgId);
}
/**
 * 获取BOM清单用于SELECT2插件
 */

public List<ErpMateriel> selectBomList2ById(String bomName, String orgId) {
	// TODO Auto-generated method stub
	return erpBomMapper.selectBomList2ById(bomName, orgId);
}

}
