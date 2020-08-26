package com.core136.service.crm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.crm.CrmPriv;
import com.core136.mapper.crm.CrmPrivMapper;
import com.core136.service.account.AccountService;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  CrmPrivService   
 * @Description:TODO CRM权限
 * @author: 稠云信息
 * @date:   2019年3月6日 下午12:10:06   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class CrmPrivService{
@Autowired
private CrmPrivMapper crmPrivMapper;
@Autowired
private AccountService accountService;


public int insertCrmPriv(CrmPriv crmPriv)
{
	return crmPrivMapper.insert(crmPriv);
}


public int updateCrmPriv(CrmPriv crmPriv,Example example)
{
	return crmPrivMapper.updateByExampleSelective(crmPriv, example);
}

public CrmPriv selectOneCrmPriv(CrmPriv crmPriv)
{
	return crmPrivMapper.selectOne(crmPriv);
}

public int selectCount(CrmPriv crmPriv)
{
	return crmPrivMapper.selectCount(crmPriv);
}

public int deleteCrmPriv(CrmPriv crmPriv)
{
	return crmPrivMapper.delete(crmPriv);
}
/**
 * 
* @Title: getAllSender 
* @Description: TODO 获取所有销售业务人员,包含经理
* @param @param orgId
* @param @return 设定文件 
* @return List<Map<String,String>> 返回类型 

 */
public List<Map<String,String>> getAllSender(String orgId,String search)
{
	CrmPriv crmPriv = new CrmPriv();
	crmPriv.setOrgId(orgId);
	crmPriv = this.selectOneCrmPriv(crmPriv);
	String accountS = "";
	if(StringUtils.isNotBlank(crmPriv.getManager()))
	{
		accountS+=","+crmPriv.getManager();
	}
	if(StringUtils.isNotBlank(crmPriv.getSender()))
	{
		accountS+=","+crmPriv.getSender();
	}
	if(StringUtils.isNotBlank(crmPriv.getSale()))
	{
		accountS+=","+crmPriv.getSale();
	}
	String[] accountId = null;
	if(StringUtils.isNotBlank(accountS))
	{
		if(accountS.startsWith(","))
		{
			accountS = accountS.substring(1,accountS.length());
		}
		if(accountS.endsWith(","))
		{
			accountS = accountS.substring(0,accountS.length()-1);
		}
		if(accountS.indexOf(",")>-1)
		{
		accountId=accountS.split(",");
		}else
		{
			accountId = new String[] {accountS};
		}
	}
	List<String> list = new ArrayList<String>();
	for(int i=0;i<accountId.length;i++)
	{
		if(StringUtils.isNotBlank(accountId[i]))
		{
		list.add(accountId[i]);
		}
	}
	return accountService.getSelect2UserListByIds(list,orgId,search);
}

}
