package com.example.livedemo.utils;

import com.example.livedemo.entity.LiveStream;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LiveList {
    private static List<LiveStream> liveStreamList = new CopyOnWriteArrayList<>();

    public static List<LiveStream> getLiveStreamList() {
        return liveStreamList;
    }
}
