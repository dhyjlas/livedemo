package com.example.livedemo.controller;

import com.example.livedemo.entity.VideoDemand;
import com.example.livedemo.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class DemandController {
    @Autowired
    private DemandService demandService;

    /**
     * 截图路径
     */
    @Value("${media.pic.path}")
    private String picPath;

    /**
     * 视频路径
     */
    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    /**
     * 获取列表
     * @param page
     * @param size
     * @param sort
     * @param direction
     * @return
     */
    @RequestMapping("video")
    public Page<VideoDemand> video(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                   @RequestParam(value = "sort", defaultValue = "id") String sort,
                                   @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                   @RequestParam(value = "type", defaultValue = "0") String type){
        Sort sortDate = new Sort(Sort.Direction.ASC, sort);
        Pageable pageable = PageRequest.of(page, size, sortDate);
        Page<VideoDemand> videoDemandPage = demandService.getDemand(type, pageable);
        return videoDemandPage;
    }

    /**
     * 截图测试
     * @param name
     * @return
     */
    @RequestMapping("cutpie")
    public String cutpie(@RequestParam String name){
        demandService.cutPic(name);
        return "success";
    }



    /**
     * 获取图片
     * @param httpServletResponse
     * @param picName
     * @throws IOException
     */
    @RequestMapping("pic/{picName}")
    public void pic(HttpServletResponse httpServletResponse, @PathVariable String picName) throws IOException {
        if(new File(picPath + picName).exists()) {
            InputStream is = new FileInputStream(picPath + picName);
            OutputStream os = httpServletResponse.getOutputStream();
            int read = -1;
            while ((read = is.read()) != -1) {
                os.write(read);
            }
            os.flush();
            os.close();
            is.close();
        }
    }

    /**
     * 处理文件上传
     * @param file
     * @param type
     * @param viewpoint
     * @param context
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile file,
                         @RequestParam String type,
                         @RequestParam String viewpoint,
                         @RequestParam String context) throws IOException {
        VideoDemand videoDemand = new VideoDemand();
        videoDemand.setType(type);
        videoDemand.setViewpoint(viewpoint);
        videoDemand.setContext(context);
        demandService.save(file, videoDemand);

        return "success";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable long id){
        demandService.delete(id);
        return "<a href=\"/\">删除成功，点击后返回</a>";
    }
}
