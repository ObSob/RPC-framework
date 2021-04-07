package com.rpc;

import com.rpc.service.HelloService;
import com.rpc.service.impl.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(Hello hello) {
        logger.info("HelloServiceImpl receive:{}.", hello.getMessage());
        String result = "Hello description is "  + hello.getDescription();
        logger.info("HelloServiceImpl return:{}.", result);
        return result;
    }
}
