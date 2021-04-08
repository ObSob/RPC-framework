package com.rpc;

import com.rpc.registy.impl.DefaultServiceRegister;
import com.rpc.remoting.socket.RpcServer;
import com.rpc.service.HelloService;

public class RpcServerMain {
    public static void main(String[] args){
        // register
        DefaultServiceRegister defaultServiceRegister = new DefaultServiceRegister();
        defaultServiceRegister.register(new HelloServiceAnotherImpl());
        defaultServiceRegister.register(new HelloServiceImpl());
        RpcServer rpcServer = new RpcServer(defaultServiceRegister);
        rpcServer.start(9999);
    }
}
