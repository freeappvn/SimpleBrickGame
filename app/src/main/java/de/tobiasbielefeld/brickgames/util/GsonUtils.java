/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 *
 */

package de.tobiasbielefeld.brickgames.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.Map;

public class GsonUtils {

    /**
     * Convert String to Object
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T toObject(String json, Class<T> cls) {
        return new Gson().fromJson(json, cls);
    }

    /**
     * Convert Map String object to Object
     *
     * @param map
     * @param cls
     * @return
     */
    public static <T> T toObject(Map<String, Object> map, Class<T> cls) {
        return new Gson().fromJson(toString(map), cls);
    }

    /**
     * Convert String to Object
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T toObject(JsonElement json, Class<T> cls) {
        return new Gson().fromJson(json, cls);
    }

    /**
     * Convert Object to String using Json
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(obj);
    }

}
