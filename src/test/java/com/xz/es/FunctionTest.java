package com.xz.es;


import java.io.File;
import java.net.URL;

import org.junit.Test;

import com.xz.es.entity.util.CsvUtil;

public class FunctionTest {

	@Test
	public void test() {
		try {
			URL url = this.getClass().getResource("/test.csv");
			File file = new File(url.getFile());

			CsvUtil.readWithCsvDozerBeanReader(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
