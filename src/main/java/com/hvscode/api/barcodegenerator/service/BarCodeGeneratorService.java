package com.hvscode.api.barcodegenerator.service;

import com.google.zxing.WriterException;
import com.hvscode.api.barcodegenerator.entity.BarCode;
import com.hvscode.api.barcodegenerator.entity.Message;
import com.itextpdf.text.DocumentException;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public interface BarCodeGeneratorService {

    BarCode generateBarCode(String codeText) throws FileNotFoundException, DocumentException;

    Message<BarCode> build(String codeType, String codeText) throws FileNotFoundException, DocumentException, WriterException;

    BufferedImage generateQRCodeImage(String codeText) throws Exception;

}