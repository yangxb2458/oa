package com.core136.service.echarts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.mapper.echarts.EchartsFinanceMapper;

import cyunsoft.bi.option.bean.OptionConfig;
import cyunsoft.bi.option.property.OptionLegend;
import cyunsoft.bi.option.property.OptionSeries;
import cyunsoft.bi.option.property.OptionTooltip;
import cyunsoft.bi.option.resdata.Data;
import cyunsoft.bi.option.resdata.LegendData;
import cyunsoft.bi.option.resdata.SeriesData;
import cyunsoft.bi.option.units.BarOption;
import cyunsoft.bi.option.style.AxisPointer;
import cyunsoft.bi.option.style.Label;

@Service
public class EchartsFinanceService {
private BarOption barOption = new BarOption();
@Autowired
EchartsFinanceMapper echartsFinanceMapper;

/**
 * 
 * @Title: getARAPOptionConfig   
 * @Description: TODO 获取财务门户的收支
 * @param account
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getARAPOptionConfig(Account account)
{
	
	OptionConfig optionConfig = new OptionConfig();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
	 Date d = new Date();
	 GregorianCalendar gc = new GregorianCalendar();
	 gc.setTime(d);
	 gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
	 String beginTime = SysTools.getTime("yyyy-MM");
	 Data[] yData = new Data[6];
	 Data dataYdata = new Data();
	 dataYdata.setValue(beginTime);
	 yData[0]=dataYdata;
	 for(int i=1;i<=5;i++)
	 {
		 gc.add(2, 1);
		 dataYdata = new Data();
		 dataYdata.setValue(sf.format(gc.getTime()));
		 yData[i]=dataYdata;
	 }
	 String endTime = sf.format(gc.getTime());
	 System.out.println(endTime);
	 OptionSeries[] optionSeries = new OptionSeries[3];
	 List<Map<String, Double>> data1List= echartsFinanceMapper.getReceviablesListData(account.getOrgId(),beginTime, endTime);
	 SeriesData[] data1 = new SeriesData[data1List.size()];
	 for(int i=0;i<data1.length;i++)
	 {
		 SeriesData seriesData = new SeriesData();
		 seriesData.setValue(data1List.get(i).get("total"));
		 data1[i]=seriesData;
	 }
	 
	 List<Map<String, Double>> data2List= echartsFinanceMapper.getPayableListData(account.getOrgId(),beginTime, endTime);
	 SeriesData[] data2 = new SeriesData[data2List.size()];
	 for(int i=0;i<data2.length;i++)
	 {
		 SeriesData seriesData = new SeriesData();
		 seriesData.setValue(data2List.get(i).get("total"));
		 data2[i]=seriesData;
	 }
	 
	 int data0Length = data1.length>=data2.length?data1.length:data2.length;
	 SeriesData[] data0 = new SeriesData[data0Length];
	 for(int i=0;i<data0Length;i++)
	 {
		 Double a = 0.0;
		 try {
			 a=data1[i].getValue();
		 }catch (Exception e) {
		}
		 Double b = 0.0;
		 try {
			 b=data2[i].getValue();
		 }catch (Exception e) {
		}
		 SeriesData seriesData = new SeriesData();
		 seriesData.setValue(a+b);
		 data0[i]=seriesData;
	 }
	 Label label = new Label();
	 label.setShow(true);
	 label.setPosition("inside");
	 OptionSeries optionSeries0 = new OptionSeries();
	 optionSeries0.setName("利润");
	 optionSeries0.setType("bar");
	 optionSeries0.setLabel(label);
	 optionSeries0.setData(data0);
	 optionSeries[0]=optionSeries0;
	 Label label1 = new Label();
	 label1.setShow(true);
	 OptionSeries optionSeries1 = new OptionSeries();
	 optionSeries1.setName("收入");
	 optionSeries1.setType("bar");
	 optionSeries1.setStack("总量");
	 optionSeries1.setLabel(label1);
	 optionSeries1.setData(data1);
	 optionSeries[1]=optionSeries1;
	 Label label2 = new Label();
	 label2.setShow(true);
	 label2.setPosition("left");
	 OptionSeries optionSeries2 = new OptionSeries();
	 optionSeries2.setName("支出");
	 optionSeries2.setType("bar");
	 optionSeries2.setStack("总量");
	 optionSeries2.setLabel(label2);
	 optionSeries2.setData(data2);
	 optionSeries[2]=optionSeries2;
	 optionConfig = barOption.getBarNegativeChartOption(yData, optionSeries);
	 OptionLegend optionLegend = new OptionLegend();
	 LegendData[] legendDatas = new LegendData[3];
	 LegendData legendData0 = new LegendData();
	 legendData0.setName("利润");
	 legendDatas[0]=legendData0;
	 LegendData legendData1 = new LegendData();
	 legendData1.setName("支出");
	 legendDatas[1]=legendData1;
	 LegendData legendData2 = new LegendData();
	 legendData2.setName("收入");
	 legendDatas[2]=legendData2;
	 optionLegend.setData(legendDatas);
	 optionConfig.setLegend(optionLegend);
	 OptionTooltip optionTooltip = new OptionTooltip();
	 optionTooltip.setTrigger("axis");
	 AxisPointer axisPointer = new AxisPointer();
	 axisPointer.setType("shadow");
	 optionTooltip.setAxisPointer(axisPointer);
	 optionConfig.setTooltip(optionTooltip);
	return optionConfig;
}
	/**
	 * 
	 * @Title: getPayReceivTotalData   
	 * @Description: TODO 获取应收应付总数
	 * @param orgId
	 * @return
	 * Map<String,String>  
	 * @throws
	 */
public Map<String, String>getPayReceivTotalData(String orgId)
{
	return echartsFinanceMapper.getPayReceivTotalData(orgId);
}
}
