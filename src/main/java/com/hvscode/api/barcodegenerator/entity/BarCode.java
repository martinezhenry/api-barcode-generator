package com.hvscode.api.barcodegenerator.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarCode {

    private String codeText;
    private byte[] barCodeRaw;
    private String mediaType;

}
