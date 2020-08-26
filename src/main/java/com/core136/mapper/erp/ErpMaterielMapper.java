package com.core136.mapper.erp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.erp.ErpMateriel;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface ErpMaterielMapper extends MyMapper<ErpMateriel>{

	/**
	 * 
	 * @Title isExistMaterielById   
	 * @Description TODO 按物料Id判断物料是否存在
	 * @param materielId
	 * @param orgId
	 * @return      
	 * int
	 */
	public int isExistMaterielById(@Param(value="materielId") String materielId,@Param(value="orgId") String orgId);
	
	/**
	 * 
	 * @Title select2ById   
	 * @Description TODO 按物料ID的模糊查询，用于SELECT2插件的选择
	 * @param materielId
	 * @param orgId
	 * @return      
	 * List<ErpMateriel>
	 */
	public List<ErpMateriel> selectMateriel2ById(@Param(value="materielCode") String materielCode,@Param(value="orgId") String orgId);
	
	/**
	 * 
	 * @Title selectOneByCode   
	 * @Description TODO 控物料编码查询物料 
	 * @param materielCode
	 * @param orgId
	 * @return      
	 * ErpMateriel
	 */
	public ErpMateriel selectOneByCode(@Param(value="materielCode") String materielCode,@Param(value="orgId") String orgId);
	
	
}
