package com.hayate.common.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Type

import java.util.*

/**
 * Created by Flame on 2020/03/20.
 */

object GsonUtils {

    val log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 序列化
     *
     * @param obj 对象
     * @return Json
     */
    fun serializer(obj: Any): String {
        return GsonBuilder().create().toJson(obj)
    }

    /**
     * 反序列化对象
     *
     * @param jsonData Json
     * @return 对象
     */
    inline fun <reified T> deserializer(jsonData: String): T? {
        return try {
            Gson().fromJson(jsonData, T::class.java)
        } catch (e: Exception) {
            log.error("Deserializer Error => ${e.message}")
            null
        }
    }

    /**
     * 反序列化对象
     *
     * @param jsonData Json
     * @return 对象
     */
    fun <T> deserializer(jsonData: String, type: Type): T? {
        return try {
            Gson().fromJson<T>(jsonData, type)
        } catch (e: Exception) {
            log.error("Deserializer Error => ${e.message}")
            null
        }
    }

    /**
     * 反序列化数组对象
     *
     * @param jsonData Json
     * @return 数组集合
     */
    inline fun <reified T> deserializerArray(jsonData: String): List<T> {
        return try {
            Gson().fromJson(jsonData, Array<T>::class.java).toList()
        } catch (e: Exception) {
            log.error("DeserializerArray Error => ${e.message}")
            arrayListOf()
        }
    }

    /**
     * 反序列化集合对象
     *
     * @param jsonData Json
     * @return 对象集合
     */
    inline fun <reified T> deserializerList(jsonData: String): List<T> {
        val gson = Gson()
        val type = object : TypeToken<Array<JsonObject>>() {}.type
        return try {
            gson.fromJson<List<JsonObject>>(jsonData, type)
                    .map { gson.fromJson(it, T::class.java) }
                    .toList()
        } catch (e: Exception) {
            log.error("DeserializerList Error => ${e.message}")
            arrayListOf()
        }
    }
}