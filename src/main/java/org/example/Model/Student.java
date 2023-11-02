package org.example.Model;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Student {
    private int ogrNo;
    private String name;
    private String lastName;
    private boolean anlasmaDurumu;
    private File uploadedPDF;
    private float gno;
    private char harfNotu;
    private int anlasmaTalepSayisi;
    private String ilgiAlani;

    // Constructors, getters, and setters

    public Student(int ogrNo, String name, String lastName, boolean anlasmaDurumu, File uploadedPDF,
                   float gno, char harfNotu, int anlasmaTalepSayisi, String ilgiAlani) {
        this.ogrNo = ogrNo;
        this.name = name;
        this.lastName = lastName;
        this.anlasmaDurumu = anlasmaDurumu;
        this.uploadedPDF = uploadedPDF;
        this.gno = gno;
        this.harfNotu = harfNotu;
        this.anlasmaTalepSayisi = anlasmaTalepSayisi;
        this.ilgiAlani = ilgiAlani;
    }

    // Getters and setters for attributes

    public int getOgrNo() {
        return ogrNo;
    }

    public void setOgrNo(int ogrNo) {
        this.ogrNo = ogrNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAnlasmaDurumu() {
        return anlasmaDurumu;
    }

    public void setAnlasmaDurumu(boolean anlasmaDurumu) {
        this.anlasmaDurumu = anlasmaDurumu;
    }

    public File getUploadedPDF() {
        return uploadedPDF;
    }

    public void setUploadedPDF(File uploadedPDF) {
        this.uploadedPDF = uploadedPDF;
    }

    public float getGno() {
        return gno;
    }

    public void setGno(float gno) {
        this.gno = gno;
    }

    public char getHarfNotu() {
        return harfNotu;
    }

    public void setHarfNotu(char harfNotu) {
        this.harfNotu = harfNotu;
    }

    public int getAnlasmaTalepSayisi() {
        return anlasmaTalepSayisi;
    }

    public void setAnlasmaTalepSayisi(int anlasmaTalepSayisi) {
        this.anlasmaTalepSayisi = anlasmaTalepSayisi;
    }

    public String getIlgiAlani() {
        return ilgiAlani;
    }

    public void setIlgiAlani(String ilgiAlani) {
        this.ilgiAlani = ilgiAlani;
    }

    // Additional methods for your student class

    // Example method for uploading a PDF file
    public void uploadPDF(File pdfFile) throws IOException {
        // Check if the provided file is not null and exists
        if (pdfFile != null && pdfFile.exists()) {
            // Define a directory where you want to store the uploaded PDFs
            File uploadDirectory = new File("upload_directory");

            // Ensure the upload directory exists, create it if not
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            // Define a unique filename for the uploaded PDF (you can customize this)
            String uniqueFileName = "student_" + ogrNo + "_file.pdf";

            // Create a file object for the destination file
            File destinationFile = new File(uploadDirectory, uniqueFileName);

            // Copy the uploaded PDF to the destination directory
            Files.copy(pdfFile.toPath(), destinationFile.toPath());

            // Update the uploadedPDF attribute to reference the stored file
            uploadedPDF = destinationFile;
        } else {
            // Handle the case where the provided file is invalid
            System.err.println("Invalid or non-existent PDF file.");
        }
    }


    // Other methods and behaviors related to a student can be added here
}
