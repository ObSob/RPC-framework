package com.rpc.transport;

import com.rpc.dto.RpcRequest;

public interface ClientTransport {
    Object sendRpcRequest(RpcRequest rpcRequest);

    void close();
}
