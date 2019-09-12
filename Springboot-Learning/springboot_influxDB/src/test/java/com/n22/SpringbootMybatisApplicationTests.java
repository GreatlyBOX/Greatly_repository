package com.n22;

import com.n22.data.InfluxDBConnect;
import com.n22.data.LogInfo;
import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {
	private InfluxDBConnect influxDB;
	private String username = "admin";//用户名
	private String password = "admin";//密码
	private String openurl = "http://127.0.0.1:8086";//连接地址
	private String database = "testdb";//数据库
	private String measurement = "disk_free";

	@Before
	public void setUp(){
		//创建 连接
		influxDB = new InfluxDBConnect(username, password, openurl, database);

		influxDB.influxDbBuild();

		influxDB.createRetentionPolicy();

//		influxDB.deleteDB(database);
//		influxDB.createDB(database);
	}



	@Test
	public void testQuery(){//测试数据查询
		String command = "select * from disk_free";
		QueryResult results = influxDB.query(command);
		if(results.getResults() == null){
			return;
		}
		List<LogInfo> lists = new ArrayList<LogInfo>();
		for (Result result : results.getResults()) {

			List<Series> series= result.getSeries();
			for (Series serie : series) {
				Map<String, String> tags = serie.getTags();
				List<List<Object>>  values = serie.getValues();
				List<String> columns = serie.getColumns();
				lists.addAll(getQueryData(columns, values));
			}
		}

//		Assert.assertTrue((!lists.isEmpty()));
//		Assert.assertEquals(2, lists.size());
	}


	@Test
	public void testInert(){

		//measurement 表
		//tags 标签
		//fields 字段
		String measurement="toilet";
		Map<String, String> tags=new HashMap<>();
		tags.put("userid","11111");
		Map<String, Object> fields=new HashMap<>();
		fields.put("code","111");
		influxDB.insert(measurement,tags,fields);
	}

//	@Test
//	public void testQueryWhere(){//tag 列名 区分大小写
//		String command = "select * from sys_code where TAG_CODE='ABC'";
//		QueryResult results = influxDB.query(command);
//
//		if(results.getResults() == null){
//			return;
//		}
//		List<CodeInfo> lists = new ArrayList<CodeInfo>();
//		for (Result result : results.getResults()) {
//
//			List<Series> series= result.getSeries();
//			for (Series serie : series) {
//				List<List<Object>>  values = serie.getValues();
//				List<String> columns = serie.getColumns();
//
//				lists.addAll(getQueryData(columns, values));
//			}
//		}
//
//		Assert.assertTrue((!lists.isEmpty()));
//		Assert.assertEquals(1, lists.size());
//
//		CodeInfo info = lists.get(0);
//
//		Assert.assertEquals(info.getCode(), "ABC");
//
//	}

//	@Test
//	public void deletMeasurementData(){
//		String command = "delete from sys_code where TAG_CODE='ABC'";
//		String err = influxDB.deleteMeasurementData(command);
//		Assert.assertNull(err);
//	}

	/***整理列名、行数据***/
	private List<LogInfo> getQueryData(List<String> columns, List<List<Object>>  values){
		List<LogInfo> lists = new ArrayList<LogInfo>();

		for (List<Object> list : values) {
			LogInfo info = new LogInfo();
			BeanWrapperImpl bean = new BeanWrapperImpl(info);
			for(int i=0; i< list.size(); i++){

				String propertyName = setColumns(columns.get(i));//字段名
				Object value = list.get(i);//相应字段值
				bean.setPropertyValue(propertyName, value);
			}

			lists.add(info);
		}

		return lists;
	}

	/***转义字段***/
	private String setColumns(String column){
		String[] cols = column.split("_");
		StringBuffer sb = new StringBuffer();
		for(int i=0; i< cols.length; i++){
			String col = cols[i].toLowerCase();
			if(i != 0){
				String start = col.substring(0, 1).toUpperCase();
				String end = col.substring(1).toLowerCase();
				col = start + end;
			}
			sb.append(col);
		}
		return sb.toString();
	}


}
