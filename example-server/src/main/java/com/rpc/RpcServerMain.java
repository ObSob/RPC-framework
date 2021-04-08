package com.rpc;

import com.rpc.registy.impl.DefaultServiceRegister;
import com.rpc.transport.socket.SocketRpcServer;

public class RpcServerMain {
    public static void main(String[] args){
        // register
        DefaultServiceRegister defaultServiceRegister = new DefaultServiceRegister();
        defaultServiceRegister.register(new HelloServiceAnotherImpl());
        defaultServiceRegister.register(new HelloServiceImpl());
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        socketRpcServer.start(9999);
    }
}
