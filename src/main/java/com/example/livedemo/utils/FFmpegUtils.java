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
        command.add("D:\\ffmpeg\\bin\\ffmpeg.exe");
        command.add("-re");
        command.add("-i");
        command.add(video.getPath() + video.getName());
        command.add("-c");
        command.add("copy");
        command.add("-f");
        command.add("flv");
        command.add(liveStream.getVideoStream());

        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        liveStream.setProcess(process);
        new PrintStream(process.getInputStream()).start();
        process.waitFor();
    }
}
