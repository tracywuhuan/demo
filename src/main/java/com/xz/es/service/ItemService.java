package com.xz.es.service;

import java.util.List;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xz.es.entity.Item;

public interface ItemService {

	Item insertItem(Item item);
	
	void insertItems(List<Item> items);
	
	Boolean updateItem(Item item);
	
	Boolean dropItem(Item item);
	
	Page<Item> getItemByLocationBox(GeoPoint topLeft, GeoPoint bottomRight,Pageable pageable);
	
	Page<Item> getItemByLocationDistance(GeoPoint gp, String distance,Pageable pageable);
	
	Page<Item> getItemByLocationPolygon(List<GeoPoint> pointlist, Pageable pageable);
	
	List<Item> getAllItems();
}
