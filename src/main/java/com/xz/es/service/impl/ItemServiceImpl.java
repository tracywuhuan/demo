package com.xz.es.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.xz.es.entity.Item;
import com.xz.es.repository.ItemRepository;
import com.xz.es.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	
	@Resource
	private ItemRepository itemRepository;
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public Item insertItem(Item item) {
		Item updatedItem = itemRepository.save(item);
		return updatedItem;
	}
	
	@Override
	public void insertItems(List<Item> items) {
		try {
			itemRepository.saveAll(items);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Boolean updateItem(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean dropItem(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Item> getItemsPagesByLocationDistance(GeoPoint gp, String distance,Pageable pageable) {
		
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("location");
		
		//以该点为中心
		geoDistanceQueryBuilder.point(gp.getLat(), gp.getLon());
		//半径
		geoDistanceQueryBuilder.distance(distance,DistanceUnit.KILOMETERS);
		
		boolQueryBuilder.filter(geoDistanceQueryBuilder);
		return itemRepository.search(boolQueryBuilder, pageable);
	}
	
	@Override
	public List<Item> getItemsByLocationDistance(GeoPoint gp, String distance, Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("location");
		
		//以该点为中心
		geoDistanceQueryBuilder.point(gp.getLat(), gp.getLon());
		//半径
		geoDistanceQueryBuilder.distance(distance,DistanceUnit.KILOMETERS);
		
		boolQueryBuilder.filter(geoDistanceQueryBuilder);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(searchQuery, Item.class);
	}

	@Override
	public long getItemsCountByLocationDistance(GeoPoint gp, String distance) {
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("location");
		
		//以该点为中心
		geoDistanceQueryBuilder.point(gp.getLat(), gp.getLon());
		//半径
		geoDistanceQueryBuilder.distance(distance,DistanceUnit.KILOMETERS);
		
		boolQueryBuilder.filter(geoDistanceQueryBuilder);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();
		return elasticsearchTemplate.count(searchQuery, Item.class);
	}


	@Override
	public Page<Item> getItemsPagesByLocationPolygon(List<GeoPoint> pointlist, Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		List<org.elasticsearch.common.geo.GeoPoint> list = new ArrayList<>();   
		
		for (GeoPoint geoPoint : pointlist) {//convert
			list.add(new org.elasticsearch.common.geo.GeoPoint(geoPoint.getLat(),geoPoint.getLon()));
		}
		GeoPolygonQueryBuilder geoPolygonQueryBuilder = new GeoPolygonQueryBuilder("location", list);
		
		boolQueryBuilder.filter(geoPolygonQueryBuilder);
		
		return itemRepository.search(boolQueryBuilder, pageable);
	}

	@Override
	public long getItemsCountByLocationPolygon(List<GeoPoint> pointlist) {
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		List<org.elasticsearch.common.geo.GeoPoint> list = new ArrayList<>();   
		
		for (GeoPoint geoPoint : pointlist) {//convert
			list.add(new org.elasticsearch.common.geo.GeoPoint(geoPoint.getLat(),geoPoint.getLon()));
		}
		GeoPolygonQueryBuilder geoPolygonQueryBuilder = new GeoPolygonQueryBuilder("location", list);
		
		boolQueryBuilder.filter(geoPolygonQueryBuilder);
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();
		return elasticsearchTemplate.count(searchQuery, Item.class);
	}

	@Override
	public List<Item> getItemsByLocationPolygon(List<GeoPoint> pointlist, Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		List<org.elasticsearch.common.geo.GeoPoint> list = new ArrayList<>();   
		
		for (GeoPoint geoPoint : pointlist) {//convert
			list.add(new org.elasticsearch.common.geo.GeoPoint(geoPoint.getLat(),geoPoint.getLon()));
		}
		GeoPolygonQueryBuilder geoPolygonQueryBuilder = new GeoPolygonQueryBuilder("location", list);
		
		boolQueryBuilder.filter(geoPolygonQueryBuilder);
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(searchQuery, Item.class);
	}
	
	@Override
	public List<Item> getItemsByLocationBox(GeoPoint topLeft, GeoPoint bottomRight, Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder = new GeoBoundingBoxQueryBuilder("location");
		
		geoBoundingBoxQueryBuilder.setCorners(topLeft.getLat(), topLeft.getLon(), bottomRight.getLat(), bottomRight.getLon());
		
		boolQueryBuilder.filter(geoBoundingBoxQueryBuilder);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(searchQuery, Item.class);
	}
	
	@Override
	public Page<Item> getItemsPagesByLocationBox(GeoPoint topLeft, GeoPoint bottomRight,Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder = new GeoBoundingBoxQueryBuilder("location");
		
		geoBoundingBoxQueryBuilder.setCorners(topLeft.getLat(), topLeft.getLon(), bottomRight.getLat(), bottomRight.getLon());
		
		boolQueryBuilder.filter(geoBoundingBoxQueryBuilder);
		
		return itemRepository.search(boolQueryBuilder, pageable);
	}
	
	@Override
	public long getItemsCountByLocationBox(GeoPoint topLeft, GeoPoint bottomRight) {
		
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder = new GeoBoundingBoxQueryBuilder("location");
		
		geoBoundingBoxQueryBuilder.setCorners(topLeft.getLat(), topLeft.getLon(), bottomRight.getLat(), bottomRight.getLon());
		
		boolQueryBuilder.filter(geoBoundingBoxQueryBuilder);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();
		return elasticsearchTemplate.count(searchQuery, Item.class);
	}



	
	@Override
	public List<Item> getAllItems() {
		//System.out.println("allitems count:"+itemRepository.count());
	    NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
	    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	    boolQueryBuilder.must(QueryBuilders.matchAllQuery());
	    searchQuery.withQuery(boolQueryBuilder)
	    .withPageable(PageRequest.of(0, (int) itemRepository.count()));//return all the items as test.csv is small
	    
	    List<Item> results = elasticsearchTemplate.queryForList(searchQuery.build(), Item.class);
	    
	    return results;
	}


}
