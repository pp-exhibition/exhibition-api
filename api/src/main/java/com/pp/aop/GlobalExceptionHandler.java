package com.pp.aop;

import com.pp.exception.CustomException;
import com.pp.exception.ResponseCode;
import com.pp.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        errorLogging(ex, request);
        final String message = getErrorMessageByMethodArgumentNotValidException(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.error(ResponseCode.METHOD_ARG_NOT_VALID, message));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse<String>> handleRuntimeException(RuntimeException e, WebRequest request) {
        errorLogging(e, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.error(ResponseCode.INTERNAL_SERVER_ERROR, e));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<String>> handleCustomException(CustomException e, WebRequest request) {
        errorLogging(e, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.fail(e.getResponseCode()));
    }

    private void errorLogging(final Exception exception, final WebRequest request) {
        /*
         true   : uri=/myapp/login;client=127.0.0.1
         false  : uri=/myapp/login
         */
        final String uri = request.getDescription(false).replace("uri=", "");
        final String user = Objects.requireNonNullElse(request.getRemoteUser(), "unknown");

        final StringBuilder builder = new StringBuilder()
                .append(System.lineSeparator())
                .append(String.format("\t[REQUEST]\t\t\t\t\t%s ::: %s", uri, user))
                .append(System.lineSeparator());

        Iterator<String> parameters = request.getParameterNames();
        while (parameters.hasNext()) {
            final String parameterKey = parameters.next();
            builder.append(String.format("\t\t\t\t\t\t\t\t[param] %s = %s", parameterKey, request.getParameter(parameterKey)))
                    .append(System.lineSeparator());
        }

        builder.append(String.format("\t[EXCEPTION LOCATION]\t\t%s", exception.getStackTrace()[0]))
                .append(System.lineSeparator())
                .append(String.format("\t[EXCEPTION TYPE]\t\t\t%s", exception.getClass().getSimpleName()))
                .append(System.lineSeparator());

        if (exception instanceof CustomException customException) {
            builder.append(String.format("\t[EXCEPTION CODE]\t\t\t%s", customException.getResponseCode().getCode()))
                    .append(System.lineSeparator())
                    .append(String.format("\t[EXCEPTION MESSAGE]\t\t\t%s", customException.getResponseCode().getMessage()));
        } else if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException){
            getErrorMessageByMethodArgumentNotValidException(methodArgumentNotValidException);
            builder.append(String.format("\t[EXCEPTION MESSAGE]\t\t\t%s", getErrorMessageByMethodArgumentNotValidException(methodArgumentNotValidException)));
        }

        log.error(builder.toString());
    }

    private String getErrorMessageByMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return String.join(" ::: ", errorMessages);
    }

}
