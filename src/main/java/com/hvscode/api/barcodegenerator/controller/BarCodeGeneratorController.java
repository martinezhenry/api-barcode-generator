package com.hvscode.api.barcodegenerator.controller;

import com.google.zxing.WriterException;
import com.hvscode.api.barcodegenerator.entity.Message;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;

public interface BarCodeGeneratorController {


    Message generateBarCode(String codeType, String sku) throws FileNotFoundException, DocumentException, WriterException;


}
