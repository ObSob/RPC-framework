package com.rpc;

import com.rpc.dto.RpcRequest;
import com.rpc.dto.RpcResponse;
import com.rpc.enumeration.RpcResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ClientMessageHandlerThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientMessageHandlerThread.class);
    private Socket socket;
    private Object service;

    public ClientMessageHandlerThread(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            Object result = invokeTargetMethod(rpcRequest);
            oos.writeObject(RpcResponse.success(result));
            oos.flush();
        } catch (IOException |
                ClassNotFoundException |
                IllegalAccessException |
                InvocationTargetException e) {
            logger.error("occur exception: ", e);
        }
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class<?> cls = Class.forName(rpcRequest.getInterfaceName());
        if (!cls.isAssignableFrom(service.getClass())) {
            return RpcResponse.fail(RpcResponseCode.NOT_FOUND_CLASS);
        }
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            logger.error("no such method: ", e);
            return RpcResponse.fail(RpcResponseCode.NOT_FOUND_METHOD);
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}
