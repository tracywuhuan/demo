package com.xz.es.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xz.es.entity.Item;
import com.xz.es.repository.ItemRepository;
import com.xz.es.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	
	@Resource
	private ItemRepository itemRepository;
	
	@Override
	public Boolean insertItem(Item item) {
		
		itemRepository.save(item);
		return null;
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
	public Page<Item> getItemByLocationBox(GeoPoint topLeft, GeoPoint bottomRight,Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder = new GeoBoundingBoxQueryBuilder("location");
		
		geoBoundingBoxQueryBuilder.setCorners(topLeft, bottomRight);
		
		boolQueryBuilder.filter(geoBoundingBoxQueryBuilder);
		
		return itemRepository.search(boolQueryBuilder, pageable);
	}

	@Override
	public Page<Item> getItemByLocationDistance(GeoPoint gp, String distance,Pageable pageable) {
		
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("location");
		
		//以该点为中心
		geoDistanceQueryBuilder.point(gp);
		//半径
		geoDistanceQueryBuilder.distance(distance,DistanceUnit.KILOMETERS);
		
		boolQueryBuilder.filter(geoDistanceQueryBuilder);
		
		return itemRepository.search(boolQueryBuilder, pageable);
	}

	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Item> getItemByLocationPolygon(List<GeoPoint> pointlist, Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		GeoPolygonQueryBuilder geoPolygonQueryBuilder = new GeoPolygonQueryBuilder("location", pointlist);
		
		boolQueryBuilder.filter(geoPolygonQueryBuilder);
		
		return itemRepository.search(boolQueryBuilder, pageable);
	}

}
