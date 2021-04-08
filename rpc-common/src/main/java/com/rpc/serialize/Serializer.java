package com.rpc.serialize;

public interface Serializer {

    /**
     * serialize
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * deserialize
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
