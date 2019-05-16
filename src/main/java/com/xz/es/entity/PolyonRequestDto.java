package com.xz.es.entity;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xz.es.entity.Item.MyGeoPoint;

@Component
public class PolyonRequestDto {

	List<MyGeoPoint> myGeoPoints;
	int from;
	int size;
	
	public PolyonRequestDto() {
		
	}

	public List<MyGeoPoint> getMyGeoPoints() {
		return myGeoPoints;
	}

	public void setMyGeoPoints(List<MyGeoPoint> myGeoPoints) {
		this.myGeoPoints = myGeoPoints;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public PolyonRequestDto(List<MyGeoPoint> myGeoPoints, int from, int size) {
		super();
		this.myGeoPoints = myGeoPoints;
		this.from = from;
		this.size = size;
	}
	
	
	
}
