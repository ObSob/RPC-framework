package com.rpc;

import com.rpc.service.HelloService;
import com.rpc.transport.socket.SocketRpcServer;

public class RpcServerMain {
    public static void main(String[] args){
        HelloService helloService = new HelloServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer("127.0.0.1", 8080);
        socketRpcServer.publishService(helloService,HelloService.class);
    }
}
