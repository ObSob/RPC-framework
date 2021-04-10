package com.rpc;

import com.rpc.service.HelloService;
import com.rpc.service.impl.Hello;
import com.rpc.transport.ClientTransport;
import com.rpc.transport.RpcClientProxy;
import com.rpc.transport.netty.client.NettyClientTransport;

import java.net.InetSocketAddress;

public class NettyClientMain {
    public static void main(String[] args) {
        ClientTransport clientTransport = new NettyClientTransport(new InetSocketAddress("127.0.0.1", 9999));
        RpcClientProxy rpcClientProxy = new RpcClientProxy(clientTransport);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        String hello = helloService.hello(new Hello("111", "222"));
        System.out.println(hello);
        clientTransport.close();
    }
}
