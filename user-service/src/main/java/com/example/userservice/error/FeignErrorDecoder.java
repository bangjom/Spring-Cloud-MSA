package com.example.userservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        if (status == 404) {
            if (methodKey.contains("getOrders")) {
                return new ResponseStatusException(HttpStatus.valueOf(response.status()), "User's orders is empty");
            }
        }
        return new Exception(response.reason());
    }
}
