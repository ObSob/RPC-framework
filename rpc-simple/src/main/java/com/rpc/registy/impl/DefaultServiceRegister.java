package com.rpc.registy.impl;

import com.rpc.enumeration.RpcErrorMessageEnum;
import com.rpc.exception.RpcException;
import com.rpc.registy.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultServiceRegister implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegister.class);

    /**
     * 接口名和服务的对应关系，TODO 处理一个接口被两个实现类实现的情况
     * key:service/interface name
     * value:service
     */
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();
        if (registeredService.contains(serviceName)) {
            logger.error("service: {} has been registered", serviceName);
            // todo: exception handle
            return;
        }
        registeredService.add(serviceName);
        Class[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class i: interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("Add service: {} and interface: {}", serviceName, service.getClass());
    }

    @Override
    public synchronized Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }
}
