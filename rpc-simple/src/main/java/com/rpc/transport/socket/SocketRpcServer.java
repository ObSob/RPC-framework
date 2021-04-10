package com.rpc.transport.socket;

import com.rpc.provider.ServiceProvider;
import com.rpc.provider.ServiceProviderImpl;
import com.rpc.registy.ServiceRegistry;
import com.rpc.registy.zk.ZKServiceRegistry;
import com.rpc.transport.RpcRequestHandler;
import com.rpc.utils.concurrent.ThreadPoolFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketRpcServer {
    private static final Logger logger = LoggerFactory.getLogger(SocketRpcServer.class);
    private final String host;
    private final int port;
    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;
    private ExecutorService threadPool;

    private RpcRequestHandler rpcRequestHandler = new RpcRequestHandler();

    public SocketRpcServer(String host, int port) {
        this.host = host;
        this.port = port;
        this.threadPool = ThreadPoolFactoryUtils.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        serviceRegistry = new ZKServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
    }

    public <T> void publishService(Object service, Class<T> serviceClass) {
        serviceProvider.addServiceProvider(service);
        serviceRegistry.registerService(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }

    public void start() {
        try (ServerSocket server = new ServerSocket()){
            server.bind(new InetSocketAddress(host, port));
            logger.info("server starts...");
            Socket socket;
            while ((socket = server.accept()) != null) {
                logger.info("client connected");
                threadPool.execute(new SocketRpcRequestHandlerRunnable(socket));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("occur IOException:", e);
        }
    }
}
