package com.rpc.registy.zk;

import com.rpc.registy.ServiceRegistry;
import com.rpc.registy.zk.util.CuratorUtils;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class ZKServiceRegistry implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(CuratorUtils.class);
    private final CuratorFramework zkClient;

    public ZKServiceRegistry() {
        zkClient = CuratorUtils.getZkClient();
    }

    @Override
    public <T> void registerService(T service, InetSocketAddress inetSocketAddress) {
        StringBuilder serverPath = new StringBuilder(CuratorUtils.ZK_REGISTER_ROOT_PATH);
        serverPath.append(inetSocketAddress.toString());
        CuratorUtils.createPersistentNode(zkClient, serverPath.toString());
        logger.info("node create success, node: {}", serverPath);
    }
}
