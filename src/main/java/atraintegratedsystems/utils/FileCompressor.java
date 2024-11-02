package atraintegratedsystems.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileCompressor {

    public static byte[] compressFile(byte[] fileData, String fileName) throws IOException {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ZipOutputStream zipStream = new ZipOutputStream(byteStream)) {

            ZipEntry zipEntry = new ZipEntry(fileName);
            zipStream.putNextEntry(zipEntry);
            zipStream.write(fileData);
            zipStream.closeEntry();

            return byteStream.toByteArray();
        }
    }
}