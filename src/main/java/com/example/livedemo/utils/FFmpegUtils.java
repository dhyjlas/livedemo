package com.example.livedemo.utils;

import com.example.livedemo.entity.LiveStream;
import com.example.livedemo.entity.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FFmpegUtils {
    private static final Logger log = LoggerFactory.getLogger(FFmpegUtils.class);

    public static void rtmp(LiveStream liveStream, Video video) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        //rtmp推流
        command.add("D:\\ffmpeg\\bin\\ffmpeg.exe");
        command.add("-re");
        command.add("-i");
        command.add(video.getPath() + video.getName());
        command.add("-c");
        command.add("copy");
        command.add("-f");
        command.add("flv");
        command.add(liveStream.getVideoStream());

        //hls推流
//        command.add("D:\\ffmpeg\\bin\\ffmpeg.exe");
//        command.add("-re");
//        command.add("-i");
//        command.add(video.getPath() + video.getName());
//        command.add("-c:v");
//        command.add("libx264");
////        command.add("-s");  //设置分辨率
////        command.add("720x576");
//        command.add("-c:a");
//        command.add("copy");
//        command.add("-f");
//        command.add("hls");
//        command.add("-hls_time");
//        command.add("10");
//        command.add("D:\\nginx-rtmp\\temp\\hls\\"+liveStream.getStream()+".m3u8");

        log.info(command.toString());
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        liveStream.setProcess(process);
        new PrintStream(process.getInputStream()).start();
        process.waitFor();
    }
}
