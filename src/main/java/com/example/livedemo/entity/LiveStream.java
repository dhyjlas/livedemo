package com.example.livedemo.entity;

import java.io.Serializable;
import java.util.List;

public class LiveStream implements Serializable {
    //视频
    private List<Video> videoList;

    //推流URL
    private String url;

    //推流名称
    private String stream;

    //执行Process
    private Process process;

    //正在播放的序号
    private int num;

    public String getVideoStream(){
        return url + stream;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void addVideo(Video video){
        videoList.add(video);
    }
}
