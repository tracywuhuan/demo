package com.xz.es;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;

import org.junit.Test;

import com.xz.es.entity.util.CsvUtil;

public class FunctionTest {

	@Test
	public void testCSV() throws Exception {
		URL url = this.getClass().getResource("/test.csv");
		File file = new File(url.getFile());

		CsvUtil.readWithCsvDozerBeanReader(file);
	}
	
	@Test
	public void testAoiHomeWorkCSV() throws Exception {
		URL url = this.getClass().getResource("/000199_0");
		File file = new File(url.getFile());

		CsvUtil.readWithCsvDozerBeanReaderAoiHomeWork(file);
	}

	@Test
	public void generateCsvSample() throws FileNotFoundException, UnsupportedEncodingException {

		int count = 1000;
		URL url = this.getClass().getResource("/test.csv");
		File file = new File(url.getFile());
		PrintWriter printWriter = new PrintWriter(file,"UTF-8");
		printWriter.println("id,title,category,brand,price,images,lat,lon");
		for (int i = 1; i <= count; i++) {
			String lat_lon = randomLonLat(116.192125, 116.523276, 39.822785, 40.169467);
			System.out.println(lat_lon);
			printWriter.println(i+","+"小米之家"+i+","+"线下店,小米,20000,http://image.baidu.com/13123.jpg,"+lat_lon);
		}
		printWriter.close();
	}
	
	/**
	    * @Title: randomLonLat
	    * @Description: 在矩形内随机生成经纬度
	    * @param MinLon：最小经度  MaxLon： 最大经度   MinLat：最小纬度   MaxLat：最大纬度
	    * @return
	    * @throws
	    */
	  public String randomLonLat(double MinLon, double MaxLon, double MinLat, double MaxLat) {
	    BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
	    String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
	    db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
	    String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
	    return lat+","+lon;
	  }

}
