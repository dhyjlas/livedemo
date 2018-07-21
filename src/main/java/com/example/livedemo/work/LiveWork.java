package com.example.livedemo.work;

import com.example.livedemo.entity.LiveStream;
import com.example.livedemo.entity.Video;
import com.example.livedemo.utils.FFmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LiveWork implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(LiveWork.class);

    private volatile LiveStream liveStream;

    public LiveWork(LiveStream liveStream){
        this.liveStream = liveStream;
    }

    @Override
    public void run() {
        Video video;
        try {
            while (true) {
                if (liveStream.getNum() >= liveStream.getVideoList().size()) {
                    liveStream.setNum(0);
                }

                video = liveStream.getVideoList().get(liveStream.getNum());
                FFmpegUtils.rtmp(liveStream, video);
                liveStream.setNum(liveStream.getNum() + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("任务{}结束", this);
        }
    }


}
