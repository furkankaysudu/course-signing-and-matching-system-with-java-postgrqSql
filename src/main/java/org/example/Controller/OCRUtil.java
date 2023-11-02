package org.example.Controller;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCRUtil {
    public static String extractTextFromPDFWithOCR(File pdfFile) {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata"); // Set the path to your tessdata directory

        try {
            return tesseract.doOCR(pdfFile);
        } catch (TesseractException e) {
            e.printStackTrace();
            return "OCR Error: " + e.getMessage();
        }
    }
}