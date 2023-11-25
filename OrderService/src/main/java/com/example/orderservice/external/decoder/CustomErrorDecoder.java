package com.example.orderservice.external.decoder;

import com.example.orderservice.exception.ErrorResponse;
import com.example.orderservice.exception.RemoteException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;


@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorResponse errorResponse;
        ObjectMapper objectMapper=new ObjectMapper();

        log.info("Request URL : {}, Response Headers : {}",response.request().url(),response.headers());

        try {
            errorResponse = objectMapper.readValue(response.body().asInputStream()
                    , ErrorResponse.class);

        }
        catch (Exception e)
        {
            throw new RemoteException("Internal Error : "+e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return new RemoteException(errorResponse.getErrorMessage(),
                errorResponse.getErrorCode());
    }
}
