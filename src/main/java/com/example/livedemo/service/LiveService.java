package com.example.livedemo.service;

import com.example.livedemo.dao.VideoDao;
import com.example.livedemo.entity.LiveStream;
import com.example.livedemo.entity.Video;
import com.example.livedemo.thread.LiveThreadPool;
import com.example.livedemo.utils.LiveList;
import com.example.livedemo.work.LiveWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LiveService {
    @Autowired
    private VideoDao videoDao;

    public void live(String id, String stream) throws Exception {
        LiveStream liveStream = null;

        //从数据库中查找视频
        Optional<Video> videoOptional = videoDao.findById(Long.valueOf(id));
        if (!videoOptional.isPresent()) {
            throw new Exception("未找到该视频ID");
        }

        //查找正在播放的视频流
        for(LiveStream live : LiveList.getLiveStreamList()){
            if(stream.equals(live.getStream()))
                liveStream = live;
        }

        if(liveStream == null) {
            //创建新的视频流
            liveStream = new LiveStream();
            liveStream.setNum(0);
            liveStream.setUrl("rtmp://192.168.1.137:1935/live/");
            liveStream.setStream(stream);
            List<Video> videoList = new ArrayList<>();
            videoList.add(videoOptional.get());
            liveStream.setVideoList(videoList);

            //开始推流
            LiveList.getLiveStreamList().add(liveStream);
            Runnable work = new LiveWork(liveStream);
            LiveThreadPool.getInstance().execute(work);
        }else{
            //加入到推流列表
            liveStream.addVideo(videoOptional.get());
        }
    }
}
