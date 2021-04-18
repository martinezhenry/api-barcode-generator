package com.hvscode.api.barcodegenerator.controller.impl;

import com.google.zxing.WriterException;
import com.hvscode.api.barcodegenerator.controller.BarCodeGeneratorController;
import com.hvscode.api.barcodegenerator.entity.BarCode;
import com.hvscode.api.barcodegenerator.entity.Message;
import com.hvscode.api.barcodegenerator.service.BarCodeGeneratorService;
import com.itextpdf.text.DocumentException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping(value = "/${version}/generator/")
public class BarCodeGeneratorControllerImpl implements BarCodeGeneratorController {

    private BarCodeGeneratorService barCodeGeneratorService;

    public BarCodeGeneratorControllerImpl(BarCodeGeneratorService barCodeGeneratorService) {
        this.barCodeGeneratorService = barCodeGeneratorService;
    }

    @Override
    @PostMapping(value = "{code-type}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"*"})
    public Message<BarCode> generateBarCode(@PathVariable(value = "code-type") String codeType, @RequestBody String sku) throws FileNotFoundException, DocumentException, WriterException {
        return this.barCodeGeneratorService.build(codeType, sku);
    }


}
