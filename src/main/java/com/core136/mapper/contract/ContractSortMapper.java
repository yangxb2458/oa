/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ContractSort.java   
 * @Package com.core136.mapper.contract   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月23日 上午11:18:23   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.contract.ContractSort;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  ContractSort   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月23日 上午11:18:23   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface ContractSortMapper extends MyMapper<ContractSort>{
	/**
	 * 
	* @Title: getContractSortTree 
	* @Description: TODO 获取合同的分类树结构
	* @param @param orgId
	* @param @param sortLeave
	* @param @return 设定文件 
	* @return List<Map<String,String>> 返回类型 

	 */
public List<Map<String,String>>getContractSortTree(@Param(value="orgId") String orgId,@Param(value="sortLeave") String sortLeave);
/**
 * 
* @Title: isExistChild 
* @Description: TODO 判断是否还有子集
* @param @param orgId
* @param @param sortId
* @param @return 设定文件 
* @return int 返回类型 

 */
public int isExistChild(@Param(value="orgId") String orgId,@Param(value="sortId") String sortId);

}
