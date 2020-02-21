package fr.toyframework.cache;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultCache extends A_Cache implements Serializable {

	private static final long serialVersionUID = -4782241533664167503L;

	private Map<Object,Map<Object,Object>> cache = new LinkedHashMap<Object,Map<Object,Object>>();
	

	public void clear() {
		cache.clear();
	}


	public void clear(Object key, Object group) {
		cache.get(group).remove(key);
	}


	public void clearGroup(Object group) {
		cache.get(group).clear();
	}


	public boolean contains(Object group) {
		return cache.containsKey(group);
	}


	public boolean contains(Object key, Object group) {
		return cache.containsKey(group)&&cache.get(group).containsKey(key);
	}


	public Object retrieve(Object key, Object group) {
		return cache.containsKey(group)?cache.get(group).get(key):null;
	}


	public Map<Object,Object> retrieveGroup(Object group) {
		return cache.get(group);
	}


	public void store(Object key, Object group, Object val) {
		if (cache.containsKey(group)) cache.get(group).put(key, val);
		else { cache.put(group, new LinkedHashMap<Object,Object>()); cache.get(group).put(key, val); }
	}

}
