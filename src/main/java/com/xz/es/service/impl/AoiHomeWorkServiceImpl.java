package com.xz.es.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
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

import com.xz.es.entity.AoiHomeWork;
import com.xz.es.repository.AoiHomeWorkRepository;
import com.xz.es.service.AoiHomeWorkService;


@Service
public class AoiHomeWorkServiceImpl implements AoiHomeWorkService{

	@Resource
	TransportClient client;
	
	@Resource
	private AoiHomeWorkRepository aoiHomeWorkRepository;
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public boolean ping() {
		ActionFuture<ClusterHealthResponse> health = client.admin().cluster().health(new ClusterHealthRequest());
		ClusterHealthStatus status = health.actionGet().getStatus();
		if (status.value() == ClusterHealthStatus.RED.value()) {
			throw new RuntimeException(
					"elasticsearch cluster health status is red.");
		}
		return true;
	}

	@Override
	public AoiHomeWork insertItem(AoiHomeWork item) {
		return aoiHomeWorkRepository.save(item);
	}

	@Override
	public void insertItems(List<AoiHomeWork> items) {
		try {
			aoiHomeWorkRepository.saveAll(items);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public AoiHomeWork updateItem(AoiHomeWork item) {
		return insertItem(item);
	}

	@Override
	public void dropItem(AoiHomeWork item) {
		aoiHomeWorkRepository.delete(item);
	}

	@Override
	public void dropItems(List<AoiHomeWork> items) {
		aoiHomeWorkRepository.deleteAll(items);
		
	}
	
	@Override
	public List<AoiHomeWork> getAllItems() {
		//System.out.println("allitems count:"+itemRepository.count());
	    NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
	    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	    boolQueryBuilder.must(QueryBuilders.matchAllQuery());
	    searchQuery.withQuery(boolQueryBuilder)
	    .withPageable(PageRequest.of(0, (int) aoiHomeWorkRepository.count()));//return all the items as test.csv is small
	    
	    List<AoiHomeWork> results = elasticsearchTemplate.queryForList(searchQuery.build(), AoiHomeWork.class);
	    
	    return results;
	}

	@Override
	public void dropAllItems() {
		aoiHomeWorkRepository.deleteAll();
	}

	@Override
	public Page<AoiHomeWork> getItemsPagesByLocationDistance(GeoPoint gp, String distance,Pageable pageable) {
		
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("location");
		
		//以该点为中心
		geoDistanceQueryBuilder.point(gp.getLat(), gp.getLon());
		//半径
		geoDistanceQueryBuilder.distance(distance,DistanceUnit.KILOMETERS);
		
		boolQueryBuilder.filter(geoDistanceQueryBuilder);
		return aoiHomeWorkRepository.search(boolQueryBuilder, pageable);
	}
	
	@Override
	public List<AoiHomeWork> getItemsByLocationDistance(GeoPoint gp, String distance, Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("location");
		
		//以该点为中心
		geoDistanceQueryBuilder.point(gp.getLat(), gp.getLon());
		//半径
		geoDistanceQueryBuilder.distance(distance,DistanceUnit.KILOMETERS);
		
		boolQueryBuilder.filter(geoDistanceQueryBuilder);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(searchQuery, AoiHomeWork.class);
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
		return elasticsearchTemplate.count(searchQuery, AoiHomeWork.class);
	}


	@Override
	public Page<AoiHomeWork> getItemsPagesByLocationPolygon(List<GeoPoint> pointlist, Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		List<org.elasticsearch.common.geo.GeoPoint> list = new ArrayList<>();   
		
		for (GeoPoint geoPoint : pointlist) {//convert
			list.add(new org.elasticsearch.common.geo.GeoPoint(geoPoint.getLat(),geoPoint.getLon()));
		}
		GeoPolygonQueryBuilder geoPolygonQueryBuilder = new GeoPolygonQueryBuilder("location", list);
		
		boolQueryBuilder.filter(geoPolygonQueryBuilder);
		
		return aoiHomeWorkRepository.search(boolQueryBuilder, pageable);
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
		return elasticsearchTemplate.count(searchQuery, AoiHomeWork.class);
	}

	@Override
	public List<AoiHomeWork> getItemsByLocationPolygon(List<GeoPoint> pointlist, Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder  = new BoolQueryBuilder();
		List<org.elasticsearch.common.geo.GeoPoint> list = new ArrayList<>();   
		
		for (GeoPoint geoPoint : pointlist) {//convert
			list.add(new org.elasticsearch.common.geo.GeoPoint(geoPoint.getLat(),geoPoint.getLon()));
		}
		GeoPolygonQueryBuilder geoPolygonQueryBuilder = new GeoPolygonQueryBuilder("location", list);
		
		boolQueryBuilder.filter(geoPolygonQueryBuilder);
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(searchQuery, AoiHomeWork.class);
	}
	
	@Override
	public List<AoiHomeWork> getItemsByLocationBox(GeoPoint topLeft, GeoPoint bottomRight, Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder = new GeoBoundingBoxQueryBuilder("location");
		
		geoBoundingBoxQueryBuilder.setCorners(topLeft.getLat(), topLeft.getLon(), bottomRight.getLat(), bottomRight.getLon());
		
		boolQueryBuilder.filter(geoBoundingBoxQueryBuilder);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(searchQuery, AoiHomeWork.class);
	}
	
	@Override
	public Page<AoiHomeWork> getItemsPagesByLocationBox(GeoPoint topLeft, GeoPoint bottomRight,Pageable pageable) {
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder = new GeoBoundingBoxQueryBuilder("location");
		
		geoBoundingBoxQueryBuilder.setCorners(topLeft.getLat(), topLeft.getLon(), bottomRight.getLat(), bottomRight.getLon());
		
		boolQueryBuilder.filter(geoBoundingBoxQueryBuilder);
		
		return aoiHomeWorkRepository.search(boolQueryBuilder, pageable);
	}
	
	@Override
	public long getItemsCountByLocationBox(GeoPoint topLeft, GeoPoint bottomRight) {
		
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder = new GeoBoundingBoxQueryBuilder("location");
		
		geoBoundingBoxQueryBuilder.setCorners(topLeft.getLat(), topLeft.getLon(), bottomRight.getLat(), bottomRight.getLon());
		
		boolQueryBuilder.filter(geoBoundingBoxQueryBuilder);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();
		return elasticsearchTemplate.count(searchQuery, AoiHomeWork.class);
	}

}
