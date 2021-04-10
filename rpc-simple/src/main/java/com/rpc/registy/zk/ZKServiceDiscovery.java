package com.rpc.registy.zk;

import com.rpc.enumeration.RpcErrorMessageEnum;
import com.rpc.exception.RpcException;
import com.rpc.registy.ServiceDiscovery;
import com.rpc.registy.zk.util.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

@Slf4j
public class ZKServiceDiscovery implements ServiceDiscovery {

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) {

        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (serviceUrlList == null || serviceUrlList.size() == 0) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        String serviceAddress = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName).get(0);
        log.info("find server address success: {}", serviceAddress);
        String[] hostAndPort = serviceAddress.split(":");
        return new InetSocketAddress(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
    }
}
