/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ProjectBuildArtificialSortMapper.java   
 * @Package com.core136.mapper.projectbuild   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月28日 上午9:52:27   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildArtificialSort;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  ProjectBuildArtificialSortMapper   
 * @Description:TODO 工程人工分类
 * @author: 稠云信息 
 * @date:   2019年9月28日 上午9:52:27   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface ProjectBuildArtificialSortMapper extends MyMapper<ProjectBuildArtificialSort>{
	/**
	 * 
	* @Title: getProjectBuildArtificialSortTree 
	* @Description: TODO 获取工程人工分类树结构
	* @param @param orgId
	* @param @param sortLeave
	* @param @return 设定文件 
	* @return List<Map<String,String>> 返回类型 

	 */
public List<Map<String,String>>getProjectBuildArtificialSortTree(@Param(value="orgId") String orgId,@Param(value="sortLeave") String sortLeave);
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
