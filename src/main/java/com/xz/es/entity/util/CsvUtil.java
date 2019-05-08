package com.xz.es.entity.util;

import java.io.File;
import java.io.FileReader;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;

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
	private static final String CSV_FILENAME = "testdata/test.csv";
	/**
	 * An example of reading using CsvDozerBeanReader.
	 */
	public static void readWithCsvDozerBeanReader(File file) throws Exception {
	        
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
	                while( (item = beanReader.read(Item.class, processors)) != null ) {
	                        System.out.println(String.format("lineNo=%s, rowNo=%s, item=%s", beanReader.getLineNumber(),
	                                beanReader.getRowNumber(), item));
	                }
	                
	        }
	        finally {
	                if( beanReader != null ) {
	                        beanReader.close();
	                }
	        }
	}
	
}
