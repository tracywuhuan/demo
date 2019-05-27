package com.xz.es.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xz.es.entity.AoiHomeWork;
import com.xz.es.entity.Item.MyGeoPoint;
import payloads.PolyonRequest;
import com.xz.es.service.impl.AoiHomeWorkServiceImpl;

@RestController
@RequestMapping("/xz/aoihomework")
public class AoiHomeWorkController {
	
	@Autowired
	private AoiHomeWorkServiceImpl aoiHomeWorkServiceImpl;
	
	/**
	 * Get the health status of elastic search cluster
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/health",method = RequestMethod.GET)
	public boolean getHealthStatus() {
		try {
			return aoiHomeWorkServiceImpl.ping();
		} catch (Exception e) {
			
			return false;
		}
		
	}
	
	/**
	 * get items by GeoDistanceQuery
	 * example:http://localhost:8080/xz/aoihomework/items/distance?location=40.035627,116.342776&distance=10km&from=0&size=100
	 */
	@Secured ({"ROLE_USER"})
	@RequestMapping(value = "/items/distance", method = RequestMethod.GET)
	public List<AoiHomeWork> getItemsByLocationDistance(@RequestParam(value = "location") String location,
			@RequestParam(value = "distance") String distance, @RequestParam(value = "from") int from,
			@RequestParam(value = "size") int size) {
		
		Double latitude = Double.parseDouble(location.split(",")[0]);
		Double longitude = Double.parseDouble(location.split(",")[1]);
		GeoPoint locationPoint = new GeoPoint(latitude.doubleValue(), longitude.doubleValue());
		List<AoiHomeWork> items = aoiHomeWorkServiceImpl.getItemsByLocationDistance(locationPoint, distance, PageRequest.of(from, size));
		return items;
	}
	
	/**
	 * get items by GeoBoundingBoxQuery
	 * example:http://localhost:8080/xz/aoihomework/items/box?topleft=40.169467,116.192125&bottomright=39.822785,116.523276&from=0&size=100
	 */
	@Secured ({"ROLE_USER"})
	@RequestMapping(value = "/items/box", method = RequestMethod.GET)
	public List<AoiHomeWork> getItemsByLocationBox(@RequestParam(value = "topleft") String topleft,
			@RequestParam(value = "bottomright") String bottomright, @RequestParam(value = "from") int from,
			@RequestParam(value = "size") int size) {
		
		Double latitude = Double.parseDouble(topleft.split(",")[0]);
		Double longitude = Double.parseDouble(topleft.split(",")[1]);
		GeoPoint topleftPoint = new GeoPoint(latitude.doubleValue(), longitude.doubleValue());
		latitude = Double.parseDouble(bottomright.split(",")[0]);
		longitude = Double.parseDouble(bottomright.split(",")[1]);
		GeoPoint bottomrightPoint = new GeoPoint(latitude.doubleValue(), longitude.doubleValue());
		List<AoiHomeWork> items = aoiHomeWorkServiceImpl.getItemsByLocationBox(topleftPoint, bottomrightPoint, PageRequest.of(from, size));
		return items;
	}
	
	/**
	 * get items by GeoPolygonQuery
	 * example:post http://localhost:8080/xz/items/polygon
	 * {
	 * 	"myGeoPoints":
	 * 	[
	 * 		{"lat":40.073246,"lon":116.138658},
	 * 		{"lat":40.150498,"lon":116.29216},
	 * 		{"lat":39.914492,"lon":116.401969}
	 * 	]
	 * 	"from":0,
	 * 	"size":10
	 * }
	 */
	@Secured ({"ROLE_USER"})
	@RequestMapping(value = "/items/polygon", method = RequestMethod.POST)
	public List<AoiHomeWork> getItemsByLocationPolygon(@RequestBody PolyonRequest polyonRequestDto) {
		
		List<MyGeoPoint> myGeoPoints = polyonRequestDto.getMyGeoPoints();
		List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
		for (MyGeoPoint myGeoPoint : myGeoPoints) {
			geoPoints.add(new GeoPoint(myGeoPoint.getLat(), myGeoPoint.getLon()));
		}
		List<AoiHomeWork> items = aoiHomeWorkServiceImpl.getItemsByLocationPolygon(geoPoints, PageRequest.of(polyonRequestDto.getFrom(), polyonRequestDto.getSize()));
		return items;
	}
}
