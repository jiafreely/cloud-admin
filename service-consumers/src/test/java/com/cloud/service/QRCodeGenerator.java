package com.cloud.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: QRCodeGenerator
 * @description: TODO
 * @date 2021/10/22 15:51
 */
public class QRCodeGenerator {
    // 暂定图片路径
    private static final String QR_CODE_IMAGE_PATH = "E:\\work\\cloud-admin\\img\\imgtest.png";

    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static void main(String[] args) {
        try {
            generateQRCodeImage("http://10.111.26.200:8120//admin/consumers/alipay/transcation", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }

}
