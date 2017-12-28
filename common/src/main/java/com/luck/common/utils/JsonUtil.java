package com.luck.common.utils;

import com.google.gson.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * json 简单操作的工具类
 * 
 * @author zhangdaoming
 * 
 */

public class JsonUtil {
	private static Gson gson = null;
	static {
		if (gson == null) {
			//gson = new Gson();
			gson = new GsonBuilder().disableHtmlEscaping().create();
		}
	}

	private JsonUtil() {
	}

	/**
	 * 将对象转换成json格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJson(Object ts) {
		String jsonStr = null;
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将对象转换成json格式(并自定义日期格式)
	 * 
	 * @param ts
	 * 
	 * @return
	 */

	public static String objectToJsonDateSerializer(Object ts,
			final String dateformat) {
		String jsonStr = null;
		gson = new GsonBuilder().registerTypeAdapter(Date.class,
				new JsonSerializer<Date>() {
					public JsonElement serialize(Date src,java.lang.reflect.Type arg1,JsonSerializationContext arg2) {
						SimpleDateFormat format = new SimpleDateFormat(
								dateformat);
						return new JsonPrimitive(format.format(src));
					}
				}).setDateFormat(dateformat).create();
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将对象转换成json格式(日期处理成yyyy-MM-dd)
	 * @param ts
	 * @return
	 */
	public static String objectToJsonDateSerializerFormatYYYYMMDD(Object ts){
		return JsonUtil.objectToJsonDateSerializer(ts,"yyyy-MM-dd");
	}

	/**
	 * 将对象转换成json格式(日期处理成yyyy-MM-dd HH:mm:ss)
	 * @param ts
	 * @return
	 */
	public static String objectToJsonDateSerializerFormatYYYYMMDDHHMMSS(Object ts){
		return JsonUtil.objectToJsonDateSerializer(ts,"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将json转换成vo
	 * @param jsonStr
	 * @param cl
	 * @return
	 */
	public static Object netJsonToBean(String jsonStr,Class<?> cl){
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		return JSONObject.toBean(jsonObject,cl);
	}
	/**
	 * 
	 * 将json格式转换成list对象
	 * @param jsonStr
	 * @return
	 */

	public static List<?> jsonToList(String jsonStr) {
		List<?> objList = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
			}.getType();
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}
	
    /** 
     * 将json格式转换成list对象，并准确指定类型 
     * @param jsonStr 
     * @param type 
     * @return 
     */  
    public static List<?> jsonToList(String jsonStr, java.lang.reflect.Type type) {  
        List<?> objList = null;  
        if (gson != null) {  
            objList = gson.fromJson(jsonStr, type);  
        }  
        return objList;  
    }

	/**
	 * 
	 * 将json格式转换成map对象
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,String> jsonToMap(String jsonStr) {
		Map<String,String> objMap = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<String,String>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}
	public static Map<String,String> jsonToMap(String jsonStr,java.lang.reflect.Type type) {
		Map<String,String> objMap = null;
		if (gson != null) {
			type = new com.google.gson.reflect.TypeToken<Map<String,String>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}
	 public static Map<String, Object> parseJSON2Map(String jsonStr){  
	        Map<String, Object> map = new HashMap<String, Object>();  
	        //最外层解析  
	        JSONObject json = JSONObject.fromObject(jsonStr);  
	        for(Object k : json.keySet()){  
	            Object v = json.get(k);   
	            //如果内层还是数组的话，继续解析  
	            if(v instanceof JSONArray){  
	                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
	                Iterator<JSONObject> it = ((JSONArray)v).iterator();  
	                while(it.hasNext()){  
	                    JSONObject json2 = it.next();  
	                    list.add(parseJSON2Map(json2.toString()));  
	                }  
	                map.put(k.toString(), list);  
	            } else {  
	                map.put(k.toString(), v);  
	            }  
	        }  
	        return map;  
	    }  
	/**
	 * 将json转换成bean对象
	 * @param jsonStr
	 * @return
	 */
	public static Object jsonToBean(String jsonStr, Class<?> cl) {
		Object obj = null;
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return obj;
	}

	/**
	 * 将json转换成bean对象,含日期
	 * @param jsonStr
	 * @return
	 */
	public static Object jsonToBeanDateSerializer(String jsonStr, Class<?> cl,final String dateformat) {
		Object obj = null;
		gson = new GsonBuilder().registerTypeAdapter(Date.class,
				new JsonSerializer<Date>() {
					public JsonElement serialize(Date src,java.lang.reflect.Type arg1,JsonSerializationContext arg2) {
						SimpleDateFormat format = new SimpleDateFormat(
								dateformat);
						return new JsonPrimitive(format.format(src));
					}
				}).setDateFormat(dateformat).create();
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return obj;
	}

	/**
	 * 根据
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static Object getJsonValue(String jsonStr, String key) {
		Object rulsObj = null;
		Map<?, ?> rulsMap = jsonToMap(jsonStr);
		if (rulsMap != null && rulsMap.size() > 0) {
			rulsObj = rulsMap.get(key);
		}
		return rulsObj;
	}

	/**
	 * map转成json字符串
	 * @param params
	 * @return
	 * @throws JSONException
	 */
	public static String buildJSON(Map<String, Object> params)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry param : params.entrySet()) {
			if ((param.getKey() != null) && (!"".equals(param.getKey()))) {
				jsonObject.put((String) param.getKey(),
						param.getValue() == null ? "" : param.getValue());
			}
		}
		return jsonObject.toString();
	}
}
