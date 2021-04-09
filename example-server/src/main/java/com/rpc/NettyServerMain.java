package com.rpc;

import com.rpc.registy.impl.DefaultServiceRegister;
import com.rpc.transport.netty.server.NettyRpcServer;

public class NettyServerMain {
    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        DefaultServiceRegister defaultServiceRegister = new DefaultServiceRegister();
        defaultServiceRegister.register(helloService);
        NettyRpcServer nettyRpcServer = new NettyRpcServer(9999);
        nettyRpcServer.run();
    }
}
