package com.fucn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(NON_DEFAULT)
public class HttpResponse implements Serializable {

    private String timeStamp;
    private Integer statusCode;
    private HttpStatus status;
    private String reason;
    private String message;
    private String developerMessage;
    private transient Map<?, ?> data;
//    transient
}
