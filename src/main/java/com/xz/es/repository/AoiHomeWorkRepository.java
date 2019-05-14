package com.xz.es.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.xz.es.entity.AoiHomeWork;


/**
 * @author wuhuan
 * @Param:
 *  AoiHomeWork: 实体类
 *  String: 实体类中的主键 Id
 */
public interface AoiHomeWorkRepository extends ElasticsearchRepository<AoiHomeWork, String>{
	

}
