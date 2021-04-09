package com.rpc.transport.socket;

import com.rpc.dto.RpcRequest;
import com.rpc.dto.RpcResponse;
import com.rpc.exception.RpcException;

import com.rpc.transport.RpcClient;
import com.rpc.utils.checker.RpcMessageChecker;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@AllArgsConstructor
public class SocketRpcClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketRpcClient.class);
    private String host;
    private int port;

    public Object sendRpcRequest(RpcRequest rpcRequest){
        try (Socket socket = new Socket(host, port)){
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
    public void shutdown() {

    }
}
