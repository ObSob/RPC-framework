package com.rpc.registy;

public interface ServiceRegistry {
    <T> void register(T service);

    Object getService(String serviceName);
}
