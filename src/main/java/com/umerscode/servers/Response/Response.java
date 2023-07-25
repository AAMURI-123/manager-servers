package com.umerscode.servers.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.umerscode.servers.Entity.Servers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@SuperBuilder
@JsonInclude(NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private LocalDateTime timeStamp;
    private int statusCode;
    private HttpStatus httpStatus;
    private String message;
    private String reason;
    private String developerMessage;
    protected List<?> data;

}
