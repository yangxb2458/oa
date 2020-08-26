package com.core136.service.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.oa.DiaryPriv;
import com.core136.mapper.oa.DiaryPrivMapper;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  DiaryPrivService   
 * @Description:TODO 工作日志权限设置
 * @author: 稠云技术 
 * @date:   2019年7月6日 下午11:38:13   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class DiaryPrivService {
@Autowired
private DiaryPrivMapper diaryPrivMapper;
/**
 * 
* @Title: setDiaryPriv 
* @Description: TODO 设置工作日志权限
* @param @param diaryPriv
* @param @return 设定文件 
* @return int 返回类型
 */
public int setDiaryPriv(DiaryPriv diaryPriv)
{
	DiaryPriv newDiaryPriv = new DiaryPriv();
	newDiaryPriv.setOrgId(diaryPriv.getOrgId());
	if(isExist(newDiaryPriv)>0)
	{
		Example example = new Example(DiaryPriv.class);
		example.createCriteria().andEqualTo("orgId",diaryPriv.getOrgId());
		return this.updateDiaryPriv(diaryPriv,example);
	}else
	{
		return this.insertDiaryPriv(diaryPriv);
	}
}


public DiaryPriv selectOneDiaryPriv(DiaryPriv diaryPriv)
{
	return diaryPrivMapper.selectOne(diaryPriv);
}

private int insertDiaryPriv(DiaryPriv diaryPriv)
{
	return diaryPrivMapper.insert(diaryPriv);
}

private int updateDiaryPriv(DiaryPriv diaryPriv,Example example)
{
	return diaryPrivMapper.updateByExampleSelective(diaryPriv, example);
}

private int isExist(DiaryPriv diaryPriv)
{
	return diaryPrivMapper.selectCount(diaryPriv);
}

}
