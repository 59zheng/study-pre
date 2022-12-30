package org.example.rpc.exception;


import org.example.rpc.enums.StatusEnum;

public class RpcException extends RuntimeException {

    public RpcException() {
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

    public RpcException(StatusEnum statusEnum) {
        super(statusEnum.getDescription());
    }
}
