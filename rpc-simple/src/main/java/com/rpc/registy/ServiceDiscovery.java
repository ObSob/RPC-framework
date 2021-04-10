package com.rpc.registy;

import java.net.InetSocketAddress;

public interface ServiceDiscovery {
    InetSocketAddress lookupService(String rpcServiceName);
}
