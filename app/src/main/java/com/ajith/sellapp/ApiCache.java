package com.ajith.sellapp;

import androidx.collection.SimpleArrayMap;

import com.ajith.sellapp.model.Product;

public class ApiCache {

    public static final String ONE_PRODUCT_DETAILS = "PRODUCT_DETAILS";

    private static ApiCache cache = new ApiCache();
    private SimpleArrayMap<String, Object> cachedObjects;


    private ApiCache() {
        cachedObjects = new SimpleArrayMap<>();
    }

    public static ApiCache getInstance() {
        return cache;
    }

    public synchronized void putCacheItem(String key, Object value) {
        cachedObjects.put(key, value);
    }
    public   static Product getOneProductDetails(){
        return  (Product) ApiCache.getInstance().getCachedObject(ApiCache.ONE_PRODUCT_DETAILS);
    }
    public synchronized void removeCacheItem(String key) {
        cachedObjects.remove(key);
    }

    public synchronized void clearCacheItem() {
        cachedObjects.clear();
    }


    private Object getCachedObject(String key) {
        if (cachedObjects.containsKey(key)) {
            return cachedObjects.get(key);
        } else {
            return null;
        }
    }
}
