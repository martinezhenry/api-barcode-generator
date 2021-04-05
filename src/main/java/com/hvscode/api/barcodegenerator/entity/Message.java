package com.hvscode.api.barcodegenerator.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message<T> {

    private String txId;
    private T body;
    private String message;
    private String responseCode;

}
