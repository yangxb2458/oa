package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrClassCode;
import com.core136.mapper.hr.HrClassCodeMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrClassCodeService {
@Autowired
private HrClassCodeMapper hrClassCodeMapper;

public int insertHrClassCode(HrClassCode hrclassCode)
{
	return hrClassCodeMapper.insert(hrclassCode);
}

public int deleteHrClassCode(HrClassCode hrClassCode)
{
	return hrClassCodeMapper.delete(hrClassCode);
}

public int updateHrClassCode(Example example,HrClassCode hrClassCode)
{
	return hrClassCodeMapper.updateByExampleSelective(hrClassCode, example);
}

public HrClassCode selectOneHrClassCode(HrClassCode hrClassCode)
{
	return hrClassCodeMapper.selectOne(hrClassCode);
}
/**
 * 
 * @Title: getCodeListByModule   
 * @Description: TODO  获取下拉列表
 * @param orgId
 * @param module
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getCodeListByModule(String orgId,String module)
{
	return hrClassCodeMapper.getCodeListByModule(orgId, module);
}

/**
 * 
 * @Title: getAllParentCodeList   
 * @Description: TODO 获取所有的主分类
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getAllParentCodeList(String orgId)
{
	return hrClassCodeMapper.getAllParentCodeList(orgId);
}

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
public List<Map<String, String>>getHrClassCodeName(String orgId,String module,String codeValue)
{
	return hrClassCodeMapper.getHrClassCodeName(orgId, module, codeValue);
}

}
