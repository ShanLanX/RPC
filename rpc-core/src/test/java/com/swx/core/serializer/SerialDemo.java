package com.swx.core.serializer;

import com.swx.core.serialization.FastJsonSerialization;
import com.swx.core.serialization.HessianSerialization;
import com.swx.core.serialization.JsonSerialization;
import com.swx.core.serialization.RpcSerialization;

import java.io.IOException;
import java.util.Arrays;

public class SerialDemo {
    public static void main(String[] args) throws IOException {
        RpcSerialization serializer=new JsonSerialization();
//        RpcSerialization serializer=new HessianSerialization();
//        RpcSerialization serializer=new FastJsonSerialization();
        User user=new User();
        user.setAge(18);
        user.setName("swx");
        byte[] bytes=serializer.serialize(user);
        System.out.println(bytes.length);
        System.out.println(new String(bytes));
        User user1=serializer.deserialize(bytes,User.class);
        System.out.println(user1);


    }
}
