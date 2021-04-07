package com.rpc;

import com.rpc.dto.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcClient {
    public static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    public Object sendRpcRequest(RpcRequest rpcRequest, String host, int port){
        try (Socket socket = new Socket(host, port)){
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(rpcRequest);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            logger.error("Exception: ", e);
        }
        return null;
    }
}
