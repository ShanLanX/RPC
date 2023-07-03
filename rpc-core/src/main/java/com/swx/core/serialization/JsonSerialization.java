package com.swx.core.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
// 会转化为json
public class JsonSerialization implements RpcSerialization{
    private static final ObjectMapper MAPPER;
    static {
        // 在序列化时包含所有字段（包括null和默认值）
        MAPPER=generateMapper(JsonInclude.Include.ALWAYS);
    }
    private static ObjectMapper generateMapper(JsonInclude.Include include){
        ObjectMapper mapper=new ObjectMapper();
        mapper.setSerializationInclusion(include);
        // 在反序列化时遇到未知属性不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        // 在反序列化时如果json字符串中博阿寒数字而不是字符串来标识枚举类型的值时是否抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS,true);
        // 自定义时间格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper;

    }
    @Override
    public <T> byte[] serialize(T obj) throws IOException {

        return obj instanceof String?((String)obj).getBytes():MAPPER.writeValueAsString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) throws IOException {
        return MAPPER.readValue(data,cls);
    }
}
