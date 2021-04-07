package com.rpc;

import com.rpc.service.HelloService;
import com.rpc.service.impl.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceAnotherImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceAnotherImpl.class);

    @Override
    public String hello(Hello hello) {
        logger.info("HelloServiceAnotherImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        logger.info("HelloServiceAnotherImpl返回: {}.", result);
        return result;
    }
}
