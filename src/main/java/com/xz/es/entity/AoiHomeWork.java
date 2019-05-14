package com.xz.es.entity;


import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

@Document(indexName = "aoi",type = "home_work")
public class AoiHomeWork {
	
	@Id
	private String id;
	
	@Field(type = FieldType.Keyword)
	private String grid_x;
	
	@Field(type = FieldType.Keyword)
	private String grid_y;
	
	@GeoPointField
	private MyGeoPoint location;
	
	@Field(type = FieldType.Keyword)
	private String city_id;
	
	@Field(type = FieldType.Keyword)
	private String bid;
	
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart",fielddata = true)
	private String name;
	
	@Field(type = FieldType.Keyword)
	private String center_x;
	
	@Field(type = FieldType.Keyword)
	private String center_y;
	
	@Field(type = FieldType.Integer)
	private Integer num;
	
	
	
	@Override
	public String toString() {
		return "AoiHomeWork [id=" + id + ", grid_x=" + grid_x + ", grid_y=" + grid_y + ", location=" + location
				+ ", city_id=" + city_id + ", bid=" + bid + ", name=" + name + ", center_x=" + center_x + ", center_y="
				+ center_y + ", num=" + num + "]";
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getGrid_x() {
		return grid_x;
	}



	public void setGrid_x(String grid_x) {
		this.grid_x = grid_x;
	}



	public String getGrid_y() {
		return grid_y;
	}



	public void setGrid_y(String grid_y) {
		this.grid_y = grid_y;
	}



	public MyGeoPoint getLocation() {
		return location;
	}



	public void setLocation(MyGeoPoint location) {
		this.location = location;
	}




	public String getCity_id() {
		return city_id;
	}



	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}



	public String getBid() {
		return bid;
	}



	public void setBid(String bid) {
		this.bid = bid;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getCenter_x() {
		return center_x;
	}



	public void setCenter_x(String center_x) {
		this.center_x = center_x;
	}



	public String getCenter_y() {
		return center_y;
	}



	public void setCenter_y(String center_y) {
		this.center_y = center_y;
	}



	public Integer getNum() {
		return num;
	}



	public void setNum(Integer num) {
		this.num = num;
	}



	public static class MyGeoPoint {
        private double lat;
        private double lon;

        public MyGeoPoint(double lat, double lon) {
			this.lat = lat;
			this.lon = lon;
		}
        
        public MyGeoPoint() {
		}

        public double getLat() {
			return lat;
		}


		public void setLat(double lat) {
			this.lat = lat;
		}


		public double getLon() {
			return lon;
		}


		public void setLon(double lon) {
			this.lon = lon;
		}


		@Override
        public String toString() {
            return "MyGeoPoint{" + "lat=" + lat + ", lon=" + lon + '}';
        }
    }
}
