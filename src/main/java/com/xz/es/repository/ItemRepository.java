package com.xz.es.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.xz.es.entity.Item;


/**
 * @author wuhuan
 * @Param:
 *  Item: 实体类
 *  Long: 实体类中的主键 Id
 */
public interface ItemRepository extends ElasticsearchRepository<Item, Long>{
	

}
