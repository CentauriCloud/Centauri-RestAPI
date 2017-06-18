package org.centauri.cloud.rest.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

	private Map<String, Object> map = new HashMap<>();

	public static Map<String, Object> from(String key, Object value) {
		return Collections.singletonMap(key, value);
	}

	public static MapUtil builder() {
		return new MapUtil();
	}

	public MapUtil add(String key, Object value) {
		map.put(key, value);
		return this;
	}

	public Map<String, Object> build() {
		return map;
	}

}
