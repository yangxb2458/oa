package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrClassCode;

@Mapper
public interface HrClassCodeMapper extends MyMapper<HrClassCode>{

	/**
	 * 
	 * @Title: getCodeListByModule   
	 * @Description: TODO 获取下拉列表
	 * @param orgId
	 * @param module
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getCodeListByModule(@Param(value="orgId")String orgId,@Param(value="module") String module);
	/**
	 * 
	 * @Title: getAllParentCodeList   
	 * @Description: TODO 获取所有的主分类
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getAllParentCodeList(@Param(value="orgId")String orgId);
	/**
	 * 
	 * @Title: getHrClassCodeName   
	 * @Description: TODO 获取分类码名称
	 * @param orgId
	 * @param module
	 * @param codeValue
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrClassCodeName(@Param(value="orgId")String orgId,@Param(value="module")String module,@Param(value="codeValue")String codeValue);
}
