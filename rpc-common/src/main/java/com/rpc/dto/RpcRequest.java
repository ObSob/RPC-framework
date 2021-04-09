package com.rpc.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@ToString
@Getter
@NoArgsConstructor
public class RpcRequest implements Serializable {
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
}
