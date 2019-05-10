package com.xz.es;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.xz.es.service.impl.ItemServiceImpl;
import com.xz.es.entity.Item;
import com.xz.es.entity.Item.MyGeoPoint;
import com.xz.es.entity.util.CsvUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private ItemServiceImpl itemServiceImpl;

	//@Test
	public void contextLoads() {
	}

	@Test
	public void createIndex() {
		elasticsearchTemplate.createIndex(Item.class);
		elasticsearchTemplate.putMapping(Item.class);
	}

	@Test
	public void deleteIndex() {
		elasticsearchTemplate.deleteIndex(Item.class);
	}

	@Test
	public void insert() {
		MyGeoPoint gp = new MyGeoPoint(40.035627, 116.342776);
		Item item = new Item(1L, "小米之家", "线下店1", "小米", 200000.00, "http://image.baidu.com/13123.jpg", gp);
		itemServiceImpl.insertItem(item);
		
		gp = new MyGeoPoint(39.82058, 116.373229);
		item = new Item(2L, "小米之家", "线下店2", "小米", 200000.00, "http://image.baidu.com/13123.jpg", gp);
		itemServiceImpl.insertItem(item);
	}
	
	@Test
	public void insertItemsFromCsv() {
		try {
			URL url = this.getClass().getResource("/test.csv");
			File file = new File(url.getFile());

			List<Item> items = CsvUtil.readWithCsvDozerBeanReader(file);
			itemServiceImpl.insertItems(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void getItemByLocationDistance() {
		
		Pageable pageable = PageRequest.of(0, 10);
		GeoPoint gp = new GeoPoint(40.035627, 116.342776);
		Page<Item> itempages = itemServiceImpl.getItemsPagesByLocationDistance(gp, "10km", pageable);
		List<Item> itemlist = itemServiceImpl.getItemsByLocationDistance(gp, "10km", pageable);
		System.out.println("count:"+itemServiceImpl.getItemsCountByLocationDistance(gp, "10km"));
		
		for (Item item : itempages) {
			System.out.println(item.toString());
		}
		
		for (Item item : itemlist) {
			System.out.println(item.toString());
		}
	}
	
	@Test
	public void getItemByLocationBox() {
		Pageable pageable = PageRequest.of(0, 10);
		GeoPoint topLeft = new GeoPoint(40.169467,116.192125);//六环左上角
		GeoPoint bottomRight = new GeoPoint(39.822785,116.523276);//六环右下角
		Page<Item> itempages = itemServiceImpl.getItemsPagesByLocationBox(topLeft, bottomRight, pageable);
		List<Item> itemlist = itemServiceImpl.getItemsByLocationBox(topLeft, bottomRight, pageable);
		
		System.out.println("count:"+itemServiceImpl.getItemsCountByLocationBox(topLeft, bottomRight));
		
		for (Item item : itempages) {
			System.out.println(item.toString());
		}
		
		for (Item item : itemlist) {
			System.out.println(item.toString());
		}
	}
	
	@Test
	public void getItemByLocationPolygon() {
		Pageable pageable = PageRequest.of(0, 10);
		List<GeoPoint> points = new ArrayList<>();           
		points.add(new GeoPoint(40.073246,116.138658));
		points.add(new GeoPoint(40.150498,116.29216));
		points.add(new GeoPoint(39.914492,116.401969));
		Page<Item> itempages = itemServiceImpl.getItemsPagesByLocationPolygon(points, pageable);
		List<Item> itemlist = itemServiceImpl.getItemsByLocationPolygon(points, pageable);
		
		System.out.println("count:"+itemServiceImpl.getItemsCountByLocationPolygon(points));
		
		for (Item item : itempages) {
			System.out.println(item.toString());
		}
		
		for (Item item : itemlist) {
			System.out.println(item.toString());
		}
	}
	
	@Test
	public void getAllItems() {
		List<Item> results = itemServiceImpl.getAllItems();
		
		for (Item item : results) {
			System.out.println(item);
		}
		
	}
	
	
}
