package com.rpc.dto;

import com.rpc.enumeration.RpcResponseCode;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {

    /**
     * magic num, to identify serialize method
     */
    private static final long serialVersionUID = 0xABAABAL;
    private String requestId;
    private Integer code;
    private String message;
    private T data;
    
    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(RpcResponseCode.SUCCESS.getCode());
        response.setMessage(RpcResponseCode.SUCCESS.getMessage());
        response.setRequestId(requestId);
        if(data != null) {
            response.setData(data);
        }
        return response;
    }

    public static <T> RpcResponse<T> fail(RpcResponseCode rpcResponseCode) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(rpcResponseCode.getCode());
        response.setMessage(rpcResponseCode.getMessage());
        return response;
    }
}
