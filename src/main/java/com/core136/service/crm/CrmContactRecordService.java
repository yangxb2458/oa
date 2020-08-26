package com.core136.service.crm;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core136.bean.crm.CrmContactRecord;
import com.core136.bean.crm.CrmCustomer;
import com.core136.bean.oa.Calendar;
import com.core136.mapper.crm.CrmContactRecordMapper;
import com.core136.mapper.oa.CalendarMapper;
import org.core136.common.utils.SysTools;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  CrmContactRecordService   
 * @Description:TODO 客户联系记录
 * @author: 稠云信息
 * @date:   2019年2月12日 下午5:10:37   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class CrmContactRecordService{
@Autowired
private CrmContactRecordMapper crmContactRecordMapper;
@Autowired
private CalendarMapper calendarMapper;
@Autowired
private CrmCustomerService CrmCustomerService;

public CrmContactRecord selectOne(CrmContactRecord crmContactRecord)
{
	return crmContactRecordMapper.selectOne(crmContactRecord);
}

public int delectCrmContactRecord(CrmContactRecord crmContactRecord)
{
	return crmContactRecordMapper.delete(crmContactRecord);
}

public int updateCrmContactRecord(CrmContactRecord crmContactRecord,Example example)
{
	return crmContactRecordMapper.updateByExampleSelective(crmContactRecord, example);
}

public int insertCrmContactRecord(CrmContactRecord crmContactRecord)
{
	return crmContactRecordMapper.insert(crmContactRecord);
}
/**
 * 
 * @Title addRecordAndCalendar   
 * @Description TODO 添加联系记录并处理下次联系加入个人日程
 * @param crmContactRecord
 * @return      
 * int
 */
@Transactional(value="generalTM")
public int addRecordAndCalendar(CrmContactRecord crmContactRecord)
{
	if(StringUtils.isNotBlank(crmContactRecord.getNextVisit()))
	{
		CrmCustomer crmCustomer = new CrmCustomer();
		crmCustomer.setCustomerId(crmContactRecord.getCustomerId());
		crmCustomer.setOrgId(crmContactRecord.getOrgId());
		crmCustomer=CrmCustomerService.selectOne(crmCustomer);
		Calendar calendar = new Calendar();
		calendar.setCalendarId(SysTools.getGUID());
		if(StringUtils.isNotBlank(crmCustomer.getCnName()))
		{
			calendar.setContent("回访"+crmCustomer.getEnName());
		}else
		{
			calendar.setContent("回访"+crmCustomer.getCnName());
		}
		calendar.setStart(crmContactRecord.getNextVisit()+" 08:00:00");
		calendar.setEnd(crmContactRecord.getNextVisit()+" 23:59:59");
		calendar.setType("1");
		calendar.setAccountId(crmContactRecord.getCreateUser());
		calendar.setUrl("/app/core/crm/customerdetails?customerId="+crmContactRecord.getCustomerId());
		calendar.setOrgId(crmContactRecord.getOrgId());
		calendarMapper.insert(calendar);
	}
	return crmContactRecordMapper.insert(crmContactRecord);
}


/**
 * 
 * @Title getRecordByCustomerId   
 * @Description TODO 获把指定企业的联系记录
 * @param example
 * @return      
 * List<CrmContactRecord>
 */
public List<CrmContactRecord> getRecordByCustomerId(Example example)
{
	return crmContactRecordMapper.selectByExample(example);
}
/**
 * 获把指定企业的联系记录
 */

public List<Map<String, Object>> getRecordListByCustomerId(String orgId, String customerId) {
	// TODO Auto-generated method stub
	return crmContactRecordMapper.getRecordListByCustomerId(orgId, customerId);
}

public PageInfo<Map<String, Object>> getRecordListByCustomerId(int pageNumber,int pageSize,String orgId,String customerId)
{
	PageHelper.startPage(pageNumber, pageSize);
	List<Map<String, Object>> datalist=crmContactRecordMapper.getRecordListByCustomerId(orgId, customerId);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(datalist);
	return pageInfo;
}

}
