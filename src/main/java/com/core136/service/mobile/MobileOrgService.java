package com.core136.service.mobile;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @ClassName:  MobileOrgService
 * @Description:TODO 移动移获取组织机信息
 * @author: 稠云技术 
 * @date:   2020年8月2日 下午3:31:47     
 * @Copyright: 2020 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core136.bean.account.UnitDept;
import com.core136.service.account.UnitDeptService;
import com.core136.service.account.UserInfoService;

import tk.mybatis.mapper.entity.Example;
@Service
public class MobileOrgService {
	@Autowired
	private UnitDeptService unitDeptService;
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 
	 * @Title: getDeptAndUserInfoForMobile   
	 * @Description: TODO 获取APP组织机
	 * @param orgId
	 * @param deptId
	 * @return
	 * JSONArray    
	 * @throws
	 */
	public JSONArray getDeptAndUserInfoForMobile(String orgId,String deptId)
	{
		JSONArray jsonArray = new JSONArray();
		Example example = new Example(UnitDept.class);
		example.createCriteria().andEqualTo("orgId",orgId).andEqualTo("orgLeaveId",deptId);
		List<UnitDept> deptlist = unitDeptService.getDeptList(example);
		for(int i=0;i<deptlist.size();i++)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", deptlist.get(i).getDeptId());
			jsonObject.put("text", deptlist.get(i).getDeptName());
			jsonObject.put("isParent", true);
			jsonArray.add(jsonObject);
		}
		
		List<Map<String, Object>> userList = userInfoService.getSelectUserByDeptId(deptId,orgId);
		for(int i=0;i<userList.size();i++)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", userList.get(i).get("accountId"));
			jsonObject.put("text", userList.get(i).get("userName"));
			jsonObject.put("sex", userList.get(i).get("sex"));
			jsonObject.put("headImg", userList.get(i).get("headImg"));
			jsonObject.put("isParent", false);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
}

