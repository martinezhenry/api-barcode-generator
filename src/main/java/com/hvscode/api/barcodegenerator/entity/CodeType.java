package com.hvscode.api.barcodegenerator.entity;

import lombok.Getter;

public enum CodeType {
    CODE_128("code-128"),
    EAN_13("ean-13"),
    QR("qr");

    @Getter
    private String value;


    CodeType(String value) {
        this.value = value;
    }



}
