package com.xz.es.entity.util;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.StrMinMax;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.xz.es.entity.AoiHomeWork;
import com.xz.es.entity.Item;

public class CsvUtil {
	private static final String[] FIELD_MAPPING = new String[] { 
	        "id",                   // simple field mapping (like CsvBeanReader)
	        "title",
	        "category",
	        "brand",
	        "price",
	        "images",
	        "location.lat", // indexed (first element) + deep mapping
	        "location.lon" // indexed (second element) + deep mapping
	        };
	
	private static final String[] AOI_HOMEWORK_FIELD_MAPPING = new String[] { 
	        "grid_x",                   // simple field mapping (like CsvBeanReader)
	        "grid_Y",
	        "location.lat",
	        "location.lon",
	        "city_id",
	        "bid",
	        "name",
	        "center_x",
	        "center_y",
	        "num"
	        };
	
	private static final String CSV_FILENAME = "testdata/test.csv";
	/**
	 * An example of reading using CsvDozerBeanReader.
	 */
	public static List<Item> readWithCsvDozerBeanReader(File file) throws Exception {
	        
	        final CellProcessor[] processors = new CellProcessor[] { 
	                new ParseLong(), //id
	                new Optional(),  //title
	                new Optional(),               // category
	                new Optional(),               // brand
	                new ParseDouble(),               // price
	                new Optional(),               // images
	                new Optional(),               // lat
	                new Optional()                // lon
	        };
	        
	        ICsvDozerBeanReader beanReader = null;
	        try {
	                beanReader = new CsvDozerBeanReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
	                
	                beanReader.getHeader(true); // ignore the header
	                beanReader.configureBeanMapping(Item.class, FIELD_MAPPING);
	                
	                
	                Item item;
	                List<Item> items = new ArrayList<>();
	                while( (item = beanReader.read(Item.class, processors)) != null ) {
	                		items.add(item);
	                       /* System.out.println(String.format("lineNo=%s, rowNo=%s, item=%s", beanReader.getLineNumber(),
	                                beanReader.getRowNumber(), item));*/
	                }
	                return items;
	        }
	        finally {
	                if( beanReader != null ) {
	                        beanReader.close();
	                }
	        }
			
	}
	/**
	 * An example of reading using CsvDozerBeanReader.
	 */
	public static List<AoiHomeWork> readWithCsvDozerBeanReaderAoiHomeWork(File file) throws Exception {
	        
	        final CellProcessor[] processors = new CellProcessor[] { 
	                new Optional(),//grid_x
	                new Optional(),  //grid_y
	                new Optional(),               // lat
	                new Optional(),               // lon
	                new Optional(),               // city_id
	                new Optional(),               // bid
	                new Optional(),               // name
	                new Optional(),                // center_x
	                new Optional(),					//center_y
	                new ParseInt()					//num
	        };
	        
	        ICsvDozerBeanReader beanReader = null;
	        try {
	                beanReader = new CsvDozerBeanReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
	                
	                beanReader.getHeader(true); // ignore the header
	                beanReader.configureBeanMapping(AoiHomeWork.class, AOI_HOMEWORK_FIELD_MAPPING);
	                
	                
	                AoiHomeWork item;
	                List<AoiHomeWork> items = new ArrayList<>();
	                while( (item = beanReader.read(AoiHomeWork.class, processors)) != null ) {
	                		items.add(item);
	                       /* System.out.println(String.format("lineNo=%s, rowNo=%s, aoihomework=%s", beanReader.getLineNumber(),
	                                beanReader.getRowNumber(), item));*/
	                }
	                return items;
	        }
	        finally {
	                if( beanReader != null ) {
	                        beanReader.close();
	                }
	        }
			
	}
	
}
