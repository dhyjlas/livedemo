package com.example.livedemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.livedemo.entity.LiveStream;
import com.example.livedemo.service.LiveService;
import com.example.livedemo.utils.LiveList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiveController {
    private static final Logger log = LoggerFactory.getLogger(LiveController.class);

    @Autowired
    private LiveService liveService;

    /**
     * 提交视频
     * @param id
     * @param live
     * @return
     * @throws Exception
     */
//    @RequestMapping("test")
//    public String test(@RequestParam String id, @RequestParam String live) throws Exception {
//        liveService.live(id, live, "live");
//
//        return "success";
//    }

    /**
     * 提交视频
     * @param id
     * @param live
     * @return
     * @throws Exception
     */
    @RequestMapping("test")
    public String test(@RequestParam String id, @RequestParam String live, @RequestParam String type) throws Exception {
        if(!"hls".equals(type) && !"live".equals(type)){
            type = "live";
        }

        liveService.live(id, live, type);

        return "success";
    }

    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }

    /**
     * 停止推流
     * @return
     */
    @RequestMapping("stop")
    public String stop(){
        for(LiveStream liveStream : LiveList.getLiveStreamList()){
            liveStream.getProcess().destroy();
        }
        LiveList.getLiveStreamList().clear();

        return "stop";
    }

    /**
     * 在线推流列表
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return JSONObject.toJSON(LiveList.getLiveStreamList()).toString();
    }
}
