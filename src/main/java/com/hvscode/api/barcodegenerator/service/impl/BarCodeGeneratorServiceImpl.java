package com.hvscode.api.barcodegenerator.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hvscode.api.barcodegenerator.entity.BarCode;
import com.hvscode.api.barcodegenerator.entity.CodeType;
import com.hvscode.api.barcodegenerator.entity.Message;
import com.hvscode.api.barcodegenerator.service.BarCodeGeneratorService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class BarCodeGeneratorServiceImpl implements BarCodeGeneratorService {
    @Override
    public BarCode generateBarCode(String codeText) throws FileNotFoundException, DocumentException {

        Document document = new Document();


        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("./tmp/".concat(codeText)));
        document.open();
        Barcode39 barcode39 = new Barcode39();

        barcode39.setCode(codeText);

        Image image = barcode39.createImageWithBarcode(pdfWriter.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);

        BarCode barCode = new BarCode();

        barCode.setBarCodeRaw(image.getRawData());

        System.out.println(barCode.getBarCodeRaw());
        barCode.setCodeText(codeText);
        document.add(image);

        document.close();

        return barCode;
    }


    public static BufferedImage generateEAN13BarcodeImage(String codeText) {
        EAN13Bean barcodeGenerator = new EAN13Bean();
        BitmapCanvasProvider canvas =
                new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        barcodeGenerator.generateBarcode(canvas, codeText);
        return canvas.getBufferedImage();
    }

    public static BufferedImage generateCode128BarcodeImage(String codeText) {
        Code128Bean barcodeGenerator = new Code128Bean();
        BitmapCanvasProvider canvas =
                new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        barcodeGenerator.generateBarcode(canvas, codeText);
        return canvas.getBufferedImage();
    }


    @Override
    public Message<BarCode> build(String codeType, String codeText) throws FileNotFoundException, DocumentException, WriterException {

        Message<BarCode> message = new Message<>();
        BarCode barCode = new BarCode();
        BufferedImage image = null;
        switch (codeType) {
            case "code-128":
                image = generateCode128BarcodeImage(codeText);
                break;
            case "ean-13":
                image = generateEAN13BarcodeImage(codeText);
                break;
            case "qr":
                image = generateQRCodeImage(codeText);
                break;
            default:
                break;
        }

        Optional.ofNullable(image).ifPresent(img -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(img, "png", baos);
                byte[] bytes = baos.toByteArray();
                barCode.setBarCodeRaw(bytes);
                barCode.setCodeText(codeText);
                barCode.setMediaType(MediaType.APPLICATION_JSON_VALUE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        message.setBody(barCode);
        message.setMessage("Success");
        message.setResponseCode("00");
        message.setTxId(String.valueOf(System.currentTimeMillis()));

        return message;
    }

    public BufferedImage generateQRCodeImage(String qrText) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 400, 400);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
