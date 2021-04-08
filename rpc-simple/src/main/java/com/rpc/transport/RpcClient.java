package com.rpc.transport;

import com.rpc.dto.RpcRequest;

public interface RpcClient {
    Object sendRpcRequest(RpcRequest rpcRequest);
}
