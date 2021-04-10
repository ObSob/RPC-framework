package com.rpc;

import com.rpc.service.HelloService;
import com.rpc.transport.netty.server.NettyServer;

public class NettyServerMain {
    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        NettyServer nettyServer = new NettyServer("127.0.0.1", 9999);
        nettyServer.publishService(helloService, HelloService.class);
    }
}
