package com.rpc.transport.socket;

import com.rpc.dto.RpcRequest;
import com.rpc.dto.RpcResponse;
import com.rpc.exception.RpcException;

import com.rpc.registy.ServiceDiscovery;
import com.rpc.registy.ServiceRegistry;
import com.rpc.registy.zk.ZKServiceDiscovery;
import com.rpc.registy.zk.ZKServiceRegistry;
import com.rpc.transport.ClientTransport;
import com.rpc.utils.checker.RpcMessageChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketRpcClient implements ClientTransport {
    private static final Logger logger = LoggerFactory.getLogger(SocketRpcClient.class);
    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        serviceDiscovery = new ZKServiceDiscovery();
    }

    public Object sendRpcRequest(RpcRequest rpcRequest){
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        try (Socket socket = new Socket()){
            socket.connect(inetSocketAddress);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(rpcRequest);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RpcResponse rpcResponse = (RpcResponse) ois.readObject();
            //check RpcResponse and RpcRequest
            RpcMessageChecker.check(rpcResponse, rpcRequest);
            return rpcResponse.getData();
        } catch (IOException | ClassNotFoundException e){
            throw new RpcException("invoke service failed", e);
        }
    }

    @Override
    public void close() {

    }
}
