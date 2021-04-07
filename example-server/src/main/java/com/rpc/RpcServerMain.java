package com.rpc;

import com.rpc.service.HelloService;

public class RpcServerMain {
    public static void main(String[] args){
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9999);
    }
}
