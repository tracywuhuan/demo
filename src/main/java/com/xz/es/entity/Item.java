package com.xz.es.entity;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

@Document(indexName = "item",type = "docs")
public class Item {

	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", category=" + category + ", brand=" + brand + ", price="
				+ price + ", images=" + images + ", location=" + location + "]";
	}
	@Id
	private Long id;
	
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
	private String title;
	
	@Field(type = FieldType.Keyword)
	private String category;
	
	@Field(type = FieldType.Keyword)
	private String brand;
	
	@Field(type = FieldType.Double)
	private Double price;
	
	@Field(index = false, type = FieldType.Keyword)
	private String images;
	
	@GeoPointField
	private GeoPoint location;
	
	public GeoPoint getLocation() {
		return location;
	}

	public void setLocation(GeoPoint location) {
		this.location = location;
	}
	
	public Item(Long id, String title, String category, String brand, Double price, String images, GeoPoint location) {
		super();
		this.id = id;
		this.title = title;
		this.category = category;
		this.brand = brand;
		this.price = price;
		this.images = images;
		this.location = location;
	}

	public Item() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	
	
	
}
