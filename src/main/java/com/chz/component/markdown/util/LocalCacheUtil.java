package com.chz.component.markdown.util;

import java.util.HashMap;
import java.util.Map;

public class LocalCacheUtil {

    public static Map<String,Object> cacheMaps = new HashMap<>();

    public static Map<String,Object> getAttrs(){
        return cacheMaps;
    }

    public static void set(String key,Object value){
        cacheMaps.put(key,value);
    }

    public static Object get(String key){
        return cacheMaps.get(key);
    }

    public static void del(String key){
        cacheMaps.remove(key);
    }

    public static void removeAll(){
        cacheMaps = null;
        cacheMaps = new HashMap<>();
    }
}
