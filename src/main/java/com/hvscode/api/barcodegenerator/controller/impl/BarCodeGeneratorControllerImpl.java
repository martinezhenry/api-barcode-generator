package com.hvscode.api.barcodegenerator.controller.impl;

import com.google.zxing.WriterException;
import com.hvscode.api.barcodegenerator.controller.BarCodeGeneratorController;
import com.hvscode.api.barcodegenerator.entity.BarCode;
import com.hvscode.api.barcodegenerator.entity.Message;
import com.hvscode.api.barcodegenerator.service.BarCodeGeneratorService;
import com.itextpdf.text.DocumentException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping(value = "/${version}/generator/")
public class BarCodeGeneratorControllerImpl implements BarCodeGeneratorController {

    private BarCodeGeneratorService barCodeGeneratorService;

    public BarCodeGeneratorControllerImpl(BarCodeGeneratorService barCodeGeneratorService) {
        this.barCodeGeneratorService = barCodeGeneratorService;
    }

    @Override
    @GetMapping(value = "{code-type}/{sku}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message generateBarCode(@PathVariable String codeType, @PathVariable String sku) throws FileNotFoundException, DocumentException, WriterException {
        return this.barCodeGeneratorService.build(codeType, sku);
    }


}
