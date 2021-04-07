package com.rpc;

import com.rpc.enumeration.RpcErrorMessageEnum;
import com.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private final ExecutorService threadPool;

    public RpcServer() {
        int corePoolSize = 10;
        int maximumPoolSize = 100;
        long keepAliveTime = 1;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MINUTES, workQueue, threadFactory);
    }

    public void register(Object service, int port) {
        if(service == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_NULL);
        }
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Server starts...");
            Socket socket;
            while((socket = server.accept()) != null){
                logger.info("client connected");
                threadPool.execute(new ClientMessageHandlerThread(socket, service));
            }
        } catch (IOException e){
            logger.error("Exception: ", e);
        }
    }
}
