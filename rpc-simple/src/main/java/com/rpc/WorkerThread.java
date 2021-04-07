package com.rpc;

import com.rpc.dto.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class WorkerThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WorkerThread.class);
    private Socket socket;
    private Object service;

    public WorkerThread(Socket socket, Object service){
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (   ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())){
            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            Object result = method.invoke(service, rpcRequest.getParameters());
            oos.writeObject(result);
            oos.flush();
        } catch (IOException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            logger.error("Exception: ", e);
        }
    }
}
