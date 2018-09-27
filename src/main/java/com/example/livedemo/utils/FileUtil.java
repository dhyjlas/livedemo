package com.example.livedemo.utils;

import java.io.*;
import java.nio.channels.FileChannel;

public class FileUtil {
    public static void uploadFile(FileInputStream io, String filePath, String fileName) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = io.getChannel();
            outputChannel = new FileOutputStream(new File(filePath + fileName)).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
}
