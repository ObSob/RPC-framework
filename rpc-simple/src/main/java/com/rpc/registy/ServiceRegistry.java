package com.rpc.registy;

import java.net.InetSocketAddress;

public interface ServiceRegistry {
    <T> void registerService(T service, InetSocketAddress inetSocketAddress);
}
