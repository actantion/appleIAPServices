package com.appleiapservices.demo.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.meitianiot.platform.open.exceptions.JsonException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;


/**
 * \@JsonIgnore用于忽略读取非本类的方法
 * \@JsonIgnoreProperties({"tableName"}) 用此注解屏蔽不需在转找成json的属性
 * \@JsonIgnoreProperties(ignoreUnknown=true)
 *
 * @createTime: 2011-9-22 上午9:34:47
 * @author: <a href="mailto:zhangqinglin@feinno.com">Zhang Qinglin</a>
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime:
 * @updateAuthor: <a href="mailto:zhangqinglin@feinno.com">Zhang Qinglin</a>
 * @changesSum:
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 转换数据对象为JSON
     *
     * @param vo
     * @return
     * @throws JsonProcessingException
     */
    public static String encodingJSON(Object vo) {
        try {
            return mapper.writeValueAsString(vo);
        }
        catch (JsonProcessingException e) {
            throw new JsonException("将对象转换为Json异常", e);
        }
    }

    /**
     * 转换数据对象为JSON
     *
     * @param vo
     * @return
     * @throws JsonProcessingException
     */
    public static byte[] encodingJSONAsBytes(Object vo) {
        try {
            return mapper.writeValueAsBytes(vo);
        }
        catch (JsonProcessingException e) {
            throw new JsonException("解码Json异常", e);
        }
    }


    /**
     * 将JSON 反序列化成对象
     *
     * @param json
     * @param type
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T decodingJSON(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        }
        catch (IOException e) {
            throw new JsonException("解码Json异常", e);
        }
    }

    /**
     * 将JSON 反序列化成对象
     *
     * @param json
     * @param type
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T decodingJSON(String json, JavaType type) {
        try {
            return mapper.readValue(json, type);
        }
        catch (IOException e) {
            throw new JsonException("解码Json异常", e);
        }
    }

    /**
     * 将JSON 反序列化成对象
     *
     * @param json
     * @param type
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T decodingJSONBytes(byte[] json, Class<T> type) {
        try {
            return mapper.readValue(json, 0, json.length, type);
        }
        catch (IOException e) {
            throw new JsonException("解码Json异常", e);
        }
    }

    /**
     * 将JSON 反序列化成对象
     *
     * @param json
     * @param type
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T decodingJSONBytes(byte[] json, JavaType type) {
        try {
            return mapper.readValue(json, 0, json.length, type);
        }
        catch (IOException e) {
            throw new JsonException("解码Json异常", e);
        }
    }

    /**
     * 将JSON 反序列化成对象
     *
     * @param json
     * @param typeReference
     * @return
     * @auther <a href="mailto:ssy8110@126.com">Zhang Qinglin</a>
     * 2014年12月18日 上午10:31:53
     */
    public static <T> T decodingJSON(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        }
        catch (IOException e) {
            throw new JsonException("解码Json异常", e);
        }
    }

    /**
     * 将JSON 反序列化成对象
     *
     * @param json
     * @param typeReference
     * @return
     * @auther <a href="mailto:ssy8110@126.com">Zhang Qinglin</a>
     * 2014年12月18日 上午10:31:53
     */
    public static <T> T decodingJSONBytes(byte[] json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, 0, json.length, typeReference);
        }
        catch (IOException e) {
            throw new JsonException("解码Json异常", e);
        }
    }

    /**
     * 获得java对象的数据类型
     *
     * @param clazz
     * @return
     */
    public static JavaType getJavaType(Type clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }

    /**
     * 获得Jackson ObjectMapper
     *
     * @return
     * @auther <a href="mailto:ssy8110@126.com">Zhang Qinglin</a>
     * 2015年1月7日 下午4:16:14
     */
    public static ObjectMapper getJsonMapper() {
        return mapper;
    }
}
