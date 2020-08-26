package com.core136.service.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.core136.bean.account.Account;
import com.core136.bean.erp.ErpMaterielSort;
import com.core136.mapper.erp.ErpMaterielSortMapper;
import org.core136.common.retdataunit.RetDataBean;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @ClassName:  ErpMaterielSortService   
 * @Description:TODO ERP物料分类数据库操作服务类
 * @author: 稠云信息
 * @date:   2018年12月7日 上午10:47:56   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ErpMaterielSortService{
@Autowired
private ErpMaterielSortMapper erpMaterielSortMapper;
@Autowired
private JdbcTemplate jdbcTemplate;
/**
 * 
 * @Title: insertMaterielSort   
 * @Description: TODO 添加ERP物料分类
 * @param: erpMaterielSort
 * @param: @return      
 * @return: int      

 */
	public int insertErpMaterielSort(ErpMaterielSort erpMaterielSort)
	{
		return erpMaterielSortMapper.insert(erpMaterielSort);
	}
	/**
	 * 
	 * @Title: selectOne   
	 * @Description: TODO 按条件查询一个分类信息  
	 * @param: erpMaterielSort
	 * @param: @return      
	 * @return: ErpMaterielSort      

	 */
	public ErpMaterielSort selectOne(ErpMaterielSort erpMaterielSort)
	{
		return erpMaterielSortMapper.selectOne(erpMaterielSort);
	}
	/**
	 * 获取物料分类的顶层分类
	 */
	
	public List<Map<String,Object>> getErpMaterielSortTree(String sortLeave,String orgId) {
		// TODO Auto-generated method stub
		return erpMaterielSortMapper.getErpMaterielSortTree(sortLeave,orgId);
	}
	/**
	 * 判断当前分类下是否还有子分类，大于0再说明下面有子分类,小于0再分类下没有子分类
	 */
	
	public int isExistChild(String sortId, String orgId) {
		// TODO Auto-generated method stub
		return erpMaterielSortMapper.isExistChild(sortId, orgId);
	}
	/**
	 * 
	 * @Title: delErpMaterielSort   
	 * @Description: TODO 删除分类   
	 * @param: erpMaterielSort
	 * @param: @return      
	 * @return: int      

	 */
	public int delErpMaterielSort(ErpMaterielSort erpMaterielSort)
	{
		return erpMaterielSortMapper.delete(erpMaterielSort);
	}
	/**
	 * 
	 * @Title: updateErpMaterielSort   
	 * @Description: TODO 更新物料分类
	 * @param: erpMaterielSort
	 * @param: example
	 * @param: @return      
	 * @return: int      

	 */
	public int updateErpMaterielSort(ErpMaterielSort erpMaterielSort,Example example)
	{
		return erpMaterielSortMapper.updateByExample(erpMaterielSort, example);
	}
	
	
	/**
	 * 
	 * @Title importMaterielSort   
	 * @Description TODO 物料分类导入
	 * @param request
	 * @return      
	 * int
 ServletException 
 FileUploadException 
	 */
	public RetDataBean importMaterielSort(MultipartFile file,Account account)
	{
//		try {
//			//List<String[]> list=ExcelUtil.readExcel(file);
//			//jdbcTemplate.execute("insert into erp_test(dm1,mc1,materiel_code) values('"+list.get(0)[0]+"','"+list.get(0)[1]+"','"+list.get(0)[14]+"')");
//			//System.out.println(list.size());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return null;
	}
}
