package com.core136.mapper.erp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.erp.ErpBomDetail;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface ErpBomDetailMapper extends MyMapper<ErpBomDetail>{
	/**
	 * 
	 * @Title getBomDetailList   
	 * @Description TODO 查询Bom下的所有物料
	 * @param bomId
	 * @param materielCode
	 * @param orgId
	 * @return      
	 * List<Map<String,Object>>
	 */
public List<Map<String,Object>> getBomDetailList(@Param(value="bomId") String bomId,@Param(value="materielCode") String materielCode,@Param(value="orgId") String orgId);

/**
 * 
 * @Title isExistMaterielCode   
 * @Description TODO 判断当前BOM下是否有相同的物料编码
 * @param bomId
 * @param materielCode
 * @param orgId
 * @return      
 * int
 */
public int isExistMaterielCode(@Param(value="bomId") String bomId,@Param(value="materielCode") String materielCode,@Param(value="orgId") String orgId);

/**
 * 
 * @Title getBomDetailByDetailId   
 * @Description TODO 按bomDetailId获取BOM清单中的物料  
 * @param bomId
 * @param bomDetailId
 * @param orgId
 * @return      
 * int
 */
public Map<String,Object> getBomDetailByDetailId(@Param(value="bomId") String bomId,@Param(value="bomDetailId") String bomDetailId,@Param(value="orgId") String orgId);

/**
 * 
 * @Title getErpBomByBomIdList   
 * @Description TODO 获取BOM清单中的子BOM清单  
 * @param bomId
 * @param materielCode
 * @param orgId
 * @return      
 * List<Map<String,Object>>
 */
public List<Map<String,Object>> getErpBomByBomIdList(@Param(value="bomId") String bomId,@Param(value="bomName") String bomName,@Param(value="orgId") String orgId);


/**
 * 
 * @Title isExistChildBomIdCode   
 * @Description TODO 查询子BOM是否存于现有的BOM中  
 * @param bomId
 * @param childBomId
 * @param orgId
 * @return      
 * int
 */
public int isExistChildBomIdCode(@Param(value="bomId") String bomId,@Param(value="childBomId") String childBomId,@Param(value="orgId") String orgId);


/**
 * 
 * @Title getProductMaterielList   
 * @Description TODO 产品物料详情 
 * @param bomId
 * @param orgId
 * @return      
 * List<Map<String,Object>>
 */
public List<Map<String,Object>> getProductMaterielList(@Param(value="bomId") String bomId,@Param(value="orgId") String orgId);


}
