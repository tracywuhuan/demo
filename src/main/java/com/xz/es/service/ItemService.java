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
	
	Page<Item> getItemsPagesByLocationBox(GeoPoint topLeft, GeoPoint bottomRight,Pageable pageable);
	
	List<Item> getItemsByLocationBox(GeoPoint topLeft, GeoPoint bottomRight,Pageable pageable);
	
	long getItemsCountByLocationBox(GeoPoint topLeft, GeoPoint bottomRight);
	
	Page<Item> getItemsPagesByLocationDistance(GeoPoint gp, String distance,Pageable pageable);
	
	List<Item> getItemsByLocationDistance(GeoPoint gp, String distance,Pageable pageable);
	
	long getItemsCountByLocationDistance(GeoPoint gp, String distance);
	
	Page<Item> getItemsPagesByLocationPolygon(List<GeoPoint> pointlist, Pageable pageable);
	
	List<Item> getItemsByLocationPolygon(List<GeoPoint> pointlist, Pageable pageable);
	
	long getItemsCountByLocationPolygon(List<GeoPoint> pointlist);
	
	List<Item> getAllItems();
}
