package com.xz.es.service;

import java.util.List;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xz.es.entity.AoiHomeWork;

public interface AoiHomeWorkService {
	
	boolean ping();

	AoiHomeWork insertItem(AoiHomeWork item);
	
	void insertItems(List<AoiHomeWork> items);
	
	AoiHomeWork updateItem(AoiHomeWork item);
	
	void dropItem(AoiHomeWork item);
	
	void dropItems(List<AoiHomeWork> items);
	
	Page<AoiHomeWork> getItemsPagesByLocationBox(GeoPoint topLeft, GeoPoint bottomRight,Pageable pageable);
	
	List<AoiHomeWork> getItemsByLocationBox(GeoPoint topLeft, GeoPoint bottomRight,Pageable pageable);
	
	long getItemsCountByLocationBox(GeoPoint topLeft, GeoPoint bottomRight);
	
	Page<AoiHomeWork> getItemsPagesByLocationDistance(GeoPoint gp, String distance,Pageable pageable);
	
	List<AoiHomeWork> getItemsByLocationDistance(GeoPoint gp, String distance,Pageable pageable);
	
	long getItemsCountByLocationDistance(GeoPoint gp, String distance);
	
	Page<AoiHomeWork> getItemsPagesByLocationPolygon(List<GeoPoint> pointlist, Pageable pageable);
	
	List<AoiHomeWork> getItemsByLocationPolygon(List<GeoPoint> pointlist, Pageable pageable);
	
	long getItemsCountByLocationPolygon(List<GeoPoint> pointlist);
	
	List<AoiHomeWork> getAllItems();
	
	void dropAllItems();
}
