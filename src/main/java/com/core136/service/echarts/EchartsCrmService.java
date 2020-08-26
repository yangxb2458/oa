package com.core136.service.echarts;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.mapper.echarts.EchartsCrmMapper;

import cyunsoft.bi.option.bean.OptionConfig;
import cyunsoft.bi.option.property.OptionSeries;
import cyunsoft.bi.option.property.OptionTitle;
import cyunsoft.bi.option.resdata.LegendData;
import cyunsoft.bi.option.resdata.SeriesData;
import cyunsoft.bi.option.style.Emphasis;
import cyunsoft.bi.option.style.ItemStyle;
import cyunsoft.bi.option.units.PieOption;

@Service
public class EchartsCrmService {
private PieOption pieOption = new PieOption();
@Autowired
EchartsCrmMapper echartsCrmMapper;

/**
 * 
 * @Title: getBiCustomerLevelPie   
 * @Description: TODO 获取CRM客户等级占比
 * @param account
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getBiCustomerLevelPie(Account account)
{
	OptionConfig optionConfig = new OptionConfig();
	List<Map<String, String>> resdataList = echartsCrmMapper.getBiCustomerLevelPie(account.getOrgId());
	OptionSeries[] optionSeriesArr = new OptionSeries[1];
	SeriesData[] dataArr = new SeriesData[resdataList.size()];
	int selectedLeng=0;
	if(dataArr.length>=10)
	{
		selectedLeng=10;
	}else
	{
		selectedLeng = dataArr.length;
	}
	String[] selected = new String[selectedLeng];
	LegendData[] legendDatas = new LegendData[dataArr.length];
	for(int i=0;i<dataArr.length;i++)
	{
		if(StringUtils.isBlank(resdataList.get(i).get("name")))
		{
			resdataList.get(i).put("name", "other"+i);
		}
		if(i<selectedLeng)
		{
			selected[i]=resdataList.get(i).get("name").toString();
		}
		LegendData legendData = new LegendData();
		legendData.setName(resdataList.get(i).get("name").toString());
		legendDatas[i]=legendData;
		SeriesData seriesData = new SeriesData();
		seriesData.setName(resdataList.get(i).get("name").toString());
		seriesData.setValue(Double.valueOf(String.valueOf(resdataList.get(i).get("value"))));
		dataArr[i]=seriesData;
	}
	OptionSeries optionSeries = new OptionSeries();
	optionSeries.setName("等级");
	optionSeries.setType("pie");
	optionSeries.setRadius("55%");
	optionSeries.setCenter(new String[]{"40%", "50%"});
	Emphasis emphasis = new Emphasis();
	ItemStyle itemStyle = new ItemStyle();
	itemStyle.setShadowBlur(10);
	itemStyle.setShadowOffsetX(0);
	itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
	emphasis.setItemStyle(itemStyle);
	optionSeries.setData(dataArr);
	optionSeriesArr[0] = optionSeries;
	optionConfig.setSeries(optionSeriesArr);
	optionConfig = pieOption.getPieLegendChartOption(legendDatas,selected,optionSeriesArr);
	OptionTitle optionTitle = new OptionTitle();
	optionTitle.setText("客户等级数据统计");
	optionTitle.setSubtext("客户等级占比");
	optionTitle.setLeft("center");
	optionConfig.setTitle(optionTitle);
	return optionConfig;
}

/**
 * 
 * @Title: getBiCustomerAreaPie   
 * @Description: TODO  获取CRM地区占比
 * @param account
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getBiCustomerAreaPie(Account account)
{
	OptionConfig optionConfig = new OptionConfig();
	List<Map<String, String>> resdataList = echartsCrmMapper.getBiCustomerAreaPie(account.getOrgId());
	OptionSeries[] optionSeriesArr = new OptionSeries[1];
	SeriesData[] dataArr = new SeriesData[resdataList.size()];
	int selectedLeng=0;
	if(dataArr.length>=10)
	{
		selectedLeng=10;
	}else
	{
		selectedLeng = dataArr.length;
	}
	String[] selected = new String[selectedLeng];
	LegendData[] legendDatas = new LegendData[dataArr.length];
	for(int i=0;i<dataArr.length;i++)
	{
		if(StringUtils.isBlank(resdataList.get(i).get("name")))
		{
			resdataList.get(i).put("name", "other"+i);
		}
		if(i<selectedLeng)
		{
			selected[i]=resdataList.get(i).get("name").toString();
		}
		LegendData legendData = new LegendData();
		legendData.setName(resdataList.get(i).get("name").toString());
		legendDatas[i]=legendData;
		SeriesData seriesData = new SeriesData();
		seriesData.setName(resdataList.get(i).get("name").toString());
		seriesData.setValue(Double.valueOf(String.valueOf(resdataList.get(i).get("value"))));
		dataArr[i]=seriesData;
	}
	OptionSeries optionSeries = new OptionSeries();
	optionSeries.setName("地区");
	optionSeries.setType("pie");
	optionSeries.setRadius("55%");
	optionSeries.setCenter(new String[]{"40%", "50%"});
	Emphasis emphasis = new Emphasis();
	ItemStyle itemStyle = new ItemStyle();
	itemStyle.setShadowBlur(10);
	itemStyle.setShadowOffsetX(0);
	itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
	emphasis.setItemStyle(itemStyle);
	optionSeries.setData(dataArr);
	optionSeriesArr[0] = optionSeries;
	optionConfig.setSeries(optionSeriesArr);
	optionConfig = pieOption.getPieLegendChartOption(legendDatas,selected,optionSeriesArr);
	OptionTitle optionTitle = new OptionTitle();
	optionTitle.setText("客户地区数据统计");
	optionTitle.setSubtext("客户地区数量占比");
	optionTitle.setLeft("center");
	optionConfig.setTitle(optionTitle);
	return optionConfig;
}

/**
 * 
 * @Title: getBiCustomerKeepUserPie   
 * @Description: TODO 获取CRM销售人员的占比
 * @param account
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getBiCustomerKeepUserPie(Account account)
{
	OptionConfig optionConfig = new OptionConfig();
	List<Map<String, String>> resdataList = echartsCrmMapper.getBiCustomerKeepUserPie(account.getOrgId());
	OptionSeries[] optionSeriesArr = new OptionSeries[1];
	SeriesData[] dataArr = new SeriesData[resdataList.size()];
	int selectedLeng=0;
	if(dataArr.length>=10)
	{
		selectedLeng=10;
	}else
	{
		selectedLeng = dataArr.length;
	}
	String[] selected = new String[selectedLeng];
	LegendData[] legendDatas = new LegendData[dataArr.length];
	for(int i=0;i<dataArr.length;i++)
	{
		if(StringUtils.isBlank(resdataList.get(i).get("name")))
		{
			resdataList.get(i).put("name", "other"+i);
		}
		if(i<selectedLeng)
		{
			selected[i]=resdataList.get(i).get("name").toString();
		}
		LegendData legendData = new LegendData();
		legendData.setName(resdataList.get(i).get("name").toString());
		legendDatas[i]=legendData;
		SeriesData seriesData = new SeriesData();
		seriesData.setName(resdataList.get(i).get("name").toString());
		seriesData.setValue(Double.valueOf(String.valueOf(resdataList.get(i).get("value"))));
		dataArr[i]=seriesData;
	}
	OptionSeries optionSeries = new OptionSeries();
	optionSeries.setName("业务员");
	optionSeries.setType("pie");
	optionSeries.setRadius("55%");
	optionSeries.setCenter(new String[]{"40%", "50%"});
	Emphasis emphasis = new Emphasis();
	ItemStyle itemStyle = new ItemStyle();
	itemStyle.setShadowBlur(10);
	itemStyle.setShadowOffsetX(0);
	itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
	emphasis.setItemStyle(itemStyle);
	optionSeries.setData(dataArr);
	optionSeriesArr[0] = optionSeries;
	optionConfig.setSeries(optionSeriesArr);
	optionConfig = pieOption.getPieLegendChartOption(legendDatas,selected,optionSeriesArr);
	OptionTitle optionTitle = new OptionTitle();
	optionTitle.setText("业务员客户数量统计");
	optionTitle.setSubtext("客户数量数据占比");
	optionTitle.setLeft("center");
	optionConfig.setTitle(optionTitle);
	return optionConfig;
}


/**
 * 
 * @Title: getARAPOptionConfig   
 * @Description: TODO 获取财务门户的收支
 * @param account
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getBiCustomerIndustryPie(Account account)
{
	
	OptionConfig optionConfig = new OptionConfig();
	List<Map<String, String>> resdataList = echartsCrmMapper.getBiCustomerIndustryPie(account.getOrgId());
	OptionSeries[] optionSeriesArr = new OptionSeries[1];
	SeriesData[] dataArr = new SeriesData[resdataList.size()];
	int selectedLeng=0;
	if(dataArr.length>=10)
	{
		selectedLeng=10;
	}else
	{
		selectedLeng = dataArr.length;
	}
	String[] selected = new String[selectedLeng];
	LegendData[] legendDatas = new LegendData[dataArr.length];
	for(int i=0;i<dataArr.length;i++)
	{
		if(StringUtils.isBlank(resdataList.get(i).get("name")))
		{
			resdataList.get(i).put("name", "other"+i);
		}
		if(i<selectedLeng)
		{
			selected[i]=resdataList.get(i).get("name").toString();
		}
		LegendData legendData = new LegendData();
		legendData.setName(resdataList.get(i).get("name").toString());
		legendDatas[i]=legendData;
		SeriesData seriesData = new SeriesData();
		seriesData.setName(resdataList.get(i).get("name").toString());
		seriesData.setValue(Double.valueOf(String.valueOf(resdataList.get(i).get("value"))));
		dataArr[i]=seriesData;
	}
	OptionSeries optionSeries = new OptionSeries();
	optionSeries.setName("行业");
	optionSeries.setType("pie");
	optionSeries.setRadius("55%");
	optionSeries.setCenter(new String[]{"40%", "50%"});
	Emphasis emphasis = new Emphasis();
	ItemStyle itemStyle = new ItemStyle();
	itemStyle.setShadowBlur(10);
	itemStyle.setShadowOffsetX(0);
	itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
	emphasis.setItemStyle(itemStyle);
	optionSeries.setData(dataArr);
	optionSeriesArr[0] = optionSeries;
	optionConfig.setSeries(optionSeriesArr);
	optionConfig = pieOption.getPieLegendChartOption(legendDatas,selected,optionSeriesArr);
	OptionTitle optionTitle = new OptionTitle();
	optionTitle.setText("客户行业数据统计");
	optionTitle.setSubtext("客户行业数据占比");
	optionTitle.setLeft("center");
	optionConfig.setTitle(optionTitle);
	return optionConfig;
}
	
}
