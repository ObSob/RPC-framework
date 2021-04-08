package com.rpc;

import com.rpc.remoting.socket.RpcClientProxy;
import com.rpc.service.HelloService;
import com.rpc.service.impl.Hello;

public class RpcClientMain {
    public static void main(String[] args){
        RpcClientProxy rpcClientProxy = new RpcClientProxy("127.0.0.1", 9999);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        // only one service impl is used
        String hello = helloService.hello(new Hello("111", "222"));
        System.out.println(hello);
        String anotherHello = helloService.hello(new Hello("111", "333"));
        System.out.println(anotherHello);
    }
}
