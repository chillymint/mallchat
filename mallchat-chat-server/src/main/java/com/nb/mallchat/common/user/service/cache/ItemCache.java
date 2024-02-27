package com.nb.mallchat.common.user.service.cache;

import com.nb.mallchat.common.user.dao.ItemConfigDao;
import com.nb.mallchat.common.user.domain.entity.ItemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 */
@Component
public class ItemCache {
	@Autowired
	private ItemConfigDao itemConfigDao;


	@Cacheable(cacheNames = "item",key = "'itemsByType:'+#itemType")
	public List<ItemConfig> getByType(Integer itemType){
		return itemConfigDao.getByType(itemType);
	}

	@Cacheable(cacheNames = "item", key = "'item:'+#itemId")
	public ItemConfig getById(Long itemId) {
		return itemConfigDao.getById(itemId);
	}

	@CacheEvict
	public List<ItemConfig> evictByType(Integer itemType){
		return itemConfigDao.getByType(itemType);
	}
}
