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
//        command.add("-re");
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

    /**
     * 视频截图
     * @param ffmpegPath
     * @param videoPath
     * @param mediaPicPath
     */
    public static void mediaPic(String ffmpegPath, String videoPath, String mediaPicPath){
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(videoPath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("5"); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add("480*270"); // 添加截取的图片大小为480*270
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

        log.info(cutpic.toString());
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(cutpic);
            builder.redirectErrorStream(true);
            builder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式转换
     * @param ffmpegPath
     * @param infile
     * @param outFile
     */
    public static void toMp4(String ffmpegPath, String infile, String outFile){
        List<String> command = new ArrayList<>();
        command.add(ffmpegPath);
        command.add("-i");
        command.add(infile);
        command.add(outFile);

        log.info(command.toString());
        try {
            Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
            new PrintStream(process.getInputStream()).start();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
