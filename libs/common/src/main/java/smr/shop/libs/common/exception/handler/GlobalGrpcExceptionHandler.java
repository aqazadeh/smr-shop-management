package smr.shop.libs.common.exception.handler;

import io.grpc.Status;
import io.grpc.StatusException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import smr.shop.libs.common.exception.GlobalException;

@GrpcAdvice
public class GlobalGrpcExceptionHandler {

    @GrpcExceptionHandler(GlobalException.class)
    public StatusException handleGrpcException(GlobalException exception) {
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        System.out.println(exception.getStatus());
        Status status;
        switch (exception.getStatus()) {
            case NOT_FOUND -> {
                status = Status.NOT_FOUND.withDescription(exception.getMessage()).withCause(exception);
            }
            case BAD_REQUEST -> {
                status = Status.INVALID_ARGUMENT.withDescription(exception.getMessage()).withCause(exception);
            }
            default -> {
                status = Status.INTERNAL.withDescription(exception.getMessage()).withCause(exception);
            }
        }
        return status.asException();
    }
}
